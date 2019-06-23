package com.cndym.adapter.tms.examine.impl;

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
public class Ex0060609 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0060704.class);

    @Override
    public void examina(String number, int item) {
        // TODO Auto-generated method stub
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
    }
//    @Override
//    public void examina(String number, int item) {
//        String ztag = ";";
//        String[] nums = number.split(ztag);
//        int ticket = getTicketCount("0060609");
//        if(nums.length > ticket){
//            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
//        }
//        int temp = 0;
//        for (String num : nums) {
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 0);//号码个数的验证
//            //NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, -1);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
//            temp += new Integer(num.split(",").length * 10);
//        }
//        //和值计算注数
//        if (item != temp) {
//            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
//        }
//    }

    public static void main(String[] args) {
        Ex0060609 ex0060609 = new Ex0060609();
        ex0060609.examina("1;2", 20);
        System.out.println("ok");
    }

}
