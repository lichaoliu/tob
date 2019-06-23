package com.cndym.service.subIssue.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cndym.bean.tms.SubIssueForGjb;
import com.cndym.service.ISubIssueForGjbService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class Lottery202SubIssueOperator extends BaseSubIssueOperator {

	private static final Logger logger = Logger.getLogger(Lottery202SubIssueOperator.class);

	private final String LOTTERY_CODE = "202";
	@Resource
	private ISubIssueForGjbService subIssueForGjbService;

	@Override
	public void operator(String xml) {

		XmlBean xmlBean = ByteCodeUtil.xmlToObject(xml);
		if (null == xmlBean) {
			logger.info(LOTTERY_CODE+" is null!");
		} else {
	        logger.info("获取冠军杯对阵:" + xmlBean.getFirst("issu"));
	        try {
		        XmlBean issueInfo = xmlBean.getFirst("issu");
		        String endtime = issueInfo.attribute("endtime");
		        String status = issueInfo.attribute("status");
		        List<XmlBean> matchList = issueInfo.getAll("match");
		        String issue = issueInfo.attribute("issue");
		        if (Utils.isNotEmpty(matchList)) {
		        	Map<String, SubIssueForGjb> gjbMap = new HashMap<String, SubIssueForGjb>();
		        	List<SubIssueForGjb> gjbList = subIssueForGjbService.getListByIssue(issue);
		        	logger.info("gjbList="+gjbList.size());
		        	if (Utils.isNotEmpty(gjbList)) {
		        		for (SubIssueForGjb subGjb : gjbList) {
		        			gjbMap.put(subGjb.getIssue()+subGjb.getSn(), subGjb);
		        		}
		        	}
		            for (XmlBean match : matchList) {
		                String sn = match.attribute("sn");
		                String sp = match.attribute("sp");
		                String name = match.attribute("name");
		                String league = match.attribute("league");
	
		                try {
			                SubIssueForGjb subIssueForGjb = null;
			                if(gjbMap.containsKey(issue + sn)) {
			                	subIssueForGjb = gjbMap.get(issue + sn);
				                subIssueForGjb.setSp(new Double(sp));
				                subIssueForGjb.setStatus(new Integer(status));
				                subIssueForGjb.setEndTime(Utils.formatDate(endtime, "yyyyMMddHHmmss"));
				                subIssueForGjb.setUpdateTime(new Date());
				                subIssueForGjbService.update(subIssueForGjb);
			                } else {
			                	subIssueForGjb = new SubIssueForGjb();
				                subIssueForGjb.setIssue(issue);
				                subIssueForGjb.setSn(sn);
				                subIssueForGjb.setLeague(league);
				                subIssueForGjb.setTeam(name);
				                subIssueForGjb.setSp(new Double(sp));
				                subIssueForGjb.setStatus(new Integer(status));
				                subIssueForGjb.setCreateTime(new Date());
				                subIssueForGjb.setEndTime(Utils.formatDate(endtime, "yyyyMMddHHmmss"));
				                subIssueForGjb.setUpdateTime(new Date());
				                subIssueForGjbService.save(subIssueForGjb);
			                }
		                } catch (Exception e) {
		                	logger.error("", e);
		                }
		            }
		        }
	        } catch(Exception e) {
	        	logger.error("", e);
			}
		}
	}
	
    public ISubIssueForGjbService getSubIssueForGjbService() {
        return subIssueForGjbService; 
    }

    public void setSubIssueForGjbService(ISubIssueForGjbService subIssueForGjbService) {
        this.subIssueForGjbService = subIssueForGjbService;
    }
}
