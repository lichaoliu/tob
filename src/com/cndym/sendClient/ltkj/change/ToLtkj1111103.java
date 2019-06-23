package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 新疆11选5（前二组选胆拖）
 * @author 梁桂立
 *
 */
@Component
public class ToLtkj1111103 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1111103 to = new ToLtkj1111103();
		String number = "01@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
