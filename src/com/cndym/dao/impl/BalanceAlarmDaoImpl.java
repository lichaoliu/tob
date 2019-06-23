package com.cndym.dao.impl;

import com.cndym.bean.other.BalanceAlarm;
import com.cndym.dao.IBalanceAlarmDao;
import com.cndym.utils.Utils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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
@Repository
public class BalanceAlarmDaoImpl extends GenericDaoImpl<BalanceAlarm> implements IBalanceAlarmDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
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

    @Override
    public List<BalanceAlarm> getBalanceAlarmList(String sid) {
        List<BalanceAlarm> list = jdbcTemplate.query("select * from other_balance_alarm where sid=?", new Object[]{sid}, ParameterizedBeanPropertyRowMapper.newInstance(BalanceAlarm.class));
        return list;
    }

    public int updateBal(BalanceAlarm balanceAlarm) {
        StringBuffer sql = new StringBuffer("");
        if (Utils.isNotEmpty(balanceAlarm)) {
            String sid = balanceAlarm.getSid();
            if (Utils.isEmpty(sid)) {
                return 0;
            }
            List<BalanceAlarm> list = getBalanceAlarmList(sid);
            if (Utils.isNotEmpty(list)) {
                sql.append("update other_balance_alarm set sid=");
                sql.append(sid);
                if (Utils.isNotEmpty(balanceAlarm.getMobile())) {
                    sql.append(" ,mobile='");
                    sql.append(balanceAlarm.getMobile());
                    sql.append("'");
                }
                if (Utils.isNotEmpty(balanceAlarm.getEmail())) {
                    sql.append(" ,email='");
                    sql.append(balanceAlarm.getEmail());
                    sql.append("'");
                }
                if (Utils.isNotEmpty(balanceAlarm.getBalanceAmount())) {
                    sql.append(" ,balance_amount='");
                    sql.append(balanceAlarm.getBalanceAmount());
                    sql.append("'");
                }
                if (Utils.isNotEmpty(balanceAlarm.getContext())) {
                    sql.append(" ,context='");
                    sql.append(balanceAlarm.getContext());
                    sql.append("'");
                }
                sql.append(" where sid='");
                sql.append(sid);
                sql.append("'");
                return jdbcTemplate.update(sql.toString());
            } else {
                balanceAlarm.setCreateTime(new Date());
                boolean flag = super.save(balanceAlarm);
                return true == flag ? 1 : 0;
            }
        }
        return 0;
    }

    @Override
    public BalanceAlarm getBalanceAlarm(Date updateTime, int count) {
        StringBuffer sql = new StringBuffer("");
        BalanceAlarm balanceAlarm = new BalanceAlarm();
        Object obj[] = new Object[2];
        if (Utils.isNotEmpty(updateTime)) {
            obj[0] = updateTime;
        } else if (Utils.isNotEmpty(count)) {

        }

        jdbcTemplate.query("select * from other_balance_alarm where sid=?", obj, ParameterizedBeanPropertyRowMapper.newInstance(BalanceAlarm.class));
        return balanceAlarm;
    }
}
