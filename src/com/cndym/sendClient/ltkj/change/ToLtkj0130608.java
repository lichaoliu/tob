package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (三连号通选)
 * @author LN  123
 *
 */
@Component
public class ToLtkj0130608 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return "123";
	}
	
	public static void main(String[] args) {
		ToLtkj0130608 to = new ToLtkj0130608();
		String number = "7,8,9"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
