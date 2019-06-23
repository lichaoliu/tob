package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0050101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0050101.class);


    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0050101", nums);
        for (String num : nums) {
            NumberTagExamine.ssqNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 2, 0);//号码个数的验证
            String[] arr = num.split("#");
            String red = arr[0];
            String blue = arr[1];
            NumberLengthExamine.defaultNumberLengthExamine(red, tag, 6, 0);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(blue, tag, 1, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(red.split(tag), 0, 9, 1);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(blue.split(tag), 1, 12, 2);//号码域验证

        }

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex0050101 ex0050101 = new Ex0050101();
        ex0050101.examina("1,2,3,4,5,3#12;1,2,3,4,5,3#12;1,2,3,4,5,3#12;1,2,3,4,5,3#12", 4);
        System.out.println("ok");
    }
}
