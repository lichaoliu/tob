package com.cndym.service.impl;

import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.user.AccountLog;
import com.cndym.dao.IAccountLogDao;
import com.cndym.service.IAccountLogService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class AccountLogServiceImpl extends GenericServiceImpl<AccountLog, IAccountLogDao> implements IAccountLogService {
    @Autowired
    private IAccountLogDao accountLogDao;


    @Override
    public Map<String, Object> customSql(String sql, Object[] para) {
        return accountLogDao.customSql(sql, para);
    }

    @Override
    public PageBean getAccountLogMemberList(AccountLogMember account, Integer page, Integer pageSize) {
        return accountLogDao.getAccountLogMemberList(account, page, pageSize);
    }

    @Override
    public List getAccountLogAmountCount(AccountLogMember accountLogMember) {
        return accountLogDao.getAccountLogAmountCount(accountLogMember);
    }

    @Override
    public PageBean getAccountLogForAdjust(AccountLogMember account, Integer page, Integer pageSize) {
        return accountLogDao.getAccountLogForAdjust(account,page,pageSize);
    }

    @Override
    public List getAccountLogAmountCountForAdjust(AccountLogMember accountLogMember) {
       return accountLogDao.getAccountLogAmountCountForAdjust(accountLogMember);
    }

    @Override
    public boolean saveAllObject(List list) {
        return accountLogDao.saveAllObject(list);
    }


}
