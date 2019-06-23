package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 作者：邓玉明 时间：11-4-30 下午1:36 QQ：757579248 email：cndym@163.com
 */
@Component
public class Ex1050101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1050101.class);

    @Override
    public void examina(String number, int item) {
    //    logger.info("该玩法暂不支持");
    //    throw new CndymException(ErrCode.E8117);
    //}
    NumberRepeatExamine.defaultNumberRepeatExamine(number, ";", 0);
    String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1050101", nums);
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 0);// 号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);// 号码域验证
        } // 校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }
    
    public static void main(String[] args) {
    	Ex1050101 ex = new Ex1050101();
    	ex.examina("06;06", 2);
    	System.out.println("ok!");
	}


}
