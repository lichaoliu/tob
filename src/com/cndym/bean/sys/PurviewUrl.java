package com.cndym.bean.sys;

import java.io.Serializable;

/**
 * User: MengJingyi
 * Date: 11-6-20 下午21:45
 * 权限表
 */
public class PurviewUrl implements Serializable {
    private static final long serialVersionUID = 723891404094361174L;
    private Long id;
    private String code;//权限字段
    private String servletName;//servlet名
    private String actionName;//action名
    private String url;//受限地址
    private String name;//权限名称
    private String codeFather;//主权限名称
    private String nameFather;//主权限名称字段
    private Integer sort;//排序

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCodeFather() {
		return codeFather;
	}
	public void setCodeFather(String codeFather) {
		this.codeFather = codeFather;
	}
	public String getNameFather() {
		return nameFather;
	}
	public void setNameFather(String nameFather) {
		this.nameFather = nameFather;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
