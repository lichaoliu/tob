package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前二组选单式）
 * @author 朱林虎
 *
 */
@Component
public class ToLtkj1031101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll(";", "");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1031101 to = new ToLtkj1031101();
		String number = "01,05;06,07;08,10"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
