package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（任五复式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1070502 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1070502 to = new ToLtkj1070502();
		String number = "01,02,06,08,09,10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
