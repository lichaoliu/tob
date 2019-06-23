package com.cndym.bean.count;

import java.io.Serializable;
import java.util.Date;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 上午11:16
 */
public class DaySaleTable implements Serializable {

    private String id;

    private String sid;

    private String name;

    private String curDate;

    private Long successTicket;

    private Double successAmount;

    private Long failureTicket;

    private Double failureAmount;

    private Long bonusTicket;

    private Double bonusAmount;

    private Double fixBonusAmount;

    private Date createTime;

    private String startDate;
    private String endDate;

    public DaySaleTable() {
    }

    public DaySaleTable(String id, String sid, String name, String curDate, Long successTicket, Double successAmount, Long failureTicket, Double failureAmount, Long bonusTicket, Double bonusAmount, Double fixBonusAmount, Date createTime) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.curDate = curDate;
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
