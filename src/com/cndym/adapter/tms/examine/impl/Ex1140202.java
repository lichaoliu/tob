package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克任选二复式玩法
 */
@Component
public class Ex1140202 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1140202.class);

    @Override
    public void examina(String number, int item) {
    	
    	String[] nums = number.split(ztag);
    	int temp = 0;
    	NumberTicketCountExamine.defaulstNumberSortExamine("1140202", nums);
        for(String num : nums){
	        //校验格式
	        NumberTagExamine.commaNumberTagExamine(num);//号码中分隔是否存在的验证
	        NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 1);//号码个数的验证，必须大于2
	        NumberLengthExamine.defaultNumberLengthExamine(num, ",", 14, -1);//号码个数的验证，必须小于14
	        NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 13, 2);//号码域验证，必须为01-13的两位数字
	        NumberSortExamine.defaulstNumberSortExamine(num, ",");//投注号码必须按照升序排列
	        NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);
	        int len = num.split(",").length;
	        temp += C(len, 2);
        }
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1140202 ex1140202 = new Ex1140202();
        ex1140202.examina("01,02,03,04,05,06,07,08,09,10,11,12,13",78);
        System.out.println("ok");
    }

}
