package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克任选二胆拖玩法
 */
@Component
public class Ex1140203 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1140203.class);

    @Override
    public void examina(String number, int item) {
        
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1140203", nums);
        for (String num : nums) {
        	//校验格式
        	NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
            String[] arr = num.split("@");
            NumberLengthExamine.defaultNumberLengthExamine(arr[0], ",", 1, 0);//号码个数的验证，胆码应该等于1
            NumberLengthExamine.defaultNumberLengthExamine(arr[1], ",", 1, 1);//号码个数的验证，拖码应该大于1
            NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 13, 2);//胆码号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 13, 2);//拖码号码域验证
            
            NumberSortExamine.defaulstNumberSortExamine(arr[0], ",");//投注号码必须按照升序排列
            NumberSortExamine.defaulstNumberSortExamine(arr[1], ",");//投注号码必须按照升序排列
            
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 0);//验证胆码与拖码是否存在重复数字
            int dan = arr[0].split(",").length;
            int tuo = arr[1].split(",").length;
            temp += C(tuo, 2 - dan);
        }
        
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1140203 ex1140203 = new Ex1140203();
        ex1140203.examina("03@01,02", 3);
        System.out.println("ok");
    }
}
