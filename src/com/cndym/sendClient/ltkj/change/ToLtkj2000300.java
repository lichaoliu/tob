package com.cndym.sendClient.ltkj.change;


import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 竞彩足球半全场投注串转换
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj2000300  implements IChange {
	
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
			if(res.indexOf(",") != -1){
				res = res.replace(",", "");
			}
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
	
	public static void main(String[] args) {
		ToLtkj2000300 to = new ToLtkj2000300();

		String number = "20140311009:30,11,10;20140311002:42;20140311003:11;20140311004:04|4*5"; 
		//{"bet":{"F20100702011":"0300","F20100702010":"0301"}}
		//String number = "20121029002:3|1*1"; 
		System.out.println(to.toSendNumberCode(number));
		
	}
}
