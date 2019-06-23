package com.cndym.service;

import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.user.AccountLog;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午11:00
 */
public interface IAccountLogService extends IGenericeService<AccountLog> {

    public Map<String, Object> customSql(String sql, Object[] para);

    public PageBean getAccountLogMemberList(AccountLogMember account, Integer page, Integer pageSize);

    public List getAccountLogAmountCount(AccountLogMember accountLogMember);

    public PageBean getAccountLogForAdjust(AccountLogMember account, Integer page, Integer pageSize);

    public List getAccountLogAmountCountForAdjust(AccountLogMember accountLogMember);

}
