package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（前三组选复式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1051302 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "") + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1051302 to = new ToLtkj1051302();
		String number = "01,02,03,06,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
