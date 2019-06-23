/**
 * 
 */
package com.cndym.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.bean.tms.WeightRule;
import com.cndym.bean.tms.WeightTime;
import com.cndym.dao.IWeightRuleDao;
import com.cndym.dao.IWeightTimeDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;

/**
 * @author 朱林虎
 *
 */
@Repository
public class WeightTimeDaoImpl extends GenericDaoImpl<WeightTime> implements
		IWeightTimeDao {

	@Resource
	private SessionFactory sessionFactoryTemp;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IWeightRuleDao weightRuleDao;
	
	@PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }
	
	@Override
	public List<WeightTime> getWeightTimeList(Integer playId) {
		String hql = "FROM WeightTime t WHERE playId = ?";
		List<WeightTime> weightTimeList = find(hql, new Object[]{playId});
		for(WeightTime weightTime : weightTimeList){
			Integer timeId = weightTime.getId();
			List<WeightRule> weightRuleList = weightRuleDao.getWeightRuleList(timeId);
			weightTime.setWeightRuleList(weightRuleList);
		}
		return weightTimeList;
	}

	@Override
	public WeightTime getWeightTimeById(Integer timeId) {
		String hql = "FROM WeightTime t WHERE id = ?";
		List<WeightTime> weightTimeList = find(hql, new Object[]{timeId});
		if(Utils.isNotEmpty(weightTimeList)){
			WeightTime weightTime = weightTimeList.get(0);
			Integer id = weightTime.getId();
			List<WeightRule> weightRuleList = weightRuleDao.getWeightRuleList(timeId);
			weightTime.setWeightRuleList(weightRuleList);
			return weightTime;
		}
		return null;
	}
	
}
