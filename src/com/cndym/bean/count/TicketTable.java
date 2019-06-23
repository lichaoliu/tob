package com.cndym.bean.count;

import java.util.Date;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午7:15
 */
public class TicketTable {

    private String id;

    private String lotteryCode;

    private String issue;

    private String postCode;

    private Date startTime;

    private Date endTime;

    private Long successTicket;

    private Double successAmount;

    private Double waitAmount;

    private Long waitTicket;

    private Long failureTicket;

    private Double failureAmount;

    private Long bonusTicket;

    private Double bonusAmount;

    private Double fixBonusAmount;

    private Date createTime;

    public TicketTable() {
    }

    public TicketTable(String id, String lotteryCode, String issue, String postCode, Date startTime, Date endTime, Long successTicket, Double successAmount, Double waitAmount, Long waitTicket, Long failureTicket, Double failureAmount, Long bonusTicket, Double bonusAmount, Double fixBonusAmount, Date createTime) {
        this.id = id;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.postCode = postCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.successTicket = successTicket;
        this.successAmount = successAmount;
        this.waitAmount = waitAmount;
        this.waitTicket = waitTicket;
        this.failureTicket = failureTicket;
        this.failureAmount = failureAmount;
        this.bonusTicket = bonusTicket;
        this.bonusAmount = bonusAmount;
        this.fixBonusAmount = fixBonusAmount;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getSuccessTicket() {
        return successTicket;
    }

    public void setSuccessTicket(Long successTicket) {
        this.successTicket = successTicket;
    }

    public Double getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(Double successAmount) {
        this.successAmount = successAmount;
    }

    public Double getWaitAmount() {
        return waitAmount;
    }

    public void setWaitAmount(Double waitAmount) {
        this.waitAmount = waitAmount;
    }

    public Long getWaitTicket() {
        return waitTicket;
    }

    public void setWaitTicket(Long waitTicket) {
        this.waitTicket = waitTicket;
    }

    public Long getFailureTicket() {
        return failureTicket;
    }

    public void setFailureTicket(Long failureTicket) {
        this.failureTicket = failureTicket;
    }

    public Double getFailureAmount() {
        return failureAmount;
    }

    public void setFailureAmount(Double failureAmount) {
        this.failureAmount = failureAmount;
    }

    public Long getBonusTicket() {
        return bonusTicket;
    }

    public void setBonusTicket(Long bonusTicket) {
        this.bonusTicket = bonusTicket;
    }

    public Double getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Double getFixBonusAmount() {
        return fixBonusAmount;
    }

    public void setFixBonusAmount(Double fixBonusAmount) {
        this.fixBonusAmount = fixBonusAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
