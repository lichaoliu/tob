package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (普通和值)
 * @author LN
 *
 */
@Component
public class ToLtkj0130104 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0130104 to = new ToLtkj0130104();
		String number = "4;5;6;7;8"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
