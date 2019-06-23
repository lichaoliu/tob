package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 七乐彩 (复式)
 * @author LN
 *
 */
@Component
public class ToLtkj0040102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "");
	}
	
	public static void main(String[] args) {
		ToLtkj0040102 to = new ToLtkj0040102();
		String number = "01,02,03,04,05,06,07,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
