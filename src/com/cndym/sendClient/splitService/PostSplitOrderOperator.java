package com.cndym.sendClient.splitService;

import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.sendClient.service.BasePostOperator;
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
public class PostSplitOrderOperator extends BasePostOperator {
    @Resource
    private ITicketService ticketService;
    private Logger logger = Logger.getLogger(getClass());

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
            String dbTicketId = Utils.formatStr(bean.attribute("dbTicketId"));
            Integer status = Utils.formatInt(bean.attribute("status"), Constants.TICKET_STATUS_SENDING);
            String lotteryCode = Utils.formatStr(bean.attribute("lotteryCode"));
            String issue = Utils.formatStr(bean.attribute("issue"));
            String saleInfo = Utils.formatStr(bean.attribute("saleInfo"));
            String dir = Utils.formatStr(bean.attribute("dir"));

            if (Utils.isEmpty(ticketId)) {
                if (status == Constants.TICKET_STATUS_FAILURE) {
                    TicketSplitUtils.updateSendEndTicket(dir, lotteryCode, issue, dbTicketId);
                    TicketSplitUtils.updateSendFailureTicket(dir, lotteryCode, issue, dbTicketId);
                }
                if (status == Constants.TICKET_STATUS_SENDING) {
                    TicketSplitUtils.updateSendEndTicket(dir, lotteryCode, issue, dbTicketId);
                }
            } else {
                if (status == Constants.TICKET_STATUS_FAILURE) {
                    TicketSplitUtils.ticketFailure(dir, lotteryCode, issue, dbTicketId, ticketId);
                }
                if (status == Constants.TICKET_STATUS_SUCCESS) {
                    TicketSplitUtils.ticketSuccess(dir, lotteryCode, issue, dbTicketId, ticketId);
                }
                if (status == Constants.TICKET_STATUS_SENDING) {
                    continue;
                }
            }

            int sumTicket = TicketSplitUtils.allTicketNum(dir, lotteryCode, issue, dbTicketId);
            int successTicket = TicketSplitUtils.successTicketNum(dir, lotteryCode, issue, dbTicketId);
            int failureTicket = TicketSplitUtils.failureTicketNum(dir, lotteryCode, issue, dbTicketId);

            Ticket ticket = new Ticket();
            ticket.setTicketId(dbTicketId);
            ticket.setSaleInfo(saleInfo);
            //全部成功
            if (successTicket == sumTicket) {
                status = Constants.TICKET_STATUS_SUCCESS;
            } else if (failureTicket == sumTicket) {
                status = Constants.TICKET_STATUS_FAILURE;
            } else if (failureTicket + successTicket == sumTicket) {
                //status = Constants.TICKET_STATUS_SOME;
                status = Constants.TICKET_STATUS_FAILURE;
                ticket.setErrCode(Constants.TICKET_STATUS_SOME + "");
                ticket.setErrMsg(successTicket + "_" + failureTicket);
            } else {
                continue;
            }
            ticket.setTicketStatus(status);
            ticketService.doUpdateTicketForSended(ticket);
        }
    }

    public ITicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(ITicketService ticketService) {
        this.ticketService = ticketService;
    }
}
