package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任六胆拖）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1050603 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1050603 to = new ToLtkj1050603();
		String number = "01,04,06,08,09@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
