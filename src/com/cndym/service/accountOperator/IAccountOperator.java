package com.cndym.service.accountOperator;

import com.cndym.bean.user.Account;

import java.util.Map;

/**
 * User: 邓玉明
 * Date: 11-3-28 上午12:18
 */
public interface IAccountOperator {
    //加减款
    public boolean execute(Account account, double amount, String orderId, String sid, String type, String memo, String edit);

    //扣款;返奖
    public boolean execute(Account account, double amount, String orderId, String sid);


    //退款
    public boolean execute(Account account, double amount, String orderId, String reEventCode, String ticketId, String sid);

}
