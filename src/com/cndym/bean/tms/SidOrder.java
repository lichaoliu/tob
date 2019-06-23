package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * User: mcs
 * Date: 12-11-11
 * Time: 下午4:50
 */
public class SidOrder implements Serializable {

    private String id;

    private String sid;

    private String orderId;

    private String sidOrderId;

    private Date createTime;

    public SidOrder() {
    }

    public SidOrder(String id,String sid, String orderId, String sidOrderId, Date createTime) {
        this.id = id;
        this.sid = sid;
        this.orderId = orderId;
        this.sidOrderId = sidOrderId;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getSidOrderId() {
        return sidOrderId;
    }

    public void setSidOrderId(String sidOrderId) {
        this.sidOrderId = sidOrderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
