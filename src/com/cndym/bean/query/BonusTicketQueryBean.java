package com.cndym.bean.query;


import com.cndym.bean.tms.BonusTicket;
import com.cndym.bean.tms.Ticket;

import java.util.Date;

/**
 * User: mcs
 * Date: 12-10-30
 * Time: 上午11:32
 */
public class BonusTicketQueryBean {

    private BonusTicket bonusTicket;

    private Date createStartTime;
    private Date createEndTime;

    private Date sendStartTime;
    private Date sendEndTime;

    private Date returnStartTime;
    private Date returnEndTime;

    private Date bonusStartTime;
    private Date bonusEndTime;

    private Integer issueStatus;
    private Integer issueBonusStatus;
    private Integer operatorsAward;

    private Double bonusAmountS;
    private Double bonusAmountE;

    public BonusTicket getBonusTicket() {
        return bonusTicket;
    }

    public void setBonusTicket(BonusTicket bonusTicket) {
        this.bonusTicket = bonusTicket;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getSendStartTime() {
        return sendStartTime;
    }

    public void setSendStartTime(Date sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    public Date getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(Date sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public Date getReturnStartTime() {
        return returnStartTime;
    }

    public void setReturnStartTime(Date returnStartTime) {
        this.returnStartTime = returnStartTime;
    }

    public Date getReturnEndTime() {
        return returnEndTime;
    }

    public void setReturnEndTime(Date returnEndTime) {
        this.returnEndTime = returnEndTime;
    }

    public Date getBonusStartTime() {
        return bonusStartTime;
    }

    public void setBonusStartTime(Date bonusStartTime) {
        this.bonusStartTime = bonusStartTime;
    }

    public Date getBonusEndTime() {
        return bonusEndTime;
    }

    public void setBonusEndTime(Date bonusEndTime) {
        this.bonusEndTime = bonusEndTime;
    }

    public Integer getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(Integer issueStatus) {
        this.issueStatus = issueStatus;
    }

    public Integer getIssueBonusStatus() {
        return issueBonusStatus;
    }

    public void setIssueBonusStatus(Integer issueBonusStatus) {
        this.issueBonusStatus = issueBonusStatus;
    }

    public Integer getOperatorsAward() {
        return operatorsAward;
    }

    public void setOperatorsAward(Integer operatorsAward) {
        this.operatorsAward = operatorsAward;
    }

    public Double getBonusAmountS() {
        return bonusAmountS;
    }

    public void setBonusAmountS(Double bonusAmountS) {
        this.bonusAmountS = bonusAmountS;
    }

    public Double getBonusAmountE() {
        return bonusAmountE;
    }

    public void setBonusAmountE(Double bonusAmountE) {
        this.bonusAmountE = bonusAmountE;
    }
}
