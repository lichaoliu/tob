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
public class ToLtkj3020101 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replace("*", "")
		 		     .replace(";", "-");
	}
	
	public static void main(String[] args){
		//0*1*2*3*1*2*3*1
		//01230123
		System.out.println(new ToLtkj3020101().toSendNumberCode("0*1*2*3*1*2*3*1;1*0*2*2*1*2*3*1"));
	}
}
