package com.cndym.job.impl.common;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.job.ITaskSchedule;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.ITicketService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

@Service("cancelOrderQuartzJob")
public class CancelOrderOperatorJob implements ITaskSchedule{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private IDistributionLockService distributionLockService;
	@Autowired
	private ITicketService ticketService;
	private final String TIME_KEY = "RESENDDENDLINE";
	@Override
	public void execute(){
		long startTime = new Date().getTime();
		logger.info("running cancelOrder JOB start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_CANCEL_ORDER_QUARTZ));
		if (!lock)
			return;
		try {
			XMemcachedClientWrapper memCache = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
			String issueJson = (String) memCache.getMemcachedClient().get(Constants.MEMCACHED_CURRENT_ISSUE_LIST);

			if (null != issueJson) {
				List<MainIssue> mainIssueList = JsonBinder.buildNormalBinder().getMapper().readValue(issueJson, new TypeReference<List<MainIssue>>() {});
				if (null != mainIssueList) {
					Long presetTime = Utils.formatLong(ConfigUtils.getValue("ALLOW_RESEND_PRESET_TIME"), 1800000);
					String lotteryCodes = ConfigUtils.getValue("ALLOW_CANCEL_RESEND_ORDER_LOTTERY");
					for (MainIssue mainIssue : mainIssueList) {
						long dendline = 0L;
						String lotteryCode = mainIssue.getLotteryCode();
						String issue = mainIssue.getName();
						if (Utils.isNotEmpty(lotteryCodes) && lotteryCodes.contains(lotteryCode)) {
							try {
								dendline = mainIssue.getDuplexTime().getTime() - presetTime;
								memCache.getMemcachedClient().set(TIME_KEY + lotteryCode + issue, 0, dendline);
							} catch (Exception e) {
								logger.error("Memcached", e);
							}

							if (dendline > 0 && new Date().getTime() > dendline) {
								// 手工重发的票结期前自动重发
								try {
									List<Ticket> ticketList = ticketService.getTicketForHandSend(lotteryCode, issue, Constants.TICKET_STATUS_RESEND);
									if (Utils.isNotEmpty(ticketList)) {
										logger.info(lotteryCode + "," + issue + "=" + ticketList.size());
										for (Ticket ticket : ticketList) {
											//ticketService.doHhandTicketFailed(ticket.getTicketId());
											ticketService.updateTicketForWait(ticket.getTicketId());
										}
									}
								} catch (Exception e) {
									logger.error("cancelOrder", e);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("cancelOrderOperatorJob", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_CANCEL_ORDER_QUARTZ));
		logger.info("running cancelOrder JOB end (" + (new Date().getTime() - startTime) + "ms)");
	}
}
