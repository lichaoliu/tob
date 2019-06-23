package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberSortExamine;
import com.cndym.adapter.tms.split.BaseSplitTicket;
import com.cndym.constant.BeiDanChuanGuan;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Ex4000300 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex4000300.class);

    @Override
    public void examina(String number, int item) {
        Map<String, String> snMap = new HashMap<String, String>();
    	String[] msg = number.split("\\|");
    	NumberLengthExamine.defaultNumberLengthExamine(msg[0], ";", 99, 99);// 验证场次个数
    	
        String cg = msg[1];
        if (!BeiDanChuanGuan.getBeiDanZJQ(cg)) {
            throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
        }

        if (msg[0].split(";").length != BeiDanChuanGuan.getChangCiCount(cg)) {
            throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + BeiDanChuanGuan.getChangCiCount(cg) + ")");
        }
    	
        for (String num : msg[0].split(";")) {
            String[] n = num.split(":");
            if (snMap.containsKey(n[0])) {
            	throw new CndymException(ErrCode.E8111);
            } else {
            	snMap.put(n[0], n[0]);
            }
            NumberSortExamine.defaulstNumberSortExamine(n[1].replace("+", ""), ",");//  验证号码是否按升序排序
            NumberLengthExamine.defaultNumberLengthExamine(n[1], ",", 99, 99);//号码个数的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(n[1], ",", 0);
            for (String xx : n[1].split(",")) {
                if (xx.contains("7")) {
                	if (!"7+".equals(xx)) {
                		throw new CndymException(ErrCode.E8111);
                	}
                } else {
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 0, 7, 1);//号码域验证
                }
            }
        }
        BaseSplitTicket baseSplitTicket = new BaseSplitTicket();
        List<SubNumberInfo> subNumberInfos = baseSplitTicket.splitTicket(number);
        int temp = 0;
        for (SubNumberInfo subNumberInfo : subNumberInfos) {
            temp += subNumberInfo.getItem();
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }
    
    public static void main(String args[]) {
    	Ex4000300 ex = new Ex4000300();
    	ex.examina("1:3,5,7+;2:0,4|2*1", 6);
    	System.out.println("ok");
    }
}