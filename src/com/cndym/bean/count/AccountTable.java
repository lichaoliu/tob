package com.cndym.bean.count;

import java.io.Serializable;
import java.util.Date;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午2:03
 */
public class AccountTable implements Serializable {

    private String id;

    private String sid;

    private String name;

    private String currDate;

    private Double payAmount;

    private Long payNum;

    private Double failureAmount;

    private Long failureNum;

    private Double bonusAmount;

    private Long bonusNum;

    private Long commissionNum;

    private Double commissionAmount;

    private Double editAccountJia;

    private Double editAccountJian;

    private Double bonusAmountNew;//操作后可用中奖金额

    private Double rechargeAmountNew;//操作后可用的充值金额

    private Double presentAmountNew;//操作后可用的赠送金额

    private Double freezeAmountNew;//操作后冻结金额

    private Date createTime;

    //查询条件
    private String startDate;

    private String endDate;

    public AccountTable() {
    }

    public AccountTable(String id, String sid, String name, String currDate, Double payAmount, Long payNum, Double failureAmount, Long failureNum, Double bonusAmount, Long bonusNum, Long commissionNum, Double commissionAmount, Double editAccountJia, Double editAccountJian, Double bonusAmountNew, Double rechargeAmountNew, Double presentAmountNew, Double freezeAmountNew, Date createTime) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.currDate = currDate;
        this.payAmount = payAmount;
        this.payNum = payNum;
        this.failureAmount = failureAmount;
        this.failureNum = failureNum;
        this.bonusAmount = bonusAmount;
        this.bonusNum = bonusNum;
        this.commissionNum = commissionNum;
        this.commissionAmount = commissionAmount;
        this.editAccountJia = editAccountJia;
        this.editAccountJian = editAccountJian;
        this.bonusAmountNew = bonusAmountNew;
        this.rechargeAmountNew = rechargeAmountNew;
        this.presentAmountNew = presentAmountNew;
        this.freezeAmountNew = freezeAmountNew;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrDate() {
        return currDate;
    }

    public void setCurrDate(String currDate) {
        this.currDate = currDate;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Long getPayNum() {
        return payNum;
    }

    public void setPayNum(Long payNum) {
        this.payNum = payNum;
    }

    public Double getFailureAmount() {
        return failureAmount;
    }

    public void setFailureAmount(Double failureAmount) {
        this.failureAmount = failureAmount;
    }

    public Long getFailureNum() {
        return failureNum;
    }

    public void setFailureNum(Long failureNum) {
        this.failureNum = failureNum;
    }

    public Double getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Double bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Long getBonusNum() {
        return bonusNum;
    }

    public void setBonusNum(Long bonusNum) {
        this.bonusNum = bonusNum;
    }

    public Long getCommissionNum() {
        return commissionNum;
    }

    public void setCommissionNum(Long commissionNum) {
        this.commissionNum = commissionNum;
    }

    public Double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(Double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public Double getEditAccountJia() {
        return editAccountJia;
    }

    public void setEditAccountJia(Double editAccountJia) {
        this.editAccountJia = editAccountJia;
    }

    public Double getEditAccountJian() {
        return editAccountJian;
    }

    public void setEditAccountJian(Double editAccountJian) {
        this.editAccountJian = editAccountJian;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
