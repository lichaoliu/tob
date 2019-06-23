package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (三不同号复式) 123-234-345  1234
 * @author LN
 *
 */
@Component
public class ToLtkj0130502 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "");
	}
	
	public static void main(String[] args) {
		ToLtkj0130502 to = new ToLtkj0130502();
		String number = "2,3,2,4,2,5,2,6,3,4"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
