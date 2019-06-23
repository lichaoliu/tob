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
 * 作者：邓玉明
 * 时间：11-4-6 上午10:38
 * QQ：757579248
 * email：cndym@163.com
 */
@Repository
public class BonusOperator extends BaseAccountOperator {
    @Resource
    private IAccountLogDao accountLogDao;
    @Resource
    private IAccountDao accountDao;

    @Override
    public boolean execute(Account account, double amount, String lotteryCodeKey, String sid) {
        final String eventCode = Constants.R00200;
        account.setBonusTotal(account.getBonusTotal() + amount);
        account.setBonusAmount(account.getBonusAmount() + amount);
        accountDao.update(account);

        AccountLog accountLogTo = new AccountLog();
        accountLogTo.setUserCode(account.getUserCode());
        accountLogTo.setBonusAmount(amount);
        accountLogTo.setBonusAmountNew(account.getBonusAmount());
        AccountOperator accountOperator = AccountOperatorList.getAccountOperator(eventCode);
        accountLogTo.setEventType(accountOperator.getEventType());
        accountLogTo.setType(accountOperator.getType());
        accountLogTo.setEventCode(eventCode);
        accountLogTo.setFreezeAmount(Constants.DOUBLE_ZERO);
        accountLogTo.setFreezeAmountNew(account.getFreezeAmount());
        accountLogTo.setPresentAmount(Constants.DOUBLE_ZERO);
        accountLogTo.setPresentAmountNew(account.getPresentAmount());
        accountLogTo.setMemo(Constants.BONUS_SUCCESS_MEMO);
        accountLogTo.setCreateTime(new Date());
        accountLogTo.setRechargeAmount(Constants.DOUBLE_ZERO);
        accountLogTo.setRechargeAmountNew(account.getRechargeAmount());
        accountLogTo.setOrderId(lotteryCodeKey);
        accountLogTo.setSid(sid);
        accountLogTo.setIndex(eventCode + "." + sid + "." + lotteryCodeKey);
        if (!accountLogDao.save(accountLogTo)) throw new CndymException(ErrCode.E8114);
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
