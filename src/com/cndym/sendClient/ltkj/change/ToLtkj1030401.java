package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任四单式）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1030401 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1030401 to = new ToLtkj1030401();
		String number = "01,05,10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
