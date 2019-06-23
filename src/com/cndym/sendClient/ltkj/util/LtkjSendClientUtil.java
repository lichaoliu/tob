package com.cndym.sendClient.ltkj.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.xfire.client.Client;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.bean.tms.Ticket;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.sendClient.IChange;
import com.cndym.sendClient.ltkj.LtkjSendClientConfig;
import com.cndym.sendClient.ltkj.bean.TraceInfo;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

/**
 * 乐透科技投注客户端工具类
 * @author 朱林虎
 *
 */
public class LtkjSendClientUtil {
	
	private static Logger logger = Logger.getLogger(LtkjSendClientUtil.class);
	private final static int RETURN_SIZE = Integer.parseInt(LtkjSendClientConfig.getValue("RETURN_SIZE"));
	
	/**
	 * 构建信息串
	 * @param ticket
	 * @return
	 */
	public static String bulidSendOrder(Ticket ticket) {
		
		StringBuffer ticketInfo = new StringBuffer();
		String lotteryCode = ticket.getLotteryCode();
		String playCode = ticket.getPlayCode();
		String pollCode = ticket.getPollCode();
		Integer item = ticket.getItem();
		Integer multiple = ticket.getMultiple();
		Integer amount = Integer.parseInt(new java.text.DecimalFormat("0").format(ticket.getAmount())) * 100;
		String numberInfo = ticket.getNumberInfo();
		
		if ("200".equals(lotteryCode) || "201".equals(lotteryCode)) {//竞彩
			String[] numberArray = numberInfo.split("\\|");
			String passType = numberArray[1];
			
			String betType = "S";
			if(numberInfo.contains(",")){
				betType = "M";
			}
			if("1*1".equals(passType)){
				passType = "00";
			}
			//passType = "01";
			ticketInfo.append(ticket.getTicketId())
					  .append("~").append(ToSendCode.playToPost(lotteryCode, playCode))
					  .append("~").append(passType)
					 // .append("~").append(ToSendCode.pollToPost(pollCode))
					  .append("~").append(betType)
					  .append("~").append(item)
					  .append("~").append(multiple);
			
			IChange change = (IChange) SpringUtils.getBean("toLtkj" + lotteryCode + playCode + "00");
		    ticketInfo.append("~").append(change.toSendNumberCode(numberInfo))
		    		  .append("~").append(amount);
		}else{//数字彩
			String betType = ToSendCode.pollToPost(pollCode);
			if("103,111,112".contains(lotteryCode)){
				if(("09".equals(playCode) && "02".equals(pollCode)) || ("10".equals(playCode) && "02".equals(pollCode))){
					betType = "P";
				}
			}
			
			if("107".equals(lotteryCode)){
				if(("10".equals(playCode) && "02".equals(pollCode)) || ("11".equals(playCode) && "02".equals(pollCode))){
					betType = "P";
				}
				if("14,15,16,17".contains(playCode)) {
					betType = "L";
				}
			}
			
			String playType = "";
			if("108".equals(lotteryCode)){
				if("02".equals(playCode) && "01".equals(pollCode)){
					String arrNum[] = numberInfo.split(";")[0].split(",");
					if(!arrNum[1].equals(arrNum[0]) && !arrNum[1].equals(arrNum[2]) && !arrNum[0].equals(arrNum[2])){
						betType = "Z";
					}
				}
				
				//将组三和组六单式改成组选单式
				if("03".equals(playCode) && "01".equals(pollCode)){
					playCode = "02";
					betType = "S";
				}else if("04".equals(playCode) && "01".equals(pollCode)){
					playCode = "02";
					betType = "Z";
				}
				
				playType = ToSendCode.playToPost(lotteryCode, playCode + pollCode);
			}else if("002".equals(lotteryCode)){
				if("03".equals(playCode) && "01".equals(pollCode)){
					betType = "Z";
				}
				playType = ToSendCode.playToPost(lotteryCode, playCode + pollCode);
				logger.info("ltkj order send request:"+playType+"aaa");
			}else if("013,014".contains(lotteryCode)){
				if(("04".equals(playCode) && "02".equals(pollCode)) || ("05".equals(playCode) && "02".equals(pollCode) ) ){
					betType = "M";
				}else{
					betType = "S";
				}
				playType = ToSendCode.playToPost(lotteryCode, playCode + pollCode);
			}else  {
				playType = ToSendCode.playToPost(lotteryCode, playCode);
			}
			
			ticketInfo.append(ticket.getTicketId())
					  .append("~").append(ToSendCode.postToSys(lotteryCode))
					  .append("~").append(playType)
					  .append("~").append(betType)
					  .append("~").append(item)
					  .append("~").append(multiple);
			
			IChange change = (IChange) SpringUtils.getBean("toLtkj" + lotteryCode + playCode + pollCode);
		    ticketInfo.append("~").append(change.toSendNumberCode(numberInfo))
		    		  .append("~").append(amount);
		}
		
		return ticketInfo.toString();
	}
	
