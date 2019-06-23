package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 黑龙江11选5(前组二复式)
 */
@Component
public class Ex1021102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1021102.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1021102", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        for (String num : nums) {
        	NumberTagExamine.commaNumberTagExamine(num);//号码中必须存在分隔符
        	NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 1);//号码个数的验证，必须大于2
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 12, -1);// 号码个数的验证，必须小于12
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 11, 2);// 号码域验证,1-11的两位数字
            NumberRepeatExamine.defaultNumberRepeatExamine(num, ",", 0);//判断是否有重复数字
            int len = num.split(",").length;
            temp += C(len, 2);
        } 
        
        // 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1021102 ex1021102 = new Ex1021102();
        ex1021102.examina("01,02,03", 3);
        System.out.println("ok");
    }

}
