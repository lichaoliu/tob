package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任四复式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1070402 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1070402 to = new ToLtkj1070402();
		String number = "01,02,06,08,09,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
