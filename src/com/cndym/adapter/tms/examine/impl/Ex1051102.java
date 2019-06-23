package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：邓玉明
 * 时间：11-4-30 下午1:36
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class Ex1051102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1051102.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1051102", nums);
        for (String num : nums) {
            NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);//验证号码是否有重复
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 3, 1);//号码个数的验证
            String[] n = num.split(tag);
            NumberAreaExamine.defaultNumberAraeExamine(n, 1, 11, 2);//号码域验证
            temp += P(n.length);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex1051102 ex1051102 = new Ex1051102();
        ex1051102.examina("05,03,02,06", 24);
    }
}
