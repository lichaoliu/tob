package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（前二单式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1120901 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll("#", "")
		               .replaceAll(";", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1120901 to = new ToLtkj1120901();
		String number = "01#11;02#06"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
