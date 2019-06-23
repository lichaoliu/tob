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
import com.cndym.utils.xml.parse.ByteCodeUtil;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-9 上午10:40:30      
 */
@Service
public class LtkjGpBonusQueryJob implements ITaskSchedule {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private IDistributionLockService distributionLockService;
	@Autowired
	private IPostBonusService postBonusService;
	
	@Override
	public void execute() {
		long startTime = new Date().getTime();
		logger.info("running LtkjGpBonusQueryJob start");
		boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_BONUS_QUERY));
		if (!lock){
			logger.info("LtkjGpBonusQueryJob Job 已锁定");
			return;
		}
		
		try {
			IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
			if (Utils.isNotEmpty(postLotteryCode)) {
				String[] lotteryCodes = postLotteryCode.split(",");
				String postCode = LtkjSendClientConfig.getValue("POST_CODE");
				for (String lotteryCode : lotteryCodes) {
					MainIssue para = new MainIssue();
					para.setStatus(Constants.ISSUE_STATUS_END);
					para.setCenterBonusStatus(Constants.ISSUE_CENTER_BONUS_STATUS_NO);
					para.setLotteryCode(lotteryCode);
					List<MainIssue> issueList = mainIssueService.getMainIssuesByStatusForBonusQuery(para);
					for (MainIssue mainIssue : issueList) {
						PostBonus postBonus = postBonusService.getPostBonus(mainIssue.getLotteryCode(), mainIssue.getName(), postCode);
						if (Utils.isNotEmpty(postBonus) && postBonus.getStatus().intValue() == 1) {
							continue;
						}
						if (!Utils.isNotEmpty(postBonus)) {
							postBonus = new PostBonus();
							postBonus.setLotteryCode(mainIssue.getLotteryCode());
							postBonus.setIssue(mainIssue.getName());
							postBonus.setPostCode(postCode);
							postBonus.setStatus(0);
							postBonus.setCreateTime(new Date());
							postBonusService.save(postBonus);
						}
						StringBuilder xml = new StringBuilder("<body lotteryCode=\"");
						xml.append(mainIssue.getLotteryCode());
						xml.append("\" issue=\"").append(mainIssue.getName());
						xml.append("\" bonusTime=\"");
						xml.append(Utils.formatDate2Str(mainIssue.getBonusTime(), "yyyy-MM-dd HH:mm:ss"));
						xml.append("\"/>");
	
						logger.info("running LtkjGpBonusQueryJob xml:" + xml.toString());
						sendClient.bonusQuery(ByteCodeUtil.xmlToObject(xml.toString()));
					}
				}
			}
		} catch (Exception e) {
			logger.error("LtkjGpBonusQueryJob error:", e);
		}
		distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_LTKJ_GP_BONUS_QUERY));
		logger.info("running LtkjGpBonusQueryJob end (" + (new Date().getTime() - startTime) + "ms)");

	}

}
