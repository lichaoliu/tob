package com.cndym.sendClient.ltkj.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ToSendCode {
	
	//彩种对应关系
    private static Map<String, String> lotteryMap = new HashMap<String, String>();
    //玩法对应关系
    private static Map<String, String> playMap = new HashMap<String, String>();
    //投注方式对应关系
	private static Map<String, String> pollMap = new HashMap<String, String>();
	//赔率对应关系
	private static Map<String, String> oddsLotteryMap = new HashMap<String, String>();

    static {
        forInstance();
    }

    public static void forInstance() {
    	lotteryMap.put("200", "");//竞彩足球
    	lotteryMap.put("201", "");//竞彩篮球
    	lotteryMap.put("103", "63");//新疆11选5
    	lotteryMap.put("113", "35");//大乐透
    	lotteryMap.put("013", "43");//江西快3
    	lotteryMap.put("014", "47");//江西快3
    	lotteryMap.put("001", "40");//双色球
    	lotteryMap.put("004", "41");//七乐彩
    	lotteryMap.put("002", "42");//福彩3D
    	lotteryMap.put("110", "36");//七星彩
    	lotteryMap.put("108", "38");//排列3
    	lotteryMap.put("109", "37");//排列5
    	lotteryMap.put("300", "32");//胜平负14场
    	lotteryMap.put("301", "34");//6场半全场
    	lotteryMap.put("302", "33");//4场进球彩
    	lotteryMap.put("303", "32");//胜平负任九场
    	lotteryMap.put("107", "402");
        lotteryMap.put("105", "31");
        lotteryMap.put("111", "407");//陕西11选5
		lotteryMap.put("112", "409");//青海11选5
    	playMap.put("20001", "15");//足球让球胜平负
    	playMap.put("20002", "13");//足球总进球数
    	playMap.put("20003", "14");//足球半全场胜平负
    	playMap.put("20004", "12");//足球比分
    	playMap.put("20005", "11");//足球胜平负
    	playMap.put("20010", "120");//足球混合过关
    	
    	playMap.put("20101", "21");//篮球胜负
    	playMap.put("20102", "22");//篮球让分胜负
    	playMap.put("20103", "23");//篮球胜分差
    	playMap.put("20104", "24");//篮球大小分
    	playMap.put("20110", "121");//篮球混合过关
    	
    	playMap.put("10301", "01");//任一
    	playMap.put("10302", "02");//任二
    	playMap.put("10303", "03");//任三
    	playMap.put("10304", "04");//任四
    	playMap.put("10305", "05");//任五
    	playMap.put("10306", "06");//任六
    	playMap.put("10307", "07");//任七
    	playMap.put("10308", "08");//任八
    	playMap.put("10309", "02");//前二直选
    	playMap.put("10310", "03");//前三直选
    	playMap.put("10311", "02");//前二组选
    	playMap.put("10312", "03");//前三组选
    	playMap.put("10501", "01");
        playMap.put("10502", "02");
        playMap.put("10503", "03");
        playMap.put("10504", "04");
        playMap.put("10505", "05");
        playMap.put("10506", "06");
        playMap.put("10507", "07");
        playMap.put("10508", "08");
        playMap.put("10510", "02");
        playMap.put("10511", "03");
        playMap.put("10512", "02");
        playMap.put("10513", "03");
        playMap.put("10701", "01");
        playMap.put("10702", "02");
        playMap.put("10703", "03");
        playMap.put("10704", "04");
        playMap.put("10705", "05");
        playMap.put("10706", "06");
        playMap.put("10707", "07");
        playMap.put("10708", "08");
        playMap.put("10710", "02");
        playMap.put("10711", "03");
        playMap.put("10712", "02");
        playMap.put("10713", "03");
        playMap.put("10714", "02");
        playMap.put("10715", "03");
        playMap.put("10716", "04");
        playMap.put("10717", "05");
        
        
    	playMap.put("11301", "00");//大乐透标准玩法
    	playMap.put("11302", "00");//大乐透追加玩法
    	playMap.put("00101", "00");//双色球标准玩法
    	playMap.put("00201", "00");//福彩3D
    	playMap.put("00401", "00");//七乐彩
    	playMap.put("11001", "00");//七星彩普通玩法
    	playMap.put("10901", "00");//排列5普通玩法
    	playMap.put("1080101", "00");//排列3直选单式玩法
    	playMap.put("1080102", "02");//排列3直选复式玩法
    	playMap.put("1080104", "05");//排列3直选和值玩法
    	playMap.put("1080201", "01");//排列3普通玩法
    	playMap.put("1080204", "06");//排列3普通玩法
    	playMap.put("1080302", "03");//排列3普通玩法
    	playMap.put("1080402", "04");//排列3普通玩法
    	playMap.put("0020101", "00");//福彩3D直选单式玩法
    	playMap.put("0020102", "02");//福彩3D直选复式玩法
    	playMap.put("0020104", "05");//福彩3D直选和值玩法
    	playMap.put("0020201", "01");//福彩3D组三单式玩法  
    	playMap.put("0020202", "03");//福彩3D组三复式玩法 
    	playMap.put("0020204", "06");//福彩3D组三和值玩法 
    	playMap.put("0020301", "01");//福彩3D组六单式玩法 
    	playMap.put("0020302", "04");//福彩3D组六复式玩法 
    	playMap.put("0020304", "07");//福彩3D组六和值玩法
    	playMap.put("0130104", "00");//江西快3普通和值
    	playMap.put("0130201", "04");//江西快3二同号单选
    	playMap.put("0130207", "03");//江西快3二同号复选
    	playMap.put("0130301", "02");//江西快3三同号单选
    	playMap.put("0130308", "01");//江西快3三同号通选
    	playMap.put("0130401", "06");//江西快3二不同号
    	playMap.put("0130402", "06");//江西快3二不同号
    	playMap.put("0130501", "05");//江西快3三不同号
    	playMap.put("0130502", "05");//江西快3三不同号
    	playMap.put("0130608", "07");//江西快3三连号通选
    	
    	playMap.put("0140104", "00");//广西快3普通和值
    	playMap.put("0140201", "04");//广西快3二同号单选
    	playMap.put("0140207", "03");//广西快3二同号复选
    	playMap.put("0140301", "02");//广西快3三同号单选
    	playMap.put("0140308", "01");//广西快3三同号通选
    	playMap.put("0140401", "06");//广西快3二不同号
    	playMap.put("0140402", "06");//广西快3二不同号
    	playMap.put("0140501", "05");//广西快3三不同号
    	playMap.put("0140502", "05");//广西快3三不同号
    	playMap.put("0140608", "07");//广西快3三连号通选
    	
    	playMap.put("11101", "01");//陕西11选5任一
    	playMap.put("11102", "02");//陕西11选5任二
    	playMap.put("11103", "03");//陕西11选5任三
    	playMap.put("11104", "04");//陕西11选5任四
    	playMap.put("11105", "05");//陕西11选5任五
    	playMap.put("11106", "06");//陕西11选5任六
    	playMap.put("11107", "07");//陕西11选5任七
    	playMap.put("11108", "08");//陕西11选5任八
    	playMap.put("11109", "02");//陕西11选5前二直选
    	playMap.put("11110", "03");//陕西11选5前三直选
    	playMap.put("11111", "02");//陕西11选5前二组选
    	playMap.put("11112", "03");//陕西11选5前三组选

		playMap.put("11201", "01");//青海11选5任一
		playMap.put("11202", "02");//青海11选5任二
		playMap.put("11203", "03");//青海11选5任三
		playMap.put("11204", "04");//青海11选5任四
		playMap.put("11205", "05");//青海11选5任五
		playMap.put("11206", "06");//青海11选5任六
		playMap.put("11207", "07");//青海11选5任七
		playMap.put("11208", "08");//青海11选5任八
		playMap.put("11209", "02");//青海11选5前二直选
		playMap.put("11210", "03");//青海11选5前三直选
		playMap.put("11211", "02");//青海11选5前二组选
		playMap.put("11212", "03");//青海11选5前三组选

    	playMap.put("30001", "14");//胜平负14场
    	playMap.put("30101", "00");//6场半全场
    	playMap.put("30201", "00");//4场进球彩
    	playMap.put("30301", "09");//胜平负任九场
    	
    	pollMap.put("01", "S");//单式
    	pollMap.put("02", "M");//复式
    	pollMap.put("03", "D");//胆拖
    	pollMap.put("04", "M");//和值
    	pollMap.put("07", "S");
    	pollMap.put("08", "S");    	
    	oddsLotteryMap.put("20001", "RQS");
    	oddsLotteryMap.put("20002", "JQS");
    	oddsLotteryMap.put("20003", "BQC");
		oddsLotteryMap.put("20004", "CBF");
		oddsLotteryMap.put("20005", "SPF");
		oddsLotteryMap.put("20010", "ZHT");

		oddsLotteryMap.put("20101", "SFC");
		oddsLotteryMap.put("20102", "RSF");
		oddsLotteryMap.put("20103", "SFD");
		oddsLotteryMap.put("20104", "DXF");
		oddsLotteryMap.put("20110", "LHT");
    }

    public static String postToSys(String post) {
        if (lotteryMap.containsKey(post)) {
            return lotteryMap.get(post);
        }
        throw new IllegalArgumentException("(" + post + ")没有找到对应的系统彩种编码");
    }

    public static String playToPost(String lotteryCode, String playCode) {
    	String key = lotteryCode + playCode;
    	if (playMap.containsKey(key)) {
            return playMap.get(key);
        }
        throw new IllegalArgumentException("(" + playCode + ")在彩种("+lotteryCode+")中没有找到对应的出票口玩法编码");
    }
    
    public static String pollToPost(String pollCode) {
    	if (pollMap.containsKey(pollCode)) {
    		return pollMap.get(pollCode);
    	}
    	 throw new IllegalArgumentException("(" + pollCode + ")没有找到对应的出票口投注方式编码");
    }
    
    public static String getOddsLottery(String lotteryCode, String playCode) {
		String code = lotteryCode + playCode;
		for (Map.Entry<String, String> entry : oddsLotteryMap.entrySet()) {
			if (code.equals(entry.getKey())) {
				return entry.getValue();
			}
		}
		throw new IllegalArgumentException("(" + code + ")没有找到对应的编码");
	}
}