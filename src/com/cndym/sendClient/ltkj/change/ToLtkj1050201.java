package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任二单式）
 * @author 程禄元
 *
 */

@Component
public class ToLtkj1050201 implements IChange {
	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
	               .replaceAll(";", "");
	return number + "-01";
	}
	
	public static void main(String[] args) {
		
		ToLtkj1050201 to=new ToLtkj1050201();
		String number="02,03;03,04";
		
		System.out.println(to.toSendNumberCode(number));

	}


}
