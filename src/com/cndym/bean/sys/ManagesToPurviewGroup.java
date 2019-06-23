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
public class ManagesToPurviewGroup implements Serializable {
    private static final long serialVersionUID = -5601340441550251597L;
    private Long id;
    private Long managerId;
    private String purviewGroupCode;
    private Date createTime;

    public ManagesToPurviewGroup() {
    }

    public ManagesToPurviewGroup(Long id, Long managerId, String purviewGroupCode, Date createTime) {
        this.id = id;
        this.managerId = managerId;
        this.purviewGroupCode = purviewGroupCode;
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

    public String getPurviewGroupCode() {
        return purviewGroupCode;
    }

    public void setPurviewGroupCode(String purviewGroupCode) {
        this.purviewGroupCode = purviewGroupCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
