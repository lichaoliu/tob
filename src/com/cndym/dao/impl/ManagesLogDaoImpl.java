package com.cndym.dao.impl;

import com.cndym.bean.user.ManagesLog;
import com.cndym.dao.IManagesLogDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-5-24 下午09:43:26
 */

@Repository
public class ManagesLogDaoImpl extends GenericDaoImpl<ManagesLog> implements IManagesLogDao {
    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PageBean findManagesLogList(ManagesLog managesLog, Date startTime,
                                       Date endTime, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From ManagesLog managesLog where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(managesLog)) {
            if (Utils.isNotEmpty(managesLog.getOperatingType())) {
                sql.append(" and managesLog.operatingType=?");
                hibernateParas.add(new HibernatePara(managesLog.getOperatingType()));
            }
            if (Utils.isNotEmpty(managesLog.getAdminName())) {
                sql.append(" and managesLog.adminName=?");
                hibernateParas.add(new HibernatePara(managesLog.getAdminName()));
            }
            if (Utils.isNotEmpty(managesLog.getIp())) {
                sql.append(" and managesLog.ip=?");
                hibernateParas.add(new HibernatePara(managesLog.getIp()));
            }
            if (Utils.isNotEmpty(managesLog.getDescription())) {
                sql.append(" and managesLog.description like ? ");
                hibernateParas.add(new HibernatePara("%" + managesLog.getDescription() + "%"));
            }
            if (Utils.isNotEmpty(startTime)) {
                sql.append(" and managesLog.createTime>=?");
                hibernateParas.add(new HibernatePara(startTime));
            }
            if (Utils.isNotEmpty(endTime)) {
                sql.append(" and managesLog.createTime<?");
                hibernateParas.add(new HibernatePara(endTime));
            }
        }
        sql.append(" order by managesLog.createTime desc ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }


    public PageBean findCooperationLogLogList(ManagesLog managesLog, Date startTime, Date endTime, Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From ManagesLog managesLog where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(managesLog)) {
            if (Utils.isNotEmpty(managesLog.getOperatingType())) {
                sql.append(" and managesLog.operatingType=?");
                hibernateParas.add(new HibernatePara(managesLog.getOperatingType()));
            } else {
                sql.append(" and managesLog.operatingType in (7,8,9,10) ");
            }
            if (Utils.isNotEmpty(managesLog.getAdminName())) {
                sql.append(" and managesLog.adminName=?");
                hibernateParas.add(new HibernatePara(managesLog.getAdminName()));
            }
            if (Utils.isNotEmpty(startTime)) {
                sql.append(" and managesLog.createTime>=?");
                hibernateParas.add(new HibernatePara(startTime));
            }
            if (Utils.isNotEmpty(endTime)) {
                sql.append(" and managesLog.createTime<=?");
                hibernateParas.add(new HibernatePara(endTime));
            }
        }
        sql.append(" order by managesLog.createTime desc ");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

    @Override
    public ManagesLog getManagesLogDaoById(String managesLogId) {
        return null;
    }

    @Override
    public int updateManagesLog(ManagesLog accountLog) {
        return 0;
    }


}
