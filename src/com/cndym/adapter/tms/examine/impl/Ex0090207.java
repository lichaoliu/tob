package com.cndym.adapter.tms.examine.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.adapter.tms.examine.utils.NumberLengthExamine;
import com.cndym.adapter.tms.examine.utils.NumberTagExamine;
import com.cndym.adapter.tms.examine.utils.NumberTicketCountExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;

@Component
public class Ex0090207 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0090207.class);

    @Override
    public void examina(String number, int item) {
        logger.error("玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
//        String[] nums = number.split(ztag);
//        NumberTicketCountExamine.defaulstNumberSortExamine("0090207", nums);
//        int ticket = 0;
//        for (String num : nums) {
//            //校验格式
//            NumberTagExamine.commaNumberTagExamine(num);//号码中分隔符的验证;
//            NumberLengthExamine.defaultNumberLengthExamine(num, ",", 2, 0);//号码个数的验证
//            NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 0, 9, 1);//号码域验证
//            ticket += 2;
//        }
//
//        // 校验注数
//        if (item != ticket) {
//            throw new CndymException(ErrCode.E8116);
//        }
    }

    public static void main(String[] args) {
        Ex0090207 ex = new Ex0090207();
        ex.examina("1,2;2,3", 4);
        System.out.println("ok");
    }
}
