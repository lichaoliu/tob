package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任六胆拖）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110603 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110603 to = new ToLtkj1110603();
		String number = "01,04,06,08,09@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
