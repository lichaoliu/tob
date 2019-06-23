package com.cndym.sendClient.service;

import com.cndym.bean.tms.BonusLog;
import com.cndym.jms.action.DataOutActionItem;
import com.cndym.jms.producer.GateWayDataOutMessageProducer;
import com.cndym.service.IBonusLogService;
import com.cndym.service.IPostBonusService;
import com.cndym.service.ITicketService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import com.renren.api.client.utils.ConfigUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.jsp.jstl.core.Config;
import java.util.*;

/**
 * 作者：邓玉明 时间：11-6-3 下午3:57 QQ：757579248 email：cndym@163.com
 */
@Component
public class PostBonusOperator extends BasePostOperator {
	@Resource
	private ITicketService ticketService;
	@Resource
	private GateWayDataOutMessageProducer gateWayDataOutMessageProducer;
	@Resource
	private IPostBonusService postBonusService;
	@Resource
	private IBonusLogService bonusLogService;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void execute(XmlBean xmlBean) {
		if (null == xmlBean)
			return;
		String lotteryCode = Utils.formatStr(xmlBean.attribute("lotteryCode"));
		String issue = Utils.formatStr(xmlBean.attribute("issue"));
		String postCode = Utils.formatStr(xmlBean.attribute("postCode"));
		List<XmlBean> ticketBean = xmlBean.getAll("ticket");
		logger.info("进入外部返奖入库处理(lotteryCode:" + lotteryCode + ",issue:" + issue + ")");

		if (null == ticketBean) {
			ticketBean = new ArrayList<XmlBean>();
		}
		List<BonusLog> bonusLogs = new ArrayList<BonusLog>();

		// 处理未回执的票(置为成功)
		// int doResult = ticketService.doUpdateSendingToSuccess(lotteryCode, issue);
		// if (doResult == 0) {
		// logger.info("处理(lotteryCode:" + lotteryCode + ",issue:" + issue + ")发送未回执票失败");
		// return;
		// }

		for (XmlBean bean : ticketBean) {
			String ticketId = Utils.formatStr(bean.attribute("ticketId"));
			BonusLog bonusLog = bonusLogService.getBonusLogByTicketId(ticketId);
			if (bonusLog != null) {
				continue;
			}
			Double bonusAmount = Utils.formatDouble(bean.attribute("bonusAmount"), 0d);
			Double fixBonusAmount = Utils.formatDouble(bean.attribute("fixBonusAmount"), 0d);
			String bonusClass = Utils.formatStr(bean.attribute("bonusClass"));
			int bigBonus = Utils.formatInt(bean.attribute("bigBonus"), 0);
			String saleCode = Utils.formatStr(bean.attribute("saleCode"));
			String saleTime = Utils.formatStr(bean.attribute("saleTime"));
			String machineCode = Utils.formatStr(bean.attribute("machineCode"));

			bonusLog = new BonusLog();
			bonusLog.setTicketId(ticketId);
			bonusLog.setLotteryCode(lotteryCode);
			bonusLog.setIssue(issue);
			bonusLog.setBonusAmount(bonusAmount);
			bonusLog.setBonusClass(bonusClass);
			bonusLog.setFixBonusAmount(fixBonusAmount);
			bonusLog.setBigBonus(bigBonus);
			bonusLog.setCreateTime(new Date());
			bonusLog.setPostCode(postCode);
			bonusLog.setSaleCode(saleCode);
			bonusLog.setSaleTime(saleTime);
			bonusLog.setDuiJiangStatus(0);
			bonusLog.setMachineCode(machineCode);
			bonusLogs.add(bonusLog);
		}
		int result = ticketService.doOutBonus(lotteryCode, issue, bonusLogs);
		if (result == 1) {
			String allowOutBonusLottery = ConfigUtils.getValue("ALLOW_OUT_BONUS_LOTTERY");
			if (allowOutBonusLottery.contains(lotteryCode)) {
				// 处理未中奖方案
				ticketService.doNoBonus(lotteryCode, issue);
				// 通知派奖
				logger.info("进入返奖结束通知(" + lotteryCode + ")");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lotteryCode", lotteryCode);
				map.put("issue", issue);
				gateWayDataOutMessageProducer.sendMessage(DataOutActionItem.DATA_OUT_FOR_BONUS, map);
			} else {
				postBonusService.updateStatusForEndBonus(lotteryCode, issue, postCode);
			}
		}
	}
}
