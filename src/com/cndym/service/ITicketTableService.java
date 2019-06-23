package com.cndym.service;

import com.cndym.bean.count.TicketTable;
import com.cndym.bean.tms.Ticket;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午7:26
 */
public interface ITicketTableService extends IGenericeService<TicketTable> {

    public void countTicketTable(String lotteryCode, String issue);

    public PageBean getPageBeanByPara(TicketTable ticketTable, Integer page, Integer pageSize);

}
