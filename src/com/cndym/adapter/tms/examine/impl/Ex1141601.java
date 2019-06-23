package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.Utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克对子单选玩法
 */
@Component
public class Ex1141601 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1141601.class);

    @Override
    public void examina(String number, int item) {

    	 String[] nums = number.split(ztag);
         int temp = 0;
         NumberTicketCountExamine.defaulstNumberSortExamine("1141601", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
		 NumberRepeatExamine.defaultNumberRepeatExamine(number, ";", 0);//重复数字判断
         for (String num : nums) {
        	//判断是否为3位
             String[] numArray = num.split(",");
             if(numArray.length != 3){
            	 throw new CndymException("号码数不足3位");
             }
             
             //判断第3个数字是否为*
             if(!"*".equals(numArray[2])){
             	 throw new CndymException("投注的第3个号码不是*");
             }
             
             //校验前两位是否是1-13的2位数字
             String[] duis = {numArray[0],numArray[1]};
             NumberAreaExamine.defaultNumberAraeExamine(duis, 01, 13, 2);//号码域验证
             
             //校验前两位是否相等
             if(!numArray[0].equals(numArray[1])){
             	 throw new CndymException("投注的前两位不相等，不是对子");
             }
             temp ++;
         }
    	// 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    	
    	
        
     // 校验注数
        if (item != 1) {
        	logger.info("实际注数(1)不等于传入("+item+")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1141601 ex1141601 = new Ex1141601();
        ex1141601.examina("02,02,*", 1);
        System.out.println("ok");
    }

}
