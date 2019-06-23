package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
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
public class Ex3000102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex3000102.class);

    @Override
    public void examina(String number, int item) {
        //校验格式
        NumberTagExamine.commaNumberTagExamine300(number);//号码中分隔符的验证
        NumberLengthExamine.defaultNumberLengthExamine(number, "\\*", 14, 0);//号码个数的验证
        if (number.indexOf("2") > -1) {
            throw new IllegalArgumentException("投注号码范围有问题" + number);
        }
        String[] arr = number.split("\\*");
        int temp = 1;
        for (String s : arr) {
            temp = temp * s.length();
        }
        if (temp <= 1) {
            throw new IllegalArgumentException("号码(" + number + ")不是复式选号");
        }

        for (String n : arr) {
            String[] ns = n.split("");
            for (int i = 1; i < ns.length; i++) {
                for (int j = i + 1; j < ns.length; j++) {
                    if (ns[i].equals(ns[j])) {
                        throw new IllegalArgumentException("号码有重复");
                    }
                }
            }
        }

        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }


    public static void main(String[] ages) {
        Ex3000102 ex3000102 = new Ex3000102();
        ex3000102.examina("310*3*3*3*3*3*3*3*3*3*3*30*3*301", 6);
    }
}
