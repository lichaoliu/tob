package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（前三组选单式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1071301 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1071301 to = new ToLtkj1071301();
		String number = "01,05,08;03,06,07;02,08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
