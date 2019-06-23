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
public class Ex1130302 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1130302.class);

    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
        /*String tag = ",";
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1130302", nums);
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 2, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 13, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(tag), 1, 12, 2);//号码域验证
            *//*NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序*//*
            NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);
            int a = num.split(",").length;
            temp += C(a, 2);
        }
        //校验注数
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }*/
    }

    public static void main(String[] args) {
        Ex1130302 ex1080402 = new Ex1130302();
        ex1080402.examina("01,09,11;12,09,10", 6);
        System.out.println("ok");
    }
}
