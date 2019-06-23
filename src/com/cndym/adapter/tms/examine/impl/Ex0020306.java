package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020306 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020306.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020306", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 9, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            String d3z6bh = BumberNoteTheNumberOfExamine.getD3z6bh().get(num.split(",").length + "");
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
            temp += new Integer(d3z6bh);
        }
        //和值计算注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0020306 ex0020306 = new Ex0020306();
        ex0020306.examina("1,2,3,4;1,2,3,4;1,2,3,4;1,2,3,4;", 16);
        System.out.println("ok");
    }
}