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
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: 邓玉明 Date: 11-3-28 上午1:12
 */
@Repository
public class OrderFailureOperator extends BaseAccountOperator {

	@Resource
	private IAccountLogDao accountLogDao;
	@Resource
	private IAccountDao accountDao;
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 投注失败退款(R00100)
	 * 
	 * @param account 退款帐户
	 * @param amount
	 * @param orderId
	 * @return
	 */
	@Override
	public boolean execute(Account account, double amount, String orderId, String reEventCode, String ticketId, String sid) {
		final String eventCode = Constants.R00100;
		// 找到要退款的那条扣款记录
		AccountLog accountLog = accountLogDao.getAccountLogByOrderIdEventCodeForUpdate(orderId, sid, reEventCode);
		logger.info("对订单(" + orderId + ")进行退款操作");
		if (null == accountLog) {
			throw new CndymException(ErrCode.E8201);
		}

		double reAmount = accountLogDao.countAccountLogAmount(orderId, sid, eventCode);

		double tempAmount = amount;
		double bonusAmount = 0d;
		double reChargeAmount = 0d;
		double presentAmount = 0d;
		// 先退奖金
		double logBonusAmount = accountLog.getBonusAmount() * -1;
		double logRechargeAmount = accountLog.getRechargeAmount() * -1;
		double logPresentAmount = accountLog.getPresentAmount() * -1;

		if ((reAmount + amount) > (logBonusAmount + logRechargeAmount + logPresentAmount)) {
			throw new CndymException(ErrCode.E8207);
		}

		if (logBonusAmount > 0) {
			if (logBonusAmount >= tempAmount) {
				bonusAmount = tempAmount;
				account.setBonusAmount(account.getBonusAmount() + tempAmount);
				tempAmount = 0;
			} else {
				account.setBonusAmount(account.getBonusAmount() + logBonusAmount);
				bonusAmount = logBonusAmount;
				tempAmount = tempAmount - logBonusAmount;
			}
		}
		// 再退彩金
		if (logRechargeAmount > 0 && tempAmount > 0) {
			if (logRechargeAmount >= tempAmount) {
				reChargeAmount = tempAmount;
				account.setRechargeAmount(account.getRechargeAmount() + tempAmount);
				tempAmount = 0;
			} else {
				account.setRechargeAmount(account.getRechargeAmount() + logRechargeAmount);
				reChargeAmount = logRechargeAmount;
				tempAmount = tempAmount - logRechargeAmount;
			}
		}
		// 最后退赠送
		if (logPresentAmount > 0 && tempAmount > 0) {
			if (accountLog.getPresentAmount() >= tempAmount) {
				presentAmount = tempAmount;
				account.setPresentAmount(account.getPresentAmount() + tempAmount);
				tempAmount = 0;
			} else {
				account.setPresentAmount(account.getPresentAmount() + logPresentAmount);
				presentAmount = logPresentAmount;
				tempAmount = tempAmount - logPresentAmount;
			}
		}
		// 更新消费总金额
		account.setPayTotal(account.getPayTotal() - amount);
		// 记录日志

		accountDao.update(account);
		AccountLog accountLogTo = new AccountLog();
		accountLogTo.setUserCode(account.getUserCode());
		accountLogTo.setBonusAmount(bonusAmount);
		accountLogTo.setBonusAmountNew(account.getBonusAmount());
		AccountOperator accountOperator = AccountOperatorList.getAccountOperator(eventCode);
		accountLogTo.setEventType(accountOperator.getEventType());
		accountLogTo.setType(accountOperator.getType());
		accountLogTo.setEventCode(eventCode);
		accountLogTo.setFreezeAmount(Constants.DOUBLE_ZERO);
		accountLogTo.setFreezeAmountNew(account.getFreezeAmount());
		accountLogTo.setOrderId(orderId);
        accountLogTo.setTicketId(ticketId);
		accountLogTo.setSid(sid);
		accountLogTo.setPresentAmount(presentAmount);
		accountLogTo.setPresentAmountNew(account.getPresentAmount());
		accountLogTo.setMemo(Constants.ORDER_FAILURE_MEMO);
		accountLogTo.setCreateTime(new Date());
		accountLogTo.setIndex(eventCode + "." + sid + "." + ticketId);
		accountLogTo.setRechargeAmount(reChargeAmount);
		accountLogTo.setRechargeAmountNew(account.getRechargeAmount());
		if (!accountLogDao.save(accountLogTo))
			throw new CndymException(ErrCode.E8114);
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
