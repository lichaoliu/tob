package com.cndym.dao.impl;

import com.cndym.bean.tms.SubGame;
import com.cndym.dao.ISubGameDao;
import com.cndym.utils.DateUtils;
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

/**
 * User:MengJingyi Date:2011-5-18 Time:下午03:57:45 Description:
 */

@Repository
public class SubGameDaoImpl extends GenericDaoImpl<SubGame> implements ISubGameDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private SessionFactory sessionFactoryTemp;

    @PostConstruct
    private void sessionFactoryInit() {
        super.setSessionFactory(sessionFactoryTemp);
    }

    @Override
    public PageBean getSubGameList(SubGame subGame, Integer page, Integer pageSize) {

        StringBuffer sql = new StringBuffer("From SubGame subGame where 1=1");
        List<HibernatePara> hibernateParas = new ArrayList<HibernatePara>();
        if (Utils.isNotEmpty(subGame.getIssue())) {
            sql.append(" and subGame.issue=?");
            hibernateParas.add(new HibernatePara(subGame.getIssue()));
        }
        if (Utils.isNotEmpty(subGame.getLotteryCode())) {
            sql.append(" and subGame.lotteryCode=?");
            hibernateParas.add(new HibernatePara(subGame.getLotteryCode()));
        }
        sql.append(" order by index asc");
        return getPageBeanByPara(sql.toString(), hibernateParas, page, pageSize);

    }


    @Override
    public List<SubGame> getSubGameListByLotteryCodeIssue(String lotteryCode, String issue) {
        String sql = "From SubGame where lotteryCode=? and issue=? order by index asc";
        return find(sql, new String[]{lotteryCode, issue});
    }

    @Override
    public SubGame getSubGameByIssueId(String issueId) {
        return null;
    }

    public SessionFactory getSessionFactoryTemp() {
        return sessionFactoryTemp;
    }

    public void setSessionFactoryTemp(SessionFactory sessionFactoryTemp) {
        this.sessionFactoryTemp = sessionFactoryTemp;
    }

    @Override
    public int updateSubGameList(SubGame subGame) {
        StringBuffer sql = new StringBuffer("update tms_sub_game set id=" + subGame.getId());
        if (Utils.isNotEmpty(subGame.getGuestName())) {
            sql.append(" ,guest_name='" + subGame.getGuestName() + "'");
        }
        if (Utils.isNotEmpty(subGame.getMasterName())) {
            sql.append(" ,master_name='" + subGame.getMasterName() + "'");
        }
        if (Utils.isNotEmpty(subGame.getIssue())) {
            sql.append(" ,issue='" + subGame.getIssue() + "'");
        }
        if (Utils.isNotEmpty(subGame.getLeageName())) {
            sql.append(" ,leage_name='" + subGame.getLeageName() + "'");
        }
        if (Utils.isNotEmpty(subGame.getLotteryCode())) {
            sql.append(" ,lottery_code='" + subGame.getLotteryCode() + "'");
        }
        if (Utils.isNotEmpty(subGame.getStartTime())) {
            sql.append(" ,start_date='" + DateUtils.formatDate2Str(subGame.getStartTime()) + "'");
        }
        if (Utils.isNotEmpty(subGame.getEndTime())) {
            sql.append(" ,end_date='" + DateUtils.formatDate2Str(subGame.getEndTime()) + "'");
        }
        if (Utils.isNotEmpty(subGame.getFinalScore())) {
            sql.append(" ,FINAL_SCORE='" + subGame.getFinalScore() + "'");
        }
        if (Utils.isNotEmpty(subGame.getResult())) {
            sql.append(" ,RESULT='" + subGame.getResult() + "'");
        }
        if (Utils.isNotEmpty(subGame.getResultDes())) {
            sql.append(" ,RESULT_DES='" + subGame.getResultDes() + "'");
        }
        if (Utils.isNotEmpty(subGame.getScoreAtHalf())) {
            sql.append(" ,SCORE_AT_HALF='" + subGame.getScoreAtHalf() + "'");
        }
        if (Utils.isNotEmpty(subGame.getSecondHalfTheScore())) {
            sql.append(" ,SECOND_HALF_THE_SCORE='" + subGame.getSecondHalfTheScore() + "'");
        }
        sql.append(" where id=" + subGame.getId());
        return jdbcTemplate.update(sql.toString());
    }

    @Override
    public void deleteByLottery(String lotteryCode, String issue) {
        List<SubGame> subGames = getSubGameListByLotteryCodeIssue(lotteryCode, issue);
        for (SubGame subGame : subGames) {
            delete(subGame);
        }
    }

    @Override
    public boolean saveAllSubGame(List<SubGame> subGameList) {
        return saveAllObject(subGameList);
    }
}