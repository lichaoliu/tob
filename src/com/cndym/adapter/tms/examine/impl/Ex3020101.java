package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
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
public class Ex3020101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex3020101.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("3020101", nums);
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine300(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, "\\*", 8, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split("\\*"), 0, 3, 1);//号码域验证
        }
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }
    public static void main(String[] ages) {
        Ex3020101 ex3000101 = new Ex3020101();
        ex3000101.examina("3*3*3*1*0*0*0*3;0*3*0*3*1*3*3*3;0*0*1*3*3*1*3*3;0*0*3*3*1*3*3*3;0*3*1*3*3*3*3*1", 5);
    }
}
