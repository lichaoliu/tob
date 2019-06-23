package com.cndym.service.impl;

import com.cndym.bean.tms.TicketReCode;
import com.cndym.dao.ITicketDao;
import com.cndym.dao.ITicketReCodeDao;
import com.cndym.service.ITicketReCodeService;
import com.cndym.service.ITicketService;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * User: mcs
 * Date: 12-10-28
 * Time: 下午2:36
 */
@Service
public class TicketReCodeServiceImpl extends GenericServiceImpl<TicketReCode, ITicketReCodeDao> implements ITicketReCodeService {

    @Autowired
    private ITicketReCodeDao ticketReCodeDao;

    @Autowired
    private ITicketService ticketService;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(ticketReCodeDao);
    }

    @Override
    public PageBean getPageBeanForIssueUpdate(String lotteryCode, String issue, String matchId, Integer page, Integer pageSize) {
        return ticketReCodeDao.getPageBeanForIssueUpdate(lotteryCode, issue, matchId, page, pageSize);
    }

    @Override
    public int updateTicketForIssueUpdate(String lotteryCode, String issue, String sn, Date date) {
        int page = 1, pageSize = 100;
        int PageTotal;
        PageBean pageBean = getPageBeanForIssueUpdate(lotteryCode, issue, sn, page, pageSize);
        PageTotal = pageBean.getPageTotal();
        List<TicketReCode> programsReCodes = pageBean.getPageContent();
        for (TicketReCode ticketReCode : programsReCodes) {
            ticketService.updateForSubIssueUpdate(ticketReCode.getTicketId(), ticketReCode.getMatchId(), date);
        }
        page++;
        while (page <= PageTotal) {
            PageBean pageBeanA = getPageBeanForIssueUpdate(lotteryCode, issue, sn, page, pageSize);
            List<TicketReCode> programsReCodesA = pageBeanA.getPageContent();
            for (TicketReCode ticketReCode : programsReCodesA) {
                ticketService.updateForSubIssueUpdate(ticketReCode.getTicketId(), ticketReCode.getMatchId(), date);
            }
            page++;
        }
        return 0;
    }
}
