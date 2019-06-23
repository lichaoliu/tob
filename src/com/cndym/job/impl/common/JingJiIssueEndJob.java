package com.cndym.job.impl.common;

import com.cndym.bean.sys.DistributionLock;
import com.cndym.job.ITaskSchedule;
import com.cndym.service.*;
import com.cndym.service.subIssue.ISubIssueOperator;
import com.cndym.utils.SpringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("jingJiIssueEndQuartzJob")
public class JingJiIssueEndJob implements ITaskSchedule{

    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private IDistributionLockService distributionLockService;
    @Autowired
    private ITicketService ticketService;


    @Override
    public void execute(){
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