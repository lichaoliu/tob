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
public class Ex0080317 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0080317");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 0;
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num, 0, 27);//号码域验证
            temp++;
        }
        if (temp != item) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080317 ex0060307 = new Ex0080317();
        ex0060307.examina("12", 9);
        System.out.println("ok");
    }
}