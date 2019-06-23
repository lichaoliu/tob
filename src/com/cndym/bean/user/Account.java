package com.cndym.bean.user;

import java.io.Serializable;

/**
 * User: 邓玉明
 * Date: 11-3-27 下午3:58
 */
public class Account implements  Serializable{
    private static final long serialVersionUID = 8305268878858528657L;
    private Long id;
    private String userCode;
    private Double bonusAmount;//可用中奖金额
    private Double rechargeAmount;//可用的充值金额
    private Double presentAmount;//可用的赠送(奖励)金额
    private Double freezeAmount;//冻结金额
    private Double bonusTotal;//中奖总金额
    private Double rechargeTotal;//充值总金额
    private Double presentTotal;//赠送(奖励)金额
    private Double payTotal;//支付总金额
    private Double drawTotal;//提现总金额
    private Integer score = 0;//积分


    public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Account() {
    }

    public Account(Long id, String userCode, Double bonusAmount, Double rechargeAmount, Double presentAmount, Double freezeAmount, Double bonusTotal, Double rechargeTotal, Double presentTotal, Double payTotal, Double drawTotal) {
        this.id = id;
        this.userCode = userCode;
        this.bonusAmount = bonusAmount;
        this.rechargeAmount = rechargeAmount;
        this.presentAmount = presentAmount;
        this.freezeAmount = freezeAmount;
        this.bonusTotal = bonusTotal;
        this.rechargeTotal = rechargeTotal;
        this.presentTotal = presentTotal;
        this.payTotal = payTotal;
        this.drawTotal = drawTotal;
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

    public Double getBonusTotal() {
        return bonusTotal;
    }

    public void setBonusTotal(Double bonusTotal) {
        this.bonusTotal = bonusTotal;
    }

    public Double getRechargeTotal() {
        return rechargeTotal;
    }

    public void setRechargeTotal(Double rechargeTotal) {
        this.rechargeTotal = rechargeTotal;
    }

    public Double getPresentTotal() {
        return presentTotal;
    }

    public void setPresentTotal(Double presentTotal) {
        this.presentTotal = presentTotal;
    }

    public Double getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(Double payTotal) {
        this.payTotal = payTotal;
    }

    public Double getDrawTotal() {
        return drawTotal;
    }

    public void setDrawTotal(Double drawTotal) {
        this.drawTotal = drawTotal;
    }
}
