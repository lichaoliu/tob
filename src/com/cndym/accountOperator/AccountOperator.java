package com.cndym.accountOperator;

/**
 * 作者：邓玉明
 * 时间：11-3-28 下午3:33
 * QQ：757579248
 * email：cndym@163.com
 */

public class AccountOperator {
    private Integer eventType;
    private String type;
    private String eventCode;
    private String name;
    private String operator;
    private String nickName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
