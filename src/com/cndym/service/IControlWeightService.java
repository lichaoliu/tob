/**
 * 
 */
package com.cndym.service;

import java.util.List;
import java.util.Map;

import com.cndym.bean.tms.ControlWeight;
import com.cndym.bean.tms.PostLottery;
import com.cndym.bean.tms.WeightPlay;
import com.cndym.bean.tms.WeightRule;
import com.cndym.bean.tms.WeightSid;
import com.cndym.bean.tms.WeightTime;

/**
 * @author 朱林虎
 *
 */
public interface IControlWeightService {
	
	/**
	 * 获取所有的出票口信息
	 * @return
	 */
	public List<ControlWeight> getAllControlWeight();
	
	/**
	 * 根据weightId获取玩法配置
	 * @param weightId
	 * @return
	 */
	public List<WeightPlay> getWeightPlayList(Integer weightId);
	
	/**
	 * 根据weightId获取已被选中的玩法
	 * @param weightId
	 * @return
	 */
	public String getSelectedWeightPlays(Integer weightId);
	
	/**
	 * 根据ID获取玩法
	 * @param playId
	 * @return
	 */
	public WeightPlay getWeightPlayById(Integer playId);
	
	/**
	 * 根据ID删除玩法
	 * @param id
	 */
	public void deleteWeightPlayById(Integer id);
	
	/**
	 * 根据玩法获取接入商出票口
	 * @param playId
	 * @return
	 */
	public List<WeightSid> getWeightSidListByPlayId(Integer playId);
	
	/**
	 * 根据彩种获取可用出票口
	 * @param lotteryCode
	 * @return
	 */
	public List<PostLottery> getPostLotteryByCode(String lotteryCode);
	
	/**
	 * 根据接入商编码,玩法获取接入商
	 * @param sid
	 * @return
	 */
	public WeightSid getWeightSidBySid(String sid,Integer playId);
	
	/**
	 * 保存接入商出票口
	 * @param weightSid
	 */
	public void saveWeightSid(WeightSid weightSid);
	
	/**
	 * 更新接入商出票口
	 * @param weightSid
	 */
	public void modifyWeightSid(WeightSid weightSid);
	
	/**
	 * 删除接入商出票口
	 * @param weightSid
	 */
	public void deleteWeightSid(WeightSid weightSid);
	
	/**
	 * 修改出票口
	 * @param postMap
	 */
	public void modifyControlWeight(Map<String, String> postMap);
	
	/**
	 * 保存玩法
	 * @param weightPlay
	 */
	public void saveWeightPlay(WeightPlay weightPlay);
	
	/**
	 * 修改玩法
	 * @param weightPlay
	 */
	public void updateWeightPlay(WeightPlay weightPlay);
	
	/**
	 * 获取时间配置
	 * @param playId
	 * @return
	 */
	public List<WeightTime> getWeightTimeList(Integer playId);
	
	/**
	 * 通过ID 获取时间配置
	 * @param timeId
	 * @return
	 */
	public WeightTime getWeightTimeById(Integer timeId);
	
	/**
	 * 通过id删除时间配置
	 * @param id
	 */
	public void deleteWeightTimeById(Integer id);
	
	/**
	 * 通过时间ID删除规则
	 * @param timeId
	 */
	public void deleteWeightRulesByTimeId(Integer timeId);
	
	/**
	 * 保存时间配置
	 * @param weightTime
	 * @return
	 */
	public WeightTime saveWeightTime(WeightTime weightTime);
	
	/**
	 * 更新时间配置
	 * @param weightTime
	 */
	public void updateWeightTime(WeightTime weightTime);
	
	/**
	 * 保存规则配置
	 * @param weightRuleList
	 */
	public void saveWeightRuleList(List<WeightRule> weightRuleList);
	
	/**
	 * 生成出票口xml
	 * @return
	 */
	public String generateControlWeightXml();
	
	/**
	 * 加载数据库出票口配置到缓存
	 */
	public void reloadControlWeightToCache();
}
