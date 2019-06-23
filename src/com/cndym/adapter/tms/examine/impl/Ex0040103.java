package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0040103 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0040103.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0040103", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{tag, "@"});//号码中分隔符的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num.replace("@", tag), tag, 0);//验证是否有重复
            NumberLengthExamine.defaultNumberLengthExamine(num, "@", 2, 0);//号码个数的验证
            String[] arr = num.split("@");
            String dan = arr[0];
            String tuo = arr[1];
            NumberLengthExamine.defaultNumberLengthExamine(dan, tag, 7, -1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuo, tag, 21, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(tuo.split(tag), 1, 30, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(dan.split(tag), 1, 30, 2);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(dan, ",");//  验证号码是否按升序排序
            NumberSortExamine.defaulstNumberSortExamine(tuo, ",");//  验证号码是否按升序排序


            temp += C(tuo.split(",").length, (7 - dan.split(",").length));
        }

        if (temp <= 1) {
            throw new IllegalArgumentException("不是合法胆拖号码(" + number + ")");
        }

        //校验注数
        if (temp != item) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0040103 ex0040103 = new Ex0040103();
        ex0040103.examina("01,02,03,05,06@07,09,24", 3);
        System.out.println("ok");
    }
}
