package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (组三复式)
 * @author LN
 *
 */
@Component
public class ToLtkj0020202 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "");
	}
	
	public static void main(String[] args) {
		ToLtkj0020202 to = new ToLtkj0020202();
		String number = "0,1,2,3,4,5,6,7,8"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
