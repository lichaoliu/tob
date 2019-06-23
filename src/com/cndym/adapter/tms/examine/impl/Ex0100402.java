package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-10-27
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */

@Component
public class Ex0100402 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0100402.class);

    @Override
    public void examina(String number, int item) {
//        String[] nums = number.split(ztag);
        String tag = ",";
//        NumberTicketCountExamine.defaulstNumberSortExamine("0100402", nums);
        String[] nums = number.split(ztag);
        int temp = nums.length;
        NumberTagExamine.defaultNumberTagExamine(number, new String[]{";", ","});//号码中分隔符的验证
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num.split(tag), 1, 6, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);//验证号码是否有重复
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
        }
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] ages) {
        Ex0100402 ex0070201 = new Ex0100402();
        ex0070201.examina("1,2;3,4;4,6", 3);
    }
}
