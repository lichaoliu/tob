package com.cndym.service;

import com.cndym.bean.user.Account;

import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:00
 */
public interface IAccountService extends IGenericeService<Account> {
	// 加减款
	public boolean doAccount(String userCode, String eventCode, double amount, String orderId, String sid, String type, String memo, String edit);

	// 扣款,返奖
	public boolean doAccount(String userCode, String eventCode, double amount, String orderId, String sid);

	// 退款
	public boolean doAccount(String userCode, String eventCode, double amount, String orderId, String reEventCode, String ticketId, String sid);

	public Map<String, Object> customSql(String sql, Object[] para);

	public Account getAccountBySid(String sid);

    public Account getAccountByUserCode(String userCode);
}
