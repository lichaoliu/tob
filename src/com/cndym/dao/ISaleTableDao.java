package com.cndym.dao;

import com.cndym.bean.count.SaleTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 上午11:48
 */
public interface ISaleTableDao extends IGenericDao<SaleTable> {

    public List<Map<String, Object>> customSql(String sql, Object[] para);


    public SaleTable getSaleTableByLotteryAndIssue(String sid, String lotteryCode, String issue);

    public PageBean getPageBeanByPara(SaleTable saleTable, Integer page, Integer pageSize);

    public PageBean getPageBeanListByPara(SaleTable saleTable, Integer page, Integer pageSize);
}
