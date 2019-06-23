/**
 * 
 */
package com.cndym.job.app;

import org.apache.log4j.Logger;

import com.cndym.job.ScheduleConfig;
import com.cndym.job.service.ExecuteTaskSchedule;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-3-2 下午03:28:04      
 */
public class MainApp {
	
	private static Logger logger = Logger.getLogger(MainApp.class.getName());
	private long SERVICE_MAIN_INTERVAL_TIME_SECONDS = 120;
	private Thread taskScheduleThread;//任务调度线程
	
	public MainApp() {
        try {
            SERVICE_MAIN_INTERVAL_TIME_SECONDS = Long.parseLong(ScheduleConfig.getValue("SERVICE_MAIN_INTERVAL_TIME_SECONDS"));
        } catch (Exception e) {
        }
        logger.info("job system start");
        createThread();
	}
	
	public void start(){
		taskScheduleThread.start();
	}
	
	private void createThread() {
		taskScheduleThread = new Thread(new Runnable() {
	             @Override
	             public void run() {
	                 while (true) {
	                     logger.info("开始执行任务调度");
	                     new Thread(new Runnable() {
	                         @Override
	                         public void run() {
	                        	 new ExecuteTaskSchedule().execute();
	                         }
	                     }).start();
	                     try {
	                         Thread.sleep(SERVICE_MAIN_INTERVAL_TIME_SECONDS * 1000);
	                     } catch (InterruptedException e) {
	                         e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	                     }
	                 }
	             }
	         });
	         logger.info("任务调度线程初始化成功！");
	    }
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
        try {
            logger.info("5秒后开始执行任务调度");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        mainApp.start();
        logger.info("主程序正常启动,开始运行");
	}

}
