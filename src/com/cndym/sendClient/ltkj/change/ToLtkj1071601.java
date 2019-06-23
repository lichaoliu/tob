package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

@Component
public class ToLtkj1071601 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1071601 to = new ToLtkj1071601();
		String number = "01,05,10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
