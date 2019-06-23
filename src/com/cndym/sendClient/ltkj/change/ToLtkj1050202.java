package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（任二复式）
 * 
 * @author 程禄元
 * 
 */
@Component
public class ToLtkj1050202 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "")+"-01";
	}

	public static void main(String[] args) {
		ToLtkj1030202 to = new ToLtkj1030202();
		String number = "01,02,06,08"; 
		System.out.println(to.toSendNumberCode(number));

	}

}
