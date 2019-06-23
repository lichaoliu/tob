package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (直选和值)
 * @author LN
 *
 */
@Component
public class ToLtkj0020104 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "");
	}
	
	public static void main(String[] args) {
		ToLtkj0020104 to = new ToLtkj0020104();
		String number = "27"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
