package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.PatternUtil;
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
public class Ex4000400 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex4000400.class);

    @Override
    public void examina(String number, int item) {
    	Map<String, String> snMap = new HashMap<String, String>();
    	String[] msg = number.split("\\|");
    	NumberLengthExamine.defaultNumberLengthExamine(msg[0], ";", 99, 99);// 验证场次个数

        String cg = msg[1];
        if (!BeiDanChuanGuan.getBeiDanBQC(cg)) {
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
            
            NumberLengthExamine.defaultNumberLengthExamine(n[1], ",", 99, 99);// 号码个数的验证
            NumberRepeatExamine.defaultNumberRepeatExamine(n[1], ",", 0);
            for (String xx : n[1].split(",")) {
                if ("02".equals(xx) || "12".equals(xx) || "32".equals(xx)) {
                    throw new IllegalArgumentException("投注号码范围有问题(" + xx + ")");
                }
                this.getNumberAraeExamine(xx);//号码域验证
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

    private void getNumberAraeExamine(String number) {
        if (PatternUtil.regValidate(number, "[\\d]{" + 2 + "}") == false) {
            throw new IllegalArgumentException("号码(" + number + ")不为长度为" + 2 + "位的数字");
        }
        long len = Long.parseLong(number);
        boolean flag = false;
        if ((len <= 3 && len >= 0) || (len <= 13 && len >= 10) || (len <= 33 && len >= 30)) {
            flag = true;
        }
        if (!flag) {
            throw new IllegalArgumentException("投注号码范围有问题(" + number + ")");
        }
    }

    public static void main(String args[]) {
    	Ex4000400 ex = new Ex4000400();
    	ex.examina("1:33,11,10;2:33|2*1", 3);
    	System.out.println("ok");
    }
}
