package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 新疆11选5(任选三胆拖)
 */
@Component
public class Ex1030303 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1030303.class);

    @Override
    public void examina(String number, int item) {

        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1030303", nums);//验证号码的票数，必须与配置文件lotteryInfo.xml相符
        for (String num : nums) {
        	//校验格式
        	NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
            String[] arr = num.split("@");
            NumberLengthExamine.defaultNumberLengthExamine(arr[0], ",", 3, -1);//号码个数的验证，胆码应该小于3
            NumberLengthExamine.defaultNumberLengthExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 3, 1);//号码个数的验证，胆码+拖码应该大于3
            NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 11, 2);//胆码号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 11, 2);//拖码号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 0);//验证胆码与拖码是否存在重复数字
            int dan = arr[0].split(",").length;
            int tuo = arr[1].split(",").length;
            temp += C(tuo, 3 - dan);
        } 
        
        // 校验注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] args) {
        Ex1030303 ex1030303 = new Ex1030303();
        ex1030303.examina("01,02@03,06", 2);
        System.out.println("ok");
    }

}
