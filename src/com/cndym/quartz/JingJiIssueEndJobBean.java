package com.cndym.quartz;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.service.*;
import com.cndym.service.subIssue.ISubIssueOperator;
import com.cndym.utils.SpringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;


public class JingJiIssueEndJobBean extends QuartzJobBean {

    private Logger logger = Logger.getLogger(getClass());
    private IDistributionLockService distributionLockService = (IDistributionLockService) SpringUtils.getBean("distributionLockServiceImpl");
    private ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long startTime = new Date().getTime();
        logger.info("竞技彩场次结束JOB开始:" + startTime);
        boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_JING_JI_ISSUE_END));
        if (!lock) return;
        String[] arr = {"200", "201", "400"};
        try {
            for (String lotteryCode : arr) {
                ISubIssueOperator subIssueOperator = (ISubIssueOperator) SpringUtils.getBean("job" + lotteryCode + "SubIssueOperator");
                subIssueOperator.operator();
            }
            ticketService.doEndIssueOperator();
        } catch (Exception e) {
            e.printStackTrace();
        }
        distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_JING_JI_ISSUE_END));
        logger.info("竞技彩场次结束: (" + (new Date().getTime() - startTime) + "ms)");
    }
}