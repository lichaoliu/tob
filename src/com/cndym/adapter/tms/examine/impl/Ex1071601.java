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
public class Ex1071601 extends BashExamina implements IExamine {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void examina(String number, int item) {
        
        logger.info("number:" + number + ";item=" + item);
        String ztag = ";";
        String nums[] = number.split(ztag);
        
        for (String num : nums) {
        	sortNumber(num);//ios版本没@和排序,临时排下序
        	
            NumberTagExamine.commaNumberTagExamine(num);
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 4L, 0L);
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);
        }

        //校验注数
        if (item != 5) {
            throw new CndymException("实际注数(" + nums.length + ")不等于传入(" + item + ")");
        }
        else return;
    }
    
    public String sortNumber(String num){
    	String[] splitNum = StringUtils.split(num, ',');
    	Arrays.sort(splitNum);
    	return StringUtils.join(splitNum, ",");
    }
    
    public static void main(String[] s){
    	Ex1071601 e = new Ex1071601();
    	e.examina("02,07,04,05", 5);
    }
}
