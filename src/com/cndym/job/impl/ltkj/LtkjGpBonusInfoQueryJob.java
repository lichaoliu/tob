/**
 * 
 */
package com.cndym.job.impl.ltkj;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.PostBonus;
import com.cndym.constant.Constants;
import com.cndym.job.ITaskSchedule;
import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.IMainIssueService;
import com.cndym.service.IPostBonusService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import com.cndym.utils.xml.parse.ByteCodeUtil;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-9 上午10:40:30      
 */
@Service
public class LtkjGpBonusInfoQueryJob implements ITaskSchedule {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private IDistributionLockService distributionLockService;
	
	@Override
	public void execute() {
		long startTime = new Date().getTime();
		logger.info("running LtkjGpBonusInfoQueryJob start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_BONUS_INFO_QUERY));
		if (!lock){
			logger.info("LtkjGpBonusInfoQueryJob 已锁定");
			return;
		}
		try {
			IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");

			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
			if (Utils.isNotEmpty(postLotteryCode)) {
				String[] lotteryCodeArr = postLotteryCode.split(",");
				for (String lotteryCode : lotteryCodeArr) {
					MainIssue para = new MainIssue();
					para.setLotteryCode(lotteryCode);
					PageBean pageBean = mainIssueService.findIssueForGetBonusNumber(para, 50, 1);
					List<MainIssue> issueList = pageBean.getPageContent();
					for (MainIssue mainIssue : issueList) {
						String issue = mainIssue.getName();

						if (null == mainIssue.getBonusNumber() || "-".equals(mainIssue.getBonusNumber())) {
							StringBuilder xml = new StringBuilder("<body lotteryCode=\"");
							xml.append(mainIssue.getLotteryCode());
							xml.append("\" issue=\"");
							xml.append(issue);
							xml.append("\"/>");
							sendClient.bonusInfoQuery(ByteCodeUtil.xmlToObject(xml.toString()));
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("LtkjGpBonusInfoQueryJob error:", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_BONUS_INFO_QUERY));
		logger.info("running LtkjGpBonusInfoQueryJob end (" + (new Date().getTime() - startTime) + "ms)");


	}

}
