package com.cndym.sms.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.cndym.sms.ISmsClient;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.Md5;
import com.cndym.utils.Utils;

public class SmsClientByMD implements ISmsClient {
	private static String urlMD = ConfigUtils.getValue("SMS_API_MD");
	private static String userIdMD = ConfigUtils.getValue("SMS_API_SP_ID_MD");
	private static String pwdMD = ConfigUtils.getValue("SMS_API_SP_PASS_MD");
	
	private static Logger logger = Logger.getLogger(SmsClientByMD.class);

	@Override
	public void sendSMS(String mobiles, String msg) {
		msg = msg+Utils.random(4);
		String reStr = "failure";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(urlMD);
		client.getParams().setContentCharset("UTF-8");
		String strPwd = Md5.Md5(userIdMD + pwdMD).toUpperCase();
		NameValuePair[] data = {new NameValuePair("sn", userIdMD), new NameValuePair("pwd", strPwd), new NameValuePair("mobile", mobiles), new NameValuePair("content", msg + "【中彩汇】"), 
				new NameValuePair("ext", ""), new NameValuePair("stime", ""), new NameValuePair("rrid", ""), new NameValuePair("msgfmt", "")};

		method.setRequestBody(data);
		try {
			client.executeMethod(method);
			reStr = method.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
		logger.info("sendSmsByMd result = " + reStr + ",mobile=" + mobiles);
	}

}
