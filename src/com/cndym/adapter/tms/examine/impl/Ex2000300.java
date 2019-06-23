package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.PatternUtil;
import com.cndym.adapter.tms.split.BaseSplitTicket;
import com.cndym.constant.JingJiChuanGuan;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-10
 * Time: 下午4:13
 */
@Component
public class Ex2000300 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex2000300.class);

    @Override
    public void examina(String number, int item) {
        String[] msg = number.split("\\|");
        String cg = msg[1];
        Integer ccCount = new Integer(cg.substring(0, 1));
        if (!com.cndym.constant.JingJiChuanGuan.getZuQiuSfBqc(cg)) {
            throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
        }
        if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount4) ||
                (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
            throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
        }
        List list = new ArrayList();
        for (String num : msg[0].split(";")) {
            String[] n = num.split(":");
            String cc = n[0];
            NumberRepeatExamine.defaultNumberRepeatExamine(n[1], ",", 0);
            if (cc.length() != 11 || list.contains(cc)) {
                throw new IllegalArgumentException("场次(" + cc + "不合法)");
            }
            list.add(cc);
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

    public static  void main(String[]ages){
        Ex2000300 ex2000300 = new Ex2000300();
        ex2000300.examina("20121123021:01|1*1",1);
    }

}
