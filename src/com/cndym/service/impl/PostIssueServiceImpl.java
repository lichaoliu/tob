package com.cndym.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cndym.bean.tms.PostIssue;
import com.cndym.control.PostCodeUtil;
import com.cndym.dao.IPostIssueDao;
import com.cndym.service.IPostIssueService;
import com.cndym.utils.Utils;

@Component
public class PostIssueServiceImpl extends GenericServiceImpl<PostIssue, IPostIssueDao> implements IPostIssueService {
	@Autowired
	private IPostIssueDao postIssueDao;

	@PostConstruct
	private void daoImplInit() {
		super.setDaoImpl(postIssueDao);
	}

	@Override
	public List<PostIssue> getPostIssueByLotteryCodeAndIssue(String lotteryCode, String issue) {
		return postIssueDao.getPostIssueByLotteryCodeAndIssue(lotteryCode, issue);
	}

	@Override
	public PostIssue getPostIssueByLotteryCodeAndIssue(String lotteryCode, String issue, String postCode) {
		return postIssueDao.getPostIssueByLotteryCodeAndIssue(lotteryCode, issue, postCode);
	}

	@Override
	public void doUpdateStatus(int status, String lotteryCode, String issue) {
		postIssueDao.doUpdateStatus(status, lotteryCode, issue);
	}
	
	@Override
    public List<Map<String,Object>> getLotteryPostIssue(String lotteryCode, String postCode) {
		List<Map<String,Object>> dataList = postIssueDao.getLotteryPostIssue(lotteryCode, postCode);
        List<Map<String,Object>> lotteryList = new ArrayList<Map<String,Object>>();
        Map<String,Object> lotteryMap = null;
        String code = "";
        String name = "";
        String post = "";
        for (int index = 0; index < dataList.size(); index++) {
            lotteryMap = (Map<String,Object>) dataList.get(index);
            code = lotteryMap.get("lotteryCode").toString();
            name = lotteryMap.get("name").toString();
            post = lotteryMap.get("postCode").toString();
            
            String canSellPostCode = PostCodeUtil.getCanSellPostCode(code, name); 
            if(Utils.isNotEmpty(canSellPostCode) && canSellPostCode.contains(post)){
            	lotteryMap.put("memcachedIssue", name);
            }else{
            	lotteryMap.put("memcachedIssue", "");
            }
            lotteryList.add(lotteryMap);
        }
        return lotteryList;
    }
}
