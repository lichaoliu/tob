package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前三定投）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1111004 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1111004 to = new ToLtkj1111004();
		String number = "02,03-04,05-06,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
