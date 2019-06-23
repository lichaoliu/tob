package com.cndym.dao;

import com.cndym.bean.tms.MainIssue;
import com.cndym.utils.hibernate.PageBean;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: 邓玉明 Date: 11-3-27 下午10:47
 */
public interface IMainIssueDao extends IGenericDao<MainIssue> {

    public MainIssue getMainIssueForUpdate(String lotteryCode, String issue);

    /**
     * 取彩种的当前期
     *
     * @param lotteryCode
     * @param issue
     * @return
     */
    public List<MainIssue> getStartMainIssueByLotteryIssue(String lotteryCode, String issue);

    public MainIssue getMainIssueByLotteryIssue(String lotteryCode, String issue);

    public MainIssue getMainIssueByLotteryIssue(String lotteryCode);

    public void doUpdateStatus(int status, int sendStatus, int bonusStatus, long id);

    public List<MainIssue> getMainIssuesByStatusForBonusQuery(MainIssue mainIssue);

    public PageBean findIssueForStatus(MainIssue mainIssue, int pageSize, int page, String order);

    public List getIssueListByLotteryAndTime(String lotteryCode);

    public int updateIssue(MainIssue issue);

    public MainIssue getMainIssueEndForCreate(String lotteryCode);

    /**
     * 根据彩种、销售状态、开奖状态获取期次列表
     *
     * @param lotteryCode    彩种
     * @param status         销售状态
     * @param operatorsAward 开奖状态
     * @return
     */
    public List getIssueListByStatusAndAward(String lotteryCode, Integer status, Integer operatorsAward);

    /**
     * 根据彩种、算奖状态、返奖状态获取期次列表
     *
     * @param lotteryCode    彩种
     * @param bonusStatus    返奖状态
     * @param operatorsAward 算奖状态
     * @return
     */
    public List<MainIssue> getIssueListByAwardAndBonus(String lotteryCode, Integer operatorsAward, Integer bonusStatus);

    /**
     * 获取最近派奖完成的彩期
     *
     * @param lotteryCode
     * @return
     */
    public MainIssue getIssueOfLastBonus(String lotteryCode);

    /**
     * 根据彩种获取待算奖的期次列表
     *
     * @param lotteryCode 彩种
     * @return
     */
    public List getIssueListForCalAward(String lotteryCode);

    /**
     * 获取最近算奖完成的彩期
     *
     * @param lotteryCode
     * @return
     */
    public MainIssue getIssueOfLastAward(String lotteryCode);

    public PageBean getIssueList(MainIssue issue, Integer page, Integer pageSize);

    public List getIssueListByEndTime(String statusTime, String endTime, String issueStatus);

    public PageBean getIssueList(MainIssue issue, Integer page, Integer pageSize, String order);

    public Boolean deleteAll(String[] ids);

    /**
     * 根据条件对彩种游戏的当前期及历史\未来各期进行查询
     *
     * @param lotteryCode    彩种编号
     * @param status         状态
     * @param issueNameStart 开始期
     * @param issueNameEnd   结束期
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return 彩种列表
     */
    public List getLotteryTotalIssue(String lotteryCode, Integer status, String issueNameStart, String issueNameEnd, String startTime, String endTime);

    /**
     * 根据彩种和状态获取期次列表
     *
     * @param lotteryCode 彩种
     * @param status      状态
     * @return 期次列表
     */
    public List getIssueListByLotteryAndStatus(String lotteryCode, Integer status);

    /**
     * 根据彩种获取销售中彩期
     *
     * @param lotteryCode 彩种
     * @return
     */
    public List getStartIssueNameForEditFootball(String lotteryCode);

    /**
     * 获取已录入未开售彩期列表
     *
     * @param lotteryCode 彩种
     * @return
     */
    public List getWaitIssueNameForEditFootBall(String lotteryCode);

    /**
     * 获取预售期列表
     *
     * @param lotteryCode 彩种
     * @return
     */
    public List getPreIssueNameForEditFootBall(String lotteryCode);

    /**
     * 获取最近3个待录入彩期
     *
     * @param lotteryCode 彩种
     * @return
     */
    public List getNotIssueNameForEditFootBall(String lotteryCode);

    /**
     * 数字彩获取当前期
     *
     * @param lotteryCode
     * @return
     */
    public MainIssue getStartIssueByLottery(String lotteryCode);

    public List<MainIssue> getOnTimeIssue(String lotteryCode);

    public PageBean findIssueForGetBonusNumber(MainIssue mainIssue, int pageSize, int page);

    public void doUpdateBonusStatus(String lotteryCode, String issue);

    public List<MainIssue> getMainIssuesByStatusForGpBonusQuery(MainIssue mainIssue);

   public List<Map<String, Object>> getnoWinIssueList(int issueStatus, int bonusStatus);
}
