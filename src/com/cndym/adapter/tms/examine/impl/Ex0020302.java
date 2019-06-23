package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020302 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020302.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020302", nums);
        int temp = 0;
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//验证号码是否重复
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 10, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
            temp = C(num.split(",").length, 3);
        }
        //校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0020302 ex0020206 = new Ex0020302();
        ex0020206.examina("0,1,2,3,4,5,6,7,8,9", 120);
        System.out.println("ok");
    }
}
