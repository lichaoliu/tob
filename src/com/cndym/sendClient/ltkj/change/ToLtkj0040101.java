package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 七乐彩 (单式)
 * @author LN
 *
 */
@Component
public class ToLtkj0040101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0040101 to = new ToLtkj0040101();
		String number = "01,02,10,16,17,21,22;03,12,17,18,22,24,30"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
