/**
 * 
 */
package com.cndym.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.bean.tms.ControlWeight;
import com.cndym.dao.IControlWeightDao;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

/**
 * @author 朱林虎
 *
 */
@Repository
public class ControlWeightDaoImpl extends GenericDaoImpl<ControlWeight>	implements IControlWeightDao {

	@Resource
	private SessionFactory sessionFactoryTemp;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

	@Override
	public List<ControlWeight> getAllControlWeight() {
		String hql = " FROM ControlWeight t WHERE t.active = ? order by t.lotteryCode";
		return find(hql, new Object[]{1});
	}
	

	@Override
	public ControlWeight getControlWeightByLotteryCode(String lotteryCode) {
		String hql = " FROM ControlWeight t WHERE t.lotteryCode = ?";
		List<ControlWeight> controlWeightList = find(hql, new Object[]{lotteryCode});
		return controlWeightList.get(0);
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
