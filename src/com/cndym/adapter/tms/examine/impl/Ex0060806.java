package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

/**
 * *USER:MengJingyi *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060806 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex0060804.class);

	@Override
	public void examina(String number, int item) {
		logger.info("该玩法暂不支持");
		throw new CndymException(ErrCode.E8117);

//		String ztag = ";";
//		String[] nums = number.split(ztag);
//		int ticket = getTicketCount("0060806");
//		if (nums.length > ticket) {
//			throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
//		}
//		int temp = 0;
//		for (String num : nums) {
//			NumberTagExamine.commaNumberTagExamine(num);// 号码中分隔符的验证
//			NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 1);// 号码个数的验证
//			NumberLengthExamine.defaultNumberLengthExamine(num, ",", 9, -1);// 号码个数的验证
//			NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);// 号码域验证
//			String ssc3xz6bh = BumberNoteTheNumberOfExamine.getSsc3xz6bh().get(num.split(",").length + "");
//			temp += new Integer(ssc3xz6bh);
//		}
//		// 和值计算注数
//		if (item != temp) {
//			throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
//		}
	}

	public static void main(String[] args) {
		Ex0060806 ex0060806 = new Ex0060806();
		ex0060806.examina("1,2,3,4;1,2,3,4,5,6,7,8", 60);
		System.out.println("ok");
	}
}
