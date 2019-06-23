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
public class Ex1080403 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1080403.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1080403", nums);
        int temp = 0;
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, "@", 2, 0);//号码个数的验证
            String dans = num.split("@")[0];
            String tuos = num.split("@")[1];
            NumberLengthExamine.defaultNumberLengthExamine(dans, ",", 0, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(dans, ",", 3, -1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuos, ",", 0, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuos, ",", 11, -1);//号码个数的验证

            NumberLengthExamine.defaultNumberLengthExamine(dans + "," + tuos, ",", 3, 1);//号码个数的验证

            String[] tuoNums = tuos.split(",");
            String[] danNums = dans.split(",");
            NumberAreaExamine.defaultNumberAraeExamine(danNums, 0, 9, 1);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(tuoNums, 0, 9, 1);//号码域验证

            NumberRepeatExamine.defaultNumberRepeatExamine(dans + "," + tuos, ",", 0);//验证是否有重复

            temp += C(tuoNums.length, 3 - danNums.length);
        }

        //校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex1080403 ex1080403 = new Ex1080403();
        ex1080403.examina("1@3,4,5,6", 2);
    }

}
