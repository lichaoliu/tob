package com.cndym.sendClient;

import com.cndym.bean.tms.BonusLog;
import com.cndym.bean.tms.SubTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.utils.xml.parse.XmlBean;

import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-5-26 下午4:23 QQ：757579248 email：cndym@163.com
 */
public interface ISendClient {
    /**
     * 发单
     *
     * @param tickets
     * @return
     */
    public XmlBean sendOrder(List<Ticket> tickets);

    /**
     * 中奖查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean bonusQuery(XmlBean xmlBean);

    /**
     * 中奖查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean bonusJcQuery(XmlBean xmlBean);

    /**
     * 期次查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean issueQuery(XmlBean xmlBean);

    /**
     * 高频期次查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean gpIssueQuery(XmlBean xmlBean);

    /**
     * 开奖公告
     *
     * @param xmlBean
     * @return
     */
    public XmlBean bonusInfoQuery(XmlBean xmlBean);

    /**
     * 订单回执查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean orderQuery(XmlBean xmlBean);

    /**
     * 竞彩订单回执查询
     *
     * @param messageIdList
     * @return
     */
    public XmlBean orderQueryForJc(List<Map<String, Object>> messageIdList);

    /**
     * 订单回执查询
     *
     * @param ticketList
     * @return
     */
    public XmlBean orderQuery(List<Ticket> ticketList);

    /**
     * 订单回执查询
     *
     * @param ticketList
     * @return
     */
    public XmlBean orderQuery(List<Ticket> ticketList, String lotteryCode);

    /**
     * 期销售查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean issueSaleQuery(XmlBean xmlBean);

    /**
     * 思乐兑奖
     *
     * @param bonusLogs
     * @return
     */
    public XmlBean sendBonus(List<BonusLog> bonusLogs);

    /**
     * 额度查询
     *
     * @return
     */
    public double accountQuery();

    /**
     * 额度查询
     *
     * @return
     */
    public double accountQuery(String type);

    /**
     * 出票失败信息
     *
     * @param ticketList
     * @param ticketStatus
     * @param errorCode
     * @param errMsg
     * @return
     */
    public XmlBean getSendOrderErrorMsg(List<Ticket> ticketList, int ticketStatus, String errorCode, String errMsg);

    public XmlBean subOrderQuery(List<SubTicket> subTicketList);

    /**
     * 北单对阵查询
     *
     * @param xmlBean
     * @return xmlBean
     */
    public XmlBean matchQueryForBeiDan(XmlBean xmlBean);

    /**
     * 北单赛果查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean matchResultQueryForBeiDan(XmlBean xmlBean);

    /**
     * 竞彩冠军杯对阵查询
     */
    public XmlBean matchQueryForJc(String lotteryCode, String issue);

    /**
     * 竞彩冠军杯sp查询
     */
    public XmlBean matchQueryForJcSp(String lotteryCode, String issue);

    /**
     * 北单返奖查询
     *
     * @param xmlBean
     * @return
     */
    public XmlBean bonusQueryForBeiDan(XmlBean xmlBean);
}
