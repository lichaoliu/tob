package com.cndym.dao;

import com.cndym.bean.count.DayTicketTable;
import com.cndym.bean.count.TicketTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-11-26
 * Time: 下午4:27
 */
public interface IDayTicketTableDao extends IGenericDao<DayTicketTable> {

    public List<Map<String, Object>> customSql(String sql, Object[] para);

    public PageBean getPageBeanByPara(DayTicketTable dayTicketTable, Integer page, Integer pageSize);

}
