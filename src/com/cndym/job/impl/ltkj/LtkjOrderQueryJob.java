/**
 * 
 */
package com.cndym.job.impl.ltkj;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.Ticket;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.ITicketService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.job.ITaskSchedule;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-3 下午02:36:43      
 */
@Service
public class LtkjOrderQueryJob implements ITaskSchedule {
	
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private IDistributionLockService distributionLockService;
	
	private final static int PAGE_SIZE = Integer.parseInt(LtkjSendClientConfig.getValue("PAGE_SIZE"));;
	
	@Override
	public void execute() {
		long startTime = new Date().getTime();
		logger.info("running LtkjOrderQueryJob start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_ORDER_QUERY));
		if (!lock){
			logger.info("LtkjOrderQuery Job 已锁定");
			return;
		}
		
		try {
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
			ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");

			String postCode = LtkjSendClientConfig.getValue("POST_CODE");

			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_OEDER_LOTTERY");
			if (Utils.isNotEmpty(postLotteryCode)) {
				String[] lotteryCodeArr = postLotteryCode.split(",");
				for (String lotteryCode : lotteryCodeArr) {
					Calendar sendTimeCalendar = Calendar.getInstance();
					int secondBefore = Utils.formatInt(LtkjSendClientConfig.getValue("POST_ORDER_SEND_TIME"), 600) * -1;
					sendTimeCalendar.add(Calendar.SECOND, secondBefore);//发送时间向前10分钟
					PageBean pageBean = ticketService.getSendedTicketEx(lotteryCode, postCode, sendTimeCalendar.getTime(), 1, PAGE_SIZE);
					List<Ticket> ticketList = pageBean.getPageContent();
					if (Utils.isNotEmpty(ticketList)) {
						sendClient.orderQuery(ticketList);
					}
				}
			}
		} catch (Exception e) {
			logger.error("LtkjOrderQueryJob Exception", e);
		}
		
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_ORDER_QUERY));
		logger.info("running LtkjOrderQueryJob end (" + (new Date().getTime() - startTime) + "ms)");
	}
	
	

}
