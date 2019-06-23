package com.cndym.dao.impl;

import com.cndym.bean.count.DayTicketTable;
import com.cndym.dao.IDayTicketTableDao;
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
 * Time: 下午4:28
 */

@Repository
public class DayTicketTableDaoImpl extends GenericDaoImpl<DayTicketTable> implements IDayTicketTableDao {

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
    public PageBean getPageBeanByPara(DayTicketTable dayTicketTable, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from DayTicketTable where 1=1 ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(dayTicketTable)) {

            if (Utils.isNotEmpty(dayTicketTable.getStartDate())) {
                sql.append(" and curDate >= ? ");
                paraList.add(new HibernatePara(dayTicketTable.getStartDate()));
            }

            if (Utils.isNotEmpty(dayTicketTable.getEndDate())) {
                sql.append(" and curDate <= ? ");
                paraList.add(new HibernatePara(dayTicketTable.getEndDate()));
            }

            if (Utils.isNotEmpty(dayTicketTable.getPostCode())) {
                if (dayTicketTable.getPostCode().equals("true")) {
                    sql.append(" and postCode is not null ");
                } else if (dayTicketTable.getPostCode().equals("false")) {
                    sql.append(" and postCode is null ");
                } else {
                    sql.append(" and postCode = ? ");
                    paraList.add(new HibernatePara(dayTicketTable.getPostCode()));
                }
            }
        }
        sql.append(" order by createTime desc");
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }
}
