package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任二胆拖）
 * 
 * @author 程禄元
 */
@Component
public class ToLtkj1050203 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
	
		return number.replaceAll(",", "").replaceAll("@", "-")+"-01";
	}

	public static void main(String[] args) {
		ToLtkj1050203 to=new ToLtkj1050203();
		String number="01@04,05,09";
		System.out.println(to.toSendNumberCode(number));

	}

}
