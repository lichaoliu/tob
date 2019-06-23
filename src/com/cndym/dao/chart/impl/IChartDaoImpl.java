package com.cndym.dao.chart.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cndym.dao.chart.IChartDao;
import com.cndym.service.chart.impl.IChartServiceImpl;
import com.cndym.utils.SpringUtils;
import com.google.gson.Gson;
/**
 * 作者：刘力超 
 * 时间：2014-6-7 下午4:02:30 
 * QQ：1147149597
 */
@Repository
public class IChartDaoImpl implements IChartDao{
	@Resource
    private JdbcTemplate jdbcTemplate;
	
	@Resource
    private SessionFactory sessionFactoryTemp;

	public List<Map<String, Object>> queryList(String sql) {
		jdbcTemplate=(JdbcTemplate)SpringUtils.getBean("jdbcTemplate");
		List<Map<String, Object>> list =jdbcTemplate.queryForList(sql);
		return list;
	}
	public List<Map<String, Object>> queryList(String sql,Object[] array) {
		jdbcTemplate=(JdbcTemplate)SpringUtils.getBean("jdbcTemplate");
		List<Map<String, Object>> list =jdbcTemplate.queryForList(sql,array);
		return list;
	}
	
}
