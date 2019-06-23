package com.cndym.sms;

public interface ISmsClient {
	
	/**
	 * 
	 * @param mobiles
	 * @param msg
	 */
	public void sendSMS(String mobiles, String msg) ;
}
