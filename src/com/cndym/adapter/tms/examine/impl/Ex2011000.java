package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.bean.SubNumberInfo;
import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
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
public class Ex2011000 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex2011000.class);

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
                    if (!com.cndym.constant.JingJiChuanGuan.getLanQiuSfc(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount4) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 1, 6, 11, 16, 2);//号码域验证
                } else if ("01".equals(playCode)) {
                    if (!com.cndym.constant.JingJiChuanGuan.getLanQiuSf(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount8) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 1, 2, 1);//号码域验证
                } else if ("02".equals(playCode)) {
                    if (!com.cndym.constant.JingJiChuanGuan.getLanQiuRfsf(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(cg) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount8) ||
                            (!"1*1".equals(cg) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 1, 2, 1);//号码域验证
                } else if ("04".equals(playCode)) {
                    if (!com.cndym.constant.JingJiChuanGuan.getLanQiuDxf(cg)) {
                        throw new IllegalArgumentException("不支持的串关方式(" + cg + ")");
                    }
                    if (("1*1".equals(msg[1]) && msg[0].split(";").length > JingJiChuanGuan.jingJiCcCount8) ||
                            (!"1*1".equals(msg[1]) && ccCount != msg[0].split(";").length)) {
                        throw new IllegalArgumentException("场次数不合法（" + msg[0].split(";").length + "）应该为（" + ccCount + ")");
                    }
                    NumberAreaExamine.defaultNumberAraeExamine(xx, 1, 2, 1);//号码域验证
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


    public static void main(String[] ages) {
        Ex2011000 ex2000400 = new Ex2011000();
        ex2000400.examina("20121115301:01:1;20121115302:02:1,2|2*1", 2);
    }
}
