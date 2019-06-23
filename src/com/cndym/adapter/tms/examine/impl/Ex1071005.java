package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 作者：邓玉明
 * 时间：11-4-30 下午1:36
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class Ex1071005 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1071005.class);

    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
//        String[] nums = number.split(ztag);
//        int temp = 0;
//        NumberTicketCountExamine.defaulstNumberSortExamine("1071005", nums);
//        for (String n : nums) {
//            //校验格式
//            NumberTagExamine.defaultNumberTagExamine(n, new String[]{",", "#"});//号码中分隔符的验证
//            String[] arr = n.split("#");
//            NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 11, 2);//号码域验证
//            NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 11, 2);//号码域验证
//            int a = arr[0].split(",").length;
//            int b = arr[1].split(",").length;
//            temp += a * b;
//        }
//        if (temp != item) {
//            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
//            throw new CndymException(ErrCode.E8116);
//        }
    }
}

