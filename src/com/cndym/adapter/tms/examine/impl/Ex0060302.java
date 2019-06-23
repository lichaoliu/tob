package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.utils.Utils;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0060302 extends BashExamina implements IExamine {

	   @Override
	    public void examina(String number, int item) {
	        String tag = ",";
	        String ztag = ";";
	        String[] ns = number.split(ztag);
	        int ticket = getTicketCount("0060302");
	        if (ns.length > ticket) {
	            throw new IllegalArgumentException("最多（" + ticket + "）票,实际" + ns.length);
	        }
	        int temp = 0;
	        for (String n : ns) {
	            NumberTagExamine.commaNumberTagExamine(n);//号码中分隔符的验证;
	            NumberLengthExamine.defaultNumberLengthExamine(n, tag, 3, 0);//号码个数的验证
	            for (String num : n.split(tag)) {
	                NumberAreaExamine.defaultNumberAraeExamine(Utils.strToStrArray(num), 0, 9);//号码域验证
	                //NumberRepeatExamine.defaultNumberRepeatExamine(num, " ", 0);//验证是否有重复

	            }
	            String[] nums = n.split(tag);
	            int bai = Utils.strToStrArray(nums[0]).length;
	            int shi = Utils.strToStrArray(nums[1]).length;
	            int ge = Utils.strToStrArray(nums[2]).length;
	            if (ge <= 1 && shi <= 1 && bai <= 1) {
	                throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
	            }
	            temp += ge * shi * bai;
	        }
	        if (temp != item) {
	            throw new IllegalArgumentException("实际注数(" + temp + ")不等于传入(" + item + ")");
	        }
	    }

    public static void main(String[] args) {
        Ex0060302 ex0060302 = new Ex0060302();
        ex0060302.examina("128,34,34", 12);
        System.out.println("ok");
    }
}
