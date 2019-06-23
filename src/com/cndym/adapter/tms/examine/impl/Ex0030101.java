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
public class Ex0030101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0030101.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticket = nums.length;
        NumberTicketCountExamine.defaulstNumberSortExamine("0030101", nums);
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证是否有重复
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 5, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 15, 2);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");  //  验证号码是否按升序排序

        }
        //校验注数
        if (item != ticket) {
            logger.info("实际注数(" + ticket + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex0030101 ex0030101 = new Ex0030101();
        ex0030101.examina("01,02,03,04,05;01,02,03,04,05;01,02,03,04,05;01,02,03,04,05;01,02,03,04,05", 5);
        System.out.println("ok");
    }
}
