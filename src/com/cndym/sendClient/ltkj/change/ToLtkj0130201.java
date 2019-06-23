package com.cndym.sendClient.ltkj.change;

import org.springframework.stereotype.Component;

import com.cndym.sendClient.IChange;

/**
 * 江西快3(二同号单式)
 * @author LN
 *
 */
@Component
public class ToLtkj0130201 implements IChange {

	@Override
	public String toSendNumberCode(String number) {
	String result="";
	  String[] bbArray=number.split(";");
	  for(int index=0;index<bbArray.length;index++){
		  if(index>0){
			  result=result+"-"; 
		  }
		  String first=  bbArray[index].replaceFirst(",", "");
		  String last=first.replace(",", "$");
		  result=result+last;
	  }
		return result;
	}
	
	public static void main(String[] args) {
		ToLtkj0130201 to = new ToLtkj0130201();
		String number = "1,1,4;2,2,6;2,3,3;4,4,5;1,5,5"; 
		System.out.println(to.toSendNumberCode(number));
	}
}

