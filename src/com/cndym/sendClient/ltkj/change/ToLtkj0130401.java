package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (二不同号单式) 3,5;3,6;4,5;4,6;5,6  12
 * @author LN
 *
 */
@Component
public class ToLtkj0130401 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0130401 to = new ToLtkj0130401();
		String number = "3,5;3,6;4,5;4,6;5,6"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
