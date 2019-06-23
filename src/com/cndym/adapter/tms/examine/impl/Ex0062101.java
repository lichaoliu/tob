package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0062101 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticketCount = getTicketCount("0062101");
        int ticket = nums.length;
        if (ticket > ticketCount) {
            throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
        }
        int myitem = 0;
        int temp = 0;
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberAreaExamine.defaultNumberAraeRxExamine(num.split(","), 0, 9, 1);//号码域验证
            myitem = BumberNoteTheNumberOfExamine.getRxNote(num.split(","), 1, 3, true);//判断是否为单式
            //校验注数
            temp += C(myitem, 2);
        }

        if (item != ticket || temp != ticket) {
            throw new IllegalArgumentException("实际注数为(" + temp + ")，传入注数为(" + item + ")应该为" + ticket);
        }
    }

    public static void main(String[] args) {
        Ex0062101 ex0062101 = new Ex0062101();
        ex0062101.examina("1,_,_,_,5;_,2,_,_,5;1,_,_,1,5;_,2,_,_,5;1,_,_,_,5", 5);
        System.out.println("ok");
    }
}
