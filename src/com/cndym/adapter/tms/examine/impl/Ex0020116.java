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
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Ex0020116 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020116.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020116", nums);
        int temp = 0;
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 1, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            String d3zxkd = BumberNoteTheNumberOfExamine.getD3zxkd().get(num);
            temp += new Integer(d3zxkd);

        }
        //跨度计算注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0020116 ex0020116 = new Ex0020116();
        ex0020116.examina("0", 10);
    }
}
