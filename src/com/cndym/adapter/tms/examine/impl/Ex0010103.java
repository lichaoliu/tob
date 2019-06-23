package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0010103 extends BashExamina implements IExamine {

    private Logger logger = Logger.getLogger(Ex0010103.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("0010103", nums);
        for (String num : nums) {
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{tag, "@", "#"});//号码中分隔符的验证
            //胆@托#蓝球
            String[] mgs = num.split("#");
            String red = mgs[0];
            String blue = mgs[1];
            NumberRepeatExamine.defaultNumberRepeatExamine(red.replace("@", tag), tag, 0);//验证红球是否有重复
            NumberRepeatExamine.defaultNumberRepeatExamine(blue, tag, 0);//验证蓝球是否有重复
            String tuo = red.split("@")[1];
            String dan = red.split("@")[0];
            String[] danNums = dan.split(tag);
            String[] tuoNums = tuo.split(tag);
            int danCount = danNums.length;
            int tuoCount = tuoNums.length;
            NumberSortExamine.defaulstNumberSortExamine(dan, ",");//  验证号码是否按升序排序
            NumberSortExamine.defaulstNumberSortExamine(tuo, ",");//  验证号码是否按升序排序
            NumberSortExamine.defaulstNumberSortExamine(blue, ",");//  验证号码是否按升序排序
            if (danCount + tuoCount < 7) {
                throw new IllegalArgumentException("胆码+托码个数必须大于7");
            }
            NumberLengthExamine.defaultNumberLengthExamine(dan, tag, 6, -1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuo, tag, 1, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuo, tag, 21, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(tuo.split(tag), 1, 33, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(dan.split(tag), 1, 33, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(blue.split(tag), 1, 16, 2);//号码域验证
            temp += C(tuoCount, 6 - danCount) * blue.split(",").length;
        }
        //校验注数
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0010103 ex0010103 = new Ex0010103();
        ex0010103.examina("01,02@03,04,05,06,07,08,25#12,09", 70);
        System.out.println("ok");
    }

}
