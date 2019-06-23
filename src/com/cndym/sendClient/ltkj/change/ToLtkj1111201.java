package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前三组选单式）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1111201 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1111201 to = new ToLtkj1111201();
		String number = "01,05,08;03,06,07;02,08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
