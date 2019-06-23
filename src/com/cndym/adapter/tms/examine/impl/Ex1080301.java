package com.cndym.adapter.tms.examine.impl;

import java.util.HashMap;
import java.util.Map;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 作者：邓玉明 时间：11-4-30 下午1:36 QQ：757579248 email：cndym@163.com
 */
@Component
public class Ex1080301 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex1080301.class);

	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(ztag);
		NumberTicketCountExamine.defaulstNumberSortExamine("1080201", nums);
		for (String num : nums) {
			// 校验格式
			NumberTagExamine.commaNumberTagExamine(num);// 号码中分隔符的验证
			NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);// 号码个数的验证
			NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);// 号码域验证
			Map<String, String> map = new HashMap<String, String>();
			String[] arr = num.split(",");
			for (String s : arr) {
				map.put(s, s);
			}
			if (map.size() < 2) {
				throw new IllegalArgumentException("三个号码不能相同");
			}
			if (map.size() == 3) {
				throw new IllegalArgumentException("必须有两个号码相同");
			}

		}
		// 校验注数
		if (item != nums.length) {
			logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
			throw new CndymException(ErrCode.E8116);
		}
	}

	public static void main(String[] args) {
		Ex1080301 ex = new Ex1080301();
		ex.examina("1,2,1;1,1,3;1,2,2;1,3,3;1,2,2", 5);
		System.out.println("ok");
	}
}
