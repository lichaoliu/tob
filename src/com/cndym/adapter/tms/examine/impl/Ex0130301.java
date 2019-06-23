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

/**
 * 三同号单选:是指从所有相同的三个号码（111、222、…、666）中任意选择一组号码进行投注。 
 * us:1,1,1
 * lt:111
 */

@Component
public class Ex0130301 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0130301.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0130301", nums);
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        for (String num : nums) {
        	System.out.println(num);
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 6, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 1);//验证号码是否重复
        }

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }


    public static void main(String[] ages) {
        Ex0130301 ex0070301 = new Ex0130301();
        ex0070301.examina("4,4,4;1,1,1;2,2,2;3,3,3;5,5,5;6,6,6", 6);
    }
}
