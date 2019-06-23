package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-7-16 下午07:27:11
 */

@Component
public class Ex0080701 extends BashExamina implements IExamine {

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int ticket = nums.length;
        int ticketCount = getTicketCount("0080701");
        if (ticket > ticketCount) {
            throw new IllegalArgumentException("最多（" + ticketCount + "）票,实际" + ticket);
        }
        for (String num : nums) {
            //校验格式
            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证
            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 3, 0);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
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
        if (item != ticket) {
            throw new IllegalArgumentException("实际注数(" + ticket + ")不等于传入(" + item + ")");
        }
    }

    public static void main(String[] args) {
        Ex0080701 ex0060701 = new Ex0080701();
        ex0060701.examina("1,1,2;1,1,2;2,2,3;1,1,2;2,2,3;1,1,2;", 3);
        System.out.println("ok");
    }
}
