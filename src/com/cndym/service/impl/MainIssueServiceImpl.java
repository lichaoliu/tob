package com.cndym.service.impl;

import com.cndym.Main;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.SubGame;
import com.cndym.constant.Constants;
import com.cndym.dao.IMainIssueDao;
import com.cndym.dao.IPostIssueDao;
import com.cndym.dao.ISubGameDao;
import com.cndym.dao.impl.MainIssueDaoImpl;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IMainIssueService;
import com.cndym.utils.Utils;
import com.cndym.utils.bonusClass.BonusClassUtils;
import com.cndym.utils.hibernate.PageBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * User: 邓玉明 Date: 11-3-27 下午11:02
 */
@Service
public class MainIssueServiceImpl extends GenericServiceImpl<MainIssue, IMainIssueDao> implements IMainIssueService {
    @Resource
    private IMainIssueDao mainIssueDao;
    @Resource
    private ISubGameDao subGameDao;

    @PostConstruct
    private void daoImplInit() {
        super.setDaoImpl(mainIssueDao);
    }

    @Override
    public List<MainIssue> getStartMainIssueByLotteryIssue(String lotteryCode, String issue) {
        return mainIssueDao.getStartMainIssueByLotteryIssue(lotteryCode, issue);
    }

    @Override
    public MainIssue getMainIssueByLotteryIssue(String lotteryCode, String issue) {
        return mainIssueDao.getMainIssueByLotteryIssue(lotteryCode, issue);
    }

    @Override
    public MainIssue getMainIssueByLotteryIssue(String lotteryCode) {
        return mainIssueDao.getMainIssueByLotteryIssue(lotteryCode);
    }

    @Override
    public void doUpdateStatus(int status, int sendStatus, int bonusStatus, long id) {
        mainIssueDao.doUpdateStatus(status, sendStatus, bonusStatus, id);
    }

    @Override
    public List<MainIssue> getMainIssuesByStatusForBonusQuery(MainIssue mainIssue) {
        return mainIssueDao.getMainIssuesByStatusForBonusQuery(mainIssue);
    }

    @Override
    public PageBean findIssueForGetBonusNumber(MainIssue mainIssue, int pageSize, int page) {
        return mainIssueDao.findIssueForGetBonusNumber(mainIssue, pageSize, page);
    }

    @Override
    public PageBean findIssueForStatus(MainIssue mainIssue, int pageSize, int page, String order) {
        return mainIssueDao.findIssueForStatus(mainIssue, pageSize, page, order);
    }

    @Override
    public List getIssueListByLotteryAndTime(String lotteryCode) {
        return mainIssueDao.getIssueListByLotteryAndTime(lotteryCode);
    }

