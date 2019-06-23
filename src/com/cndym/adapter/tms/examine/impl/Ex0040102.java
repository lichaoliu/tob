package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0040102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0040102.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        int numCount = 7;
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0040102", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, numCount, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 17, -1);//号码个数的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);//验证蓝球是否有重复
            NumberAreaExamine.defaultNumberAraeExamine(num.split(tag), 1, 30, 2);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, tag);//  验证号码是否按升序排序
            int a = num.split(tag).length;
            if (a <= numCount) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
            temp += C(a, numCount);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0040102 ex0040102 = new Ex0040102();
        ex0040102.examina("10,11,12,13,14,15,16,18,19,20,21,22", 36);
        System.out.println("ok");
    }
}
