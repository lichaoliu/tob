package com.cndym.constant;

import java.util.HashMap;
import java.util.Map;

public class BeiDanChuanGuan {
	private static Map<String, String> beiDanSPF = new HashMap<String, String>();
	private static Map<String, String> beiDanSXP = new HashMap<String, String>();
	private static Map<String, String> beiDanZJQ = new HashMap<String, String>();
	private static Map<String, String> beiDanBQC = new HashMap<String, String>();
	private static Map<String, String> beiDanDCBF = new HashMap<String, String>();
	private static Map<String, Integer> beiDanChangCi = new HashMap<String, Integer>();
	
	static {
		//让球胜平负
		beiDanSPF.put("1*1", "1");
		beiDanSPF.put("2*1", "1");
		beiDanSPF.put("2*3", "1");
		beiDanSPF.put("3*1", "1");
		beiDanSPF.put("3*4", "1");
		beiDanSPF.put("3*7", "1");
		beiDanSPF.put("4*1", "1");
		beiDanSPF.put("4*5", "1");
		beiDanSPF.put("4*11", "1");
		beiDanSPF.put("4*15", "1");
		beiDanSPF.put("5*1", "1");
		beiDanSPF.put("5*6", "1");
		beiDanSPF.put("5*16", "1");
		beiDanSPF.put("5*26", "1");
		beiDanSPF.put("5*31", "1");
		beiDanSPF.put("6*1", "1");
		beiDanSPF.put("6*7", "1");
		beiDanSPF.put("6*22", "1");
		beiDanSPF.put("6*42", "1");
		beiDanSPF.put("6*57", "1");
		beiDanSPF.put("6*63", "1");
		beiDanSPF.put("7*1", "1");
		beiDanSPF.put("8*1", "1");
		beiDanSPF.put("9*1", "1");
		beiDanSPF.put("10*1", "1");
		beiDanSPF.put("11*1", "1");
		beiDanSPF.put("12*1", "1");
		beiDanSPF.put("13*1", "1");
		beiDanSPF.put("14*1", "1");
		beiDanSPF.put("15*1", "1");
		
		//上下盘单双
		beiDanSXP.put("1*1", "1");
		beiDanSXP.put("2*1", "1");
		beiDanSXP.put("2*3", "1");
		beiDanSXP.put("3*1", "1");
		beiDanSXP.put("3*4", "1");
		beiDanSXP.put("3*7", "1");
		beiDanSXP.put("4*1", "1");
		beiDanSXP.put("4*5", "1");
		beiDanSXP.put("4*11", "1");
		beiDanSXP.put("4*15", "1");
		beiDanSXP.put("5*1", "1");
		beiDanSXP.put("5*6", "1");
		beiDanSXP.put("5*16", "1");
		beiDanSXP.put("5*26", "1");
		beiDanSXP.put("5*31", "1");
		beiDanSXP.put("6*1", "1");
		beiDanSXP.put("6*7", "1");
		beiDanSXP.put("6*22", "1");
		beiDanSXP.put("6*42", "1");
		beiDanSXP.put("6*57", "1");
		beiDanSXP.put("6*63", "1");

		//总进球
		beiDanZJQ.put("1*1", "1");
		beiDanZJQ.put("2*1", "1");
		beiDanZJQ.put("2*3", "1");
		beiDanZJQ.put("3*1", "1");
		beiDanZJQ.put("3*4", "1");
		beiDanZJQ.put("3*7", "1");
		beiDanZJQ.put("4*1", "1");
		beiDanZJQ.put("4*5", "1");
		beiDanZJQ.put("4*11", "1");
		beiDanZJQ.put("4*15", "1");
		beiDanZJQ.put("5*1", "1");
		beiDanZJQ.put("5*6", "1");
		beiDanZJQ.put("5*16", "1");
		beiDanZJQ.put("5*26", "1");
		beiDanZJQ.put("5*31", "1");
		beiDanZJQ.put("6*1", "1");
		beiDanZJQ.put("6*7", "1");
		beiDanZJQ.put("6*22", "1");
		beiDanZJQ.put("6*42", "1");
		beiDanZJQ.put("6*57", "1");
		beiDanZJQ.put("6*63", "1");
		
		//半全场
		beiDanBQC.put("1*1", "1");
		beiDanBQC.put("2*1", "1");
		beiDanBQC.put("2*3", "1");
		beiDanBQC.put("3*1", "1");
		beiDanBQC.put("3*4", "1");
		beiDanBQC.put("3*7", "1");
		beiDanBQC.put("4*1", "1");
		beiDanBQC.put("4*5", "1");
		beiDanBQC.put("4*11", "1");
		beiDanBQC.put("4*15", "1");
		beiDanBQC.put("5*1", "1");
		beiDanBQC.put("5*6", "1");
		beiDanBQC.put("5*16", "1");
		beiDanBQC.put("5*26", "1");
		beiDanBQC.put("5*31", "1");
		beiDanBQC.put("6*1", "1");
		beiDanBQC.put("6*7", "1");
		beiDanBQC.put("6*22", "1");
		beiDanBQC.put("6*42", "1");
		beiDanBQC.put("6*57", "1");
		beiDanBQC.put("6*63", "1");
		
		//单场比分
		beiDanDCBF.put("1*1", "1");
		beiDanDCBF.put("2*1", "1");
		beiDanDCBF.put("2*3", "1");
		beiDanDCBF.put("3*1", "1");
		beiDanDCBF.put("3*4", "1");
		beiDanDCBF.put("3*7", "1");
		
		//传关及支持的场次数
		beiDanChangCi.put("1*1", 1);
		beiDanChangCi.put("2*1", 2);
		beiDanChangCi.put("2*3", 2);
		beiDanChangCi.put("3*1", 3);
		beiDanChangCi.put("3*4", 3);
		beiDanChangCi.put("3*7", 3);
		beiDanChangCi.put("4*1", 4);
		beiDanChangCi.put("4*5", 4);
		beiDanChangCi.put("4*11", 4);
		beiDanChangCi.put("4*15", 4);
		beiDanChangCi.put("5*1", 5);
		beiDanChangCi.put("5*6", 5);
		beiDanChangCi.put("5*16", 5);
		beiDanChangCi.put("5*26", 5);
		beiDanChangCi.put("5*31", 5);
		beiDanChangCi.put("6*1", 6);
		beiDanChangCi.put("6*7", 6);
		beiDanChangCi.put("6*22", 6);
		beiDanChangCi.put("6*42", 6);
		beiDanChangCi.put("6*57", 6);
		beiDanChangCi.put("6*63", 6);
		beiDanChangCi.put("7*1", 7);
		beiDanChangCi.put("8*1", 8);
		beiDanChangCi.put("9*1", 9);
		beiDanChangCi.put("10*1", 10);
		beiDanChangCi.put("11*1", 11);
		beiDanChangCi.put("12*1", 12);
		beiDanChangCi.put("13*1", 13);
		beiDanChangCi.put("14*1", 14);
		beiDanChangCi.put("15*1", 15);
	}
	
	public static boolean getBeiDanSPF(String chuanGuan) {
		return beiDanSPF.containsKey(chuanGuan);
	}

	public static boolean getBeiDanSXP(String chuanGuan) {
		return beiDanSXP.containsKey(chuanGuan);
	}
	
	public static boolean getBeiDanZJQ(String chuanGuan) {
		return beiDanZJQ.containsKey(chuanGuan);
	}
	
	public static boolean getBeiDanBQC(String chuanGuan) {
		return beiDanBQC.containsKey(chuanGuan);
	}
	
	public static boolean getBeiDanDCBF(String chuanGuan) {
		return beiDanDCBF.containsKey(chuanGuan);
	}
	
	public static int getChangCiCount(String chuanGuan) {
		if (beiDanChangCi.containsKey(chuanGuan)) {
			return beiDanChangCi.get(chuanGuan);
		}
		return 0;
	}
}

