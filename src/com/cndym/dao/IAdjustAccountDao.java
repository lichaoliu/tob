package com.cndym.dao;

import com.cndym.bean.user.AdjustAccount;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:24
 */
public interface IAdjustAccountDao extends IGenericDao<AdjustAccount> {
    public Map<String, Object> customSql(String sql, Object[] para);

    public int updateAjustAccount(AdjustAccount adjustAccount);

    public PageBean getAdjustAccountByAdjustId(String adjustId);

    public boolean isSendAmountByUser(String userCode, String backup1);
}
