package com.cndym.sms.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.cndym.sms.ISmsClient;
import com.cndym.utils.ConfigUtils;

public class SmsClientByCTY implements ISmsClient {
	
	private static String url = ConfigUtils.getValue("SMS_API");
	private static String userId = ConfigUtils.getValue("SMS_API_SP_ID");
	private static String pwd = ConfigUtils.getValue("SMS_API_SP_PASS");
	private static Logger logger = Logger.getLogger(SmsClientByCTY.class);

	@Override
	public void sendSMS(String mobiles, String msg) {
		String[] mobileStr = mobiles.split(",");
		for (String mobile : mobileStr) {
			String reStr = "failure";
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(url);

			client.getParams().setContentCharset("GB2312");
			method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GB2312");

			NameValuePair[] data = {// 提交短信
			new NameValuePair("un", userId), new NameValuePair("pwd", pwd), new NameValuePair("mobile", mobile), new NameValuePair("msg", msg), };

			method.setRequestBody(data);

			try {
				client.executeMethod(method);
				reStr = method.getResponseBodyAsString();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();

			}
			logger.info("sendSmsByCTY result = " + reStr + ",mobile=" + mobile);
		}
	}

}
