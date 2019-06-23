package com.cndym.bean.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-5-30 下午9:58
 */

public class PurviewGroup implements Serializable {
    private static final long serialVersionUID = -5601340441550251577L;
    private Long id;
    private String purviewGroupCode;
    private String name;
    private Date createTime;

    public PurviewGroup() {
    }


    public PurviewGroup(Long id, String purviewGroupCode, String name, Date createTime) {
        this.id = id;
        this.purviewGroupCode = purviewGroupCode;
        this.name = name;
        this.createTime = createTime;
    }

    public String getPurviewGroupCode() {
        return purviewGroupCode;
    }

    public void setPurviewGroupCode(String purviewGroupCode) {
        this.purviewGroupCode = purviewGroupCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
