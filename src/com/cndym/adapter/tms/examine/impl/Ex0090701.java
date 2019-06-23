package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

@Component
public class Ex0090701 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0090701", nums);
        
        for (String num : nums) {
            //校验格式
            String[] arr = num.split(",");
        	NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
        	NumberRepeatExamine.defaultNumberRepeatExamine(num, ",");//重复验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(arr, 0, 9, 1);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");// 验证号码是否按升序排序

            if (arr[0].equals(arr[1]) && (arr[1].equals(arr[2]))) {
            	throw new CndymException(ErrCode.E8111);
            }
        }

        //校验注数
        int ticket = nums.length;
        if (item != ticket) {
        	throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String args[]) {
    	Ex0090701 ex = new Ex0090701();
    	ex.examina("1,1,2;2,1,1", 2);
    	System.out.println("ok");
    }
}
