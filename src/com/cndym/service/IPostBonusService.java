package com.cndym.service;

import com.cndym.bean.tms.PostBonus;

/**
 * User: mcs
 * Date: 12-11-13
 * Time: 上午10:36
 */
public interface IPostBonusService extends IGenericeService<PostBonus> {

    public int updateStatusForEndBonus(String lotteryCode, String issue, String postCode);

    public PostBonus getPostBonus(String lotteryCode, String issue, String postCode);
}
