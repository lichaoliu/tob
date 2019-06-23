package com.cndym.exception;

import org.apache.log4j.Logger;

/**
 * 作者：邓玉明
 * 时间：10-12-20 上午10:20
 * QQ：757579248
 * email：cndym@163.com
 */
public class CndymException extends RuntimeException {
    private static final long serialVersionUID = -4365630128856068164L;
    private Logger logger = Logger.getLogger(getClass());
    public CndymException(String code) {
        super(code);
        alarm(code);
    }

    private void alarm(String code){
        //logger.error("code="+code+";msg="+ ErrCode.codeToMsg(code));

        //报警
    }
}
