package com.cndym.utils;

import java.util.HashMap;
import java.util.Map;

public class TocDateUtils {
	
	public static String getSendSmsNumber(){
		String url = "http://10.1.7.16:8080/innDateServlet";
		Map<String, String> param = new HashMap<String, String>();
		param.put("action", "getPhoneForSendSms");
        String encoding = "utf-8";
		HttpClientUtils hcu = new HttpClientUtils(url, encoding, param);
		return hcu.httpClientRequest();
	}
	
	public static void main(String[] args) {
		System.out.println(getSendSmsNumber());
	}

}
