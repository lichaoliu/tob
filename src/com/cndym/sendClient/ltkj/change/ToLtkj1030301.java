package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任三单式）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1030301 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1030301 to = new ToLtkj1030301();
		String number = "01,05,11;02,06,08"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
