package com.cndym.adapter.tms.examine.impl;

import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.Utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060502 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex0060502.class);

	 @Override
	    public void examina(String number, int item) {
	        String tag = ",";
	        String ztag = ";";
	        String[] ns = number.split(ztag);
	        int temp = 0;
	        for (String n : ns) {
	            NumberTagExamine.commaNumberTagExamine(n);//号码中分隔符的验证;
	            NumberLengthExamine.defaultNumberLengthExamine(n, tag, 5, 0);//号码个数的验证
	            for (String num : n.split(tag)) {
	                NumberAreaExamine.defaultNumberAraeExamine(Utils.strToStrArray(num), 0, 9);//号码域验证
	            }
	            String[] nums = n.split(tag);
	            int wan = Utils.strToStrArray(nums[0]).length;
	            int qian = Utils.strToStrArray(nums[1]).length;
	            int bai = Utils.strToStrArray(nums[2]).length;
	            int shi = Utils.strToStrArray(nums[3]).length;
	            int ge = Utils.strToStrArray(nums[4]).length;
	            if (ge <= 1 && shi <= 1 && bai <= 1 && qian <= 1 && wan <= 1) {
	                throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
	            }
	            temp += ge * shi * bai * qian * wan;
	        }
	        if (temp != item) {
	            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
	        }
	    }


	public static void main(String[] args) {
		Ex0060502 ex0060502 = new Ex0060502();
		ex0060502.examina("1,20,3,3,10", 4);
		System.out.println("ok");
	}
}
