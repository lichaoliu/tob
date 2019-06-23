package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

@Component
public class Ex0110101 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex0110101.class);
	
    @Override
    public void examina(String number, int item) {
    	logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
//        String[] nums = number.split(ztag);
//        NumberTicketCountExamine.defaulstNumberSortExamine("0110101", nums);
//        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
//        for (String num : nums) {
//            //校验格式
//            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 6, 1);//号码域验证
//            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
//        }
//        //校验注数
//        if (item != nums.length) {
//            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
//            throw new CndymException(ErrCode.E8116);
//        }

    }

    public static void main(String[] ages) {
    	Ex0110101 ex = new Ex0110101();
    	ex.examina("1,1,1;1,2,3;3,3,4", 3);
    }
}
