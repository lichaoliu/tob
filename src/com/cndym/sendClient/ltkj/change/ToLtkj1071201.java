package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（前二组选单式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1071201 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1071201 to = new ToLtkj1071201();
		String number = "01,05;06,07;08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
