package com.cndym.servlet.manages.chart.bean;

/**
 * 查询30天票量作bean用
 */
public class TicketsDetail {
	private String name;
	private Object[] data;
	public TicketsDetail() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	
}
