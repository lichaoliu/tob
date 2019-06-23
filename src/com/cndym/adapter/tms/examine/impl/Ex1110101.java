package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
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
public class Ex1110101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1110101.class);

    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
//        String ztag = ";";
//        String[] nums = number.split(ztag);
//        for (String num : nums) {
//            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 5, 0);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 22, 2);//号码域验证
//        }
//        //校验注数
//        if (item != nums.length) {
//            throw new IllegalArgumentException("实际注数(" + nums.length + ")不等于传入(" + item + ")");
//        }

    }

    public static void main(String[] args) {
        Ex1110101 ex1110101 = new Ex1110101();
        ex1110101.examina("01,02,23,04,05", 1);
        System.out.println("ok");
    }
}
