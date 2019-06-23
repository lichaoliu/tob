package com.cndym.quartz.autotest;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.autotest.AutoSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.ByteCodeUtil;

public class AutoGpBonusInfoQueryJobBean extends QuartzJobBean {
	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long startTime = new Date().getTime();
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_AUTO_GP_BONUS_INFO_QUERY));
		if (!lock)
			return;
		logger.info("running auto bonusInfoQuery JOB start");
		try {
			IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("autoSendClientImpl");

			String postLotteryCode = AutoSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
			if (Utils.isNotEmpty(postLotteryCode)) {
				String[] lotteryCodeArr = postLotteryCode.split(",");
				for (String lotteryCode : lotteryCodeArr) {
					MainIssue para = new MainIssue();
					para.setLotteryCode(lotteryCode);
					PageBean pageBean = mainIssueService.findIssueForGetBonusNumber(para, 50, 1);
					List<MainIssue> issueList = pageBean.getPageContent();
					for (MainIssue mainIssue : issueList) {
						if (null == mainIssue.getBonusNumber() || "-".equals(mainIssue.getBonusNumber())) {
							StringBuilder xml = new StringBuilder("<body lotteryCode=\"");
							xml.append(mainIssue.getLotteryCode());
							xml.append("\" issue=\"");
							xml.append(mainIssue.getName());
							xml.append("\"/>");
							sendClient.bonusInfoQuery(ByteCodeUtil.xmlToObject(xml.toString()));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("auto bonusInfoQuery JOB error:" + e.getMessage());
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_AUTO_GP_BONUS_INFO_QUERY));
		logger.info("running auto bonusInfoQuery JOB end (" + (new Date().getTime() - startTime) + "ms)");

	}

	public static void main(String[] args) {
	}
}