    @Override
    public int doSaveIssue(MainIssue mainIssue) {
        Date endTime = mainIssue.getEndTime();
        LotteryBean lotteryBean = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryBean(mainIssue.getLotteryCode());

        Long early = lotteryBean.getLotteryClass().getEarly();
        Long danshi = lotteryBean.getLotteryClass().getDashi();

        Calendar simplexCalendar = Calendar.getInstance();
        Calendar duplexCalendar = Calendar.getInstance();

        simplexCalendar.setTime(endTime);
        duplexCalendar.setTime(endTime);
        simplexCalendar.add(Calendar.SECOND, danshi.intValue() * -1);
        duplexCalendar.add(Calendar.SECOND, early.intValue() * -1);

        mainIssue.setBonusNumber("-");
        mainIssue.setBonusClass("-");
        mainIssue.setBonusTotal(0d);
        mainIssue.setPrizePool("0");
        mainIssue.setGlobalSaleTotal("0");

        mainIssue.setSimplexTime(simplexCalendar.getTime());
        mainIssue.setDuplexTime(duplexCalendar.getTime());
        mainIssue.setStatus(Constants.ISSUE_STATUS_WAIT);
        mainIssue.setSendStatus(Constants.ISSUE_SALE_WAIT);
        mainIssue.setBonusStatus(Constants.ISSUE_BONUS_STATUS_NO);
        mainIssue.setOperatorsAward(Constants.OPERATORS_AWARD_WAIT);
        mainIssue.setCenterBonusStatus(Constants.ISSUE_CENTER_BONUS_STATUS_NO);
        mainIssue.setBonusClass(BonusClassUtils.getDefaultBonusClassByLotteryCode(mainIssue.getLotteryCode()));
        try {
            mainIssueDao.save(mainIssue);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public int doSaveIssueEx(MainIssue mainIssue) {
        Date endTime = mainIssue.getEndTime();
        LotteryBean lotteryBean = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryBean(mainIssue.getLotteryCode());

        Long early = lotteryBean.getLotteryClass().getEarly();
        Long danshi = lotteryBean.getLotteryClass().getDashi();

        Calendar simplexCalendar = Calendar.getInstance();
        Calendar duplexCalendar = Calendar.getInstance();

        simplexCalendar.setTime(endTime);
        duplexCalendar.setTime(endTime);
        simplexCalendar.add(Calendar.SECOND, danshi.intValue() * -1);
        duplexCalendar.add(Calendar.SECOND, early.intValue() * -1);

        mainIssue.setBonusNumber("-");
        mainIssue.setBonusClass("-");
        mainIssue.setBonusTotal(0d);
        mainIssue.setPrizePool("0");
        mainIssue.setGlobalSaleTotal("0");

        mainIssue.setSimplexTime(simplexCalendar.getTime());
        mainIssue.setDuplexTime(duplexCalendar.getTime());
        mainIssue.setStatus(Constants.ISSUE_STATUS_WAIT);
        mainIssue.setSendStatus(Constants.ISSUE_SALE_WAIT);
        mainIssue.setBonusStatus(Constants.ISSUE_BONUS_STATUS_NO);
        mainIssue.setOperatorsAward(Constants.OPERATORS_AWARD_WAIT);
        mainIssue.setCenterBonusStatus(Constants.ISSUE_CENTER_BONUS_STATUS_NO);
        mainIssue.setBonusClass(BonusClassUtils.getDefaultBonusClassByLotteryCode(mainIssue.getLotteryCode()));

        try {
            MainIssue oldIssue = mainIssueDao.getMainIssueByLotteryIssue(mainIssue.getLotteryCode(), mainIssue.getName());

            if (oldIssue != null) {
                if (oldIssue.getStatus() == 0) {
                    mainIssue.setId(oldIssue.getId());
                    mainIssueDao.update(mainIssue);
                }
            } else {
                mainIssueDao.save(mainIssue);
            }
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public int updateIssue(MainIssue issue) {
        return mainIssueDao.updateIssue(issue);
    }

    @Override
    public List<MainIssue> getIssueListByStatusAndAward(String lotteryCode, Integer status, Integer operatorsAward) {
        return mainIssueDao.getIssueListByStatusAndAward(lotteryCode, status, operatorsAward);
    }

    @Override
    public List<MainIssue> getIssueListByAwardAndBonus(String lotteryCode, Integer operatorsAward, Integer bonusStatus) {
        return mainIssueDao.getIssueListByAwardAndBonus(lotteryCode, operatorsAward, bonusStatus);
    }

    @Override
    public MainIssue getIssueOfLastBonus(String lotteryCode) {
        return mainIssueDao.getIssueOfLastBonus(lotteryCode);
    }

    @Override
    public List getIssueListForCalAward(String lotteryCode) {
        return mainIssueDao.getIssueListForCalAward(lotteryCode);
    }

    @Override
    public MainIssue getIssueOfLastAward(String lotteryCode) {
        return mainIssueDao.getIssueOfLastAward(lotteryCode);
    }

    @Override
    public PageBean getIssueList(MainIssue issue, Integer page, Integer pageSize) {
        return mainIssueDao.getIssueList(issue, page, pageSize);
    }

    @Override
    public List getIssueListByEndTime(String statusTime, String endTime, String issueStatus) {
        return mainIssueDao.getIssueListByEndTime(statusTime, endTime, issueStatus);
    }

    @Override
    public PageBean getIssueList(MainIssue issue, Integer page, Integer pageSize, String order) {
        return mainIssueDao.getIssueList(issue, page, pageSize, order);
    }

    @Override
    public Boolean deleteAll(String[] ids) {
        return mainIssueDao.deleteAll(ids);
    }

    @Override
    public List getLotteryTotalIssue(String lotteryCode, Integer status, String issueNameStart, String issueNameEnd, String startTime, String endTime) {
        List dataList = mainIssueDao.getLotteryTotalIssue(lotteryCode, status, issueNameStart, issueNameEnd, startTime, endTime);
        List lotteryList = new ArrayList();
        Map lotteryMap = null;
        String code = "";
        for (int index = 0; index < dataList.size(); index++) {
            lotteryMap = (Map) dataList.get(index);
            code = lotteryMap.get("lotteryCode").toString();
            lotteryMap.put("sellIssue", mainIssueDao.getIssueListByLotteryAndTime(code));
            // lotteryMap.put("gfSellIssue", mainIssueDao.getIssueListByLotteryAndStatus(code, 1));
            lotteryMap.put("preSellIssue", mainIssueDao.getIssueListByLotteryAndStatus(code, 3));
            lotteryList.add(lotteryMap);
        }
        return lotteryList;
    }

    @Override
    public List getStartIssueNameForEditFootball(String lotteryCode) {
        return mainIssueDao.getStartIssueNameForEditFootball(lotteryCode);
    }

    @Override
    public List getWaitIssueNameForEditFootBall(String lotteryCode) {
        return mainIssueDao.getWaitIssueNameForEditFootBall(lotteryCode);
    }

    @Override
    public List getNotIssueNameForEditFootBall(String lotteryCode) {
        return mainIssueDao.getNotIssueNameForEditFootBall(lotteryCode);
    }

    @Override
    public List getPreIssueNameForEditFootBall(String lotteryCode) {
        return mainIssueDao.getPreIssueNameForEditFootBall(lotteryCode);
    }

    @Override
    public boolean doUpdateIssue(MainIssue mainIssue, List<String> mainTeamList, List<String> guestTeamList, List<String> leageNameList, List<String> playTimeList) {

        Date endTime = mainIssue.getEndTime();
        LotteryBean lotteryBean = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryBean(mainIssue.getLotteryCode());

        Long early = lotteryBean.getLotteryClass().getEarly();
        Long danshi = lotteryBean.getLotteryClass().getDashi();

        Calendar simplexCalendar = Calendar.getInstance();
        Calendar duplexCalendar = Calendar.getInstance();

        simplexCalendar.setTime(endTime);
        duplexCalendar.setTime(endTime);

        simplexCalendar.add(Calendar.SECOND, danshi.intValue() * -1);
        duplexCalendar.add(Calendar.SECOND, early.intValue() * -1);

        MainIssue tempIssue = mainIssueDao.getMainIssueByLotteryIssue(mainIssue.getLotteryCode(), mainIssue.getName());
        MainIssue newIssue = null;
        if (Utils.isNotEmpty(tempIssue)) {
            newIssue = tempIssue;
        } else {
            newIssue = mainIssue;
            newIssue.setStatus(Constants.ISSUE_STATUS_WAIT);
            newIssue.setSendStatus(Constants.ISSUE_SALE_WAIT);
            newIssue.setBonusStatus(Constants.ISSUE_BONUS_STATUS_NO);
            newIssue.setOperatorsAward(Constants.OPERATORS_AWARD_WAIT);
            newIssue.setCenterBonusStatus(Constants.ISSUE_CENTER_BONUS_STATUS_NO);
            newIssue.setBonusClass(BonusClassUtils.getDefaultBonusClassByLotteryCode(mainIssue.getLotteryCode()));
        }
        newIssue.setStartTime(mainIssue.getStartTime());
        newIssue.setEndTime(mainIssue.getEndTime());
        newIssue.setSimplexTime(simplexCalendar.getTime());
        newIssue.setDuplexTime(duplexCalendar.getTime());

        List<SubGame> subGameList = new ArrayList<SubGame>();
        List<SubGame> otherGameList = new ArrayList<SubGame>();

        String lotteryCode = mainIssue.getLotteryCode();
        String issue = mainIssue.getName();
        String otherLotteryCode = "";
        if (lotteryCode.equals("300")) {
            otherLotteryCode = "303";
        } else if (lotteryCode.equals("303")) {
            otherLotteryCode = "300";
        }

        MainIssue otherMainIssue = null;
        if (lotteryCode.equals("300") || lotteryCode.equals("303")) {
            MainIssue otherTempIssue = mainIssueDao.getMainIssueByLotteryIssue(otherLotteryCode, issue);
            if (Utils.isNotEmpty(otherTempIssue)) {
                otherMainIssue = otherTempIssue;
            } else {
                otherMainIssue = new MainIssue();
                otherMainIssue.setStatus(newIssue.getStatus());
                otherMainIssue.setSendStatus(newIssue.getSendStatus());
                otherMainIssue.setBonusStatus(newIssue.getBonusStatus());
                otherMainIssue.setOperatorsAward(newIssue.getOperatorsAward());
                otherMainIssue.setCenterBonusStatus(newIssue.getCenterBonusStatus());
                otherMainIssue.setBonusClass(BonusClassUtils.getDefaultBonusClassByLotteryCode(otherLotteryCode));
            }
            otherMainIssue.setLotteryCode(otherLotteryCode);
            otherMainIssue.setName(newIssue.getName());
            otherMainIssue.setStartTime(newIssue.getStartTime());
            otherMainIssue.setEndTime(newIssue.getEndTime());
            otherMainIssue.setSimplexTime(newIssue.getSimplexTime());
            otherMainIssue.setDuplexTime(newIssue.getDuplexTime());
            otherMainIssue.setBonusTime(newIssue.getBonusTime());
        }

        List<SubGame> sfcSubGameList = subGameDao.getSubGameListByLotteryCodeIssue(lotteryCode, issue);
        Map<String, SubGame> sfcSubGameMap = new HashMap<String, SubGame>();
        Map<String, SubGame> r9SubGameMap = new HashMap<String, SubGame>();
        for (SubGame sfcSubGame : sfcSubGameList) {
            sfcSubGameMap.put(sfcSubGame.getLotteryCode() + sfcSubGame.getIssue() + sfcSubGame.getIndex(), sfcSubGame);
        }
        if (lotteryCode.equals("300") || lotteryCode.equals("303")) {
            List<SubGame> r9SubGameList = subGameDao.getSubGameListByLotteryCodeIssue(otherLotteryCode, issue);
            for (SubGame r9SubGame : r9SubGameList) {
                r9SubGameMap.put(r9SubGame.getLotteryCode() + r9SubGame.getIssue() + r9SubGame.getIndex(), r9SubGame);
            }
        }

        for (int index = 1; index <= leageNameList.size(); index++) {
            SubGame subGame = null;
            if (sfcSubGameMap.containsKey(lotteryCode + issue + index)) {
                subGame = sfcSubGameMap.get(lotteryCode + issue + index);
            } else {
                subGame = new SubGame();
                subGame.setLotteryCode(lotteryCode);
                subGame.setIssue(issue);
            }
            subGame.setLeageName(leageNameList.get(index - 1));
            subGame.setMasterName(mainTeamList.get(index - 1));
            subGame.setGuestName(guestTeamList.get(index - 1));
            subGame.setStartTime(Utils.formatDate(playTimeList.get(index - 1), "yyyy-MM-dd HH:mm:ss"));
            subGame.setIndex(index);
            subGameList.add(subGame);

            if (lotteryCode.equals("300") || lotteryCode.equals("303")) {
                SubGame otherSubGame = null;
                if (r9SubGameMap.containsKey(otherLotteryCode + issue + index)) {
                    otherSubGame = r9SubGameMap.get(otherLotteryCode + issue + index);
                } else {
                    otherSubGame = new SubGame();
                    otherSubGame.setLotteryCode(otherLotteryCode);
                    otherSubGame.setIssue(issue);
                }
                otherSubGame.setLeageName(leageNameList.get(index - 1));
                otherSubGame.setMasterName(mainTeamList.get(index - 1));
                otherSubGame.setGuestName(guestTeamList.get(index - 1));
                otherSubGame.setStartTime(Utils.formatDate(playTimeList.get(index - 1), "yyyy-MM-dd HH:mm:ss"));
                otherSubGame.setIndex(index);
                otherGameList.add(otherSubGame);
            }
        }

        List<MainIssue> issueList = new ArrayList<MainIssue>();
        issueList.add(newIssue);
        if (Utils.isNotEmpty(otherMainIssue)) {
            issueList.add(otherMainIssue);
        }
        try {
            if (Utils.isNotEmpty(otherGameList)) {
                subGameDao.saveAllSubGame(otherGameList);
            }
            subGameDao.saveAllSubGame(subGameList);
            mainIssueDao.saveAllObject(issueList);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public MainIssue getStartIssueByLottery(String lotteryCode) {
        return mainIssueDao.getStartIssueByLottery(lotteryCode);
    }

    @Override
    public List<MainIssue> getOnTimeIssue(String lotteryCode) {
        return mainIssueDao.getOnTimeIssue(lotteryCode);
    }

    @Override
    public void doUpdateBonusStatus(String lotteryCode, String issue) {
        mainIssueDao.doUpdateBonusStatus(lotteryCode, issue);
    }

    @Override
    public List<MainIssue> getMainIssuesByStatusForGpBonusQuery(MainIssue mainIssue) {
        return mainIssueDao.getMainIssuesByStatusForGpBonusQuery(mainIssue);
    }

    @Override
    public int doAutoBonusGpIssue(String lotteryCode, String issue, String bonusNumber) {
        MainIssue mainIssue = mainIssueDao.getMainIssueByLotteryIssue(lotteryCode, issue);
        mainIssue.setBonusNumber(bonusNumber);
        mainIssue.setBonusTime(new Date());

        boolean rs = mainIssueDao.update(mainIssue);

        if (rs) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<Map<String, Object>> getnoWinIssueList(int issueStatus, int bonusStatus) {
        return mainIssueDao.getnoWinIssueList(issueStatus, bonusStatus);
    }

}
