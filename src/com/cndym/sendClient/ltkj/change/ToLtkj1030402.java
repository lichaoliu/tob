package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任四复式）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1030402 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1030402 to = new ToLtkj1030402();
		String number = "01,02,06,08,09,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
