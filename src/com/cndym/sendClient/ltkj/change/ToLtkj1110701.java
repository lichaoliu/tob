package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任七单式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110701 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110701 to = new ToLtkj1110701();
		String number = "01,05,06,07,08,10,11;02,03,04,05,06,07,09"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
