package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.SubIssueForJingCaiLanQiu;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ISubIssueForJingCaiLanQiuService;
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
public class Lottery201SubIssueOperator extends BaseSubIssueOperator {

    private static final Logger logger = Logger.getLogger(Lottery201SubIssueOperator.class);

    private final String LOTTERY_CODE = "201";
    @Resource
    private ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService;
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
        List<XmlBean> packageBeanList = xmlBean.getAll("package");
        if (null == packageBeanList) {
//            subIssueForJingCaiLanQiuService.updateEndOperator();
            return;
        }

        for (XmlBean packageBean : packageBeanList) {

            XmlBean dateBean = packageBean.getFirst("date");
            XmlBean dayTextBean = packageBean.getFirst("dayText");
            String date = dateBean.attribute("text");
            String week = dayTextBean.attribute("text");

            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(LOTTERY_CODE, date);
            if (null == mainIssue) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                try {
                    mainIssue = new MainIssue();
                    mainIssue.setLotteryCode(LOTTERY_CODE);
                    mainIssue.setName(date);
                    mainIssue.setStartTime(simpleDateFormat.parse(date + "000000"));
                    mainIssue.setSimplexTime(simpleDateFormat.parse(date + "235959"));
                    mainIssue.setDuplexTime(simpleDateFormat.parse(date + "235959"));
                    mainIssue.setBonusTime(simpleDateFormat.parse(date + "235959"));
                    mainIssue.setBonusClass("[]");
                    mainIssue.setBonusNumber("-");
                    mainIssue.setGlobalSaleTotal("0");
                    mainIssue.setSaleTotal("0");
                    mainIssue.setSendStatus(Constants.ISSUE_SALE_SEND);
                    mainIssue.setStatus(Constants.ISSUE_STATUS_START);
                    mainIssueService.save(mainIssue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            List<XmlBean> matchBeanList = packageBean.getAll("match");

            Map<String, SubIssueForJingCaiLanQiu> jingCaiLanQiuMap = new HashMap<String, SubIssueForJingCaiLanQiu>();

            List<SubIssueForJingCaiLanQiu> subIssueForJingCaiLanQiuList = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuListByDate(date);

            for (SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu : subIssueForJingCaiLanQiuList) {
                jingCaiLanQiuMap.put(new StringBuilder(subIssueForJingCaiLanQiu.getIssue()).append(subIssueForJingCaiLanQiu.getSn()).append(subIssueForJingCaiLanQiu.getLotteryCode()).append(subIssueForJingCaiLanQiu.getPlayCode()).append(subIssueForJingCaiLanQiu.getPollCode()).toString(), subIssueForJingCaiLanQiu);
            }

            for (XmlBean matchBean : matchBeanList) {
                String playCode = matchBean.attribute("playCode");
                String pollCode = matchBean.attribute("pollCode");
                int operator = Utils.formatInt(matchBean.attribute("operation"), 0);
                String matchId = matchBean.attribute("mid");

                String sn = matchBean.attribute("sn");
                SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = jingCaiLanQiuMap.get(new StringBuilder(date).append(sn).append(LOTTERY_CODE).append(playCode).append(pollCode).toString());
                String letBall = matchBean.attribute("letBall");
                String preCast = matchBean.attribute("preCast");

                SimpleDateFormat sdfForEndTime = new SimpleDateFormat("yy-MM-dd HH:mm");
                String endTime = matchBean.attribute("endTime");
                String matchColor = matchBean.attribute("matchColor");

                XmlBean matchNameBean = matchBean.getFirst("matchName");
                XmlBean mainTeamBean = matchBean.getFirst("mainTeam");
                XmlBean guestTeamBean = matchBean.getFirst("guestTeam");
                XmlBean winOrNegaBean = matchBean.getFirst("winOrNega");
                XmlBean letWinOrNegaBean = matchBean.getFirst("letWinOrNega");
                XmlBean winNegaDiffBean = matchBean.getFirst("winNegaDiff");
                XmlBean bigOrLittleBean = matchBean.getFirst("bigOrLittle");

                if (null == matchNameBean) {
                    return;
                }
                if (null == mainTeamBean) {
                    return;
                }
                if (null == guestTeamBean) {
                    return;
                }
                if (null == winOrNegaBean) {
                    winOrNegaBean = ByteCodeUtil.xmlToObject("<t/>");
                }
                if (null == letWinOrNegaBean) {
                    letWinOrNegaBean = ByteCodeUtil.xmlToObject("<t/>");
                }
                if (null == winNegaDiffBean) {
                    winNegaDiffBean = ByteCodeUtil.xmlToObject("<t/>");
                }
                if (null == bigOrLittleBean) {
                    bigOrLittleBean = ByteCodeUtil.xmlToObject("<t/>");
                }

                String matchName = matchNameBean.attribute("text");
                String mainTeam = mainTeamBean.attribute("text");
                String guestTeam = guestTeamBean.attribute("text");


                double negaSp = Utils.formatDouble(winOrNegaBean.attribute("nega"), -99d);
                double winSp = Utils.formatDouble(winOrNegaBean.attribute("win"), -99d);

                double letNegaSp = Utils.formatDouble(letWinOrNegaBean.attribute("letNega"), -99d);
                double letWinSp = Utils.formatDouble(letWinOrNegaBean.attribute("letWin"), -99d);

                double winNegaDiffM1T5Sp = Utils.formatDouble(winNegaDiffBean.attribute("s1-5"), -99d);
                double winNegaDiffM6T10Sp = Utils.formatDouble(winNegaDiffBean.attribute("s6-10"), -99d);
                double winNegaDiffM11T15Sp = Utils.formatDouble(winNegaDiffBean.attribute("s11-15"), -99d);
                double winNegaDiffM16T20Sp = Utils.formatDouble(winNegaDiffBean.attribute("s16-20"), -99d);
                double winNegaDiffM21T25Sp = Utils.formatDouble(winNegaDiffBean.attribute("s21-25"), -99d);
                double winNegaDiffM26Sp = Utils.formatDouble(winNegaDiffBean.attribute("s26"), -99d);

                double winNegaDiffG1T5Sp = Utils.formatDouble(winNegaDiffBean.attribute("f1-5"), -99d);
                double winNegaDiffG6T10Sp = Utils.formatDouble(winNegaDiffBean.attribute("f6-10"), -99d);
                double winNegaDiffG11T15Sp = Utils.formatDouble(winNegaDiffBean.attribute("f11-15"), -99d);
                double winNegaDiffG16T20Sp = Utils.formatDouble(winNegaDiffBean.attribute("f16-20"), -99d);
                double winNegaDiffG21T25Sp = Utils.formatDouble(winNegaDiffBean.attribute("f21-25"), -99d);
                double winNegaDiffG26Sp = Utils.formatDouble(winNegaDiffBean.attribute("f26"), -99d);

                double bigSp = Utils.formatDouble(bigOrLittleBean.attribute("big"), -99d);
                double littleSp = Utils.formatDouble(bigOrLittleBean.attribute("little"), -99d);
                if (null == subIssueForJingCaiLanQiu) {
                    try {
                        subIssueForJingCaiLanQiu = new SubIssueForJingCaiLanQiu();
                        subIssueForJingCaiLanQiu.setLotteryCode(LOTTERY_CODE);
                        subIssueForJingCaiLanQiu.setPlayCode(playCode);
                        subIssueForJingCaiLanQiu.setPollCode(pollCode);
                        subIssueForJingCaiLanQiu.setIssue(date);
                        subIssueForJingCaiLanQiu.setSn(sn);
                        subIssueForJingCaiLanQiu.setWeek(week);
                        subIssueForJingCaiLanQiu.setMatchId(matchId);
                        subIssueForJingCaiLanQiu.setMatchColor(matchColor);
                        subIssueForJingCaiLanQiu.setEndTime(sdfForEndTime.parse(endTime));
                        subIssueForJingCaiLanQiu.setMatchName(matchName);
                        subIssueForJingCaiLanQiu.setMainTeam(mainTeam);
                        subIssueForJingCaiLanQiu.setGuestTeam(guestTeam);
                        subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);
                        subIssueForJingCaiLanQiu.setBonusOperator(Constants.SUB_ISSUE_BONUS_OPERATOR_NO);
                        subIssueForJingCaiLanQiu.setCreateTime(new Date());
                        subIssueForJingCaiLanQiu.setUpdateTime(new Date());
                        subIssueForJingCaiLanQiu.setLetBall(letBall);
                        subIssueForJingCaiLanQiu.setNegaSp(negaSp);
                        subIssueForJingCaiLanQiu.setWinSp(winSp);
                        subIssueForJingCaiLanQiu.setLetNegaSp(letNegaSp);
                        subIssueForJingCaiLanQiu.setLetWinSp(letWinSp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG1T5Sp(winNegaDiffG1T5Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG6T10Sp(winNegaDiffG6T10Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG11T15Sp(winNegaDiffG11T15Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG16T20Sp(winNegaDiffG16T20Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG21T25Sp(winNegaDiffG21T25Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG26Sp(winNegaDiffG26Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM1T5Sp(winNegaDiffM1T5Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM6T10Sp(winNegaDiffM6T10Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM11T15Sp(winNegaDiffM11T15Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM16T20Sp(winNegaDiffM16T20Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM21T25Sp(winNegaDiffM21T25Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM26Sp(winNegaDiffM26Sp);
                        subIssueForJingCaiLanQiu.setBigSp(bigSp);
                        subIssueForJingCaiLanQiu.setLittleSp(littleSp);
                        subIssueForJingCaiLanQiu.setPreCast(preCast);
                        subIssueForJingCaiLanQiu.setOperatorsAward(Constants.SUB_ISSUE_BONUS_OPERATOR_NO);
                        Map<String, Date> dateMap = getEndTime(LOTTERY_CODE, subIssueForJingCaiLanQiu.getEndTime());
                        subIssueForJingCaiLanQiu.setEndDanShiTime(dateMap.get(END_TIME_DAN_SHI));
                        subIssueForJingCaiLanQiu.setEndFuShiTime(dateMap.get(END_TIME_FU_SHI));
                        subIssueForJingCaiLanQiu.setEndStatus(Constants.SUB_ISSUE_END_QUERY_YES);

                        subIssueForJingCaiLanQiuService.save(subIssueForJingCaiLanQiu);

                        StringBuffer key = new StringBuffer();
                        key.append(subIssueForJingCaiLanQiu.getLotteryCode()).append(".").append(subIssueForJingCaiLanQiu.getIssue()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getPlayCode()).append(".").append(subIssueForJingCaiLanQiu.getPollCode()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getSn());
                        memcachedClientWrapper.set(key.toString(), 0, subIssueForJingCaiLanQiu);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    if ("1".equals(subIssueForJingCaiLanQiu.getBackup1())) {//不自动更新的对阵
                        jingCaiLanQiuMap.remove(new StringBuilder(date).append(sn).append(LOTTERY_CODE).append(playCode).append(pollCode).toString());
                        continue;
                    }
                    if (subIssueForJingCaiLanQiu.getEndOperator().intValue() == Constants.SUB_ISSUE_END_OPERATOR_END) {
                        jingCaiLanQiuMap.remove(new StringBuilder(date).append(sn).append(LOTTERY_CODE).append(playCode).append(pollCode).toString());

                        //更新
                        StringBuffer key = new StringBuffer();
                        key.append(subIssueForJingCaiLanQiu.getLotteryCode()).append(".").append(subIssueForJingCaiLanQiu.getIssue()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getPlayCode()).append(".").append(subIssueForJingCaiLanQiu.getPollCode()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getSn());
                        memcachedClientWrapper.delete(key.toString());
                        continue;
                    }

                    if (subIssueForJingCaiLanQiu.getEndOperator().intValue() == Constants.SUB_ISSUE_END_OPERATOR_CANCEL) {
                        //更新
                        StringBuffer key = new StringBuffer();
                        key.append(subIssueForJingCaiLanQiu.getLotteryCode()).append(".").append(subIssueForJingCaiLanQiu.getIssue()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getPlayCode()).append(".").append(subIssueForJingCaiLanQiu.getPollCode()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getSn());
                        memcachedClientWrapper.delete(key.toString());
                        continue;
                    }

                    long oldTime = subIssueForJingCaiLanQiu.getEndTime().getTime();
                    try {
                        subIssueForJingCaiLanQiu.setIssue(date);
                        subIssueForJingCaiLanQiu.setSn(sn);
                        subIssueForJingCaiLanQiu.setWeek(week);
                        subIssueForJingCaiLanQiu.setMatchId(matchId);
                        subIssueForJingCaiLanQiu.setMatchColor(matchColor);
                        subIssueForJingCaiLanQiu.setEndTime(sdfForEndTime.parse(endTime));
                        subIssueForJingCaiLanQiu.setMatchName(matchName);
                        subIssueForJingCaiLanQiu.setMainTeam(mainTeam);
                        subIssueForJingCaiLanQiu.setGuestTeam(guestTeam);
                        subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);
                        subIssueForJingCaiLanQiu.setUpdateTime(new Date());
                        subIssueForJingCaiLanQiu.setLetBall(letBall);
                        subIssueForJingCaiLanQiu.setNegaSp(negaSp);
                        subIssueForJingCaiLanQiu.setWinSp(winSp);
                        subIssueForJingCaiLanQiu.setLetNegaSp(letNegaSp);
                        subIssueForJingCaiLanQiu.setLetWinSp(letWinSp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG1T5Sp(winNegaDiffG1T5Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG6T10Sp(winNegaDiffG6T10Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG11T15Sp(winNegaDiffG11T15Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG16T20Sp(winNegaDiffG16T20Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG21T25Sp(winNegaDiffG21T25Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffG26Sp(winNegaDiffG26Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM1T5Sp(winNegaDiffM1T5Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM6T10Sp(winNegaDiffM6T10Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM11T15Sp(winNegaDiffM11T15Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM16T20Sp(winNegaDiffM16T20Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM21T25Sp(winNegaDiffM21T25Sp);
                        subIssueForJingCaiLanQiu.setWinNegaDiffM26Sp(winNegaDiffM26Sp);
                        subIssueForJingCaiLanQiu.setBigSp(bigSp);
                        subIssueForJingCaiLanQiu.setLittleSp(littleSp);
                        subIssueForJingCaiLanQiu.setPreCast(preCast);
                        Map<String, Date> dateMap = getEndTime(LOTTERY_CODE, subIssueForJingCaiLanQiu.getEndTime());
                        subIssueForJingCaiLanQiu.setEndDanShiTime(dateMap.get(END_TIME_DAN_SHI));
                        subIssueForJingCaiLanQiu.setEndFuShiTime(dateMap.get(END_TIME_FU_SHI));
                        subIssueForJingCaiLanQiuService.update(subIssueForJingCaiLanQiu);

                        if (oldTime != subIssueForJingCaiLanQiu.getEndTime().getTime()) {
                            ticketReCodeService.updateTicketForIssueUpdate(subIssueForJingCaiLanQiu.getLotteryCode(), subIssueForJingCaiLanQiu.getIssue(), sn, subIssueForJingCaiLanQiu.getEndTime());
                        }

                        StringBuffer key = new StringBuffer();
                        key.append(subIssueForJingCaiLanQiu.getLotteryCode()).append(".").append(subIssueForJingCaiLanQiu.getIssue()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getPlayCode()).append(".").append(subIssueForJingCaiLanQiu.getPollCode()).append(".");
                        key.append(subIssueForJingCaiLanQiu.getSn());
                        memcachedClientWrapper.set(key.toString(), 0, subIssueForJingCaiLanQiu);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    jingCaiLanQiuMap.remove(new StringBuilder(date).append(sn).append(LOTTERY_CODE).append(playCode).append(pollCode).toString());
                }
            }
            if (jingCaiLanQiuMap.size() > 0) {
                //为防止并发，应该提前结期处理
                long currTime = new Date().getTime();
                for (Map.Entry<String, SubIssueForJingCaiLanQiu> entry : jingCaiLanQiuMap.entrySet()) {
                    SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = entry.getValue();
                    long oldTime = subIssueForJingCaiLanQiu.getEndTime().getTime();
                    if (oldTime <= currTime) continue;
					logger.info(LOTTERY_CODE+subIssueForJingCaiLanQiu.getIssue()+subIssueForJingCaiLanQiu.getSn()+"=not");

                    /**
                    subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
                    subIssueForJingCaiLanQiu.setUpdateTime(new Date());
                    subIssueForJingCaiLanQiuService.update(subIssueForJingCaiLanQiu);

                    //更新
                    StringBuffer key = new StringBuffer();
                    key.append(subIssueForJingCaiLanQiu.getLotteryCode()).append(".").append(subIssueForJingCaiLanQiu.getIssue()).append(".");
                    key.append(subIssueForJingCaiLanQiu.getPlayCode()).append(".").append(subIssueForJingCaiLanQiu.getPollCode()).append(".");
                    key.append(subIssueForJingCaiLanQiu.getSn());
                    memcachedClientWrapper.delete(key.toString());
                    */
                }
            }
        }
    }

    public ISubIssueForJingCaiLanQiuService getSubIssueForJingCaiLanQiuService() {
        return subIssueForJingCaiLanQiuService;
    }

    public void setSubIssueForJingCaiLanQiuService(ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService) {
        this.subIssueForJingCaiLanQiuService = subIssueForJingCaiLanQiuService;
    }

    public IMainIssueService getMainIssueService() {
        return mainIssueService;
    }

    public void setMainIssueService(IMainIssueService mainIssueService) {
        this.mainIssueService = mainIssueService;
    }

}
