package com.cndym.adapter.tms.examine.impl;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.exception.CndymException;

@Component
public class Ex1071501 extends BashExamina implements IExamine {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void examina(String number, int item) {
        
        logger.info("number:" + number + ";item=" + item);
        String ztag = ";";
        String nums[] = number.split(ztag);
        
        for (String num : nums) {
        	NumberTagExamine.defaultNumberTagExamine(num, new String[]{"#"});//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 3L, 0L);
            NumberAreaExamine.defaultNumberAraeExamine(num.split("#"), 1, 11, 2);
            NumberRepeatExamine.defaultNumberRepeatExamine(num, "#", 0);
        }

        //校验注数
        if (nums.length * 3 != item ) {
            throw new CndymException("实际注数(" + (nums.length * 3) + ")不等于传入(" + item + ")");
        }
        else return;
    }
    
    
    public static void main(String[] s){
    	Ex1071501 e = new Ex1071501();
    	e.examina("02#05#04", 3);
    }
}
