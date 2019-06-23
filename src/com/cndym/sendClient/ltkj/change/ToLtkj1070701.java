package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（任七单式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1070701 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1070701 to = new ToLtkj1070701();
		String number = "01,05,06,07,08,10,11;02,03,04,05,06,07,09"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
