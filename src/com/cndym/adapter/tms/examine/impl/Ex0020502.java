package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-11-1
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */

@Component
public class Ex0020502 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020502.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020502", nums);
        int temp = 0;
        for (String num : nums) {
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 1, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, tag, 11, -1);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(tag), 0, 9, 1);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(num, tag, 0);//验证蓝球是否有重复
            NumberSortExamine.defaulstNumberSortExamine(num, ",");  //  验证号码是否按升序排序

            String d3zhfs = BumberNoteTheNumberOfExamine.getD3zhfs().get(num.split(",").length + "");
            temp += new Integer(d3zhfs);
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }


    public static void main(String[] ages) {
        Ex0020502 ex0020502 = new Ex0020502();
        ex0020502.examina("1,2,3,4,5,6,7,8,9", 330);
    }
}
