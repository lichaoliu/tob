/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.WeightTime;

/**
 * @author 朱林虎
 *
 */
public interface IWeightTimeDao extends IGenericDao<WeightTime> {
	
	public List<WeightTime> getWeightTimeList(Integer playId);
	public WeightTime getWeightTimeById(Integer timeId);
}
