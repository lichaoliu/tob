package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-10-27
 * Time: 下午1:52
 * To change this template use File | Settings | File Templates.
 */

@Component
public class Ex0100104 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0100104.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0100104", nums);
        Map<String, String> map = new HashMap<String, String>();
        for (String num : nums) {
            if (map.containsValue(num)) {
                throw new IllegalArgumentException("号码有重复" + num);
            }
            map.put(num, num);
            if (new Long(num) < 10 && num.length() != 1) {
                throw new IllegalArgumentException("号码不为4~17的整数");
            }
            NumberAreaExamine.defaultNumberAraeExamine(num, 4, 17);//号码域验证
        }

        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex0100104 ex0100104 = new Ex0100104();
        ex0100104.examina("7;17", 2);
    }
}
