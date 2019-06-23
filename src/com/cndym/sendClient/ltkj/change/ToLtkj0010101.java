package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 双色球 (单式)
 * @author LN
 *
 */
@Component
public class ToLtkj0010101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace("#", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0010101 to = new ToLtkj0010101();
		String number = "02,05,12,17,25,28#13;03,06,07,15,21,28#14"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
