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
public class ToLtkj3030102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replace("*", "-")
					 .replace("4", "*");
	}
	
	public static void main(String[] args){
		//4*301*4*30*1*4*3*4*1*3*1*1*3*4
		//01-*-0-1-3-*-*-*-03-013-*-0-1-1
		System.out.println(new ToLtkj3030102().toSendNumberCode("4*301*4*30*1*4*3*4*1*3*1*1*3*4"));
	}
}
