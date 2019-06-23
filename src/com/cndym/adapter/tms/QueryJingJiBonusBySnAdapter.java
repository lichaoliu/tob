package com.cndym.adapter.tms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.IBonusTicketService;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

@Component
public class QueryJingJiBonusBySnAdapter extends BaseAdapter implements IAdapter {
	@Autowired
	private IBonusTicketService bonusTicketService;

	@Autowired
	private ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService;
	@Autowired
	private ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService;

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
		String issue = queryBean.attribute("day");
		String lotteryCode = queryBean.attribute("lotteryId");
		String sn = queryBean.attribute("sn");

		int page = Utils.formatInt(queryBean.attribute("page"), 1);
		int pageSize = Utils.formatInt(queryBean.attribute("pageSize"), 200);

		if (Utils.isNotEmpty(lotteryCode) && Utils.isNotEmpty(issue) && Utils.isNotEmpty(sn)) {

			int bonusStatus = -1;
			if ("200".equals(lotteryCode)) {
				SubIssueForJingCaiZuQiu zuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuByIssueSn(issue, sn);
				if (zuQiu == null) {
					return returnErrorCodeBody(ErrCode.E8205);
				}
				bonusStatus = zuQiu.getBonusOperator();
			} else if ("201".equals(lotteryCode)) {
				SubIssueForJingCaiLanQiu lanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuByIssueSn(issue, sn);
				if (lanQiu == null) {
					return returnErrorCodeBody(ErrCode.E8205);
				}
				bonusStatus = lanQiu.getBonusOperator();
			}
			if (bonusStatus == Constants.ISSUE_BONUS_STATUS_YES) {
				PageBean pageBean = bonusTicketService.getBounsTicketByIssue(sid, lotteryCode, issue, sn, page, pageSize);

				return getReturnBean(pageBean, pageSize);
			} else {
				return returnErrorCodeBody(ErrCode.E0003);
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
			xml.append("id =\"" + bonusTicket.getOutTicketId() + "\" ");
			xml.append("bonusAmount =\"" + Utils.formatNumberZD(bonusTicket.getBonusAmount()) + "\" ");
			xml.append("fixBonusAmount =\"" + Utils.formatNumberZD(bonusTicket.getFixBonusAmount()) + "\" ");
			String bt = bonusTicket.getBonusTime() == null ? "" : Utils.formatDate2Str(bonusTicket.getBonusTime(), Constants.DATE_FORMAT);
			xml.append("bonusTime =\"" + bt + "\" ");
			xml.append("isBig =\"" + bonusTicket.getBigBonus() + "\" ");

			xml.append("/>");
		}

		xml.append("</response>");
		xml.append("</body>");
		return ByteCodeUtil.xmlToObject(xml.toString());
	}

}
