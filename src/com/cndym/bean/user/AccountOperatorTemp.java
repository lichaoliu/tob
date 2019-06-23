package com.cndym.bean.user;

import java.io.Serializable;
import java.util.Date;

/**`
 * User: 邓玉明
 * Date: 11-6-4 下午4:42
 */

/**
 * 帐户操作临时表，用来做返关事务的，如返奖
 */
public class AccountOperatorTemp implements Serializable {
	private Long id;
	private String userCode;
	private String orderId;
	private String ticketId;
	private String sid;
	private String resources;
	private String eventCode;
	private Double amount;
	private Integer status;
	private Date createTime;
	private Date acceptTime;
	private String backup1;
	private String backup2;
	private String backup3;

	public AccountOperatorTemp() {
	}

	public AccountOperatorTemp(Long id, String userCode, String orderId, String ticketId, String sid, String resources, String eventCode, Double amount, Integer status, Date createTime,
			Date acceptTime, String backup1, String backup2, String backup3) {
		this.id = id;
		this.userCode = userCode;
		this.orderId = orderId;
		this.ticketId = ticketId;
		this.sid = sid;
		this.resources = resources;
		this.eventCode = eventCode;
		this.amount = amount;
		this.status = status;
		this.createTime = createTime;
		this.acceptTime = acceptTime;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
}
