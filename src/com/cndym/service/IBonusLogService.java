package com.cndym.service;

import java.util.List;

import com.cndym.bean.tms.BonusJcLog;
import com.cndym.bean.tms.BonusLog;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:00
 */
public interface IBonusLogService extends IGenericeService<BonusLog> {

	public BonusLog getBonusLogByTicketId(String ticketId);

	public List<BonusLog> getNoDuiJiangTicket(String lotteryCode, String postCode);

	public int updateBonusLogForDuiJiang(BonusLog bonusLog);

	public boolean doSaveAll(List<BonusLog> bonusLog, BonusJcLog bonusJcLog);

	public PageBean getNoDuiJiangTicket(String lotteryCode, String postCode, Integer pageId, Integer pageSize);
}
