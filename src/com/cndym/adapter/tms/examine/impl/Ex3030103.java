package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.utils.CombinUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：邓玉明
 * 时间：11-4-30 下午1:36
 * QQ：757579248
 * email：cndym@163.com
 */
@Component
public class Ex3030103 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex3030103.class);

    @Override
    public void examina(String number, int item) {
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);

        /*//校验格式
        NumberTagExamine.commaNumberTagExamine300(number);//号码中分隔符的验证
        NumberLengthExamine.defaultNumberLengthExamine(number, "@", 2, 0);//号码个数的验证
        String[] nums = number.split("@");
        String danList = nums[0];
        String tuoList = nums[1];
        String[] dans = danList.split("\\*");
        String[] tuos = tuoList.split("\\*");
        NumberLengthExamine.defaultNumberLengthExamine(danList, "\\*", 14, 0);//号码个数的验证
        NumberLengthExamine.defaultNumberLengthExamine(tuoList, "\\*", 14, 0);//号码个数的验证
        for (int i = 0; i < 14; i++) {
            String[] ds = dans[i].split("");
            for (int z = 1; z < ds.length; z++) {
                for (int j = z + 1; j < ds.length; j++) {
                    if (ds[z].equals(ds[j])) {
                        throw new IllegalArgumentException("号码有重复");
                    }
                }
            }

            String[] ts = tuos[i].split("");
            for (int z = 1; z < ds.length; z++) {
                for (int j = z + 1; j < ts.length; j++) {
                    if (ts[z].equals(ts[j])) {
                        throw new IllegalArgumentException("号码有重复");
                    }
                }
            }

        }

        int temp = 0;

        int dl = 0;
        int dItem = 1;
        int dCount = 0;
        for (int i = 0; i < dans.length; i++) {
            String numS = dans[i];
            if (4 != new Integer(numS)) {
                dl++;
                if (numS.length() > 1) {
                    dItem *= numS.length();//胆码注数
                    dCount += numS.length();//胆码总数
                } else {
                    dCount++;
                }
            }
            String[] num = numS.split("");
            for (String ns : num) {
                if ("".equals(ns)) {
                    continue;
                }
                int n = new Integer(ns);
                NumberAreaExamine.defaultNumberAraeExamine(ns, 0, 4, 1);//号码域验证
                if (n == 2) {
                    throw new IllegalArgumentException("号码(" + n + ")不合法");
                }
            }
        }

        //拖码总数
        int tCount = 0;

        //拖码列表
        List<String> newTuoList = new ArrayList<String>();
        for (String tuoNumber : tuos) {
            if (!tuoNumber.equals("4")) {
                newTuoList.add(tuoNumber);
                if (tuoNumber.length() > 1) {
                    tCount += tuoNumber.length();
                } else {
                    tCount++;
                }
            }
        }
        //拖码个数
        int tl = newTuoList.size();

        if (tl <= 0) {
            throw new IllegalArgumentException("拖码总数(" + tl + ")不大于0");
        }
        if (dl >= 9) {
            throw new IllegalArgumentException("胆码总数(" + dl + ")不小于9");
        }
        //计算拖码可组成的复式注数
        List<int[]> indexList = CombinUtils.createCombinationIndex(9 - dl, tl);

        int tuoItem = 0;
        for (int[] indexArray : indexList) {
            int subTuoItem = 1;
            for (int index : indexArray) {
                subTuoItem *= newTuoList.get(index).length();
            }
            tuoItem += subTuoItem;
        }


        int count = dCount + tCount;


        if (count < 10) {
            throw new IllegalArgumentException("号码总数(" + count + ")不大于10");

        }
        if (count > 38) {
            throw new IllegalArgumentException("号码总数(" + count + ")不小于38");

        }

        //注数
        temp += dItem * tuoItem;

        if (temp <= 1) {
            throw new IllegalArgumentException("号码(" + number + ")不是胆拖选号");
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }*/
    }


    public static void main(String[] ages) {
        Ex3030103 ex3000203 = new Ex3030103();
        ex3000203.examina("30*0*3*301*301*4*4*4*4*4*4*4*4*4@4*4*4*4*4*4*4*4*4*301*301*301*10*4", 2430);
    }
}
