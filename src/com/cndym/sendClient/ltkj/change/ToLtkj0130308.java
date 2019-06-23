package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (三同号通选)
 * @author LN
 *
 */
@Component
public class ToLtkj0130308 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		if("777".equals(number.replaceAll(",", ""))){
			return "111";
		}
		return  "111";
	}
	
	public static void main(String[] args) {
		ToLtkj0130308 to = new ToLtkj0130308();
		String number = "7,7,7"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
