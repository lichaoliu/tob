package com.cndym;

import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.service.IMemberService;
import com.cndym.serviceMap.ServiceBean;
import com.cndym.serviceMap.ServiceList;
import com.cndym.utils.*;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import com.cndym.xmlObject.Header;
import org.apache.log4j.Logger;

/**
 * 作者：邓玉明 时间：10-12-20 上午9:21 QQ：757579248 email：cndym@163.com
 */
public class GlobalAdapter {

	private Logger logger = Logger.getLogger(getClass());

	private IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");

	public XmlBean execute(String msg) {
		if (null == msg) {
			throw new CndymException(ErrCode.E0003);
		}
		XmlBean xmlBean = toXmlBean(msg, Utils.getClassPath() + ConfigUtils.getValue(Constants.DEFAULT_XSD));// Schema校验header
		XmlBean header = xmlBean.getFirst("header");
		String sid = header.getFirst("sid").attribute("text").trim();
		String digestType = header.getFirst("digestType").attribute("text").trim();
		String cmd = header.getFirst("cmd").attribute("text").trim();
		ServiceBean serviceBean = ServiceList.getServiceBean(cmd.trim());
		try {
			logger.info("cmd:" + serviceBean.getCmd() + ",sid:" + sid);
			String xsd = Utils.getClassPath() + ConfigUtils.getValue(Constants.XSD_PATH) + serviceBean.getXsd();
			try {
				SchemaUtils.checkBody(msg, xsd);
			} catch (Exception e) {
				logger.error("cmd:" + serviceBean.getCmd() + ",sid:" + sid + " ErrCode:0003,msg:" + ErrCode.codeToMsg(ErrCode.E0003));
				return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E0003);
			}
			XmlBean bodyBean = xmlBean.getFirst("body");
			Member member = null;
			if (Constants.MD5_DIGEST_TYPE.equals(digestType.toLowerCase())) {
				member = memberService.getMemberBySid(sid);
				if (null == member) {
					logger.error("cmd:" + serviceBean.getCmd() + ",sid:" + sid + " ErrCode:8002,msg:" + ErrCode.codeToMsg(ErrCode.E8002));
					return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E8002);
				}
				if (member.getStatus().intValue() != Constants.MEMBER_STATUS_LIVE) {
					logger.error("cmd:" + serviceBean.getCmd() + ",sid:" + sid + " ErrCode:8003,msg:" + ErrCode.codeToMsg(ErrCode.E8003));
					return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E8003);
				}
				boolean result = Utils.encryptMsg(msg, member.getPrivateKey());
				logger.error("MD5校验结果:" + result);
				if (!result) {
					logger.error("cmd:" + serviceBean.getCmd() + ",sid:" + sid + " ErrCode:0001,msg:" + ErrCode.codeToMsg(ErrCode.E0001));
					return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E0001);
				}
			}
			XmlBean sidBean = ByteCodeUtil.xmlToObject("<sid>" + sid + "</sid>");
			bodyBean.add("sid", sidBean);
			IAdapter adapter = (IAdapter) SpringUtils.getBean(serviceBean.getAdapter());// 取出对应的service
			XmlBean bodyReturn = adapter.execute(bodyBean);// 调用业务逻辑
			if (bodyReturn == null) {
				logger.error("cmd:" + serviceBean.getCmd() + ",sid:" + sid + " ErrCode:0999,msg:" + ErrCode.codeToMsg(ErrCode.E0001));
				return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E0999);
			} else {
				Header reHeader = new Header(sid, serviceBean.getResponse(), digestType);
				return returnXml(reHeader, bodyReturn);
			}
		} catch (CndymException e) {
			e.printStackTrace();
			logger.error("cmd:" + serviceBean.getCmd() + ",sid:" + sid + " ErrCode:0999,msg:" + ErrCode.codeToMsg(ErrCode.E0001));
			return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E0999);
		} catch (Exception e) {
			e.printStackTrace();
			return returnErrorXml(new Header(sid, serviceBean.getResponse(), digestType), ErrCode.E0999);
		}
	}

	public XmlBean toXmlBean(String msg, String path) {
		SchemaUtils.check(msg, path);
		return ByteCodeUtil.xmlToObject(msg);
	}

	public XmlBean returnXml(Header header, XmlBean body) {
		logger.info("select sid="+header.getSid()+",start");
		Member member = memberService.getMemberBySid(header.getSid());
		logger.info("select sid="+header.getSid()+",end");
		if (null == member) {
			throw new IllegalArgumentException("SID:" + header.getSid() + "找不到对应的记录");
		}
		String digest = Md5.Md5(header.getSid() + member.getPrivateKey() + body.toString());
		header.setDigest(digest);
		String xml = "<message></message>";
		XmlBean bean = ByteCodeUtil.xmlToObject(xml);
		bean.add(ByteCodeUtil.xmlToObject(header.toXml()));
		bean.add(body);
		return bean;
	}

	public XmlBean returnErrorXml(Header header, String errorCode) {
		logger.error("select sid="+header.getSid()+",start");
		Member member = memberService.getMemberBySid(header.getSid());
		logger.error("select sid="+header.getSid()+",end");
		if (null == member) {
			throw new IllegalArgumentException("SID:" + header.getSid() + "找不到对应的记录");
		}
		String body = "<body><response code=\"" + errorCode + "\" msg=\"" + ErrCode.codeToMsg(errorCode) + "\"></response></body>";
		String digest = Md5.Md5(header.getSid() + member.getPrivateKey() + body.toString());
		header.setDigest(digest);
		StringBuffer xml = new StringBuffer("<message>");
		xml.append(header.toXml() + body + "</message>");
		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
