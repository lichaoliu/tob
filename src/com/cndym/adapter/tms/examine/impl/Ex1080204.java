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
 * 作者：邓玉明
 * 时间：11-4-30 下午1:36
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class Ex1080204 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1080204.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1080204", nums);
        for (String n : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(n, 2, 25);//号码域验证
            int num = Integer.parseInt(n);
            for (int i = 0; i < 10; i++) {
                for (int j = i; j < 10; j++) {
                    for (int k = j; k < 10; k++) {
                        if (i == k && k == j) {
                            continue;
                        }
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
        Ex1080204 ex1080204 = new Ex1080204();
        ex1080204.examina("4", 4);
        System.out.println("ok");
    }
}
