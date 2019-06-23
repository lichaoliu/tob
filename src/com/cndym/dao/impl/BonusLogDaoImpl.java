package com.cndym.dao.impl;

import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.dao.IBonusLogDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:25
 */
@Repository
public class BonusLogDaoImpl extends GenericDaoImpl<BonusLog> implements IBonusLogDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SessionFactory sessionFactoryTemp;

	@PostConstruct
	private void sessionFactoryInit() {
		super.setSessionFactory(sessionFactoryTemp);
	}

	@Override
	public Map<String, Object> customSql(String sql, Object[] para) {
		return null;
	}

	@Override
	public BonusLog getBonusLogByTicketId(String ticketId) {
		StringBuffer sql = new StringBuffer("from BonusLog where ticketId = ? ");
		List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
		hibernateParas.add(new HibernatePara(ticketId));

		List<BonusLog> logList = findList(sql.toString(), hibernateParas);
		if (Utils.isNotEmpty(logList)) {
			return logList.get(0);
		}
		return null;
	}

	@Override
	public List<BonusLog> getNoDuiJiangTicket(String lotteryCode, String postCode) {
		StringBuffer sql = new StringBuffer("from BonusLog where duiJiangStatus = ? ");
		List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
		hibernateParas.add(new HibernatePara(Constants.DUI_JIANG_STATUS_NO));
		sql.append(" and lotteryCode=? ");
		hibernateParas.add(new HibernatePara(lotteryCode));

		sql.append(" and postCode=? ");
		hibernateParas.add(new HibernatePara(postCode));
		return findList(sql.toString(), hibernateParas);
	}

	@Override
	public PageBean getNoDuiJiangTicket(String lotteryCode, String postCode, Integer pageId, Integer pageSize) {
		StringBuffer sql = new StringBuffer("from BonusLog where duiJiangStatus = ? ");
		List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
		hibernateParas.add(new HibernatePara(Constants.DUI_JIANG_STATUS_NO));
		sql.append(" and lotteryCode=? ");
		hibernateParas.add(new HibernatePara(lotteryCode));

		sql.append(" and postCode=? ");
		hibernateParas.add(new HibernatePara(postCode));
		return getPageBeanByPara(sql.toString(), hibernateParas, pageId, pageSize);
	}

	@Override
	public int updateBonusLogForDuiJiang(BonusLog bonusLog) {
		String sql = "UPDATE TMS_BONUS_LOG SET DUI_JIANG_STATUS = ?,SEND_BONUS_TIME = ?,ERROR_CODE=?,ERROR_MSG=? WHERE TICKET_ID = ? ";
		return jdbcTemplate.update(sql, new Object[] { bonusLog.getDuiJiangStatus(), new Date(), bonusLog.getErrorCode(), bonusLog.getErrorMsg(), bonusLog.getTicketId() });
	}
}
