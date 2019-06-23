package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
@Component
public class Ex0120207 extends BashExamina implements IExamine{
	private Logger logger = Logger.getLogger(Ex0120207.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0120207", nums);
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            String[] ns = num.split(",");
            for (int i = 0; i < ns.length; i++) {
                if (i != 2) {
                    NumberAreaExamine.defaultNumberAraeExamine(ns[i], 1, 6, 1);//号码域验证

                } else {
                    if (!"*".equals(ns[i])) {
                        throw new IllegalArgumentException("号码格式最后一位不为*，(" + num + ")");
                    }
                }
            }
            if (!ns[0].equals(ns[1])) {
                throw new IllegalArgumentException("前两个号码必须重复");
            }
        }
        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0120207 ex0120202 = new Ex0120207();
        ex0120202.examina("2,2,*", 1);
//    	NumberRepeatExamine.defaultNumberRepeatExamine("2,2,*;1,1,*;1,1,*", ";", 0);
    }
}
