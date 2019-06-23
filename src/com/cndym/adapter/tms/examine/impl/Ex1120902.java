package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 青海11选5(前二复式)
 */
@Component
public class Ex1120902 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1120902.class);

    @Override
    public void examina(String number, int item) {

    	 String[] nums = number.split(ztag);
         int temp = 0;
         NumberTicketCountExamine.defaulstNumberSortExamine("1120902", nums);
         for (String n : nums) {
             //校验格式
             NumberLengthExamine.defaultNumberLengthExamine(n, "#", 2, 0);//号码个数的验证，即#号分割的个数
             String[] num = n.split("#");
             String ge = num[0];
             String shi = num[1];
             String[] ges = ge.split(",");
             String[] shis = shi.split(",");
             if (ges.length + shis.length <= 2) {
                 throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
             }
             NumberLengthExamine.defaultNumberLengthExamine(ge, ",", 1, 2);//号码个数的验证
             NumberLengthExamine.defaultNumberLengthExamine(shi, ",", 1, 2);//号码个数的验证
             NumberAreaExamine.defaultNumberAraeExamine(ges, 1, 11, 2);//号码域验证
             NumberAreaExamine.defaultNumberAraeExamine(shis, 1, 11, 2);//号码域验证

             NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(ge).append(",").append(shi).toString(), ",", 0);

//         排重的注数算法
//            temp = itemCount(ges, shis);
             temp += ges.length * shis.length;
         }
         if (temp != item) {
             logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
             throw new CndymException(ErrCode.E8116);
         }
    }

    public static void main(String[] args) {
        Ex1120902 ex1110902 = new Ex1120902();
        ex1110902.examina("01,08#1011", 4);
        System.out.println("ok");
    }

}
