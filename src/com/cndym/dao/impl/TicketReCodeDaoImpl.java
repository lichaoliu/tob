package com.cndym.dao.impl;

import com.cndym.bean.tms.TicketReCode;
import com.cndym.dao.ITicketReCodeDao;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.HibernatePara;
import com.cndym.utils.hibernate.PageBean;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: mcs
 * Date: 12-10-28
 * Time: 下午2:35
 */

@Repository
public class TicketReCodeDaoImpl extends GenericDaoImpl<TicketReCode> implements ITicketReCodeDao {

    @Resource
    private SessionFactory sessionFactoryTemp;
    @Resource
    private JdbcTemplate jdbcTemplate;


    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public PageBean getPageBeanForIssueUpdate(String lotteryCode, String issue, String matchId, Integer page, Integer pageSize) {
        StringBuilder sql = new StringBuilder("From TicketReCode where 1=1 ");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        sql.append(" and createTime>=? ");
        hibernateParas.add(new HibernatePara(calendar.getTime()));
        
        if (Utils.isNotEmpty(lotteryCode)) {
            sql.append(" and lotteryCode=?");
            hibernateParas.add(new HibernatePara(lotteryCode));
        }
        if ("400".equals(lotteryCode)) {
            if (Utils.isNotEmpty(issue)) {
                sql.append(" and issue=?");
                hibernateParas.add(new HibernatePara(issue));
            }
            if (Utils.isNotEmpty(matchId)) {
                sql.append(" and matchId=?");
                hibernateParas.add(new HibernatePara(matchId));
            }
        } else {
            if (Utils.isNotEmpty(matchId)) {
                sql.append(" and matchId=?");
                hibernateParas.add(new HibernatePara(issue + matchId));
            }
        }

        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);
    }

}
