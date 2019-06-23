package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.springframework.stereotype.Component;

@Component
public class Ex0090101 extends BashExamina implements IExamine {

	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(ztag);
		NumberTicketCountExamine.defaulstNumberSortExamine("0090101", nums);
		
		for (String num : nums) {
			//校验格式
			NumberLengthExamine.defaultNumberLengthExamine2(num, 1, 0);// 号码个数的验证
			NumberAreaExamine.defaultNumberAraeExamine(num, 0, 9);// 号码域验证
		}
		
		// 校验注数
		int ticket = nums.length;
		if (item != ticket) {
			throw new CndymException(ErrCode.E8116);
		}
	}

	public static void main(String[] args) {
		Ex0090101 ex = new Ex0090101();
		ex.examina("1;2;3", 3);
		System.out.println("ok");
	}
}
