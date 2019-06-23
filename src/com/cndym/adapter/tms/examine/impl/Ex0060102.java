package com.cndym.adapter.tms.examine.impl;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.utils.Utils;

/**
 * *USER:MengJingyi *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060102 extends BashExamina implements IExamine {

	@Override
	public void examina(String number, int item) {
		String tag = "";
		String ztag = ";";
		String[] nums = number.split(ztag);
		int ticket = getTicketCount("0060102");
		if (nums.length > ticket) {
			throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
		}
		int temp = 0;
		for (String num : nums) {
			// NumberLengthExamine.defaultNumberLengthExamine(num, tag, 1, 1);//号码个数的验证
			// NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);//验证是否有重复
			NumberAreaExamine.defaultNumberAraeExamine(Utils.strToStrArray(num), 0, 9, 1);// 号码域验证
			int a = Utils.strToStrArray(num).length;
			if (a <= 1 && a > 10) {
				throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
			}
			temp += C(a, 1);
		}
		if (temp != item) {
			throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
		}
	}

	public static void main(String[] args) {
		Ex0060102 ex0060102 = new Ex0060102();
		ex0060102.examina("19", 2);
		System.out.println("ok");
	}

}
