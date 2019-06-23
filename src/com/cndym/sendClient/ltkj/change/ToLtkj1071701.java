package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

@Component
public class ToLtkj1071701 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1071701 to = new ToLtkj1071701();
		String number = "01,05,06,07,08;02,05,06,08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
