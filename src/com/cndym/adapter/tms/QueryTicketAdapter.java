package com.cndym.adapter.tms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 按外部投注订单号查询订单信息
 * 
 * @author 金坤涛
 * 
 */
@Component
public class QueryTicketAdapter extends BaseAdapter implements IAdapter {

	@Autowired
	private ITicketService ticketService;

	@Override
	public XmlBean execute(XmlBean xmlBean) {
		XmlBean ticketsBean = xmlBean.getFirst("tickets");
		XmlBean sidBean = xmlBean.getFirst("sid");
		String sid = "";
		if (sidBean != null) {
			sid = sidBean.attribute("text");
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}

		List<XmlBean> ticketBeanList = ticketsBean.getAll("ticket");
		if (Utils.isNotEmpty(ticketBeanList)) {
			if (ticketBeanList.size() > 50) {
				return returnErrorCodeBody(ErrCode.E8204);
			}
			List<String> ticketIdList = new ArrayList<String>();
			for (XmlBean ticketBean : ticketBeanList) {
				String ticketId = ticketBean.attribute("ticketId");

				ticketIdList.add(ticketId);
			}

			List<Ticket> ticketList = ticketService.getTicketByOutTicketId(ticketIdList, sid);
			if (Utils.isNotEmpty(ticketList)) {
				Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
				for (Ticket ticket : ticketList) {
					ticketMap.put(ticket.getOutTicketId(), ticket);
				}
				XmlBean bodyBean = getReturnBean(ticketIdList, ticketMap);
				return bodyBean;
			} else {
				return returnErrorCodeBody(ErrCode.E8201);
			}
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}
	}

	private XmlBean getReturnBean(List<String> ticketIdList, Map<String, Ticket> ticketMap) {
		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		for (String ticketId : ticketIdList) {
			Ticket ticket = ticketMap.get(ticketId);
			if (ticket != null) {
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
					xml.append("msg =\"等待交易\" ");//
				} else if (ticketStatus == 2 || ticketStatus == 6) {
					xml.append("status =\"1\" ");// 交易中
					xml.append("msg =\"交易中\" ");//
				} else if (ticketStatus == 3) {
					xml.append("status =\"2\" ");// 交易成功
					xml.append("msg =\"交易成功\" ");//
				} else if (ticketStatus == 4 || ticketStatus == 5) {
					xml.append("status =\"3\" ");// 交易失败
					xml.append("msg =\"交易失败\" ");//
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
			} else {
				xml.append("<ticket ");
				xml.append("ticketId =\"" + ticketId + "\" ");
				xml.append("status =\"999\" ");// 交易成功
				xml.append("msg =\"票号不存在\" ");//
				xml.append(">");
				xml.append("</ticket>");
			}
		}

		xml.append("</response>");
		xml.append("</body>");
		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
