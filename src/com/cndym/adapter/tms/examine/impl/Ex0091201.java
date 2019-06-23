package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

@Component
public class Ex0091201 extends BashExamina implements IExamine {
    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0091201", nums);
        
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberAreaExamine.defaultNumberAraeRxExamine(num.split(","), 0, 9, 1);//号码域验证
            BumberNoteTheNumberOfExamine.getRxNote(num.split(","), 1, 3, true);//任选个数验证
        }
        
        int ticket = nums.length;
        if (item != ticket) {
        	throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
    	Ex0091201 ex = new Ex0091201();
        ex.examina("1,_,_,_,5;_,2,_,_,5;1,_,_,_,5;_,2,_,_,5;1,_,_,_,5", 5);
        System.out.println("ok");
    }
}
