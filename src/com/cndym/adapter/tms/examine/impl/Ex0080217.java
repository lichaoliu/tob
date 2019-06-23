package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import org.springframework.stereotype.Component;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0080217 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String ztag = ";";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0080217");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 0;
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num, 0, 18);//号码域验证
            temp++;
        }
        if (temp != item) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080217 ex0060207 = new Ex0080217();
        ex0060207.examina("12;17", 2);
        System.out.println("ok");
    }
}