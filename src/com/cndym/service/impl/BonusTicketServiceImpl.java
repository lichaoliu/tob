package com.cndym.service.impl;

import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.dao.IBonusTicketDao;
import com.cndym.service.IBonusTicketService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BonusTicketServiceImpl extends GenericServiceImpl<BonusTicket, IBonusTicketDao> implements IBonusTicketService {
    @Autowired
    private IBonusTicketDao bonusTicketDao;

    @Override
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, int page, int pageSize) {
        return bonusTicketDao.getBounsTicketByIssue(sid, lotteryCode, issue, page, pageSize);
    }

    @Override
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, String sn, int page, int pageSize) {
        return bonusTicketDao.getBounsTicketByIssue(sid, lotteryCode, issue, sn, page, pageSize);
    }

    @Override
    public PageBean getPageBeanByPara(BonusTicketQueryBean queryBean, Integer page, Integer pageSize) {
        return bonusTicketDao.getPageBeanByPara(queryBean, page, pageSize);
    }

    @Override
    public Map<String, Object> getBonusTicketCount(BonusTicketQueryBean queryBean) {
        return bonusTicketDao.getBonusTicketCount(queryBean);
    }

}
