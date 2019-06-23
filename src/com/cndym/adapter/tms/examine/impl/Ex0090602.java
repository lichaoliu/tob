package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.springframework.stereotype.Component;

@Component
public class Ex0090602 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0090602", nums);

        int ticket = 0;
        for (String num : nums) {
            //校验格式
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证是否有重复
            NumberSortExamine.defaulstNumberSortExamine(num, ",");// 验证号码是否按升序排序
            ticket += A(num.split(",").length);
        }

        //校验注数
        if (item != ticket) {
        	throw new CndymException(ErrCode.E8116);
        }
    }
    
    private static int A(int num) {
    	int total = 0;
    	for (int i = 1; i <= num; i++) {
    		total += i;
    	}
    	return total;
    }
    
    public static void main(String args[]) {
    	Ex0090602 ex = new Ex0090602();
    	ex.examina("3,4,5;9,8,7", 12);
    	System.out.println("ok");
    }
}
