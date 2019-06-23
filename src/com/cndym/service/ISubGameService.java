package com.cndym.service;

import com.cndym.bean.tms.SubGame;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;

/**
 * User:MengJingyi
 * Date:2011-5-18
 * Time:下午04:09:02
 * Description:
 * 传统足彩对阵
 */
public interface ISubGameService extends IGenericeService<SubGame> {

    /**
     * 根据期次获取对阵对象
     *
     * @param issueId
     * @return
     */
    public SubGame getSubGameByIssueId(String issueId);

    /**
     * 更新对阵
     *
     * @param subGame
     * @return
     */
    public int updateSubGameList(SubGame subGame);

    /**
     * 分页查询对阵
     *
     * @param subGame
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean getSubGameList(SubGame subGame, Integer page, Integer pageSize);

    /**
     * 根据彩种，期次获取对阵列表
     *
     * @param lotteryCode
     * @param issue
     * @return
     */
    public List<SubGame> getSubGameListByLotteryCodeIssue(String lotteryCode, String issue);

    /**
     * 根据彩种，期次删除对阵
     *
     * @param lotteryCode 彩种
     * @param issue       期次
     */
    public void deleteByLottery(String lotteryCode, String issue);


    public boolean doSaveAllObject(List<SubGame> subGameList);
}
