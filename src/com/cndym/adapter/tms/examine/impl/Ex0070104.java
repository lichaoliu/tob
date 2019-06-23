package com.cndym.adapter.tms.examine.impl;

import java.util.HashMap;
import java.util.Map;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class Ex0070104 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0070104.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0070104", nums);
        Map<String, String> map = new HashMap<String, String>();
        for (String num : nums) {
            if (map.containsKey(num)) {
                throw new IllegalArgumentException("号码有重复" + num);
            }
            map.put(num, num);
            if (new Long(num) < 10 && num.length() != 1) {
                throw new IllegalArgumentException("号码不为4~17的整数");
            }
            NumberAreaExamine.defaultNumberAraeExamine(num, 4, 17);//号码域验证
        }
        
        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }
    
    public static void main(String args[]) {
    	Ex0070104 ex = new Ex0070104();
    	ex.examina("4;5;16;17", 3);
    	System.out.println("ok");
    }
}
