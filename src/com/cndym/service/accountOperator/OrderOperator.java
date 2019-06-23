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
import java.util.Map;

/**
 * User: 邓玉明
 * Date: 11-4-5 下午9:33
 */
@Repository
public class OrderOperator extends BaseAccountOperator {
    @Resource
    private IAccountLogDao accountLogDao;
    @Resource
    private IAccountDao accountDao;


    @Override
    public boolean execute(Account account, double amount, String orderId, String sid) {
        final String eventCode = Constants.P10000;
        double bonusAmount = 0d;
        double reChargeAmount = 0d;
        double presentAmount = 0d;
        double bonusAmountPay = 0d;
        double reChargeAmountPay = 0d;
        double presentAmountPay = 0d;
        double tempAmount = amount;
        if (account.getBonusAmount() + account.getRechargeAmount() + account.getPresentAmount() < tempAmount) {
            throw new CndymException(ErrCode.E8004);
        }

        if (account.getPresentAmount() > 0) {
            if (account.getPresentAmount() >= tempAmount) {
                presentAmountPay = tempAmount;
                presentAmount = account.getPresentAmount() - tempAmount;
                tempAmount = 0d;
            } else {
                presentAmountPay = account.getPresentAmount();
                tempAmount = tempAmount - account.getPresentAmount();
                presentAmount = 0;
            }
        } else {
            presentAmount = account.getPresentAmount();
        }
        if (tempAmount > 0 && account.getRechargeAmount() > 0) {
            if (account.getRechargeAmount() >= tempAmount) {
                reChargeAmountPay = tempAmount;
                reChargeAmount = account.getRechargeAmount() - tempAmount;
                tempAmount = 0;
            } else {
                reChargeAmountPay = account.getRechargeAmount();
                tempAmount = tempAmount - account.getRechargeAmount();
                reChargeAmount = 0;
            }
        } else {
            reChargeAmount = account.getRechargeAmount();
        }
        if (tempAmount > 0 && account.getBonusAmount() > 0) {
            if (account.getBonusAmount() >= tempAmount) {
                bonusAmountPay = tempAmount;
                bonusAmount = account.getBonusAmount() - tempAmount;
                tempAmount = 0;
            } else {
                bonusAmountPay = account.getBonusAmount();
                tempAmount = tempAmount - account.getBonusAmount();
                bonusAmount = 0;
            }
        } else {
            bonusAmount = account.getBonusAmount();
        }

        if (tempAmount > 0) {
            throw new CndymException(ErrCode.E8114);
        }

        //更新新的金额到帐户
        account.setBonusAmount(bonusAmount);
        account.setRechargeAmount(reChargeAmount);
        account.setPresentAmount(presentAmount);
        //更新消费总金额
        account.setPayTotal(account.getPayTotal() + amount);
        //记录日志
        accountDao.update(account);
        AccountLog accountLogTo = new AccountLog();
        accountLogTo.setUserCode(account.getUserCode());
        accountLogTo.setBonusAmount(bonusAmountPay * -1);
        accountLogTo.setBonusAmountNew(account.getBonusAmount());
        AccountOperator accountOperator = AccountOperatorList.getAccountOperator(eventCode);
        accountLogTo.setEventType(accountOperator.getEventType());
        accountLogTo.setType(accountOperator.getType());
        accountLogTo.setEventCode(eventCode);
        accountLogTo.setFreezeAmount(Constants.DOUBLE_ZERO);
        accountLogTo.setFreezeAmountNew(account.getFreezeAmount());
        accountLogTo.setOrderId(orderId);
        accountLogTo.setSid(sid);
        accountLogTo.setIndex(eventCode + "." + sid + "." + orderId);
        accountLogTo.setPresentAmount(presentAmountPay * -1);
        accountLogTo.setPresentAmountNew(account.getPresentAmount());
        accountLogTo.setMemo(Constants.ORDER_SUCCESS_MEMO);
        accountLogTo.setCreateTime(new Date());
        accountLogTo.setRechargeAmount(reChargeAmountPay * -1);
        accountLogTo.setRechargeAmountNew(account.getRechargeAmount());
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
