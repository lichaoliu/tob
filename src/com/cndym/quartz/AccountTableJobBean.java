package com.cndym.quartz;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.user.ManagesLog;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.dao.IDaySaleTableDao;
import com.cndym.service.IAccountTableService;
import com.cndym.service.IDaySaleTableService;
import com.cndym.service.IDayTicketTableService;
import com.cndym.service.IDistributionLockService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Calendar;
import java.util.Date;

/**
 * 作者：邓玉明
 * 时间：11-6-19 下午6:43
 * QQ：757579248
 * email：cndym@163.com
 */
public class AccountTableJobBean extends QuartzJobBean {
    private Logger logger = Logger.getLogger(getClass());
    private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
    IAccountTableService accountTableService = (IAccountTableService) SpringUtils.getBean("accountTableServiceImpl");
    IDaySaleTableService daySaleTableService = (IDaySaleTableService) SpringUtils.getBean("daySaleTableServiceImpl");
    IDayTicketTableService dayTicketTableService = (IDayTicketTableService) SpringUtils.getBean("dayTicketTableServiceImpl");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startTime = new Date().getTime();
        boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_COUNT_TABLE_QUARTZ));
        if (!lock) return;
        logger.info("running count account table JOB start");
        logger.info("开始生成日帐户报表...");
        accountTableService.countAccountTable();
        logger.info("生成日帐户报表结束");

        logger.info("开始生成日销售报表...");
        daySaleTableService.countSaleTable();
        logger.info("生成日销售报表结束");

        logger.info("开始生成日出票报表...");
        dayTicketTableService.countTicketTable();
        logger.info("生成日出票结束");

        distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_COUNT_TABLE_QUARTZ));
        logger.info("running count table JOB end (" + (new Date().getTime() - startTime) + "ms)");
    }

}
