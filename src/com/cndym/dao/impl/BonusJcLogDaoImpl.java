package com.cndym.dao.impl;

import com.cndym.bean.tms.BonusJcLog;
import com.cndym.dao.IBonusJcLogDao;
import com.cndym.utils.Utils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 13-5-29
 * Time: 下午5:55
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BonusJcLogDaoImpl extends GenericDaoImpl<BonusJcLog> implements IBonusJcLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public BonusJcLog getBonusJcLogForPare(String issue, String sn, String week) {
        String sql = "From BonusJcLog where issue=? and sn=? and week=?";
        return (BonusJcLog) find(sql, new Object[]{issue, sn, week});
    }

    @Override
    public BonusJcLog getBonusJcLogForJcId(Long id, String lotteryCode) {
        String sql = "From BonusJcLog where subForIssueId=? and lotteryCode=?";
        List<BonusJcLog> bonusJcLogList = find(sql, new Object[]{id, lotteryCode});
        BonusJcLog bonusJcLog = null;
        if (Utils.isNotEmpty(bonusJcLogList)) {
            bonusJcLog = bonusJcLogList.get(0);
        }
        return bonusJcLog;
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }
}
