package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.Utils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克顺子包选玩法
 */
@Component
public class Ex1141001 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1141001.class);

    @Override
    public void examina(String number, int item) {

		 String[] nums = number.split(ztag);
		 NumberTicketCountExamine.defaulstNumberSortExamine("1141001", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
		 NumberRepeatExamine.defaultNumberRepeatExamine(number, ";", 0);//重复数字判断
	     int temp = 0;
	     for (String num : nums) {
	         //校验格式
	    	 NumberSortExamine.defaulstNumberSortExamine(num, ",");//投注号码必须按照升序排列
	         NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 0);//号码个数的验证，必须等于1
	         NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 21, 24, 2);//号码域验证(21-24的两位数字)
	         NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//是否存在重复的数字
	         temp ++;
	     }
    	// 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + temp + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1141001 ex1141001 = new Ex1141001();
        ex1141001.examina("24", 1);
        System.out.println("ok");
    }

}
