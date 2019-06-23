package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任四复式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120402 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120402 to = new ToLtkj1120402();
		String number = "01,02,06,08,09,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
