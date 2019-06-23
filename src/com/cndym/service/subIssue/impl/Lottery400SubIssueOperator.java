package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.SubIssueForBeiDan;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ISubIssueForBeiDanService;
import com.cndym.service.ITicketReCodeService;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.utils.Utils;
import com.cndym.utils.xml.parse.ByteCodeUtil;
import com.cndym.utils.xml.parse.XmlBean;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：邓玉明
 * email：cndym@163.com
 * QQ：757579248
 * 时间: 12-2-1 下午4:21
 */
@Repository
public class Lottery400SubIssueOperator extends BaseSubIssueOperator {
	private static final Logger logger = Logger.getLogger(Lottery400SubIssueOperator.class);
    private final String LOTTERY_CODE = "400";
    @Resource
    private ISubIssueForBeiDanService subIssueForBeiDanService;
    @Resource
    private IMainIssueService mainIssueService;
    @Resource
    private ITicketReCodeService ticketReCodeService;
	@Resource
	private XMemcachedClientWrapper memcachedClientWrapper;

    @Override
    public void operator(String xml) {
        XmlBean xmlBean = ByteCodeUtil.xmlToObject(xml);
        if (null == xmlBean) {
            return;
        }
        List<XmlBean> matchBeanList = xmlBean.getAll("match");
        if (null == matchBeanList) {
            subIssueForBeiDanService.updateEndOperator();
            return;
        }

        long startTime = new Date().getTime();
        long lastTime = new Date().getTime();

        LotteryClass lotteryClass = LotteryList.getLotteryClass(LOTTERY_CODE);
        long danShi = lotteryClass.getDashi();
        long fuShi = lotteryClass.getEarly();

        String issue = xmlBean.attribute("issue");
        List<SubIssueForBeiDan> subIssueForBeiDanList = subIssueForBeiDanService.getSubIssueForBeiDanListByIssue(issue);
        Map<String, SubIssueForBeiDan> beiDanMap = new HashMap<String, SubIssueForBeiDan>();
        for (SubIssueForBeiDan subIssueForBeiDan : subIssueForBeiDanList) {
            beiDanMap.put(subIssueForBeiDan.getIssue() + "" + subIssueForBeiDan.getSn(), subIssueForBeiDan);
        }
        for (XmlBean matchBean : matchBeanList) {
            int operator = Utils.formatInt(matchBean.attribute("operation"), 0);
            String sn = matchBean.attribute("sn");
            String date = matchBean.attribute("date");
            String week = matchBean.attribute("week");
            String letBall = matchBean.attribute("letBall");
            SimpleDateFormat sdfForEndTime = new SimpleDateFormat("yy-MM-dd HH:mm");
            String endTime = matchBean.attribute("endTime");
            String matchColor = matchBean.attribute("matchColor");

            SubIssueForBeiDan subIssueForBeiDan = beiDanMap.get(issue + "" + sn);

            XmlBean matchNameBean = matchBean.getFirst("matchName");
            XmlBean mainTeamBean = matchBean.getFirst("mainTeam");
            XmlBean guestTeamBean = matchBean.getFirst("guestTeam");
            XmlBean winOrNegaBean = matchBean.getFirst("winOrNega");
            XmlBean scoreBean = matchBean.getFirst("score");
            XmlBean totalGoalBean = matchBean.getFirst("totalGoal");
            XmlBean halfCourtBean = matchBean.getFirst("halfCourt");
            XmlBean shangXiaPan = matchBean.getFirst("shangXiaPan");

            if (null == matchNameBean) {
                return;
            }
            if (null == mainTeamBean) {
                return;
            }
            if (null == guestTeamBean) {
                return;
            }

            double winSp = -99d;
            double flatSp = -99d;
            double negaSp = -99d;

            double shangDanSp = -99d;
            double shangShuangSp = -99d;
            double xiaDanSp = -99d;
            double xiaShuangSp = -99d;
         
            double totalGoal0Sp = -99d;
            double totalGoal1Sp = -99d;
            double totalGoal2Sp = -99d;
            double totalGoal3Sp = -99d;
            double totalGoal4Sp = -99d;
            double totalGoal5Sp = -99d;
            double totalGoal6Sp = -99d;
            double totalGoal7Sp = -99d;

            double halfCourtFFSp = -99d;
            double halfCourtFPSp = -99d;
            double halfCourtFSSp = -99d;
            double halfCourtPFSp = -99d;
            double halfCourtPPSp = -99d;
            double halfCourtPSSp = -99d;
            double halfCourtSFSp = -99d;
            double halfCourtSPSp = -99d;
            double halfCourtSSSp = -99d;

            double scoreFQTSp = -99d;
            double scorePQTSp = -99d;
            double scoreSQTSp = -99d;
            double score00Sp = -99d;
            double score01Sp = -99d;
            double score02Sp = -99d;
            double score03Sp = -99d;
            double score04Sp = -99d;
            double score10Sp = -99d;
            double score11Sp = -99d;
            double score12Sp = -99d;
            double score13Sp = -99d;
            double score14Sp = -99d;
            double score20Sp = -99d;
            double score21Sp = -99d;
            double score22Sp = -99d;
            double score23Sp = -99d;
            double score24Sp = -99d;
            double score30Sp = -99d;
            double score31Sp = -99d;
            double score32Sp = -99d;
            double score33Sp = -99d;
            double score40Sp = -99d;
            double score41Sp = -99d;
            double score42Sp = -99d;
            
            //40001
            if (Utils.isNotEmpty(winOrNegaBean)) {
                negaSp = Utils.formatDouble(winOrNegaBean.attribute("nega"), -99d);
                flatSp = Utils.formatDouble(winOrNegaBean.attribute("flat"), -99d);
                winSp = Utils.formatDouble(winOrNegaBean.attribute("win"), -99d);
            } else if (Utils.isNotEmpty(subIssueForBeiDan)) {
                negaSp = subIssueForBeiDan.getNegaSp();
                flatSp = subIssueForBeiDan.getFlatSp();
                winSp = subIssueForBeiDan.getWinSp();
            }
            //40002
            if (null == shangXiaPan && null != subIssueForBeiDan) {
                shangDanSp = subIssueForBeiDan.getShangDanSp();
                shangShuangSp = subIssueForBeiDan.getShangShuangSp();
                xiaDanSp = subIssueForBeiDan.getXiaDanSp();
                xiaShuangSp = subIssueForBeiDan.getXiaShuangSp();
            } else if (null != shangXiaPan) {
            	shangDanSp = Utils.formatDouble(shangXiaPan.attribute("shangDanSp"), -99d);
                shangShuangSp = Utils.formatDouble(shangXiaPan.attribute("shangShuangSp"), -99d);
                xiaDanSp = Utils.formatDouble(shangXiaPan.attribute("xiaDanSp"), -99d);
                xiaShuangSp = Utils.formatDouble(shangXiaPan.attribute("xiaShuangSp"), -99d);
            }
            
            //40003
            if (Utils.isNotEmpty(totalGoalBean)) {
                totalGoal0Sp = Utils.formatDouble(totalGoalBean.attribute("tg_0"), -99d);
                totalGoal1Sp = Utils.formatDouble(totalGoalBean.attribute("tg_1"), -99d);
                totalGoal2Sp = Utils.formatDouble(totalGoalBean.attribute("tg_2"), -99d);
                totalGoal3Sp = Utils.formatDouble(totalGoalBean.attribute("tg_3"), -99d);
                totalGoal4Sp = Utils.formatDouble(totalGoalBean.attribute("tg_4"), -99d);
                totalGoal5Sp = Utils.formatDouble(totalGoalBean.attribute("tg_5"), -99d);
                totalGoal6Sp = Utils.formatDouble(totalGoalBean.attribute("tg_6"), -99d);
                totalGoal7Sp = Utils.formatDouble(totalGoalBean.attribute("tg_7"), -99d);
            } else if (Utils.isNotEmpty(subIssueForBeiDan)) {
                totalGoal0Sp = subIssueForBeiDan.getTotalGoal0Sp();
                totalGoal1Sp = subIssueForBeiDan.getTotalGoal1Sp();
                totalGoal2Sp = subIssueForBeiDan.getTotalGoal2Sp();
                totalGoal3Sp = subIssueForBeiDan.getTotalGoal3Sp();
                totalGoal4Sp = subIssueForBeiDan.getTotalGoal4Sp();
                totalGoal5Sp = subIssueForBeiDan.getTotalGoal5Sp();
                totalGoal6Sp = subIssueForBeiDan.getTotalGoal6Sp();
                totalGoal7Sp = subIssueForBeiDan.getTotalGoal7Sp();
            }
            
            //40004
            if (Utils.isNotEmpty(halfCourtBean)) {
                halfCourtFFSp = Utils.formatDouble(halfCourtBean.attribute("hc_ff"), -99d);
                halfCourtFPSp = Utils.formatDouble(halfCourtBean.attribute("hc_fp"), -99d);
                halfCourtFSSp = Utils.formatDouble(halfCourtBean.attribute("hc_fs"), -99d);
                halfCourtPFSp = Utils.formatDouble(halfCourtBean.attribute("hc_pf"), -99d);
                halfCourtPPSp = Utils.formatDouble(halfCourtBean.attribute("hc_pp"), -99d);
                halfCourtPSSp = Utils.formatDouble(halfCourtBean.attribute("hc_ps"), -99d);
                halfCourtSFSp = Utils.formatDouble(halfCourtBean.attribute("hc_sf"), -99d);
                halfCourtSPSp = Utils.formatDouble(halfCourtBean.attribute("hc_sp"), -99d);
                halfCourtSSSp = Utils.formatDouble(halfCourtBean.attribute("hc_ss"), -99d);
            } else if (Utils.isNotEmpty(subIssueForBeiDan)){
                halfCourtFFSp = subIssueForBeiDan.getHalfCourtFFSp();
                halfCourtFPSp = subIssueForBeiDan.getHalfCourtFPSp();
                halfCourtFSSp = subIssueForBeiDan.getHalfCourtFSSp();
                halfCourtPFSp = subIssueForBeiDan.getHalfCourtPFSp();
                halfCourtPPSp = subIssueForBeiDan.getHalfCourtPPSp();
                halfCourtPSSp = subIssueForBeiDan.getHalfCourtPSSp();
                halfCourtSFSp = subIssueForBeiDan.getHalfCourtSFSp();
                halfCourtSPSp = subIssueForBeiDan.getHalfCourtSPSp();
                halfCourtSSSp = subIssueForBeiDan.getHalfCourtSSSp();
            }

            //40005
            if (Utils.isNotEmpty(scoreBean)) {
                scoreFQTSp = Utils.formatDouble(scoreBean.attribute("sp_f_qt"), -99d);
                scorePQTSp = Utils.formatDouble(scoreBean.attribute("sp_p_qt"), -99d);
                scoreSQTSp = Utils.formatDouble(scoreBean.attribute("sp_s_qt"), -99d);
                score00Sp = Utils.formatDouble(scoreBean.attribute("sp_00"), -99d);
                score01Sp = Utils.formatDouble(scoreBean.attribute("sp_01"), -99d);
                score02Sp = Utils.formatDouble(scoreBean.attribute("sp_02"), -99d);
                score03Sp = Utils.formatDouble(scoreBean.attribute("sp_03"), -99d);
                score04Sp = Utils.formatDouble(scoreBean.attribute("sp_04"), -99d);
                score10Sp = Utils.formatDouble(scoreBean.attribute("sp_10"), -99d);
                score11Sp = Utils.formatDouble(scoreBean.attribute("sp_11"), -99d);
                score12Sp = Utils.formatDouble(scoreBean.attribute("sp_12"), -99d);
                score13Sp = Utils.formatDouble(scoreBean.attribute("sp_13"), -99d);
                score14Sp = Utils.formatDouble(scoreBean.attribute("sp_14"), -99d);
                score20Sp = Utils.formatDouble(scoreBean.attribute("sp_20"), -99d);
                score21Sp = Utils.formatDouble(scoreBean.attribute("sp_21"), -99d);
                score22Sp = Utils.formatDouble(scoreBean.attribute("sp_22"), -99d);
                score23Sp = Utils.formatDouble(scoreBean.attribute("sp_23"), -99d);
                score24Sp = Utils.formatDouble(scoreBean.attribute("sp_24"), -99d);
                score30Sp = Utils.formatDouble(scoreBean.attribute("sp_30"), -99d);
                score31Sp = Utils.formatDouble(scoreBean.attribute("sp_31"), -99d);
                score32Sp = Utils.formatDouble(scoreBean.attribute("sp_32"), -99d);
                score33Sp = Utils.formatDouble(scoreBean.attribute("sp_33"), -99d);
                score40Sp = Utils.formatDouble(scoreBean.attribute("sp_40"), -99d);
                score41Sp = Utils.formatDouble(scoreBean.attribute("sp_41"), -99d);
                score42Sp = Utils.formatDouble(scoreBean.attribute("sp_42"), -99d);
            } else if (Utils.isNotEmpty(subIssueForBeiDan)) {
            	scoreFQTSp = subIssueForBeiDan.getScoreFQTSp();
                scorePQTSp = subIssueForBeiDan.getScorePQTSp();
                scoreSQTSp = subIssueForBeiDan.getScoreSQTSp();
                score00Sp = subIssueForBeiDan.getScore00Sp();
                score01Sp = subIssueForBeiDan.getScore01Sp();
                score02Sp = subIssueForBeiDan.getScore02Sp();
                score03Sp = subIssueForBeiDan.getScore03Sp();
                score04Sp = subIssueForBeiDan.getScore04Sp();
                score10Sp = subIssueForBeiDan.getScore10Sp();
                score11Sp = subIssueForBeiDan.getScore11Sp();
                score12Sp = subIssueForBeiDan.getScore12Sp();
                score13Sp = subIssueForBeiDan.getScore13Sp();
                score14Sp = subIssueForBeiDan.getScore14Sp();
                score20Sp = subIssueForBeiDan.getScore20Sp();
                score21Sp = subIssueForBeiDan.getScore21Sp();
                score22Sp = subIssueForBeiDan.getScore22Sp();
                score23Sp = subIssueForBeiDan.getScore23Sp();
                score24Sp = subIssueForBeiDan.getScore24Sp();
                score30Sp = subIssueForBeiDan.getScore30Sp();
                score31Sp = subIssueForBeiDan.getScore31Sp();
                score32Sp = subIssueForBeiDan.getScore32Sp();
                score33Sp = subIssueForBeiDan.getScore33Sp();
                score40Sp = subIssueForBeiDan.getScore40Sp();
                score41Sp = subIssueForBeiDan.getScore41Sp();
                score42Sp = subIssueForBeiDan.getScore42Sp();
            }

            String matchName = matchNameBean.attribute("text");
            String mainTeam = mainTeamBean.attribute("text");
            String guestTeam = guestTeamBean.attribute("text");

            Date tempTime = null;
            try {
                tempTime = sdfForEndTime.parse(endTime);
                if (startTime > tempTime.getTime()) {
                    startTime = tempTime.getTime();
                }
                if (lastTime < tempTime.getTime()) {
                    lastTime = tempTime.getTime();
                }
            } catch (ParseException e) {
            	logger.error("lottery400SubIssueOperator", e);
                return;
            }

            if (null == subIssueForBeiDan) {
                subIssueForBeiDan = new SubIssueForBeiDan();
                subIssueForBeiDan.setLotteryCode(LOTTERY_CODE);
                subIssueForBeiDan.setIssue(issue);
                subIssueForBeiDan.setDate(date);
                subIssueForBeiDan.setSn(sn);
                subIssueForBeiDan.setWeek(week);
                subIssueForBeiDan.setMatchColor(matchColor);
                subIssueForBeiDan.setEndTime(tempTime);
                subIssueForBeiDan.setMatchName(matchName);
                subIssueForBeiDan.setMainTeam(mainTeam);
                subIssueForBeiDan.setGuestTeam(guestTeam);
                subIssueForBeiDan.setEndOperator(operator);
                subIssueForBeiDan.setBonusOperator(Constants.SUB_ISSUE_BONUS_OPERATOR_NO);
                subIssueForBeiDan.setCreateTime(new Date());
                subIssueForBeiDan.setUpdateTime(new Date());
                subIssueForBeiDan.setLetBall(letBall);
                subIssueForBeiDan.setNegaSp(negaSp);
                subIssueForBeiDan.setFlatSp(flatSp);
                subIssueForBeiDan.setWinSp(winSp);
                subIssueForBeiDan.setScoreFQTSp(scoreFQTSp);
                subIssueForBeiDan.setScorePQTSp(scorePQTSp);
                subIssueForBeiDan.setScoreSQTSp(scoreSQTSp);
                subIssueForBeiDan.setScore00Sp(score00Sp);
                subIssueForBeiDan.setScore01Sp(score01Sp);
                subIssueForBeiDan.setScore02Sp(score02Sp);
                subIssueForBeiDan.setScore03Sp(score03Sp);
                subIssueForBeiDan.setScore04Sp(score04Sp);
                subIssueForBeiDan.setScore10Sp(score10Sp);
                subIssueForBeiDan.setScore11Sp(score11Sp);
                subIssueForBeiDan.setScore12Sp(score12Sp);
                subIssueForBeiDan.setScore13Sp(score13Sp);
                subIssueForBeiDan.setScore14Sp(score14Sp);
                subIssueForBeiDan.setScore20Sp(score20Sp);
                subIssueForBeiDan.setScore21Sp(score21Sp);
                subIssueForBeiDan.setScore22Sp(score22Sp);
                subIssueForBeiDan.setScore23Sp(score23Sp);
                subIssueForBeiDan.setScore24Sp(score24Sp);
                subIssueForBeiDan.setScore30Sp(score30Sp);
                subIssueForBeiDan.setScore31Sp(score31Sp);
                subIssueForBeiDan.setScore32Sp(score32Sp);
                subIssueForBeiDan.setScore33Sp(score33Sp);
                subIssueForBeiDan.setScore40Sp(score40Sp);
                subIssueForBeiDan.setScore41Sp(score41Sp);
                subIssueForBeiDan.setScore42Sp(score42Sp);
                subIssueForBeiDan.setShangDanSp(shangDanSp);
                subIssueForBeiDan.setShangShuangSp(shangShuangSp);
                subIssueForBeiDan.setXiaDanSp(xiaDanSp);
                subIssueForBeiDan.setXiaShuangSp(xiaShuangSp);
                subIssueForBeiDan.setTotalGoal0Sp(totalGoal0Sp);
                subIssueForBeiDan.setTotalGoal1Sp(totalGoal1Sp);
                subIssueForBeiDan.setTotalGoal2Sp(totalGoal2Sp);
                subIssueForBeiDan.setTotalGoal3Sp(totalGoal3Sp);
                subIssueForBeiDan.setTotalGoal4Sp(totalGoal4Sp);
                subIssueForBeiDan.setTotalGoal5Sp(totalGoal5Sp);
                subIssueForBeiDan.setTotalGoal6Sp(totalGoal6Sp);
                subIssueForBeiDan.setTotalGoal7Sp(totalGoal7Sp);
                subIssueForBeiDan.setHalfCourtFFSp(halfCourtFFSp);
                subIssueForBeiDan.setHalfCourtFPSp(halfCourtFPSp);
                subIssueForBeiDan.setHalfCourtFSSp(halfCourtFSSp);
                subIssueForBeiDan.setHalfCourtPFSp(halfCourtPFSp);
                subIssueForBeiDan.setHalfCourtPPSp(halfCourtPPSp);
                subIssueForBeiDan.setHalfCourtPSSp(halfCourtPSSp);
                subIssueForBeiDan.setHalfCourtSFSp(halfCourtSFSp);
                subIssueForBeiDan.setHalfCourtSPSp(halfCourtSPSp);
                subIssueForBeiDan.setHalfCourtSSSp(halfCourtSSSp);
                Map<String, Date> dateMap = getEndTime(LOTTERY_CODE, subIssueForBeiDan.getEndTime());
                subIssueForBeiDan.setEndDanShiTime(dateMap.get(END_TIME_DAN_SHI));
                subIssueForBeiDan.setEndFuShiTime(dateMap.get(END_TIME_FU_SHI));
                subIssueForBeiDan.setEndStatus(Constants.SUB_ISSUE_END_QUERY_YES);
                subIssueForBeiDan.setInputAwardStatus(Constants.INPUT_AWARD_NO);
                subIssueForBeiDan.setOperatorsAward(Constants.OPERATORS_AWARD_WAIT);
                subIssueForBeiDan.setDelay(0);
                subIssueForBeiDanService.save(subIssueForBeiDan);
                
				StringBuffer key = new StringBuffer();
				key.append(LOTTERY_CODE).append(".");
				key.append(issue).append(".");
				key.append(sn);
				memcachedClientWrapper.set(key.toString(), 0, subIssueForBeiDan);
            } else {
                if ("1".equals(subIssueForBeiDan.getBackup1())) {
                    continue;
                }
				if (subIssueForBeiDan.getEndOperator().intValue() == Constants.SUB_ISSUE_END_OPERATOR_END) {
					// 更新
					StringBuffer key = new StringBuffer();
					key.append(LOTTERY_CODE).append(".");
					key.append(issue).append(".");
					key.append(sn);
					memcachedClientWrapper.delete(key.toString());
					continue;
				}
				if (subIssueForBeiDan.getEndOperator().intValue() == Constants.SUB_ISSUE_END_OPERATOR_CANCEL) {
					// 更新
					StringBuffer key = new StringBuffer();
					key.append(LOTTERY_CODE).append(".");
					key.append(issue).append(".");
					key.append(sn);
					memcachedClientWrapper.delete(key.toString());
					continue;
				}
                if (tempTime.getTime() != subIssueForBeiDan.getEndTime().getTime()) {
                    subIssueForBeiDan.setDelay(1);
                    ticketReCodeService.updateTicketForIssueUpdate(subIssueForBeiDan.getLotteryCode(), subIssueForBeiDan.getIssue(), sn, subIssueForBeiDan.getEndTime());
                }
                subIssueForBeiDan.setLotteryCode(LOTTERY_CODE);
                subIssueForBeiDan.setIssue(issue);
                subIssueForBeiDan.setDate(date);
                subIssueForBeiDan.setSn(sn);
                subIssueForBeiDan.setWeek(week);
                subIssueForBeiDan.setMatchColor(matchColor);
                subIssueForBeiDan.setEndTime(tempTime);
                subIssueForBeiDan.setMatchName(matchName);
                subIssueForBeiDan.setMainTeam(mainTeam);
                subIssueForBeiDan.setGuestTeam(guestTeam);
                subIssueForBeiDan.setEndOperator(operator);
                subIssueForBeiDan.setUpdateTime(new Date());
                subIssueForBeiDan.setLetBall(letBall);
                subIssueForBeiDan.setNegaSp(negaSp);
                subIssueForBeiDan.setFlatSp(flatSp);
                subIssueForBeiDan.setWinSp(winSp);
                subIssueForBeiDan.setScoreFQTSp(scoreFQTSp);
                subIssueForBeiDan.setScorePQTSp(scorePQTSp);
                subIssueForBeiDan.setScoreSQTSp(scoreSQTSp);
                subIssueForBeiDan.setScore00Sp(score00Sp);
                subIssueForBeiDan.setScore01Sp(score01Sp);
                subIssueForBeiDan.setScore02Sp(score02Sp);
                subIssueForBeiDan.setScore03Sp(score03Sp);
                subIssueForBeiDan.setScore04Sp(score04Sp);
                subIssueForBeiDan.setScore10Sp(score10Sp);
                subIssueForBeiDan.setScore11Sp(score11Sp);
                subIssueForBeiDan.setScore12Sp(score12Sp);
                subIssueForBeiDan.setScore13Sp(score13Sp);
                subIssueForBeiDan.setScore14Sp(score14Sp);
                subIssueForBeiDan.setScore20Sp(score20Sp);
                subIssueForBeiDan.setScore21Sp(score21Sp);
                subIssueForBeiDan.setScore22Sp(score22Sp);
                subIssueForBeiDan.setScore23Sp(score23Sp);
                subIssueForBeiDan.setScore24Sp(score24Sp);
                subIssueForBeiDan.setScore30Sp(score30Sp);
                subIssueForBeiDan.setScore31Sp(score31Sp);
                subIssueForBeiDan.setScore32Sp(score32Sp);
                subIssueForBeiDan.setScore33Sp(score33Sp);
                subIssueForBeiDan.setScore40Sp(score40Sp);
                subIssueForBeiDan.setScore41Sp(score41Sp);
                subIssueForBeiDan.setScore42Sp(score42Sp);
                subIssueForBeiDan.setShangDanSp(shangDanSp);
                subIssueForBeiDan.setShangShuangSp(shangShuangSp);
                subIssueForBeiDan.setXiaDanSp(xiaDanSp);
                subIssueForBeiDan.setXiaShuangSp(xiaShuangSp);
                subIssueForBeiDan.setTotalGoal0Sp(totalGoal0Sp);
                subIssueForBeiDan.setTotalGoal1Sp(totalGoal1Sp);
                subIssueForBeiDan.setTotalGoal2Sp(totalGoal2Sp);
                subIssueForBeiDan.setTotalGoal3Sp(totalGoal3Sp);
                subIssueForBeiDan.setTotalGoal4Sp(totalGoal4Sp);
                subIssueForBeiDan.setTotalGoal5Sp(totalGoal5Sp);
                subIssueForBeiDan.setTotalGoal6Sp(totalGoal6Sp);
                subIssueForBeiDan.setTotalGoal7Sp(totalGoal7Sp);
                subIssueForBeiDan.setHalfCourtFFSp(halfCourtFFSp);
                subIssueForBeiDan.setHalfCourtFPSp(halfCourtFPSp);
                subIssueForBeiDan.setHalfCourtFSSp(halfCourtFSSp);
                subIssueForBeiDan.setHalfCourtPFSp(halfCourtPFSp);
                subIssueForBeiDan.setHalfCourtPPSp(halfCourtPPSp);
                subIssueForBeiDan.setHalfCourtPSSp(halfCourtPSSp);
                subIssueForBeiDan.setHalfCourtSFSp(halfCourtSFSp);
                subIssueForBeiDan.setHalfCourtSPSp(halfCourtSPSp);
                subIssueForBeiDan.setHalfCourtSSSp(halfCourtSSSp);
                Map<String, Date> dateMap = getEndTime(LOTTERY_CODE, subIssueForBeiDan.getEndTime());
                subIssueForBeiDan.setEndDanShiTime(dateMap.get(END_TIME_DAN_SHI));
                subIssueForBeiDan.setEndFuShiTime(dateMap.get(END_TIME_FU_SHI));
                subIssueForBeiDanService.update(subIssueForBeiDan);
				StringBuffer key = new StringBuffer();
				key.append(LOTTERY_CODE).append(".");
				key.append(issue).append(".");
				key.append(sn);
				memcachedClientWrapper.set(key.toString(), 0, subIssueForBeiDan);
            }
        }
        MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(LOTTERY_CODE, issue);
        if (null == mainIssue) {
            mainIssue = new MainIssue();
            mainIssue.setLotteryCode(LOTTERY_CODE);
            mainIssue.setName(issue);
            mainIssue.setStartTime(new Date(startTime));
            mainIssue.setSimplexTime(new Date(lastTime - danShi * 1000));
            mainIssue.setDuplexTime(new Date(lastTime - fuShi * 1000));
            mainIssue.setBonusTime(new Date(lastTime));
            mainIssue.setEndTime(new Date(lastTime));
            mainIssue.setBonusClass("[]");
            mainIssue.setBonusNumber("-");
            mainIssue.setGlobalSaleTotal("0");
            mainIssue.setOperatorsAward(2);
            mainIssue.setBonusStatus(1);
            mainIssue.setSaleTotal("0");
            mainIssue.setSendStatus(Constants.ISSUE_SALE_SEND);
            mainIssue.setStatus(Constants.ISSUE_STATUS_START);
            mainIssueService.save(mainIssue);
        } else {
            mainIssue.setStartTime(new Date(startTime));
            mainIssue.setSimplexTime(new Date(lastTime - danShi * 1000));
            mainIssue.setDuplexTime(new Date(lastTime - fuShi * 1000));
            mainIssue.setBonusTime(new Date(lastTime));
            mainIssue.setEndTime(new Date(lastTime));
            mainIssueService.update(mainIssue);
        }
    }

    public ISubIssueForBeiDanService getSubIssueForBeiDanService() {
        return subIssueForBeiDanService;
    }

    public void setSubIssueForBeiDanService(ISubIssueForBeiDanService subIssueForBeiDanService) {
        this.subIssueForBeiDanService = subIssueForBeiDanService;
    }

    public IMainIssueService getMainIssueService() {
        return mainIssueService;
    }

    public void setMainIssueService(IMainIssueService mainIssueService) {
        this.mainIssueService = mainIssueService;
    }

}
