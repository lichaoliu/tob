package com.cndym.service.impl;

import com.cndym.bean.count.AccountTable;
import com.cndym.constant.Constants;
import com.cndym.dao.IAccountTableDao;
import com.cndym.dao.IMemberDao;
import com.cndym.service.IAccountTableService;
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
 * Date: 12-10-31
 * Time: 下午2:18
 */

@Service
public class AccountTableServiceImpl extends GenericServiceImpl<AccountTable, IAccountTableDao> implements IAccountTableService {

    @Autowired
    private IAccountTableDao accountTableDao;

    @Autowired
    private IMemberDao memberDao;

    @Autowired
    private IManagesLogService managesLogService;

    private Logger logger = Logger.getLogger(getClass());

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(accountTableDao);
    }

    @Override
    public void countAccountTable() {
        String sql = "select ual.user_code as userCode,ual.event_code as eventCode," +
                "sum(ual.bonus_amount) as bonusAmount,count(id) as numberCount," +
                "sum(ual.recharge_amount) as rechargeAmount," +
                "sum(case when ual.backup1 is null then ual.present_amount else 0 end) as presentAmount," +
                "sum(ual.freeze_amount) as freezeAmount " +
                " FROM user_account_log ual where ual.create_time >= ? and ual.create_time < ? group by ual.event_code,user_code";

        String sqlAccount = "select um.user_code as userCode,sum(a.bonus_amount) as bonusAmount, sum(a.recharge_amount) as rechargeAmount, " +
                "sum(a.present_amount) as presentAmount ,sum(a.freeze_amount) as freezeAmount FROM user_account a,user_member um where a.user_code = um.user_code group by um.user_code";

        Date[] objectArray = getDateArray();
        Date date1 = objectArray[1];
        Date date = objectArray[0];
        List<Map<String, Object>> dataList = accountTableDao.customListSql(sql, new Object[]{date, date1});
        List<Map<String, Object>> accountMapList = accountTableDao.customListSql(sqlAccount, null);

        String memberSql = "select sid,name,user_code as userCode from user_member where status = ? ";

        Map<String, AccountTable> accountTableMap = new HashMap<String, AccountTable>();
        List<Map<String, Object>> memberMapList = accountTableDao.customListSql(memberSql, new Object[]{Constants.MEMBER_STATUS_LIVE});

        for (Map<String, Object> memberMap : memberMapList) {
            String sid = memberMap.get("sid") + "";
            String name = memberMap.get("name") + "";
            String userCode = memberMap.get("userCode") + "";
            AccountTable accountTable = null;
            if (accountTableMap.containsKey(userCode)) {
                accountTable = accountTableMap.get(userCode);
            } else {
                accountTable = new AccountTable();
            }
            accountTable.setSid(sid);
            accountTable.setName(name);
            accountTable.setCurrDate(Utils.formatDate2Str(date, "yyyy-MM-dd"));
            accountTable.setPayAmount(0d);
            accountTable.setPayNum(0L);
            accountTable.setFailureAmount(0d);
            accountTable.setFailureNum(0L);
            accountTable.setBonusAmount(0d);
            accountTable.setBonusNum(0L);
            accountTable.setCommissionAmount(0d);
            accountTable.setCommissionNum(0L);
            accountTable.setEditAccountJia(0d);
            accountTable.setEditAccountJian(0d);
            accountTable.setRechargeAmountNew(0d);
            accountTable.setBonusAmountNew(0d);
            accountTable.setPresentAmountNew(0d);
            accountTable.setFreezeAmountNew(0d);

            accountTableMap.put(userCode, accountTable);
        }

        for (Map<String, Object> accountMap : accountMapList) {
            String userCode = accountMap.get("userCode") + "";
            double bonusAmount = format(accountMap.get("bonusAmount")).doubleValue();
            double presentAmount = format(accountMap.get("presentAmount")).doubleValue();
            double rechargeAmount = format(accountMap.get("rechargeAmount")).doubleValue();
            double freezeAmount = format(accountMap.get("freezeAmount")).doubleValue();
            if (accountTableMap.containsKey(userCode)) {
                AccountTable accountTable = accountTableMap.get(userCode);
                accountTable.setBonusAmountNew(bonusAmount);
                accountTable.setPresentAmountNew(presentAmount);
                accountTable.setRechargeAmountNew(rechargeAmount);
                accountTable.setFreezeAmountNew(freezeAmount);

                accountTableMap.put(userCode, accountTable);
            }
        }

        for (Map<String, Object> dataMap : dataList) {
            String userCode = dataMap.get("userCode") + "";
            String eventCode = dataMap.get("EVENTCODE").toString();
            Double freezeamount = new Double(dataMap.get("FREEZEAMOUNT").toString());
            Double presentamount = new Double(dataMap.get("PRESENTAMOUNT").toString());
            Double rechargeamount = new Double(dataMap.get("RECHARGEAMOUNT").toString());
            Double bonusamount = new Double(dataMap.get("BONUSAMOUNT").toString());
            Long count = new Long(dataMap.get("NUMBERCOUNT").toString());
            Double amount = Math.abs(presentamount + rechargeamount + bonusamount);
            if (accountTableMap.containsKey(userCode)) {
                AccountTable accountTable = accountTableMap.get(userCode);
                //投注失败退款
                if (eventCode.equals("00100")) {
                    accountTable.setFailureAmount(amount);
                    accountTable.setFailureNum(count);
                }
                //返奖
                if (eventCode.equals("00200")) {
                    accountTable.setBonusAmount(amount);
                    accountTable.setBonusNum(count);
                }
                //返佣
                if (eventCode.equals("00400")) {
                    accountTable.setCommissionAmount(amount);
                    accountTable.setCommissionNum(count);
                }
                //额度加
                if (eventCode.equals("00500")) {
                    accountTable.setEditAccountJia(amount);
                }
                //投注支付
                if (eventCode.equals("10000")) {
                    accountTable.setPayAmount(amount);
                    accountTable.setPayNum(count);
                }
                //额度减
                if (eventCode.equals("10400")) {
                    accountTable.setEditAccountJian(amount);
                }
                accountTableMap.put(userCode, accountTable);
            }
        }

        List<AccountTable> accountTableList = new ArrayList<AccountTable>();
        for (Map.Entry<String, AccountTable> entry : accountTableMap.entrySet()) {
            AccountTable accountTable = entry.getValue();
            accountTable.setCreateTime(new Date());
            accountTableList.add(accountTable);
        }

        //累加所有接入商数据
        AccountTable allAccountTable = new AccountTable();
        allAccountTable.setPayAmount(0d);
        allAccountTable.setPayNum(0L);
        allAccountTable.setFailureAmount(0d);
        allAccountTable.setFailureNum(0L);
        allAccountTable.setBonusAmount(0d);
        allAccountTable.setBonusNum(0L);
        allAccountTable.setCommissionAmount(0d);
        allAccountTable.setCommissionNum(0L);
        allAccountTable.setEditAccountJia(0d);
        allAccountTable.setEditAccountJian(0d);
        allAccountTable.setRechargeAmountNew(0d);
        allAccountTable.setBonusAmountNew(0d);
        allAccountTable.setPresentAmountNew(0d);
        allAccountTable.setFreezeAmountNew(0d);
        allAccountTable.setCurrDate(Utils.formatDate2Str(date, "yyyy-MM-dd"));
        for (AccountTable accountTable : accountTableList) {
            allAccountTable.setPayAmount(allAccountTable.getPayAmount() + accountTable.getPayAmount());
            allAccountTable.setPayNum(allAccountTable.getPayNum() + accountTable.getPayNum());

            allAccountTable.setBonusAmount(allAccountTable.getBonusAmount() + accountTable.getBonusAmount());
            allAccountTable.setBonusNum(allAccountTable.getBonusNum() + accountTable.getBonusNum());

            allAccountTable.setCommissionAmount(allAccountTable.getCommissionAmount() + accountTable.getCommissionAmount());
            allAccountTable.setCommissionNum(allAccountTable.getCommissionNum() + accountTable.getCommissionNum());

            allAccountTable.setFailureAmount(allAccountTable.getFailureAmount() + accountTable.getFailureAmount());
            allAccountTable.setFailureNum(allAccountTable.getFailureNum() + accountTable.getFailureNum());

            allAccountTable.setEditAccountJia(allAccountTable.getEditAccountJia() + accountTable.getEditAccountJia());
            allAccountTable.setEditAccountJian(allAccountTable.getEditAccountJian() + accountTable.getEditAccountJian());

            allAccountTable.setBonusAmountNew(allAccountTable.getBonusAmountNew() + accountTable.getBonusAmountNew());
            allAccountTable.setPresentAmountNew(allAccountTable.getPresentAmountNew() + accountTable.getPresentAmountNew());
            allAccountTable.setRechargeAmountNew(allAccountTable.getRechargeAmountNew() + accountTable.getRechargeAmountNew());
            allAccountTable.setFreezeAmountNew(allAccountTable.getFreezeAmountNew() + accountTable.getFreezeAmountNew());
        }
        allAccountTable.setCreateTime(new Date());
        accountTableList.add(allAccountTable);
        accountTableDao.saveAllObject(accountTableList);
        String des = "生成日报表";
        managesLogService.addManagesLog(des, Constants.MANAGER_LOG_DATA);
    }

    @Override
    public PageBean getPageBeanByPara(AccountTable accountTable, Integer page, Integer pageSize) {
        return accountTableDao.getPageBeanByPara(accountTable, page, pageSize);
    }

    @Override
    public Map<String, Object> getAccountTableCount(AccountTable accountTable) {
        return accountTableDao.getAccountTableCount(accountTable);
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
        IAccountTableService accountTableService = (IAccountTableService) SpringUtils.getBean("accountTableServiceImpl");
        accountTableService.countAccountTable();
    }
}
