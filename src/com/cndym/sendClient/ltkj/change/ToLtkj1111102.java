package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前二组选复式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1111102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1111102 to = new ToLtkj1111102();
		String number = "01,02,03,06,08,09,10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
