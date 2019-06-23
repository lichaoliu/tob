package com.cndym.adapter.tms.examine.impl;

import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;

/**
 * * USER:MengJingyi TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060602 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex0060602.class);

	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(";");
		int ticket = nums.length;
		int ticketCount = getTicketCount("0060602");
		if (ticket > ticketCount) {
			throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
		}

		int temp = 0;
		for (String num : nums) {
			// 校验格式
			NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 1);// 号码个数的验证
			NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, -1);// 号码个数的验证
			NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9);// 号码域验证
			int coun = num.split(",").length;
			temp += C(coun, 2);
		}

		// 校验注数
		if (item != temp) {
			throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
		}
	}

	public static void main(String[] args) {
		Ex0060602 ex0060602 = new Ex0060602();
		ex0060602.examina("0,2,3,5,7,8,9", 21);
		System.out.println("OK");
	}

}
