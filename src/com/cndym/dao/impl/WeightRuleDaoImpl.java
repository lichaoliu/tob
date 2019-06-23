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

import com.cndym.bean.tms.WeightRule;
import com.cndym.dao.IWeightRuleDao;

/**
 * @author 朱林虎
 *
 */
@Repository
public class WeightRuleDaoImpl extends GenericDaoImpl<WeightRule> implements
		IWeightRuleDao {

	@Resource
	private SessionFactory sessionFactoryTemp;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }
	
	@Override
	public List<WeightRule> getWeightRuleList(Integer timeId) {
		String hql = "FROM WeightRule t WHERE timeId = ?";
		return find(hql, new Object[]{timeId});
	}
	
	@Override
	public void deleteWeightRulesByTimeId(Integer timeId) {
		String hql = "FROM WeightRule t WHERE timeId = ?";
		List<WeightRule> weightRuleList = find(hql, new Object[]{timeId});
		for(WeightRule weightRule : weightRuleList){
			delete(weightRule);
		}
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
