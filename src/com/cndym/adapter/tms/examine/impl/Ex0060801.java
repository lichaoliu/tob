package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060801 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticket = nums.length;
        int ticketCount = getTicketCount("0060801");
        if (ticket > ticketCount) {
            throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
        }
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证是否有重复
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证

        }
        //校验注数
        if (item != ticket) {
            throw new IllegalArgumentException("实际注数(" + ticket + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0060801 ex0060801 = new Ex0060801();
        ex0060801.examina("1,0,5;1,0,5;1,0,5;1,0,5;1,0,5;1,0,5;1,0,5;1,0,5;1,0,5;1,0,5", 4);
        System.out.println("ok");
    }
}
