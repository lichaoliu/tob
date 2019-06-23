package com.cndym.sendClient;

/**
 * 作者：邓玉明
 * 时间：11-5-30 下午1:38
 * QQ：757579248
 * email：cndym@163.com
 */

import com.cndym.utils.SpringUtils;

public class ToSendNumberCode {
	public static String toNewNumberCode(String numberCode, String lotteryCode, String playCode, String pollCode) {
		IChange change = (IChange) SpringUtils.getBean(new StringBuffer("to").append(lotteryCode).append(playCode).append(pollCode).toString());
		return change.toSendNumberCode(numberCode);
	}

	public static String toGdNumberCode(String numberCode, String lotteryCode, String playCode, String pollCode) {
		IChange change = (IChange) SpringUtils.getBean(new StringBuffer("toGd").append(lotteryCode).append(playCode).append(pollCode).toString());
		return change.toSendNumberCode(numberCode);
	}
}
