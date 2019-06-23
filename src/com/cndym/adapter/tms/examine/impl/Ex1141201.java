package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克顺子单选玩法
 */
@Component
public class Ex1141201 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1141201.class);

    @Override
    public void examina(String number, int item) {

    	 String[] nums = number.split(ztag);
    	 NumberTicketCountExamine.defaulstNumberSortExamine("1141201", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
		 NumberRepeatExamine.defaultNumberRepeatExamine(number, ";", 0);//重复数字判断
         int temp = 0;
         for (String num : nums) {
        	//校验格式
         	String[] numbers = num.split(",");
         	if(numbers.length != 3){
         		throw new IllegalArgumentException("山东快乐扑克顺子单选数字长度为3个，实际为" + numbers.length + "个");
         	}
         	NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 01, 13, 2);//号码域验证
         	NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//重复数字判断
             //判断三个数字是否连续
         	int first = Integer.parseInt(numbers[0]);
         	int second = Integer.parseInt(numbers[1]);
         	int third = Integer.parseInt(numbers[2]);
         	
         	if(!((second - first == 1) && (third -second == 1))){
         		if(!(first == 12 && second == 13 && third == 1)){
         			throw new IllegalArgumentException("山东快乐扑克顺子单选的3个数字（"+number+"）不连续");
             	}
         	}
             temp ++;
         }
    	// 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    	
    }

    public static void main(String[] args) {
        Ex1141201 ex1141201 = new Ex1141201();
        ex1141201.examina("12,13,01", 1);
        System.out.println("ok");
    }

}
