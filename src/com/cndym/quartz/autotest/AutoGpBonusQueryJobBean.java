package com.cndym.quartz.autotest;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.PostBonus;
import com.cndym.constant.Constants;
import com.cndym.sendClient.ISendClient;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IPostBonusService;
import com.cndym.service.ITicketService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;

/**
 * 作者：邓玉明 时间：11-7-24 下午4:55 QQ：757579248 email：cndym@163.com
 */
public class AutoGpBonusQueryJobBean extends QuartzJobBean {
	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
	private IPostBonusService postBonusService = (IPostBonusService) SpringUtils.getBean("postBonusServiceImpl");

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long startTime = new Date().getTime();
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_AUTO_GP_BONUS_QUERY));
		if (!lock)
			return;
		logger.info("running fcby bonusQuery JOB start");
		IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
		String postLotteryCode = "006,107";
		if (Utils.isNotEmpty(postLotteryCode)) {
			String[] lotteryCodes = postLotteryCode.split(",");
			for (String lotteryCode : lotteryCodes) {
				MainIssue para = new MainIssue();
				para.setStatus(Constants.ISSUE_STATUS_END);
				para.setBonusStatus(Constants.ISSUE_BONUS_STATUS_NO);
				para.setOperatorsAward(Constants.OPERATORS_AWARD_WAIT);
				para.setLotteryCode(lotteryCode);
				List<MainIssue> issueList = mainIssueService.getMainIssuesByStatusForGpBonusQuery(para);
				for (MainIssue mainIssue : issueList) {
					String issue = mainIssue.getName();
					if (Utils.isEmpty(mainIssue.getBonusNumber()) || Utils.isEmpty(mainIssue.getBonusClass()) || "-".equals(mainIssue.getBonusNumber()) || "-".equals(mainIssue.getBonusClass())) {
						logger.error("(" + lotteryCode + ")第" + issue + "期失败,期次开奖号码或奖级设置不对");
					} else {
						StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
						interfaceUrl.append("?lotteryCode=").append(lotteryCode);
						interfaceUrl.append("&issue=").append(issue);

						logger.info(lotteryCode + "." + issue + "开始算奖");
						HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
						logger.info("发送到URL:" + interfaceUrl);
						String reStr = "";
						try {
							reStr = httpClientUtils.httpClientGet();
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
						logger.error("返回信息:" + reStr);
					}
				}
			}
		}

		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_AUTO_GP_BONUS_QUERY));
		logger.info("running fcby bonusQuery JOB end (" + (new Date().getTime() - startTime) + "ms)");

	}

	public static void main(String[] args) {

	}
}
