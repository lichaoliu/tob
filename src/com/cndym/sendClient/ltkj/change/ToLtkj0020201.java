package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (组三单式)
 * @author LN
 *
 */
@Component
public class ToLtkj0020201 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0020201 to = new ToLtkj0020201();
		String number = "1,2,2;2,3,3;3,4,4;5,5,6;2,4,4"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
