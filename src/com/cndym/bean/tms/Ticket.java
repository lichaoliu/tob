package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明 Date: 11-3-27 下午6:27
 */
public class Ticket implements Serializable {
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
    private Integer item;
    private Integer multiple;
    private String numberInfo;
    private String userInfo;
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
    private String bonusClass;
    private Double fixBonusAmount;
    private String startGameId;
    private String endGameId;
    private Date gameStartTime;
    private Date gameEndTime;
    private Date sendTime;
    private Date returnTime;
    private Date bonusTime;
    private String saleInfo;//竞彩的返回串号
    private Integer bigBonus;//是否是大奖，0是普通，1是大奖
    private Integer duiJiangStatus;//是否兑奖 0 未兑奖 1 已兑奖
    private Date duiJiangTime; //兑奖时间
    private String backup1;
    private String backup2;
    private String backup3;
    private String sidOutTicketId;//接入商票索引字段

    private Integer sumTicket; //总票数
    private Integer successTicket;//成功票数
    private Integer failureTicket;//失败票数
    private Double successAmount;//成功金额
    private Double failureAmount;//失败金额


    public Ticket() {
    }

    public Ticket(String ticketId) {
        this.ticketId = ticketId;
    }

    public Ticket(String ticketId, Double bonusAmount, String bonusClass, Double fixBonusAmount) {
        this.ticketId = ticketId;
        this.bonusAmount = bonusAmount;
        this.bonusClass = bonusClass;
        this.fixBonusAmount = fixBonusAmount;
    }

    public Ticket(String ticketId, String lotteryCode, String issue, Double bonusAmount, Date returnTime) {
        this.ticketId = ticketId;
        this.lotteryCode = lotteryCode;
        this.issue = issue;
        this.bonusAmount = bonusAmount;
        this.returnTime = returnTime;
    }

    public Ticket(Long id, String outTicketId, String sid, String userCode, String orderId, String ticketId, String lotteryCode, String playCode, String pollCode, String issue, Integer item, Integer multiple, String numberInfo, String userInfo, String saleCode, String errCode, String errMsg, String postCode, Double amount, Integer ticketStatus, Integer bonusStatus, Double bonusAmount, Date createTime, Date acceptTime, String bonusClass, Double fixBonusAmount, String startGameId, String endGameId, Date gameStartTime, Date gameEndTime, Date sendTime, Date returnTime, Date bonusTime, String saleInfo, Integer bigBonus, Integer duiJiangStatus, Date duiJiangTime, String backup1, String backup2, String backup3, String sidOutTicketId, Integer sumTicket, Integer successTicket, Integer failureTicket, Double successAmount, Double failureAmount) {
        this.id = id;
        this.outTicketId = outTicketId;
        this.sid = sid;
        this.userCode = userCode;
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.lotteryCode = lotteryCode;
        this.playCode = playCode;
        this.pollCode = pollCode;
        this.issue = issue;
        this.item = item;
        this.multiple = multiple;
        this.numberInfo = numberInfo;
        this.userInfo = userInfo;
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
        this.bonusClass = bonusClass;
        this.fixBonusAmount = fixBonusAmount;
        this.startGameId = startGameId;
        this.endGameId = endGameId;
        this.gameStartTime = gameStartTime;
        this.gameEndTime = gameEndTime;
        this.sendTime = sendTime;
        this.returnTime = returnTime;
        this.bonusTime = bonusTime;
        this.saleInfo = saleInfo;
        this.bigBonus = bigBonus;
        this.duiJiangStatus = duiJiangStatus;
        this.duiJiangTime = duiJiangTime;
        this.backup1 = backup1;
        this.backup2 = backup2;
        this.backup3 = backup3;
        this.sidOutTicketId = sidOutTicketId;
        this.sumTicket = sumTicket;
        this.successTicket = successTicket;
        this.failureTicket = failureTicket;
        this.successAmount = successAmount;
        this.failureAmount = failureAmount;
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

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
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

    public Date getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(Date gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public Date getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(Date gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public Integer getDuiJiangStatus() {
        return duiJiangStatus;
    }

    public void setDuiJiangStatus(Integer duiJiangStatus) {
        this.duiJiangStatus = duiJiangStatus;
    }

    public Date getDuiJiangTime() {
        return duiJiangTime;
    }

    public void setDuiJiangTime(Date duiJiangTime) {
        this.duiJiangTime = duiJiangTime;
    }

    public String getSidOutTicketId() {
        return sidOutTicketId;
    }

    public void setSidOutTicketId(String sidOutTicketId) {
        this.sidOutTicketId = sidOutTicketId;
    }

    public Integer getSumTicket() {
        return sumTicket;
    }

    public void setSumTicket(Integer sumTicket) {
        this.sumTicket = sumTicket;
    }

    public Integer getSuccessTicket() {
        return successTicket;
    }

    public void setSuccessTicket(Integer successTicket) {
        this.successTicket = successTicket;
    }

    public Integer getFailureTicket() {
        return failureTicket;
    }

    public void setFailureTicket(Integer failureTicket) {
        this.failureTicket = failureTicket;
    }

    public Double getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(Double successAmount) {
        this.successAmount = successAmount;
    }

    public Double getFailureAmount() {
        return failureAmount;
    }

    public void setFailureAmount(Double failureAmount) {
        this.failureAmount = failureAmount;
    }
}
