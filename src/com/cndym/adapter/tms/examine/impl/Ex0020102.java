package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020102.class);

    @Override
    public void examina(String number, int item) {
        logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);

//		String[] nums = number.split(ztag);
//		int temp = 0;
//		NumberTicketCountExamine.defaulstNumberSortExamine("0020102", nums);
//		for (String num : nums) {
//		NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
//		NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
//		String[] arr = num.split(",");
//		String aNums = arr[0];
//		String bNums = arr[1];
//		String cNums = arr[2];
//
//		int a = aNums.length();
//		int b = bNums.length();
//		int c = cNums.length();
//		if (a == 1 && b == 1 && c == 1) {
//		throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
//		}
//		for (String n : arr) {
//		String[] ns = n.split("");
//		for (int i = 1; i < ns.length; i++) {
//		for (int j = i + 1; j < ns.length; j++) {
//		if (ns[i].equals(ns[j])) {
//		throw new IllegalArgumentException("号码有重复");
//		}
//		}
//		}
//		}
//
//		NumberSortExamine.defaulstNumberSortExamine(aNums);
//		NumberSortExamine.defaulstNumberSortExamine(bNums);
//		NumberSortExamine.defaulstNumberSortExamine(cNums);
//		temp += a * b * c;
//		}
//
//		if (temp != item) {
//		logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
//		throw new CndymException(ErrCode.E8116);
//		}
    }

    public static void main(String[] args) {
        Ex0020102 ex0020101 = new Ex0020102();
        ex0020101.examina("1234,1,123", 12);
        System.out.println("ok");
    }

}
