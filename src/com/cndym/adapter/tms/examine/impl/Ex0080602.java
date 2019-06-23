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
public class Ex0080602 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticket = nums.length;
        int ticketCount = getTicketCount("0080602");
        if (ticket > ticketCount) {
            throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
        }
        int temp = 0;
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            temp += C(num.split(",").length, 2);
        }
        //校验注数
        if (item != temp) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080602 ex0060101 = new Ex0080602();
        ex0060101.examina("1,9,1", 3);
        System.out.println("ok");
    }
}
