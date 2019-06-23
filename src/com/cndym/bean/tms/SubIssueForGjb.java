package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * QQ: 116741034
 * Date: 14-5-20
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 */
public class SubIssueForGjb implements Serializable {
    private static final long serialVersionUID = -3321142443089046870L;
    private Long id;
    private String issue;//当天时间
    private Date endTime;//比赛借宿时间
    private Integer status;//状态1、新开；6、兑奖完成
    private String team;//队名
    private Date updateTime;//更新时间
    private Date createTime;//创建时间
    private Double sp;
    private String league;//联赛名
    private String sn;//队名

    public SubIssueForGjb() {
    }

    public SubIssueForGjb(Long id, String issue, Date endTime, Integer status, String team, Date updateTime, Date createTime, Double sp, String league, String sn) {
        this.id = id;
        this.issue = issue;
        this.endTime = endTime;
        this.status = status;
        this.team = team;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.sp = sp;
        this.league = league;
        this.sn = sn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getSp() {
        return sp;
    }

    public void setSp(Double sp) {
        this.sp = sp;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
