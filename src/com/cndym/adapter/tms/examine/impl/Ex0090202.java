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
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.Utils;

import org.springframework.stereotype.Component;

@Component
public class Ex0090202 extends BashExamina implements IExamine {
    @Override
    public void examina(String number, int item) {
    	String[] nums = number.split(ztag);
		NumberTicketCountExamine.defaulstNumberSortExamine("0090202", nums);

        int ticket = 0;
        for (String num : nums) {
        	Map<String, String> repeatMap = null;
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 0);//号码个数的验证

            String[] numArray = num.split(",");
            for (String fs : numArray) {
	        	repeatMap = new HashMap<String, String>();
	        	String[] fsArray = Utils.strToStrArray(fs);
	            NumberAreaExamine.defaultNumberAraeExamine(fsArray, 0, 9);//号码域验证
	            NumberSortExamine.defaulstNumberSortExamine(fs);// 验证号码是否按升序排序

	            for (String key : fsArray) {
					if (repeatMap.containsKey(key)) {
						throw new IllegalArgumentException("号码(" + fs + ")有重复");
					} else {
						repeatMap.put(key, key);
					}
				}
            }
            int shi = Utils.strToStrArray(numArray[0]).length;
            int ge = Utils.strToStrArray(numArray[1]).length;
            if (ge <= 1 && shi <= 1) {
                throw new IllegalArgumentException("号码(" + num + ")不是复式选号");
            }
            ticket += ge * shi;
        }

        if (ticket != item) {
        	throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0090202 ex = new Ex0090202();
        ex.examina("1,23;1,23", 4);
        System.out.println("ok");
    }
}
