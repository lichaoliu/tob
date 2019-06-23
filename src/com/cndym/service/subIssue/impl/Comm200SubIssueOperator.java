package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.MatchEditLog;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.service.IMatchEditLogService;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-8 上午11:42
 */
@Repository
public class Comm200SubIssueOperator extends BaseSubIssueOperator {

	private static final Logger logger = Logger.getLogger(Comm200SubIssueOperator.class);
	@Resource
	private ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService;

	@Resource
	private XMemcachedClientWrapper memcachedClientWrapper;

	private static final String LOTTERY_CODE = "200";

	@Override
	public SubIssueComm getSubIssueComm(String issue, String matchId, String playCode, String pollCode) {
		String playCodeStr = playCode;
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
			MatchEditLog matchEditLog = matchEditLogService.getMatchEditLog(LOTTERY_CODE, playCodeStr, pollCode, issue, sn);
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
		if (Utils.isNotEmpty(playCode) && playCode.equals("04")) {
			playCode = "00";
			pollCode = "02";
		}

		StringBuffer key = new StringBuffer();
		key.append(LOTTERY_CODE).append(".").append(issue).append(".");
		key.append(playCode).append(".").append(pollCode).append(".");
		key.append(sn);

		SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = null;
		if (Utils.isNotEmpty(memcachedClientWrapper.get(key.toString()))) {
			subIssueForJingCaiZuQiu = (SubIssueForJingCaiZuQiu) memcachedClientWrapper.get(key.toString());
		}

		if (!Utils.isNotEmpty(subIssueForJingCaiZuQiu)) {
			subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(sn, issue, playCode, pollCode, Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);

			if (null == subIssueForJingCaiZuQiu) {
				throw new CndymException(ErrCode.E8119);
			}

			memcachedClientWrapper.set(key.toString(), 0, subIssueForJingCaiZuQiu);
		}

		if (pollCode.equals("02")) {
			double sp = 0;
			if ("01".equals(playCodeStr)) {
				sp = subIssueForJingCaiZuQiu.getWinSp() == null ? 0 : subIssueForJingCaiZuQiu.getWinSp();
			} else if ("02".equals(playCodeStr)) {
				sp = subIssueForJingCaiZuQiu.getTotalGoal0Sp() == null ? 0 : subIssueForJingCaiZuQiu.getTotalGoal0Sp();
			} else if ("03".equals(playCodeStr)) {
				sp = subIssueForJingCaiZuQiu.getHalfCourtFFSp() == null ? 0 : subIssueForJingCaiZuQiu.getHalfCourtFFSp();
			} else if ("04".equals(playCodeStr)) {
				sp = subIssueForJingCaiZuQiu.getScore00Sp() == null ? 0 : subIssueForJingCaiZuQiu.getScore00Sp();
			} else if ("05".equals(playCodeStr)) {
				sp = subIssueForJingCaiZuQiu.getSpfWinSp() == null ? 0 : subIssueForJingCaiZuQiu.getSpfWinSp();
			}

			if (sp == -99) {
				throw new CndymException(ErrCode.E8120);
			}
		}

		SubIssueComm subIssueComm = new SubIssueComm();
		subIssueComm.setLotteryCode(LOTTERY_CODE);
		subIssueComm.setIssue(issue);
		subIssueComm.setMatchId(matchId);
		subIssueComm.setPlayCode(subIssueForJingCaiZuQiu.getPlayCode());
		subIssueComm.setPollCode(subIssueForJingCaiZuQiu.getPollCode());
		subIssueComm.setWeek(subIssueForJingCaiZuQiu.getWeek());
		subIssueComm.setEndTime(subIssueForJingCaiZuQiu.getEndTime());
		subIssueComm.setEndOperator(subIssueForJingCaiZuQiu.getEndOperator());
		subIssueComm.setBonusOperator(subIssueForJingCaiZuQiu.getBonusOperator());
		subIssueComm.setDanShiEndTime(subIssueForJingCaiZuQiu.getEndDanShiTime());
		subIssueComm.setFuShiEndTime(subIssueForJingCaiZuQiu.getEndFuShiTime());
		return subIssueComm;
	}

	private void isShow(SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu, String playCode) {

	}

	public ISubIssueForJingCaiZuQiuService getSubIssueForJingCaiZuQiuService() {
		return subIssueForJingCaiZuQiuService;
	}

	public void setSubIssueForJingCaiZuQiuService(ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService) {
		this.subIssueForJingCaiZuQiuService = subIssueForJingCaiZuQiuService;
	}
}
