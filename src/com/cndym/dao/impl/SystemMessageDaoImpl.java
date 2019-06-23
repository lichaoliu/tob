package com.cndym.dao.impl;

import com.cndym.bean.user.SystemMessage;
import com.cndym.dao.ISystemMessageDao;
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
 * User: 梅传颂
 * Date: 11-6-19
 * Time: 下午6:49
 */

@Repository
public class SystemMessageDaoImpl extends GenericDaoImpl<SystemMessage> implements ISystemMessageDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    public PageBean getSystemMessageList(SystemMessage systemMessage,String startTime,String endTime,Integer page, Integer pageSize) {
        StringBuffer sql = new StringBuffer("From SystemMessage WHERE 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if(Utils.isNotEmpty(systemMessage)){
            
        }
        if(Utils.isNotEmpty(startTime)){
            sql.append(" and createTime >= ?");
            hibernateParas.add(new HibernatePara(Utils.formatDate(startTime)));
        }
        if(Utils.isNotEmpty(endTime)){
            sql.append(" and createTime <= ?");
            hibernateParas.add(new HibernatePara(Utils.formatDate(endTime)));
        }
        sql.append(" order by createTime desc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
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
}
