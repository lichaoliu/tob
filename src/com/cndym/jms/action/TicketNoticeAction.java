package com.cndym.jms.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.service.IMemberService;
import com.cndym.service.ITicketService;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.Utils;

@Component
public class TicketNoticeAction implements IDataOutAction {
    private Logger logger = Logger.getLogger(getClass());
    
    @Resource
    private ITicketService ticketService;
    @Resource
	private IMemberService memberService;

    @Override
    public void execute(ActiveMQMapMessage mapMessage) {
       // logger.info("收到票外发数据");
        try {
           // logger.info("票数据:" + mapMessage.getMapNames());
            final String ticketId = (String) mapMessage.getObject("ticketId");
            logger.info("出票通知JMS消费者收到的票号:" + ticketId);
            Thread.sleep(2000);
            final Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            final Member member = memberService.getMemberBySid(ticket.getSid());
            int ticketStatus = ticket.getTicketStatus();
            if (ticketStatus >=3 && ticketStatus <=5 && Utils.isNotEmpty(member.getBackup3())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendNotice(member, ticket);
                    }
                }).start();
            }
        } catch (Exception e) {
            logger.error("出票通知JMS消费者Action错误:", e);
        }
    }

    private void sendNotice(Member member, Ticket ticket) {
    	String xml = getReturnXml(ticket);
        logger.info("url="+member.getBackup3()+","+"xml="+xml);
        Map<String, String> map = new HashMap<String, String>();
        map.put("msg", xml);
		HttpClientUtils httpClientUtils = new HttpClientUtils(member.getBackup3(), "utf-8", map);
		httpClientUtils.setOutTime(20000);
		httpClientUtils.setReqTime(20000);
		for (int i=0; i<3; i++) {
			map = httpClientUtils.httpClientRequestD();
			String status = map.get("status");
			if ("200".equals(status)) {
				i = 3;
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
			}
		}
    }

	private String getReturnXml(Ticket ticket) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		xml.append("<message>");
		xml.append("<header><cmd>101</cmd></header>");
		xml.append("<body><tickets>");
		if (ticket != null) {
			xml.append("<ticket ");
			xml.append("ticketId=\"" + ticket.getOutTicketId() + "\" ");
			xml.append("sysId=\"" + ticket.getTicketId() + "\" ");
			String saleCode = ticket.getSaleCode() == null ? "" : ticket.getSaleCode();
			xml.append("saleCode=\"" + saleCode + "\" ");
			xml.append("orderId=\"" + ticket.getOrderId() + "\" ");
			xml.append("lotteryId=\"" + ticket.getLotteryCode() + "\" ");
			xml.append("issue=\"" + ticket.getIssue() + "\" ");
			xml.append("amount=\"" + Utils.formatNumberZD(ticket.getAmount()) + "\" ");

			int ticketStatus = ticket.getTicketStatus();
			if (ticketStatus == 3) {
				xml.append("status=\"2\" ");// 交易成功
				xml.append("msg=\"交易成功\" ");//
			} else if (ticketStatus == 4 || ticketStatus == 5) {
				xml.append("status=\"3\" ");// 交易失败
				xml.append("msg=\"交易失败\" ");//
			}

			if (ticket.getReturnTime() != null) {
				xml.append("printTime=\"" + Utils.formatDate2Str(ticket.getReturnTime(), Constants.DATE_FORMAT) + "\" ");
			} else {
				xml.append("printTime=\"" + "" + "\" ");
			}
			xml.append(">");
			
			String saleInfo = "";
			if (ticketStatus == 3) {
				saleInfo = ticket.getSaleInfo() == null ? "" : ticket.getSaleInfo();
			}
			xml.append("<odds><![CDATA[" + saleInfo + "]]></odds>");

			xml.append("</ticket>");
		}
		xml.append("</tickets>");
		xml.append("</body>");
		xml.append("</message>");
		return xml.toString();
	}
}
