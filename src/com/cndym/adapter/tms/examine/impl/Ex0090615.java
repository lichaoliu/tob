package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import org.springframework.stereotype.Component;

@Component
public class Ex0090615 extends BashExamina implements IExamine {
	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(this.ztag);
		int ticket = nums.length;
		int ticketCount = getTicketCount("0090615");
		if (ticket > ticketCount) {
			throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
		}
		int temp = 0;
		for (String num : nums) {
			NumberLengthExamine.defaultNumberLengthExamine(num, "|", 2L, 0L);
			String[] numMsg = num.split("|");
			NumberLengthExamine.defaultNumberLengthExamine(numMsg[0], ",", 0L, 1L);
			NumberAreaExamine.defaultNumberAraeExamine(numMsg[0].split(","), 0, 9, 1);
			NumberLengthExamine.defaultNumberLengthExamine(numMsg[1], ",", 0L, 1L);
			NumberAreaExamine.defaultNumberAraeExamine(numMsg[1].split(","), 0, 9, 1);
			int shi = numMsg[0].split(",").length;
			int ge = numMsg[1].split(",").length;
			temp += ge * shi;
		}

		if (temp != item)
			throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
	}
}