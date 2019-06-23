package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（任七胆拖）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1030703 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-01";
	}
	
	public static void main(String[] args) {
		ToLtkj1030703 to = new ToLtkj1030703();
		String number = "01,04,06,08,09,11@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
