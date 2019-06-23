package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import org.springframework.stereotype.Component;

@Component
public class Ex0090617 extends BashExamina implements IExamine {
	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(this.ztag);
		int ticket = nums.length;
		int ticketCount = getTicketCount("0090617");
		if (ticket > ticketCount) {
			throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
		}
		int temp = 0;
		for (String num : nums) {
			NumberLengthExamine.defaultNumberLengthExamine(num, ",", -1L, 1L);
			NumberLengthExamine.defaultNumberLengthExamine(num, ",", 19L, -1L);
			if ((num.equals("0")) || (num.equals("1")) || (num.equals("17")) || (num.equals("18")))
				temp++;
			else if ((num.equals("2")) || (num.equals("3")) || (num.equals("15")) || (num.equals("16")))
				temp += 2;
			else if ((num.equals("4")) || (num.equals("5")) || (num.equals("13")) || (num.equals("14")))
				temp += 3;
			else if ((num.equals("6")) || (num.equals("7")) || (num.equals("11")) || (num.equals("12")))
				temp += 4;
			else if ((num.equals("8")) || (num.equals("9")) || (num.equals("10"))) {
				temp += 5;
			}
		}

		if (temp != item)
			throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
	}
}