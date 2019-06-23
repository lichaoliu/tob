package com.cndym.service;

import com.cndym.bean.user.AdjustAccount;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:00
 */
public interface IAdjustAccountService extends IGenericeService<AdjustAccount> {
    public Map<String, Object> customSql(String sql, Object[] para);

    public int doAddAdjust(String sid, String userCode, double amount, String type, String body, String adminName);

    public PageBean getAdjustAccountByAdjustId(String adjustId);

}
