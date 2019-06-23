package com.cndym.bean.query;

import com.cndym.bean.user.AccountLog;
import com.cndym.bean.user.Member;

import java.util.Date;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-5-17 下午11:52:25
 */

public class AccountLogMember {
    private Date startTime;
    private Date endTime;
    private Double amountc;
    private Double amountd;
    private AccountLog accountLog;
    private String sid;
    private String name;
    private String operator;
    private Member member;

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

    public Double getAmountc() {
        return amountc;
    }

    public void setAmountc(Double amountc) {
        this.amountc = amountc;
    }

    public Double getAmountd() {
        return amountd;
    }

    public void setAmountd(Double amountd) {
        this.amountd = amountd;
    }

    public AccountLog getAccountLog() {
        return accountLog;
    }

    public void setAccountLog(AccountLog accountLog) {
        this.accountLog = accountLog;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}