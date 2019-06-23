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
public class Ex0020106 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020106.class);

    @Override
    public void examina(String number, int item) {
        logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
        /* String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020106", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 1);//号码个数的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 11, -1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
            String d3zxbh = BumberNoteTheNumberOfExamine.getD3zxbh().get(num.split(",").length + "");
            temp += new Integer(d3zxbh);

        }
        //和值计算注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }*/
    }

    public static void main(String[] args) {
        Ex0020106 ex0020106 = new Ex0020106();
        ex0020106.examina("1,2,8", 27);
        System.out.println("ok");
    }
}
