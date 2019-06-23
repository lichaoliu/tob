package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（前二组选单式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1121101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1121101 to = new ToLtkj1121101();
		String number = "01,05;06,07;08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
