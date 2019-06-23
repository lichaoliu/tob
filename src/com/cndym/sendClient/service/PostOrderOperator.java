package com.cndym.sendClient.service;

import com.cndym.bean.tms.Ticket;
import com.cndym.jms.producer.SendToClientTicketNoticeMessageProducer;
import com.cndym.service.ITicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：邓玉明
 * 时间：11-6-3 下午3:56
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class PostOrderOperator extends BasePostOperator {
    private Logger logger = Logger.getLogger(getClass());
    
	@Resource
    private ITicketService ticketService;

	@Resource
    private SendToClientTicketNoticeMessageProducer sendToClientTicketNoticeMessageProducer;

    @Override
    public void execute(XmlBean xmlBean) {
        if (null == xmlBean) return;
        logger.info("出票结果通知处理:" + xmlBean.toString());
        List<XmlBean> ticketBean = xmlBean.getAll("ticket");
        if (!Utils.isNotEmpty(ticketBean)) {
            return;
        }
        for (XmlBean bean : ticketBean) {
            String ticketId = Utils.formatStr(bean.attribute("ticketId"));
            Integer status = Utils.formatInt(bean.attribute("status"), 0);
            String errCode = Utils.formatStr(bean.attribute("errCode"));
            String errMsg = Utils.formatStr(bean.attribute("errMsg"));
            String saleCode = Utils.formatStr(bean.attribute("saleCode"));
            String saleInfo = Utils.formatStr(bean.attribute("saleInfo"));
            if (Utils.isEmpty(ticketId)) {//如果出票失败，也不能为空！！！
                continue;
            }
            Ticket ticket = new Ticket();
            ticket.setTicketId(ticketId);
            ticket.setTicketStatus(status);
            ticket.setErrCode(errCode);
            ticket.setErrMsg(errMsg);
            ticket.setSaleCode(saleCode);
            ticket.setSaleInfo(saleInfo);
            ticketService.doUpdateTicketForSended(ticket);

            try {
            	sendToClientTicketNoticeMessageProducer.sendMessage(ticket.getOrderId(), ticket.getTicketId());
            } catch (Exception e) {
            	logger.error("", e);
            }
        }
    }

    public ITicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(ITicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    public SendToClientTicketNoticeMessageProducer getSendToClientTicketNoticeMessageProducer() {
		return sendToClientTicketNoticeMessageProducer;
	}

	public void setSendToClientTicketNoticeMessageProducer(
			SendToClientTicketNoticeMessageProducer sendToClientTicketNoticeMessageProducer) {
		this.sendToClientTicketNoticeMessageProducer = sendToClientTicketNoticeMessageProducer;
	}

}
