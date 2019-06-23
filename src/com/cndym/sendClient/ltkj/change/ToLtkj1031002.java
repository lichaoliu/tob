package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前三复式）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1031002 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
					   .replaceAll("#", "-");
		return number + "-03";
	}
	
	public static void main(String[] args) {
		ToLtkj1031002 to = new ToLtkj1031002();
		String number = "01,02#08,09#10,11"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
