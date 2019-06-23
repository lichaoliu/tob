package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任一复式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120102 to = new ToLtkj1120102();
		String number = "01,02,03,04,05,06,07,08,09,10,11,12"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
