package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
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
public class Ex1090102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1090102.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1090102", nums);
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 5, 0);//号码个数的验证
            String[] arr = num.split(",");
            int a = arr[0].length();
            int b = arr[1].length();
            int c = arr[2].length();
            int d = arr[3].length();
            int e = arr[4].length();

            if (a == 1 && b == 1 && c == 1 && d == 1 && e == 1) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
            for (String n : arr) {
//                NumberSortExamine.defaulstNumberSortExamine(n);//  验证号码是否按升序排序
                String[] ns = n.split("");
                for (int i = 1; i < ns.length; i++) {
                    for (int j = i + 1; j < ns.length; j++) {
                        if (ns[i].equals(ns[j])) {
                            throw new IllegalArgumentException("号码有重复");
                        }
                    }
                }
            }
            temp += a * b * c * d * e;
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1090102 ex1090102 = new Ex1090102();
        ex1090102.examina("1,2,3,4,359", 3);
        System.out.println("ok");
    }
}
