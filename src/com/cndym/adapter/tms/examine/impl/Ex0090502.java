package com.cndym.adapter.tms.examine.impl;

import java.util.HashMap;
import java.util.Map;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.utils.Utils;

import org.springframework.stereotype.Component;

@Component
public class Ex0090502 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("0090502", nums);

        int ticket = 1;
        String tag = ",";
        NumberTagExamine.commaNumberTagExamine(number);//号码中分隔符的验证;
        NumberLengthExamine.defaultNumberLengthExamine(number, tag, 5, 0);//号码个数的验证
        
        Map<String, String> repeatMap = null;
        nums = number.split(tag);
        for (String num : nums) {
        	String[] numArray = Utils.strToStrArray(num);
        	NumberAreaExamine.defaultNumberAraeExamine(numArray, 0, 9, 1);//号码域验证
        	NumberSortExamine.defaulstNumberSortExamine(num);// 验证号码是否按升序排序

        	repeatMap = new HashMap<String, String>();
            //验证是否有重复
			for (String key : numArray) {
				if (repeatMap.containsKey(key)) {
					throw new IllegalArgumentException("号码(" + num + ")有重复");
				} else {
					repeatMap.put(key, key);
				}
			}
			ticket = ticket * numArray.length;
        }
        if (1 == ticket) {
        	throw new IllegalArgumentException("号码(" + number + ")不是复式选号");
        }
        if (ticket != item) {
            throw new IllegalArgumentException("实际注数(" + ticket + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0090502 ex = new Ex0090502();
        ex.examina("10,20,30,13,10", 32);
        System.out.println("ok");
    }
}
