package com.cndym.job.impl.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.sendClient.ISendClient;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.ITicketService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.Ticket;
import com.cndym.control.PostMap;
import com.cndym.control.bean.Weight;
import com.cndym.job.ITaskSchedule;

@Service("commonOrderQueryJob")
public class CommonOrderQueryJob implements ITaskSchedule{
	private Logger logger = Logger.getLogger(getClass());
	private final static int pageSize = 50;
	@Autowired
	private IDistributionLockService distributionLockService;
	
	@Override
	public void execute(){
	    long startTime = new Date().getTime();
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_COMMON_ORDER_QUERY));
		if (!lock)
			return;
	    logger.info("running CommonOrderQueryJob start");
		try {
			String lotteryCodeStr = ConfigUtils.getValue("COMMON_ORDER_QUERY_LOTTERY");
			if (Utils.isNotEmpty(lotteryCodeStr)) {
				ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
				Map<String, Weight> weightMap = ElTagUtils.getWeightMap();
				String postCode = null;
				String postClass = null;
				PageBean pageBean = null;
				ISendClient sendClient = null;
				List<Ticket> ticketList = null;
				String[] lotteryCodeArr = lotteryCodeStr.split(",");

				for (String lotteryCode : lotteryCodeArr) {
					postCode = weightMap.get(lotteryCode).getDefaultPostCode();
					postClass = PostMap.getPost(postCode).getPostClass();
					sendClient = (ISendClient) SpringUtils.getBean(postClass);
					pageBean = ticketService.getSendedTicket(lotteryCode, postCode, 1, pageSize, "desc");
					Long itemTotal = pageBean.getItemTotal();
					ticketList = pageBean.getPageContent();
					if (Utils.isNotEmpty(ticketList) && itemTotal > 100) {
						logger.info("CommonOrderQueryJob total=" + itemTotal);
						if (postCode.equals("20") && lotteryCode.startsWith("20")) {
							sendClient.orderQuery(ticketList, lotteryCode);
						} else {
							sendClient.orderQuery(ticketList);
						}
					}
				}
				weightMap = null;
			}
		} catch (Exception e) {
			logger.error("CommonOrderQueryJob", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_COMMON_ORDER_QUERY));
        logger.info("running CommonOrderQueryJob end (" + (new Date().getTime() - startTime) + "ms)");
	}
}
