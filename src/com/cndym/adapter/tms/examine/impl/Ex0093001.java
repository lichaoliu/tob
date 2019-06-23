package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.springframework.stereotype.Component;

@Component
public class Ex0093001 extends BashExamina implements IExamine {
    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0093001", nums);
        
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 0);//号码个数的验证
            //号码域验证
            String[] numArray = num.split(",");
            for (String n : numArray) {
            	if (!"0".equals(n) && !"1".equals(n) && !"2".equals(n) && !"9".equals(n)) {
            		throw new IllegalArgumentException("投注号码(" + num + ")不为 0,1,2,9");
            	}
            }
        }

        //校验注数
        int ticket = nums.length;
        if (item != ticket) {
        	throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0093001 ex = new Ex0093001();
        ex.examina("9,1;9,0;9,2;9,9;9,2;9,1", 6);
        System.out.println("ok");
    }
}
