package com.cndym.adapter.tms.bean;

/**
 * 作者：邓玉明 时间：11-4-6 下午4:01 QQ：757579248 email：cndym@163.com
 */
public class IssueInfo implements Comparable<IssueInfo> {
	private int db;
	private String name;
	private int status;
	private int sendStatus;

	private IssueInfo(int db, String name) {
		this.db = db;
		this.name = name;
	}

	public static IssueInfo forInstance(int db, String issue) {
		return new IssueInfo(db, issue);
	}

	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	@Override
	public int compareTo(IssueInfo issueInfo) {
		if (Long.parseLong("1" + getName()) > Long.parseLong("1" + issueInfo.getName())) {
			return 1;
		}
		return 0;
	}
}
