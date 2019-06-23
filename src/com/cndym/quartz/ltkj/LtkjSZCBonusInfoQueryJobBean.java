/**
 * 
 */
package com.cndym.quartz.ltkj;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * @author 李娜
 * 数字彩查询开奖号码
 */
public class LtkjSZCBonusInfoQueryJobBean extends QuartzJobBean {

	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		long startTime = new Date().getTime();
		logger.info("running LtkjSZCBonusInfoQueryJobBean start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_SZC_BONUS_QUERY));
		if (!lock)
			return;
		try {
			IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_LOTTERY");
			logger.info("postLotteryCode----------:"+postLotteryCode+"  ");
			if (Utils.isNotEmpty(postLotteryCode)) {

				String[] lotteryCodeArr = postLotteryCode.split(",");
				for (String lotteryCode : lotteryCodeArr) {
					MainIssue para = new MainIssue();
					para.setLotteryCode(lotteryCode);
					PageBean pageBean = mainIssueService.findIssueForGetBonusNumber(para, 50, 1);
					List<MainIssue> issueList = pageBean.getPageContent();
					logger.info("lotteryCode:"+lotteryCode+"  issueList.size:"+issueList.size());
					for (MainIssue mainIssue : issueList) {
						String issue = mainIssue.getName();
						if (null == mainIssue.getBonusNumber() || "-".equals(mainIssue.getBonusNumber())) {
							StringBuilder xml = new StringBuilder("<body lotteryCode=\"");
							xml.append(mainIssue.getLotteryCode());
							xml.append("\" issue=\"");
							xml.append(issue);
							xml.append("\"/>");
							logger.info("getLotteryCode:"+mainIssue.getLotteryCode()+"  issue"+issue);
							sendClient.bonusInfoQuery(ByteCodeUtil.xmlToObject(xml.toString()));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("LtkjSZCBonusInfoQueryJobBean error:", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_SZC_BONUS_QUERY));
		logger.info("running LtkjGpBonusInfoQueryJob end (" + (new Date().getTime() - startTime) + "ms)");

	}
}
