package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-11-1
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
@Component
public class Ex0020103 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020103.class);

    @Override
    public void examina(String number, int item) {
        String tag = ",";
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("0020103", nums);
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, "@", 2, 0);//号码个数的验证
            String[] numMsg = num.split("@");
            String dan = numMsg[0];
            String tuo = numMsg[1];
            NumberLengthExamine.defaultNumberLengthExamine(dan, tag, 0, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(dan, tag, 3, -1);//号码个数的验证
            NumberSortExamine.defaulstNumberSortExamine(dan, ",");  //  验证号码是否按升序排序
            NumberSortExamine.defaulstNumberSortExamine(tuo, ",");  //  验证号码是否按升序排序

            int tuoCount = tuo.split(tag).length;
            int danCount = dan.split(tag).length;
            if (danCount + tuoCount < 3) {
                throw new IllegalArgumentException("胆码+拖码必须大于3，目前" + danCount + tuoCount);
            }

            temp += C(tuoCount, 3 - danCount);

        }

        //校验注数
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }
}