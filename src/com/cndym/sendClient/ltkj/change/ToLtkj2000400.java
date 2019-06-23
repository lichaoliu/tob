package com.cndym.sendClient.ltkj.change;


import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 竞彩足球半全场投注串转换
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj2000400  implements IChange {
	
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
			String ltScores = "";
			if(res.indexOf(",") != -1){
				String[] scores = res.split(",");
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
				if("90".equals(res)){
					ltScores = "3A";
				}else if("99".equals(res)){
					ltScores = "1A";
				}else if("09".equals(res)){
					ltScores = "0A";
				}else {
					ltScores = res;
				}
				
			}
			
			if(index > 0){
				buffer.append(",\"");
			}else {
				buffer.append("\"");
			}
			
			buffer.append(issue)
			      .append("\":")
			      .append("\"")
			      .append(ltScores)
			      .append("\"");
			
			index ++;
		}
		buffer.append("}}");
		return buffer.toString();
	}
	
	public static void main(String[] args) {
		ToLtkj2000400 to = new ToLtkj2000400();

		String number = "20121029001:10,51,90;20121029002:09|2*1"; 
		//{"bet":{"F20100702011":"0300","F20100702010":"0301"}}
		//String number = "20121029002:3|1*1"; 
		System.out.println(to.toSendNumberCode(number));
		
	}
}
