package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 双色球 (胆拖投注)
 * @author LN
 *
 */
@Component
public class ToLtkj0010103 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replaceAll("@", "*").replace(",","").replace("#","-");
	}
	
	public static void main(String[] args) {
		ToLtkj0010103 to = new ToLtkj0010103();
		//0102*0305060708-01
		String number = "01,02@03,05,06,07,08#01"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