	/**
	 * 构建查询出票状态对象集
	 * @param tickets
	 * @return
	 */
	public static List<TraceInfo> buildQueryOrder(List<Ticket> tickets){
		List<TraceInfo> traceInfoList = new ArrayList<TraceInfo>();
		int i = 0;
		int j = 0;
		StringBuffer traceCodes = new StringBuffer();
		for(Ticket ticket : tickets){
			traceCodes.append(ticket.getTicketId()).append("|");
			i ++;
			j ++;
			if(i % RETURN_SIZE == 0 || i == tickets.size()){
				traceCodes.deleteCharAt(traceCodes.lastIndexOf("|"));
				traceInfoList.add(new TraceInfo(j, traceCodes.toString()));
				j = 0;
				traceCodes = new StringBuffer();
			}
		}
		return traceInfoList;
	}
	
	/**
	 * 赔率转换
	 * @param spValue
	 * @param ticket
	 * @return
	 */
	public static String covertOddsToNumber(String spValue,Ticket ticket){
		
		String numberInfo = ticket.getNumberInfo();
		String[] numbers = numberInfo.split("\\|");
//		String passType = numbers[1];
		String playCode = ticket.getPlayCode();
		String lotteryCode = ticket.getLotteryCode();
//		if ("1*1".equals(passType) && !"20004".equals(lotteryCode + playCode) && !"20103".equals(lotteryCode + playCode)) {
//			return covertOddsDanGuan(numberInfo, lotteryCode, playCode);
//		}
		
		String oddsLottery = ToSendCode.getOddsLottery(lotteryCode, playCode) + "|";
		StringBuffer buffer = new StringBuffer(oddsLottery);
		
		String[] numberArray = numbers[0].split(";");
		String[] oddsInfoArray = spValue.split("//");
		
		int index = 0;
		for(String number : numberArray){
			
			String[] info = number.split(":");
			String match = "";
			String bets = "";
			String playCode10 = "";
			
			//切割赛事和投注号码
			if ("10".equals(playCode)) {
				playCode10 = info[1];
				match = info[0].substring(2) + ":" + playCode10;
				bets = info[2];
			} else {
				match = info[0].substring(2);
				bets = info[1];
			}
			
			if (index > 0) {
				buffer.append(",");
			}
			
			buffer.append(match);
			String oddsInfo = getBetsByMatch(info[0],oddsInfoArray);
			if(oddsInfo == null){
				continue;
			}
			
			if(lotteryCode.equals("201") || (lotteryCode.equals("200") && ("01".contains(playCode) || "01".equals(playCode10) || "05".contains(playCode) || "05".equals(playCode10)))){//所有篮球玩法及足球胜平负和让球胜平负需要加让球
				String letNum = null;
				if((lotteryCode.equals("200") && ("05".contains(playCode) || "05".equals(playCode10)))
						|| (lotteryCode.equals("201") && ("01".contains(playCode) || "01".equals(playCode10) || "03".contains(playCode) || "03".equals(playCode10)))){
					letNum = "0";
				}else {
					letNum = getLetBall(ticket,info[0],playCode10,oddsInfo);
				}
				//获取让球数量
				if (Utils.isNotEmpty(letNum)) {
					buffer.append("(").append(letNum).append(")");					
				} else if (lotteryCode.equals("201") || (lotteryCode.equals("200") && ("05".contains(playCode) || "05".equals(playCode10)))){
					buffer.append("(0)");
				}
			}

			buffer.append("=");
			
			String[] betArray = bets.split(",");
			String[] oddBetsArray = oddsInfo.substring(oddsInfo.indexOf(",") + 1).split("/");
			int j = 0;
			for (String bet : betArray) {
				if (j > 0) {
					buffer.append("/");
				}

				if ("200".equals(lotteryCode) && ("03".equals(playCode) || "03".equals(playCode10))) {
					bet = bet.substring(0,1) + "-" + bet.substring(1);
				}
				if ("200".equals(lotteryCode) && ("04".equals(playCode) || "04".equals(playCode10))) {
					bet = bet.substring(0,1) + ":" + bet.substring(1);
				}
				
				buffer.append(bet).append("(").append(oddBetsArray[j].substring(oddBetsArray[j].lastIndexOf(":") + 1)).append(")");
				j++;
			}
			index++;
		}
		buffer.append("|").append(numbers[1]);
		
		return buffer.toString();
		
	}
	
