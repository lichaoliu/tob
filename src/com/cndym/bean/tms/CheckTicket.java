package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author 朱林虎
 *
 */
public class CheckTicket implements Serializable {
   
	private static final long serialVersionUID = -5277193494280077954L;
    
    private Long id;
    private String outTicketId;//外部票号（partnersCode+outTicketId唯一索引）
    private String sid;//代理商号
    private String userCode;
    private String orderId;//批次号（partnersCode+orderId唯一索引）
    private String ticketId;
    private String lotteryCode;
    private String playCode;
    private String pollCode;
    private String issue;
    private Integer item;
    private Integer multiple;
    private String postCode;
    private Integer ticketStatus;
    private Integer ticketStatusDiff;
    private String upTicketStatus;
    private String upTicketStatusDesc;
    private Integer bonusStatus;
    private Integer bigBonus;//是否是大奖，0是普通，1是大奖
    private Integer checkAmountStatus;
    private Integer checkBonusStatus;
    private Integer checkTicketStatus;
    private Double bonusAmount;
    private Double amount;
    private Double fixBonusAmount;
    private Double upBonusAmount;
    private Double upAmount;
    private Double upFixBonusAmount;
    private Double bonusAmountDiff;
    private Double amountDiff;
    private Double fixBonusAmountDiff;
    private Date createTime;
    private Date sendTime;
    private Date returnTime;
    private Date bonusTime;
    private Date checkTime;
    private Date updateTime;
    private String backup1;
    private String backup2;
    private String backup3;

    public CheckTicket() {
    }

    public CheckTicket(String ticketId) {
        this.ticketId = ticketId;
    }

    public CheckTicket(Long id, String sid, String ticketId, String lotteryCode,String postCode, String issue, Double fixBonusAmount,Integer checkBonusStatus,Integer checkAmountStatus,Integer bonusStatus,
    		Integer ticketStatus,Double bonusAmount,Double amount,Double upBonusAmount,Double upAmount,Double upFixBonusAmount,Double amountDiff,Double bonusAmountDiff,Double fixBonusAmountDiff,String backup1, String backup2, String backup3,
    		String orderId,String outTicketId,String pollCode,Integer item,Integer multiple,Integer bigBonus,Date createTime,Date sendTime,Date returnTime,Date bonusTime,Date checkTime,Date updateTime,Integer ticketStatusDiff
    		,String upTicketStatus,String upTicketStatusDesc,Integer checkTicketStatus) {
        this.id = id;
        this.sid = sid;
        this.ticketId = ticketId;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.checkAmountStatus = checkAmountStatus;
        this.checkBonusStatus = checkBonusStatus;
        this.postCode = postCode;
        this.amount = amount;
        this.ticketStatus = ticketStatus;
        this.bonusStatus = bonusStatus;
        this.bonusAmount = bonusAmount;
        this.fixBonusAmount = fixBonusAmount;
        this.upAmount = upAmount;
        this.upBonusAmount = upBonusAmount;
        this.upFixBonusAmount = upFixBonusAmount;
        this.amountDiff = amountDiff;
        this.bonusAmountDiff = bonusAmountDiff;
        this.fixBonusAmountDiff = fixBonusAmountDiff;
        this.upTicketStatus = upTicketStatus;
        this.ticketStatusDiff = ticketStatusDiff;
        this.upTicketStatusDesc = upTicketStatusDesc;
        this.checkTicketStatus = checkTicketStatus;
        this.orderId = orderId;
        this.outTicketId = outTicketId;
        this.pollCode = pollCode;
        this.item = item;
        this.multiple = multiple;
        this.bigBonus = bigBonus;
        this.createTime = createTime;
        this.sendTime = sendTime;
        this.returnTime = returnTime;
        this.bonusTime = bonusTime;
        this.checkTime = checkTime;
        this.updateTime = updateTime;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
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


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(Integer ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Integer getBonusStatus() {
        return bonusStatus;
    }

    public void setBonusStatus(Integer bonusStatus) {
        this.bonusStatus = bonusStatus;
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

	public Integer getCheckAmountStatus() {
		return checkAmountStatus;
	}

	public void setCheckAmountStatus(Integer checkAmountStatus) {
		this.checkAmountStatus = checkAmountStatus;
	}

	public Integer getCheckBonusStatus() {
		return checkBonusStatus;
	}

	public void setCheckBonusStatus(Integer checkBonusStatus) {
		this.checkBonusStatus = checkBonusStatus;
	}

	public Double getUpBonusAmount() {
		return upBonusAmount;
	}

	public void setUpBonusAmount(Double upBonusAmount) {
		this.upBonusAmount = upBonusAmount;
	}

	public Double getUpAmount() {
		return upAmount;
	}

	public void setUpAmount(Double upAmount) {
		this.upAmount = upAmount;
	}

	public Double getUpFixBonusAmount() {
		return upFixBonusAmount;
	}

	public void setUpFixBonusAmount(Double upFixBonusAmount) {
		this.upFixBonusAmount = upFixBonusAmount;
	}

	public Double getBonusAmountDiff() {
		return bonusAmountDiff;
	}

	public void setBonusAmountDiff(Double bonusAmountDiff) {
		this.bonusAmountDiff = bonusAmountDiff;
	}

	public Double getAmountDiff() {
		return amountDiff;
	}

	public void setAmountDiff(Double amountDiff) {
		this.amountDiff = amountDiff;
	}

	public Double getFixBonusAmountDiff() {
		return fixBonusAmountDiff;
	}

	public void setFixBonusAmountDiff(Double fixBonusAmountDiff) {
		this.fixBonusAmountDiff = fixBonusAmountDiff;
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

	public String getOutTicketId() {
		return outTicketId;
	}

	public void setOutTicketId(String outTicketId) {
		this.outTicketId = outTicketId;
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

	public String getPlayCode() {
		return playCode;
	}

	public void setPlayCode(String playCode) {
		this.playCode = playCode;
	}

	public String getPollCode() {
		return pollCode;
	}

	public void setPollCode(String pollCode) {
		this.pollCode = pollCode;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getBigBonus() {
		return bigBonus;
	}

	public void setBigBonus(Integer bigBonus) {
		this.bigBonus = bigBonus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Date getBonusTime() {
		return bonusTime;
	}

	public void setBonusTime(Date bonusTime) {
		this.bonusTime = bonusTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getTicketStatusDiff() {
		return ticketStatusDiff;
	}

	public void setTicketStatusDiff(Integer ticketStatusDiff) {
		this.ticketStatusDiff = ticketStatusDiff;
	}

	public String getUpTicketStatus() {
		return upTicketStatus;
	}

	public void setUpTicketStatus(String upTicketStatus) {
		this.upTicketStatus = upTicketStatus;
	}

	public String getUpTicketStatusDesc() {
		return upTicketStatusDesc;
	}

	public void setUpTicketStatusDesc(String upTicketStatusDesc) {
		this.upTicketStatusDesc = upTicketStatusDesc;
	}

	public Integer getCheckTicketStatus() {
		return checkTicketStatus;
	}

	public void setCheckTicketStatus(Integer checkTicketStatus) {
		this.checkTicketStatus = checkTicketStatus;
	}
	
}
