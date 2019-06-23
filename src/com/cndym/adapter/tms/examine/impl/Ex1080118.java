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

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-10-28
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */

@Component
public class Ex1080118 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex1080118.class);

    @Override
    public void examina(String number, int item) {
        //1,2@3,4,5,6
        /* 排列3玩法从0～9中选择M（0<M<=2)个不重复数字作为胆码，选择N（N＋M>=4）个不重复、且不包含在胆码中的数字作为拖码，
        组合成P(3,3)×C[(3-M),N]个直选投注，票面上标明“直选组合胆拖票”。
                例如，胆码为数字1，拖码为2、3、4。该直选组合胆拖票是把123、132、321、312、213、
                231、124、142、214、241、412、421、134、143、341、314、431、413这18注号码在一张彩票上表现出来。
        */

        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);

        /* String[] nums = number.split(ztag);
        NumberTicketCountExamine.defaulstNumberSortExamine("1080118", nums);
        int temp = 0;
        for (String num : nums) {
            NumberLengthExamine.defaultNumberLengthExamine(num, "@", 2, 0);//号码个数的验证
            String[] numMsg = num.split("@");
            String danL = numMsg[0];
            String tuoL = numMsg[1];
            NumberLengthExamine.defaultNumberLengthExamine(danL, ",", 0, 1);//号码个数的验证
            NumberLengthExamine.defaultNumberLengthExamine(danL, ",", 3, -1);//号码个数的验证

            NumberAreaExamine.defaultNumberAraeExamine(danL.split(","), 0, 9, 1);//号码域验证
            NumberAreaExamine.defaultNumberAraeExamine(tuoL.split(","), 0, 9, 1);//号码域验证

            String[] dans = danL.split(",");
            String[] tuos = tuoL.split(",");
            int count = dans.length + tuos.length;
            if (count < 4) {
                throw new IllegalArgumentException("胆码+拖码必须大于4，目前等于" + count);
            }
            NumberRepeatExamine.defaultNumberRepeatExamine(danL + "," + tuoL, ",", 0);//验证号码是否有重复
            //TODO 重点测试
            temp += C(tuos.length, 3 - dans.length) * 6;
        }

        //和值计算注数
        if (item != temp) {
            logger.info("实际注数(" + temp + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }*/
    }

    public static void main(String[] ages) {
        Ex1080118 ex1080118 = new Ex1080118();
        ex1080118.examina("3@2,6,7", 4);
    }

}
