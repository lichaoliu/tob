package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0050102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0050102.class);


    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0050102", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.ssqNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 2, 0);//号码个数的验证
            String[] arr = num.split("#");
            String red = arr[0];
            String blue = arr[1];
            NumberLengthExamine.defaultNumberLengthExamine(red, tag, 6, 0);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(blue, tag, 1, 0);//号码个数的验证

//        NumberAreaExamine.defaultNumberAraeExamine(red.split(tag), 0, 9, 1);//号码域验证
//        NumberAreaExamine.defaultNumberAraeExamine(blue.split(tag), 1, 12, 2);//号码域验证

            int count = 1;
            int c = 0;
            String[] leftArr = red.split(",");
            for (String s : leftArr) {
                c += count * s.length();
                count = count * s.length();
            }
            int blueCount = blue.length() / 2;
            temp += count * blueCount;
            if (c <= 6 && blueCount <= 1) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex0050102 ex0050102 = new Ex0050102();
        ex0050102.examina("12,3,0,5,6,09#0809;12,3,0,5,6,09#0809", 8);
        System.out.println("ok");
    }
}
