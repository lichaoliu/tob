package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克任选四单式玩法
 */
@Component
public class Ex1140401 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1140401.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1140401", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        for (String num : nums) {
        	 //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔是否存在的验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//投注号码必须按照升序排列
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 4, 0);//号码个数的验证，必须等于4
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 13, 2);//号码域验证，必须为01-13的两位数字
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//是否存在重复的数字
        } // 校验注数
        if (nums.length != item) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1140401 ex0063001 = new Ex1140401();
        ex0063001.examina("01,02,04,05;02,06,09,10;01,11,12,13;08,09,10,11;01,05,06,07", 5);
        System.out.println("ok");
    }

}
