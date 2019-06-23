/**
 * 
 */
package com.cndym.job.app;

import com.cndym.utils.SpringUtils;
import com.zch.scheduler.core.Scheduler;
import com.zch.scheduler.core.impl.DefaultScheduler;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-5-20 上午11:12:43      
 */
public class SchedulerApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Scheduler scheduler = new DefaultScheduler();
		Scheduler scheduler = (Scheduler)SpringUtils.getBean("scheduler");
		scheduler.schedule();
	}

}
