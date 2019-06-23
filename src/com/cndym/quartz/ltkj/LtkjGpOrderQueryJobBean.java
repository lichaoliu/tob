/**
 * 
 */
package com.cndym.quartz.ltkj;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.Ticket;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.ITicketService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

/**
 * @author 朱林虎
 * 高频查询回执
 */
public class LtkjGpOrderQueryJobBean extends QuartzJobBean {

	private Logger logger = Logger.getLogger(getClass());
	private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
	private final static int PAGE_SIZE = Integer.parseInt(LtkjSendClientConfig.getValue("PAGE_SIZE"));;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		long startTime = new Date().getTime();
		logger.info("running LtkjGpOrderQueryJob start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_ORDER_QUERY));
		if (!lock)
			return;
		try {
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
			ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");

			String postCode = LtkjSendClientConfig.getValue("POST_CODE");

			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_GP_OEDER_LOTTERY");
			if (Utils.isNotEmpty(postLotteryCode)) {
				String[] lotteryCodeArr = postLotteryCode.split(",");
				for (String lotteryCode : lotteryCodeArr) {
					Calendar sendTimeCalendar = Calendar.getInstance();
					int secondBefore = Utils.formatInt(LtkjSendClientConfig.getValue("POST_ORDER_GP_SEND_TIME"), 60) * -1;
					sendTimeCalendar.add(Calendar.SECOND, secondBefore);//发送时间向前1分钟
					PageBean pageBean = ticketService.getSendedTicketEx(lotteryCode, postCode, sendTimeCalendar.getTime(), 1, PAGE_SIZE);
					List<Ticket> ticketList = pageBean.getPageContent();
					if (Utils.isNotEmpty(ticketList)) {
						sendClient.orderQuery(ticketList);
					}
				}
			}
		} catch (Exception e) {
			logger.error("LtkjGpOrderQueryJob Exception", e);
		}

		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_ORDER_QUERY));
		logger.info("running LtkjGpOrderQueryJob end (" + (new Date().getTime() - startTime) + "ms)");

	}

}
