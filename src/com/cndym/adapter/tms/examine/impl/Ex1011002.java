package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 江西11选5(前三复式)
 */
@Component
public class Ex1011002 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1011002.class);

    @Override
    public void examina(String number, int item) {

    	 String[] nums = number.split(ztag);
         int temp = 0;
         NumberTicketCountExamine.defaulstNumberSortExamine("1011002", nums);
         for (String n : nums) {
        	 //校验格式
             NumberLengthExamine.defaultNumberLengthExamine(n, "#", 3, 0);//号码个数的验证
             String[] num = n.split("#");
             String ge = num[0];
             String shi = num[1];
             String bai = num[2];
             String[] ges = ge.split(",");
             String[] shis = shi.split(",");
             String[] bais = bai.split(",");
             if (ges.length + shis.length + bais.length <= 3) {
                 throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
             }
             NumberLengthExamine.defaultNumberLengthExamine(ge, ",", 1, 2);//号码个数的验证
             NumberLengthExamine.defaultNumberLengthExamine(shi, ",", 1, 2);//号码个数的验证
             NumberLengthExamine.defaultNumberLengthExamine(bai, ",", 1, 2);//号码个数的验证
             NumberAreaExamine.defaultNumberAraeExamine(ges, 1, 11, 2);//号码域验证
             NumberAreaExamine.defaultNumberAraeExamine(shis, 1, 11, 2);//号码域验证
             NumberAreaExamine.defaultNumberAraeExamine(bais, 1, 11, 2);//号码域验证
             NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(ge).append(",").append(shi).append(",").append(bai).toString(), ",", 0);

             temp += ges.length * shis.length * bais.length;
         }
         if (temp != item) {
             logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
             throw new CndymException(ErrCode.E8116);
         }
    }

    public static void main(String[] args) {
        Ex1011002 ex1021002 = new Ex1011002();
        ex1021002.examina("01,08#02,10#05,11", 8);
        System.out.println("ok");
    }

}
