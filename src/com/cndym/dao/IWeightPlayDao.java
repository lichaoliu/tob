/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.WeightPlay;

/**
 * @author 朱林虎
 *
 */
public interface IWeightPlayDao extends IGenericDao<WeightPlay> {

	public List<WeightPlay> getWeightPlayList(Integer weightId);
	
	public WeightPlay getWeightPlayById(Integer playId);
	
	public void deleteById(Integer playId);
}
