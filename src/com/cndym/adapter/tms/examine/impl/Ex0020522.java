package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020522 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020522.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020522", nums);
        for (String n : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(n, 0, 9, 1);//号码域验证
        }

        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }


    }

    public static void main(String[] args) {
        Ex0020522 ex0020101 = new Ex0020522();
        ex0020101.examina("2", 1);
        System.out.println("ok");
    }
}
