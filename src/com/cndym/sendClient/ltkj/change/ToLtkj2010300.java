package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 竞彩篮球胜分差投注串转换
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj2010300 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		String[] numbers = number.split("\\|");
		String[] nums = numbers[0].split(";");
		StringBuffer buffer = new StringBuffer();
		buffer.append("{\"bet\":{");
		int index = 0;
		for (String num : nums) {
			String issue = "B" + num.substring(0,num.indexOf(':'));
			String res = num.substring(num.indexOf(':') + 1);
//			if(res.indexOf(",") != -1){
//				res = "0" + res.replace(",", "0");
//			}else {
//				res = "0" + res;
//			}
			
			res = res.replace("0", "5")
					 .replace("11", "01")
					 .replace("12", "02")
					 .replace("13", "03")
					 .replace("14", "04")
					 .replace("15", "05")
					 .replace("16", "06")
					 .replace(",", "");
			
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
		ToLtkj2010300 to = new ToLtkj2010300();

		String number = "20131024301:05|1*1"; 
		//{"bet":{"F20100702011":"0300","F20100702010":"0301"}}
		//String number = "20121029002:3|1*1"; 
		System.out.println(to.toSendNumberCode(number));
		
	}
}
