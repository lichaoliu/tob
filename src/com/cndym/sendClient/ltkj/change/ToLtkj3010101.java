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
 * 创建时间：2015-2-27 下午03:06:48      
 */
@Component
public class ToLtkj3010101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replace("*", "")
		 		     .replace(";", "-");
	}
	
	public static void main(String[] args){
		//3*1*1*3*1*1*3*1*1*3*1*1*3*0
		//01301301301313
		System.out.println(new ToLtkj3010101().toSendNumberCode("3*1*1*3*1*1*3*1*1*3*1*1*3*0;0*1*3*3*1*3*3*1*1*3*1*1*3*0"));
	}
}
