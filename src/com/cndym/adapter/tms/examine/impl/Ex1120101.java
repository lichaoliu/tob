package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 作者：李娜
 * 时间：2017年4月24日14:47:35
 * 青海11选5(任选一单式)
 */
@Component
public class Ex1120101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1120101.class);

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
        Ex1120101 ex1110101 = new Ex1120101();
        ex1110101.examina("01,02,23,04,05", 1);
        System.out.println("ok");
    }
}
