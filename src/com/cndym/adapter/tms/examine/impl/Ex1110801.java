package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 陕西11选5(任选八单式)
 */
@Component
public class Ex1110801 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1110801.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1110801", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        for (String num : nums) {
        	 //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔是否存在的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 8, 0);//号码个数的验证，必须等于8
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);// 号码域验证,1-11的两位数字
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//投注号码必须按照升序排列
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//判断是否有重复数字
        } 
        
        // 校验注数
        if (nums.length != item) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1110801 ex1110801 = new Ex1110801();
        ex1110801.examina("01,02,03,04,05,06,07,08;02,03,04,05,06,07,09,10;01,02,03,05,08,09,10,11;01,02,03,04,05,06,09,10;02,03,04,06,07,09,10,11", 5);
        System.out.println("ok");
    }

}