	public static String covertNoticeOddsToNumber(JsonNode spValue,Ticket ticket){
		
		String numberInfo = ticket.getNumberInfo();
		String[] numbers = numberInfo.split("\\|");
//		String passType = numbers[1];
		String playCode = ticket.getPlayCode();
		String lotteryCode = ticket.getLotteryCode();
//		if ("1*1".equals(passType) && !"20004".equals(lotteryCode + playCode) && !"20103".equals(lotteryCode + playCode)) {
//			return covertOddsDanGuan(numberInfo, lotteryCode, playCode);
//		}
		
		String oddsLottery = ToSendCode.getOddsLottery(lotteryCode, playCode) + "|";
		StringBuffer buffer = new StringBuffer(oddsLottery);
		
		String[] numberArray = numbers[0].split(";");
		//String[] oddsInfoArray = spValue.split("//");
		int index = 0;
		for(String number : numberArray){
			
			String[] info = number.split(":");
			String match = "";
			String bets = "";
			String playCode10 = "";
			String tempPlayCode = playCode;
			String tempPreScore = null;
			int isHh = 0;//是否混合过关，0-否 1-是
			//切割赛事和投注号码
			if ("10".equals(playCode)) {
				playCode10 = info[1];
				tempPlayCode = playCode10;
				match = info[0].substring(2) + ":" + playCode10;
				bets = info[2];
				isHh = 1;
			} else {
				match = info[0].substring(2);
				bets = info[1];
			}
			
			if (index > 0) {
				buffer.append(",");
			}
			
			buffer.append(match);
			JsonNode oddsInfo = getNoticeBetsByMatch(lotteryCode,info[0],spValue);
			if(oddsInfo == null){
				continue;
			}
			
			if(lotteryCode.equals("201") || (lotteryCode.equals("200") && ("01".contains(playCode) || "01".equals(playCode10) || "05".contains(playCode) || "05".equals(playCode10)))){//所有篮球玩法及足球胜平负和让球胜平负需要加让球
				String letNum = null;
				if((lotteryCode.equals("200") && ("05".contains(playCode) || "05".equals(playCode10)))
						|| (lotteryCode.equals("201") && ("01".contains(playCode) || "01".equals(playCode10) || "03".contains(playCode) || "03".equals(playCode10)))){
					letNum = "0";
				}else {
					letNum = getNoticLetBall(ticket,info[0],playCode10,oddsInfo);
				}
				//获取让球数量
				if (Utils.isNotEmpty(letNum)) {
					buffer.append("(").append(letNum).append(")");					
				} else if (lotteryCode.equals("201") || (lotteryCode.equals("200") && ("05".contains(playCode) || "05".equals(playCode10)))){
					buffer.append("(0)");
				}
				tempPreScore = letNum;
			}

			buffer.append("=");
			
			String[] betArray = bets.split(",");
			int j = 0;
			for (String bet : betArray) {
				String tempBet = bet;
				if (j > 0) {
					buffer.append("/");
				}

				if ("200".equals(lotteryCode) && ("03".equals(playCode) || "03".equals(playCode10))) {
					bet = bet.substring(0,1) + "-" + bet.substring(1);
				}
				if ("200".equals(lotteryCode) && ("04".equals(playCode) || "04".equals(playCode10))) {
					bet = bet.substring(0,1) + ":" + bet.substring(1);
				}
				String fieldName = convertBets(lotteryCode, tempPlayCode, tempBet, isHh, tempPreScore);
				JsonNode oddJsonNode = oddsInfo.get(fieldName);
				//logger.info("彩种：" + lotteryCode + " 是否混合：" + isHh +" 中彩汇投注串：" + number + " 玩法：" + tempPlayCode + " 投注项：" +  tempBet + " 转换后投注项:" + fieldName + " 在投注串[" + oddsInfo.toString() + "]中查询到赔率为"  + oddJsonNode);
				if(oddJsonNode == null){
					logger.info(" TicketId:" + ticket.getTicketId() + "zch bet_number：" + number + " in letou_numbers[" + oddsInfo.toString() + "]can not get ["+tempBet + "-" +fieldName+"] odds");
					continue;
				}
				String odd = oddJsonNode.getTextValue();
				buffer.append(bet).append("(").append(odd).append(")");
				j++;
			}
			index++;
		}
		buffer.append("|").append(numbers[1]);
		
		return buffer.toString();
		
	}
	
