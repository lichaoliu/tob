package com.cndym.service.impl;

import com.cndym.accountOperator.AccountOperatorList;
import com.cndym.bean.user.Account;
import com.cndym.dao.IAccountDao;
import com.cndym.service.IAccountService;
import com.cndym.service.accountOperator.IAccountOperator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, IAccountDao> implements IAccountService {
	@Autowired
	private IAccountDao accountDao;
	private Logger logger = Logger.getLogger(getClass());

	@PostConstruct
	public void initDao() {
		super.setDaoImpl(accountDao);
	}

	@Override
	public Map<String, Object> customSql(String sql, Object[] para) {
		return null;
	}

	@Override
	public Account getAccountByUserCode(String userCode) {
		return accountDao.getAccountByUserCode(userCode);
	}

	@Override
	public boolean doAccount(String userCode, String eventCode, double amount, String orderId, String sid, String type, String memo, String edit) {
		if (amount <= 0) {
			logger.info("帐户操作(" + eventCode + ")传入金额(" + amount + ")小于等于0");
			return false;
		}
		Account account = accountDao.getAccountByUseCodeForUpdate(userCode);
		if (null != account) {
			IAccountOperator accountOperator = AccountOperatorList.getOperatorService(eventCode);
			if (null == accountOperator) {
				logger.error(eventCode + "没有找到对应的处理类");
			} else {
				return accountOperator.execute(account, amount, orderId, sid, type, memo, eventCode);
			}
		}
		return false;
	}

	@Override
	public boolean doAccount(String userCode, String eventCode, double amount, String orderId, String sid) {
		if (amount <= 0) {
			logger.info("帐户操作(" + eventCode + ")传入金额(" + amount + ")小于等于0");
			return false;
		}
		Account account = accountDao.getAccountByUseCodeForUpdate(userCode);
		if (null != account) {
			IAccountOperator accountOperator = AccountOperatorList.getOperatorService(eventCode);
			if (null == accountOperator) {
				logger.error(eventCode + "没有找到对应的处理类");
			} else {
				return accountOperator.execute(account, amount, orderId, sid);
			}
		}
		return false;
	}

	@Override
	public boolean doAccount(String userCode, String eventCode, double amount, String orderId, String reEventCode, String ticketId, String sid) {
		if (amount <= 0) {
			logger.info("帐户操作(" + eventCode + ")传入金额(" + amount + ")小于等于0");
			return false;
		}
		Account account = accountDao.getAccountByUseCodeForUpdate(userCode);
		if (null != account) {
			IAccountOperator accountOperator = AccountOperatorList.getOperatorService(eventCode);
			if (null == accountOperator) {
				logger.error(eventCode + "没有找到对应的处理类");
			} else {
				return accountOperator.execute(account, amount, orderId, reEventCode, ticketId, sid);
			}
		}
		return false;
	}

	@Override
	public Account getAccountBySid(String sid) {
		return accountDao.getAccountBySid(sid);
	}

	public IAccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

}
