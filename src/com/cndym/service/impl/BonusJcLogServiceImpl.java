package com.cndym.service.impl;

import com.cndym.bean.tms.BonusJcLog;
import com.cndym.dao.IBonusJcLogDao;
import com.cndym.service.IBonusJcLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-5-29
 * Time: 下午5:53
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BonusJcLogServiceImpl extends GenericServiceImpl<BonusJcLog, IBonusJcLogDao> implements IBonusJcLogService {
    @Autowired
    private IBonusJcLogDao bonusJcLogDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(bonusJcLogDao);
    }

    @Override
    public BonusJcLog getBonusJcLogForPare(String issue, String sn, String week) {
        return bonusJcLogDao.getBonusJcLogForPare(issue, sn, week);
    }

    @Override
    public BonusJcLog getBonusJcLogForJcId(Long id, String lotteryCode) {
        return bonusJcLogDao.getBonusJcLogForJcId(id, lotteryCode);
    }

}
