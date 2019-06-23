package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任六胆拖）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120603 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120603 to = new ToLtkj1120603();
		String number = "01,04,06,08,09@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
