package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 二不同号单式：
 * us:1,2
 * lt:12
 */

@Component
public class Ex0130401 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0130401.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0130401", nums);
        NumberRepeatExamine.defaultNumberRepeatExamine(number, ztag, 0);//验证号码是否有重复
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 6, 1);//号码域验证
            //NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
            Map<String, String> map = new HashMap<String, String>();
            String[] arr = num.split(",");
            for (String s : arr) {
                map.put(s, s);
            }
            if (map.size() == 1) {
                throw new IllegalArgumentException("号码不能全部相同" + num);
            }
        }
        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }

    }

    public static void main(String[] ages) {
        Ex0130401 ex0070201 = new Ex0130401();
        ex0070201.examina("4,3;3,4", 2);
    }
}
