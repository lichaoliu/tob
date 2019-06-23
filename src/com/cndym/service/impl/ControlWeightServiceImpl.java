/**
 * 
 */
package com.cndym.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.tms.ControlWeight;
import com.cndym.bean.tms.PostConfig;
import com.cndym.bean.tms.PostLottery;
import com.cndym.bean.tms.WeightPlay;
import com.cndym.bean.tms.WeightRule;
import com.cndym.bean.tms.WeightSid;
import com.cndym.bean.tms.WeightTime;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.control.bean.RuleForAmount;
import com.cndym.control.bean.RuleForProportion;
import com.cndym.control.bean.RuleKey;
import com.cndym.control.bean.SidKey;
import com.cndym.control.bean.Weight;
import com.cndym.dao.IControlWeightDao;
import com.cndym.dao.IPostConfigDao;
import com.cndym.dao.IPostLotteryDao;
import com.cndym.dao.IWeightPlayDao;
import com.cndym.dao.IWeightRuleDao;
import com.cndym.dao.IWeightSidDao;
import com.cndym.dao.IWeightTimeDao;
import com.cndym.service.IControlWeightService;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.Xml;

/**
 * @author 朱林虎
 *
 */
@Service
public class ControlWeightServiceImpl implements IControlWeightService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
    private IControlWeightDao controlWeightDao;
	
	@Autowired
	private IPostLotteryDao postLotteryDao;
	
	@Autowired
	private IWeightPlayDao weightPlayDao;
	
	@Autowired
	private IPostConfigDao postConfigDao;
	
	@Autowired
	private IWeightSidDao weightSidDao;
	
	@Autowired
	private IWeightTimeDao weightTimeDao;
	
	@Autowired
	private IWeightRuleDao weightRuleDao;
	
	@Override
	public List<ControlWeight> getAllControlWeight() {
		List<ControlWeight> controlWeightList = controlWeightDao.getAllControlWeight();
		for(ControlWeight controlWeight : controlWeightList){
			String lotteryCode = controlWeight.getLotteryCode();
			List<PostLottery> postLotteryList = postLotteryDao.getPostLotteryByCode(lotteryCode);
			controlWeight.setPostLotteryList(postLotteryList);
			Integer weightId = controlWeight.getId();
			List<WeightPlay> weightPlayList = weightPlayDao.getWeightPlayList(weightId);
			controlWeight.setWeightPlayList(weightPlayList);
		}
		return controlWeightList;
	}
	
	

	@Override
	public List<WeightPlay> getWeightPlayList(Integer weightId) {
		return weightPlayDao.getWeightPlayList(weightId);
	}


	@Override
	public List<PostLottery> getPostLotteryByCode(String lotteryCode) {
		return postLotteryDao.getPostLotteryByCode(lotteryCode);
	}
	

	@Override
	public List<WeightSid> getWeightSidListByPlayId(Integer playId) {
		return weightSidDao.getWeightSidList(playId);
	}
	
	@Override
	public WeightSid getWeightSidBySid(String sid,Integer playId) {
		return weightSidDao.getWeightSidBySid(sid,playId);
	}
	
	@Override
	public List<WeightTime> getWeightTimeList(Integer playId) {
		return weightTimeDao.getWeightTimeList(playId);
	}

	@Override
	public void saveWeightSid(WeightSid weightSid) {
		weightSidDao.save(weightSid);
	}

	@Override
	public void modifyWeightSid(WeightSid weightSid) {
		weightSidDao.update(weightSid);
	}
	
	@Override
	public void deleteWeightSid(WeightSid weightSid) {
		weightSidDao.delete(weightSid);
	}

	@Override
	public void saveWeightPlay(WeightPlay weightPlay) {
		weightPlayDao.save(weightPlay);
	}


	@Override
	public void updateWeightPlay(WeightPlay weightPlay) {
		weightPlayDao.update(weightPlay);
	}

	@Override
	public WeightPlay getWeightPlayById(Integer playId) {
		return weightPlayDao.getWeightPlayById(playId);
	}
	
	@Override
	public void deleteWeightPlayById(Integer id) {
		weightPlayDao.deleteById(id);
	}

	@Override
	public WeightTime getWeightTimeById(Integer timeId) {
		return weightTimeDao.getWeightTimeById(timeId);
	}

	@Override
	public WeightTime saveWeightTime(WeightTime weightTime) {
		weightTimeDao.save(weightTime);
		return weightTime;
	}
	
	@Override
	public void updateWeightTime(WeightTime weightTime) {
		weightTimeDao.update(weightTime);
	}

	@Override
	public void saveWeightRuleList(List<WeightRule> weightRuleList) {
		weightRuleDao.saveAllObject(weightRuleList);
	}

	@Override
	public void deleteWeightRulesByTimeId(Integer timeId) {
		weightRuleDao.deleteWeightRulesByTimeId(timeId);
	}
	
	@Override
	public void deleteWeightTimeById(Integer id) {
		WeightTime weightTime = weightTimeDao.findById(WeightTime.class, id);
		weightRuleDao.deleteWeightRulesByTimeId(id);
		weightTimeDao.delete(weightTime);
	}

	@Override
	public String getSelectedWeightPlays(Integer weightId) {
		StringBuffer plays = new StringBuffer();
		List<WeightPlay> weightPlayList = weightPlayDao.getWeightPlayList(weightId);
		for(WeightPlay weightPlay : weightPlayList){
			if(plays.length() == 0){
				plays.append(weightPlay.getPlayCodes());
			}else {
				plays.append(",").append(weightPlay.getPlayCodes());
			}
		}
		return plays.toString();
	}

	@Override
	public void modifyControlWeight(Map<String, String> postMap) {
		for(String lotteryCode : postMap.keySet()){
			ControlWeight controlWeight = controlWeightDao.getControlWeightByLotteryCode(lotteryCode);
			controlWeight.setLastPostCode(controlWeight.getPostCode());
			controlWeight.setPostCode(postMap.get(lotteryCode));
			controlWeight.setUpdateTime(new Date());
			controlWeightDao.update(controlWeight);
		}
	}

	@Override
	public String generateControlWeightXml() {
		List<PostConfig> postConfigList = postConfigDao.getAllPostConfigList();
		List<ControlWeight> controlWeightList = getAllControlWeight();
		
		Xml root = Xml.build("root");
		
		Xml postList = Xml.build("postList");
		for(PostConfig postConfig : postConfigList){
			Xml post = Xml.build("post");
			post.put("name", postConfig.getPostName());
			post.put("postClass",postConfig.getPostClass());
			post.put("code",postConfig.getPostCode());
			postList.add(post);
		}
		root.add(postList);
		
		Xml weights = Xml.build("weights");
		for(ControlWeight controlWeight : controlWeightList){
			Xml weight = Xml.build("weight");
			weight.put("name",controlWeight.getLotteryName());
			weight.put("defaultPostCode",controlWeight.getPostCode());
			weight.put("lotteryCode",controlWeight.getLotteryCode());
			Integer active = controlWeight.getActive();
			if(active == 1){
				weight.put("active","true");
			}else {
				weight.put("active","false");
			}
			
			List<WeightPlay> weightPlayList = controlWeight.getWeightPlayList();
			for(WeightPlay weightPlay : weightPlayList){
				Xml play = buildWeightPlayXml(weightPlay);
				if(play != null){
					weight.add(play);
				}
			}
			weights.add(weight);
		}
		
		root.add(weights);
		
		return root.toXml();
	}
	
	private Xml buildWeightPlayXml(WeightPlay weightPlay){
		
		List<WeightSid> weightSidList = weightPlay.getWeightSidList();
		List<WeightTime> weightTimeList = weightPlay.getWeightTimeList();
		if(Utils.isEmpty(weightSidList) && Utils.isEmpty(weightTimeList)){
			return null;
		}
		
		Xml play = Xml.build("play");
		play.put("code",weightPlay.getPlayCodes());
		if(Utils.isNotEmpty(weightPlay.getPollCodes())){
			play.put("poll", weightPlay.getPollCodes());
		}
		
		for(WeightSid weightSid : weightSidList){
			Xml sid = Xml.build("sid");
			sid.put("value",weightSid.getSid());
			sid.setTextNode(weightSid.getPostCode());
			play.add(sid);
		}
		
		for(WeightTime weightTime : weightTimeList){
			
			List<WeightRule> weightRuleList = weightTime.getWeightRuleList();
			
			Xml time = Xml.build("time");
			time.put("startTime",weightTime.getStartTime());
			time.put("week",weightTime.getWeeks());
			String ruleType = weightTime.getType();
			Xml rule = Xml.build("rule");
			rule.put("type",ruleType);
			
			if(Constants.CONTROL_WEIGHT_CONSTANT.equals(ruleType)){
				rule.setTextNode(weightRuleList.get(0).getPostCode());
			}
			
			if(Constants.CONTROL_WEIGHT_AMOUNT.equals(ruleType)){
				for(WeightRule weightRule : weightRuleList){
					Xml amount = Xml.build("item");
					amount.put("operate", weightRule.getParam());
					amount.put("amount",weightRule.getAmount());
					amount.setTextNode(weightRule.getPostCode());
					rule.add(amount);
				}
			}
			
			if(Constants.CONTROL_WEIGHT_PROPORTION.equals(ruleType)){
				for(WeightRule weightRule : weightRuleList){
					Xml amount = Xml.build("item");
					amount.put("proportion", weightRule.getParam());
					amount.setTextNode(weightRule.getPostCode());
					rule.add(amount);
				}
			}
			time.add(rule);
			play.add(time);
		}
		
		return play;
	}
	
	@Override
	public void reloadControlWeightToCache() {
		
		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
		
		Map<RuleKey, List> ruleMap = new HashMap<RuleKey, List>();
		Map<SidKey, String> sidMap = new HashMap<SidKey, String>();
		Map<String, Weight> weightMap = new HashMap<String, Weight>();
		
		String WEIGHT_RULE_MAP_VALUE = "weight_rule_map_value";
		String WEIGHT_MAP_VALUE = "weight_map_value";
		String WEIGHT_SID_MAP_VALUE = "weight_sid_map_value";
		
		List<ControlWeight> controlWeightList = getAllControlWeight();
		for(ControlWeight controlWeight : controlWeightList){
			String lotteryCode = controlWeight.getLotteryCode();
			Weight weight = new Weight();
			weight.setActive(controlWeight.getActive() == 1);
			weight.setLotteryCode(lotteryCode);
			weight.setName(controlWeight.getLotteryName());
			weight.setDefaultPostCode(controlWeight.getPostCode());
			
			List<com.cndym.control.bean.WeightPlay> weightPlays = new ArrayList<com.cndym.control.bean.WeightPlay>();
			List<WeightPlay> weightPlayList = controlWeight.getWeightPlayList();
			for(WeightPlay weightPlay : weightPlayList){
				String playCodeStr = weightPlay.getPlayCodes();
				String pollCodeStr = weightPlay.getPollCodes();
				
				List<WeightSid> weightSidList = weightPlay.getWeightSidList();
				List<WeightTime> weightTimeList = weightPlay.getWeightTimeList();
				
				String[] playCodeArr = joinPlayPollCode(playCodeStr, pollCodeStr);
				for (String playCode : playCodeArr) {
					com.cndym.control.bean.WeightPlay play = new com.cndym.control.bean.WeightPlay();
					play.setPlayCode(playCode);
					
					List<com.cndym.control.bean.WeightTime> weightTimes = new ArrayList<com.cndym.control.bean.WeightTime>();
					
					//构建一个玩法下的不同下游的出票口
					for(WeightSid weightSid : weightSidList){
						SidKey sidKey = new SidKey();
						sidKey.setLotteryCode(lotteryCode);
						sidKey.setPlayCode(playCode);
						sidKey.setSid(weightSid.getSid());
						sidMap.put(sidKey, weightSid.getPostCode());
					}
					
					//构建一个玩法下的不同时间，不同规则的出票口
					for(WeightTime weightTime : weightTimeList){
						String weekStr = weightTime.getWeeks();
						String ruleType =  weightTime.getType();
						String timeStr = weightTime.getStartTime();
						String[] weekArr = weekStr.split(",");
						for(String week : weekArr){
							
							RuleKey ruleKey = new RuleKey();
							ruleKey.setLotteryCode(lotteryCode);
							ruleKey.setPlayCode(playCode);
							ruleKey.setWeek(week);
							ruleKey.setTimeStr(timeStr);
							ruleKey.setType(ruleType);
							
							com.cndym.control.bean.WeightTime time = new com.cndym.control.bean.WeightTime();
							time.setWeek(week);
							time.setStartTime(timeStr);
							time.setRuleType(ruleType.toString());
							weightTimes.add(time);
							
							//根据不同的规则配置出票口
							List<WeightRule> weightRuleList = weightTime.getWeightRuleList();
							if (Constants.CONTROL_WEIGHT_CONSTANT.equals(ruleType)) {
								List<String> postCodeList = new ArrayList<String>();
								postCodeList.add(weightRuleList.get(0).getPostCode());
								ruleMap.put(ruleKey, postCodeList);
							}
							
							if (Constants.CONTROL_WEIGHT_AMOUNT.equals(ruleType)) {
								List<RuleForAmount> ruleForAmounts = new ArrayList<RuleForAmount>();
								for (WeightRule weightRule : weightRuleList) {
									RuleForAmount ruleForAmount = new RuleForAmount();
									ruleForAmount.setOperate(weightRule.getParam());
									ruleForAmount.setAmount(weightRule.getAmount());
									ruleForAmount.setPostCode(weightRule.getPostCode());
									ruleForAmounts.add(ruleForAmount);
								}
								ruleMap.put(ruleKey, ruleForAmounts);
							}
							if (Constants.CONTROL_WEIGHT_PROPORTION.equals(ruleType)) {
								List<RuleForProportion> ruleForProportions = new ArrayList<RuleForProportion>();
								for (WeightRule weightRule : weightRuleList) {
									RuleForProportion ruleForProportion = new RuleForProportion();
									ruleForProportion.setProportion(Integer.parseInt(weightRule.getParam()));
									ruleForProportion.setPostCode(weightRule.getPostCode());
									ruleForProportions.add(ruleForProportion);
								}
								ruleMap.put(ruleKey, ruleForProportions);
							}
							
						}
						play.setWeightTimes(weightTimes);
					}
					weightPlays.add(play);
				}
			}
			weight.setWeightPlays(weightPlays);
			weightMap.put(weight.getLotteryCode(), weight);
		}
		
		logger.info("before modify cache weight_sid_map_value:" + memcachedClientWrapper.get(WEIGHT_SID_MAP_VALUE));
		logger.info("before modify cache weight_rule_map_value:" + memcachedClientWrapper.get(WEIGHT_RULE_MAP_VALUE));
		logger.info("before modify cache weight_map_value:" + memcachedClientWrapper.get(WEIGHT_MAP_VALUE));
		
		String sidMapJsonStr = JsonBinder.buildNonDefaultBinder().toJson(sidMap);
		memcachedClientWrapper.set(WEIGHT_SID_MAP_VALUE, 0, sidMapJsonStr);
		String ruleMapJsonStr = JsonBinder.buildNonDefaultBinder().toJson(ruleMap);
		memcachedClientWrapper.set(WEIGHT_RULE_MAP_VALUE, 0, ruleMapJsonStr);
		String weightMapJsonStr = JsonBinder.buildNonDefaultBinder().toJson(weightMap);
		memcachedClientWrapper.set(WEIGHT_MAP_VALUE, 0, weightMapJsonStr);
		
		logger.info("after modify cache weight_sid_map_value:" + memcachedClientWrapper.get(WEIGHT_SID_MAP_VALUE));
		logger.info("after modify cache weight_rule_map_value:" + memcachedClientWrapper.get(WEIGHT_RULE_MAP_VALUE));
		logger.info("after modify cache weight_map_value:" + memcachedClientWrapper.get(WEIGHT_MAP_VALUE));
	}
	
	private String[] joinPlayPollCode(String playCodeStr, String pollCodeStr) {
		String[] playCodeArr = playCodeStr.split(",");
		if (Utils.isNotEmpty(pollCodeStr)) {
			int index = 0;
			String[] pollCodeArr = pollCodeStr.split(",");
			String[] joinCode = new String[playCodeArr.length * pollCodeArr.length];
			for (String playCode : playCodeArr) {
				for (String pollCode : pollCodeArr) {
					joinCode[index++] = playCode + pollCode;
				}
			}
			return joinCode;
		}
		return playCodeArr;
	}


	public static void main(String [] args){
		Xml root = Xml.build("body");
	    Xml lotteryRequest = Xml.build("lotteryRequest");
	    Xml ticket = Xml.build("ticket");
	    ticket.put("username", "tset");
	    Xml issue = Xml.build("issue");
	    issue.setTextNode("20140926001");
	    Xml userProfile = Xml.build("userProfile");
	    Xml anteCode = Xml.build("anteCode");
	    root.add(lotteryRequest);
	    lotteryRequest.add(ticket);
	    ticket.add(issue);
	    ticket.add(userProfile);
	    ticket.add(anteCode);
	    System.out.println(root.toXml());
	}
	
}
