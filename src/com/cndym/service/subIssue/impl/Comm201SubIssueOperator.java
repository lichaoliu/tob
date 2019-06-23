package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.MatchEditLog;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.service.IMatchEditLogService;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-8 上午11:42
 */
@Repository
public class Comm201SubIssueOperator extends BaseSubIssueOperator {

	private static final Logger logger = Logger.getLogger(Comm201SubIssueOperator.class);

	@Resource
	private ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService;

	@Resource
	private XMemcachedClientWrapper memcachedClientWrapper;

	private static final String LOTTERY_CODE = "201";

	@Override
	public SubIssueComm getSubIssueComm(String issue, String matchId, String playCode, String pollCode) {
		String oldPlayCode = playCode;
		issue = matchId.substring(0, 8);
		String sn = matchId.substring(8);

		// 判断该场比赛的这个玩法是否禁售
		String keyStr = Constants.MEMCACHED_MATCH_LOG + issue + sn + LOTTERY_CODE + playCode;
		if (pollCode.equals("00")) {
			keyStr = keyStr + "02";
		} else {
			keyStr = keyStr + pollCode;
		}
		Integer isCansSell = memcachedClientWrapper.get(keyStr);
		if (isCansSell == null) {
			IMatchEditLogService matchEditLogService = (IMatchEditLogService) SpringUtils.getBean("matchEditLogServiceImpl");
			MatchEditLog matchEditLog = matchEditLogService.getMatchEditLog(LOTTERY_CODE, oldPlayCode, pollCode, issue, sn);
			if (matchEditLog != null) {
				isCansSell = matchEditLog.getStatus();
			} else {
				isCansSell = 1;// 默认开售
			}

			try {
				memcachedClientWrapper.getMemcachedClient().set(keyStr, 432000, isCansSell);
			} catch (Exception e) {
				throw new CndymException(ErrCode.E8120);
			}
		}
		if (isCansSell == 0) {// 玩法禁售
			throw new CndymException(ErrCode.E8120);
		}

		if (Utils.isNotEmpty(pollCode) && pollCode.equals("00")) {
			playCode = "00";
			pollCode = "02";
		}
		if (Utils.isNotEmpty(playCode) && playCode.equals("03")) {
			playCode = "00";
			pollCode = "02";
		}

		StringBuffer key = new StringBuffer();
		key.append(LOTTERY_CODE).append(".").append(issue).append(".");
		key.append(playCode).append(".").append(pollCode).append(".");
		key.append(sn);
		SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = null;
		if (Utils.isNotEmpty(memcachedClientWrapper.get(key.toString()))) {
			subIssueForJingCaiLanQiu = (SubIssueForJingCaiLanQiu) memcachedClientWrapper.get(key.toString());
		}
		subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(sn, issue, playCode, pollCode, Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);

		if (null == subIssueForJingCaiLanQiu) {
			throw new CndymException(ErrCode.E8119);
		}

		// 验证sp值
		isShow(subIssueForJingCaiLanQiu, oldPlayCode);

		SubIssueComm subIssueComm = new SubIssueComm();
		subIssueComm.setLotteryCode(LOTTERY_CODE);
		subIssueComm.setIssue(issue);
		subIssueComm.setMatchId(matchId);
		subIssueComm.setPlayCode(subIssueForJingCaiLanQiu.getPlayCode());
		subIssueComm.setPollCode(subIssueForJingCaiLanQiu.getPollCode());
		subIssueComm.setWeek(subIssueForJingCaiLanQiu.getWeek());
		subIssueComm.setEndTime(subIssueForJingCaiLanQiu.getEndTime());
		subIssueComm.setEndOperator(subIssueForJingCaiLanQiu.getEndOperator());
		subIssueComm.setBonusOperator(subIssueForJingCaiLanQiu.getBonusOperator());
		subIssueComm.setDanShiEndTime(subIssueForJingCaiLanQiu.getEndDanShiTime());
		subIssueComm.setFuShiEndTime(subIssueForJingCaiLanQiu.getEndFuShiTime());

		memcachedClientWrapper.set(key.toString(), 0, subIssueForJingCaiLanQiu);
		return subIssueComm;
	}

