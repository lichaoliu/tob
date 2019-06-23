package com.cndym.dao;

import com.cndym.bean.tms.SubGame;
import com.cndym.utils.hibernate.PageBean;

import java.util.List;


/**
 *User:MengJingyi
 *Date:2011-5-18
 *Time:下午03:54:55
 *Description: 
 */
public interface ISubGameDao extends IGenericDao<SubGame>{
	public SubGame getSubGameByIssueId(String issueId);
	public PageBean getSubGameList(SubGame subGame, Integer page, Integer pageSize);
	public int updateSubGameList(SubGame subGame);
    public List<SubGame> getSubGameListByLotteryCodeIssue(String lotteryCode, String issue);
    public void deleteByLottery(String lotteryCode, String issue);
    public boolean saveAllSubGame(List<SubGame> subGameList);
}
