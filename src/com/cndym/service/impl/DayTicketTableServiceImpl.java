package com.cndym.service.impl;

import com.cndym.bean.count.DayTicketTable;
import com.cndym.bean.count.TicketTable;
import com.cndym.bean.tms.MainIssue;
import com.cndym.constant.Constants;
import com.cndym.dao.IDayTicketTableDao;
import com.cndym.dao.IMainIssueDao;
import com.cndym.dao.ITicketTableDao;
import com.cndym.service.IDayTicketTableService;
import com.cndym.service.IManagesLogService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: mcs
 * Date: 12-11-26
 * Time: 下午4:31
 */
@Service
public class DayTicketTableServiceImpl extends GenericServiceImpl<DayTicketTable, IDayTicketTableDao> implements IDayTicketTableService {


    @Autowired
    private IDayTicketTableDao dayTicketTableDao;
    @Autowired
    private IManagesLogService managesLogService;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(dayTicketTableDao);
    }

    @Override
    public void countTicketTable() {

        Date[] objectArray = getDateArray();
        Date date1 = objectArray[1];
        Date date = objectArray[0];

        String postSql = "select t.post_code as postCode from tms_ticket t where t.send_time >= ? and t.send_time < ? group by t.post_code ";
        List<Map<String, Object>> postMapList = dayTicketTableDao.customSql(postSql, new Object[]{date, date1});

        String successSql = "select t.post_code as postCode,count(t.id) as successTicket,sum(t.amount) as successAmount from tms_ticket t where t.send_time >= ? and t.send_time < ? and ticket_status = ? group by t.post_code";
        String waitSql = "select t.post_code as postCode,count(t.id) as waitTicket,sum(t.amount) as waitAmount from tms_ticket t where t.send_time >= ? and t.send_time < ? and ticket_status = ? group by  t.post_code";
        String failureSql = "select t.post_code as postCode,count(t.id) as failureTicket,sum(t.amount) as failureAmount from tms_ticket t where t.send_time >= ? and t.send_time < ? and ticket_status = ? group by  t.post_code";
        String bonusSql = "select t.post_code as postCode,count(t.id) as bonusTicket,sum(t.bonus_amount) as bonusAmount ,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t where t.send_time >= ? and t.send_time < ? and ticket_status = ? and bonus_status = ? group by t.post_code";

        List<Map<String, Object>> successMapList = dayTicketTableDao.customSql(successSql, new Object[]{date, date1, Constants.TICKET_STATUS_SUCCESS});
        List<Map<String, Object>> waitMapList = dayTicketTableDao.customSql(waitSql, new Object[]{date, date1, Constants.TICKET_STATUS_SENDING});
        List<Map<String, Object>> failureMapList = dayTicketTableDao.customSql(failureSql, new Object[]{date, date1, Constants.TICKET_STATUS_FAILURE});
        List<Map<String, Object>> bonusMapList = dayTicketTableDao.customSql(bonusSql, new Object[]{date, date1, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_YES});

        Map<String, DayTicketTable> ticketTableMap = new HashMap<String, DayTicketTable>();
        for (Map<String, Object> postMap : postMapList) {
            String postCode = postMap.get("postCode") + "";
            DayTicketTable ticketTable = null;
            if (ticketTableMap.containsKey(postCode)) {
                ticketTable = ticketTableMap.get(postCode);
            } else {
                ticketTable = new DayTicketTable();
            }
            ticketTable.setCurDate(Utils.formatDate2Str(date, "yyyy-MM-dd"));
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
                DayTicketTable ticketTable = ticketTableMap.get(postCode);
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
                DayTicketTable ticketTable = ticketTableMap.get(postCode);
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
                DayTicketTable ticketTable = ticketTableMap.get(postCode);
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
                DayTicketTable ticketTable = ticketTableMap.get(postCode);
                ticketTable.setBonusTicket(bonusTicket);
                ticketTable.setBonusAmount(bonusAmount);
                ticketTable.setFixBonusAmount(fixBonusAmount);
                ticketTableMap.put(postCode, ticketTable);
            }
        }

        List<DayTicketTable> ticketTableList = new ArrayList<DayTicketTable>();
        for (Map.Entry<String, DayTicketTable> entry : ticketTableMap.entrySet()) {
            DayTicketTable ticketTable = entry.getValue();
            ticketTable.setCreateTime(new Date());
            ticketTableList.add(ticketTable);
        }

        DayTicketTable allTicketTable = new DayTicketTable();
        allTicketTable.setCurDate(Utils.formatDate2Str(date, "yyyy-MM-dd"));
        allTicketTable.setSuccessTicket(0L);
        allTicketTable.setSuccessAmount(0d);
        allTicketTable.setWaitAmount(0d);
        allTicketTable.setWaitTicket(0L);
        allTicketTable.setFailureTicket(0L);
        allTicketTable.setFailureAmount(0d);
        allTicketTable.setBonusTicket(0L);
        allTicketTable.setBonusAmount(0d);
        allTicketTable.setFixBonusAmount(0d);
        for (DayTicketTable ticketTable : ticketTableList) {
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
        dayTicketTableDao.saveAllObject(ticketTableList);
        String des = "生成日出票报表";
        managesLogService.addManagesLog(des, Constants.MANAGER_LOG_DATA);
    }

    @Override
    public PageBean getPageBeanByPara(DayTicketTable dayTicketTable, Integer page, Integer pageSize) {
        return dayTicketTableDao.getPageBeanByPara(dayTicketTable, page, pageSize);
    }


    private static Date[] getDateArray() {
        Date[] objectArray = new Date[2];

        Calendar today = Calendar.getInstance();
        //today.add(Calendar.DATE, 1);
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        yesterday.set(Calendar.HOUR, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        String todayStr = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DAY_OF_MONTH) + " 00:00:00";
        String yesterdayStr = yesterday.get(Calendar.YEAR) + "-" + (yesterday.get(Calendar.MONTH) + 1) + "-" + yesterday.get(Calendar.DAY_OF_MONTH) + " 00:00:00";

        objectArray[0] = Utils.formatDate(yesterdayStr);
        objectArray[1] = Utils.formatDate(todayStr);
        return objectArray;
    }

    private static BigDecimal format(Object object) {
        if (null == object) {
            return new BigDecimal(0);
        }
        return (BigDecimal) object;
    }

    public static void main(String[] args) {
        IDayTicketTableService dayTicketTableService = (IDayTicketTableService) SpringUtils.getBean("dayTicketTableServiceImpl");
        dayTicketTableService.countTicketTable();
    }
}
