package com.cndym.bean.count;

import java.util.Date;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午7:15
 */
public class DayTicketTable {

    private String id;

    private String postCode;

    private String curDate;

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



    private String startDate;

    private String endDate;

    public DayTicketTable() {

    }
    public DayTicketTable(String id, String postCode, String curDate, Long successTicket, Double successAmount, Double waitAmount, Long waitTicket, Long failureTicket, Double failureAmount, Long bonusTicket, Double bonusAmount, Double fixBonusAmount, Date createTime) {
        this.id = id;
        this.postCode = postCode;
        this.curDate = curDate;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCurDate() {
        return curDate;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
