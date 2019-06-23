package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克豹子单选玩法
 */
@Component
public class Ex1141401 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1141401.class);

    @Override
    public void examina(String number, int item) {

    	 String[] nums = number.split(ztag);
         int temp = 0;
         NumberTicketCountExamine.defaulstNumberSortExamine("1141401", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
		 NumberRepeatExamine.defaultNumberRepeatExamine(number, ";", 0);//重复数字判断
         for (String num : nums) {
        	//校验格式
             NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 01, 13, 2);//号码域验证
             //判断三个数字是否相同
             String[] numArray = num.split(",");
             if(numArray.length != 3){
            	 throw new CndymException("号码数不足3位");
             }
             if(!numArray[0].equals(numArray[1]) || !numArray[0].equals(numArray[2]) || !numArray[1].equals(numArray[2])){
            	 throw new CndymException("投注的3个号码不相同");
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
        Ex1141401 ex1141401 = new Ex1141401();
        ex1141401.examina("02,02,02", 1);
        System.out.println("ok");
    }

}
