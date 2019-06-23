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
public class Ex0090302 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
		NumberTicketCountExamine.defaulstNumberSortExamine("0090302", number.split(ztag));
    	String tag = ",";
        NumberTagExamine.commaNumberTagExamine(number);//号码中分隔符的验证;
        NumberLengthExamine.defaultNumberLengthExamine(number, tag, 3, 0);//号码个数的验证

        int temp = 0;
        Map<String, String> repeatMap = null;
        for (String num : number.split(tag)) {
        	String[] numArray = Utils.strToStrArray(num);
        	repeatMap = new HashMap<String, String>();
        	
        	NumberAreaExamine.defaultNumberAraeExamine(numArray, 0, 9, 1);//号码域验证
        	NumberSortExamine.defaulstNumberSortExamine(num);// 验证号码是否按升序排序
        	
            //验证是否有重复
			for (String key : numArray) {
				if (repeatMap.containsKey(key)) {
					throw new IllegalArgumentException("号码(" + num + ")有重复");
				} else {
					repeatMap.put(key, key);
				}
			}
        }
        String[] nums = number.split(tag);
        int bai = nums[0].length();
        int shi = nums[1].length();
        int ge = nums[2].length();
        if (ge <= 1 && shi <= 1 && bai <= 1) {
            throw new IllegalArgumentException("号码(" + number + ")不是复式选号");
        }
        temp += ge * shi * bai;

        if (temp != item) {
        	throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] args) {
        Ex0090302 ex = new Ex0090302();
        ex.examina("1,2,34", 2);
        System.out.println("ok");
    }
}
