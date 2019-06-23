package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Ex0110608 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0110608.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0110608", nums);
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        //校验格式
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            if (!"7,8,9".equals(num)) {
                throw new IllegalArgumentException("号码(" + num + ")不为7,8,9");
            }
        }

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
    	Ex0110608 ex = new Ex0110608();
        ex.examina("123,234,345,456", 1);
    }
}
