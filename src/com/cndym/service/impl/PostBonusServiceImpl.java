package com.cndym.service.impl;

import com.cndym.bean.tms.PostBonus;
import com.cndym.dao.IPostBonusDao;
import com.cndym.service.IPostBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * User: mcs
 * Date: 12-11-13
 * Time: 上午10:37
 */
@Service
public class PostBonusServiceImpl extends GenericServiceImpl<PostBonus, IPostBonusDao> implements IPostBonusService {

    @Autowired
    private IPostBonusDao postBonusDao;

    @PostConstruct
    public void initDao() {
        super.setDaoImpl(postBonusDao);
    }

    @Override
    public int updateStatusForEndBonus(String lotteryCode, String issue, String postCode) {
        return postBonusDao.updateStatusForEndBonus(lotteryCode, issue, postCode);
    }

    @Override
    public PostBonus getPostBonus(String lotteryCode, String issue, String postCode) {
        return postBonusDao.getPostBonus(lotteryCode, issue, postCode);
    }
}
