package com.cndym.sendClient.autotest.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-5-30 下午1:15 QQ：757579248 email：cndym@163.com
 */
public class ToSendCode {

	private static Map<String, String> oddsLotteryMap = new HashMap<String, String>();

	static {
		forInstance();
	}

	public static void forInstance() {

		oddsLotteryMap.put("20001", "RQS");
		oddsLotteryMap.put("20002", "JQS");
		oddsLotteryMap.put("20003", "BQC");
		oddsLotteryMap.put("20004", "CBF");
		oddsLotteryMap.put("20005", "SPF");
		oddsLotteryMap.put("20010", "ZHT");

		oddsLotteryMap.put("20101", "SFC");
		oddsLotteryMap.put("20102", "RSF");
		oddsLotteryMap.put("20103", "SFD");
		oddsLotteryMap.put("20104", "DXF");
		oddsLotteryMap.put("20110", "LHT");

		oddsLotteryMap.put("40001", "SPF");
		oddsLotteryMap.put("40002", "SXP");
		oddsLotteryMap.put("40003", "JQS");
		oddsLotteryMap.put("40004", "BQC");
		oddsLotteryMap.put("40005", "CBF");
	}

	public static String getOddsLottery(String lotteryCode, String playCode) {
		String code = lotteryCode + playCode;
		for (Map.Entry<String, String> entry : oddsLotteryMap.entrySet()) {
			if (code.equals(entry.getKey())) {
				return entry.getValue();
			}
		}
		throw new IllegalArgumentException("(" + code + ")没有找到对应的编码");
	}

}
