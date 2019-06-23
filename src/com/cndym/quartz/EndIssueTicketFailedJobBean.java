package com.cndym.quartz;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.ITicketService;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

public class EndIssueTicketFailedJobBean extends QuartzJobBean {
	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
	private ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long startTime = new Date().getTime();
		logger.info("running ticketFailed JOB start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_TICKET_FAILED_QUARTZ));
		if (!lock)
			return;
		try {
			XMemcachedClientWrapper memCache = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
			String issueJson = (String) memCache.getMemcachedClient().get(Constants.MEMCACHED_CURRENT_ISSUE_LIST);

			if (null != issueJson) {
				List<MainIssue> mainIssueList = JsonBinder.buildNormalBinder().getMapper().readValue(issueJson, new TypeReference<List<MainIssue>>() {});
				String lotteryCodes = "009,010,011";
				if (null != mainIssueList) {
					for (MainIssue mainIssue : mainIssueList) {
						String lotteryCode = mainIssue.getLotteryCode();
						String issue = mainIssue.getName();
						if (new Date().getTime() > mainIssue.getDuplexTime().getTime() && lotteryCodes.contains(lotteryCode)) {
							try {
								List<Ticket> ticketList = ticketService.getTicketForHandSend(lotteryCode, issue, Constants.TICKET_STATUS_WAIT);
								if (Utils.isNotEmpty(ticketList)) {
									logger.info(lotteryCode + "," + issue + "=" + ticketList.size());
									for (Ticket ticket : ticketList) {
										ticketService.doHhandTicketFailed(ticket.getTicketId());
									}
								}
							} catch (Exception e) {
								logger.error("ticketFailed", e);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("ticketFailed", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_TICKET_FAILED_QUARTZ));
		logger.info("running ticketFailed JOB end (" + (new Date().getTime() - startTime) + "ms)");
	}
}
