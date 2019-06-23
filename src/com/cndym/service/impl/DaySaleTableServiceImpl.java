package com.cndym.service.impl;

import com.cndym.bean.count.DaySaleTable;
import com.cndym.constant.Constants;
import com.cndym.dao.IDaySaleTableDao;
import com.cndym.service.IDaySaleTableService;
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
 * Time: 下午4:05
 */

@Service
public class DaySaleTableServiceImpl extends GenericServiceImpl<DaySaleTable, IDaySaleTableDao> implements IDaySaleTableService {

    @Autowired
    private IDaySaleTableDao daySaleTableDao;

    @Autowired
    private IManagesLogService managesLogService;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(daySaleTableDao);
    }

    public void countSaleTable() {

        Date[] objectArray = getDateArray();
        Date date1 = objectArray[1];
        Date date = objectArray[0];

        String successSql = "select t.sid as sid,count(t.id) as successTicket,sum(t.amount) as successAmount from tms_ticket t where create_time >= ? and create_time < ? and ticket_status = ? group by t.sid";
        String failureSql = "select t.sid as sid,count(t.id) as failureTicket,sum(t.amount) as failureAmount from tms_ticket t where  create_time >= ? and create_time < ? and ticket_status = ? group by t.sid";
        String bonusSql = "select t.sid as sid,count(t.id) as bonusTicket,sum(t.bonus_amount) as bonusAmount,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t where create_time >= ? and create_time < ? and ticket_status = ? and bonus_status = ? group by t.sid";

        List<Map<String, Object>> successMapList = daySaleTableDao.customSql(successSql, new Object[]{date, date1, Constants.TICKET_STATUS_SUCCESS});
        List<Map<String, Object>> failureMapList = daySaleTableDao.customSql(failureSql, new Object[]{date, date1, Constants.TICKET_STATUS_FAILURE});
        List<Map<String, Object>> bonusMapList = daySaleTableDao.customSql(bonusSql, new Object[]{date, date1, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_YES});

        String memberSql = "select sid,name from user_member where status = ? ";

        Map<String, DaySaleTable> saleTableMap = new HashMap<String, DaySaleTable>();
        List<Map<String, Object>> memberMapList = daySaleTableDao.customSql(memberSql, new Object[]{Constants.MEMBER_STATUS_LIVE});
        for (Map<String, Object> memberMap : memberMapList) {
            String sid = memberMap.get("sid") + "";
            String name = memberMap.get("name") + "";
            DaySaleTable saleTable = null;
            if (saleTableMap.containsKey(sid)) {
                saleTable = saleTableMap.get(sid);
            } else {
                saleTable = new DaySaleTable();
            }
            saleTable.setSid(sid);
            saleTable.setName(name);
            saleTable.setCurDate(Utils.formatDate2Str(date, "yyyy-MM-dd"));
            saleTable.setSuccessTicket(0L);
            saleTable.setSuccessAmount(0d);
            saleTable.setFailureTicket(0L);
            saleTable.setFailureAmount(0d);
            saleTable.setBonusTicket(0L);
            saleTable.setBonusAmount(0d);
            saleTable.setFixBonusAmount(0d);
            saleTableMap.put(sid, saleTable);
        }

        for (Map<String, Object> successMap : successMapList) {
            long successTicket = format(successMap.get("successTicket")).longValue();
            double successAmount = format(successMap.get("successAmount")).doubleValue();
            String sid = successMap.get("sid") + "";
            if (saleTableMap.containsKey(sid)) {
                DaySaleTable saleTable = saleTableMap.get(sid);
                saleTable.setSuccessTicket(successTicket);
                saleTable.setSuccessAmount(successAmount);
                saleTableMap.put(sid, saleTable);
            }
        }

        for (Map<String, Object> failureMap : failureMapList) {
            long failureTicket = format(failureMap.get("failureTicket")).longValue();
            double failureAmount = format(failureMap.get("failureAmount")).doubleValue();
            String sid = failureMap.get("sid") + "";
            if (saleTableMap.containsKey(sid)) {
                DaySaleTable saleTable = saleTableMap.get(sid);
                saleTable.setFailureTicket(failureTicket);
                saleTable.setFailureAmount(failureAmount);
                saleTableMap.put(sid, saleTable);
            }
        }

        for (Map<String, Object> bonusMap : bonusMapList) {
            long bonusTicket = format(bonusMap.get("bonusTicket")).longValue();
            double bonusAmount = format(bonusMap.get("bonusAmount")).doubleValue();
            double fixBonusAmount = format(bonusMap.get("fixBonusAmount")).doubleValue();
            String sid = bonusMap.get("sid") + "";
            if (saleTableMap.containsKey(sid)) {
                DaySaleTable saleTable = saleTableMap.get(sid);
                saleTable.setBonusTicket(bonusTicket);
                saleTable.setBonusAmount(bonusAmount);
                saleTable.setFixBonusAmount(fixBonusAmount);
                saleTableMap.put(sid, saleTable);
            }
        }

        List<DaySaleTable> saleTableList = new ArrayList<DaySaleTable>();
        for (Map.Entry<String, DaySaleTable> entry : saleTableMap.entrySet()) {
            DaySaleTable saleTable = entry.getValue();
            saleTable.setCreateTime(new Date());
            saleTableList.add(saleTable);
        }

        DaySaleTable allSaleTable = new DaySaleTable();
        allSaleTable.setCurDate(Utils.formatDate2Str(date, "yyyy-MM-dd"));
        allSaleTable.setSuccessTicket(0L);
        allSaleTable.setSuccessAmount(0d);
        allSaleTable.setFailureTicket(0L);
        allSaleTable.setFailureAmount(0d);
        allSaleTable.setBonusTicket(0L);
        allSaleTable.setBonusAmount(0d);
        allSaleTable.setFixBonusAmount(0d);
        for (DaySaleTable saleTable : saleTableList) {
            allSaleTable.setSuccessAmount(allSaleTable.getSuccessAmount() + saleTable.getSuccessAmount());
            allSaleTable.setSuccessTicket(allSaleTable.getSuccessTicket() + saleTable.getSuccessTicket());

            allSaleTable.setFailureAmount(allSaleTable.getFailureAmount() + saleTable.getFailureAmount());
            allSaleTable.setFailureTicket(allSaleTable.getFailureTicket() + saleTable.getFailureTicket());

            allSaleTable.setBonusAmount(allSaleTable.getBonusAmount() + saleTable.getBonusAmount());
            allSaleTable.setFixBonusAmount(allSaleTable.getFixBonusAmount() + saleTable.getFixBonusAmount());
            allSaleTable.setBonusTicket(allSaleTable.getBonusTicket() + saleTable.getBonusTicket());

        }
        allSaleTable.setCreateTime(new Date());
        saleTableList.add(allSaleTable);
        daySaleTableDao.saveAllObject(saleTableList);
        String des = "生成日销售报表";
        managesLogService.addManagesLog(des, Constants.MANAGER_LOG_DATA);

    }

    @Override
    public PageBean getPageBeanByPara(DaySaleTable daySaleTable, Integer page, Integer pageSize) {
        return daySaleTableDao.getPageBeanByPara(daySaleTable, page, pageSize);
    }

    @Override
    public Map<String, Object> getdaySaleTableCount(DaySaleTable daySaleTable) {
        return daySaleTableDao.getdaySaleTableCount(daySaleTable);
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
        IDaySaleTableService daySaleTableService = (IDaySaleTableService) SpringUtils.getBean("daySaleTableServiceImpl");
        daySaleTableService.countSaleTable();
    }
}
