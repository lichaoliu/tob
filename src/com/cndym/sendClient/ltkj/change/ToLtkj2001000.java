package com.cndym.sendClient.ltkj.change;


import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;
import com.cndym.sendClient.ltkj.util.ToSendCode;

/**
 * 竞彩足球混合过关投注串转换
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj2001000  implements IChange {
	
	@Override
	public String toSendNumberCode(String number) {
		String[] numbers = number.split("\\|");
		String[] nums = numbers[0].split(";");
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"bet\":{");
		int index = 0;
		for (String num : nums) {
			String issue = "F" + num.substring(0,num.indexOf(':'));
			String res = num.substring(num.indexOf(':') + 1);
			String[] resArray = res.split(":");
			res = change(resArray[0], resArray[1]);
			if(index > 0){
				buffer.append(",\"");
			}else {
				buffer.append("\"");
			}
			
			buffer.append(issue)
			      .append("\":")
			      .append("\"")
			      .append(res)
			      .append("\"");
			
			index ++;
		}
		buffer.append("}}");
		return buffer.toString();
	}
	
	private String change(String playCode,String betStr){
		String result = null;
		if("01".equals(playCode) || "02".equals(playCode) || "05".equals(playCode)){
			if(betStr.indexOf(",") != -1){
				betStr = "0" + betStr.replace(",", "0");
			}else {
				betStr = "0" + betStr;
			}
			result = betStr + "-" + ToSendCode.playToPost("200",playCode);
		}
		if("03".equals(playCode)){
			if(betStr.indexOf(",") != -1){
				result = betStr.replace(",","") + "-" + "14";
			}else {
				result = betStr + "-" + "14";;
			}
		}
		
		if("04".equals(playCode)){
			String ltScores = "";
			if(betStr.indexOf(",") != -1){
				String[] scores = betStr.split(",");
				for(String score : scores){
					
					if("90".equals(score)){
						score = "3A";
					}
					if("99".equals(score)){
						score = "1A";
					}
					if("09".equals(score)){
						score = "0A";
					}
					ltScores += score;
				}
			}else {
				if("90".equals(betStr)){
					ltScores = "3A";
				}else if("99".equals(betStr)){
					ltScores = "1A";
				}else if("09".equals(betStr)){
					ltScores = "0A";
				}else {
					ltScores = betStr;
				}
				
			}
			result = ltScores + "-" + "12";
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		ToLtkj2001000 to = new ToLtkj2001000();

		String number = "20130621001:02:1,7;20130621002:04:90,09;20130621003:05:3,1,0;20130621004:01:1,3;20130621006:03:33,11,10|4*11"; 
		//{"bet":{"F20100702011":"0300","F20100702010":"0301"}}
		//String number = "20121029002:3|1*1"; 
		System.out.println(to.toSendNumberCode(number));
		
	}
}
