package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.PatternUtil;
import com.cndym.adapter.tms.split.BaseSplitTicket;
import com.cndym.constant.JingJiChuanGuan;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-10
 * Time: 下午4:12
 */
@Component
public class Ex2001000 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex2001000.class);

    @Override
    public void examina(String number, int item) {
        String[] msg = number.split("\\|");
        String cg = msg[1];
        Integer ccCount = new Integer(cg.substring(0, 1));
        List list = new ArrayList();
        Map<String, String> playCodeMap = new HashMap<String, String>();
        int itemCount = 0;
        for (String num : msg[0].split(";")) {
            String[] n = num.split(":");
            String cc = n[0];
            String playCode = n[1];
            playCodeMap.put(playCode, playCode);
            NumberRepeatExamine.defaultNumberRepeatExamine(n[2], ",", 0);
            if (cc.length() != 11 || list.contains(cc)) {
                throw new IllegalArgumentException("场次(" + cc + "不合法)");
            }
            list.add(cc);
            for (String xx : n[2].split(",")) {
                if ("03".equals(playCode)) {
                    if (!com.cndym.constant.JingJiChuanGuan.getZuQiuSfBqc(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount4) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    if ("02".equals(xx) || "12".equals(xx) || "32".equals(xx)) {
                        throw new IllegalArgumentException("投注号码范围有问题(" + xx + ")");
                    }
                    this.getNumberAraeExamine03(xx);//号码域验证
                } else if ("04".equals(playCode)) {
                    if (!com.cndym.constant.JingJiChuanGuan.getZuQiuBf(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount4) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    this.getNumberAraeExamine04(xx);//号码域验证
                } else if ("02".equals(playCode)) {
                    if (!com.cndym.constant.JingJiChuanGuan.getZuQiuZjq(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount6) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 0, 7, 1);//号码域验证
                } else if ("01".equals(playCode) || "05".equals(playCode)) {
                    if (!JingJiChuanGuan.getZuQiuSpf(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount8) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    if ("2".equals(xx)) {
                        throw new IllegalArgumentException("投注号码范围有问题(" + xx + ")");
                    }
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 0, 3, 1);//号码域验证
                } else {
                    throw new IllegalArgumentException("玩法不存在（" + playCode + "）");
                }
            }
            BaseSplitTicket baseSplitTicket = new BaseSplitTicket();
            String[] numbers = msg[0].split(";");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < numbers.length; i++) {
                String[] ss = numbers[i].split(":");
                sb.append(ss[0]);
                sb.append(":");
                sb.append(ss[2]);
                if (numbers.length - 1 != i) {
                    sb.append(";");
                }
            }
            String nm = sb.toString() + "|" + msg[1];
            List<SubNumberInfo> subNumberInfos = baseSplitTicket.splitTicket(nm);
            int temp = 0;
            for (SubNumberInfo subNumberInfo : subNumberInfos) {
                temp += subNumberInfo.getItem();
            }
            itemCount = temp;

        }
        if (playCodeMap.size() <= 1) {
            throw new IllegalArgumentException("混投必须两种玩法");
        }
        if (itemCount != item) {
            logger.info("实际注数(" + itemCount + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    private void getNumberAraeExamine03(String number) {
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

    private void getNumberAraeExamine04(String number) {
        if (PatternUtil.regValidate(number, "[\\d]{" + 2 + "}") == false) {
            throw new IllegalArgumentException("号码(" + number + ")不为长度为" + 2 + "位的数字");
        }
        long len = Long.parseLong(number);
        boolean flag = false;
        if ((len <= 5 && len >= 0) || (len <= 15 && len >= 10) ||
                (len <= 25 && len >= 20) || (len <= 33 && len >= 30) ||
                (len <= 42 && len >= 40) || (len <= 52 && len >= 50) ||
                len == 90 || len == 99 || len == 9) {
            flag = true;
        }
        if (!flag) {
            throw new IllegalArgumentException("投注号码范围有问题(" + number + ")");
        }
    }

    public static void main(String[] ages) {
        Ex2001000 ex2000400 = new Ex2001000();
        ex2000400.examina("20130614001:01:3;20130614002:05:3|2*1", 1);
    }
}
