package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 14-2-13
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class MatchEditLog implements Serializable {
    private Long id;
    private String lotteryCode;
    private String playCode;
    private String pollCode;
    private String issue;//当天时间
    private String sn;//当天场次序号
    private Date createTime;//创建时间
    private Integer status;//状态：0，禁售、1，正常

    public MatchEditLog() {
    }

    public MatchEditLog(Integer status, Date createTime, String sn, String issue, Long id, String lotteryCode, String playCode, String pollCode) {
        this.status = status;
        this.createTime = createTime;
        this.sn = sn;
        this.issue = issue;
        this.id = id;
        this.lotteryCode = lotteryCode;
        this.playCode = playCode;
        this.pollCode = pollCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
