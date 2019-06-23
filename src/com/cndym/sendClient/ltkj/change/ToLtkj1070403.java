package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 山东11选5（任四胆拖）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1070403 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1070403 to = new ToLtkj1070403();
		String number = "01,04,06@02,03"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
