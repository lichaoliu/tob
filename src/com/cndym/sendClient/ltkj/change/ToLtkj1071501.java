package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

@Component
public class ToLtkj1071501 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "").replaceAll("#", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1071501 to = new ToLtkj1071501();
		String number = "01#05#11;02,06,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
