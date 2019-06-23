package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任五单式）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1030501 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1030501 to = new ToLtkj1030501();
		String number = "01,05,06,07,08;02,05,06,08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
