package com.cndym.bean.tms;

import java.util.Date;

/**
 * User: mcs
 * Date: 12-11-13
 * Time: 上午10:24
 */
public class PostBonus {

    private String id;

    //彩种
    private String lotteryCode;
    //彩期
    private String issue;
    //出票口编号
    private String postCode;
    //处理状态 0 未处理 1 已处理
    private Integer status;
    //创建时间
    private Date createTime;
    //处理时间
    private Date acceptTime;

    public PostBonus() {
    }

    public PostBonus(String id, String lotteryCode, String issue, String postCode, Integer status, Date createTime, Date acceptTime) {
        this.id = id;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.postCode = postCode;
        this.status = status;
        this.createTime = createTime;
        this.acceptTime = acceptTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }
}
