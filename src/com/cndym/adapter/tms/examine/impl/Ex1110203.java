package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 陕西11选5(任选三胆拖)
 */
@Component
public class Ex1110203 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1110203.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1110203", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        for (String num : nums) {
        	//校验格式
        	NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
            String[] arr = num.split("@");
            NumberLengthExamine.defaultNumberLengthExamine(arr[0], ",", 1, 0);//号码个数的验证，胆码应该等于1
            NumberLengthExamine.defaultNumberLengthExamine(arr[1], ",", 1, 1);//号码个数的验证，拖码应该大于1
            NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 11, 2);//胆码号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 11, 2);//拖码号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 0);//验证胆码与拖码是否存在重复数字
            int dan = arr[0].split(",").length;
            int tuo = arr[1].split(",").length;
            temp += C(tuo, 2 - dan);
        } 
        
        // 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1110203 ex1110203 = new Ex1110203();
        ex1110203.examina("02@05,06,07,08,09,10,11", 7);
        System.out.println("ok");
    }

}
