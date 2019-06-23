package com.cndym.dao;

import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.user.AccountLog;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:26
 */
public interface IAccountLogDao extends IGenericDao<AccountLog> {

    public AccountLog getAccountLogByOrderIdEventCode(String orderId, String sid, String eventCode);

    public AccountLog getAccountLogByOrderIdEventCodeForUpdate(String orderId, String sid, String eventCode);

    public double countAccountLogAmount(String orderId, String sid, String eventCode);

    public Map<String, Object> customSql(String sql, Object[] para);

    public PageBean getAccountLogMemberList(AccountLogMember account, Integer page, Integer pageSize);

    public List getAccountLogAmountCount(AccountLogMember accountLogMember);

    public List getAccountLogAmountCountForAdjust(AccountLogMember accountLogMember);

    public PageBean getAccountLogForAdjust(AccountLogMember account, Integer page, Integer pageSize);

}
