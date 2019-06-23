package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

@Component
public class Ex0090604 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0090604.class);

    @Override
    public void examina(String number, int item) {
        logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);

//    	String[] nums = number.split(ztag);
//		NumberTicketCountExamine.defaulstNumberSortExamine("0090604", nums);
//    	NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证是否有重复
//
//    	int ticket = 0;
//		for (String num : nums) {
//			NumberAreaExamine.defaultNumberAraeExamine(num, 0, 18);//号码域验证
//			String ssc2xzxhz = BumberNoteTheNumberOfExamine.getSsc2xzuxhz().get(num);
//			ticket += new Integer(ssc2xzxhz);
//		}
//		//和值计算注数
//		if (item != ticket) {
//			throw new CndymException(ErrCode.E8116);
//		}
    }

    public static void main(String args[]) {
        Ex0090604 ex = new Ex0090604();
        ex.examina("4;5", 6);
        System.out.println("ok");
    }
}
