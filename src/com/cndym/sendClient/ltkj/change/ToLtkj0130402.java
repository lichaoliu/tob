package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (二不同号复式) 12-23-34
 * @author LN
 *
 */
@Component
public class ToLtkj0130402 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "");
	}
	
	public static void main(String[] args) {
		ToLtkj0130402 to = new ToLtkj0130402();
		String number = "2,3,2,4,2,5,2,6,3,4"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
