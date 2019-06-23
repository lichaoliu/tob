package com.cndym.adapter.tms;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.SubGame;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ISubGameService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

@Component
public class QueryFootBallGameAdapter extends BaseAdapter implements IAdapter {
	private static Logger logger = Logger.getLogger(OrderAdapter.class);

	@Autowired
	private IMainIssueService mainIssueService;
	@Autowired
	private ISubGameService subGameService;

	@Override
	public XmlBean execute(XmlBean xmlBean) {
		logger.info("接受请求: " + xmlBean.toXml());

		XmlBean queryBean = xmlBean.getFirst("query");
		String lotteryCode = queryBean.attribute("lotteryId");
		String issue = queryBean.attribute("issue");

		if (Utils.isNotEmpty(lotteryCode) && "300,301,302,303".indexOf(lotteryCode) > -1) {
			List<MainIssue> issueList = mainIssueService.getStartMainIssueByLotteryIssue(lotteryCode, issue);

			if (Utils.isNotEmpty(issueList)) {
				StringBuffer returnStr = new StringBuffer();

				for (MainIssue issueBean : issueList) {
					List<SubGame> subGameList = subGameService.getSubGameListByLotteryCodeIssue(issueBean.getLotteryCode(), issueBean.getName());
					returnStr.append(getIssueString(subGameList, issueBean));
				}

				XmlBean bodyBean = getReturnBean(returnStr.toString());
				return bodyBean;
			} else {
				return returnErrorCodeBody(ErrCode.E8202);
			}
		}

		return returnErrorCodeBody(ErrCode.E0003);
	}

	private String getIssueString(List<SubGame> subGameList, MainIssue mainIssue) {
		StringBuffer xml = new StringBuffer();

		xml.append("<issue ");
		xml.append("name =\"" + mainIssue.getName() + "\" ");
		xml.append("lotteryId=\"" + mainIssue.getLotteryCode() + "\" ");
		xml.append("startTime =\"" + Utils.formatDate2Str(mainIssue.getStartTime(), Constants.DATE_FORMAT) + "\" ");
		xml.append("stopTime=\"" + Utils.formatDate2Str(mainIssue.getDuplexTime(), Constants.DATE_FORMAT) + "\" ");
		xml.append("endTime =\"" + Utils.formatDate2Str(mainIssue.getEndTime(), Constants.DATE_FORMAT) + "\" ");

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

		xml.append("status =\"" + issueStatus + "\" ");
		if (mainIssue.getBonusTime() != null) {
			xml.append("bonusTime =\"" + Utils.formatDate2Str(mainIssue.getBonusTime(), Constants.DATE_FORMAT) + "\" ");
		} else {
			xml.append("bonusTime =\"" + "" + "\" ");
		}

		String bonusNumber = mainIssue.getBonusNumber() == null ? "" : mainIssue.getBonusNumber();
		xml.append("bonusNumber =\"" + bonusNumber + "\" ");

		String bonusTotal = mainIssue.getBonusTotal() == null ? "" : Utils.formatNumberZD(mainIssue.getBonusTotal());
		xml.append("bonusTotal =\"" + bonusTotal + "\" ");
		String prizePool = mainIssue.getPrizePool() == null ? "" : Utils.formatNumberZD(mainIssue.getPrizePool());
		xml.append("prizePool =\"" + prizePool + "\" ");

		String globalSaleTotal = mainIssue.getGlobalSaleTotal() == null ? "" : Utils.formatNumberZD(mainIssue.getGlobalSaleTotal());
		xml.append("globalSaleTotal =\"" + globalSaleTotal + "\">");

		xml.append("<gameList>");
		for (SubGame subGame : subGameList) {
			xml.append("<game ");
			xml.append("leageName=\"" + subGame.getLeageName() + "\" ");
			xml.append("masterName=\"" + subGame.getMasterName() + "\" ");
			xml.append("guestName=\"" + subGame.getGuestName() + "\" ");

			String startTime = Utils.formatDate2Str(subGame.getStartTime(), "yyyy-MM-dd HH:mm:ss");
			String endTime = Utils.formatDate2Str(subGame.getEndTime(), "yyyy-MM-dd HH:mm:ss");
			xml.append("startTime=\"" + startTime + "\" ");
			xml.append("endTime=\"" + endTime + "\" ");
			xml.append("index=\"" + subGame.getIndex() + "\" ");
			xml.append("result=\"" + subGame.getResult() + "\" ");
			xml.append("resultDes=\"" + subGame.getResultDes() + "\" ");
			xml.append("scoreAtHalf=\"" + subGame.getScoreAtHalf() + "\" ");
			xml.append("finalScore=\"" + subGame.getFinalScore() + "\" ");
			xml.append("secondHalfTheScore=\"" + subGame.getSecondHalfTheScore() + "\" ");
			xml.append("/>");
		}
		xml.append("</gameList>");

		String bonusClass = mainIssue.getBonusClass() == null ? "" : mainIssue.getBonusClass();
		if (status == 3 || status == 4) {
			xml.append("<bonusClass><![CDATA[" + bonusClass + "]]></bonusClass>");
		} else {
			xml.append("<bonusClass></bonusClass>");
		}

		xml.append("</issue>");

		return xml.toString();
	}

	private XmlBean getReturnBean(String issueStr) {
		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		xml.append(issueStr);
		xml.append("</response>");
		xml.append("</body>");

		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
