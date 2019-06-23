/**
 * 
 */
package com.cndym.job.impl.ltkj;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.job.ITaskSchedule;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-9 上午10:40:30      
 */
@Service
public class LtkjGpIssueQueryJob implements ITaskSchedule {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private IDistributionLockService distributionLockService;
	
	@Override
	public void execute() {
		long startTime = new Date().getTime();
		logger.info("running LtkjGpIssueQueryJob start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_ISSUE_QUERY));
		if (!lock){
			logger.info("LtkjGpIssueQuery Job 已锁定");
			return;
		}
		
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
