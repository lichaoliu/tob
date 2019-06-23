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
public class Ex0080510 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0080510");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 5, 0);//号码个数的验证

            String[] numL = num.split(tag);
            String wan = numL[0];
            String qian = numL[1];
            String bai = numL[2];
            String shi = numL[3];
            String ge = numL[4];

            NumberAreaExamine.defaultNumberAraeExamine(ge, 0, 123456789);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(shi, 0, 123456789);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(bai, 0, 123456789);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(qian, 0, 123456789);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(wan, 0, 123456789);//号码域验证

            temp += 5 * ge.length() * shi.length() * bai.length() * qian.length() * wan.length();
        }
        if (temp != item) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080510 ex0060508 = new Ex0080510();
        ex0060508.examina("1,2,3,4,51", 10);
        System.out.println("ok");
    }
}
