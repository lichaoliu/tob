package com.cndym.dao;

import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.utils.hibernate.PageBean;

import java.util.Map;

public interface IBonusTicketDao extends IGenericDao<BonusTicket> {

    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, int page, int pageSize);

    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, String sn, int page, int pageSize);

    public PageBean getPageBeanByPara(BonusTicketQueryBean queryBean, Integer page, Integer pageSize);

    public Map<String, Object> getBonusTicketCount(BonusTicketQueryBean queryBean);
}
