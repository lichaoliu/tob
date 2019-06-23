package com.cndym.adapter.tms.bean;

import java.io.Serializable;

public class BuyNumber implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6691206335201468070L;

    private String buyNumber;// 投注号码
    private String playId;// 玩法ID
    private String modelId;// 模式Id
    private int sum;// 投注金额
    private int count;// 注数
    private String numberFormat;
    private String uploadMatch;

    public String getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(String buyNumber) {
        this.buyNumber = buyNumber;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public String getUploadMatch() {
        return uploadMatch;
    }

    public void setUploadMatch(String uploadMatch) {
        this.uploadMatch = uploadMatch;
    }
}
