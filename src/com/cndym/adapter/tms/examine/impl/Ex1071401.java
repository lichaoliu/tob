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
import com.cndym.exception.ErrCode;

@Component
public class Ex1071401 extends BashExamina implements IExamine {
    
    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void examina(String number, int item) {
    	logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
        
        /*
        logger.info("number:" + number + ";item=" + item);
        String ztag = ";";
        String nums[] = number.split(ztag);
        int ticket = getTicketCount("1071401");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        
        for (String num : nums) {
        	sortNumber(num);//ios版本,临时排下序
        	
            NumberTagExamine.commaNumberTagExamine(num);
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2L, 0L);
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);
        }

        //校验注数
        if (item != nums.length) {
            throw new CndymException("实际注数(" + nums.length + ")不等于传入(" + item + ")");
        }
        else return;
        */
    }
    
    public String sortNumber(String num){
    	String[] splitNum = StringUtils.split(num, ',');
    	Arrays.sort(splitNum);
    	return StringUtils.join(splitNum, ",");
    }
    
    public static void main(String[] s){
    	Ex1071401  e = new Ex1071401();
    	e.examina("01,11", 1);
    }
}
