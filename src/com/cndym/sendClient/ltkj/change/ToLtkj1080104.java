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
public class ToLtkj1080104 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		if(number.length() == 1){
			number = "0" + number;
		}
		return number;
	}
	
	public static void main(String[] args) {
		ToLtkj1080104 to = new ToLtkj1080104();
		String number = "01"; 
		System.out.println(to.toSendNumberCode(number));
	}

}
