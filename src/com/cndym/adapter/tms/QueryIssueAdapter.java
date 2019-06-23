package com.cndym.adapter.tms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IMemberService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 期次查询
 * 
 * @author 金坤涛
 * 
 */
@Component
public class QueryIssueAdapter extends BaseAdapter implements IAdapter {
	@Autowired
	private IMainIssueService mainIssueService;
	@Autowired
	private IMemberService memberService;

	@Override
	public XmlBean execute(XmlBean xmlBean) {
		String sid = Utils.formatStr(xmlBean.getFirst("sid").attribute("text"));
		XmlBean queryBean = xmlBean.getFirst("query");
		String issue = queryBean.attribute("issue");
		String lotteryCode = queryBean.attribute("lotteryId");

		if (Utils.isNotEmpty(lotteryCode)) {
			
			if(Utils.isEmpty(issue) && lotteryControl(lotteryCode) == 1){
				return returnErrorCodeBody(ErrCode.E8109);
			}
			
			Member member = memberService.getMemberBySid(sid);
	        //验证彩种是否该代理商可售
	        if (lotteryControl(member.getBackup1(),lotteryCode) == 0) {
	            return returnErrorCodeBody(ErrCode.E8109);
	        }
			List<MainIssue> issueList = mainIssueService.getStartMainIssueByLotteryIssue(lotteryCode, issue);

			if (Utils.isNotEmpty(issueList)) {
				XmlBean bodyBean = getReturnBean(issueList);
				return bodyBean;
			} else {
				return returnErrorCodeBody(ErrCode.E8202);
			}

		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}
	}

	private XmlBean getReturnBean(List<MainIssue> issueList) {
		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		for (MainIssue mainIssue : issueList) {
			xml.append("<issue ");
			xml.append("name=\"" + mainIssue.getName() + "\" ");
			xml.append("lotteryId=\"" + mainIssue.getLotteryCode() + "\" ");
			xml.append("startTime=\"" + Utils.formatDate2Str(mainIssue.getStartTime(), Constants.DATE_FORMAT) + "\" ");
			xml.append("stopTime=\"" + Utils.formatDate2Str(mainIssue.getDuplexTime(), Constants.DATE_FORMAT) + "\" ");
			xml.append("endTime=\"" + Utils.formatDate2Str(mainIssue.getEndTime(), Constants.DATE_FORMAT) + "\" ");

			int issueStatus = 0;// 状态(0未开售,1销售中,2已截止,3已开奖,4已返奖,5暂停销售)
			int status = mainIssue.getStatus();
			int bonusStatus = mainIssue.getBonusStatus();
			int sendStatus = mainIssue.getSendStatus();
			if (status == Constants.ISSUE_SALE_WAIT) {
				if (lotteryControl(mainIssue.getLotteryCode()) == 1) {
					issueStatus = 5;
				} else {
					issueStatus = 0;
				}
			} else if (status == Constants.ISSUE_STATUS_START) {
				if (lotteryControl(mainIssue.getLotteryCode()) == 1) {
					issueStatus = 5;
				} else {
					if (sendStatus == Constants.ISSUE_SALE_SEND) {
						issueStatus = 1;
					} else if (sendStatus == Constants.ISSUE_SALE_WAIT) {
						issueStatus = 0;
					}
				}
			} else if (status == Constants.ISSUE_STATUS_END) {
				if (bonusStatus == Constants.BONUS_STATUS_YES) {
					issueStatus = 4;
				} else if (Utils.isNotEmpty(mainIssue.getBonusNumber()) && !"-".equals(mainIssue.getBonusNumber())) {
					issueStatus = 3;
				} else {
					issueStatus = 2;
				}
			} else if (status == Constants.ISSUE_STATUS_PAUSE) {
				issueStatus = 5;
			}

			xml.append("status=\"" + issueStatus + "\" ");
			xml.append("operatorsAward=\"" + mainIssue.getOperatorsAward() + "\" ");
			if (mainIssue.getBonusTime() != null) {
				xml.append("bonusTime=\"" + Utils.formatDate2Str(mainIssue.getBonusTime(), Constants.DATE_FORMAT) + "\" ");
			} else {
				xml.append("bonusTime=\"" + "" + "\" ");
			}
			if (mainIssue.getSendBonusTime() != null) {
				xml.append("sendBonusTime=\"" + Utils.formatDate2Str(mainIssue.getSendBonusTime(), Constants.DATE_FORMAT) + "\" ");
			} else {
				xml.append("sendBonusTime=\"" + "" + "\" ");
			}

			String bonusNumber = mainIssue.getBonusNumber() == null ? "" : mainIssue.getBonusNumber();
			xml.append("bonusNumber=\"" + bonusNumber + "\" ");

			String bonusTotal = mainIssue.getBonusTotal() == null ? "" : Utils.formatNumberZD(mainIssue.getBonusTotal());
			xml.append("bonusTotal=\"" + bonusTotal + "\" ");
			String prizePool = mainIssue.getPrizePool() == null ? "" : Utils.formatNumberZD(mainIssue.getPrizePool());
			xml.append("prizePool=\"" + prizePool + "\" ");

			// String saleTotal = mainIssue.getSaleTotal() == null ? "" : Utils.formatMoney(mainIssue.getSaleTotal());
			// xml.append("saleTotal =\"" + saleTotal + "\" ");

			String globalSaleTotal = mainIssue.getGlobalSaleTotal() == null ? "" : Utils.formatNumberZD(mainIssue.getGlobalSaleTotal());
			xml.append("globalSaleTotal=\"" + globalSaleTotal + "\">");

			String bonusClass = mainIssue.getBonusClass() == null ? "" : mainIssue.getBonusClass();
			xml.append("<bonusClass><![CDATA[" + bonusClass + "]]></bonusClass>");
			xml.append("</issue>");
		}
		xml.append("</response>");
		xml.append("</body>");
		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
