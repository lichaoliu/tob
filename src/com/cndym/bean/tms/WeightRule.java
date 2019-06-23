package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 朱林虎
 *
 */
public class WeightRule implements Serializable {
	
	private static final long serialVersionUID = 4081793933512338107L;
	
	private Integer id;
	private Integer timeId;
    private String param;
    private Double amount;
    private String postCode;
    private String userCode;
    private Date createTime;
    private Date updateTime;
    private String backup1;
    private String backup2;
    private String backup3;

    public WeightRule() {
    }

    public WeightRule(Integer timeId,String param, Double amount, String postCode) {
        this.timeId = timeId;
    	this.param = param;
    	this.amount = amount;
    	this.postCode = postCode;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTimeId() {
		return timeId;
	}

	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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
