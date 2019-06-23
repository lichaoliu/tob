package com.cndym.adapter.tms.examine.impl;

import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cndym.adapter.tms.examine.BashExamina;
import com.cndym.adapter.tms.examine.IExamine;

/**
 **USER:MengJingyi
 **TIME:2011 2011-7-16 下午07:27:11
 *
 */

@Component
public class Ex0060804 extends BashExamina implements IExamine {
    private Logger logger = Logger.getLogger(Ex0060804.class);

	@Override
	public void examina(String number, int item) {
		// TODO Auto-generated method stub
        logger.info("该玩法暂不支持");
        throw new CndymException(ErrCode.E8117);
    }

}
