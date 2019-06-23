package com.cndym.admin.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.admin.dao.SendClientPostCodeDao;
import com.cndym.bean.tms.SendClientPostCode;
import com.cndym.dao.impl.GenericDaoImpl;
@Repository
public class SendClientPostCodeDaoImpl extends GenericDaoImpl<SendClientPostCode> implements SendClientPostCodeDao{
	@Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }
    
	@Override
	public List<SendClientPostCode> getSendClientPostCodeByPostCode(String sql,String postCode) {
		List <SendClientPostCode> list=find(sql, new Object[]{postCode});
		return list;
	}

}
