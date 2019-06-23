package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任四胆拖）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110403 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110403 to = new ToLtkj1110403();
		String number = "01,04,06@02,03"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
