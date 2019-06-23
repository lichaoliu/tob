package com.cndym.service.subIssue.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-9 下午4:50
 */
public class SubIssueComm implements Serializable {
    private String lotteryCode;
    private String issue;
    private String playCode;
    private String pollCode;
    private String sn;
    private String matchId;
    private String week;
    private Date endTime;
    private int endOperator;
    private int bonusOperator;
    private Date danShiEndTime;
    private Date FuShiEndTime;


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

    public String getPlayCode() {
        return playCode;
    }

    public void setPlayCode(String playCode) {
        this.playCode = playCode;
    }

    public String getPollCode() {
        return pollCode;
    }

    public void setPollCode(String pollCode) {
        this.pollCode = pollCode;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getEndOperator() {
        return endOperator;
    }

    public void setEndOperator(int endOperator) {
        this.endOperator = endOperator;
    }

    public int getBonusOperator() {
        return bonusOperator;
    }

    public void setBonusOperator(int bonusOperator) {
        this.bonusOperator = bonusOperator;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Date getDanShiEndTime() {
        return danShiEndTime;
    }

    public void setDanShiEndTime(Date danShiEndTime) {
        this.danShiEndTime = danShiEndTime;
    }

    public Date getFuShiEndTime() {
        return FuShiEndTime;
    }

    public void setFuShiEndTime(Date fuShiEndTime) {
        FuShiEndTime = fuShiEndTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
