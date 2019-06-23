package com.cndym.job.impl.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.sys.DistributionLock;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.ManagesLog;
import com.cndym.constant.Constants;
import com.cndym.job.ITaskSchedule;
import com.cndym.service.IDistributionLockService;
import com.cndym.service.IManagesLogService;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.service.ITicketService;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

@Service("autoCalculateAwardQuartzJob")
public class AutoCalculateAwardJob implements ITaskSchedule{
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private IDistributionLockService distributionLockService;

    @Override
    public void execute(){
        long startTime = new Date().getTime();
        boolean lock = distributionLockService.doStartLock(new DistributionLock(DistributionLock.LOCK_FOR_AUTO_CALCULATE_AWARD_QUARTZ));
        if (!lock) return;
        logger.info("running JOB start");
        
        try {
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = new SubIssueForJingCaiZuQiu();
            subIssueForJingCaiZuQiu.setPollCode("02");
            subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_END);
            subIssueForJingCaiZuQiu.setOperatorsAward(0);
            subIssueForJingCaiZuQiu.setInputAwardStatus(1);
        	ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuListByPara(subIssueForJingCaiZuQiu, 1, 50);
            List<SubIssueForJingCaiZuQiu> listSubIssue = pageBean.getPageContent();

            for (SubIssueForJingCaiZuQiu subIssue : listSubIssue) {
            	final String lotteryCode = subIssue.getLotteryCode();
            	final String issue = subIssue.getIssue();
            	final String sn = subIssue.getSn();
            	final String week = subIssue.getWeek();
            	logger.info(lotteryCode+issue+sn+","+subIssue.getEndOperator()+","+subIssue.getInputAwardStatus()+","+subIssue.getOperatorsAward());
            	if (!Utils.isNotEmpty(subIssue.getInputAwardStatus()) || 1 != subIssue.getInputAwardStatus()) {
            		logger.info(lotteryCode+issue+sn+"：未抓取到赛过。");
            		continue;
            	}

            	// 判断票的状态
                IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
                TicketQueryBean ticketQueryBean = new TicketQueryBean();
                Ticket ticket = new Ticket();
                ticket.setLotteryCode(lotteryCode);
                ticket.setEndGameId(issue+sn);
                ticket.setTicketStatus(Constants.TICKET_STATUS_SENDING);
                ticketQueryBean.setTicket(ticket);
                Map<String, Object> ticketMap = ticketService.getTicketCount(ticketQueryBean);
                if (Utils.isNotEmpty(ticketMap)) {
                    int ticketNum = Utils.formatInt(ticketMap.get("ticketNum") + "", 0);
                    if (ticketNum > 0) {
                    	logger.info(lotteryCode+issue+sn+",未回执:" + ticketNum);
                        continue;
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    	String reStr = null;
                        try {
                            StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
                            interfaceUrl.append("?lotteryCode=").append(lotteryCode);
                            interfaceUrl.append("&issue=").append(issue);
                            interfaceUrl.append("&sn=").append(sn);
                            
                            HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
                            logger.info(lotteryCode+issue+sn+",发送到URL:" + interfaceUrl);
                        	reStr = httpClientUtils.httpClientGet();
                            logger.info(lotteryCode+issue+sn+",返回信息:" + reStr);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                        if (Utils.isNotEmpty(reStr) && reStr.equals("success")) {
                            setManagesLog("启动竞彩足球(200)" + week + sn + "(" + issue + ")成功", Constants.MANAGER_LOG_START_CAL_AWARD);
                        } else {
                            setManagesLog("启动竞彩足球((200))" + week + sn + "(" + issue + ")失败", Constants.MANAGER_LOG_START_CAL_AWARD);
                        }
                    }
                }).start();
            }
        } catch(Exception e) {
        	logger.error("", e);
        }
        distributionLockService.doEndLock(new DistributionLock(DistributionLock.LOCK_FOR_AUTO_CALCULATE_AWARD_QUARTZ));
        logger.info("running JOB end (" + (new Date().getTime() - startTime) + "ms)");
    }
    
    private void setManagesLog (String description, Integer operatingType) {
	    IManagesLogService managesLogService = (IManagesLogService) SpringUtils.getBean("managesLogServiceImpl");
	    ManagesLog managesLog = new ManagesLog();
	    managesLog.setCreateTime(new Date());
	    managesLog.setIp("127.0.0.1");
	    managesLog.setType("footballCalculateAward");
	    managesLog.setAdminName("System");
	    managesLog.setDescription(description);
	    managesLog.setOperatingType(operatingType);
	    managesLogService.save(managesLog);
    }
}
