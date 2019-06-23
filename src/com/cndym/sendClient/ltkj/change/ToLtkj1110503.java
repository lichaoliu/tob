package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任五胆拖）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110503 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110503 to = new ToLtkj1110503();
		String number = "01,04,06,08@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
