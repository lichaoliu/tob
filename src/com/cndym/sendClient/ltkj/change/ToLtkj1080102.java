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
public class ToLtkj1080102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replace(",", "-");
	}
	
	public static void main(String[] args) {
		ToLtkj1080102 to = new ToLtkj1080102();
		String number = "12,23,2"; 
		System.out.println(to.toSendNumberCode(number));
	}

}
