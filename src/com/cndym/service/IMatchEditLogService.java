package com.cndym.service;

import com.cndym.bean.tms.MatchEditLog;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: MengJingyi QQ: 116741034 Date: 14-2-13 Time: 上午10:40 To change this template use File | Settings | File Templates.
 */
public interface IMatchEditLogService extends IGenericeService<MatchEditLog> {

	public List<MatchEditLog> getMatchEditLogsByMsg(MatchEditLog matchEditLog);

	public boolean setEditLogsByMsg(List<MatchEditLog> matchEditLog);

	public void delEditLogsByMsg(String lotteryCode, String issue, String sn);

	public MatchEditLog getMatchEditLog(String lotteryCode, String playCode, String pollCode, String issue, String sn);

}
