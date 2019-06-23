package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060207 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String ztag = ";";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0060207");
        if(nums.length > ticket){
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 2;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, temp, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(tag), 0, 9, 1);//号码域验证
        }
        if (temp * nums.length != item) {
            throw new IllegalArgumentException("实际注数(" + temp * nums.length + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0060207 ex0060207 = new Ex0060207();
        ex0060207.examina("2,8", 6);
        System.out.println("ok");
    }
}
