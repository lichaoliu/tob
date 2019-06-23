package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-1-31 上午9:59
 */
public class TicketReCode implements Serializable {
    private static final long serialVersionUID = -5354069855346612456L;
    private Long id;
    private String ticketId;
    private String lotteryCode;
    private String issue;
    private String matchId;
    private Date createTime;
    private Date endTime;

    public TicketReCode() {
    }

    public TicketReCode(Long id, String ticketId, String lotteryCode, String issue, String matchId, Date createTime, Date endTime) {
        this.id = id;
        this.ticketId = ticketId;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.matchId = matchId;
        this.createTime = createTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
