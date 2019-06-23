package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 朱林虎
 *
 */
public class WeightTime implements Serializable {
	
	private static final long serialVersionUID = 4081793933512338107L;
	
	private Integer id;
	private Integer playId;
	private String startTime;
    private String weeks;
    private String type;
    private String userCode;
    private Date createTime;
    private Date updateTime;
    private String backup1;
    private String backup2;
    private String backup3;
    
    private List<WeightRule> weightRuleList;

    public WeightTime() {
    }

    public WeightTime(Integer playId,String type,String startTime,String weeks) {
        this.playId = playId;
        this.startTime = startTime;
    	this.weeks = weeks;
    	this.type = type;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlayId() {
		return playId;
	}

	public void setPlayId(Integer playId) {
		this.playId = playId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getBackup3() {
		return backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}

	public List<WeightRule> getWeightRuleList() {
		return weightRuleList;
	}

	public void setWeightRuleList(List<WeightRule> weightRuleList) {
		this.weightRuleList = weightRuleList;
	}
	
}
