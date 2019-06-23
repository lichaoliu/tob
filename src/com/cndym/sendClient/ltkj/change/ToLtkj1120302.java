package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任三复式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120302 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120302 to = new ToLtkj1120302();
		String number = "01,02,06,08,09"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
