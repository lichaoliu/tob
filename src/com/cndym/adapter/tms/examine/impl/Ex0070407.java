package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Ex0070407 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0070407.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0070407", nums);
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
            String[] ns = num.split(",");
            for (int i = 0; i < ns.length; i++) {
                if (i < 2) {
                    NumberAreaExamine.defaultNumberAraeExamine(ns[i], 1, 6, 1);//号码域验证
                } else {
                    if (!"255".equals(ns[i])) {
                        throw new IllegalArgumentException("号码格式最后一位不为255，(" + num + ")");
                    }
                }
            }
            if (ns[0].equals(ns[1])) {
                throw new IllegalArgumentException("前两位号码不可以重复" + num);
            }
        }

        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0070407 ex0070301 = new Ex0070407();
        ex0070301.examina("1,2,255", 1);
    }
}
