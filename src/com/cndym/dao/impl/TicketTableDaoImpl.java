package com.cndym.dao.impl;

import com.cndym.bean.count.TicketTable;
import com.cndym.dao.ITicketTableDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
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
 * Time: 下午7:24
 */
@Repository
public class TicketTableDaoImpl extends GenericDaoImpl<TicketTable> implements ITicketTableDao {

    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;


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
    public PageBean getPageBeanByPara(TicketTable ticketTable, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("from TicketTable where 1=1 ");
        List<HibernatePara> paraList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(ticketTable)) {
            if (Utils.isNotEmpty(ticketTable.getLotteryCode())) {
                sql.append(" and lotteryCode = ? ");
                paraList.add(new HibernatePara(ticketTable.getLotteryCode()));
            }
            if (Utils.isNotEmpty(ticketTable.getIssue())) {
                sql.append(" and issue = ? ");
                paraList.add(new HibernatePara(ticketTable.getIssue()));
            }
            if (Utils.isNotEmpty(ticketTable.getPostCode())) {
                if (ticketTable.getPostCode().equals("true")) {
                    sql.append(" and postCode is not null ");
                } else if (ticketTable.getPostCode().equals("false")) {
                    sql.append(" and postCode is null ");
                } else {
                    sql.append(" and postCode = ? ");
                    paraList.add(new HibernatePara(ticketTable.getPostCode()));
                }
            }
        }
        sql.append(" order by createTime desc");
        return getPageBeanByPara(sql.toString(), paraList, page, pageSize);
    }
}
