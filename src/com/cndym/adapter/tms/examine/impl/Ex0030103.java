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
public class Ex0030103 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0030103.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0030102", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num.replace("@", ","), tag, 0);//验证是否有重复
            NumberLengthExamine.defaultNumberLengthExamine(num, "@", 2, 0);//号码个数的验证
            String[] arr = num.split("@");
            String dan = arr[0];
            String tuo = arr[1];
            NumberLengthExamine.defaultNumberLengthExamine(dan, tag, 5, -1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuo, tag, 15, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(tuo.split(tag), 1, 15, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(dan.split(tag), 1, 15, 2);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(dan, ",");
            NumberSortExamine.defaulstNumberSortExamine(tuo, ",");
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(dan).append(",").append(tuo).toString(), ",", 0);
            temp += C(tuo.split(",").length, (5 - dan.split(",").length));
        }

        //校验注数
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0030103 ex0030103 = new Ex0030103();
        ex0030103.examina("01,02,03,04@05,06,07,09,10,15,12,13,14,11", 10);
        System.out.println("ok");
    }
}
