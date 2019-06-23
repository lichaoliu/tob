package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（前三定投）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1121004 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1121004 to = new ToLtkj1121004();
		String number = "02,03-04,05-06,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
