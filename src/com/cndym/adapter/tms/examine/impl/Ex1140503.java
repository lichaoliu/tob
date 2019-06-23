package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 山东快乐扑克任选五胆拖玩法
 */
@Component
public class Ex1140503 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1140503.class);

    @Override
    public void examina(String number, int item) {
    	String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1140503", nums);
        for (String num : nums) {
	        //校验格式
	        NumberTagExamine.defaultNumberTagExamine(num, new String[]{",", "@"});//号码中分隔符的验证
	        String[] arr = num.split("@");
	        NumberLengthExamine.defaultNumberLengthExamine(arr[0], ",", 5, -1L);//号码个数的验证，胆码应该小于5
	        NumberLengthExamine.defaultNumberLengthExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 5, 1);//号码个数的验证，胆码+拖码应该大于5
	        NumberAreaExamine.defaultNumberAraeExamine(arr[0].split(","), 1, 13, 2);//号码域验证
	        NumberAreaExamine.defaultNumberAraeExamine(arr[1].split(","), 1, 13, 2);//号码域验证
	        
	        NumberSortExamine.defaulstNumberSortExamine(arr[0], ",");//投注号码必须按照升序排列
            NumberSortExamine.defaulstNumberSortExamine(arr[1], ",");//投注号码必须按照升序排列
	        
	        NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(arr[0]).append(",").append(arr[1]).toString(), ",", 0);//验证胆码与拖码是否存在重复数字
	        int dan = arr[0].split(",").length;
	        int tuo = arr[1].split(",").length;
	        temp += C(tuo, 5 - dan);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex1140503 ex1140503 = new Ex1140503();
        ex1140503.examina("01,03@04,08,07,09", 4);
        System.out.println("ok");
    }
}
