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

import com.cndym.bean.tms.PostLottery;
import com.cndym.dao.IPostLotteryDao;

/**
 * @author 朱林虎
 *
 */
@Repository
public class PostLotteryDaoImpl extends GenericDaoImpl<PostLottery> implements IPostLotteryDao {
	
	@Resource
	private SessionFactory sessionFactoryTemp;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

	@Override
	public List<PostLottery> getPostLotteryByCode(String lotteryCode) {
		String hql = "FROM PostLottery t WHERE t.lotteryCode = ? and t.active = ? order by t.postCode";
		return find(hql, new Object[]{lotteryCode,1});
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
