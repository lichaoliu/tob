package com.cndym.service.accountOperator;

import com.cndym.accountOperator.AccountOperator;
import com.cndym.accountOperator.AccountOperatorList;
import com.cndym.bean.user.Account;
import com.cndym.bean.user.AccountLog;
import com.cndym.constant.Constants;
import com.cndym.dao.IAccountDao;
import com.cndym.dao.IAccountLogDao;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 作者：邓玉明 时间：11-7-7 下午10:26 QQ：757579248 email：cndym@163.com
 */
@Repository
public class EditAccountOperator extends BaseAccountOperator {
	@Resource
	private IAccountLogDao accountLogDao;
	@Resource
	private IAccountDao accountDao;

	private static final String BONUS_AMOUNT = "01";
	private static final String RECHARGE_AMOUNT = "02";
	private static final String PRESENT_AMOUNT = "03";
	private static final String FREEZE_AMOUNT = "04";

	@Override
	public boolean execute(Account account, double amount, String orderId, String sid, String type, String memo, String eventCode) {

		if (Constants.P10400.equals(eventCode)) {
			amount = amount * -1;
		}

		AccountLog accountLogTo = new AccountLog();
		accountLogTo.setUserCode(account.getUserCode());
		AccountOperator accountOperator = AccountOperatorList.getAccountOperator(eventCode);
		accountLogTo.setEventType(accountOperator.getEventType());
		accountLogTo.setType(accountOperator.getType());
		accountLogTo.setEventCode(eventCode);
		accountLogTo.setOrderId(orderId);
		accountLogTo.setBonusAmount(Constants.DOUBLE_ZERO);
		accountLogTo.setFreezeAmount(Constants.DOUBLE_ZERO);
		accountLogTo.setPresentAmount(Constants.DOUBLE_ZERO);
		accountLogTo.setRechargeAmount(Constants.DOUBLE_ZERO);
		if (BONUS_AMOUNT.equals(type)) {
			account.setBonusAmount(account.getBonusAmount() + amount);
			if (account.getBonusAmount() < 0) {
				throw new CndymException(ErrCode.E8115);
			}
			account.setBonusTotal(account.getBonusTotal() + amount);
			accountLogTo.setBonusAmount(amount);
		}
		if (RECHARGE_AMOUNT.equals(type)) {
			account.setRechargeAmount(account.getRechargeAmount() + amount);
			if (account.getRechargeAmount() < 0) {
				throw new CndymException(ErrCode.E8115);
			}
			account.setRechargeTotal(account.getRechargeTotal() + amount);
			accountLogTo.setRechargeAmount(amount);
		}
		if (PRESENT_AMOUNT.equals(type)) {
			account.setPresentAmount(account.getPresentAmount() + amount);
			if (account.getPresentAmount() < 0) {
				throw new CndymException(ErrCode.E8115);
			}
			account.setPresentTotal(account.getPresentTotal() + amount);
			accountLogTo.setPresentAmount(amount);
		}
		if (FREEZE_AMOUNT.equals(type)) {
			account.setFreezeAmount(account.getFreezeAmount() + amount);
			if (account.getFreezeAmount() < 0) {
				throw new CndymException(ErrCode.E8115);
			}
			accountLogTo.setFreezeAmount(amount);
		}
		accountLogTo.setBonusAmountNew(account.getBonusAmount());
		accountLogTo.setFreezeAmountNew(account.getFreezeAmount());
		accountLogTo.setPresentAmountNew(account.getPresentAmount());
		accountLogTo.setRechargeAmountNew(account.getRechargeAmount());
		accountLogTo.setOrderId(orderId);
		accountLogTo.setSid(sid);
		accountLogTo.setIndex(orderId);
		accountLogTo.setMemo(memo);
		accountLogTo.setBackup1(memo);
		accountLogTo.setCreateTime(new Date());
		accountDao.update(account);
		if (!accountLogDao.save(accountLogTo))
			throw new CndymException(ErrCode.E8201);
		return true;
	}

	public IAccountLogDao getAccountLogDao() {
		return accountLogDao;
	}

	public void setAccountLogDao(IAccountLogDao accountLogDao) {
		this.accountLogDao = accountLogDao;
	}

	public IAccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}
}
