/**
 * 
 */
package com.cndym.dao;

import java.util.List;

import com.cndym.bean.tms.PostLottery;

/**
 * @author 朱林虎
 *
 */
public interface IPostLotteryDao extends IGenericDao<PostLottery>{
	
	public List<PostLottery> getPostLotteryByCode(String lotteryCode);
}
