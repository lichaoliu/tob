package com.cndym.service.impl;

import com.cndym.bean.other.BalanceAlarm;
import com.cndym.dao.IBalanceAlarmDao;
import com.cndym.service.IBalanceAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-4-16
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BalanceAlarmServiceImpl extends GenericServiceImpl<BalanceAlarm, IBalanceAlarmDao> implements IBalanceAlarmService {
    @Autowired
    IBalanceAlarmDao balanceAlarmDao;

    @Override
    public List<BalanceAlarm> getBalanceAlarmList(String sid) {
        return balanceAlarmDao.getBalanceAlarmList(sid);
    }

    @Override
    public int updateBal(BalanceAlarm balanceAlarm) {
        return balanceAlarmDao.updateBal(balanceAlarm);
    }

    @Override
    public BalanceAlarm getBalanceAlarm(Date updateTime, int count) {
        return balanceAlarmDao.getBalanceAlarm(updateTime, count);
    }
}
