package com.cndym.sendClient.ltkj.change;

import com.cndym.sendClient.IChange;
import org.springframework.stereotype.Component;

/**
 * 青海11选5（前三复式）
 * @author 李娜
 *
 */
@Component
public class ToLtkj1121002 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
					   .replaceAll("#", "-");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1121002 to = new ToLtkj1121002();
		String number = "01,02#08,09#10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
