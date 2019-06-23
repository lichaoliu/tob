package com.cndym.dao;

import com.cndym.bean.tms.TicketReCode;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: mcs
 * Date: 12-10-28
 * Time: 下午2:34
 */
public interface ITicketReCodeDao extends IGenericDao<TicketReCode> {

    public PageBean getPageBeanForIssueUpdate(String lotteryCode, String issue, String matchId, Integer page, Integer pageSize);

}
