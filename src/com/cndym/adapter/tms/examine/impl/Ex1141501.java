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
 * 山东快乐扑克对子包选玩法
 */
@Component
public class Ex1141501 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1141501.class);

    @Override
    public void examina(String number, int item) {

       if(Utils.isEmpty(number)){
    	   throw new IllegalArgumentException("待验证的号码串为空");
       }
       if(!"00".equals(number)){
    	   throw new IllegalArgumentException("待验证的号码串不为00");
       }
    	// 校验注数
        if (item != 1) {
            logger.info("实际注数(1)不等于传入("+item+")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1141501 ex1141501 = new Ex1141501();
        ex1141501.examina("00", 1);
        System.out.println("ok");
    }

}
