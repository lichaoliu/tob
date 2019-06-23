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
public class Ex0010102 extends BashExamina implements IExamine {

    private Logger logger = Logger.getLogger(Ex0010102.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("0010102", nums);
        for (String num : nums) {
            NumberTagExamine.ssqNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 2, 0);//号码个数的验证
            String[] arr = num.split("#");
            String red = arr[0];
            String blue = arr[1];
            NumberLengthExamine.defaultNumberLengthExamine(red, tag, 21, -1);//号码个数的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(red, tag, 0);//验证红球是否有重复
            NumberRepeatExamine.defaultNumberRepeatExamine(blue, tag, 0);//验证蓝球是否有重复
            NumberAreaExamine.defaultNumberAraeExamine(red.split(tag), 1, 33, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(blue.split(tag), 1, 16, 2);//号码域验证
            String[] redNums = red.split(tag);
            String[] blueNums = blue.split(tag);
            int a = redNums.length;
            int b = blueNums.length;
            NumberSortExamine.defaulstNumberSortExamine(red, ",");//  验证号码是否按升序排序
            NumberSortExamine.defaulstNumberSortExamine(blue, ",");//  验证号码是否按升序排序
            if (a <= 6 && b <= 1) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
            temp += C(a, 6) * C(b, 1);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0010102 ex0010102 = new Ex0010102();
        ex0010102.examina("01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20#07", 7);
        System.out.println("ok");
    }
}
