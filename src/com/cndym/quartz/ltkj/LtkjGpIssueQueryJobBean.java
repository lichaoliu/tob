/**
 * 
 */
package com.cndym.quartz.ltkj;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;

/**
 * @author 朱林虎
 * 高频查询期次
 */
public class LtkjGpIssueQueryJobBean extends QuartzJobBean {

	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		long startTime = new Date().getTime();
		logger.info("running LtkjGpIssueQueryJob start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_ISSUE_QUERY));
		if (!lock)
			return;
		try {
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
	
			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
			if (Utils.isNotEmpty(postLotteryCode)) {
				String[] lotteryCodeArr = postLotteryCode.split(",");
				for (String lotteryCode : lotteryCodeArr) {
					StringBuilder xml = new StringBuilder("<body lotteryCode=\"");
					xml.append(lotteryCode);
					xml.append("\"/>");
					sendClient.issueQuery(ByteCodeUtil.xmlToObject(xml.toString()));
				}
			}
		} catch (Exception e) {
			logger.error("LtkjGpIssueQueryJob error:", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_ISSUE_QUERY));
		logger.info("running LtkjGpIssueQueryJob end (" + (new Date().getTime() - startTime) + "ms)");

	}
}
