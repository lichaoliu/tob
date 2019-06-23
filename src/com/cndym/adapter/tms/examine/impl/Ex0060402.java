package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060402 extends BashExamina implements IExamine {
	private Logger logger = Logger.getLogger(Ex0060304.class);
    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
    }
	
//    @Override
//    public void examina(String number, int item) {
//        String tag = ",";
//        String ztag = ";";
//        String[] ns = number.split(ztag);
//        int ticket = getTicketCount("0060402");
//        if(ns.length > ticket){
//            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + ns.length);
//        }
//        int temp = 0;
//        for (String n : ns) {
//            NumberTagExamine.commaNumberTagExamine(n);//号码中分隔符的验证;
//            NumberLengthExamine.defaultNumberLengthExamine(n, tag, 4, 0);//号码个数的验证
//            for (String num : n.split(tag)) {
//                NumberAreaExamine.defaultNumberAraeExamine(num.split(" "), 0, 9);//号码域验证
//                NumberRepeatExamine.defaultNumberRepeatExamine(num, " ", 0);//验证是否有重复
//
//            }
//            String[] nums = n.split(tag);
//            int qian = nums[0].split(" ").length;
//            int bai = nums[1].split(" ").length;
//            int shi = nums[2].split(" ").length;
//            int ge = nums[3].split(" ").length;
//            if (ge <= 1 && shi <= 1 && bai <= 1 && qian <= 1) {
//                throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
//            }
//            temp += ge * shi * bai * qian;
//        }
//        if (temp != item) {
//            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
//        }
//    }

    public static void main(String[] args) {
        Ex0060402 ex0060402 = new Ex0060402();
        ex0060402.examina("1,2,3,6 8;1,2,3,6 8", 4);
        System.out.println("ok");
    }
}
