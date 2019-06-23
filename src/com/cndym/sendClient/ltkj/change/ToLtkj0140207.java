package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3 (二同号复选)11-22-33   1,1,*
 * @author lgl
 *
 */
@Component
public class ToLtkj0140207 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
			return number.replaceAll(",", "").replace("*", "").replace(";", "-");
	}
	public static void main(String[] args) {
		ToLtkj0140207 to = new ToLtkj0140207();
		String number = "1,1,*;2,2,*;5,5,*;6,6,*;3,3,*"; 
		System.out.println(to.toSendNumberCode(number));
	}
}
