package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;

/**
 * *USER:MengJingyi *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060604 extends BashExamina implements IExamine {

	@Override
	public void examina(String number, int item) {
		String ztag = ";";
		String[] nums = number.split(ztag);
		int ticket = getTicketCount("0060604");
		if (nums.length > ticket) {
			throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
		}
		int temp = 0;
		for (String num : nums) {
			NumberAreaExamine.defaultNumberAraeExamine(num, 0, 18);// 号码域验证
			String ssc2xzuxhz = BumberNoteTheNumberOfExamine.getSsc2xzuxhz().get(num);
			temp += new Integer(ssc2xzuxhz);
		}
		// 和值计算注数
		if (item != temp) {
			throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
		}
	}

	public static void main(String[] args) {
		Ex0060604 ex0060101 = new Ex0060604();
		ex0060101.examina("18", 1);
		System.out.println("ok");
	}
}