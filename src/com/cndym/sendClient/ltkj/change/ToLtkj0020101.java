package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (直选单式)
 * @author LN
 *
 */
@Component
public class ToLtkj0020101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0020101 to = new ToLtkj0020101();
		String number = "1,2,3;2,3,4;3,4,4;1,3,5;2,4,6"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
