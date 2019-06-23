package com.cndym.dao.impl;

import com.cndym.bean.tms.PostBonus;
import com.cndym.dao.IPostBonusDao;
import com.cndym.utils.Utils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * User: mcs
 * Date: 12-11-13
 * Time: 上午10:31
 */

@Repository
public class PostBonusDaoImpl extends GenericDaoImpl<PostBonus> implements IPostBonusDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }


    @Override
    public int updateStatusForEndBonus(String lotteryCode, String issue, String postCode) {
        String sql = "update tms_post_bonus set status = ?,accept_time = ? where lottery_code = ? and issue = ? and post_code = ? ";
        return jdbcTemplate.update(sql, new Object[]{1, new Date(), lotteryCode, issue, postCode});
    }

    @Override
    public PostBonus getPostBonus(String lotteryCode, String issue, String postCode) {
        String sql = "from PostBonus where lottery_code = ? and issue = ? and post_code = ? ";
        List<PostBonus> postBonusList = find(sql, new Object[]{lotteryCode, issue, postCode});
        if (Utils.isNotEmpty(postBonusList)) {
            return postBonusList.get(0);
        }
        return null;
    }
}
