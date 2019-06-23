package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 三连号通选:是指对所有三个相连的号码（仅限：123、234、345、456）进行投注
 * us:7,8,9
 * lt:123 
 */

@Component
public class Ex0130608 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0130608.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0130608", nums);
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            if (!"7,8,9".equals(num)) {
                throw new IllegalArgumentException("号码(" + num + ")不为7,8,9");
            }
        }

        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0130608 ex0070501 = new Ex0130608();
        ex0070501.examina("7,8,9", 1);
    }
}
