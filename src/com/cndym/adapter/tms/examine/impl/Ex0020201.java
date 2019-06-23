package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;

import java.util.HashMap;
import java.util.Map;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0020201 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0020201.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0020201", nums);
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
            NumberSortExamine.defaulstNumberSortExamine(num, ",");//  验证号码是否按升序排序
            Map<String, String> map = new HashMap<String, String>();
            String[] arr = num.split(",");
            for (String s : arr) {
                map.put(s, s);
            }

            if (map.size() == 1) {
                throw new IllegalArgumentException("号码不能全部相同" + num);
            }

            if (!arr[0].equals(arr[1]) && !arr[1].equals(arr[2])) {
                throw new IllegalArgumentException("前两个号码或后两个号码必须重复");
            }
        }
        //校验注数
        if (item != nums.length) {
            logger.info("实际注数(" + nums.length + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0020201 ex0010101 = new Ex0020201();
        ex0010101.examina("1,2,2;4,4,5;4,4,5;1,4,4;4,4,5;", 5);
        System.out.println("ok");
    }

}
