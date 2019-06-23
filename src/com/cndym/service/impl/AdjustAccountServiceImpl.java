package com.cndym.service.impl;

import com.cndym.bean.user.AdjustAccount;
import com.cndym.constant.Constants;
import com.cndym.dao.IAdjustAccountDao;
import com.cndym.service.IAccountService;
import com.cndym.service.IAdjustAccountService;
import com.cndym.utils.OrderIdBuildUtils;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class AdjustAccountServiceImpl extends GenericServiceImpl<AdjustAccount, IAdjustAccountDao> implements IAdjustAccountService {
    @Autowired
    private IAdjustAccountDao adjustAccountDao;
    @Resource
    private IAccountService accountService;

    private static final String BONUS_AMOUNT = "01";
    private static final String RECHARGE_AMOUNT = "02";
    private static final String PRESENT_AMOUNT = "03";
    private static final String FREEZE_AMOUNT = "04";

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(adjustAccountDao);
    }


    @Override
    public Map<String, Object> customSql(String sql, Object[] para) {
        return null;
    }

    public IAdjustAccountDao getAdjustAccountDao() {
        return adjustAccountDao;
    }

    public void setAdjustAccountDao(IAdjustAccountDao adjustAccountDao) {
        this.adjustAccountDao = adjustAccountDao;
    }

    public boolean save(AdjustAccount adjustAccount) {
        return adjustAccountDao.save(adjustAccount);
    }

    @Override
    public int doAddAdjust(String sid, String userCode, double amount, String type, String body, String adminName) {
        AdjustAccount adjustAccount = new AdjustAccount();
        adjustAccount.setBody(body);
        adjustAccount.setBonusAmount(Constants.DOUBLE_ZERO);
        adjustAccount.setPresentAmount(Constants.DOUBLE_ZERO);
        adjustAccount.setRechargeAmount(Constants.DOUBLE_ZERO);
        adjustAccount.setFreezeAmount(Constants.DOUBLE_ZERO);
        adjustAccount.setCreateTime(new Date());
        adjustAccount.setUserCode(userCode);
        adjustAccount.setOperator(adminName);
        adjustAccount.setAdjustId(OrderIdBuildUtils.buildDiyCountOrderId());
        if (BONUS_AMOUNT.equals(type)) {
            adjustAccount.setBonusAmount(amount);
        }
        if (RECHARGE_AMOUNT.equals(type)) {
            adjustAccount.setRechargeAmount(amount);
        }
        if (PRESENT_AMOUNT.equals(type)) {
            adjustAccount.setPresentAmount(amount);
        }
        if (FREEZE_AMOUNT.equals(type)) {
            adjustAccount.setFreezeAmount(amount);
        }
        //save(adjustAccount);
        adjustAccountDao.save(adjustAccount);
        boolean end = false;
        if (amount > 0) {
            end = accountService.doAccount(userCode, Constants.R00500, amount, adjustAccount.getAdjustId(), sid, type, body, body);
        } else {
            end = accountService.doAccount(userCode, Constants.P10400, amount * -1, adjustAccount.getAdjustId(), sid, type, body, body);
        }
        if (end) {
            return 1;
        } else {
            return 0;
        }
    }

    public IAccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public PageBean getAdjustAccountByAdjustId(String adjustId) {
        return adjustAccountDao.getAdjustAccountByAdjustId(adjustId);
    }
}
