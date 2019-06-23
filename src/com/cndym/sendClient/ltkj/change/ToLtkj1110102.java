package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任一复式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110102 to = new ToLtkj1110102();
		String number = "01,02,03,04,05,06,07,08,09,10,11,12"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
