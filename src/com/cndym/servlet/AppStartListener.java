package com.cndym.servlet;

import com.cndym.control.ThreadFactory;
import com.cndym.control.WeightMap;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: mcs Date: 12-11-5 Time: 上午9:42
 * <p/>
 * 系统启动监听
 */
public class AppStartListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ThreadFactory.forInstance();
		WeightMap.forInstance();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		//
	}
}
