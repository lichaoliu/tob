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
public class Ex1070801 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1070801.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1070801", nums);
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);

        }
        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1070801 ex0063001 = new Ex1070801();
        ex0063001.examina("01,02,03,04,06,07,08,09;01,02,03,04,06,09,10,11;01,02,03,04,06,09,10,11;01,02,03,04,06,09,10,11;01,02,03,04,06,09,10,11;01,02,03,04,06,09,10,11", 2);
        System.out.println("ok");
    }

}
