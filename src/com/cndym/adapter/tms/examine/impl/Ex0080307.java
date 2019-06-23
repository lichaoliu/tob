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
public class Ex0080307 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        int temp = 3;
        String ztag = ";";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0080307");
        if(nums.length > ticket){
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
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
        Ex0080307 ex0060307 = new Ex0080307();
        ex0060307.examina("1,2,3;1,2,3;1,2,3;", 9);
        System.out.println("ok");
    }
}
