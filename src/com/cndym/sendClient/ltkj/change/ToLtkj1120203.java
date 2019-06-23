package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（任二胆拖）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120203 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1120203 to = new ToLtkj1120203();
		String number = "01@02,05,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
