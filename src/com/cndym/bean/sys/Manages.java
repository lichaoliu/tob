package com.cndym.bean.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * User: 邓玉明
 * Date: 11-5-30 下午10:06
 */
public class Manages implements Serializable {
    private static final long serialVersionUID = 723891404094361174L;
    private Long id;
    private String userName;
    private String passWord;
    private String mobile;
    private Date createTime;
    private String loginIp;//上次登陆IP
    private Date loginTime;//上次登陆时间
    private String body;//备注
    
    private String phone;//座机
    private String departments;//部门
    private String post;//职位
    private String email;//邮箱
    private String permissions;//权限
    private Integer status;//状态
    private String realName;//姓名
    private Integer type;//0 业务管理 1 联盟查询系统管理员 2 联盟管理员
    
    
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public Manages() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Manages(Long id, String userName, String passWord, String mobile, Date createTime, String loginIp, Date loginTime, String body,Integer type) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.mobile = mobile;
        this.createTime = createTime;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.body = body;
        this.type = type;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
