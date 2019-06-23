package com.cndym.dao;

import com.cndym.bean.count.TicketTable;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午7:23
 */
public interface ITicketTableDao extends IGenericDao<TicketTable> {

    public List<Map<String, Object>> customListSql(String sql, Object[] para);

    public Map<String, Object> customSql(String sql, Object[] para);

    public PageBean getPageBeanByPara(TicketTable ticketTable, Integer page, Integer pageSize);

}
