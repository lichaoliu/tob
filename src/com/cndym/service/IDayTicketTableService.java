package com.cndym.service;

import com.cndym.bean.count.DayTicketTable;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: mcs
 * Date: 12-11-26
 * Time: 下午4:30
 */
public interface IDayTicketTableService extends IGenericeService<DayTicketTable> {

    public void countTicketTable();

    public PageBean getPageBeanByPara(DayTicketTable dayTicketTable, Integer page, Integer pageSize);
}
