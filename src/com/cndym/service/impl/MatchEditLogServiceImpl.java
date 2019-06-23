package com.cndym.service.impl;

import com.cndym.bean.tms.MatchEditLog;
import com.cndym.dao.IMatchEditLogDao;
import com.cndym.service.IMatchEditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: MengJingyi QQ: 116741034 Date: 14-2-13 Time: 上午10:47 To change this template use File | Settings | File Templates.
 */
@Service
public class MatchEditLogServiceImpl extends GenericServiceImpl<MatchEditLog, IMatchEditLogDao> implements IMatchEditLogService {
	@Autowired
	private IMatchEditLogDao matchEditLogDao;

	@Override
	public List<MatchEditLog> getMatchEditLogsByMsg(MatchEditLog matchEditLog) {
		return matchEditLogDao.getMatchEditLogsByMsg(matchEditLog);
	}

	@Override
	public boolean setEditLogsByMsg(List<MatchEditLog> matchEditLog) {
		return matchEditLogDao.saveAllObject(matchEditLog);
	}

	@Override
	public void delEditLogsByMsg(String lotteryCode, String issue, String sn) {
		matchEditLogDao.delEditLogsByMsg(lotteryCode, issue, sn);
	}

	@Override
	public MatchEditLog getMatchEditLog(String lotteryCode, String playCode, String pollCode, String issue, String sn) {
		return matchEditLogDao.getMatchEditLog(lotteryCode, playCode, pollCode, issue, sn);
	}
}
