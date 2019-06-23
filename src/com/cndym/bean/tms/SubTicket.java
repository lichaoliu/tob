package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明 Date: 11-3-27 下午6:27
 */
public class SubTicket implements Serializable {
    private static final long serialVersionUID = -5277193494280077954L;
    private Long id;
    private String ticketId;
    private String subTicketId;//子票内部票号(唯一索引)
    private String lotteryCode;
    private String playCode;
    private String pollCode;
    private String issue;
    private Integer item;
    private Integer multiple;
    private String numberInfo;
    private String saleCode;
    private String errCode;
    private String errMsg;
    private String postCode;//出票口
    private Double amount;
    private Integer ticketStatus;
    private Integer bonusStatus;
    private Double bonusAmount;
    private Date createTime;
    private Date acceptTime;
    private Double fixBonusAmount;
    private Date sendTime;
    private Date returnTime;
    private Date bonusTime;
    private String saleInfo;//竞彩的返回串号
    private Integer bigBonus;//是否是大奖，0是普通，1是大奖
    private String backup1;
    private String backup2;
    private String backup3;

    public SubTicket() {
    }

    public SubTicket(String subTicketId) {
        this.subTicketId = subTicketId;
    }

    public SubTicket(Long id, String ticketId, String subTicketId, String lotteryCode, String playCode, String pollCode, String issue, Integer item, Integer multiple, String numberInfo, String saleCode, String errCode, String errMsg, String postCode, Double amount, Integer ticketStatus, Integer bonusStatus, Double bonusAmount, Date createTime, Date acceptTime, Double fixBonusAmount, Date sendTime, Date returnTime, Date bonusTime, String saleInfo, Integer bigBonus, String backup1, String backup2, String backup3) {
        this.id = id;
        this.ticketId = ticketId;
        this.subTicketId = subTicketId;
        this.lotteryCode = lotteryCode;
        this.playCode = playCode;
        this.pollCode = pollCode;
        this.issue = issue;
        this.item = item;
        this.multiple = multiple;
        this.numberInfo = numberInfo;
        this.saleCode = saleCode;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.postCode = postCode;
        this.amount = amount;
        this.ticketStatus = ticketStatus;
        this.bonusStatus = bonusStatus;
        this.bonusAmount = bonusAmount;
        this.createTime = createTime;
        this.acceptTime = acceptTime;
        this.fixBonusAmount = fixBonusAmount;
        this.sendTime = sendTime;
        this.returnTime = returnTime;
        this.bonusTime = bonusTime;
        this.saleInfo = saleInfo;
        this.bigBonus = bigBonus;
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


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSubTicketId() {
        return subTicketId;
    }

    public void setSubTicketId(String subTicketId) {
        this.subTicketId = subTicketId;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
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

    public String getNumberInfo() {
        return numberInfo;
    }

    public void setNumberInfo(String numberInfo) {
        this.numberInfo = numberInfo;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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

    public Double getFixBonusAmount() {
        return fixBonusAmount;
    }

    public void setFixBonusAmount(Double fixBonusAmount) {
        this.fixBonusAmount = fixBonusAmount;
    }

    public String getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(String saleInfo) {
        this.saleInfo = saleInfo;
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

    public Integer getBigBonus() {
        return bigBonus;
    }

    public void setBigBonus(Integer bigBonus) {
        this.bigBonus = bigBonus;
    }
}
