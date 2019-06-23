package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任八单式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120801 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120801 to = new ToLtkj1120801();
		String number = "01,05,06,07,08,09,10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
