package com.cndym.adapter.tms.examine.impl;

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

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-4-30 下午1:36 QQ：757579248 email：cndym@163.com
 */
@Component
public class Ex1061002 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex1061002.class);

	@Override
	public void examina(String number, int item) {
		String[] nums = number.split(ztag);
		int temp = 0;
		NumberTicketCountExamine.defaulstNumberSortExamine("1061002", nums);
		for (String n : nums) {
			// 校验格式
			NumberLengthExamine.defaultNumberLengthExamine(n, "#", 2, 0);// 号码个数的验证
			String[] num = n.split("#");
			String ge = num[0];
			String shi = num[1];
			String[] ges = ge.split(",");
			String[] shis = shi.split(",");
			if (ges.length + shis.length <= 2) {
				throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
			}
			NumberLengthExamine.defaultNumberLengthExamine(ge, ",", 1, 2);// 号码个数的验证
			NumberLengthExamine.defaultNumberLengthExamine(shi, ",", 1, 2);// 号码个数的验证
			NumberAreaExamine.defaultNumberAraeExamine(ges, 1, 11, 2);// 号码域验证
			NumberAreaExamine.defaultNumberAraeExamine(shis, 1, 11, 2);// 号码域验证

			NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(ge).append(",").append(shi).toString(), ",", 0);

			// 排重的注数算法
			// temp = itemCount(ges, shis);
			temp += ges.length * shis.length;
		}
		if (temp != item) {
			logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
			throw new CndymException(ErrCode.E8116);
		}

	}

	private static int itemCount(String[] ges, String[] shis) {
		int item = 0;
		int size = 0;
		int geLen = ges.length;
		int shiLen = shis.length;
		if (geLen > shiLen) {
			for (String n : ges) {
				for (String n0 : shis) {
					if (n.equals(n0)) {
						size++;
					}
				}
			}
		} else {
			for (String n : shis) {
				for (String n0 : ges) {
					if (n.equals(n0)) {
						size++;
					}
				}
			}
		}
		int i = geLen * shiLen;
		int repeatsNum = 0;
		if (i != item) {
			repeatsNum = size;
		}
		item = i - repeatsNum;
		return item;
	}

	public static void main(String[] args) {
		Ex1061002 ex = new Ex1061002();
		ex.examina("01#11,09", 2);
		System.out.println("ok");
	}
}
