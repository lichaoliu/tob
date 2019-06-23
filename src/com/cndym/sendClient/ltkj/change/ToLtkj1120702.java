package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任七复式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120702 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120702 to = new ToLtkj1120702();
		String number = "01,02,03,06,08,09,10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
