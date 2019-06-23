package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
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
public class Ex0060606 extends BashExamina implements IExamine {
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
//        int ticket = getTicketCount("0060606");
//        if(nums.length > ticket){
//            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
//        }
//        int temp = 0;
//        for (String num : nums) {
//            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 1);//号码个数的验证
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 7, -1);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
//            String ssc2xzuxbh = BumberNoteTheNumberOfExamine.getSsc2xzuxbh().get(num.split(",").length + "");
//            temp += new Integer(ssc2xzuxbh);
//        }
//        //和值计算注数
//        if (item != temp) {
//            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
//        }
//    }

    public static void main(String[] args) {
        Ex0060606 ex0060606 = new Ex0060606();
        ex0060606.examina("1,2,3;1,2,3,5", 16);
        System.out.println("ok");
    }
}
