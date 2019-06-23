package com.cndym.job.impl.common;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.control.ControlCenter;
import com.cndym.job.ITaskSchedule;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.ITicketService;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明 时间：11-5-26 下午4:20 QQ：757579248 email：cndym@163.com
 */
@Service("orderQuartzJob")
public class OrderClusterableJob implements ITaskSchedule{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private IDistributionLockService distributionLockService;
	@Autowired
	private ITicketService ticketService;

	@Override
	public void execute() {
		long startTime = new Date().getTime();
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_ORDER_QUARTZ));
		if (!lock)
			return;
		logger.info("running order send JOB start");
		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
		try {
			String issueJson = (String) memcachedClientWrapper.getMemcachedClient().get(Constants.MEMCACHED_CURRENT_ISSUE_LIST);
			if (null != issueJson) {
				List<MainIssue> mainIssueList = null;
				mainIssueList = JsonBinder.buildNormalBinder().getMapper().readValue(issueJson, new TypeReference<List<MainIssue>>() {
				});
				if (null != mainIssueList) {
					for (MainIssue mainIssue : mainIssueList) {
						if (Constants.ISSUE_SALE_SEND != mainIssue.getSendStatus()) {
							continue;
						}
						if (!Utils.isSendTicketByDiy(mainIssue.getLotteryCode())) {
							continue;
						}
						if (!Utils.isSendTicket(mainIssue.getLotteryCode())) {
							logger.info("彩种(" + mainIssue.getLotteryCode() + ")期次(" + mainIssue.getName() + ")在当前时间下暂时不发送");
							continue;
						}

						List<Map<String, Object>> mapList = ticketService.getSendTicket(mainIssue.getLotteryCode(), mainIssue.getName());
						for (Map<String, Object> map : mapList) {
							try {
								Map<String, String> stringMap = new HashMap<String, String>();
								stringMap.put("lotteryCode", (String) map.get("lotteryCode"));
								stringMap.put("orderId", (String) map.get("orderId"));
								stringMap.put("sid", (String) map.get("sid"));
								ControlCenter.executeFormDateBase(stringMap);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			// 北单、竞彩订单发送
			List<LotteryClass> list = LotteryList.getLotteryClassList();
			for (LotteryClass lotteryClass : list) {
				if (Constants.NOT_ALLOW_RESERVE_ISSUE_TYPE == lotteryClass.getAuto().intValue()) {
					if (!Utils.isSendTicketByDiy(lotteryClass.getCode())) {
						continue;
					}
					if (!Utils.isSendTicket(lotteryClass.getCode())) {
						logger.info("彩种(" + lotteryClass.getCode() + ")期次(" + lotteryClass.getName() + ")在当前时间下暂时不发送");
						continue;
					}
					// 一定要提前结期
					List<Map<String, Object>> mapList = ticketService.getSendTicket(lotteryClass.getCode());
					logger.info("彩种(" + lotteryClass.getCode() + ")有(" + mapList.size() + ")条批次需要发送");
					for (Map<String, Object> map : mapList) {
						try {
							Map<String, String> stringMap = new HashMap<String, String>();
							stringMap.put("lotteryCode", (String) map.get("lotteryCode"));
							stringMap.put("orderId", (String) map.get("orderId"));
							stringMap.put("sid", (String) map.get("sid"));
							ControlCenter.executeFormDateBase(stringMap);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_ORDER_QUARTZ));
		logger.info("running order send JOB end (" + (new Date().getTime() - startTime) + "ms)");

	}

}
