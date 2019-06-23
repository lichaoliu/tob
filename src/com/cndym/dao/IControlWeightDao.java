/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.ControlWeight;

/**
 * @author 朱林虎
 *
 */
public interface IControlWeightDao extends IGenericDao<ControlWeight> {
	
	public List<ControlWeight> getAllControlWeight();
	
	public ControlWeight getControlWeightByLotteryCode(String lotteryCode);
}
