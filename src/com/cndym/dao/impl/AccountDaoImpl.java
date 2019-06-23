package com.cndym.dao.impl;

import com.cndym.bean.user.Account;
import com.cndym.dao.IAccountDao;
import com.cndym.dao.impl.rowMapperBean.AccountRowMapper;
import com.cndym.utils.Utils;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:25
 */
@Repository
public class AccountDaoImpl extends GenericDaoImpl<Account> implements IAccountDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource
	private SessionFactory sessionFactoryTemp;

	@PostConstruct
	private void sessionFactoryInit() {
		super.setSessionFactory(sessionFactoryTemp);
	}

	@Override
	public Account getAccountByUserCode(String userCode) {
		List<Account> list = find("From Account where userCode=?", new Object[] { userCode });
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 根据sid取账户信息
	 * 
	 * @param sid
	 * @return
	 */
	@Override
	public Account getAccountBySid(String sid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.* from user_account u inner join user_member um on u.user_code = um.user_code ");
		sql.append("where um.sid = '" + sid + "'");

		List<Account> accountList = jdbcTemplate.query(sql.toString(), ParameterizedBeanPropertyRowMapper.newInstance(Account.class));
		if (Utils.isNotEmpty(accountList)) {
			return accountList.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> customSql(String sql, Object[] para) {
		return jdbcTemplate.queryForMap(sql, para);
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

	@Override
	public Account getAccountByUseCodeForUpdate(String userCode) {
		String sql = "select * from user_account a where a.user_code=? for update";
		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { userCode }, new AccountRowMapper());
		} catch (Exception e) {
			return null;
		}
	}
}
