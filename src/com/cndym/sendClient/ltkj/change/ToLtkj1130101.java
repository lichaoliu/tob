/**
 * 
 */
package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**           
 * 创建人：朱林虎    
 * QQ:279562102
 * Email:skysea361@163.com
 * 创建时间：2015-2-13 上午11:05:15      
 */
@Component
public class ToLtkj1130101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		// TODO Auto-generated method stub
		return number.replace(",", "")
					 .replace("#", "")
					 .replace(";", "-")
					 + "-0";
	}
	
	public static void main(String[] args) {
		ToLtkj1130101 to = new ToLtkj1130101();
		String number = "07,08,10,14,20,30,33#07,09,12"; 
		System.out.println(to.toSendNumberCode(number));
	}

}
