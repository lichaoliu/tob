package com.cndym.sendClient.service;

import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.sendClient.splitService.TicketSplitUtils;
import com.cndym.service.ISubTicketService;
import com.cndym.service.ITicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: mcs Date: 12-12-4 Time: 下午4:13
 */

@Component
public class PostSubOrderOperator extends BasePostOperator {

	@Resource
	private ITicketService ticketService;
	@Resource
	private ISubTicketService subTicketService;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void execute(XmlBean xmlBean) {
		if (null == xmlBean)
			return;
		logger.info("出票结果通知处理:" + xmlBean.toString());
		List<XmlBean> ticketBean = xmlBean.getAll("ticket");
		if (!Utils.isNotEmpty(ticketBean)) {
			return;
		}
		for (XmlBean bean : ticketBean) {
			String subTicketId = Utils.formatStr(bean.attribute("subTicketId"));
			Integer status = Utils.formatInt(bean.attribute("status"), Constants.TICKET_STATUS_SENDING);
			String saleInfo = Utils.formatStr(bean.attribute("saleInfo"));
			String saleCode = Utils.formatStr(bean.attribute("saleCode"));

			SubTicket subTicket = new SubTicket();
			subTicket.setSubTicketId(subTicketId);
			subTicket.setSaleInfo(saleInfo);
			subTicket.setTicketStatus(status);
			subTicket.setSaleCode(saleCode);
			subTicketService.doUpdateSubTicketForSended(subTicket);
		}
	}
}
