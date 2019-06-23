package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任二胆拖）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110203 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110203 to = new ToLtkj1110203();
		String number = "01@02,05,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
