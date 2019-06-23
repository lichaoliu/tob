package com.cndym.bean.admin;

import java.io.Serializable;
import java.util.Date;

public class AlertAmountTable implements Serializable {

    private String id;

    private String postCode;

    private String postName;

    private Double alertAmount;
    
    private String lotteryCode;
    
    private String status;
    
    private Date statusTime;
    
    private String remark;
    
    private String balance;
    
    public AlertAmountTable() {
    }

    public AlertAmountTable(String id, String postCode, String postName, Double alertAmount, String lotteryCode, String status,Date statusTime, String remark, String balance) {
        this.id = id;
        this.postCode = postCode;
        this.postName = postName;
        this.alertAmount = alertAmount;
        this.lotteryCode = lotteryCode;
        this.status = status;
        this.statusTime = statusTime;
        this.remark = remark;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Double getAlertAmount() {
		return alertAmount;
	}

	public void setAlertAmount(Double alertAmount) {
		this.alertAmount = alertAmount;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
