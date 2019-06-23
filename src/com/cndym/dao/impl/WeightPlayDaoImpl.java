/**
 * 
 */
package com.cndym.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.bean.tms.WeightPlay;
import com.cndym.bean.tms.WeightSid;
import com.cndym.bean.tms.WeightTime;
import com.cndym.dao.IWeightPlayDao;
import com.cndym.dao.IWeightRuleDao;
import com.cndym.dao.IWeightSidDao;
import com.cndym.dao.IWeightTimeDao;
import com.cndym.utils.Utils;

/**
 * @author 朱林虎
 *
 */
@Repository
public class WeightPlayDaoImpl extends GenericDaoImpl<WeightPlay> implements
		IWeightPlayDao {

	@Resource
	private SessionFactory sessionFactoryTemp;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private IWeightSidDao weightSidDao;
	
	@Autowired
	private IWeightTimeDao weightTimeDao;
	
	@Autowired
	private IWeightRuleDao weightRuleDao;
	
	@PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

	@Override
	public List<WeightPlay> getWeightPlayList(Integer weightId) {
		String hql = "FROM WeightPlay t WHERE t.weightId = ?";
		List<WeightPlay> weightPlayList = find(hql, new Object[]{weightId});
		for(WeightPlay weightPlay : weightPlayList){
			Integer playId = weightPlay.getId();
			List<WeightSid> weightSidList = weightSidDao.getWeightSidList(playId);
			List<WeightTime> weightTimeList = weightTimeDao.getWeightTimeList(playId);
			weightPlay.setWeightSidList(weightSidList);
			weightPlay.setWeightTimeList(weightTimeList);
		}
		return weightPlayList;
	}
	

	@Override
	public WeightPlay getWeightPlayById(Integer playId) {
		String hql = "FROM WeightPlay t WHERE t.id = ?";
		List<WeightPlay> weightPlayList = find(hql, new Object[]{playId});
		if(Utils.isNotEmpty(weightPlayList)){
			WeightPlay weightPlay = weightPlayList.get(0);
			List<WeightSid> weightSidList = weightSidDao.getWeightSidList(playId);
			List<WeightTime> weightTimeList = weightTimeDao.getWeightTimeList(playId);
			weightPlay.setWeightSidList(weightSidList);
			weightPlay.setWeightTimeList(weightTimeList);
			return weightPlay;
		}
		return null;
	}
	

	@Override
	public void deleteById(Integer playId) {
		
		List<WeightSid> weightSidList = weightSidDao.getWeightSidList(playId);
		List<WeightTime> weightTimeList = weightTimeDao.getWeightTimeList(playId);
		
		for(WeightSid weightSid : weightSidList){
			weightSidDao.delete(weightSid);
		}
		
		for(WeightTime weightTime : weightTimeList){
			weightRuleDao.deleteWeightRulesByTimeId(weightTime.getId());
			weightTimeDao.delete(weightTime);
		}
		
		WeightPlay weightPlay = findById(WeightPlay.class, playId);
		delete(weightPlay);
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
