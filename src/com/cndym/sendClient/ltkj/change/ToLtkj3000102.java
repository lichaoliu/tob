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
public class ToLtkj3000102 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
		return number.replace("*", "-");
	}
	
	public static void main(String[] args){
		//30*1*1*3*10*1*3*1*1*3*1*1*3*0
		//013-01-03-0-1-3-01-0-1-0-13-10-03-0
		System.out.println(new ToLtkj3000102().toSendNumberCode("30*1*1*3*10*1*3*1*1*3*1*10*3*0"));
	}
}
