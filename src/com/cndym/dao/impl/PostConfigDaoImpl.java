/**
 * 
 */
package com.cndym.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.bean.tms.PostConfig;
import com.cndym.dao.IPostConfigDao;

/**
 * @author 朱林虎
 *
 */
@Repository
public class PostConfigDaoImpl extends GenericDaoImpl<PostConfig> implements
		IPostConfigDao {

	@Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }
    
	@Override
	public List<PostConfig> getAllPostConfigList() {
		String hql = " FROM PostConfig t where t.active = ? order by t.postCode";
		return find(hql, new Object[]{1});
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
