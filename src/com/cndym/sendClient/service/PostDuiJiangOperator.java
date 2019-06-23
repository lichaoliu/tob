package com.cndym.sendClient.service;

import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.service.IBonusLogService;
import com.cndym.service.ITicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：邓玉明 时间：11-6-3 下午3:56 QQ：757579248 email：cndym@163.com
 */
@Component
public class PostDuiJiangOperator extends BasePostOperator {
	@Resource
	private IBonusLogService bonusLogService;
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void execute(XmlBean xmlBean) {
		if (null == xmlBean)
			return;
		logger.info("兑奖结果通知处理:" + xmlBean.toString());
		List<XmlBean> ticketBean = xmlBean.getAll("ticket");
		if (Utils.isNotEmpty(ticketBean)) {
			for (XmlBean bean : ticketBean) {
				String ticketId = Utils.formatStr(bean.attribute("ticketId"));
				if (Utils.isEmpty(ticketId)) {
					continue;
				}

				Integer duiJiangStatus = Utils.formatInt(bean.attribute("duiJiangStatus"), Constants.DUI_JIANG_STATUS_NO);
				String errorCode = Utils.formatStr(bean.attribute("errorCode"));
				String errorMsg = Utils.formatStr(bean.attribute("errorMsg"));

				BonusLog bonusLog = new BonusLog();
				bonusLog.setTicketId(ticketId);
				bonusLog.setDuiJiangStatus(duiJiangStatus);
				bonusLog.setErrorCode(errorCode);
				bonusLog.setErrorMsg(errorMsg);
				bonusLogService.updateBonusLogForDuiJiang(bonusLog);
			}
		}
	}
}
