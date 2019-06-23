package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Ex0090609 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0090609.class);

    @Override
    public void examina(String number, int item) {
        logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);

//        String[] nums = number.split(ztag);
//        NumberTicketCountExamine.defaulstNumberSortExamine("0090609", nums);
//        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证是否有重复
//
//        int ticket = 0;
//        for (String num : nums) {
//            NumberLengthExamine.defaultNumberLengthExamine(num, 1, 0);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num, 0, 9, 1);//号码域验证
//            ticket += 10;
//        }
//
//        if (ticket != item) {
//        	throw new CndymException(ErrCode.E8116);
//        }
    }

    public static void main(String[] args) {
        Ex0090609 ex = new Ex0090609();
        ex.examina("7;8;9", 30);
        System.out.println("ok");
    }
}
