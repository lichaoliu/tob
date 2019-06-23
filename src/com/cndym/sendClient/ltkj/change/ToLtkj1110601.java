package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任六单式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110601 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110601 to = new ToLtkj1110601();
		String number = "01,05,06,07,08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
