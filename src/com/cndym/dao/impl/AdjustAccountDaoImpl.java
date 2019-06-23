package com.cndym.dao.impl;

import com.cndym.bean.user.AdjustAccount;
import com.cndym.dao.IAdjustAccountDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:25
 */
@Repository
public class AdjustAccountDaoImpl extends GenericDaoImpl<AdjustAccount> implements IAdjustAccountDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }


    @Override
    public Map<String, Object> customSql(String sql, Object[] para) {
        return jdbcTemplate.queryForMap(sql, para);
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
    public int updateAjustAccount(AdjustAccount adjustAccount) {

        return 0;
    }

    @Override
    public PageBean getAdjustAccountByAdjustId(String adjustId) {
        String sql = "From AdjustAccount ad,Member m where ad.userCode=m.userCode and ad.adjustId=?";
        return getPageBean(sql, new String[]{adjustId}, 1, 1);
    }

    @Override
    public boolean isSendAmountByUser(String userCode, String backup1) {
        String sql = "select id from USER_ADJUST_ACCOUNT where user_code = ? and backup1 =?";
        List list = jdbcTemplate.queryForList(sql,new Object[]{userCode,backup1});
        if(Utils.isNotEmpty(list)){
            return true;
        }
        return false;
    }
}
