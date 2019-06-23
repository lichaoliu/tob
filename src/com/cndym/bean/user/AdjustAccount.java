package com.cndym.bean.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 作者：邓玉明
 * 时间：11-6-20 上午9:09
 * QQ：757579248
 * email：cndym@163.com
 */
public class AdjustAccount implements Serializable {
    private static final long serialVersionUID = -5398163013633490419L;
    private Long id;
    private String userCode;
    private Double bonusAmount;//中奖金额
    private Double rechargeAmount;//充值金额
    private Double presentAmount;//赠送(奖励)金额
    private Double freezeAmount;//冻结金额
    private Date createTime;
    private String body;
    private String adjustId;
    private String operator;//操作人
    private String backup1;
    private String backup2;
    private String backup3;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAdjustId() {
		return adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	public AdjustAccount() {
    }

    public AdjustAccount(Long id, String userCode, Double bonusAmount, Double rechargeAmount, Double presentAmount, Double freezeAmount, Date createTime, String body, String adjustId, String operator, String backup1, String backup2, String backup3) {
        this.id = id;
        this.userCode = userCode;
        this.bonusAmount = bonusAmount;
        this.rechargeAmount = rechargeAmount;
        this.presentAmount = presentAmount;
        this.freezeAmount = freezeAmount;
        this.createTime = createTime;
        this.body = body;
        this.adjustId = adjustId;
        this.operator = operator;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
}
