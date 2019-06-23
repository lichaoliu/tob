package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
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
public class Ex1050603 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1050603.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1050603", nums);
        for (String num : nums) {
            //校验格式
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
            String[] arr = num.split("@");
            NumberLengthExamine.defaultNumberLengthExamine(arr[0], ",", 0, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(arr[0], ",", 6, -1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(arr[1], ",", 1, 1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 11, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 11, 2);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 0);
            int dan = arr[0].split(",").length;
            int tuo = arr[1].split(",").length;
            temp += C(tuo, 6 - dan);
        }

        if (temp <= 1) {
            throw new IllegalArgumentException("不是合法胆拖号码(" + number + ")");
        }

        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }
    
}
