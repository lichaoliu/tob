package com.cndym.dao.impl;

import com.cndym.bean.count.SaleTable;
import com.cndym.dao.ISaleTableDao;
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
 * Time: 上午11:48
 */
@Repository
public class SaleTableDaoImpl extends GenericDaoImpl<SaleTable> implements ISaleTableDao {

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
    public SaleTable getSaleTableByLotteryAndIssue(String sid, String lotteryCode, String issue) {
        String sql = "From SaleTable where sid = ? and lotteryCode=? and issue=?";
        List<SaleTable> saleTables = find(sql, new String[]{sid, lotteryCode, issue});
        if (saleTables.isEmpty()) {
            return null;
        }
        return saleTables.get(0);
    }

    @Override
    public PageBean getPageBeanByPara(SaleTable saleTable, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from SaleTable where 1=1 ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(saleTable)) {
            if (Utils.isNotEmpty(saleTable.getLotteryCode())) {
                sql.append(" and lotteryCode = ? ");
                paraList.add(new HibernatePara(saleTable.getLotteryCode()));
            }
            if (Utils.isNotEmpty(saleTable.getIssue())) {
                sql.append(" and issue = ? ");
                paraList.add(new HibernatePara(saleTable.getIssue()));
            }
            if (Utils.isNotEmpty(saleTable.getSid())) {
                if (saleTable.getSid().equals("true")) {
                    sql.append(" and sid is not null ");
                } else if (saleTable.getSid().equals("false")) {
                    sql.append(" and sid is null ");
                } else {
                    sql.append(" and sid = ? ");
                    paraList.add(new HibernatePara(saleTable.getSid()));
                }
            }
            if (Utils.isNotEmpty(saleTable.getName())) {
                sql.append(" and name like ? ");
                paraList.add(new HibernatePara("%" + saleTable.getName() + "%"));
            }
        }
        sql.append(" order by createTime desc");
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }

    @Override
    public PageBean getPageBeanListByPara(SaleTable saleTable, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from SaleTable where 1=1 ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(saleTable)) {
            if (Utils.isNotEmpty(saleTable.getSid())) {
                sql.append(" and sid = ? ");
                paraList.add(new HibernatePara(saleTable.getSid()));
            }
            if (Utils.isNotEmpty(saleTable.getLotteryCode())) {
                sql.append(" and lotteryCode = ? ");
                paraList.add(new HibernatePara(saleTable.getLotteryCode()));
            }
            if (Utils.isNotEmpty(saleTable.getCbonusAmount())) {
                sql.append(" and bonusAmount >= ? ");
                paraList.add(new HibernatePara(saleTable.getCbonusAmount()));
            }
            if (Utils.isNotEmpty(saleTable.getDbonusAmount())) {
                sql.append(" and bonusAmount <= ? ");
                paraList.add(new HibernatePara(saleTable.getDbonusAmount()));
            }
            if (Utils.isNotEmpty(saleTable.getIssue())) {
                sql.append(" and issue = ? ");
                paraList.add(new HibernatePara(saleTable.getIssue()));
            }
            if (Utils.isNotEmpty(saleTable.getStartTime())) {
                sql.append(" and startTime = ? ");
                paraList.add(new HibernatePara(saleTable.getStartTime()));
            }
            if (Utils.isNotEmpty(saleTable.getEndTime())) {
                sql.append(" and endTime = ? ");
                paraList.add(new HibernatePara(saleTable.getEndTime()));
            }

        }
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }
}
