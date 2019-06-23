package com.cndym.service;

import com.cndym.bean.count.SaleTable;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 上午11:51
 */
public interface ISaleTableService extends IGenericeService<SaleTable> {

    public void countSaleTable(String lotteryCode, String issue);

    public PageBean getPageBeanByPara(SaleTable saleTable, Integer page, Integer pageSize);

    public PageBean getPageBeanListByPara(SaleTable saleTable, Integer page, Integer pageSize);

}
