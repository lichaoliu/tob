package com.cndym.servlet.manages.bean;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 11-11-1
 * Time: 下午7:13
 */
public class Award implements Comparable<Award>{
    private String amount;
    private String count;
    private String cityCount;
    private Integer winLevel;

    public Award(){
    }

    public Award(Integer winLevel, String amount, String count, String cityCount) {
        this.amount = amount;
        this.count = count;
        this.cityCount = cityCount;
        this.winLevel = winLevel;
    }

    public Award(String amount, String count, String cityCount) {
        this.amount = amount;
        this.count = count;
        this.cityCount = cityCount;
    }

    public Integer getWinLevel() {
        return winLevel;
    }

    public void setWinLevel(Integer winLevel) {
        this.winLevel = winLevel;
    }

    public String getAmount() {
        return amount;
    }

    public String getCount() {
        return count;
    }

    public String getCityCount() {
        return cityCount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCityCount(String cityCount) {
        this.cityCount = cityCount;
    }

    @Override
    public int compareTo(Award award) {
        if (this.winLevel>award.getWinLevel()){
            return 1;
        }
        return 0;
    }
}
