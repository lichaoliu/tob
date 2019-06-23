package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Ex0090204 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0090204.class);

    @Override
    public void examina(String number, int item) {
        logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
//    	String[] nums = number.split(ztag);
//		NumberTicketCountExamine.defaulstNumberSortExamine("0090204", nums);
//    	NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证是否有重复
//
//		int ticket = 0;
//		for (String num : nums) {
//			NumberAreaExamine.defaultNumberAraeExamine(num, 0, 18);//号码域验证
//			String ssc2xzxhz = BumberNoteTheNumberOfExamine.getSsc2xzxhz().get(num);
//			ticket += new Integer(ssc2xzxhz);
//		}
//
//        //校验注数
//		if (item != ticket) {
//			throw new CndymException(ErrCode.E8116);
//		}
    }

    public static void main(String[] args) {
        Ex0090204 ex0060204 = new Ex0090204();
        ex0060204.examina("0;18", 2);
        System.out.println("ok");
    }
}
