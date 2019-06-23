package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明 Date: 11-3-27 下午4:52
 */
public class MainIssue implements Serializable {

	private static final long serialVersionUID = -9170187568993057008L;
	private Long id;
	private String lotteryCode;
	private String name;
	private Date startTime;// 官方开始时间
	private Date simplexTime;// 单式结束时间
	private Date duplexTime;// 复式结束时间
	private Date endTime;// 官方停止时间
	private Integer status;// 状态(0,预售，1,销售，2,暂停，3,结期)
	private Integer sendStatus;// 是否可发单(0,不可，1,可)
	private Date bonusTime;// 开奖时间
	private Date sendBonusTime;// 派奖时间
	private Integer bonusStatus;// 返奖状态(0,等待返奖，1,已返奖)
	private String bonusNumber;// 开奖号码
	private Double bonusTotal;// 中奖总金额
	private String prizePool;// 下期奖池信息
	private String bonusClass;// 奖级信息[{'n1':'奖级','c1':'单注奖金','m1':'全国个数','t1':'奖级中文名','a1':地方个数(默认0)},{'n1':'奖级','c1':'单注奖金','m1':'全国个数','t1':'奖级中文名','a1':地方个数(默认0)}]
	private String saleTotal;// 销售总金额
	private String globalSaleTotal;// 全国销量
	private String backup1;
	private String backup2;
	private String backup3;
	private Integer operatorsAward;// 是否算奖（0等待算奖，1算奖中，2完成算奖)
	private Integer centerBonusStatus; // 中心返奖状态(0,等待返奖，1,已返奖)

	// 查询使用
	private Integer[] statuses;
	private String issueNameStart;
	private String issueNameEnd;

	public MainIssue() {
	}

	public MainIssue(Long id, String lotteryCode, String name, Date startTime, Date simplexTime, Date duplexTime, Date endTime, Integer status, Integer sendStatus, Date bonusTime,
			Integer bonusStatus, String bonusNumber, Double bonusTotal, String prizePool, String bonusClass, String saleTotal, String globalSaleTotal, String backup1, String backup2, String backup3,
			Integer operatorsAward, Integer centerBonusStatus, Date sendBonusTime) {
		this.id = id;
		this.lotteryCode = lotteryCode;
		this.name = name;
		this.startTime = startTime;
		this.simplexTime = simplexTime;
		this.duplexTime = duplexTime;
		this.endTime = endTime;
		this.status = status;
		this.sendStatus = sendStatus;
		this.bonusTime = bonusTime;
		this.bonusStatus = bonusStatus;
		this.bonusNumber = bonusNumber;
		this.bonusTotal = bonusTotal;
		this.prizePool = prizePool;
		this.bonusClass = bonusClass;
		this.saleTotal = saleTotal;
		this.globalSaleTotal = globalSaleTotal;
		this.backup1 = backup1;
		this.backup2 = backup2;
		this.backup3 = backup3;
		this.operatorsAward = operatorsAward;
		this.centerBonusStatus = centerBonusStatus;
		this.sendBonusTime = sendBonusTime;
	}

	public Integer getCenterBonusStatus() {
		return centerBonusStatus;
	}

	public void setCenterBonusStatus(Integer centerBonusStatus) {
		this.centerBonusStatus = centerBonusStatus;
	}

	public String getPrizePool() {
		return prizePool;
	}

	public void setPrizePool(String prizePool) {
		this.prizePool = prizePool;
	}

	public String getBonusClass() {
		return bonusClass;
	}

	public void setBonusClass(String bonusClass) {
		this.bonusClass = bonusClass;
	}

	public Date getSimplexTime() {
		return simplexTime;
	}

	public void setSimplexTime(Date simplexTime) {
		this.simplexTime = simplexTime;
	}

	public Date getDuplexTime() {
		return duplexTime;
	}

	public void setDuplexTime(Date duplexTime) {
		this.duplexTime = duplexTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLotteryCode() {
		return lotteryCode;
	}

	public void setLotteryCode(String lotteryCode) {
		this.lotteryCode = lotteryCode;
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

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
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

	public String getBonusNumber() {
		return bonusNumber;
	}

	public void setBonusNumber(String bonusNumber) {
		this.bonusNumber = bonusNumber;
	}

	public Double getBonusTotal() {
		return bonusTotal;
	}

	public void setBonusTotal(Double bonusTotal) {
		this.bonusTotal = bonusTotal;
	}

	public String getSaleTotal() {
		return saleTotal;
	}

	public void setSaleTotal(String saleTotal) {
		this.saleTotal = saleTotal;
	}

	public String getGlobalSaleTotal() {
		return globalSaleTotal;
	}

	public void setGlobalSaleTotal(String globalSaleTotal) {
		this.globalSaleTotal = globalSaleTotal;
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

	public Integer getOperatorsAward() {
		return operatorsAward;
	}

	public void setOperatorsAward(Integer operatorsAward) {
		this.operatorsAward = operatorsAward;
	}

	public Integer[] getStatuses() {
		return statuses;
	}

	public void setStatuses(Integer[] statuses) {
		this.statuses = statuses;
	}

	public String getIssueNameStart() {
		return issueNameStart;
	}

	public void setIssueNameStart(String issueNameStart) {
		this.issueNameStart = issueNameStart;
	}

	public String getIssueNameEnd() {
		return issueNameEnd;
	}

	public void setIssueNameEnd(String issueNameEnd) {
		this.issueNameEnd = issueNameEnd;
	}

	public Date getSendBonusTime() {
		return sendBonusTime;
	}

	public void setSendBonusTime(Date sendBonusTime) {
		this.sendBonusTime = sendBonusTime;
	}
}
