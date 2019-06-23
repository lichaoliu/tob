package com.cndym.sendClient.ltkj.change;


import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;
import com.cndym.sendClient.ltkj.util.ToSendCode;

/**
 * 竞彩篮球混合过关投注串转换
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj2011000  implements IChange {
	
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
		if("01".equals(playCode) || "02".equals(playCode)){
			betStr = betStr.replace("1", "01")
						   .replace("2", "00")
						   .replace(",", "");
			
			result = betStr + "-" + ToSendCode.playToPost("201",playCode);
		}
		if("03".equals(playCode)){
			betStr = betStr.replace("0", "5")
						   .replace("11", "01")
						   .replace("12", "02")
						   .replace("13", "03")
						   .replace("14", "04")
						   .replace("15", "05")
						   .replace("16", "06")
						   .replace(",", "");
			result = betStr + "-" + "23";
		}
		
		if("04".equals(playCode)){
			betStr = betStr.replace("1", "01")
						   .replace("2", "02")
						   .replace(",", "");
			result = betStr + "-" + "24";
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		ToLtkj2011000 to = new ToLtkj2011000();

		String number = "20131024301:02:2;20131024302:04:1;20131024303:04:1;20131024304:02:1;20131024305:01:2|5*5"; 
		//{"bet":{"F20100702011":"0300","F20100702010":"0301"}}
		//String number = "20121029002:3|1*1"; 
		System.out.println(to.toSendNumberCode(number));
		
	}
}
