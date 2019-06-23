package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 作者：邓玉明
 * 时间：11-4-30 下午1:36
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class Ex1130202 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1130202.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1130202", nums);
        for (String num : nums) {
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "#"});//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 2, 0);//号码个数的验证
            String[] arr = num.split("#");
            NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 35, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 12, 2);//号码域验证
            /*NumberSortExamine.defaulstNumberSortExamine(arr[0], ",");//  验证号码是否按升序排序
            NumberSortExamine.defaulstNumberSortExamine(arr[1], ",");//  验证号码是否按升序排序*/
            NumberRepeatExamine.defaultNumberRepeatExamine(arr[0], ",", 0);
            NumberRepeatExamine.defaultNumberRepeatExamine(arr[1], ",", 0);
            int a = arr[0].split(",").length;
            int b = arr[1].split(",").length;
            if (a <= 5 && b <= 2) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
            temp += C(a, 5) * C(b, 2);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1130202 ex1080402 = new Ex1130202();
        ex1080402.examina("01,02,03,04,06#01,02,03;01,02,03,04,05#01,09,11", 6);
        System.out.println("ok");
    }
}
