package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任三复式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110302 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110302 to = new ToLtkj1110302();
		String number = "01,02,06,08,09"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
