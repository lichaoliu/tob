package com.cndym.sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.cndym.sms.impl.SmsClientByCTY;
import com.cndym.sms.impl.SmsClientByMD;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.TocDateUtils;
import com.cndym.utils.Utils;

public class SmsClient {
	private static Logger logger = Logger.getLogger(SmsClient.class);
	private static String smsFlag = ConfigUtils.getValue("SMS_FLAG");
	private static ISmsClient clientMD = new SmsClientByMD();
	private static ISmsClient clientCTY = new SmsClientByCTY();
	
	public static void sendSMS(String mobiles, String msg) {
		logger.info("SmsClient:sendSMS,mobiles=" + mobiles + ",msg=" + msg);
		
		if(Utils.isNotEmpty(mobiles) && Utils.isNotEmpty(msg)){
			if (check(mobiles)) {
				if ("2".equals(smsFlag)){
					clientMD.sendSMS(mobiles, msg);
				} else {
					clientCTY.sendSMS(mobiles, msg);
				}
			}
		}
	}
	
	/**
	 * 检验号码
	 * @param mobiles
	 * @return
	 */
	private static boolean check(String mobiles) {
		String[] arrMobiles = mobiles.split(",");
		Pattern p = Pattern.compile("\\d{11}");
		for (String mobile : arrMobiles) {
			Matcher m = p.matcher(mobile);
			if (!m.matches()) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		sendSMS("15397171965", "test?(测试用)[12；：]");
	}
}
