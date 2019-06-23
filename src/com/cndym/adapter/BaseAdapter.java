package com.cndym.adapter;

import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.exception.ErrCode;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 作者：邓玉明 时间：10-12-26 下午7:32 QQ：757579248 email：cndym@163.com
 */
public class BaseAdapter {

	protected XmlBean returnSubTagBody(String response) {
		return returnSubTagBody(ErrCode.E0000, response);
	}

	protected XmlBean returnSubTagBody(XmlBean response) {
		return returnSubTagBody(ErrCode.E0000, response.toXml());
	}

	protected XmlBean returnDefaultBody() {
		return returnErrorCodeBody(ErrCode.E0000);
	}

	protected XmlBean returnErrorCodeBody(String errCode) {
		String xml = "<body><response code=\"" + errCode + "\" msg=\"" + ErrCode.codeToMsg(errCode) + "\"/></body>";
		return ByteCodeUtil.xmlToObject(xml);
	}

	protected XmlBean returnSubTagBody(String errCode, String response) {
		String xml = "<body><response code=\"" + errCode + "\" msg=\"" + ErrCode.codeToMsg(errCode) + "\">" + response + "</response></body>";
		return ByteCodeUtil.xmlToObject(xml);
	}

	/**
	 * 检验彩种是否停售   1是停售
	 * 
	 * @param lotteryCode
	 * @return
	 */
	public static int lotteryControl(String lotteryCode) {
		StringBuffer key = new StringBuffer("lottery.").append(lotteryCode).append(".control");
		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
		String value = memcachedClientWrapper.get(key.toString());
		if (Utils.isNotEmpty(value)) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 检验彩种是否能在该代理商可售
	 * 
	 * @param lotteryCode
	 * @return
	 */
	public static int lotteryControl(String bakup1,String lotteryCode) {
		if (Utils.isNotEmpty(bakup1) && bakup1.contains(lotteryCode)) {
			return 1;
		}
		return 0;
	}

}
