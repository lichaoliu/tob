package com.cndym.service;

import com.cndym.bean.count.DaySaleTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

/**
 * User: mcs
 * Date: 12-11-26
 * Time: 下午4:03
 */
public interface IDaySaleTableService extends IGenericeService<DaySaleTable> {

    public void countSaleTable();

    public PageBean getPageBeanByPara(DaySaleTable daySaleTable, Integer page, Integer pageSize);

    public Map<String,Object> getdaySaleTableCount(DaySaleTable daySaleTable);

}
