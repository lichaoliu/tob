package com.cndym.adapter.tms.examine.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
/**
 * 普通和值（4~17）
 */
@Component
public class Ex0140104 extends BashExamina implements IExamine{
    private Logger logger = Logger.getLogger(Ex0140104.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0140104", nums);
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
        for (int i = 0; i < nums.length; i++) {
			System.out.println(nums[i]);
		}
    }

    public static void main(String[] ages) {
    	Ex0140104 ex0140104 = new Ex0140104();
        ex0140104.examina("9;4;6;7;8", 5);
    }
}
