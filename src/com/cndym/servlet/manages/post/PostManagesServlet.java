package com.cndym.servlet.manages.post;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.l;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.cndym.bean.sys.Manages;
import com.cndym.bean.tms.ControlWeight;
import com.cndym.bean.tms.PostLottery;
import com.cndym.bean.tms.WeightPlay;
import com.cndym.bean.tms.WeightRule;
import com.cndym.bean.tms.WeightSid;
import com.cndym.bean.tms.WeightTime;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.control.PostMap;
import com.cndym.control.WeightMap;
import com.cndym.control.bean.Weight;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.lottery.lotteryInfo.bean.LotteryPlay;
import com.cndym.service.IControlWeightService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.DateUtils;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;

public class PostManagesServlet extends BaseManagesServlet {

	private static final long serialVersionUID = -4446490338696217193L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static final String RELOAD_DB_WEIGHT = "reload_db_weight";
	private static final String RELEASE_CONTROL_WEIGHT = "release_control_weight";
	private static final String RELOAD_FILE_WEIGHT = "reload_file_weight";
	private static final String LOAD_WEIGHT_PLAY = "load_weight_play";
	private static final String PRE_ADD_WEIGHT_PLAY = "pre_add_weight_play";
	private static final String ADD_WEIGHT_PLAY = "add_weight_play";
	private static final String PRE_MODIFY_WEIGHT_PLAY = "pre_modify_weight_play";
	private static final String MODIFY_WEIGHT_PLAY = "modify_weight_play";
	private static final String DELETE_WEIGHT_PLAY = "delete_weight_play";
	private static final String PRE_MODIFY_WEIGHT_SID = "pre_modify_weight_sid";
	private static final String MODIFY_WEIGHT_SID = "modify_weight_sid";
	private static final String LOAD_WEIGHT_TIME = "load_weight_time";
	private static final String PRE_ADD_WEIGHT_TIME = "pre_add_weight_time";
	private static final String ADD_WEIGHT_TIME = "add_weight_time";
	private static final String PRE_MODIFY_WEIGHT_TIME = "pre_modify_weight_time";
	private static final String MODIFY_WEIGHT_TIME = "modify_weight_time";
	private static final String DELETE_WEIGHT_TIME = "delete_weight_time";
	private static final String RELOAD_CACHE_WEIGHT = "reload_cache_weight";
	private static final String VIEW_CONTROL_WEIGHT = "view_control_weight";
	
	private static final String WEB_INF = "WEB-INF";
	private static final String CLASSES = "classes";
	private static final String CONTROL_WEIGHT_FILENAME = "ControlWeight";
    
