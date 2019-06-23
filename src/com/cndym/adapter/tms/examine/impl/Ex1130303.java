package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
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
public class Ex1130303 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1130303.class);

    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
        /* String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1130303", nums);
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, "@", 2, 0);//号码个数的验证
            String numDans = num.split("@")[0];
            String numTuos = num.split("@")[1];
            NumberLengthExamine.defaultNumberLengthExamine(numDans, ",", 1, 0);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(numTuos, ",", 1, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(numDans + "," + numTuos, ",", 2, 1);//号码个数的验证

            NumberAreaExamine.defaultNumberAraeExamine(numDans.split(","), 1, 12, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(numTuos.split(","), 1, 12, 2);//号码域验证

            NumberRepeatExamine.defaultNumberRepeatExamine(numDans + "," + numTuos, ",", 0);
            temp += C(numTuos.split(",").length, 2 - numDans.split(",").length);
        }

        //校验注数
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }*/
    }

    public static void main(String[] ages) {
        Ex1130303 ex1130303 = new Ex1130303();
        ex1130303.examina("01@02,03,04", 3);
    }
}
