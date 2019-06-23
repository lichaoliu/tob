package com.cndym.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.cndym.service.IMainIssueService;
import com.cndym.utils.SpringUtils;

/**
 * User: mcs Date: 12-11-5 Time: 上午9:42
 * <p/>
 * 系统启动监听
 */
public class InitSpringListener implements ServletContextListener {
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
}
