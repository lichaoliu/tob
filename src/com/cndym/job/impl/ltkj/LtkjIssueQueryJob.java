/**
 * 
 */
package com.cndym.job.impl.ltkj;

import java.util.Date;

import org.apache.log4j.Logger;

import com.cndym.sendClient.ISendClient;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.zch.scheduler.job.DefaultAbstractJob;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-3 下午02:36:43      
 */
public class LtkjIssueQueryJob extends DefaultAbstractJob {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void execute() {
		long startTime = new Date().getTime();
		logger.info("running LtkjIssueQueryJob start");
		
		try {
			ISendClient sendClient = (ISendClient) SpringUtils.getBean("ltkjSendClientImpl");
	
			String postLotteryCode = LtkjSendClientConfig.getValue("POST_CODE_LOTTERY");
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
			logger.error("LtkjIssueQueryJob error:", e);
		}
		
		logger.info("running LtkjIssueQueryJob end (" + (new Date().getTime() - startTime) + "ms)");
	}
	
	

}
