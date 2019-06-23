package com.cndym.adapter.tms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.ITicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 按批次号查询投注订单信息
 * 
 * @author 金坤涛
 * 
 */
@Component
public class QueryTicketByOrderIdAdapter extends BaseAdapter implements IAdapter {
	@Autowired
	private ITicketService ticketService;

	@Override
	public XmlBean execute(XmlBean xmlBean) {
		XmlBean sidBean = xmlBean.getFirst("sid");
		String sid = "";
		if (sidBean != null) {
			sid = sidBean.attribute("text");
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}

		XmlBean queryBean = xmlBean.getFirst("query");
		String orderId = queryBean.attribute("orderId");

		if (Utils.isNotEmpty(orderId)) {
			List<Ticket> ticketList = ticketService.getTicketByOrderId(orderId, sid);
			if (Utils.isNotEmpty(ticketList)) {
				XmlBean bodyBean = getReturnBean(ticketList);
				return bodyBean;
			} else {
				return returnErrorCodeBody(ErrCode.E8201);
			}
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}
	}

	private XmlBean getReturnBean(List<Ticket> ticketList) {
		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		for (Ticket ticket : ticketList) {
			xml.append("<ticket ");
			xml.append("ticketId =\"" + ticket.getOutTicketId() + "\" ");
			xml.append("sysId =\"" + ticket.getTicketId() + "\" ");
			String saleCode = ticket.getSaleCode() == null ? "" : ticket.getSaleCode();
			xml.append("saleCode =\"" + saleCode + "\" ");
			xml.append("orderId =\"" + ticket.getOrderId() + "\" ");
			xml.append("lotteryId=\"" + ticket.getLotteryCode() + "\" ");
			xml.append("issue =\"" + ticket.getIssue() + "\" ");
			xml.append("amount =\"" + Utils.formatNumberZD(ticket.getAmount()) + "\" ");
			int ticketStatus = ticket.getTicketStatus();
			if (ticketStatus == 0 || ticketStatus == 1) {
				xml.append("status =\"0\" ");// 等待交易
			} else if (ticketStatus == 2 || ticketStatus == 6) {
				xml.append("status =\"1\" ");// 交易中
			} else if (ticketStatus == 3) {
				xml.append("status =\"2\" ");// 交易成功
			} else if (ticketStatus == 4 || ticketStatus == 5) {
				xml.append("status =\"3\" ");// 交易失败
			}
			xml.append("bonusStatus =\"" + ticket.getBonusStatus() + "\" ");
			xml.append("bonusAmount =\"" + Utils.formatNumberZD(ticket.getBonusAmount()) + "\" ");
			xml.append("fixBonusAmount =\"" + Utils.formatNumberZD(ticket.getFixBonusAmount()) + "\" ");

			if (ticket.getReturnTime() != null) {
				xml.append("printTime =\"" + Utils.formatDate2Str(ticket.getReturnTime(), Constants.DATE_FORMAT) + "\" ");
			} else {
				xml.append("printTime =\"" + "" + "\" ");
			}
			xml.append(">");

			String saleInfo = "";
			if (ticketStatus == 3) {
				saleInfo = ticket.getSaleInfo() == null ? "" : ticket.getSaleInfo();
			}
			xml.append("<odds><![CDATA[" + saleInfo + "]]></odds>");

			xml.append("<bonusClass><![CDATA[" + ticket.getBonusClass() + "]]></bonusClass>");
			xml.append("</ticket>");
		}

		xml.append("</response>");
		xml.append("</body>");
		return ByteCodeUtil.xmlToObject(xml.toString());
	}

}
