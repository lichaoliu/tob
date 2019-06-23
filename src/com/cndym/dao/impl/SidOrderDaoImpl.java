package com.cndym.dao.impl;

import com.cndym.bean.tms.SidOrder;
import com.cndym.dao.ISidOrderDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * User: mcs
 * Date: 12-11-11
 * Time: 下午5:02
 */
@Repository
public class SidOrderDaoImpl extends GenericDaoImpl<SidOrder> implements ISidOrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

}
