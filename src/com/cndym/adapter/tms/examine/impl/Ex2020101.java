package com.cndym.adapter.tms.examine.impl;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;
import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: mengjingyi
 * Date: 12-2-10
 * Time: 下午4:12
 */
@Component
public class Ex2020101 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex2020101.class);

    @Override
    public void examina(String number, int item) {
        String[] msg = number.split(":");
        if (msg.length != 2) {
            throw new CndymException(ErrCode.E8111);
        }
        String issue = msg[0];
        String num = msg[1];
        if (num.split(",").length > 1) {
            throw new CndymException(ErrCode.E8111);
        }
        if (issue.length() != 12) {
            logger.info("issue长度不为12");
            throw new CndymException(ErrCode.E8111);
        } else if (!"201407141001".equals(issue)) {
            logger.info("投注串期次错误：" + issue);
            throw new CndymException(ErrCode.E8111);
        }
        NumberAreaExamine.defaultNumberAraeExamine(num.split(","), 1, 32, 2);//号码域验证
        if (1 != item) {
            logger.info("实际注数(" + 1 + ")不等于传入(" + item + ")");
            throw new CndymException(ErrCode.E8116);
        }
    }

    public static void main(String[] ages) {
        Ex2020101 ex2000400 = new Ex2020101();
        ex2000400.examina("201405246001:03", 1);
    }
}
