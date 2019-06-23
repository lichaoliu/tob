package com.cndym.service;

import com.cndym.bean.tms.Ticket;
import com.cndym.bean.tms.TicketReCode;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;

/**
 * User: mcs
 * Date: 12-10-28
 * Time: 下午2:36
 */
public interface ITicketReCodeService extends IGenericeService<TicketReCode> {

    public PageBean getPageBeanForIssueUpdate(String lotteryCode, String issue, String matchId, Integer page, Integer pageSize);


    public int updateTicketForIssueUpdate(String lotteryCode, String issue, String sn, Date date);

}

