package com.cndym.service.impl;

import com.cndym.bean.count.TicketTable;
import com.cndym.bean.count.TicketTable;
import com.cndym.bean.tms.MainIssue;
import com.cndym.constant.Constants;
import com.cndym.dao.IMainIssueDao;
import com.cndym.dao.ITicketTableDao;
import com.cndym.service.IManagesLogService;
import com.cndym.service.ITicketTableService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午7:27
 */

@Service
public class TicketTableServiceImpl extends GenericServiceImpl<TicketTable, ITicketTableDao> implements ITicketTableService {

    @Autowired
    private ITicketTableDao ticketTableDao;
    @Autowired
    private IManagesLogService managesLogService;

    @Autowired
    private IMainIssueDao mainIssueDao;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(ticketTableDao);
    }


    @Override
    public void countTicketTable(String lotteryCode, String issue) {
        MainIssue mainIssue = mainIssueDao.getMainIssueByLotteryIssue(lotteryCode, issue);

        String postSql = "select t.post_code as postCode from tms_ticket t where t.lottery_code = ? and t.issue = ? group by t.post_code";
        List<Map<String, Object>> postMapList = ticketTableDao.customListSql(postSql, new Object[]{lotteryCode, issue});

        String successSql = "select t.post_code as postCode,count(t.id) as successTicket,sum(t.amount) as successAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? group by t.post_code";
        String waitSql = "select t.post_code as postCode,count(t.id) as waitTicket,sum(t.amount) as waitAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? group by  t.post_code";
        String failureSql = "select t.post_code as postCode,count(t.id) as failureTicket,sum(t.amount) as failureAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? group by  t.post_code";
        String bonusSql = "select t.post_code as postCode,count(t.id) as bonusTicket,sum(t.bonus_amount) as bonusAmount ,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? and bonus_status = ? group by t.post_code";

        List<Map<String, Object>> successMapList = ticketTableDao.customListSql(successSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS});
        List<Map<String, Object>> waitMapList = ticketTableDao.customListSql(waitSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SENDING});
        List<Map<String, Object>> failureMapList = ticketTableDao.customListSql(failureSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_FAILURE});
        List<Map<String, Object>> bonusMapList = ticketTableDao.customListSql(bonusSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_YES});

        Map<String, TicketTable> ticketTableMap = new HashMap<String, TicketTable>();
        for (Map<String, Object> postMap : postMapList) {
            String postCode = postMap.get("postCode") + "";
            TicketTable ticketTable = null;
            if (ticketTableMap.containsKey(postCode)) {
                ticketTable = ticketTableMap.get(postCode);
            } else {
                ticketTable = new TicketTable();
            }
            ticketTable.setLotteryCode(lotteryCode);
            ticketTable.setIssue(issue);
            ticketTable.setStartTime(mainIssue.getStartTime());
            ticketTable.setEndTime(mainIssue.getEndTime());
            ticketTable.setPostCode(postCode);
            ticketTable.setSuccessAmount(0d);
            ticketTable.setSuccessTicket(0L);
            ticketTable.setWaitAmount(0d);
            ticketTable.setWaitTicket(0L);
            ticketTable.setFailureAmount(0d);
            ticketTable.setFailureTicket(0l);
            ticketTable.setBonusAmount(0d);
            ticketTable.setFixBonusAmount(0d);
            ticketTable.setBonusTicket(0l);

            ticketTableMap.put(postCode, ticketTable);
        }

        for (Map<String, Object> successMap : successMapList) {
            long successTicket = format(successMap.get("successTicket")).longValue();
            double successAmount = format(successMap.get("successAmount")).doubleValue();
            String postCode = successMap.get("postCode") + "";
            if (ticketTableMap.containsKey(postCode)) {
                TicketTable ticketTable = ticketTableMap.get(postCode);
                ticketTable.setSuccessTicket(successTicket);
                ticketTable.setSuccessAmount(successAmount);
                ticketTableMap.put(postCode, ticketTable);
            }
        }

        for (Map<String, Object> waitMap : waitMapList) {
            long waitTicket = format(waitMap.get("waitTicket")).longValue();
            double waitAmount = format(waitMap.get("waitAmount")).doubleValue();
            String postCode = waitMap.get("postCode") + "";
            if (ticketTableMap.containsKey(postCode)) {
                TicketTable ticketTable = ticketTableMap.get(postCode);
                ticketTable.setWaitTicket(waitTicket);
                ticketTable.setWaitAmount(waitAmount);
                ticketTableMap.put(postCode, ticketTable);
            }
        }

        for (Map<String, Object> failureMap : failureMapList) {
            long failureTicket = format(failureMap.get("failureTicket")).longValue();
            double failureAmount = format(failureMap.get("failureAmount")).doubleValue();
            String postCode = failureMap.get("postCode") + "";
            if (ticketTableMap.containsKey(postCode)) {
                TicketTable ticketTable = ticketTableMap.get(postCode);
                ticketTable.setFailureTicket(failureTicket);
                ticketTable.setFailureAmount(failureAmount);
                ticketTableMap.put(postCode, ticketTable);
            }
        }

        for (Map<String, Object> bonusMap : bonusMapList) {
            long bonusTicket = format(bonusMap.get("bonusTicket")).longValue();
            double bonusAmount = format(bonusMap.get("bonusAmount")).doubleValue();
            double fixBonusAmount = format(bonusMap.get("fixBonusAmount")).doubleValue();
            String postCode = bonusMap.get("postCode") + "";
            if (ticketTableMap.containsKey(postCode)) {
                TicketTable ticketTable = ticketTableMap.get(postCode);
                ticketTable.setBonusTicket(bonusTicket);
                ticketTable.setBonusAmount(bonusAmount);
                ticketTable.setFixBonusAmount(fixBonusAmount);
                ticketTableMap.put(postCode, ticketTable);
            }
        }

        List<TicketTable> ticketTableList = new ArrayList<TicketTable>();
        for (Map.Entry<String, TicketTable> entry : ticketTableMap.entrySet()) {
            TicketTable ticketTable = entry.getValue();
            ticketTable.setCreateTime(new Date());
            ticketTableList.add(ticketTable);
        }

        TicketTable allTicketTable = new TicketTable();
        allTicketTable.setLotteryCode(lotteryCode);
        allTicketTable.setIssue(issue);
        allTicketTable.setStartTime(mainIssue.getStartTime());
        allTicketTable.setEndTime(mainIssue.getEndTime());
        allTicketTable.setSuccessTicket(0L);
        allTicketTable.setSuccessAmount(0d);
        allTicketTable.setWaitAmount(0d);
        allTicketTable.setWaitTicket(0L);
        allTicketTable.setFailureTicket(0L);
        allTicketTable.setFailureAmount(0d);
        allTicketTable.setBonusTicket(0L);
        allTicketTable.setBonusAmount(0d);
        allTicketTable.setFixBonusAmount(0d);
        for (TicketTable ticketTable : ticketTableList) {
            allTicketTable.setSuccessAmount(allTicketTable.getSuccessAmount() + ticketTable.getSuccessAmount());
            allTicketTable.setSuccessTicket(allTicketTable.getSuccessTicket() + ticketTable.getSuccessTicket());

            allTicketTable.setWaitAmount(allTicketTable.getWaitAmount() + ticketTable.getWaitAmount());
            allTicketTable.setWaitTicket(allTicketTable.getWaitTicket() + ticketTable.getWaitTicket());

            allTicketTable.setFailureAmount(allTicketTable.getFailureAmount() + ticketTable.getFailureAmount());
            allTicketTable.setFailureTicket(allTicketTable.getFailureTicket() + ticketTable.getFailureTicket());

            allTicketTable.setBonusAmount(allTicketTable.getBonusAmount() + ticketTable.getBonusAmount());
            allTicketTable.setFixBonusAmount(allTicketTable.getFixBonusAmount() + ticketTable.getFixBonusAmount());
            allTicketTable.setBonusTicket(allTicketTable.getBonusTicket() + ticketTable.getBonusTicket());

        }
        allTicketTable.setCreateTime(new Date());
        ticketTableList.add(allTicketTable);
        ticketTableDao.saveAllObject(ticketTableList);
        String des = "生成" + lotteryCode + "【" + issue + "】期出票报表";
        managesLogService.addManagesLog(des, Constants.MANAGER_LOG_DATA);
    }

    @Override
    public PageBean getPageBeanByPara(TicketTable ticketTable, Integer page, Integer pageSize) {
        return ticketTableDao.getPageBeanByPara(ticketTable, page, pageSize);
    }

    private static BigDecimal format(Object object) {
        if (null == object) {
            return new BigDecimal(0);
        }
        return (BigDecimal) object;
    }


    public static void main(String[] args) {
        ITicketTableService ticketTableService = (ITicketTableService) SpringUtils.getBean("ticketTableServiceImpl");
        ticketTableService.countTicketTable("002", "2012209");
    }
}
