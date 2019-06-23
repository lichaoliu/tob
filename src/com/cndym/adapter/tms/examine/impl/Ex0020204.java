package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020204 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020204.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020204", nums);
        int temp = 0;
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num, 1, 26);//号码域验证
            String d3z3hz = BumberNoteTheNumberOfExamine.getD3z3hz().get(num);
            temp += new Integer(d3z3hz);
        }
        //和值计算注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0020204 ex0020204 = new Ex0020204();
        ex0020204.examina("4", 10);
        System.out.println("ok");
    }
}