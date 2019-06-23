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
public class Ex1080303 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1080303.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1080303", nums);
        for (String num : nums) {
            //校验格式
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
            String[] arr = num.split("@");
            String danNums = arr[0];
            String tuoNums = arr[1];
            NumberLengthExamine.defaultNumberLengthExamine(danNums, ",", 1, 0);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuoNums, ",", 1, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuoNums, ",", 11, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(danNums.split(","), 0, 9, 1);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(tuoNums.split(","), 0, 9, 1);//号码域验证
            NumberAreaExamine.defaultNumberAraeRxExamine(danNums, tuoNums, 2, ",");
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(danNums).append(",").append(tuoNums).toString(), ",", 0);

            int tuo = tuoNums.split(",").length;
            temp += tuo * 2;
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex1080303 ex1080303 = new Ex1080303();
        ex1080303.examina("1@2,3", 18);
    }

}
