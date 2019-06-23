package com.cndym.dao.impl;

import com.cndym.bean.count.AccountTable;
import com.cndym.dao.IAccountTableDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: mcs
 * Date: 12-10-31
 * Time: 下午2:16
 */

@Repository
public class AccountTableDaoImpl extends GenericDaoImpl<AccountTable> implements IAccountTableDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public List<Map<String, Object>> customListSql(String sql, Object[] para) {
        return jdbcTemplate.queryForList(sql, para);
    }

    @Override
    public Map<String, Object> customSql(String sql, Object[] para) {
        return jdbcTemplate.queryForMap(sql, para);
    }

    @Override
    public PageBean getPageBeanByPara(AccountTable accountTable, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from AccountTable where 1=1 ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(accountTable)) {
            if (Utils.isNotEmpty(accountTable.getSid())) {
                if (accountTable.getSid().equals("true")) {
                    sql.append(" and sid is not null ");
                } else if (accountTable.getSid().equals("false")) {
                    sql.append(" and sid is null ");
                } else {
                    sql.append(" and sid = ? ");
                    paraList.add(new HibernatePara(accountTable.getSid()));
                }
            }
            if (Utils.isNotEmpty(accountTable.getName())) {
                sql.append(" and name like ? ");
                paraList.add(new HibernatePara("%" + accountTable.getName() + "%"));
            }
            if (Utils.isNotEmpty(accountTable.getStartDate())) {
                sql.append(" and currDate >= ? ");
                paraList.add(new HibernatePara(accountTable.getStartDate()));
            }
            if (Utils.isNotEmpty(accountTable.getEndDate())) {
                sql.append(" and currDate <= ? ");
                paraList.add(new HibernatePara(accountTable.getEndDate()));
            }
        }
        sql.append(" order by currDate desc, sid asc ");
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }

    @Override
    public Map<String, Object> getAccountTableCount(AccountTable accountTable) {
        StringBuffer sql = new StringBuffer("select sum(t.BONUS_AMOUNT) bonusAmount,sum(t.PAY_AMOUNT) payAmount,sum(t.COMMISSION_AMOUNT) commAmount,sum(t.FAILURE_AMOUNT) failuerAmount," +
                "sum(t.EDIT_ACCOUNT_JIA) editAmountJia,sum(t.EDIT_ACCOUNT_JIAN) editAmountJian,sum(t.BONUS_AMOUNT_NEW) bonusAmontNew,sum(t.RECHARGE_AMOUNT_NEW) rechargeAmountNew," +
                "sum(t.PRESENT_AMOUNT_NEW) presentAmountNew from count_account_table t where 1=1 ");
        List<Object> paraList = new ArrayList<Object>();
        if (Utils.isNotEmpty(accountTable)) {
            if (Utils.isNotEmpty(accountTable.getSid())) {
                if (accountTable.getSid().equals("true")) {
                    sql.append(" and t.sid is not null ");
                } else if (accountTable.getSid().equals("false")) {
                    sql.append(" and t.sid is null ");
                } else {
                    sql.append(" and t.sid = ? ");
                    paraList.add(accountTable.getSid());
                }
            }
            if (Utils.isNotEmpty(accountTable.getName())) {
                sql.append(" and t.name like ? ");
                paraList.add("%" + accountTable.getName() + "%");
            }
            if (Utils.isNotEmpty(accountTable.getStartDate())) {
                sql.append(" and t.curr_date >= ? ");
                paraList.add(accountTable.getStartDate());
            }
            if (Utils.isNotEmpty(accountTable.getEndDate())) {
                sql.append(" and t.curr_date <= ? ");
                paraList.add(accountTable.getEndDate());
            }
        }
        sql.append(" order by t.create_time desc ");
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paraList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }
}