	private static XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper)SpringUtils.getBean("xmemcachedClientWrapper");
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        logger.info("action="+action);
        IControlWeightService controlWeightService = (IControlWeightService)SpringUtils.getBean("controlWeightServiceImpl");
        String adminName = null;
        Manages manages = (Manages) request.getSession().getAttribute("adminUser");
        if (Utils.isNotEmpty(manages)) {
            adminName = ((Manages) request.getSession().getAttribute("adminUser")).getUserName();
        }
        
        if(VIEW_CONTROL_WEIGHT.equals(action)) {
        	List<ControlWeight> controlWeightList = controlWeightService.getAllControlWeight();
        	if (Utils.isNotEmpty(controlWeightList)) {
        		request.setAttribute("controlWeightList", controlWeightList);
        	} else {
        		request.setAttribute("msg", "获取出票口数据失败");
        	}
        	request.getRequestDispatcher("/manages/system/viewDBControlWeight.jsp").forward(request, response);
        	return;
        } else if(RELOAD_DB_WEIGHT.equals(action)){
        	String myCheckbox[] = request.getParameterValues("myCheckbox");
        	if (Utils.isNotEmpty(myCheckbox)) {
        		Map<String, String> postMap = new HashMap<String, String>();
        		for (String lotteryCode : myCheckbox) {
        			String newPostCode = request.getParameter("postCode" + lotteryCode);
        			postMap.put(lotteryCode, newPostCode);
        		}
        		controlWeightService.modifyControlWeight(postMap);
        		setManagesLog(request, action, "<span style=\"color:#f00\">修改数据库出票口配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
        	}
        	request.getRequestDispatcher("/manages/postManagesServlet?action=view_control_weight").forward(request, response);
		} else if(RELEASE_CONTROL_WEIGHT.equals(action)){
			String xml = controlWeightService.generateControlWeightXml();
			//开始发布到xml
			FileOutputStream fileOutputStream = null;
	        try {
	        	XMLOutputter xmlOutputter = new XMLOutputter();
				xmlOutputter.setFormat(Format.getPrettyFormat().setEncoding("UTF-8")); 
				String contextPath = request.getSession().getServletContext().getRealPath("");
				String path = contextPath + File.separator + WEB_INF + File.separator + CLASSES + File.separator + "xml";
				File oldFile = new File(path + File.separator + CONTROL_WEIGHT_FILENAME + ".xml");
				File oldFileBak = new File(oldFile.getParent() + File.separator + CONTROL_WEIGHT_FILENAME  + ".xml_bak" + DateUtils.formatDate2Str(new Date(), "yyyyMMddHHmmss"));
				if(oldFile.renameTo(oldFileBak)){
					File file = new File(path + File.separator + CONTROL_WEIGHT_FILENAME + ".xml");
					fileOutputStream = new FileOutputStream(file);
					Reader in = new StringReader(xml);     
					Document doc = (new SAXBuilder()).build(in);
					xmlOutputter.output(doc, fileOutputStream);  
				}
			} catch (Exception e) {
				logger.error("release ControlWeight to XML error:",e);
			} finally{
				try {
					if(fileOutputStream != null){
						fileOutputStream.close();
					}
				} catch (IOException e1) {
					logger.error("release ControlWeight Close Stream error:",e1);
				}
			}
			
			//开始发布到缓存
			//controlWeightService.reloadControlWeightToCache();
			setManagesLog(request, action, "<span style=\"color:#f00\">发布数据库出票口到配置文件</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=view_control_weight").forward(request, response);
			
		} else if (RELOAD_FILE_WEIGHT.equals(action)) {
			PostMap.forInstance();
        	WeightMap.forInstance();
    		List<Weight> weightList = ElTagUtils.getWeightList();
        	if (Utils.isNotEmpty(weightList)) {
        		//request.setAttribute("weightList", weightList);
				setManagesLog(request, action, "<span style=\"color:#f00\">加载出票口配置文件</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
        	} else {
        		request.setAttribute("msg", "加载配置文件数据失败");
        	}
        	request.getRequestDispatcher("/manages/postManagesServlet?action=view_control_weight").forward(request, response);
        	
        }else if(LOAD_WEIGHT_PLAY.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			String lotteryCode = request.getParameter("lotteryCode");
			List<WeightPlay> weightPlayList = controlWeightService.getWeightPlayList(weightId);
			
			request.setAttribute("weightId", weightId);
			request.setAttribute("lotteryCode", lotteryCode);
			
			if (Utils.isNotEmpty(weightPlayList)) {
				String plays = controlWeightService.getSelectedWeightPlays(weightId);
				LotteryClass lotteryClass = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryClass(lotteryCode);
				List<LotteryPlay> lotteryPlayList = lotteryClass.getLotteryPlayList();
				request.setAttribute("lotteryPlayList", lotteryPlayList);
				request.setAttribute("weightPlayList", weightPlayList);
				request.setAttribute("plays", plays);
        	} else {
        		request.setAttribute("msg", "此出票口暂无玩法配置，请添加");
        	}
			request.getRequestDispatcher("/manages/system/controlWeightPlayList.jsp").forward(request, response);
		} else if(PRE_ADD_WEIGHT_PLAY.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			String lotteryCode = request.getParameter("lotteryCode");
			LotteryClass lotteryClass = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryClass(lotteryCode);
			List<LotteryPlay> lotteryPlayList = lotteryClass.getLotteryPlayList();
			if (Utils.isNotEmpty(lotteryPlayList)) {
				String plays = controlWeightService.getSelectedWeightPlays(weightId);
				request.setAttribute("weightId", weightId);
				request.setAttribute("lotteryCode", lotteryCode);
				request.setAttribute("lotteryPlayList", lotteryPlayList);
				request.setAttribute("plays", plays);
			}else{
				request.setAttribute("msg", "此彩种暂无玩法配置！");
			}
			
			request.getRequestDispatcher("/manages/system/addWeightPlay.jsp").forward(request, response);
		}else if(ADD_WEIGHT_PLAY.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			String lotteryCode = request.getParameter("lotteryCode");
			String playCodes = request.getParameter("playCodes");
			String pollCodes = request.getParameter("pollCodes");
			WeightPlay weightPlay = new WeightPlay();
			weightPlay.setWeightId(weightId);
			weightPlay.setLotteryCode(lotteryCode);
			weightPlay.setPlayCodes(playCodes);
			weightPlay.setPollCodes(pollCodes);
			weightPlay.setCreateTime(new Date());
			weightPlay.setUserCode(adminName);
			controlWeightService.saveWeightPlay(weightPlay);
			setManagesLog(request, action, "<span style=\"color:#f00\">添加出票口玩法配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=load_weight_play&weightId=" + weightId + "&lotteryCode=" + lotteryCode).forward(request, response);
		}else if(PRE_MODIFY_WEIGHT_PLAY.equals(action)){
			
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			WeightPlay weightPlay = controlWeightService.getWeightPlayById(playId);
			if(Utils.isNotEmpty(weightPlay)){
				Integer weightId = weightPlay.getWeightId();
				String lotteryCode = weightPlay.getLotteryCode();
				LotteryClass lotteryClass = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryClass(lotteryCode);
				List<LotteryPlay> lotteryPlayList = lotteryClass.getLotteryPlayList();
				String plays = controlWeightService.getSelectedWeightPlays(weightId);
				request.setAttribute("weightId", weightId);
				request.setAttribute("playId", playId);
				request.setAttribute("weightPlay", weightPlay);
				request.setAttribute("lotteryCode", lotteryCode);
				request.setAttribute("lotteryPlayList", lotteryPlayList);
				request.setAttribute("plays", plays);
			}
			request.getRequestDispatcher("/manages/system/modifyWeightPlay.jsp").forward(request, response);
		}else if(MODIFY_WEIGHT_PLAY.equals(action)){
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			String lotteryCode = request.getParameter("lotteryCode");
			String playCodes = request.getParameter("playCodes");
			String pollCodes = request.getParameter("pollCodes");
			WeightPlay weightPlay = controlWeightService.getWeightPlayById(playId);
			weightPlay.setPlayCodes(playCodes);
			weightPlay.setPollCodes(pollCodes);
			weightPlay.setUpdateTime(new Date());
			weightPlay.setUserCode(adminName);
			controlWeightService.updateWeightPlay(weightPlay);
			setManagesLog(request, action, "<span style=\"color:#f00\">修改出票口玩法配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=load_weight_play&weightId=" + weightId + "&lotteryCode=" + lotteryCode).forward(request, response);
		}else if(DELETE_WEIGHT_PLAY.equals(action)){
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			String lotteryCode = request.getParameter("lotteryCode");
			controlWeightService.deleteWeightPlayById(playId);
			setManagesLog(request, action, "<span style=\"color:#f00\">删除出票口玩法配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=load_weight_play&weightId=" + weightId + "&lotteryCode=" + lotteryCode).forward(request, response);
		}else if(PRE_MODIFY_WEIGHT_SID.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			List<WeightSid> weightSidList = controlWeightService.getWeightSidListByPlayId(playId);
			List<PostLottery> postLotteryList = controlWeightService.getPostLotteryByCode(lotteryCode);
			request.setAttribute("weightId", weightId);
			request.setAttribute("weightSidList", weightSidList);
			request.setAttribute("postLotteryList", postLotteryList);
			request.setAttribute("playId", playId);
			request.setAttribute("lotteryCode", lotteryCode);
			request.getRequestDispatcher("/manages/system/memberWeightSidConfig.jsp").forward(request, response);
		}else if(MODIFY_WEIGHT_SID.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			String sidCheckBox[] = request.getParameterValues("sidCheckBox");
			if (Utils.isNotEmpty(sidCheckBox)) {
        		for (String sid : sidCheckBox) {
        			String postCode = request.getParameter("postCode" + sid);
        			WeightSid weightSid = controlWeightService.getWeightSidBySid(sid,playId);
        			if(!"00".equals(postCode)){
        				if(weightSid != null){
        					weightSid.setPostCode(postCode);
        					weightSid.setUpdateTime(new Date());
        					weightSid.setUserCode(adminName);
        					controlWeightService.modifyWeightSid(weightSid);
        				}else {
        					WeightSid newWeightSid = new WeightSid();
        					newWeightSid.setSid(sid);
        					newWeightSid.setPostCode(postCode);
        					newWeightSid.setPlayId(playId);
        					newWeightSid.setCreateTime(new Date());
        					newWeightSid.setUserCode(adminName);
        					controlWeightService.saveWeightSid(newWeightSid);
						}
        			}else {
						if(weightSid != null){
							controlWeightService.deleteWeightSid(weightSid);
						}
					}
        		}
			}
			setManagesLog(request, action, "<span style=\"color:#f00\">修改出票口玩法接入商配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=pre_modify_weight_sid&weightId=" + weightId + "&playId=" + playId + "&lotteryCode=" + lotteryCode).forward(request, response);
		}else if(LOAD_WEIGHT_TIME.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			List<WeightTime> weightTimeList = controlWeightService.getWeightTimeList(playId);
			request.setAttribute("weightId", weightId);
			request.setAttribute("playId", playId);
			request.setAttribute("lotteryCode", lotteryCode);
			if(Utils.isNotEmpty(weightTimeList)){
				for(WeightTime weightTime : weightTimeList){
					String type = weightTime.getType();
					if("constant".equals(type)){
						weightTime.setType("常规");
					}
					if("amount".equals(type)){
						weightTime.setType("投注金额");
					}
					if("proportion".equals(type)){
						weightTime.setType("比例");
					}
					String weeks = weightTime.getWeeks();
					String[] weekArray = weeks.split(",");
					StringBuffer weekBuffer = new StringBuffer();
					for(String week : weekArray){
						if("1".equals(week)){
							weekBuffer.append("星期日     ");
						}
						if("2".equals(week)){
							weekBuffer.append("星期一     ");
						}
						if("3".equals(week)){
							weekBuffer.append("星期二     ");
						}
						if("4".equals(week)){
							weekBuffer.append("星期三     ");
						}
						if("5".equals(week)){
							weekBuffer.append("星期四     ");
						}
						if("6".equals(week)){
							weekBuffer.append("星期五     ");
						}
						if("7".equals(week)){
							weekBuffer.append("星期六     ");
						}
					}
					weightTime.setWeeks(weekBuffer.toString());
				}
				request.setAttribute("weightTimeList", weightTimeList);
			}else {
				request.setAttribute("msg", "暂无时间配置，请添加");
			}
			
			request.getRequestDispatcher("/manages/system/controlWeightTimeList.jsp").forward(request, response);
		}else if(PRE_ADD_WEIGHT_TIME.equals(action)){
		
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			
			List<PostLottery> postLotteryList = controlWeightService.getPostLotteryByCode(lotteryCode);
			
			request.setAttribute("weightId", weightId);
			request.setAttribute("playId", playId);
			request.setAttribute("lotteryCode", lotteryCode);
			request.setAttribute("postLotteryList", postLotteryList);
			
			request.getRequestDispatcher("/manages/system/addWeightTime.jsp").forward(request, response);
			
		}else if(ADD_WEIGHT_TIME.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			String[] week = request.getParameterValues("week");
			StringBuffer weeks = new StringBuffer();
			for(String w : week){
				weeks.append(w).append(",");
			}
			weeks.deleteCharAt(weeks.length() - 1);
			
			String startTime = request.getParameter("startTime");
			String type = request.getParameter("type");
			
			WeightTime weightTime = new WeightTime();
			weightTime.setPlayId(playId);
			weightTime.setStartTime(startTime);
			weightTime.setWeeks(weeks.toString());
			weightTime.setType(type);
			weightTime.setUserCode(adminName);
			weightTime.setCreateTime(new Date());
			
			WeightTime time = controlWeightService.saveWeightTime(weightTime);
			Integer timeId = time.getId();
			
			List<WeightRule> weightRuleList = new ArrayList<WeightRule>();
			
			if("constant".equals(type)){
				String postCode = request.getParameter("postCodeconstant");
				WeightRule weightRule = new WeightRule();
				weightRule.setTimeId(timeId);
				weightRule.setParam("constant");
				weightRule.setPostCode(postCode);
				weightRule.setUserCode(adminName);
				weightRule.setCreateTime(new Date());
				weightRuleList.add(weightRule);
				logger.info("constant postCode:" + postCode);
			}
			
			List<PostLottery> postLotteryList = controlWeightService.getPostLotteryByCode(lotteryCode);
			Integer postSize = postLotteryList.size();
			
			if("amount".equals(type)){
//				for(Integer i = 1; i <= postSize; i ++){
//					String operate = request.getParameter("operate" + i);
//					String amountStr = request.getParameter("amount" + i);
//					String postCode = request.getParameter("amountpostCode"  + i);
//					if(Utils.isNotEmpty(amountStr) && !"0".equals(postCode) && !"0".equals(operate)){
//						Double amount = Double.parseDouble(amountStr);
//						WeightRule weightRule = new WeightRule();
//						weightRule.setTimeId(timeId);
//						weightRule.setParam(operate);
//						weightRule.setAmount(amount);
//						weightRule.setPostCode(postCode);
//						weightRule.setUserCode(adminName);
//						weightRule.setCreateTime(new Date());
//						weightRuleList.add(weightRule);
//						logger.info("amount" + i + " operate:" + operate + " amount:" + amount + " postCode:" + postCode);
//					}
//				}
				
				String operate = request.getParameter("operate");
				String amountStr = request.getParameter("amount");
				String postCode = request.getParameter("amountpostCode");
				Double amount = Double.parseDouble(amountStr);
				WeightRule weightRule = new WeightRule();
				weightRule.setTimeId(timeId);
				weightRule.setParam(operate);
				weightRule.setAmount(amount);
				weightRule.setPostCode(postCode);
				weightRule.setUserCode(adminName);
				weightRule.setCreateTime(new Date());
				weightRuleList.add(weightRule);
				logger.info("amount operate:" + operate + " amount:" + amount + " postCode:" + postCode);
			}
			
			if("proportion".equals(type)){
				for(Integer i = 1; i <= postSize; i ++){
					String proportion = request.getParameter("proportion" + i);
					String postCode = request.getParameter("proportionpostCode"  + i);
					if(Utils.isNotEmpty(proportion) && !"0".equals(postCode)){
						WeightRule weightRule = new WeightRule();
						weightRule.setTimeId(timeId);
						weightRule.setParam(proportion);
						weightRule.setPostCode(postCode);
						weightRule.setUserCode(adminName);
						weightRule.setCreateTime(new Date());
						weightRuleList.add(weightRule);
						logger.info("operate" + i + " proportion:" + proportion + " postCode:" + postCode);
					}
				}
			}
			controlWeightService.saveWeightRuleList(weightRuleList);
			setManagesLog(request, action, "<span style=\"color:#f00\">添加出票口玩法时间配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=load_weight_time&weightId="+ weightId + "&playId="+playId+"&lotteryCode=" + lotteryCode).forward(request, response);
		}else if(PRE_MODIFY_WEIGHT_TIME.equals(action)){
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer timeId = Integer.parseInt(request.getParameter("timeId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			WeightTime weightTime = controlWeightService.getWeightTimeById(timeId);
			
			List<PostLottery> postLotteryList = controlWeightService.getPostLotteryByCode(lotteryCode);
			
			request.setAttribute("weightId", weightId);
			request.setAttribute("timeId", timeId);
			request.setAttribute("playId", playId);
			request.setAttribute("lotteryCode", lotteryCode);
			request.setAttribute("weightTime", weightTime);
			request.setAttribute("postLotteryList", postLotteryList);
			
			request.getRequestDispatcher("/manages/system/modifyWeightTime.jsp").forward(request, response);
		}else if(MODIFY_WEIGHT_TIME.equals(action)){
			Integer timeId = Integer.parseInt(request.getParameter("timeId"));
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			String[] week = request.getParameterValues("week");
			StringBuffer weeks = new StringBuffer();
			for(String w : week){
				weeks.append(w).append(",");
			}
			weeks.deleteCharAt(weeks.length() - 1);
			
			String startTime = request.getParameter("startTime");
			String type = request.getParameter("type");
			
			WeightTime weightTime = controlWeightService.getWeightTimeById(timeId);
			weightTime.setPlayId(playId);
			weightTime.setStartTime(startTime);
			weightTime.setWeeks(weeks.toString());
			weightTime.setType(type);
			weightTime.setUserCode(adminName);
			weightTime.setUpdateTime(new Date());
			
			List<WeightRule> weightRuleList = new ArrayList<WeightRule>();
			
			if("constant".equals(type)){
				String postCode = request.getParameter("postCodeconstant");
				WeightRule weightRule = new WeightRule();
				weightRule.setTimeId(timeId);
				weightRule.setParam("constant");
				weightRule.setPostCode(postCode);
				weightRule.setUserCode(adminName);
				weightRule.setCreateTime(new Date());
				weightRuleList.add(weightRule);
				logger.info("constant postCode:" + postCode);
			}
			
			List<PostLottery> postLotteryList = controlWeightService.getPostLotteryByCode(lotteryCode);
			Integer postSize = postLotteryList.size();
			
			if("amount".equals(type)){
//				for(Integer i = 1; i <= postSize; i ++){
//					String operate = request.getParameter("operate" + i);
//					String amountStr = request.getParameter("amount" + i);
//					String postCode = request.getParameter("amountpostCode"  + i);
//					if(Utils.isNotEmpty(amountStr) && !"0".equals(postCode) && !"0".equals(operate)){
//						Double amount = Double.parseDouble(amountStr);
//						WeightRule weightRule = new WeightRule();
//						weightRule.setTimeId(timeId);
//						weightRule.setParam(operate);
//						weightRule.setAmount(amount);
//						weightRule.setPostCode(postCode);
//						weightRule.setUserCode(adminName);
//						weightRule.setCreateTime(new Date());
//						weightRuleList.add(weightRule);
//						logger.info("amount" + i + " operate:" + operate + " amount:" + amount + " postCode:" + postCode);
//					}
//				}
				String operate = request.getParameter("operate");
				String amountStr = request.getParameter("amount");
				String postCode = request.getParameter("amountpostCode");
				Double amount = Double.parseDouble(amountStr);
				WeightRule weightRule = new WeightRule();
				weightRule.setTimeId(timeId);
				weightRule.setParam(operate);
				weightRule.setAmount(amount);
				weightRule.setPostCode(postCode);
				weightRule.setUserCode(adminName);
				weightRule.setCreateTime(new Date());
				weightRuleList.add(weightRule);
				logger.info("amount operate:" + operate + " amount:" + amount + " postCode:" + postCode);
			}
			
			if("proportion".equals(type)){
				for(Integer i = 1; i <= postSize; i ++){
					String proportion = request.getParameter("proportion" + i);
					String postCode = request.getParameter("proportionpostCode"  + i);
					if(Utils.isNotEmpty(proportion) && !"0".equals(postCode)){
						WeightRule weightRule = new WeightRule();
						weightRule.setTimeId(timeId);
						weightRule.setParam(proportion);
						weightRule.setPostCode(postCode);
						weightRule.setUserCode(adminName);
						weightRule.setCreateTime(new Date());
						weightRuleList.add(weightRule);
						logger.info("operate" + i + " proportion:" + proportion + " postCode:" + postCode);
					}
				}
			}
			controlWeightService.deleteWeightRulesByTimeId(timeId);
			controlWeightService.saveWeightRuleList(weightRuleList);
			controlWeightService.updateWeightTime(weightTime);
			setManagesLog(request, action, "<span style=\"color:#f00\">修改出票口时间配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=load_weight_time&weightId="+ weightId + "&playId="+playId+"&lotteryCode=" + lotteryCode).forward(request, response);
		}else if(DELETE_WEIGHT_TIME.equals(action)){
			Integer timeId = Integer.parseInt(request.getParameter("timeId"));
			Integer weightId = Integer.parseInt(request.getParameter("weightId"));
			Integer playId = Integer.parseInt(request.getParameter("playId"));
			String lotteryCode = request.getParameter("lotteryCode");
			controlWeightService.deleteWeightTimeById(timeId);
			setManagesLog(request, action, "<span style=\"color:#f00\">删除出票口玩法时间配置</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
			request.getRequestDispatcher("/manages/postManagesServlet?action=load_weight_time&weightId="+ weightId + "&playId="+playId+"&lotteryCode=" + lotteryCode).forward(request, response);
		}else  if (RELOAD_CACHE_WEIGHT.equals(action)){
        	String myCheckbox[] = request.getParameterValues("myCheckbox");
        	if (Utils.isNotEmpty(myCheckbox)) {
        		boolean flushCache = false;
        		Map<String, Weight> weightMap = ElTagUtils.getWeightMap();
        		Weight weight = null;
        		for (String lottery_code : myCheckbox) {
        			weight = weightMap.get(lottery_code);
        			String newPostCode = Utils.formatStr(request.getParameter("postCode" + lottery_code), weight.getDefaultPostCode());
        			if (Utils.isNotEmpty(weight) && !weight.getDefaultPostCode().equals(newPostCode)) {
        				setManagesLog(request, action, weight.getName()+"("+lottery_code+")从<span style=\"color:#f00\">" + PostMap.getPost(weight.getDefaultPostCode()).getName() + "(" + weight.getDefaultPostCode() +")</span>切换到" + "<span style=\"color:#f00\">" + PostMap.getPost(newPostCode).getName() + "(" + newPostCode +")</span>", Constants.MANAGER_LOG_SYS_MESSAGE);
        				weight.setDefaultPostCode(newPostCode);
        				flushCache = true;
        			}
        		}
        		if (flushCache) {
	        		String weightMapJsonStr = JsonBinder.buildNonDefaultBinder().toJson(weightMap);
	        		memcachedClientWrapper.set("weight_map_value", 0, weightMapJsonStr);
        		}
        		List<Weight> weightList = ElTagUtils.getWeightList();
            	if (Utils.isNotEmpty(weightList)) {
            		request.setAttribute("weightList", weightList);
            	} else {
            		request.setAttribute("msg", "取缓冲数据失败");
            	}
        	}
        	request.getRequestDispatcher("/manages/system/viewControlWeight.jsp").forward(request, response);
        	return;
        }
    }
}
