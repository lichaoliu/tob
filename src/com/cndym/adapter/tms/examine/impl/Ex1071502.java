package com.cndym.adapter.tms.examine.impl;

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
public class Ex1071502 extends BashExamina implements IExamine {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void examina(String number, int item) {
    	logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
        
        /*        
        logger.info("number:" + number + ";item=" + item);
        String ztag = ";";
        String nums[] = number.split(ztag);
        int ticket = getTicketCount("1071502");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3L, 1L);
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);
            int len = num.split(",").length;
            temp += C(len, 3);
        }

        //校验注数
        if (temp != item) {
            throw new CndymException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }
        else return;
        */
    }
    
    public static void main(String[] s){
    	Ex1071502 e = new Ex1071502();
    	e.examina("02,03,04,05", 4);
    }
}
