package com.cndym.dao.impl;

import com.cndym.bean.tms.MatchEditLog;
import com.cndym.dao.IMatchEditLogDao;
import com.cndym.dao.impl.rowMapperBean.MatchEditLogRowMapper;
import com.cndym.utils.Utils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: MengJingyi QQ: 116741034 Date: 14-2-13 Time: 上午10:51 To change this template use File | Settings | File Templates.
 */
@Repository
public class MatchEditLogDaoImpl extends GenericDaoImpl<MatchEditLog> implements IMatchEditLogDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SessionFactory sessionFactoryTemp;

	@PostConstruct
	private void sessionFactoryInit() {
		super.setSessionFactory(sessionFactoryTemp);
	}

	@Override
	public List<MatchEditLog> getMatchEditLogsByMsg(MatchEditLog matchEditLog) {
		StringBuffer sqlStr = new StringBuffer("from MatchEditLog where 1=1 ");
		List<Object> param = new ArrayList<Object>();
		if (Utils.isNotEmpty(matchEditLog)) {
			if (Utils.isNotEmpty(matchEditLog.getLotteryCode())) {
				sqlStr.append(" and lotteryCode=?");
				param.add(matchEditLog.getLotteryCode());
			}
			if (Utils.isNotEmpty(matchEditLog.getSn())) {
				sqlStr.append(" and sn=?");
				param.add(matchEditLog.getSn());
			}
			if (Utils.isNotEmpty(matchEditLog.getIssue())) {
				sqlStr.append(" and issue=?");
				param.add(matchEditLog.getIssue());
			}
		}
		return find(sqlStr.toString(), param.toArray());
	}

	@Override
	public MatchEditLog getMatchEditLog(String lotteryCode, String playCode, String pollCode, String issue, String sn) {
		StringBuffer sqlStr = new StringBuffer("from MatchEditLog where 1=1 ");
		List<Object> param = new ArrayList<Object>();

		if (Utils.isNotEmpty(lotteryCode)) {
			sqlStr.append(" and lotteryCode=?");
			param.add(lotteryCode);
		}
		if (Utils.isNotEmpty(playCode)) {
			sqlStr.append(" and playCode=?");
			param.add(playCode);
		}
		if (Utils.isNotEmpty(pollCode)) {
			sqlStr.append(" and pollCode=?");
			param.add(pollCode);
		}
		if (Utils.isNotEmpty(sn)) {
			sqlStr.append(" and sn=?");
			param.add(sn);
		}
		if (Utils.isNotEmpty(issue)) {
			sqlStr.append(" and issue=?");
			param.add(issue);
		}

		List<MatchEditLog> matchEditLogList = find(sqlStr.toString(), param.toArray());
		if (Utils.isNotEmpty(matchEditLogList)) {
			return matchEditLogList.get(0);
		}

		return null;
	}

	@Override
	public void delEditLogsByMsg(String lotteryCode, String issue, String sn) {
		String sql = "delete from tms_macth_edit_log where lottery_code='" + lotteryCode + "' and issue='" + issue + "' and sn='" + sn + "'";
		jdbcTemplate.execute(sql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public SessionFactory getSessionFactoryTemp() {
		return sessionFactoryTemp;
	}

	public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
		this.sessionFactoryTemp = sessionFactoryTemp;
	}
}