	private void isShow(SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu, String playCode) {
		// 胜负
		if (playCode.equals("01")) {
			Double negaSp = subIssueForJingCaiLanQiu.getNegaSp();
			Double winSp = subIssueForJingCaiLanQiu.getWinSp();
			if (negaSp.intValue() == -99 || winSp.intValue() == -99) {
				throw new CndymException(ErrCode.E8120);
			}
		}
		// 让分胜负
		if (playCode.equals("02")) {
			Double letNegaSp = subIssueForJingCaiLanQiu.getLetNegaSp();
			Double letWinSp = subIssueForJingCaiLanQiu.getLetWinSp();
			if (letNegaSp.intValue() == -99 || letWinSp.intValue() == -99) {
				throw new CndymException(ErrCode.E8120);
			}
		}

		// 胜分差
		if (playCode.equals("03")) {

			Double winNegaDiffM1T5Sp = subIssueForJingCaiLanQiu.getWinNegaDiffM1T5Sp();
			Double winNegaDiffM6T10Sp = subIssueForJingCaiLanQiu.getWinNegaDiffM6T10Sp();
			Double winNegaDiffM11T15Sp = subIssueForJingCaiLanQiu.getWinNegaDiffM11T15Sp();
			Double winNegaDiffM16T20Sp = subIssueForJingCaiLanQiu.getWinNegaDiffM16T20Sp();
			Double winNegaDiffM21T25Sp = subIssueForJingCaiLanQiu.getWinNegaDiffM21T25Sp();
			Double winNegaDiffM26Sp = subIssueForJingCaiLanQiu.getWinNegaDiffM26Sp();
			Double winNegaDiffG1T5Sp = subIssueForJingCaiLanQiu.getWinNegaDiffG1T5Sp();
			Double winNegaDiffG6T10Sp = subIssueForJingCaiLanQiu.getWinNegaDiffG6T10Sp();
			Double winNegaDiffG11T15Sp = subIssueForJingCaiLanQiu.getWinNegaDiffG11T15Sp();
			Double winNegaDiffG16T20Sp = subIssueForJingCaiLanQiu.getWinNegaDiffG16T20Sp();
			Double winNegaDiffG21T25Sp = subIssueForJingCaiLanQiu.getWinNegaDiffG21T25Sp();
			Double winNegaDiffG26Sp = subIssueForJingCaiLanQiu.getWinNegaDiffG26Sp();

			if (winNegaDiffM1T5Sp.intValue() == -99 || winNegaDiffM6T10Sp.intValue() == -99 || winNegaDiffM11T15Sp.intValue() == -99 || winNegaDiffM16T20Sp.intValue() == -99
					|| winNegaDiffM21T25Sp.intValue() == -99 || winNegaDiffM26Sp.intValue() == -99 || winNegaDiffG1T5Sp.intValue() == -99 || winNegaDiffG6T10Sp.intValue() == -99
					|| winNegaDiffG11T15Sp.intValue() == -99 || winNegaDiffG16T20Sp.intValue() == -99 || winNegaDiffG21T25Sp.intValue() == -99 || winNegaDiffG26Sp.intValue() == -99) {
				throw new CndymException(ErrCode.E8120);
			}
		}
		// 大小分
		if (playCode.equals("04")) {
			Double bigSp = subIssueForJingCaiLanQiu.getBigSp();
			Double littleSp = subIssueForJingCaiLanQiu.getLittleSp();
			if (bigSp.intValue() == -99 || littleSp.intValue() == -99) {
				throw new CndymException(ErrCode.E8120);
			}
		}
	}

	public ISubIssueForJingCaiLanQiuService getSubIssueForJingCaiLanQiuService() {
		return subIssueForJingCaiLanQiuService;
	}

	public void setSubIssueForJingCaiLanQiuService(ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService) {
		this.subIssueForJingCaiLanQiuService = subIssueForJingCaiLanQiuService;
	}
}
