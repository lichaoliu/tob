package com.cndym.dao.impl;

import com.cndym.bean.count.DaySaleTable;
import com.cndym.dao.IDaySaleTableDao;
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
 * Date: 12-11-26
 * Time: 下午4:11
 */
@Repository
public class DaySaleTableDaoImpl extends GenericDaoImpl<DaySaleTable> implements IDaySaleTableDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public List<Map<String, Object>> customSql(String sql, Object[] para) {
        return jdbcTemplate.queryForList(sql, para);
    }

    @Override
    public PageBean getPageBeanByPara(DaySaleTable daySaleTable, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from DaySaleTable where 1=1 ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(daySaleTable)) {
            if (Utils.isNotEmpty(daySaleTable.getStartDate())) {
                sql.append(" and curDate >= ? ");
                paraList.add(new HibernatePara(daySaleTable.getStartDate()));
            }
            if (Utils.isNotEmpty(daySaleTable.getEndDate())) {
                sql.append(" and curDate <= ? ");
                paraList.add(new HibernatePara(daySaleTable.getEndDate()));
            }
            if (Utils.isNotEmpty(daySaleTable.getSid())) {
                if (daySaleTable.getSid().equals("true")) {
                    sql.append(" and sid is not null ");
                } else if (daySaleTable.getSid().equals("false")) {
                    sql.append(" and sid is null ");
                } else {
                    sql.append(" and sid = ? ");
                    paraList.add(new HibernatePara(daySaleTable.getSid()));
                }
            }
            if (Utils.isNotEmpty(daySaleTable.getName())) {
                sql.append(" and name like ? ");
                paraList.add(new HibernatePara("%" + daySaleTable.getName() + "%"));
            }
        }
        sql.append(" order by createTime desc");
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }

    @Override
    public Map<String, Object> getdaySaleTableCount(DaySaleTable daySaleTable) {
        StringBuffer sql = new StringBuffer("select sum(t.SUCCESS_AMOUNT) succAmount,sum(t.SUCCESS_TICKET) succNum,sum(t.FAILURE_AMOUNT) failAmount,");
        sql.append("sum(t.FAILURE_TICKET) failNum,sum(t.BONUS_AMOUNT) bonusAmount,sum(t.FIX_BONUS_AMOUNT) fixBonusAmount,sum(t.BONUS_TICKET) bonusNum");
        sql.append(" from COUNT_DAY_SALE_TABLE t where 1=1 ");
        List<Object> paraList = new ArrayList<Object>();
        if (Utils.isNotEmpty(daySaleTable)) {
            if (Utils.isNotEmpty(daySaleTable.getStartDate())) {
                sql.append(" and t.CUR_DATE >= ? ");
                paraList.add(daySaleTable.getStartDate());
            }
            if (Utils.isNotEmpty(daySaleTable.getEndDate())) {
                sql.append(" and t.CUR_DATE <= ? ");
                paraList.add(daySaleTable.getEndDate());
            }
            if (Utils.isNotEmpty(daySaleTable.getSid())) {
                if (daySaleTable.getSid().equals("true")) {
                    sql.append(" and t.sid is not null ");
                } else if (daySaleTable.getSid().equals("false")) {
                    sql.append(" and t.sid is null ");
                } else {
                    sql.append(" and t.sid = ? ");
                    paraList.add(daySaleTable.getSid());
                }
            }
            if (Utils.isNotEmpty(daySaleTable.getName())) {
                sql.append(" and t.name like ? ");
                paraList.add("%" + daySaleTable.getName() + "%");
            }
        }
        sql.append(" order by t.create_time desc");
        List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql.toString(), paraList.toArray());
        if (Utils.isNotEmpty(dataList)) {
            return dataList.get(0);
        }
        return null;
    }
}
