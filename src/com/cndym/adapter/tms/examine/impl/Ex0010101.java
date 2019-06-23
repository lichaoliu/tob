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
public class Ex0010101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0010101.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0010101", nums);
        for (String num : nums) {
            NumberTagExamine.ssqNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 2, 0);//号码个数的验证
            String[] arr = num.split("#");
            String red = arr[0];
            String blue = arr[1];
            NumberSortExamine.defaulstNumberSortExamine(red, ",");//  验证号码是否按升序排序
            NumberRepeatExamine.defaultNumberRepeatExamine(red, tag, 0);//验证红球是否有重复
            NumberRepeatExamine.defaultNumberRepeatExamine(blue, tag, 0);//验证蓝球是否有重复
            NumberLengthExamine.defaultNumberLengthExamine(red, tag, 6, 0);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(blue, tag, 1, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(red.split(tag), 1, 33, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(blue.split(tag), 1, 16, 2);//号码域验证
        }

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0010101 ex0010101 = new Ex0010101();
        ex0010101.examina("01,02,03,04,05,07#08;01,02,03,04,05,07#08;01,02,03,04,05,07#08;01,02,03,04,05,07#08;01,02,03,04,05,07#08;01,02,03,04,05,07#08", 1);
    }
}
