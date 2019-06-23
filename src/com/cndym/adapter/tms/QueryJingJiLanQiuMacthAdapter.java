package com.cndym.adapter.tms;

import com.cndym.IAdapter;
import com.cndym.adapter.BaseAdapter;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA. User: MengJingyi QQ: 116741034 Date: 12-10-31 Time: 上午11:48 To change this template use File | Settings | File Templates.
 */

@Component
public class QueryJingJiLanQiuMacthAdapter extends BaseAdapter implements IAdapter {
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
		String day = queryBean.attribute("day");
		String sn = queryBean.attribute("sn");
		String playCode = queryBean.attribute("playId");
		String pollCode = queryBean.attribute("pollId");

		if (Utils.isNotEmpty(day) && Utils.isNotEmpty(playCode) && Utils.isNotEmpty(pollCode) && Utils.isNotEmpty(sn)) {
			if (playCode.equals("03") || pollCode.equals("02")) {
				playCode = "00";
			}
			SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuByIssueSn(day, sn, playCode, pollCode);
			if (subIssueForJingCaiLanQiu != null) {
				XmlBean bodyBean = getXml(subIssueForJingCaiLanQiu);

				return bodyBean;
			} else {
				return returnErrorCodeBody(ErrCode.E8205);
			}
		} else {
			return returnErrorCodeBody(ErrCode.E0003);
		}
	}

	private XmlBean getXml(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu) {
		StringBuffer xml = new StringBuffer();

		int endOperator = subIssueForJingCaiLanQiu.getEndOperator() == null ? 0 : subIssueForJingCaiLanQiu.getEndOperator();
		int bonusOperator = subIssueForJingCaiLanQiu.getBonusOperator() == null ? 0 : subIssueForJingCaiLanQiu.getBonusOperator();
		int inputAwardStatus = subIssueForJingCaiLanQiu.getInputAwardStatus() == null ? 0 : subIssueForJingCaiLanQiu.getInputAwardStatus();

		int status = 6;// 状态(1销售中,2已截止,3已开奖,4已返奖,5取消 6 异常)
		if (endOperator == 0) {
			status = 0;
		} else if (endOperator == 1) {
			if (bonusOperator == 1) {
				status = 4;
			} else {
				if (inputAwardStatus == 1) {
					status = 3;
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
		xml.append("<body>");
		xml.append("<response code=\"" + ErrCode.E0000 + "\" msg=\"" + ErrCode.codeToMsg(ErrCode.E0000) + "\">");
		xml.append("<match sn=\"" + subIssueForJingCaiLanQiu.getSn() + "\" endTime=\"" + Utils.formatDate2Str(subIssueForJingCaiLanQiu.getEndTime(), Constants.DATE_FORMAT) + "\" status=\"" + status
				+ "\">");
		xml.append("<matchName>" + subIssueForJingCaiLanQiu.getMatchName() + "</matchName>");
		xml.append("<mainTeam>" + subIssueForJingCaiLanQiu.getMainTeam() + "</mainTeam>");
		xml.append("<guestTeam>" + subIssueForJingCaiLanQiu.getGuestTeam() + "</guestTeam>");

		String mainTeamHalfScore = subIssueForJingCaiLanQiu.getMainTeamHalfScore() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getMainTeamHalfScore());
		String guestTeamHalfScore = subIssueForJingCaiLanQiu.getGuestTeamHalfScore() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getGuestTeamHalfScore());
		String mainTeamScore = subIssueForJingCaiLanQiu.getMainTeamScore() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getMainTeamScore());
		String guestTeamScore = subIssueForJingCaiLanQiu.getGuestTeamScore() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getGuestTeamScore());
		String letBall = subIssueForJingCaiLanQiu.getLetBall() == null ? "0" : subIssueForJingCaiLanQiu.getLetBall();
		String preCast = subIssueForJingCaiLanQiu.getPreCast() == null ? "" : subIssueForJingCaiLanQiu.getPreCast();

		xml.append("<matchResult mainTeamHalfScore=\"" + mainTeamHalfScore + "\" guestTeamHalfScore=\"" + guestTeamHalfScore + "\" mainTeamScore=\"" + mainTeamScore + "\" guestTeamScore=\""
				+ guestTeamScore + "\" letBall=\"" + letBall + "\" preCast=\"" + preCast + "\" />");

		String winOrNegaSp = subIssueForJingCaiLanQiu.getWinOrNegaSp() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getWinOrNegaSp());
		xml.append("<winOrNegaSp>" + winOrNegaSp + "</winOrNegaSp>");

		String letWinOrNegaSp = subIssueForJingCaiLanQiu.getLetWinOrNegaSp() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getLetWinOrNegaSp());
		xml.append("<letWinOrNegaSp>" + letWinOrNegaSp + "</letWinOrNegaSp>");

		String winNegaDiffSp = subIssueForJingCaiLanQiu.getWinNegaDiffSp() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getWinNegaDiffSp());
		xml.append("<winNegaDiffSp>" + winNegaDiffSp + "</winNegaDiffSp>");

		String bigOrLittleSp = subIssueForJingCaiLanQiu.getBigOrLittleSp() == null ? "" : String.valueOf(subIssueForJingCaiLanQiu.getBigOrLittleSp());
		xml.append("<bigOrLittleSp>" + bigOrLittleSp + "</bigOrLittleSp>");

		xml.append("</match>");
		xml.append("</response>");
		xml.append("</body>");

		return ByteCodeUtil.xmlToObject(xml.toString());
	}
}
