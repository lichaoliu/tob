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
public class Ex0080319 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0080319");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 3, 0);//号码个数的验证

            String[] numL = num.split(tag);
            String bai = numL[0];
            String shi = numL[1];
            String ge = numL[2];

            NumberAreaExamine.defaultNumberAraeExamine(ge, 0, 123456789);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(shi, 0, 123456789);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(bai, 0, 123456789);//号码域验证

            temp += 3 * ge.length() * shi.length() * bai.length();
        }
        if (temp != item) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080319 ex0060307 = new Ex0080319();
        ex0060307.examina("13,12,20", 24);
        System.out.println("ok");
    }
}
