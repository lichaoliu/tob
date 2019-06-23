package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.BumberNoteTheNumberOfExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060204 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0060204.class);

    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);

        /*String ztag = ";";
        String[] nums = number.split(ztag);
        int ticket = getTicketCount("0060204");
        if (nums.length > ticket) {
            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + nums.length);
        }
        int temp = 0;
        for (String num : nums) {
            NumberAreaExamine.defaultNumberAraeExamine(num, 0, 18);//号码域验证
            String ssc2xzxhz = BumberNoteTheNumberOfExamine.getSsc2xzxhz().get(num);
            temp += new Integer(ssc2xzxhz);
        }
        //和值计算注数
        if (item != temp) {
            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
        }*/
    }

    public static void main(String[] args) {
        Ex0060204 ex0060204 = new Ex0060204();
        ex0060204.examina("12", 7);
        System.out.println("ok");
    }
}
