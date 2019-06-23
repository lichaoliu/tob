package com.cndym.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.adapter.tms.bean.MatchTimeInfo;
import com.cndym.admin.dao.IBackTicketDao;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.tms.TicketReCode;
import com.cndym.dao.ITicketReCodeDao;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.service.impl.GenericServiceImpl;
import com.cndym.service.subIssue.ISubIssueOperator;
import com.cndym.service.subIssue.bean.SubIssueComm;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.hibernate.PageBean;

@Service
public class BackTicketServiceImpl extends GenericServiceImpl<Ticket, IBackTicketDao> implements IBackTicketService {
    @Autowired
    private IBackTicketDao ticketDao;
    @Autowired
    private ITicketReCodeDao ticketReCodeDao;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(ticketDao);
    }

    public void setTicketReCode(Ticket ticket) {
        ISubIssueOperator subIssueOperator = (ISubIssueOperator) SpringUtils.getBean("comm" + ticket.getLotteryCode() + "SubIssueOperator");
        String numberInfo = ticket.getNumberInfo();
        String[] arr = numberInfo.split("\\|");
        if (arr.length != 2) {
            throw new CndymException(ErrCode.E8111);
        }
        String[] matchIdArr = arr[0].split(";");
        String guoGuan = arr[1];
        String pollCode = ticket.getPollCode();
        if (guoGuan.equals("1*1")) {
            pollCode = "01";
        }

        List<MatchTimeInfo> matchList = new ArrayList<MatchTimeInfo>();
        for (String number : matchIdArr) {
            String[] subArr = number.split(":");
            if ((!"10".equals(ticket.getPlayCode()) && subArr.length != 2) || ("10".equals(ticket.getPlayCode()) && subArr.length != 3)) {
                throw new CndymException(ErrCode.E8111);
            }
            MatchTimeInfo matchTimeInfo = new MatchTimeInfo();
            String palyCode = "";
            if ("10".equals(ticket.getPlayCode())) {
                palyCode = subArr[1];
            } else {
                palyCode = ticket.getPlayCode();
            }
            SubIssueComm subIssueComm = subIssueOperator.getSubIssueComm(ticket.getIssue(), subArr[0], palyCode, pollCode);
            matchTimeInfo.setMatchId(subArr[0]);
            matchTimeInfo.setIssue(subIssueComm.getIssue());
            matchTimeInfo.setTime(subIssueComm.getEndTime());
            matchTimeInfo.setDanShiEndTime(subIssueComm.getDanShiEndTime());
            matchTimeInfo.setFuShiEndTime(subIssueComm.getFuShiEndTime());
            matchList.add(matchTimeInfo);

            TicketReCode ticketReCode = new TicketReCode();
            ticketReCode.setTicketId(ticket.getTicketId());
            ticketReCode.setLotteryCode(ticket.getLotteryCode());
            ticketReCode.setIssue(ticket.getIssue());
            ticketReCode.setMatchId(matchTimeInfo.getMatchId());
            ticketReCode.setEndTime(matchTimeInfo.getTime());
            ticketReCode.setCreateTime(new Date());

            ticket.setPollCode(subIssueComm.getPollCode());
            ticketReCodeDao.save(ticketReCode);
        }
        Collections.sort(matchList);
        MatchTimeInfo start = matchList.get(0);
        if (start.getFuShiEndTime().getTime() <= new Date().getTime()) {
            throw new CndymException(ErrCode.E8118);
        }
        MatchTimeInfo end = matchList.get(matchList.size() - 1);
        ticket.setStartGameId(start.getMatchId());
        ticket.setEndGameId(end.getMatchId());
        ticket.setGameStartTime(start.getTime());
        ticket.setGameEndTime(end.getTime());
    }
    
    @Override
    public PageBean getPageBeanByPara(TicketQueryBean queryBean, Integer page, Integer pageSize) {
        return ticketDao.getPageBeanByPara(queryBean, page, pageSize);
    }

    @Override
    public PageBean getPageBeanByParaForNoSend(TicketQueryBean queryBean, Integer page, Integer pageSize) {
        return ticketDao.getPageBeanByParaForNoSend(queryBean, page, pageSize);
    }

    @Override
    public Map<String, Object> getTicketCount(TicketQueryBean queryBean) {
        return ticketDao.getTicketCount(queryBean);
    }

    @Override
    public Map<String, Object> getTicketCountNoSend(TicketQueryBean queryBean) {
        return ticketDao.getTicketCountNoSend(queryBean);
    }
    
    @Override
    public List<Map<String, Object>> findNoSendTickets(Date createStartTime, Date createEndTime, String postCode) {
        return ticketDao.findNoSendTickets(createStartTime, createEndTime, postCode);
    }
    
    @Override
	public List<Map<String, Object>> findJcNoSendTickets(Date createStartTime, Date createEndTime, String postCode) {
		return ticketDao.findJcNoSendTickets(createStartTime, createEndTime, postCode);
	}
    
    @Override
    public PageBean getPageBeanByPara(Ticket ticket, Integer page, Integer pageSize) {
        return ticketDao.getPageBeanByPara(ticket, page, pageSize);
    }
}
