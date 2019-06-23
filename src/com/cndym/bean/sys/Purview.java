package com.cndym.bean.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-5-30 下午9:58
 */

/**
 * 权限表
 */
public class Purview implements Serializable{
    private static final long serialVersionUID = -5601340441550251597L;
    private Long id;
    private Long managerId;
    private String purviewCode;//权限编码
    private Date createTime;

    public Purview() {
    }

    public Purview(Long id, Long managerId, String purviewCode, Date createTime) {
        this.id = id;
        this.managerId = managerId;
        this.purviewCode = purviewCode;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getPurviewCode() {
        return purviewCode;
    }

    public void setPurviewCode(String purviewCode) {
        this.purviewCode = purviewCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
