package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (组六和值)
 * @author LN
 *
 */
@Component
public class ToLtkj0020304 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "");
	}
	
	public static void main(String[] args) {
		ToLtkj0020304 to = new ToLtkj0020304();
		String number = "26"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
