package com.cndym.bean.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * User: mcs
 * Date: 11-7-24
 * Time: 下午3:10
 */
public class CooperationManages implements Serializable {

    private Long id;
    private Long managesId;
    private Long cooperationId;
    private Date createTime;

    public CooperationManages() {
    }

    public CooperationManages(Long id, Long managesId, Long cooperationId, Date createTime) {
        this.id = id;
        this.managesId = managesId;
        this.cooperationId = cooperationId;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManagesId() {
        return managesId;
    }

    public void setManagesId(Long managesId) {
        this.managesId = managesId;
    }

    public Long getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(Long cooperationId) {
        this.cooperationId = cooperationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
