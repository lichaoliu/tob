package com.cndym.bean.count;

import oracle.sql.DATE;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 上午11:16
 */
public class SaleTable implements Serializable {

    private String id;

    private String sid;

    private String name;

    private String lotteryCode;

    private String issue;

    private Date startTime;

    private Date endTime;

    private Long successTicket;

    private Double successAmount;

    private Long failureTicket;

    private Double failureAmount;

    private Long bonusTicket;

    private Double bonusAmount;

    private Double fixBonusAmount;

    private Date createTime;




    //查询标记
    private String type;
    private Double cbonusAmount;
    private Double dbonusAmount;

    public SaleTable() {
    }

    public SaleTable(String id, String sid, String name, String lotteryCode, String issue, Date startTime, Date endTime, Long successTicket, Double successAmount, Long failureTicket, Double failureAmount, Long bonusTicket, Double bonusAmount, Double fixBonusAmount, Date createTime) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.successTicket = successTicket;
        this.successAmount = successAmount;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getSuccessTicket() {
        return successTicket;
    }

    public void setSuccessTicket(Long successTicket) {
        this.successTicket = successTicket;
    }
    public Long getFailureTicket() {
        return failureTicket;
    }

    public void setFailureTicket(Long failureTicket) {
        this.failureTicket = failureTicket;
    }

    public Long getBonusTicket() {
        return bonusTicket;
    }

    public void setBonusTicket(Long bonusTicket) {
        this.bonusTicket = bonusTicket;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Double getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(Double successAmount) {
        this.successAmount = successAmount;
    }

    public Double getFailureAmount() {
        return failureAmount;
    }

    public void setFailureAmount(Double failureAmount) {
        this.failureAmount = failureAmount;
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

    public Double getCbonusAmount() {
        return cbonusAmount;
    }

    public void setCbonusAmount(Double cbonusAmount) {
        this.cbonusAmount = cbonusAmount;
    }

    public Double getDbonusAmount() {
        return dbonusAmount;
    }

    public void setDbonusAmount(Double dbonusAmount) {
        this.dbonusAmount = dbonusAmount;
    }
}
