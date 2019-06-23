package com.cndym.sendClient.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.SubTicket;
import com.cndym.constant.Constants;
import com.cndym.sendClient.ISendClientOperator;
import com.cndym.service.ISubTicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 作者：邓玉明 时间：11-6-3 下午3:57 QQ：757579248 email：cndym@163.com
 */
@Component
public class PostBonusExOperator extends BasePostOperator {
	@Resource
	private ISubTicketService subTicketService;
	@Resource
	private ISendClientOperator postBonusOperator;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void execute(XmlBean xmlBean) {
		if (null == xmlBean)
			return;
		String lotteryCode = Utils.formatStr(xmlBean.attribute("lotteryCode"));
		String issue = Utils.formatStr(xmlBean.attribute("issue"));
		String postCode = Utils.formatStr(xmlBean.attribute("postCode"));
		List<XmlBean> ticketBean = xmlBean.getAll("ticket");
		logger.info("进入子票 外部返奖入库处理(lotteryCode:" + lotteryCode + ",issue:" + issue + ")");

		if (null == ticketBean) {
			ticketBean = new ArrayList<XmlBean>();
		}

		Map<String, Double> bonusAmountMap = new HashMap<String, Double>();
		List<SubTicket> subList = new ArrayList<SubTicket>();
		for (XmlBean bean : ticketBean) {
			String subTicketId = Utils.formatStr(bean.attribute("ticketId"));
			SubTicket subTicket = subTicketService.findSubTicketBySubTicketId(subTicketId);
			if (subTicket == null) {
				continue;
			}

			Double bonusAmount = Utils.formatDouble(bean.attribute("bonusAmount"), 0d);
			Double fixBonusAmount = Utils.formatDouble(bean.attribute("fixBonusAmount"), 0d);
			int bigBonus = Utils.formatInt(bean.attribute("bigBonus"), 0);

			subTicket.setBonusAmount(bonusAmount);
			subTicket.setFixBonusAmount(fixBonusAmount);
			subTicket.setBigBonus(bigBonus);
			subTicket.setBonusStatus(Constants.BONUS_STATUS_YES);
			subTicket.setBonusTime(new Date());

			subList.add(subTicket);

			String ticketId = subTicket.getTicketId();
			double oba = bonusAmountMap.get(ticketId) == null ? 0 : bonusAmountMap.get(ticketId);
			bonusAmountMap.put(ticketId, oba + bonusAmount);
		}

		boolean flag = subTicketService.saveAllObject(subList);
		if (flag) {
			subTicketService.doNoBonus(lotteryCode, issue, postCode);
		}

		StringBuffer reXml = new StringBuffer("<bonus lotteryCode=\"").append(lotteryCode).append("\" issue=\"").append(issue).append("\" postCode=\"").append(postCode).append("\">");
		for (String ticketId : bonusAmountMap.keySet()) {
			double bonusAmount = bonusAmountMap.get(ticketId);
			reXml.append("<ticket ticketId=\"").append(ticketId).append("\" bonusAmount=\"").append(bonusAmount).append("\" fixBonusAmount=\"").append(bonusAmount).append("\" bonusClass=\"")
					.append("").append("\"/>");
		}
		reXml.append("</bonus>");

		logger.info("sub ticket bonus request:" + reXml.toString());
		postBonusOperator.execute(ByteCodeUtil.xmlToObject(reXml.toString()));
	}
}
