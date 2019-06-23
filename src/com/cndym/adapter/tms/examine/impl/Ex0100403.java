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
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-10-27
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */

@Component
public class Ex0100403 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0100403.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        String tag = ",";
        NumberTicketCountExamine.defaulstNumberSortExamine("0100403", nums);
        int temp = 0;
        for (String num : nums) {
            //校验格式
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{"@", ","});//号码中分隔符的验证
            String[] numbers = num.split("@");
            String dans = numbers[0];
            String tuos = numbers[1];
            NumberAreaExamine.defaultNumberAraeExamine(dans.split(tag), 1, 6, 1);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(tuos.split(tag), 1, 6, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(tuos, tag, 0);//验证号码是否有重复
            NumberSortExamine.defaulstNumberSortExamine(tuos, tag);//  验证号码是否按升序排序
            NumberLengthExamine.defaultNumberLengthExamine(dans, tag, 1, 0);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(tuos, tag, 6, -1);//号码个数的验证
            temp += (tuos.split(",")).length;
        }
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] ages) {
        Ex0100403 ex0070201 = new Ex0100403();
        ex0070201.examina("1@2,3,4", 3);
    }
}
