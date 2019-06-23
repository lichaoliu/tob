/**
 * 
 */
package com.cndym.sendClient.ltkj.util;

/**
 * @author 朱林虎
 * 
 */
public class BonusNumberFormat {
	public static String format(String lotteryCode, String bonusNumber) {
		return bonusNumber.replace("//","#").replace("/", ",");
	}
	
	public static void main(String args[]) {
		String aa="05/08/12/24/34//07/10";
		System.out.print(format("113",aa));
	}
}
