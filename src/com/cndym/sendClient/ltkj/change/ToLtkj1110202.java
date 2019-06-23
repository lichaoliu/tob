package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任二复式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1110202 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1110202 to = new ToLtkj1110202();
		String number = "01,02,06,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
