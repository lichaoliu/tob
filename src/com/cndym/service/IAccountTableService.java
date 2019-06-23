package com.cndym.service;

import com.cndym.bean.count.AccountTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午2:17
 */
public interface IAccountTableService extends IGenericeService<AccountTable> {

    public void countAccountTable();

    public PageBean getPageBeanByPara(AccountTable accountTable, Integer page, Integer pageSize);

    public Map<String,Object> getAccountTableCount(AccountTable accountTable);
}
