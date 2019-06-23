package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-5-29
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
public class BonusJcLog implements Serializable {
    private Long id;
    private String issue;
    private String lotteryCode;
    private String sn;
    private String week;
    private Date createTime;
    private String postCode;
    private Long subForIssueId;
    private String backup1;
    private String backup2;

    public BonusJcLog() {
    }

    public BonusJcLog(Long id, String issue, String lotteryCode, String sn, String week, Date createTime, String postCode, Long subForIssueId, String backup1, String backup2) {
        this.id = id;
        this.issue = issue;
        this.lotteryCode = lotteryCode;
        this.sn = sn;
        this.week = week;
        this.createTime = createTime;
        this.postCode = postCode;
        this.subForIssueId = subForIssueId;
        this.backup1 = backup1;
        this.backup2 = backup2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Long getSubForIssueId() {
        return subForIssueId;
    }

    public void setSubForIssueId(Long subForIssueId) {
        this.subForIssueId = subForIssueId;
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1;
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2;
    }
}
