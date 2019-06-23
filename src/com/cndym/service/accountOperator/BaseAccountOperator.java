package com.cndym.service.accountOperator;

import com.cndym.bean.user.Account;

import java.util.Map;

/**
 * 作者：邓玉明
 * 时间：11-6-3 上午11:20
 * QQ：757579248
 * email：cndym@163.com
 */
public class BaseAccountOperator implements IAccountOperator {

    @Override
    public boolean execute(Account account, double amount, String orderId, String sid, String type, String memo, String edit) {
        return false;
    }

    @Override
    public boolean execute(Account account, double amount, String orderId, String sid) {
        return false;
    }


    @Override
    public boolean execute(Account account, double amount, String orderId, String reEventCode, String ticketId, String sid) {
        return false;
    }

}
