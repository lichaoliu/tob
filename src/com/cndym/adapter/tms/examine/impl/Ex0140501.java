package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 三不同号单式：是指对三个各不相同的号码进行投注。
 */

@Component
public class Ex0140501 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0140501.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0140501", nums);
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 6, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证号码是否重复
//            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
        }

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0140501 ex0070501 = new Ex0140501();
        ex0070501.examina("1,2,4;1,2,4", 2);
    }
}
