package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（任三胆拖）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1050303 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1050303 to = new ToLtkj1050303();
		String number = "01,11@02,03,05,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
