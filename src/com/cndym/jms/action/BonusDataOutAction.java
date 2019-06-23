package com.cndym.jms.action;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ISubIssueForBeiDanService;
import com.cndym.service.ITicketService;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;

/**
 * 作者：邓玉明
 * 时间：11-6-26 上午11:21
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class BonusDataOutAction implements IDataOutAction {
    private Logger logger = Logger.getLogger(getClass());
    @Resource
    private IMainIssueService mainIssueService;
    @Resource
    private ITicketService ticketService;

    @Override
    public void execute(ActiveMQMapMessage mapMessage) {
        logger.info("收到中奖数据");
        try {
            final String lotteryCode = (String) mapMessage.getObject("lotteryCode");
            final String issue = (String) mapMessage.getObject("issue");
            String sn = (String) mapMessage.getObject("sn");
            logger.info("中奖数据:彩种（" + lotteryCode + "),期次(" + issue + ")");
            new Thread(new Runnable() {
                @Override
                public void run() {
                	bonus(lotteryCode, issue);
                }
            }).start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void bonus(String lotteryCode, String issue) {
        logger.info("自动派奖:彩种（" + lotteryCode + "),期次(" + issue + ")");
        MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
        if (null == mainIssue) {
            return;
        }
        if (Constants.OPERATORS_AWARD_COMPLETE != mainIssue.getOperatorsAward()) {
            return;
        }
        ticketService.doBonusAmountToAccount(lotteryCode, issue);
    }
}
