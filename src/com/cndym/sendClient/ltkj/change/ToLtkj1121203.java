package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（前三组选胆拖）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1121203 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1121203 to = new ToLtkj1121203();
		String number = "01@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
