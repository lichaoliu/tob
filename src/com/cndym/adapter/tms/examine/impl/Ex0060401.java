package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060401 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex0060304.class);
    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
        
//        String[] nums = number.split(ztag);
//        int ticket = nums.length;
//        int ticketCount = getTicketCount("0060401");
//        if (ticket > ticketCount) {
//            throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
//        }
//        for (String num : nums) {
//            //校验格式
//            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 4, 0);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
//
//        }
//        //校验注数
//        if (item != ticket) {
//            throw new IllegalArgumentException("实际注数(" + ticket + ")不等于传入(" + item + ")");
//        }
    }

    public static void main(String[] args) {
        Ex0060401 ex0060102 = new Ex0060401();
        ex0060102.examina("1,2,3,4;1,2,3,4;1,2,3,4;1,2,3,4", 4);
        System.out.println("ok");
    }

}
