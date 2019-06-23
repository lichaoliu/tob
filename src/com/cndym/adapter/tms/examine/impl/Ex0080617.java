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
public class Ex0080617 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticket = nums.length;
        int ticketCount = getTicketCount("0080617");
        if (ticket > ticketCount) {
            throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
        }
        for (String num : nums) {
            //校验格式
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 18);//号码域验证

        }
        //校验注数
        if (item != ticket) {
            throw new IllegalArgumentException("实际注数(" + ticket + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080617 ex0060101 = new Ex0080617();
        ex0060101.examina("10;11", 1);
        System.out.println("ok");
    }
}
