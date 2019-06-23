package com.cndym.bean.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统提醒消息
 * User: 梅传颂
 * Date: 11-6-19
 * Time: 下午6:39
 */
public class SystemMessage implements Serializable {

    private Integer id;
    //操作人code
    private String operatorCode;
    //接收人姓名
    private String operatorName;
    //消息标题
    private String title;
    //消息内容
    private String body;
    //发送时间
    private Date createTime;

    public SystemMessage(){

    }

    public SystemMessage(Integer id, String operatorCode, String operatorName, String title, String body, Integer status, Date createTime) {
        this.id = id;
        this.operatorCode = operatorCode;
        this.operatorName = operatorName;
        this.title = title;
        this.body = body;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