	/**
	 * 转换玩法投注代码
	 * @param lotteryCode
	 * @param playCode
	 * @param bet
	 * @return
	 */
	private static String convertBets(String lotteryCode,String playCode,String bet,int isHh,String tempPreScore){
		String returnBet = "";
		if("200".equals(lotteryCode)){
			if(isHh == 0){
				if("01".equals(playCode) || "02".equals(playCode) || "05".equals(playCode)){
					returnBet = "0" + bet; 
				}
			}
			
			if(isHh == 1){
				if("01".equals(playCode)){
					returnBet = bet + "R"; 
				}
				if("02".equals(playCode)){
					returnBet = bet + "B";  
				}
				if("05".equals(playCode)){
					returnBet = "0" + bet; 
				}
			}
			
			if("03".equals(playCode) ){
				returnBet = bet;
			}
			
			if("04".equals(playCode) ){
				if("90".equals(bet)){
					returnBet = "3A";
				}else if("99".equals(bet)){
					returnBet = "1A";
				}else if("09".equals(bet)){
					returnBet = "0A";
				}else {
					returnBet = bet;
				}
			}
		}
		
		if("201".equals(lotteryCode)){
			if(isHh == 0){
				if("01".equals(playCode)){
					
					if("1".equals(bet)){
						returnBet = "01";
					}
					
					if("2".equals(bet)){
						returnBet = "00";
					}
				}
				
				if("02".equals(playCode)){
					
					if("1".equals(bet)){
						returnBet = "01" + "(" + tempPreScore + ")"; 
					}
					
					if("2".equals(bet)){
						returnBet = "00" + "(" + tempPreScore + ")"; 
					}
				}
				
				if("04".equals(playCode)){
					returnBet = "0" + bet + "(" + tempPreScore + ")"; 
				}
			}
			
			if(isHh == 1){
				if("01".equals(playCode)){
					
					if("1".equals(bet)){
						returnBet = "01";
					}
					
					if("2".equals(bet)){
						returnBet = "00";
					}
					
				}
				if("02".equals(playCode)){
					
					if("1".equals(bet)){
						returnBet = "01D" + "(" + tempPreScore + ")"; 
					}
					
					if("2".equals(bet)){
						returnBet = "00D" + "(" + tempPreScore + ")"; 
					}
					
				}
				
				if("04".equals(playCode)){
					returnBet = "0" + bet + "B" + "(" + tempPreScore + ")";  
				}
			}
			if("03".equals(playCode)){
				returnBet = bet.replace("0", "5")
							   .replace("11", "01")
							   .replace("12", "02")
							   .replace("13", "03")
							   .replace("14", "04")
							   .replace("15", "05")
							   .replace("16", "06");
			}
			
		}
		return returnBet;
	}
	
