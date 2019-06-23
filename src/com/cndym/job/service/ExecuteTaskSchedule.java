/**
 * 
 */
package com.cndym.job.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.service.IDistributionLockService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.job.ITaskSchedule;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-2-27 上午11:17:12      
 */

public class ExecuteTaskSchedule {
	
	private static Logger logger = Logger.getLogger(ExecuteTaskSchedule.class.getName());
	private ExecutorService pool = Executors.newCachedThreadPool();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public void execute(){
		logger.info("进入调度控制中心......");
		IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
		DistributionLock distributionLockQuery = new DistributionLock();
		distributionLockQuery.setStatus(0);
		distributionLockQuery.setStop(0);
		List<DistributionLock> distributionLockList = distributionLockService.getDistributionLockList(distributionLockQuery);
		if(Utils.isNotEmpty(distributionLockList)){
			logger.info("当前可运行的job数量是：" + distributionLockList.size());
			for(DistributionLock distributionLock : distributionLockList){
				Date createTime = distributionLock.getCreateTime();
				long between = 0L;
				boolean isFirst = false;
				if(Utils.isNotEmpty(createTime)){
					Date nowTime = new Date();
					between = nowTime.getTime() - createTime.getTime();
					logger.info("jobName:"  + distributionLock.getName() + ",createTime:" + sdf.format(createTime) + ",nowTime:" + sdf.format(nowTime) + ",between:" + between);
				}else {
					isFirst = true;
					logger.info("jobName:"  + distributionLock.getName() + " is the first running...");
				}
				
				long interval= distributionLock.getInterval();
				if(between >= interval || isFirst){
					String name = distributionLock.getName();
					final ITaskSchedule taskSchedule = (ITaskSchedule) SpringUtils.getBean(name + "Job");
					pool.execute(new Runnable() {
						@Override
						public void run() {
							taskSchedule.execute();
						}
					});
				}
			}
		}else {
			logger.info("暂无job可以运行");
		}
		
	}
}
