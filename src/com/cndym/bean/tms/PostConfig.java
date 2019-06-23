package com.cndym.bean.tms;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 朱林虎
 *
 */
public class PostConfig implements Serializable {
	
	private static final long serialVersionUID = 4081793933512338107L;
	
	private Integer id;
    private String postCode;//出票口
    private String postName;
    private String postClass;
    private Integer active;//是否可用 0 不可用 1 可用
    private Date createTime;
    private Date updateTime;
    private String backup1;
    private String backup2;
    private String backup3;

    public PostConfig() {
    }

    public PostConfig(String postCode, String postName,String postClass,Integer active) {
        this.postCode = postCode;
        this.postName = postName;
        this.postClass = postClass;
        this.active = active;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	public String getPostClass() {
		return postClass;
	}

	public void setPostClass(String postClass) {
		this.postClass = postClass;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

	public String getBackup2() {
		return backup2;
	}

	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}

	public String getBackup3() {
		return backup3;
	}

	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}
    
}
