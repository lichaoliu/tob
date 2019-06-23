package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（前一单式）
 * @author 程禄元
 *
 */

@Component
public class ToLtkj1070101 implements IChange {
	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(";", "");
	return number + "-01";
	}
	
	public static void main(String[] args) {
		
		ToLtkj1070101 to=new ToLtkj1070101();
		String number="02;03;03;04";
		
		System.out.println(to.toSendNumberCode(number));

	}


}
