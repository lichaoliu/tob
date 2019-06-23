package com.cndym.service.issueOperator;

import com.cndym.bean.tms.MainIssue;
import com.cndym.constant.Constants;
import com.cndym.jms.producer.SendToClientIssueMessageProducer;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IMainIssueService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 作者：邓玉明
 * 时间：11-4-30 下午5:55
 * QQ：757579248
 * email：cndym@163.com
 */
@Repository
public class StartIssueOperator implements IIssueOperator {
    private Logger logger = Logger.getLogger(getClass());

    @Resource
    private IMainIssueService mainIssueService;

    @Override
    public void operator(MainIssue mainIssue) {
        logger.info("进入彩种(" + mainIssue.getLotteryCode() + ")期次(" + mainIssue.getName() + ")开期处理");
        if (mainIssue.getStatus().intValue() != Constants.ISSUE_STATUS_START) {
            mainIssue.setStatus(Constants.ISSUE_STATUS_START);
            mainIssueService.doUpdateStatus(mainIssue.getStatus(), mainIssue.getSendStatus(), mainIssue.getBonusStatus(), mainIssue.getId());
        }
        //判断是否可以发单
        if (mainIssue.getSendStatus().intValue() != Constants.ISSUE_SALE_SEND) {
            long startTime = mainIssue.getStartTime().getTime();
            LotteryClass lotteryClass = LotteryList.getLotteryClass(mainIssue.getLotteryCode());
            if ((startTime + lotteryClass.getPutOff()) < new Date().getTime()) {
                mainIssue.setSendStatus(Constants.ISSUE_SALE_SEND);
                mainIssueService.doUpdateStatus(mainIssue.getStatus(), mainIssue.getSendStatus(), mainIssue.getBonusStatus(), mainIssue.getId());
            }
        }

        logger.info("彩种(" + mainIssue.getLotteryCode() + ")期次(" + mainIssue.getName() + ")开期处理结束");
    }

    public IMainIssueService getMainIssueService() {
        return mainIssueService;
    }

    public void setMainIssueService(IMainIssueService mainIssueService) {
        this.mainIssueService = mainIssueService;
    }
}
