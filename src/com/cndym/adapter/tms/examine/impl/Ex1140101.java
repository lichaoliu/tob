package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克任选一单式玩法
 */
@Component
public class Ex1140101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1140101.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1140101", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        NumberSortExamine.defaulstNumberSortExamine(number, ";");//投注号码必须按照升序排列
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 0);// 号码个数的验证，必须等于1
            NumberAreaExamine.defaultNumberAraeExamine(num, 1, 13, 2);// 号码域验证
        } // 校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1140101 ex1140101 = new Ex1140101();
        ex1140101.examina("01;02;03;05;08", 5);
        System.out.println("ok");
    }

}
