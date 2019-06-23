package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

public class PostIssue implements Serializable {
	private static final long serialVersionUID = -4013105928532629373L;

	private String id;
	private String lotteryCode;
	private String postCode;
	private String name;
	private Date startTime;
	private Date duplexTime;
	private Date endTime;
	private Integer status;
	private Date bonusTime;
	private Integer bonusStatus;
	private Date createTime;
	private Date updateTime;
	private String backup1;
	private String backup2;
	private String backup3;

	public PostIssue() {

	}

	public PostIssue(String id, String lotteryCode, String postCode, String name, Date startTime, Date duplexTime, Date endTime, Integer status, Date bonusTime, Integer bonusStatus, Date createTime,
			Date updateTime, String backup1, String backup2, String backup3) {
		this.id = id;
		this.lotteryCode = lotteryCode;
		this.postCode = postCode;
		this.name = name;
		this.startTime = startTime;
		this.duplexTime = duplexTime;
		this.endTime = endTime;
		this.status = status;
		this.bonusTime = bonusTime;
		this.bonusStatus = bonusStatus;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getDuplexTime() {
		return duplexTime;
	}

	public void setDuplexTime(Date duplexTime) {
		this.duplexTime = duplexTime;
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

	public Date getBonusTime() {
		return bonusTime;
	}

	public void setBonusTime(Date bonusTime) {
		this.bonusTime = bonusTime;
	}

	public Integer getBonusStatus() {
		return bonusStatus;
	}

	public void setBonusStatus(Integer bonusStatus) {
		this.bonusStatus = bonusStatus;
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
}
