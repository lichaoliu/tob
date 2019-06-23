package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任七胆拖）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1050703 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1050703 to = new ToLtkj1050703();
		String number = "01,04,06,08,09,11@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
