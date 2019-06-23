package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任三复式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1050302 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1050302 to = new ToLtkj1050302();
		String number = "01,02,06,08,09"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
