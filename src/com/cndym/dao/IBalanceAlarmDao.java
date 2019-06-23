package com.cndym.dao;

import com.cndym.bean.other.BalanceAlarm;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-4-16
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public interface IBalanceAlarmDao extends IGenericDao<BalanceAlarm> {
    public List<BalanceAlarm> getBalanceAlarmList(String sid);

    public int updateBal(BalanceAlarm balanceAlarm);

    public BalanceAlarm getBalanceAlarm(Date updateTime, int count);
}
