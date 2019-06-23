package com.cndym.dao;

import com.cndym.bean.tms.BonusLog;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:24
 */
public interface IBonusLogDao extends IGenericDao<BonusLog> {
	public Map<String, Object> customSql(String sql, Object[] para);

	public BonusLog getBonusLogByTicketId(String ticketId);

	public List<BonusLog> getNoDuiJiangTicket(String lotteryCode, String postCode);

	public int updateBonusLogForDuiJiang(BonusLog bonusLog);

	public PageBean getNoDuiJiangTicket(String lotteryCode, String postCode, Integer pageId, Integer pageSize);
}