	/**
	 * 转换竞彩浮动赔率
	 * 
	 * @param
	 * @return
	 */
	public static String covertOddsDanGuan(String numberInfo, String lotteryCode, String playCode) {
		String oddsLottery = ToSendCode.getOddsLottery(lotteryCode, playCode) + "|";

		String number = numberInfo.split("\\|")[0];
		String passType = numberInfo.split("\\|")[1];

		String[] numbers = number.split(";");
		String numberStr = "";
		for (String num : numbers) {
			String matchNo = num.split(":")[0];
			String betInfo = num.split(":")[1];

			String[] bets = betInfo.split(",");
			String betStr = "";
			for (String bet : bets) {
				if ("20004".equals(lotteryCode + playCode)) {
					char[] bt = bet.toCharArray();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < bt.length; j++) {
						if (j == 0) {
							sb.append(bt[j]);
						} else {
							sb.append(":").append(bt[j]);
						}
					}

					bet = sb.toString();
				} else if ("20003".equals(lotteryCode + playCode)) {
					char[] bt = bet.toCharArray();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < bt.length; j++) {
						if (j == 0) {
							sb.append(bt[j]);
						} else {
							sb.append("-").append(bt[j]);
						}
					}

					bet = sb.toString();
				}

				if (Utils.isNotEmpty(betStr)) {
					betStr = betStr + "/" + bet + "(1.00)";
				} else {
					betStr = bet + "(1.00)";
				}
			}

			if ("200".equals(lotteryCode)) {
				matchNo = matchNo.substring(2);
			} else {
				matchNo = matchNo.substring(2) + "(0)";
			}

