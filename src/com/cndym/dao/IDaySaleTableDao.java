package com.cndym.dao;

import com.cndym.bean.count.DaySaleTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-11-26
 * Time: 下午4:06
 */
public interface IDaySaleTableDao extends IGenericDao<DaySaleTable> {

    public List<Map<String, Object>> customSql(String sql, Object[] para);

    public PageBean getPageBeanByPara(DaySaleTable daySaleTable, Integer page, Integer pageSize);

    public Map<String, Object> getdaySaleTableCount(DaySaleTable daySaleTable);
}
