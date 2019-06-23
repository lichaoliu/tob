package com.cndym.service.subIssue;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cndym.lottery.lotteryInfo.bean.LotteryTime;
import com.cndym.utils.Utils;

public class SpecialDayOperator {
	public static final String CONFIG_FILE = "jcSpecialDayConfig.properties";
	private static Map<String, String> specialDayMap = null;
    public static Logger logger = Logger.getLogger(SpecialDayOperator.class);
	
	static {
		forInstance();
	}
	
	public static void forInstance() {
		specialDayMap = new HashMap<String, String>();
		try {
            Properties properties = new Properties();
			InputStream inputStream = new FileInputStream(Utils.getClassPath() + CONFIG_FILE);
			properties.load(inputStream);
			Enumeration<Object> all = properties.keys();
			while (all.hasMoreElements()) {
				String name = (String) all.nextElement();
				String value = (String) properties.get(name);
				specialDayMap.put(name, value);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public static String getValue(String name) {
		if (specialDayMap.containsKey(name)) {
			return specialDayMap.get(name);
		}
		return null;
	}

	public static void setValue(String key, String value) {
		if (specialDayMap.containsKey(key)) {
			specialDayMap.put(key, value);
		}
	}
	
	public static LotteryTime getLotteryTime(String lotteryCode, Date endTime, LotteryTime lotteryTime) {
		LotteryTime lt = new LotteryTime();
        lt.setAction(lotteryTime.getAction());
        lt.setLotteryCode(lotteryTime.getLotteryCode());
		lt.setWeek(lotteryTime.getWeek());
        lt.setStartTime(lotteryTime.getStartTime());
        lt.setEndTime(lotteryTime.getEndTime());
        lt.setAddDate(lotteryTime.getAddDate());
		try {
	        String day = Utils.formatDate2Str(endTime, "yyyyMMdd");
	        if (specialDayMap.containsKey(day)) {
	        	String[] timeStartEnd = specialDayMap.get(day).split("_");
		        //if ("200".equals(lotteryCode)) {
		        	lt.setStartTime(timeStartEnd[1]);
		        	lt.setEndTime(timeStartEnd[0]);
		        	lt.setAddDate(Utils.formatInt(timeStartEnd[2], 0));
		        //} else if ("201".equals(lotteryCode)) {
		        //	if("07:30:00".equals(lotteryTime.getStartTime()) && !"00:00:00_09:00:00_0".equals(specialDayMap.get(day))) {
			    //    	lt.setStartTime("09:00:00");
		        //	}
		        //}
	        }
		} catch (Exception e) {
			logger.error("", e);
		}
		return lt;
	}
}
