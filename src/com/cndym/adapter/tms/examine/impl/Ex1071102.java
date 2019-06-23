package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberRepeatExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
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
public class Ex1071102 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1071102.class);

    @Override
    public void examina(String number, int item) {
        String[] nums = number.split(ztag);
        int temp = 0;
        NumberTicketCountExamine.defaulstNumberSortExamine("1071102", nums);
        for (String n : nums) {
            //校验格式
            NumberLengthExamine.defaultNumberLengthExamine(n, "#", 3, 0);//号码个数的验证
            String[] num = n.split("#");
            String ge = num[0];
            String shi = num[1];
            String bai = num[2];
            String[] ges = ge.split(",");
            String[] shis = shi.split(",");
            String[] bais = bai.split(",");
            if (ges.length + shis.length + bais.length <= 3) {
                throw new IllegalArgumentException("号码(" + n + ")不是复式选号");
            }
            NumberLengthExamine.defaultNumberLengthExamine(ge, ",", 1, 2);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(shi, ",", 1, 2);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(bai, ",", 1, 2);//号码个数的验证
            NumberAreaExamine.defaultNumberAraeExamine(ges, 1, 11, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(shis, 1, 11, 2);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(bais, 1, 11, 2);//号码域验证
            NumberRepeatExamine.defaultNumberRepeatExamine(new StringBuffer(ge).append(",").append(shi).append(",").append(bai).toString(), ",", 0);

//        排重的注数算法
//        int temp = itemCount(n);
            temp += ges.length * shis.length * bais.length;
        }
        if (temp != item) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }


    private static int itemCount(String numberStr) {
        List<String> strings = action(numberStr);
        int count = 0;
        for (String string : strings) {
            if (string.split("_").length == 4) {
                count++;
            }
        }
        return count;
    }


    public static List<String> action(String numberStr) {
        String[] fArr = numberStr.split("\\#");
        List<String> resultArray = new ArrayList<String>();
        List<String> resultTemp = new ArrayList<String>();
        for (int i = 0; i < fArr.length; i++) {
            List<String> list = new ArrayList<String>();
            if (i == 0) {
                for (int j = 0; j < fArr[0].split(",").length; j++) {
                    list.add("_" + fArr[0].split(",")[j] + "_");
                }
            } else {
                String[] tb = fArr[i].split(",");
                for (int j = 0; j < tb.length; j++) {
                    resultTemp = resultArray.subList(0, resultArray.size());
                    for (int k = 0; k < resultTemp.size(); k++) {
                        if (!resultTemp.get(k).contains("_" + tb[j] + "_")) {
                            list.add(resultTemp.get(k) + tb[j] + "_");
                        }
                    }
                }

            }
            resultArray.addAll(list);
        }
        return resultArray;
    }

    public static void main(String[] args) {
        Ex1071102 ex0063001 = new Ex1071102();
        ex0063001.examina("01,09#02#11;01#06,09#03", 4);
        System.out.println("ok");
    }

}