			if (Utils.isNotEmpty(numberStr)) {
				numberStr = numberStr + "," + matchNo + "=" + betStr;
			} else {
				numberStr = matchNo + "=" + betStr;
			}
		}

		String returnodds = oddsLottery + numberStr + "|" + passType;
		return returnodds;
	}
	
	/**
	 * 根据比赛获取相应赔率
	 * @param match
	 * @param oddsInfoArray
	 * @return
	 */
	private static String getBetsByMatch(String match,String[] oddsInfoArray){
		for(String oddsInfo : oddsInfoArray){
			if(oddsInfo.contains(match)){
				return oddsInfo;
			}
		}
		return null;
	}
	
	/**
	 * 获取出票通知的比赛信息
	 * @param match
	 * @param bets
	 * @return
	 */
	private static JsonNode getNoticeBetsByMatch(String lotteryCode, String match,JsonNode bets){
		Iterator<String> fieldNames = bets.getFieldNames();
		
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			String realMatch = "";
			if("200".equals(lotteryCode)){
				realMatch = "F" + match;
			}
			if("201".equals(lotteryCode)){
				realMatch = "B" + match;
			}
			if (fieldName.equals(realMatch)) {
				return bets.get(fieldName);
			}
		}
		logger.info("match[" + match + "]在 bets的json[" + bets.toString()+"]找不到对应的赛事");
		return null;
	}
	
	/**
	 * 获取json节点的名称
	 * @param jsonNode
	 * @return
	 */
	private static List<String> getFieldNamesFromJsonNode(JsonNode jsonNode){
		Iterator<String> fieldNames = jsonNode.getFieldNames();
		List<String> fieldNameList = new ArrayList<String>();
		while(fieldNames.hasNext()) {
			String fieldName = fieldNames.next();
			fieldNameList.add(fieldName);
		}
		return fieldNameList;
	}
	
	/**
	 * 获取让球（分）数量
	 * @param ticket
	 * @param match
	 * @return
	 */
	private static String getLetBall(Ticket ticket,String match,String playCode10,String oddsInfo){
		String issue = match.substring(0,8);
		String sn = match.substring(8);//获取sn
		String lotteryCode = ticket.getLotteryCode();
		String playCode = ticket.getPlayCode();
		//String tempPlayCode = playCode;//缓存本次投注实际玩法，用于篮球判断
		String pollCode = ticket.getPollCode();
		if("10".equals(playCode) && !"".equals(playCode10)){
			playCode = playCode10;
			//tempPlayCode = playCode10;
		}
		if("02".equals(pollCode)){
			playCode = "00";
		}

		try {
			StringBuffer key = new StringBuffer();
			XMemcachedClientWrapper xmcw = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
			key.append(lotteryCode).append(".").append(issue).append(".");
			key.append(playCode).append(".").append(pollCode).append(".");
			key.append(sn);
			if("200".equals(lotteryCode)){//从缓存中获取足球的让球
				SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = xmcw.get(key.toString());
				if(subIssueForJingCaiZuQiu != null){
					return subIssueForJingCaiZuQiu.getLetBall();					
				}else {//如果从缓存中不能取到让球，则从数据库中直接取
					logger.error("Ltkj Canot Get LetBall From Cache issue=[" + issue + "],sn=[" + sn + "],pollCode=[" + pollCode + "],playCode=[" + playCode + "]的让球");
					return getFootLetBallFromDB(issue, sn, pollCode, playCode);
				}
			}
			
			if("201".equals(lotteryCode)){//从缓存中获取篮球的让球（分）
//				SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = xmcw.get(key.toString());
//				if(subIssueForJingCaiLanQiu != null){
//					String lqLetNum = null;
//					if ("02".equals(tempPlayCode)) {
//						lqLetNum = subIssueForJingCaiLanQiu.getLetBall();
//					}else if("04".equals(tempPlayCode)) {
//						lqLetNum = subIssueForJingCaiLanQiu.getPreCast();
//					}
//					return lqLetNum;
//				}
				return getBasketLetBall(oddsInfo);
			}
		} catch (Exception e) {
			logger.error("Memcache Exception", e);
		}
		
		return null;
	}
	
	/**
	 * 获取出票通知的让球
	 * @param ticket
	 * @param match
	 * @param playCode10
	 * @param oddInfo
	 * @return
	 */
	private static String getNoticLetBall(Ticket ticket,String match,String playCode10,JsonNode oddsInfo){
		
		String issue = match.substring(0,8);
		String sn = match.substring(8);//获取sn
		String lotteryCode = ticket.getLotteryCode();
		String playCode = ticket.getPlayCode();
		//String tempPlayCode = playCode;//缓存本次投注实际玩法，用于篮球判断
		String pollCode = ticket.getPollCode();
		if("10".equals(playCode) && !"".equals(playCode10)){
			playCode = playCode10;
			//tempPlayCode = playCode10;
		}
		if("02".equals(pollCode)){
			playCode = "00";
		}

		try {
			StringBuffer key = new StringBuffer();
			XMemcachedClientWrapper xmcw = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
			key.append(lotteryCode).append(".").append(issue).append(".");
			key.append(playCode).append(".").append(pollCode).append(".");
			key.append(sn);
			if("200".equals(lotteryCode)){//从缓存中获取足球的让球
				SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = xmcw.get(key.toString());
				if(subIssueForJingCaiZuQiu != null){
					return subIssueForJingCaiZuQiu.getLetBall();					
				}else {//如果从缓存中不能取到让球，则从数据库中直接取
					logger.error("Ltkj Canot Get LetBall From Cache issue=[" + issue + "],sn=[" + sn + "],pollCode=[" + pollCode + "],playCode=[" + playCode + "]的让球");
					return getFootLetBallFromDB(issue, sn, pollCode, playCode);
				}
			}
			
			if("201".equals(lotteryCode)){//从缓存中获取篮球的让球（分）
				return getNoticeBasketLetBall(oddsInfo);
			}
		} catch (Exception e) {
			logger.error("Memcache Exception", e);
		}
		
		return null;
	}
	
	/**
	 * 从数据库获取竞彩让球
	 * @param issue
	 * @param sn
	 * @param pollCode
	 * @param playCode
	 * @return
	 */
	private static String getFootLetBallFromDB(String issue, String sn, String pollCode, String playCode){
		ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService)SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
		SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuByIssueSn(issue, sn, pollCode, playCode);
		if(subIssueForJingCaiZuQiu != null){
			return subIssueForJingCaiZuQiu.getLetBall();
		}
		logger.error("Ltkj Canot Get LetBall From DB issue=[" + issue + "],sn=[" + sn + "],pollCode=[" + pollCode + "],playCode=[" + playCode + "]的让球");
		return null;
	}
	
	/**
	 * 获取篮球出票通知的让分或预设分
	 * @param playCode
	 * @param oddsInfo
	 * @return
	 */
	private static String getNoticeBasketLetBall(JsonNode oddsInfo){
		List<String> fieldNames = getFieldNamesFromJsonNode(oddsInfo);
		String fieldName = fieldNames.get(0);
		if(fieldName != null){
			return fieldName.substring(fieldName.indexOf("(") + 1, fieldName.indexOf(")"));
		}
		return null;
	}
	
	/**
	 * 获取篮球的让分或预设分
	 * @param playCode
	 * @param oddsInfo
	 * @return
	 */
	private static String getBasketLetBall(String oddsInfo){
		String[] oddBetsArray = oddsInfo.substring(oddsInfo.indexOf(",") + 1).split("/");
		for(String oddBet : oddBetsArray){
			if(oddBet.contains("(")){
				return oddBet.substring(oddBet.indexOf("(") + 1, oddBet.indexOf(")"));
			}
		}
		return null;
	}
	
	/**
	 * 处理投注返回信息
	 * @param message
	 * @return
	 */
	public static Element getOrderResponse(String message) {
		 Element ticketElement = null;
		if (Utils.isNotEmpty(message)) {
			try {
				 Document document = DocumentHelper.parseText(message);
			     Element rootElement = document.getRootElement();
			     Element ticketsElement = rootElement.element("Tickets");
			     ticketElement = ticketsElement.element("Ticket");
			} catch (Exception e) {
				logger.error("Parse SendOrder XML Exception", e);
			}
		}
		return ticketElement;
	}
	
	/**
	 * 处理出票状态返回信息
	 * @param message
	 * @return
	 */
	public static List<Element> getQueryResponse(String message) {
		List<Element> tickets = null;
		if (Utils.isNotEmpty(message)) {
			try {
				 Document document = DocumentHelper.parseText(message);
			     Element rootElement = document.getRootElement();
			     tickets = rootElement.elements("Ticket");
			} catch (Exception e) {
				logger.error("Parse QueryOrder XML Exception", e);
			}
		}
		return tickets;
	}
	
	 public static XmlBean getResponse(String message) {
	        XmlBean response = null;
	        if (Utils.isNotEmpty(message)) {
	            try {
	            	response = ByteCodeUtil.xmlToObject(message);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return response;
	 }
	
	/**
	 * 处理查询余额返回信息
	 * @param message
	 * @return
	 */
	public static Element getAccountResponse(String message) {
		Element rootElement = null;
		if (Utils.isNotEmpty(message)) {
			try {
				 Document document = DocumentHelper.parseText(message);
			     rootElement = document.getRootElement();
			    
			} catch (Exception e) {
				logger.error("Parse QueryAccount XML Exception", e);
			}
		}
		return rootElement;
	}
	
	
	/**
	 * 调用webservice方法
	 * @param url
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static String invokeWebservice(String url,String methodName,Object[] params){
		 Client client = null;  
	        try {  
	            client = new Client(new URL(url)); 
	            client.setTimeout(60000);
	            Object[] result = client.invoke(methodName, params);  
	            return (String)result[0];
	        } catch (MalformedURLException e) {  
	        	logger.error("WebService MalformedURLException", e);
	        } catch (Exception e) {  
	        	logger.error("WebService Exception", e);
	        } finally {
	        	client.close();
	        }
	        return null;
	}
	
	
	/**
	 * 乐透转咱们  
	 * 乐透的期次格式：双色球：16105 年份后两位+编号(三位)
	 * 咱们的期次格式：双色球：2013110
	 * @param lotteryCode
	 * @param issue
	 * @return
	 */
	public static String ltkjToUsIssueFormat(String lotteryCode,String issue){
		if("001".equals(lotteryCode)||"002".equals(lotteryCode)||"004".equals(lotteryCode)){
			return "20"+issue; 
		}
		return issue;
	}
	/**
	 * 咱们转乐透
	 * @param lotteryCode
	 * @param issue
	 * @return
	 */
	public static String usToLtkjIssueFormat(String lotteryCode,String issue){
		if("001".equals(lotteryCode)||"002".equals(lotteryCode)||"004".equals(lotteryCode)){
			return issue.substring(2);
		}
		return issue;
	}
	public static void main(String args[]) {
		String betType = "S";
		String arrNum[] = "2,2,1;2,3,3;3,4,4;5,3,5;2,4,2".split(";")[0].split(",");
		if(!arrNum[1].equals(arrNum[0]) && !arrNum[1].equals(arrNum[2]) && !arrNum[0].equals(arrNum[2])){
			betType = "Z";
		}
		
		System.out.println(betType);
		
//		Ticket ticket = new Ticket();
//		ticket.setTicketId("3031102101911520272726");
//		ticket.setNumberInfo("20140606051:01:3,0;20140606052:01:1,3;20140606053:05:0,3;20140606054:05:1;20140606055:05:3;20140606056:01:3;20140606057:01:3;20140606058:01:3|8*28");
//		ticket.setIssue("20140606");
//		ticket.setMultiple(1);
//		ticket.setItem(248);
//		ticket.setLotteryCode("200");
//		ticket.setPlayCode("10");
//		ticket.setPollCode("02");
//		ticket.setAmount(496d);
//		//String oddsInfo = covertOddsToNumber("F20140606051,1R:3.30/3R:2.50//F20140606052,0:2.95/3:2.06//F20140606053,1:3.65/3:1.78//F20140606054,3:2.08//F20140606055,3R:3.15//F20140606056,3R:1.61//F20140606057,3R:2.98//F20140606058,3R:2.80", ticket);
//		
////		ticket.setTicketId("305150951038820DgM91");
////		ticket.setNumberInfo("20140528301:03:12,03;20140528302:02:1;20140529302:02:1|3*1");
////		ticket.setIssue("20140528");
////		ticket.setMultiple(1);
////		ticket.setItem(24);
////		ticket.setLotteryCode("201");
////		ticket.setPlayCode("10");
////		ticket.setPollCode("02");
////		ticket.setAmount(48d);
////		String oddsInfo = covertOddsToNumber("B20140528301,12:6.9/03:2.9//B20140528302,1:9.6//B20140529302,2:5.9", ticket);
////		ticket.setTicketId("3052111511355512370x");
////		ticket.setNumberInfo("20140528302:2;20140529302:2|2*1");
////		ticket.setIssue("20140516");
////		ticket.setMultiple(3);
////		ticket.setItem(1);
////		ticket.setLotteryCode("201");
////		ticket.setPlayCode("02");
////		ticket.setPollCode("02");
////		ticket.setAmount(6d);
////		String oddsInfo = covertOddsToNumber("B20140528302,0(+1.5):1.70//B20140529302,0(-5.5):1.71", ticket);
//		
////		ticket.setTicketId("30529093325102e3bM2S72");
////		ticket.setNumberInfo("20140603301:04:2,1;20140603302:01:2,1|2*1");
////		ticket.setIssue("20140603");
////		ticket.setMultiple(1);
////		ticket.setItem(4);
////		ticket.setLotteryCode("201");
////		ticket.setPlayCode("10");
////		ticket.setPollCode("02");
////		ticket.setAmount(8d);
////		String oddsInfo = covertOddsToNumber("B20140603301,2B(155.5):1.70/1B(155.5):1.70//B20140603302,0:4.85/1:1.03", ticket);
//		
////		ticket.setTicketId("30515140744084m61UoxU");
////		ticket.setNumberInfo("20140515002:3,0|1*1");
////		ticket.setIssue("20140515");
////		ticket.setMultiple(1);
////		ticket.setItem(2);
////		ticket.setLotteryCode("200");
////		ticket.setPlayCode("01");
////		ticket.setPollCode("02");
////		ticket.setAmount(4d);
////		String oddsInfo = covertOddsToNumber("F20140515002,3:2.58/0:3.30", ticket);
//		
//		
//	//	logger.info("oddsInfo:" + oddsInfo);
//		//String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><Balance>9.9999224E7</Balance></response>";
//		//System.out.println(getResponse(xml));
//		String json = "{\"00\":\"2.100\",\"01\":\"2.200\",\"02\":\"2.300\"}";
//		ObjectMapper objectMapper = new ObjectMapper();
//		try {
//			JsonNode rootNode = objectMapper.readTree(json);
//			List<String> fieldNames = getFieldNamesFromJsonNode(rootNode);
//			for(String fieldName : fieldNames){
//				logger.info("fieldName:" + fieldName);
//				logger.info("value:" + rootNode.path(fieldName).getTextValue());
//			}
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
}
