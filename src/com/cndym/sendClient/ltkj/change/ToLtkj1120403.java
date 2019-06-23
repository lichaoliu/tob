package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任四胆拖）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120403 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120403 to = new ToLtkj1120403();
		String number = "01,04,06@02,03"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
