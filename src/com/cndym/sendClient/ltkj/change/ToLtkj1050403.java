package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任四胆拖）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1050403 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1050403 to = new ToLtkj1050403();
		String number = "01,04,06@02,03"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
