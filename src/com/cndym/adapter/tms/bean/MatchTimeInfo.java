package com.cndym.adapter.tms.bean;

import com.cndym.utils.Utils;

import java.util.Date;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-9 下午4:45
 */
public class MatchTimeInfo implements Comparable<MatchTimeInfo> {
    private String matchId;
    private String issue;
    private Date time;
    private Date danShiEndTime;
    private Date FuShiEndTime;

    public MatchTimeInfo() {
    }

    public MatchTimeInfo(String matchId, String issue, Date time, Date danShiEndTime, Date fuShiEndTime) {
        this.matchId = matchId;
        this.time = time;
        this.danShiEndTime = danShiEndTime;
        FuShiEndTime = fuShiEndTime;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    @Override
    public int compareTo(MatchTimeInfo matchTimeInfo) {
        if (null == getTime() || null == matchTimeInfo.getTime()) {
            return 0;
        }
        if (this.getTime().getTime() > matchTimeInfo.getTime().getTime()) {
            return 1;
        } else if (this.getTime().getTime() == matchTimeInfo.getTime().getTime()) {
            if (Utils.formatLong(this.getMatchId(), 0) > Utils.formatLong(matchTimeInfo.getMatchId(), 0)) {
                return 1;
            }
        }
        return 0;
    }
}
