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

import com.cndym.bean.tms.WeightSid;
import com.cndym.dao.IWeightSidDao;

/**
 * @author 朱林虎
 *
 */
@Repository
public class WeightSidDaoImpl extends GenericDaoImpl<WeightSid> implements
		IWeightSidDao {

	@Resource
	private SessionFactory sessionFactoryTemp;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }
	
	@Override
	public List<WeightSid> getWeightSidList(Integer playId) {
		String hql = "FROM WeightSid t WHERE playId = ?";
		return find(hql, new Object[]{playId});
	}

	@Override
	public WeightSid getWeightSidBySid(String sid,Integer playId) {
		String hql = "FROM WeightSid t WHERE sid = ? and playId=?";
		List<WeightSid> weightSidList = find(hql, new Object[]{sid,playId});
		if(weightSidList != null && weightSidList.size() > 0){
			return weightSidList.get(0);
		}
		return null;
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
