package com.cndym.adapter.tms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.dao.IBonusTicketDao;
import com.cndym.exception.ErrCode;
import com.cndym.service.IBonusTicketService;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ITicketService;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

@Component
public class QueryBonusByIssueAdapter extends BaseAdapter implements IAdapter {
	@Autowired
	private IMainIssueService mainIssueService;
	@Autowired
	private IBonusTicketService bonusTicketService;

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
		String issue = queryBean.attribute("issue");
		String lotteryCode = queryBean.attribute("lotteryId");

		int page = Utils.formatInt(queryBean.attribute("page"), 1);
		int pageSize = Utils.formatInt(queryBean.attribute("pageSize"), 200);

		if (Utils.isNotEmpty(lotteryCode) && Utils.isNotEmpty(issue)) {
			MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
			if (mainIssue == null) {
				return returnErrorCodeBody(ErrCode.E8202);
			}
			int bonusStatus = mainIssue.getBonusStatus();

			if (bonusStatus == Constants.ISSUE_BONUS_STATUS_YES) {
				PageBean pageBean = bonusTicketService.getBounsTicketByIssue(sid, lotteryCode, issue, page, pageSize);

				return getReturnBean(pageBean, pageSize);
			} else {
				return returnErrorCodeBody(ErrCode.E8203);
			}
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}
	}

	private XmlBean getReturnBean(PageBean pageBean, int pageSize) {
		List<BonusTicket> bonusTicketList = pageBean.getPageContent();
		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\" page=\"" + pageBean.getPageId() + "\" pageSize=\"" + pageSize + "\" pageTotal=\""
				+ pageBean.getPageTotal() + "\">");
		for (BonusTicket bonusTicket : bonusTicketList) {
			xml.append("<ticket ");
			xml.append("ticketId =\"" + bonusTicket.getOutTicketId() + "\" ");
			xml.append("sysId =\"" + bonusTicket.getTicketId() + "\" ");
			xml.append("orderId =\"" + bonusTicket.getOrderId() + "\" ");
			xml.append("bonusAmount =\"" + Utils.formatNumberZD(bonusTicket.getBonusAmount()) + "\" ");
			xml.append("fixBonusAmount =\"" + Utils.formatNumberZD(bonusTicket.getFixBonusAmount()) + "\" ");

			String bt = bonusTicket.getBonusTime() == null ? "" : Utils.formatDate2Str(bonusTicket.getBonusTime(), Constants.DATE_FORMAT);
			xml.append("bonusTime =\"" + bt + "\" ");

			int big = bonusTicket.getBigBonus() == null ? 0 : bonusTicket.getBigBonus();
			if (big == 5) {
				big = 0;
			} else if (big == 4) {
				big = 1;
			}
			xml.append("isBig =\"" + big + "\" ");
			xml.append(">");
			xml.append("<bonusClass><![CDATA[" + bonusTicket.getBonusClass() + "]]></bonusClass>");
			xml.append("</ticket>");
		}

		xml.append("</response>");
		xml.append("</body>");
		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
