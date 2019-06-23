package com.cndym.dao;

import com.cndym.bean.query.TicketMainIssueBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Member;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:52
 */
public interface ITicketDao extends IGenericDao<Ticket> {
    public Ticket getTicketByTicketIdForUpdate(String ticketId);

    public Ticket getTicketByTicketId(String ticketId);
    
    public Ticket getTicketById(long id);

    public Ticket getTicketByBackup1(String backup1, String postCode);

    public List<Map<String, Object>> countBonusAmount(String lotteryCode, String issue, String sn);

    public Map getTicketCount(Ticket ticket, Member member);

    /**
     * 根据批次号和商户id获取票
     *
     * @param orderId 批次号
     * @param sid     商户id
     * @return
     */
    public List<Ticket> getTicketByOrderId(String orderId, String sid, Integer ticketStatus);

    public List<Ticket> getTicketByOrderId(String orderId, String sid);

    /**
     * 根据票号和商户号获取票
     *
     * @param ticketIdList 票号列表
     * @param sid          商户id
     * @return
     */
    public List<Ticket> getTicketByOutTicketId(List<String> ticketIdList, String sid);

    /**
     * 根据批次id和sid更新票状态
     *
     * @param ticketStatus
     * @param ticketId
     * @param oldTicketStatus
     * @param postCode
     * @return
     */
    public int updateTicketStatusForOrderId(Integer ticketStatus, String ticketId, Integer oldTicketStatus, String postCode);

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
    
    /**
     * 查询发送中的票(发送时间和创建时间限定向前推迟)
     *
     * @param lotteryCode
     * @param postCode
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getSendedDelayTicket(String lotteryCode, String postCode,Date sendTime,Date createTime, int page, int pageSize);

    public int updateTicketForWait(String ticketId);
    
    public Ticket getTicketByTikcetId(String ticketId);

    public int updateBonusAmount(BonusLog bonusLog);

    public int updateForSubIssueUpdate(Ticket ticket);

    public List<Ticket> getTicketsForEndIssue(String lotteryCode, String issue);

    public List<Ticket> getTicketsForEndIssue();

    public int updateSpInfo(String ticketId, String spInfo);

    public List<Map<String, Object>> getSendTicket(String lotteryCode, String issue);

    public List<Map<String, Object>> getSendTicket(String lotteryCode);

    public PageBean getTicketForBonus(String lotteryCode, String issue, String sn, int pageSize, int page);

    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize);

    public PageBean getSendedTicket(String lotteryCode, String postCode, int page, int pageSize, String desc);

    public PageBean getSendedTicketOrderByIssue(String lotteryCode, String postCode, int page, int pageSize);

    public int doNoBonus(String lotteryCode, String issue);

    public int updateTicketForDuiJiang(Ticket ticket);

    public List<Ticket> getNoDuiJiangTicket(String lotteryCode, String postCode);

    public PageBean getPageBeanByTicket(TicketQueryBean ticketQueryBean, Integer page, Integer pageSize);

    public int updateSaleInfo(String ticketId, String saleInfo);

    public int updateSendingTicketToSuccess(String lotteryCode, String issue);

    int updateTicketStatusForOrderIdForNoSend(Integer ticketStatus, String ticketId, Integer oldTicketStatus, String postCode);

    public int updateForSubIssueUpdate(String programsOrderId, String sn, Date date);

    public List<Map<String, Object>> getTicketCountByDay(Map<String, Object> cond);

    public int getTicketCountByDayCount(Map<String, Object> cond);

    public Ticket getTicketBySaleCode(String lotteryCode, String issue, String saleCode, String postCode);

    public PageBean getSendedTicket(String postCode, int page, int pageSize);

    public int getTicketCountByIssue(String lotteryCode, String issue);

    public Map<String, Object> getTicketMainIssueCountByIssue(TicketMainIssueBean ticketMainIssueBean);

    public PageBean getSendedTicket(String lotteryCode, String playCode, String pollCode, String postCode, int page, int pageSize);

    public PageBean getSendedTicketEx(String lotteryCode, String postCode, Date sendTime, int page, int pageSize);

    public PageBean getPageBeanByParaSending(Ticket ticket, Integer page, Integer pageSize);

    public PageBean getSendedTicketEXEX(String lotteryCode, String postCode, String issue, int page, int pageSize);

    public List<Map<String, Object>> findNoSendTicket(Integer ticketStatus, String postCode);

    public PageBean getPageBeanByParaLottery(TicketQueryBean queryBean);

    public PageBean getIssueLotteryCount(TicketMainIssueBean ticketMainIssueBean, Integer page, Integer pageSize);

    public List<Map<String, Object>> findNoSendTicketList(Integer ticketStatus, Date createStartTime, Date createEndTime, String postCode);
    
    public int doNoBonus(String lotteryCode, String issue, String sn);

    public List<Ticket> getTicketForHandSend(String lotteryCode, String issue, Integer ticketStatus);

    public PageBean getDateOrIssueCount(TicketQueryBean ticketQueryBean, String type, Integer page, Integer pageSize);

    public List<Map<String, Object>> getDateOrIssueCountByMsg(TicketQueryBean ticketQueryBean, String type);
}