package com.cndym.dao;

import java.util.List;
import java.util.Map;

import com.cndym.bean.tms.PostIssue;

public interface IPostIssueDao extends IGenericDao<PostIssue> {

	public List<PostIssue> getPostIssueByLotteryCodeAndIssue(String lotteryCode, String issue);

	public PostIssue getPostIssueByLotteryCodeAndIssue(String lotteryCode, String issue, String postCode);

	public void doUpdateStatus(int status, String lotteryCode, String issue);
	
	public List<Map<String,Object>> getLotteryPostIssue(String lotteryCode, String postCode);
	
}
