package com.cndym.quartz;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.PostIssue;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.control.PostCodeUtil;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IPostIssueService;
import com.cndym.service.issueOperator.IIssueOperator;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * 作者：邓玉明 时间：11-4-30 下午4:26 QQ：757579248 email：cndym@163.com
 */
public class IssueClusterableJobBean extends QuartzJobBean {
	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long startTime = new Date().getTime();
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_ISSUE_QUARTZ));
		if (!lock)
			return;
		logger.info("running issue JOB start");
		IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
		IIssueOperator startIssueOperator = (IIssueOperator) SpringUtils.getBean("startIssueOperator");
		IIssueOperator endIssueOperator = (IIssueOperator) SpringUtils.getBean("endIssueOperator");
		Map<String, MainIssue> mainIssueMap = new HashMap<String, MainIssue>();
		try {
			XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
			String issueJson = (String) memcachedClientWrapper.getMemcachedClient().get(Constants.MEMCACHED_CURRENT_ISSUE_LIST);
			if (null != issueJson) {
				List<MainIssue> mainIssueList = JsonBinder.buildNormalBinder().getMapper().readValue(issueJson, new TypeReference<List<MainIssue>>() {
				});
				if (null != mainIssueList) {
					for (MainIssue mainIssue : mainIssueList) {
						if (mainIssue.getEndTime().getTime() < new Date().getTime()) {
							// 处理结期相关操作
							endIssueOperator.operator(mainIssue);
						} else {
							mainIssueMap.put(mainIssue.getLotteryCode() + mainIssue.getName(), mainIssue);
						}
					}
				}
			}

			// 当前期
			List<LotteryClass> list = LotteryList.getLotteryClassList();
			List<MainIssue> mainIssues = new ArrayList<MainIssue>();
			List<String> saleMainIssue = new ArrayList<String>();
			for (LotteryClass lotteryClass : list) {
				if (Constants.ALLOW_RESERVE_ISSUE_TYPE != lotteryClass.getAuto().intValue()) {
					continue;
				}
				MainIssue para = new MainIssue();
				para.setLotteryCode(lotteryClass.getCode());
				para.setStatuses(new Integer[] { 0, 1 });
				if (lotteryClass.getModify().intValue() == 1) {
					lotteryClass.setCurrent(10);
				}
				List<MainIssue> cainIssueList = mainIssueService.findIssueForStatus(para, lotteryClass.getCurrent(), 1, Constants.ASC).getPageContent();
				for (MainIssue mainIssue : cainIssueList) {
					// 过期了的期次
					if (mainIssue.getEndTime().getTime() < new Date().getTime()) {
						endIssueOperator.operator(mainIssue);
						continue;
					}
					logger.info("current_issue:lotteryCode(" + mainIssue.getLotteryCode() + "),name(" + mainIssue.getName() + "),status(" + mainIssue.getStatus() + "),startTime("
							+ mainIssue.getStartTime() + "),endTime(" + mainIssue.getEndTime() + "),sendStatus(" + mainIssue.getSendStatus() + ")");

					if (mainIssue.getStatus().intValue() == Constants.ISSUE_STATUS_WAIT || mainIssue.getSendStatus().intValue() == Constants.ISSUE_SALE_WAIT) {
						String canSellPostCode = PostCodeUtil.getCanSellPostCode(mainIssue.getLotteryCode(), mainIssue.getName());
						logger.info("canSellPostCode:" + canSellPostCode);
						if (Utils.isNotEmpty(canSellPostCode)) {
							startIssueOperator.operator(mainIssue);
						}
					}
					mainIssues.add(mainIssue);
				}

				List<Map> subList = mainIssueService.getIssueListByLotteryAndTime(lotteryClass.getCode());
				StringBuffer buffer = new StringBuffer();
				for (Map issue : subList) {
					buffer.append(",");
					buffer.append(issue.get("name"));
				}
				if (buffer.length() > 0) {
					saleMainIssue.add(lotteryClass.getCode() + ":" + buffer.substring(1));
				}
			}
			String jsonStr = JsonBinder.buildNonDefaultBinder().toJson(mainIssues);
			String saleJsonStr = JsonBinder.buildNonDefaultBinder().toJson(saleMainIssue);
			memcachedClientWrapper.getMemcachedClient().set(Constants.MEMCACHED_CURRENT_ISSUE_LIST, 0, jsonStr);
			memcachedClientWrapper.getMemcachedClient().set(Constants.MEMCACHED_SALE_ISSUE_LIST, 0, saleJsonStr);
			logger.info("saleMainIssueJson:" + saleJsonStr);
		} catch (Exception e) {
			logger.info("", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_ISSUE_QUARTZ));
		logger.info("running issue JOB end (" + (new Date().getTime() - startTime) + "ms)");
	}
}
