package com.cndym.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TicketIdBuildUtils {
	private static String[] numberString = {
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "q", "m",
		"w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d",
		"f", "g", "h", "j", "k", "z", "x", "c", "v", "b", "n",
		"W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D",
		"F", "G", "H", "J", "K", "Z", "X", "C", "V", "B", "N",
		"M", "Q"};

	public static String buildRandomString(int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int a = (int) (Math.random() * 60);
			sb.append(numberString[a]);
		}
		return sb.toString();
	}

	public static String buildTicketId(){
		String begin = "3";
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(begin);	    
	    buffer.append(formatDate2Str(new Date(), "MMddHHmmssSSS"));
	    buffer.append(buildRandomString(6));
	    return buffer.toString();
	}

	public static String formatDate2Str(Date date, String format){
	    SimpleDateFormat sdf = new SimpleDateFormat(format);
	    return sdf.format(date);
	}
	
	public static void main(String args[]) {
		System.out.println("start");
		String ticketId = null;
		Map<String, String> map = new HashMap<String, String>();
		for(int i=0 ;i < 1000000; i++) {
			ticketId = buildTicketId();
			if(map.containsKey(ticketId)) {
				System.out.println(ticketId);
			} else {
				map.put(ticketId, "1");
			}
		}
		System.out.println("end");
	}
}
