package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import org.springframework.stereotype.Component;

@Component
public class Ex0090304 extends BashExamina implements IExamine {
	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(this.ztag);
		NumberTicketCountExamine.defaulstNumberSortExamine("0090304", nums);
		NumberRepeatExamine.defaultNumberRepeatExamine(number, this.ztag, 0);

		int ticket = 0;
		for (String num : nums) {
			NumberAreaExamine.defaultNumberAraeExamine(num, 0L, 27L);
			String ssc3xzxhz = (String)BumberNoteTheNumberOfExamine.getSsc3xzxhz().get(num);
			ticket += new Integer(ssc3xzxhz).intValue();
		}

		if (item != ticket)
			throw new CndymException("8116");
	}

	public static void main(String[] args) {
		Ex0090304 ex0060304 = new Ex0090304();
		ex0060304.examina("27;1", 4);
    	System.out.println("ok");
	}
}