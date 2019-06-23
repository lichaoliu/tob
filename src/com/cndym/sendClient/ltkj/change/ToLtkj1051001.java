package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（前二单式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1051001 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll("#", "")
		               .replaceAll(";", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1051001 to = new ToLtkj1051001();
		String number = "01#11;02#06"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
