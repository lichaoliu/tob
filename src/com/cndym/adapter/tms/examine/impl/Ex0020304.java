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
public class Ex0020304 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020304.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020304", nums);
        int temp = 0;
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num, 3, 24);//号码域验证
            String d3z6hz = BumberNoteTheNumberOfExamine.getD3z6hz().get(num);
            temp += new Integer(d3z6hz);
        }
        //和值计算注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0020304 ex0020304 = new Ex0020304();
        ex0020304.examina("3", 1);
        System.out.println("ok");
    }
}
