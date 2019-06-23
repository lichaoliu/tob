package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任三单式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120301 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120301 to = new ToLtkj1120301();
		String number = "01,05,11;02,06,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
