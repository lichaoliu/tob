package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 七乐彩 (胆拖投注)
 * @author LN
 *
 */
@Component
public class ToLtkj0040103 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll(",", "").replace("@", "*");
	}
	
	public static void main(String[] args) {
		ToLtkj0040103 to = new ToLtkj0040103();
		String number = "03@04,07,10,12,13,15,16,17,19,20,22,24,26,28,29,30"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
