package com.cndym.dao;

import com.cndym.bean.count.AccountTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午2:15
 */
public interface IAccountTableDao extends IGenericDao<AccountTable> {

    public List<Map<String, Object>> customListSql(String sql, Object[] para);

    public Map<String, Object> customSql(String sql, Object[] para);

    public PageBean getPageBeanByPara(AccountTable accountTable, Integer page, Integer pageSize);

    public Map<String, Object> getAccountTableCount(AccountTable accountTable);
}
