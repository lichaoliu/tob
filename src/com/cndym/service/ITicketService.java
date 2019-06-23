package com.cndym.service;

import com.cndym.adapter.tms.bean.MatchTimeInfo;
import com.cndym.adapter.tms.bean.NumberInfo;
import com.cndym.bean.query.TicketMainIssueBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.Ticket;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.XmlBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:00
 */
public interface ITicketService extends IGenericeService<Ticket> {
    // 出票回执处理
    public int doUpdateTicketForSended(Ticket ticket);

    // 返奖到帐户处理非竞技
    public int doBonusAmountToAccount(String lotteryCode, String issue);

    // 返奖到帐户处理竞技
    public int doBonusAmountToAccount(String lotteryCode, String issue, String sn);

    public void doSendTicket(String orderId, String sid, Integer ticketStatus);

    public Map<String, Object> doSaveTicket(XmlBean xmlBean, List<NumberInfo> numberInfoList, List<String> ticketIdList, Integer totalAmount);
    
    public Map<String, Object> doSaveTicket(XmlBean xmlBean, List<NumberInfo> numberInfoList, List<String> ticketIdList, Map<String, List<MatchTimeInfo>> matchTimeInfoMap, Integer totalAmount);

    public List<Ticket> getTicketByOutTicketId(List<String> ticketIdList, String sid);

    public List<Ticket> getTicketByOrderId(String orderId, String sid);

    /**
     * 查询代理商数字彩一期的中奖票
     *
     * @param sid
     * @param lotteryCode
     * @param issue
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getBounsTicketByIssue(String sid, String lotteryCode, String issue, int page, int pageSize);

    public int updateForSubIssueUpdate(String ticketId, String sn, Date date);

    public int updateBonusAmount(BonusLog bonusLog);

    public int doEndIssueOperator(String lotteryCode, String issue);

    public int doEndIssueOperator();

    public Ticket getTicketByTicketId(String ticketId);
    
    public Ticket getTicketById(long id);

    public Ticket getTicketByBackup1(String backup1, String postCode);

    public int updateSpInfo(String ticketId, String spInfo);

    public List<Map<String, Object>> getSendTicket(String lotteryCode, String issue);

    public List<Map<String, Object>> getSendTicket(String lotteryCode);

    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize);

    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize, String desc);

    public PageBean getSendedTicketOrderByIssue(String lotteryCode, String postCode, int page, int pageSize);
    
    public PageBean getSendedDelayTicket(String lotteryCode, String postCode,Date sendTime,Date createTime, int page, int pageSize);

    public int doOutBonus(String lotteryCode, String issue, List<BonusLog> bonusLogList);

    public int doNoBonus(String lotteryCode, String issue);

    public int doUpdateTicketForDuiJiang(Ticket ticket);

    public List<Ticket> getNoDuiJiangTicket(String lotteryCode, String postCode);

    public PageBean getPageBeanByTicket(TicketQueryBean ticketQueryBean, Integer page, Integer pageSize);

    public int updateSaleInfo(String ticketId, String saleInfo);

    public int doUpdateSendingToSuccess(String lotteryCode, String issue);

    public PageBean getTicketCountByDay(Map<String, Object> cond);

    public Ticket getTicketBySaleCode(String lotteryCode, String issue, String saleCode, String postCode);

    public int doHhandTicketFailed(String ticketId);

    public PageBean getSendedTicket(String postCode, int page, int pageSize);

    public int getTicketCountByIssue(String lotteryCode, String issue);

    public Map<String, Object> getTicketMainIssueCountByIssue(TicketMainIssueBean ticketMainIssueBean);

    public PageBean getSendedTicket(String lotteryCode, String playCode, String pollCode, String postCode, int page, int pageSize);

    public void doBonusToAccount(String ticketId, double amount);

    public void doReSendTicket(String ticketId);

    public PageBean getSendedTicketEx(String lotteryCode, String postCode, Date sendTime, int page, int pageSize);

    public PageBean getPageBeanByParaSending(Ticket ticket, Integer page, Integer pageSize);

    public PageBean getSendedTicketEXEX(String lotteryCode, String postCode, String issue, int page, int pageSize);

    public List<Map<String, Object>> findNoSendTicket(Integer ticketStatus, String postCode);

    public List<Ticket> getTicketsForEndIssue(String lotteryCode, String issue);

    public PageBean getPageBeanByParaLottery(TicketQueryBean queryBean);

    public PageBean getIssueLotteryCount(TicketMainIssueBean ticketMainIssueBean, Integer page, Integer pageSize);

    public int doHhandTicketFailedByTicketId(String ticketId);

    public int doHhandTicketSuccess(String ticketId);

    public List<Map<String, Object>> findNoSendTicketList(Integer ticketStatus, Date createStartTime, Date createEndTime, String postCode);

    public int doOutBonus(String lotteryCode, String issue, String sn, List<BonusLog> bonusLogList);

    public int doNoBonus(String lotteryCode, String issue, String sn);

    public List<Ticket> getTicketForHandSend(String lotteryCode, String issue, Integer ticketStatus);

    public PageBean getDateOrIssueCount(TicketQueryBean ticketQueryBean, String type, Integer page, Integer pageSize);

    public List<Map<String, Object>> getDateOrIssueCountByMsg(TicketQueryBean ticketQueryBean, String type);
    
    public int updateTicketForWait(String ticketId);
    
}