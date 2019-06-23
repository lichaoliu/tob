package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 黑龙江11选5(任选八复式)
 */
@Component
public class Ex1020802 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1020802.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1020802", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        for (String num : nums) {
        	NumberTagExamine.commaNumberTagExamine(num);//号码中必须存在分隔符
        	NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, 1);//号码个数的验证，必须大于7
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 12, -1);// 号码个数的验证，必须小于12
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);// 号码域验证,1-11的两位数字
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//投注号码必须按照升序排列
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//判断是否有重复数字
            int len = num.split(",").length;
            temp += C(len, 8);
        } 
        
        // 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1020802 ex1020802 = new Ex1020802();
        ex1020802.examina("01,02,03,04,05,06,07,08,09", 9);
        System.out.println("ok");
    }

}
