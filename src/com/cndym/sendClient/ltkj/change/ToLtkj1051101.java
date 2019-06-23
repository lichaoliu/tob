package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 *广东11选5（前三单式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1051101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll("#", "")
		               .replaceAll(";", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1051101 to = new ToLtkj1051101();
		String number = "01#10#11;02#10#11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
