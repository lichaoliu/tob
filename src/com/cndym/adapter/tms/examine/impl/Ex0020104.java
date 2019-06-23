package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020104 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020104.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("0020104", nums);
        for (String n : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(n, 0, 27);//号码域验证
            int num = Integer.parseInt(n);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < 10; k++) {
                        if ((i + k + j) == num) {
                            temp++;
                        }
                    }
                }
            }
        }

        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }


    }

    public static void main(String[] args) {
        Ex0020104 ex0020101 = new Ex0020104();
        ex0020101.examina("0", 1);
        System.out.println("ok");
    }
}
