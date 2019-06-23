package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（前三单式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1121001 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll("#", "")
		               .replaceAll(";", "");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1121001 to = new ToLtkj1121001();
		String number = "01#10#11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
