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
public class Ex1060802 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1060802.class);

    @Override
    public void examina(String number, int item) {
		logger.info("该玩法暂不支持");
		throw new CndymException(ErrCode.E8117);
		
//        String[] nums = number.split(ztag);
//        NumberTicketCountExamine.defaulstNumberSortExamine("1060802", nums);
//        int temp = 0;
//        for (String num : nums) {
//            //校验格式
//            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, 1);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);//号码域验证
//            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);
//            int len = num.split(",").length;
//            temp += C(len, 8);
//        }
//        if (temp != item) {
//            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
//            throw new CndymException(ErrCode.E8116);
//        }
    }


    public static void main(String[] ages) {
        Ex1060802 ex1070802 = new Ex1060802();
        ex1070802.examina("01,02,03,04,05,06,07,08,09,10,11,12", 1);
    }
}
