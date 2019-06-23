package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.exception.CndymException;
import org.springframework.stereotype.Component;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060702 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String ztag = ";";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0060702");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 0;
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证号码是否重复
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 11, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            temp += C(num.split(",").length);
        }
        //校验注数
        if (item != temp) {
            throw new CndymException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String [] args){
        Ex0060702 ex0060702 = new Ex0060702();
        ex0060702.examina("1,8",2);
        System.out.println("OK");
    }
}
