package com.cndym.adapter.tms.examine.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.Utils;

@Component
public class Ex0090102 extends BashExamina implements IExamine {

	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(ztag);
		NumberTicketCountExamine.defaulstNumberSortExamine("0090102", nums);

		int ticket = 0;
		for (String num : nums) {
			Map<String, String> repeatMap = new HashMap<String, String>();
			String[] strArray = Utils.strToStrArray(num);
			NumberAreaExamine.defaultNumberAraeExamine(strArray, 0, 9, 1);// 号码域验证
			NumberSortExamine.defaulstNumberSortExamine(num);// 验证号码是否按升序排序

			for (String key : strArray) {
				if (repeatMap.containsKey(key)) {
					throw new IllegalArgumentException("号码(" + num + ")有重复");
				} else {
					repeatMap.put(key, key);
				}
			}

			int a = Utils.strToStrArray(num).length;
			if (a <= 1 || a > 10) {
				throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
			}
			ticket += a;
		}
		if (ticket != item) {
			throw new CndymException(ErrCode.E8116);
		}
	}

	public static void main(String[] args) {
		Ex0090102 ex = new Ex0090102();
		ex.examina("1234567890", 10);
		System.out.println("ok");
	}
}
