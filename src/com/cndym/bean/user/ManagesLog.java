package com.cndym.bean.user;

import java.util.Date;

/**
 **USER:MengJingyi
 **TIME:2011 2011-5-23 下午11:00:23
 */

public class ManagesLog {
	private Long id;
	private String adminName;//操作管理员
	private String type;//操作类型
	private String description;//描述
	private Date createTime;//创建时间
	private String ip;//操作ip
	private Integer operatingType;//类型（0:默认类型，1:登录/注销，2: 会员账户被冻结或解冻，3: 管理员的提现审核， 4:管理员更改额度操作，5:日终数据处理操作）

	
	public Integer getOperatingType() {
		return operatingType;
	}
	public void setOperatingType(Integer operatingType) {
		this.operatingType = operatingType;
	}
	public ManagesLog() {
		super();
	}
	public ManagesLog(Long id, String adminName, String type,
			String description, Date createTime) {
		super();
		this.id = id;
		this.adminName = adminName;
		this.type = type;
		this.description = description;
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
