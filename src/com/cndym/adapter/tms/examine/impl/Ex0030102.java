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
public class Ex0030102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0030102.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0030102", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 5, 1);//号码个数的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);//验证蓝球是否有重复
            NumberAreaExamine.defaultNumberAraeExamine(num.split(tag), 1, 15, 2);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");  //  验证号码是否按升序排序
            int a = num.split(tag).length;
            if (a <= 5) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
            temp += C(a, 5);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0030102 ex0030102 = new Ex0030102();
        ex0030102.examina("01,02,03,04,05,09;01,02,03,04,05,09;01,02,03,04,05,09;", 18);
        System.out.println("ok");
    }
}
