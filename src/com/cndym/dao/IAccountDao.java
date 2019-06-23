package com.cndym.dao;

import com.cndym.bean.user.Account;

import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:24
 */
public interface IAccountDao extends IGenericDao<Account> {
	public Account getAccountByUserCode(String userCode);

	public Account getAccountByUseCodeForUpdate(String userCode);

	public Map<String, Object> customSql(String sql, Object[] para);

	public Account getAccountBySid(String sid);
}
