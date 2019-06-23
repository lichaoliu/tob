package com.cndym.bean.user;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午4:14
 */
public class AccountLog implements Serializable {
    private static final long serialVersionUID = 3529532806061025845L;
    private Long id;
    private String userCode;
    private String orderId;//批次号
    private String ticketId;//票号
    private String sid;//商户号
    private Integer eventType;//操作类型(0,出帐，1,入帐)
    private String type;//二级类型
    private String eventCode;//操作业务码(001:充值,)
    private Double bonusAmount;//操作中奖金额
    private Double rechargeAmount;//操作的充值金额
    private Double presentAmount;//操作的赠送金额
    private Double freezeAmount;//冻结金额
    private Double bonusAmountNew;//操作后可用中奖金额
    private Double rechargeAmountNew;//操作后可用的充值金额
    private Double presentAmountNew;//操作后可用的赠送金额
    private Double freezeAmountNew;//操作后冻结金额
    private Date createTime;
    private String memo;//备注
    private String index;//唯一性索引使用
    private String backup1;//退款
    private String backup2;//
    private String backup3;

     private String[] eventCodeArr;


    public AccountLog() {
    }

    public AccountLog(Long id, String userCode, String orderId, String ticketId, String sid, Integer eventType, String type, String eventCode, Double bonusAmount, Double rechargeAmount, Double presentAmount, Double freezeAmount, Double bonusAmountNew, Double rechargeAmountNew, Double presentAmountNew, Double freezeAmountNew, Date createTime, String memo, String index, String backup1, String backup2, String backup3) {
        this.id = id;
        this.userCode = userCode;
        this.orderId = orderId;
        this.ticketId = ticketId;
        this.sid = sid;
        this.eventType = eventType;
        this.type = type;
        this.eventCode = eventCode;
        this.bonusAmount = bonusAmount;
        this.rechargeAmount = rechargeAmount;
        this.presentAmount = presentAmount;
        this.freezeAmount = freezeAmount;
        this.bonusAmountNew = bonusAmountNew;
        this.rechargeAmountNew = rechargeAmountNew;
        this.presentAmountNew = presentAmountNew;
        this.freezeAmountNew = freezeAmountNew;
        this.createTime = createTime;
        this.memo = memo;
        this.index = index;
        this.backup1 = backup1;
        this.backup2 = backup2;
        this.backup3 = backup3;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Double getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Double getPresentAmount() {
        return presentAmount;
    }

    public void setPresentAmount(Double presentAmount) {
        this.presentAmount = presentAmount;
    }

    public Double getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(Double freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public Double getBonusAmountNew() {
        return bonusAmountNew;
    }

    public void setBonusAmountNew(Double bonusAmountNew) {
        this.bonusAmountNew = bonusAmountNew;
    }

    public Double getRechargeAmountNew() {
        return rechargeAmountNew;
    }

    public void setRechargeAmountNew(Double rechargeAmountNew) {
        this.rechargeAmountNew = rechargeAmountNew;
    }

    public Double getPresentAmountNew() {
        return presentAmountNew;
    }

    public void setPresentAmountNew(Double presentAmountNew) {
        this.presentAmountNew = presentAmountNew;
    }

    public Double getFreezeAmountNew() {
        return freezeAmountNew;
    }

    public void setFreezeAmountNew(Double freezeAmountNew) {
        this.freezeAmountNew = freezeAmountNew;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public String[] getEventCodeArr() {
        return eventCodeArr;
    }

    public void setEventCodeArr(String[] eventCodeArr) {
        this.eventCodeArr = eventCodeArr;
    }
}
