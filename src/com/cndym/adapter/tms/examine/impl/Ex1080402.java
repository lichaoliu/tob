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
public class Ex1080402 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1080402.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        //TODO 我们这边的组六复式相当于大赢家那边的组六包号
        NumberTicketCountExamine.defaulstNumberSortExamine("1080402", nums);
        int temp = 0;
        for (String n : nums) {
            NumberTagExamine.commaNumberTagExamine(n);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(n, ",", 3, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(n, ",", 10, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(n.split(","), 0, 9, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(n);//重复检查
            //校验注数
            int num = n.split(",").length;
            temp += C(num, 3);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1080402 ex1080402 = new Ex1080402();
        ex1080402.examina("1,2,3", 4);
        System.out.println("ok");
    }
}
