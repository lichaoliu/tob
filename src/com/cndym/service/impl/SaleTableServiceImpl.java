package com.cndym.service.impl;

import com.cndym.bean.count.SaleTable;
import com.cndym.bean.tms.MainIssue;
import com.cndym.constant.Constants;
import com.cndym.dao.IMainIssueDao;
import com.cndym.dao.IManagesLogDao;
import com.cndym.dao.ISaleTableDao;
import com.cndym.service.IManagesLogService;
import com.cndym.service.ISaleTableService;
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
 * Time: 上午11:51
 */
@Service
public class SaleTableServiceImpl extends GenericServiceImpl<SaleTable, ISaleTableDao> implements ISaleTableService {

    @Autowired
    private ISaleTableDao saleTableDao;

    @Autowired
    private IManagesLogService managesLogService;

    @Autowired
    private IMainIssueDao mainIssueDao;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(saleTableDao);
    }


    @Override
    public void countSaleTable(String lotteryCode, String issue) {

        MainIssue mainIssue = mainIssueDao.getMainIssueByLotteryIssue(lotteryCode, issue);

        String successSql = "select t.sid as sid,count(t.id) as successTicket,sum(t.amount) as successAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? group by t.sid";
        String failureSql = "select t.sid as sid,count(t.id) as failureTicket,sum(t.amount) as failureAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? group by t.sid";
        String bonusSql = "select t.sid as sid,count(t.id) as bonusTicket,sum(t.bonus_amount) as bonusAmount,sum(t.fix_bonus_amount) as fixBonusAmount from tms_ticket t where t.lottery_code = ? and t.issue = ? and ticket_status = ? and bonus_status = ? group by t.sid";

        List<Map<String, Object>> successMapList = saleTableDao.customSql(successSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS});
        List<Map<String, Object>> failureMapList = saleTableDao.customSql(failureSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_FAILURE});
        List<Map<String, Object>> bonusMapList = saleTableDao.customSql(bonusSql, new Object[]{lotteryCode, issue, Constants.TICKET_STATUS_SUCCESS, Constants.BONUS_STATUS_YES});

        String memberSql = "select sid,name from user_member where status = ? ";

        Map<String, SaleTable> saleTableMap = new HashMap<String, SaleTable>();
        List<Map<String, Object>> memberMapList = saleTableDao.customSql(memberSql, new Object[]{Constants.MEMBER_STATUS_LIVE});
        for (Map<String, Object> memberMap : memberMapList) {
            String sid = memberMap.get("sid") + "";
            String name = memberMap.get("name") + "";
            SaleTable saleTable = null;
            if (saleTableMap.containsKey(sid)) {
                saleTable = saleTableMap.get(sid);
            } else {
                saleTable = new SaleTable();
            }
            saleTable.setSid(sid);
            saleTable.setName(name);
            saleTable.setLotteryCode(lotteryCode);
            saleTable.setIssue(issue);
            saleTable.setStartTime(mainIssue.getStartTime());
            saleTable.setEndTime(mainIssue.getEndTime());
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
                SaleTable saleTable = saleTableMap.get(sid);
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
                SaleTable saleTable = saleTableMap.get(sid);
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
                SaleTable saleTable = saleTableMap.get(sid);
                saleTable.setBonusTicket(bonusTicket);
                saleTable.setBonusAmount(bonusAmount);
                saleTable.setFixBonusAmount(fixBonusAmount);
                saleTableMap.put(sid, saleTable);
            }
        }

        List<SaleTable> saleTableList = new ArrayList<SaleTable>();
        for (Map.Entry<String, SaleTable> entry : saleTableMap.entrySet()) {
            SaleTable saleTable = entry.getValue();
            saleTable.setCreateTime(new Date());
            saleTableList.add(saleTable);
        }

        SaleTable allSaleTable = new SaleTable();
        allSaleTable.setLotteryCode(lotteryCode);
        allSaleTable.setIssue(issue);
        allSaleTable.setStartTime(mainIssue.getStartTime());
        allSaleTable.setEndTime(mainIssue.getEndTime());
        allSaleTable.setSuccessTicket(0L);
        allSaleTable.setSuccessAmount(0d);
        allSaleTable.setFailureTicket(0L);
        allSaleTable.setFailureAmount(0d);
        allSaleTable.setBonusTicket(0L);
        allSaleTable.setBonusAmount(0d);
        allSaleTable.setFixBonusAmount(0d);
        for (SaleTable saleTable : saleTableList) {
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
        saleTableDao.saveAllObject(saleTableList);
        String des = "生成" + lotteryCode + "【" + issue + "】期销售报表";
        managesLogService.addManagesLog(des, Constants.MANAGER_LOG_DATA);
    }

    @Override
    public PageBean getPageBeanByPara(SaleTable saleTable, Integer page, Integer pageSize) {
        return saleTableDao.getPageBeanByPara(saleTable, page, pageSize);
    }

    @Override
    public PageBean getPageBeanListByPara(SaleTable saleTable, Integer page, Integer pageSize) {
        return saleTableDao.getPageBeanListByPara(saleTable, page, pageSize);
    }

    private static BigDecimal format(Object object) {
        if (null == object) {
            return new BigDecimal(0);
        }
        return (BigDecimal) object;
    }
}
