package com.cndym.servlet.manages.bean;

/**
 * User: 邓玉明
 * Date: 11-6-13 下午11:15
 */
public class BonusClass implements Comparable<BonusClass> {
    private Integer classes;//奖级
    private Long total;//中励总注数
    private String amount;//等级金额
    private Long cityCount;
    private String className;//奖级名称


    public BonusClass() {
    }

    public BonusClass(Integer classes, Long total, String amount) {
        this.classes = classes;
        this.total = total;
        this.amount = amount;
    }

    public BonusClass(Integer classes, Long total, String amount, Long cityCount) {
        this.classes = classes;
        this.total = total;
        this.amount = amount;
        this.cityCount = cityCount;
    }

    public BonusClass(Integer classes, Long total, String amount, Long cityCount, String className) {
        this.classes = classes;
        this.total = total;
        this.amount = amount;
        this.cityCount = cityCount;
        this.className = className;
    }

    public Long getCityCount() {
        return cityCount;
    }

    public void setCityCount(Long cityCount) {
        this.cityCount = cityCount;
    }

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
        this.classes = classes;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public int compareTo(BonusClass bonusClass) {
        if (this.classes > bonusClass.getClasses()) {
            return 1;
        }
        return 0;
    }
}
