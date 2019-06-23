package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（前三复式）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1071102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
					   .replaceAll("#", "-");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1071102 to = new ToLtkj1071102();
		String number = "01,02#08,09#10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
