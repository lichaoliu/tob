package com.cndym.dao.chart;


import java.util.List;
import java.util.Map;

import com.cndym.dao.IGenericDao;

/**
 * 作者：刘力超 
 * 时间：2014-6-7 下午4:02:47 
 * QQ：1147149597
 */
public interface IChartDao{
	List<Map<String, Object>> queryList(String sql);
	List<Map<String, Object>> queryList(String sql,Object[] objectArray);
}
