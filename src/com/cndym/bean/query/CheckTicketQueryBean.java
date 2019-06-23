package com.cndym.bean.query;


import com.cndym.bean.tms.CheckTicket;
import com.cndym.bean.tms.Ticket;

import java.util.Date;

/**
 * 校验票查询
 * @author 朱林虎
 *
 */
public class CheckTicketQueryBean {

    private CheckTicket checkTicket;

    private Date createStartTime;
    private Date createEndTime;

    private Date sendStartTime;
    private Date sendEndTime;

    private Date returnStartTime;
    private Date returnEndTime;

    private Date bonusStartTime;
    private Date bonusEndTime;

    private Integer amountDiffStatus;
    private Integer fixBonusDiffStatus;
    private Integer bonusDiffStatus;
    private Integer ticketDiffStatus;


    public CheckTicket getCheckTicket() {
		return checkTicket;
	}

	public void setCheckTicket(CheckTicket checkTicket) {
		this.checkTicket = checkTicket;
	}

	public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public Date getSendStartTime() {
        return sendStartTime;
    }

    public void setSendStartTime(Date sendStartTime) {
        this.sendStartTime = sendStartTime;
    }

    public Date getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(Date sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public Date getReturnStartTime() {
        return returnStartTime;
    }

    public void setReturnStartTime(Date returnStartTime) {
        this.returnStartTime = returnStartTime;
    }

    public Date getReturnEndTime() {
        return returnEndTime;
    }

    public void setReturnEndTime(Date returnEndTime) {
        this.returnEndTime = returnEndTime;
    }

    public Date getBonusStartTime() {
        return bonusStartTime;
    }

    public void setBonusStartTime(Date bonusStartTime) {
        this.bonusStartTime = bonusStartTime;
    }

    public Date getBonusEndTime() {
        return bonusEndTime;
    }

    public void setBonusEndTime(Date bonusEndTime) {
        this.bonusEndTime = bonusEndTime;
    }

	public Integer getAmountDiffStatus() {
		return amountDiffStatus;
	}

	public void setAmountDiffStatus(Integer amountDiffStatus) {
		this.amountDiffStatus = amountDiffStatus;
	}

	public Integer getFixBonusDiffStatus() {
		return fixBonusDiffStatus;
	}

	public void setFixBonusDiffStatus(Integer fixBonusDiffStatus) {
		this.fixBonusDiffStatus = fixBonusDiffStatus;
	}

	public Integer getBonusDiffStatus() {
		return bonusDiffStatus;
	}

	public void setBonusDiffStatus(Integer bonusDiffStatus) {
		this.bonusDiffStatus = bonusDiffStatus;
	}

	public Integer getTicketDiffStatus() {
		return ticketDiffStatus;
	}

	public void setTicketDiffStatus(Integer ticketDiffStatus) {
		this.ticketDiffStatus = ticketDiffStatus;
	}
    
}