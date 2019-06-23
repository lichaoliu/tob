package com.cndym.dao;

import com.cndym.bean.tms.PostBonus;

/**
 * User: mcs
 * Date: 12-11-13
 * Time: 上午10:29
 */
public interface IPostBonusDao extends IGenericDao<PostBonus> {

    public int updateStatusForEndBonus(String lotteryCode, String issue, String postCode);

    public PostBonus getPostBonus(String lotteryCode, String issue, String postCode);

}
