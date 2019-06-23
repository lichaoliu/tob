package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (三同号单选) 111 111-222-333  1,1,1;2,2,2
 * @author lgl
 *
 */
@Component
public class ToLtkj0140301 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace(";", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj0140301 to = new ToLtkj0140301();
		String number = "1,1,1;2,2,2"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
