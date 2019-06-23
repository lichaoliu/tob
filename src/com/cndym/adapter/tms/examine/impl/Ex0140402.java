package com.cndym.adapter.tms.examine.impl;

import java.util.ArrayList;
import java.util.List;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.*;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 二不同号复式：
 * us:1,2;3,4
 * lt:12-23-34
 */

@Component
public class Ex0140402 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0140402.class);

    @Override
    public void examina(String number, int item) {
    	logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
//        String tag = ",";
//        String[] nums = number.split(tag);
//        if(nums.length==2){
//        	 logger.info("复试玩法不能进行单式投注 号码:"+number+" 注数:"+item);
//        	throw new CndymException(ErrCode.E8209);
//        }
//        NumberRepeatExamine.defaultNumberRepeatExamine(number, tag, 0);//验证号码是否有重复
//        for (String num : nums) {
//        	NumberAreaExamine.defaultNumberAraeExamine(num, 1, 6, 1);//号码域验证
//		}
//        String str="";
//        List<String> arr=new ArrayList<String>();
//        count(0,str,nums,2,arr);
//        int temp = arr.size();
//        if (item != temp) {
//            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
//            throw new CndymException(ErrCode.E8116);
//        }
    }

    private static void count(int i, String str, String[] num,int n,List<String> arr) {
        if(n==0){
        	arr.add(str);
            return;
        }
        if(i==num.length){
            return;
        }
        count(i+1,str+num[i],num,n-1,arr);
        count(i+1,str,num,n,arr);
    }
    public static void main(String[] ages) {
        Ex0140402 ex0070201 = new Ex0140402();
        ex0070201.examina("1,2,6,4,5,3", 15);
    }
}
