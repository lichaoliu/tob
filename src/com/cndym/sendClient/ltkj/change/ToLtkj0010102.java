package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 双色球 (复式)
 * @author LN
 *
 */
@Component
public class ToLtkj0010102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace("#","-");
	}
	
	public static void main(String[] args) {
		ToLtkj0010102 to = new ToLtkj0010102();
		String number = "01,02,03,04,05,06,07,08#01,02"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
