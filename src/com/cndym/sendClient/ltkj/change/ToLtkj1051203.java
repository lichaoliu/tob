package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 广东11选5（前二组选胆拖）
 * @author 程禄元
 *
 */
@Component
public class ToLtkj1051203 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		number = number.replaceAll(",", "")
		               .replaceAll("@", "-");
		return number + "-02";
	}
	
	public static void main(String[] args) {
		ToLtkj1051203 to = new ToLtkj1051203();
		String number = "01@02,03,07"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
