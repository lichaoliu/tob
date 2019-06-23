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
public class Ex1130103 extends BashExamina implements IExamine {

    private Logger logger = Logger.getLogger(Ex1130103.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1130103", nums);
        for (String num : nums) {
            NumberTagExamine.defaultNumberTagExamine(num, new String[]{tag, "#", "@"});//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, "#", 2, 0);//号码个数的验证
            String[] arr = num.split("#");
            String hong = arr[0];
            String lan = arr[1];
            String[] hongDt = hong.split("@");
            String[] lanDt = lan.split("@");
            if (hongDt.length == 1 && lanDt.length == 1) {
                throw new IllegalArgumentException("(" + num + ")不是胆拖号码");
            }

            int hongCount = 0;
            if (hongDt.length != 2) {
                NumberLengthExamine.defaultNumberLengthExamine(hong, tag, 4, 1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(hong, tag, 36, -1);//号码个数的验证
                NumberAreaExamine.defaultNumberAraeExamine(hong.split(tag), 1, 35, 2);
                /*NumberSortExamine.defaulstNumberSortExamine(hong, ",");//  验证号码是否按升序排序*/
                NumberRepeatExamine.defaultNumberRepeatExamine(hong, tag, 0);
                hongCount = C(hong.split(tag).length, 5);
            } else {
                NumberLengthExamine.defaultNumberLengthExamine(hongDt[0], tag, 5, -1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(hongDt[1], tag, 0, 1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(hong.replace("@", tag), tag, 5, 1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(hong.replace("@", tag), tag, 36, -1);//号码个数的验证
                NumberAreaExamine.defaultNumberAraeExamine(hongDt[0].split(tag), 1, 35, 2);//号码域验证
                NumberAreaExamine.defaultNumberAraeExamine(hongDt[1].split(tag), 1, 35, 2);
                NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(hongDt[0]).append(tag).append(hongDt[1]).toString(), tag, 0);
                /*NumberSortExamine.defaulstNumberSortExamine(hongDt[0], ",");//  验证号码是否按升序排序
                NumberSortExamine.defaulstNumberSortExamine(hongDt[1], ",");//  验证号码是否按升序排序*/
                hongCount = C(hongDt[1].split(tag).length, 5 - hongDt[0].split(tag).length);

            }


            int lanCount = 0;
            if (lanDt.length != 2) {
                NumberLengthExamine.defaultNumberLengthExamine(lan, tag, 1, 1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(lan, tag, 13, -1);//号码个数的验证
                NumberAreaExamine.defaultNumberAraeExamine(lan.split(tag), 1, 12, 2);
                /*NumberSortExamine.defaulstNumberSortExamine(lan, ",");//  验证号码是否按升序排序*/
                NumberRepeatExamine.defaultNumberRepeatExamine(lan, tag, 0);
                lanCount = C(lan.split(tag).length, 2);
            } else {
                NumberLengthExamine.defaultNumberLengthExamine(lanDt[0], tag, 2, -1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(lanDt[1], tag, 0, 1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(lan.replace("@", tag), tag, 2, 1);//号码个数的验证
                NumberLengthExamine.defaultNumberLengthExamine(lan.replace("@", tag), tag, 13, -1);//号码个数的验证
                NumberAreaExamine.defaultNumberAraeExamine(lanDt[0].split(tag), 1, 12, 2);//号码域验证
                NumberAreaExamine.defaultNumberAraeExamine(lanDt[1].split(tag), 1, 12, 2);
                NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(lanDt[0]).append(tag).append(lanDt[1]).toString(), tag, 0);
                /*NumberSortExamine.defaulstNumberSortExamine(lanDt[0], ",");//  验证号码是否按升序排序
                NumberSortExamine.defaulstNumberSortExamine(lanDt[1], ",");//  验证号码是否按升序排序*/
                lanCount = C(lanDt[1].split(tag).length, 2 - lanDt[0].split(tag).length);

            }
            temp += hongCount * lanCount;

        }
        //校验注数
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1130103 ex1080402 = new Ex1130103();
        ex1080402.examina("02,03,04,05@09,10#01,02,03", 6);
        System.out.println("ok");
    }
}
