package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：邓玉明 时间：11-5-26 下午5:17 QQ：757579248 email：cndym@163.com
 */
public class BonusLog implements Serializable {
	private static final long serialVersionUID = -4424767333648030648L;
	private Long id;
	private String ticketId;
	private String lotteryCode;
	private String issue;
	private Double bonusAmount;// 税后奖金
	private Double fixBonusAmount;// 税前奖金
	private Integer bigBonus;// 是否是大奖（0,不是;1,是）
	private String bonusClass;// 中奖级别json
	private Date createTime;
	private String postCode;
	private String saleCode;
	private Integer duiJiangStatus;
	private String saleTime;
	private String machineCode;
	private Date sendBonusTime;
	private String errorCode;
	private String errorMsg;

	public BonusLog() {
	}

	public BonusLog(Long id, String ticketId, String lotteryCode, String issue, Double bonusAmount, Double fixBonusAmount, Integer bigBonus, String bonusClass, Date createTime, String postCode,
			String saleCode, Integer duiJiangStatus, String saleTime, String machineCode, Date sendBonusTime, String errorCode, String errorMsg) {
		super();
		this.id = id;
		this.ticketId = ticketId;
		this.lotteryCode = lotteryCode;
		this.issue = issue;
		this.bonusAmount = bonusAmount;
		this.fixBonusAmount = fixBonusAmount;
		this.bigBonus = bigBonus;
		this.bonusClass = bonusClass;
		this.createTime = createTime;
		this.postCode = postCode;
		this.saleCode = saleCode;
		this.duiJiangStatus = duiJiangStatus;
		this.saleTime = saleTime;
		this.machineCode = machineCode;
		this.sendBonusTime = sendBonusTime;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public Double getFixBonusAmount() {
		return fixBonusAmount;
	}

	public void setFixBonusAmount(Double fixBonusAmount) {
		this.fixBonusAmount = fixBonusAmount;
	}

	public Integer getBigBonus() {
		return bigBonus;
	}

	public void setBigBonus(Integer bigBonus) {
		this.bigBonus = bigBonus;
	}

	public String getBonusClass() {
		return bonusClass;
	}

	public void setBonusClass(String bonusClass) {
		this.bonusClass = bonusClass;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	public Integer getDuiJiangStatus() {
		return duiJiangStatus;
	}

	public void setDuiJiangStatus(Integer duiJiangStatus) {
		this.duiJiangStatus = duiJiangStatus;
	}

	public String getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
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

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public Date getSendBonusTime() {
		return sendBonusTime;
	}

	public void setSendBonusTime(Date sendBonusTime) {
		this.sendBonusTime = sendBonusTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
