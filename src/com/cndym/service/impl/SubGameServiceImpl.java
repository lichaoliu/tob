package com.cndym.service.impl;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cndym.bean.tms.SubGame;
import com.cndym.dao.ISubGameDao;
import com.cndym.service.ISubGameService;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User:MengJingyi
 * Date:2011-5-18
 * Time:下午04:10:13
 * Description:
 */

@Service
public class SubGameServiceImpl extends GenericServiceImpl<SubGame, ISubGameDao> implements ISubGameService {

    @Autowired
    private ISubGameDao subGameDao;

    @Override
    public SubGame getSubGameByIssueId(String issueId) {
        return subGameDao.getSubGameByIssueId(issueId);
    }

    @Override
    public int updateSubGameList(SubGame subGame) {
        return subGameDao.updateSubGameList(subGame);
    }

    @Override
    public PageBean getSubGameList(SubGame subGame, Integer page,
                                   Integer pageSize) {
        return subGameDao.getSubGameList(subGame, page, pageSize);
    }

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(subGameDao);
    }

    @Override
    public List<SubGame> getSubGameListByLotteryCodeIssue(String lotteryCode, String issue) {
        return subGameDao.getSubGameListByLotteryCodeIssue(lotteryCode, issue);
    }

    @Override
    public void deleteByLottery(String lotteryCode, String issue) {
        subGameDao.deleteByLottery(lotteryCode, issue);
    }

    @Override
    public boolean doSaveAllObject(List<SubGame> subGameList) {
        return subGameDao.saveAllObject(subGameList);
    }
}
