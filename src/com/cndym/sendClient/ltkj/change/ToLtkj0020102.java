package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 福彩3D (直选复式)
 * @author LN
 *
 */
@Component
public class ToLtkj0020102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0020102 to = new ToLtkj0020102();
		String number = "1,2,34"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
