package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明 Date: 11-3-27 下午6:27
 */
public class BonusTicket implements Serializable {
    private static final long serialVersionUID = -5277193494280077954L;
    private Long id;
    private String outTicketId;//外部票号（partnersCode+outTicketId唯一索引）
    private String sid;//代理商号
    private String userCode;
    private String orderId;//批次号（partnersCode+orderId唯一索引）
    private String ticketId;//内部票号（唯一索引）
    private String lotteryCode;
    private String playCode;
    private String pollCode;
    private String issue;
    private String postCode;//出票口
    private Double amount;
    private Double bonusAmount;
    private String bonusClass;
    private Double fixBonusAmount;
    private String startGameId;
    private String endGameId;
    private Date createTime;
    private Date sendTime;
    private Date returnTime;
    private Date bonusTime;
    private Integer bigBonus;//是否是大奖，0是普通，1是大奖
    private String backup1;
    private String backup2;
    private String backup3;


    public BonusTicket() {
    }

    public BonusTicket(String ticketId) {
        this.ticketId = ticketId;
    }

    public BonusTicket(String ticketId, Double bonusAmount, String bonusClass, Double fixBonusAmount) {
        this.ticketId = ticketId;
        this.bonusAmount = bonusAmount;
        this.bonusClass = bonusClass;
        this.fixBonusAmount = fixBonusAmount;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getBonusClass() {
        return bonusClass;
    }

    public void setBonusClass(String bonusClass) {
        this.bonusClass = bonusClass;
    }

    public Double getFixBonusAmount() {
        return fixBonusAmount;
    }

    public void setFixBonusAmount(Double fixBonusAmount) {
        this.fixBonusAmount = fixBonusAmount;
    }

    public String getOutTicketId() {
        return outTicketId;
    }

    public void setOutTicketId(String outTicketId) {
        this.outTicketId = outTicketId;
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

    public String getStartGameId() {
        return startGameId;
    }

    public void setStartGameId(String startGameId) {
        this.startGameId = startGameId;
    }

    public String getEndGameId() {
        return endGameId;
    }

    public void setEndGameId(String endGameId) {
        this.endGameId = endGameId;
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
