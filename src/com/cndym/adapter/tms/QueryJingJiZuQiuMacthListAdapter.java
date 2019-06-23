package com.cndym.adapter.tms;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryJingJiZuQiuMacthListAdapter extends BaseAdapter implements IAdapter {

	@Autowired
	private ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService;

	@Override
	public XmlBean execute(XmlBean xmlBean) {
		XmlBean queryBean = xmlBean.getFirst("query");
		String day = queryBean.attribute("day");
		String endTime = queryBean.attribute("endTime");
		String playCode = queryBean.attribute("playId");
		String pollCode = queryBean.attribute("pollId");

		if ((Utils.isNotEmpty(day) || Utils.isNotEmpty(endTime)) && Utils.isNotEmpty(playCode) && Utils.isNotEmpty(pollCode)) {
			if (playCode.equals("04") || pollCode.equals("02")) {
				playCode = "00";
			}

			List<SubIssueForJingCaiZuQiu> subIssueForJingCaiZuQiuList = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuListByPera(day, endTime, playCode, pollCode);
			if (subIssueForJingCaiZuQiuList != null) {
				XmlBean bodyBean = getZuQiuXml(subIssueForJingCaiZuQiuList);

				return bodyBean;
			} else {
				return returnErrorCodeBody(ErrCode.E8205);
			}
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}
	}

	private XmlBean getZuQiuXml(List<SubIssueForJingCaiZuQiu> subIssueForJingCaiZuQiuList) {
		StringBuffer xml = new StringBuffer();
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		xml.append("<matchList total=\"");
		xml.append(subIssueForJingCaiZuQiuList.size());
		xml.append("\">");
		for (SubIssueForJingCaiZuQiu zuQiu : subIssueForJingCaiZuQiuList) {
			int endOperator = zuQiu.getEndOperator() == null ? 0 : zuQiu.getEndOperator();
			int bonusOperator = zuQiu.getBonusOperator() == null ? 0 : zuQiu.getBonusOperator();
			int inputAwardStatus = zuQiu.getInputAwardStatus() == null ? 0 : zuQiu.getInputAwardStatus();
			int operatorsAward = zuQiu.getOperatorsAward() == null ? 0 : zuQiu.getOperatorsAward();
			int status = 6;// 状态(1销售中,2已截止,3已开奖,4已返奖,5取消,6异常,7已算奖)
			if (endOperator == 0) {
				status = 0;
			} else if (endOperator == 1) {
				if (bonusOperator == 1) {
					status = 4;
				} else {
					if (inputAwardStatus == 1) {
						if (operatorsAward == 2) {
							status = 7;
						} else {
							status = 3;
						}
					} else {
						status = 2;
					}
				}
			} else if (endOperator == 2) {
				if (bonusOperator == 1) {
					status = 4;
				} else {
					status = 5;
				}
			}
			xml.append("<match sn=\"" + zuQiu.getSn() + "\" mid=\"" + zuQiu.getMatchId() + "\"  endTime=\"" + Utils.formatDate2Str(zuQiu.getEndTime(), Constants.DATE_FORMAT) + "\" status=\"" + status
					+ "\" date=\"" + zuQiu.getIssue() + "\">");
			xml.append("<matchName>" + zuQiu.getMatchName() + "</matchName>");
			xml.append("<mainTeam>" + zuQiu.getMainTeam() + "</mainTeam>");
			xml.append("<guestTeam>" + zuQiu.getGuestTeam() + "</guestTeam>");
			String mainTeamHalfScore = zuQiu.getMainTeamHalfScore() == null ? "" : String.valueOf(zuQiu.getMainTeamHalfScore());
			String guestTeamHalfScore = zuQiu.getGuestTeamHalfScore() == null ? "" : String.valueOf(zuQiu.getGuestTeamHalfScore());
			String mainTeamScore = zuQiu.getMainTeamScore() == null ? "" : String.valueOf(zuQiu.getMainTeamScore());
			String guestTeamScore = zuQiu.getGuestTeamScore() == null ? "" : String.valueOf(zuQiu.getGuestTeamScore());
			String letBall = zuQiu.getLetBall() == null ? "0" : zuQiu.getLetBall();

			xml.append("<matchResult mainTeamHalfScore=\"" + mainTeamHalfScore + "\" guestTeamHalfScore=\"" + guestTeamHalfScore + "\" mainTeamScore=\"" + mainTeamScore + "\" guestTeamScore=\""
					+ guestTeamScore + "\" letBall=\"" + letBall + "\" />");

			String winOrNegaSp = zuQiu.getWinOrNegaSp() == null ? "" : String.valueOf(zuQiu.getWinOrNegaSp());
			xml.append("<winOrNegaSp>" + winOrNegaSp + "</winOrNegaSp>");

			String spfWinOrNegaSp = zuQiu.getSpfWinOrNegaSp() == null ? "" : String.valueOf(zuQiu.getSpfWinOrNegaSp());
			xml.append("<spfWinOrNegaSp>" + spfWinOrNegaSp + "</spfWinOrNegaSp>");

			String scoreSp = zuQiu.getScoreSp() == null ? "" : String.valueOf(zuQiu.getScoreSp());
			xml.append("<scoreSp>" + scoreSp + "</scoreSp>");

			String totalGoalSp = zuQiu.getTotalGoalSp() == null ? "" : String.valueOf(zuQiu.getTotalGoalSp());
			xml.append("<totalGoalSp>" + totalGoalSp + "</totalGoalSp>");

			String halfCourtSp = zuQiu.getHalfCourtSp() == null ? "" : String.valueOf(zuQiu.getHalfCourtSp());
			xml.append("<halfCourtSp>" + halfCourtSp + "</halfCourtSp>");

			xml.append("</match>");

		}
		xml.append("</matchList>");
		xml.append("</response>");
		xml.append("</body>");

		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
