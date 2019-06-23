/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.WeightSid;

/**
 * @author 朱林虎
 *
 */
public interface IWeightSidDao extends IGenericDao<WeightSid> {
	
	public List<WeightSid> getWeightSidList(Integer playId);
	
	public WeightSid getWeightSidBySid(String sid,Integer playId);
}
