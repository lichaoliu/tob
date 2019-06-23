/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.WeightRule;

/**
 * @author 朱林虎
 *
 */
public interface IWeightRuleDao extends IGenericDao<WeightRule> {
	
	public List<WeightRule> getWeightRuleList(Integer timeId);
	
	public void deleteWeightRulesByTimeId(Integer timeId);
}
