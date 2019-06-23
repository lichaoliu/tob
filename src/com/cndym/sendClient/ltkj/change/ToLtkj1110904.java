package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前二定投）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110904 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1110904 to = new ToLtkj1110904();
		String number = "02,03-04,05"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
