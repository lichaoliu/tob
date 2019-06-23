package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (组六单式)
 * @author LN
 *
 */
@Component
public class ToLtkj0020301 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0020301 to = new ToLtkj0020301();
		String number = "1,2,3;2,3,5;3,4,5;1,3,5;2,4,9"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
