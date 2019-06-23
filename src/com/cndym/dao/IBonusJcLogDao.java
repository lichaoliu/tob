package com.cndym.dao;

import com.cndym.bean.tms.BonusJcLog;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-5-29
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
public interface IBonusJcLogDao extends IGenericDao<BonusJcLog> {
    public BonusJcLog getBonusJcLogForPare(String issue, String sn, String week);

    public BonusJcLog getBonusJcLogForJcId(Long id, String lotteryCode);
}
