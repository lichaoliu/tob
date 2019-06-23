package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Ex0110104 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0110104.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0110104", nums);
        NumberSortExamine.defaulstNumberSortExamine(number, ";");//  验证号码是否按升序排序
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num, 4, 17, 2);//号码域验证
        }
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
    	Ex0110104 ex = new Ex0110104();
        ex.examina("04;16", 2);
    }
}
