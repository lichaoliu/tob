package com.cndym.bean.tms;

import java.util.Date;

/**
 * 负责出票口和彩种关系的对应
 * @author liulichao
 *
 */
public class SendClientPostCode {
	private Integer id;
	private String lotteryCode;
	private String postCode;
	private Integer status;//0 停用 1在此出票口出此彩种
	private Date updateTime;
	private String backup1;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
}
