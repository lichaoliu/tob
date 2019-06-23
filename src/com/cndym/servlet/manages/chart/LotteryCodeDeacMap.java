package com.cndym.servlet.manages.chart;

import java.util.HashMap;
import java.util.Map;
/**
 * 作者：刘力超 时间：2014-6-7 上午10:07:45 QQ：1147149597
 * 
 * 将将饼状图，数据库彩种编号更换为彩种名
 */
public class LotteryCodeDeacMap {
	
	
	private static Map<String, String> codeDescMap = new HashMap<String, String>();

    static {
        forInstance();
    }

    public static void forInstance() {
    	codeDescMap.put("001","双色球");
    	codeDescMap.put("002", "福彩3D");
    	codeDescMap.put("003", "15选5");
    	codeDescMap.put("004", "七乐彩");
    	codeDescMap.put("007", "湖北快3");
    	codeDescMap.put("010", "安徽快3");
    	codeDescMap.put("011", "江苏快3");
    	codeDescMap.put("006", "重庆时时彩");
    	codeDescMap.put("009", "江西时时彩");
    	codeDescMap.put("113", "大乐透");
    	codeDescMap.put("108", "排列三");
    	codeDescMap.put("109", "排列五");
    	codeDescMap.put("110", "七星彩");
    	codeDescMap.put("107", "山东11选5");
    	codeDescMap.put("106", "安徽11选5");
    	codeDescMap.put("105", "广东11选5");
    	codeDescMap.put("104", "重庆11选5");
    	codeDescMap.put("103", "新疆11选5");
    	codeDescMap.put("300", "胜平负14场");
    	codeDescMap.put("303", "胜平负9场");
    	codeDescMap.put("301", "6场半全场");
    	codeDescMap.put("302", "4场进球彩");
    	codeDescMap.put("200", "竞彩足球");
    	codeDescMap.put("201", "竞彩篮球");
    	codeDescMap.put("114", "山东快乐扑克");
    	codeDescMap.put("012", "甘肃快三");
    }

    public static String getCodeDescMsg(String errorCode) {
        String msg = codeDescMap.get(errorCode);
        if (msg == null) {
            return errorCode;
        } else {
            return msg;
        }
    }

	
	
	
	
	
	
	
	
	
	
	
	

}
