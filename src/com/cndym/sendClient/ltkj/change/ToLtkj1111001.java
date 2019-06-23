package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前三单式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1111001 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll("#", "")
		               .replaceAll(";", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1111001 to = new ToLtkj1111001();
		String number = "01#10#11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
