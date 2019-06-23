package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.springframework.stereotype.Component;

@Component
public class Ex0090601 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0090601", nums);
        
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证是否有重复
        }
        //校验注数
        int ticket = nums.length;
        if (item != ticket) {
        	throw new CndymException(ErrCode.E8116);
        }
    }
    
    public static void main(String args[]) {
    	Ex0090601 ex = new Ex0090601();
    	ex.examina("3,4;8,9", 2);
    	System.out.println("ok");
    }
}
