package com.cndym.dao.impl;

import com.cndym.bean.user.AccountOperatorTemp;
import com.cndym.dao.IAccountOperatorTempDao;
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

/**
 * User: 邓玉明
 * Date: 11-3-27 下午10:25
 */
@Repository
public class AccountOperatorTempDaoImpl extends GenericDaoImpl<AccountOperatorTemp> implements IAccountOperatorTempDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public PageBean getAccountOperatorTempList(AccountOperatorTemp accountOperatorTemp, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From AccountOperatorTemp where 1=1");
        List<HibernatePara> hibernateParaList = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(accountOperatorTemp)) {
            if (Utils.isNotEmpty(accountOperatorTemp.getOrderId())) {
                sql.append(" and orderId=?");
                hibernateParaList.add(new HibernatePara(accountOperatorTemp.getOrderId()));
            }
            if (Utils.isNotEmpty(accountOperatorTemp.getEventCode())) {
                sql.append(" and eventCode=?");
                hibernateParaList.add(new HibernatePara(accountOperatorTemp.getEventCode()));
            }
            if (Utils.isNotEmpty(accountOperatorTemp.getUserCode())) {
                sql.append(" and userCode=?");
                hibernateParaList.add(new HibernatePara(accountOperatorTemp.getUserCode()));
            }
            if (Utils.isNotEmpty(accountOperatorTemp.getStatus())) {
                sql.append(" and status=?");
                hibernateParaList.add(new HibernatePara(accountOperatorTemp.getStatus()));
            }
        }
        return getPageBeanByPara(sql.toString(), hibernateParaList, page, pageSize);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }
}
