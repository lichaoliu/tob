package com.cndym.service.subIssue.impl;

import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.SubIssueForJingCaiZuQiu;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ISubIssueForJingCaiZuQiuService;
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
 * 作者：邓玉明 email：cndym@163.com QQ：757579248 时间: 12-2-1 下午4:21
 */
@Repository
public class Lottery200SubIssueOperator extends BaseSubIssueOperator {

	private static final Logger logger = Logger.getLogger(Lottery200SubIssueOperator.class);

	private final String LOTTERY_CODE = "200";
	@Resource
	private ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService;
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
//			subIssueForJingCaiZuQiuService.updateEndOperator();
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

			List<SubIssueForJingCaiZuQiu> subIssueForJingCaiZuQiuList = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuListByDate(date);
			Map<String, SubIssueForJingCaiZuQiu> jingCaiZuQiuMap = new HashMap<String, SubIssueForJingCaiZuQiu>();
			for (SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu : subIssueForJingCaiZuQiuList) {
				jingCaiZuQiuMap.put(
						new StringBuilder(subIssueForJingCaiZuQiu.getIssue()).append(subIssueForJingCaiZuQiu.getSn()).append(subIssueForJingCaiZuQiu.getLotteryCode())
								.append(subIssueForJingCaiZuQiu.getPlayCode()).append(subIssueForJingCaiZuQiu.getPollCode()).toString(), subIssueForJingCaiZuQiu);
			}

			for (XmlBean matchBean : matchBeanList) {
				String playCode = matchBean.attribute("playCode");
				String pollCode = matchBean.attribute("pollCode");
				String sn = matchBean.attribute("sn");
				int operator = Utils.formatInt(matchBean.attribute("operation"), 0);
				String matchId = matchBean.attribute("mid");
				SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = jingCaiZuQiuMap.get(new StringBuilder(date).append(sn).append(LOTTERY_CODE).append(playCode).append(pollCode).toString());

				String letBall = matchBean.attribute("letBall");
				SimpleDateFormat sdfForEndTime = new SimpleDateFormat("yy-MM-dd HH:mm");
				String endTime = matchBean.attribute("endTime");
				String matchColor = matchBean.attribute("matchColor");

				XmlBean matchNameBean = matchBean.getFirst("matchName");
				XmlBean mainTeamBean = matchBean.getFirst("mainTeam");
				XmlBean guestTeamBean = matchBean.getFirst("guestTeam");
				XmlBean winOrNegaBean = matchBean.getFirst("winOrNega");
				XmlBean spfWinOrNegaBean = matchBean.getFirst("spfWinOrNega");
				XmlBean scoreBean = matchBean.getFirst("score");
				XmlBean totalGoalBean = matchBean.getFirst("totalGoal");
				XmlBean halfCourtBean = matchBean.getFirst("halfCourt");

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
				if (null == scoreBean) {
					scoreBean = ByteCodeUtil.xmlToObject("<t/>");
				}
				if (null == totalGoalBean) {
					totalGoalBean = ByteCodeUtil.xmlToObject("<t/>");
				}
				if (null == halfCourtBean) {
					halfCourtBean = ByteCodeUtil.xmlToObject("<t/>");
				}

				String matchName = matchNameBean.attribute("text");
				String mainTeam = mainTeamBean.attribute("text");
				String guestTeam = guestTeamBean.attribute("text");

				double negaSp = Utils.formatDouble(winOrNegaBean.attribute("nega"), -99d);
				double flatSp = Utils.formatDouble(winOrNegaBean.attribute("flat"), -99d);
				double winSp = Utils.formatDouble(winOrNegaBean.attribute("win"), -99d);

				double spfNegaSp = Utils.formatDouble(spfWinOrNegaBean.attribute("spfNega"), -99d);
				double spfFlatSp = Utils.formatDouble(spfWinOrNegaBean.attribute("spfFlat"), -99d);
				double spfWinSp = Utils.formatDouble(spfWinOrNegaBean.attribute("spfWin"), -99d);

				double scoreFQTSp = Utils.formatDouble(scoreBean.attribute("sp_f_qt"), -99d);
				double scorePQTSp = Utils.formatDouble(scoreBean.attribute("sp_p_qt"), -99d);
				double scoreSQTSp = Utils.formatDouble(scoreBean.attribute("sp_s_qt"), -99d);
				double score00Sp = Utils.formatDouble(scoreBean.attribute("sp_00"), -99d);
				double score01Sp = Utils.formatDouble(scoreBean.attribute("sp_01"), -99d);
				double score02Sp = Utils.formatDouble(scoreBean.attribute("sp_02"), -99d);
				double score03Sp = Utils.formatDouble(scoreBean.attribute("sp_03"), -99d);
				double score04Sp = Utils.formatDouble(scoreBean.attribute("sp_04"), -99d);
				double score05Sp = Utils.formatDouble(scoreBean.attribute("sp_05"), -99d);
				double score10Sp = Utils.formatDouble(scoreBean.attribute("sp_10"), -99d);
				double score11Sp = Utils.formatDouble(scoreBean.attribute("sp_11"), -99d);
				double score12Sp = Utils.formatDouble(scoreBean.attribute("sp_12"), -99d);
				double score13Sp = Utils.formatDouble(scoreBean.attribute("sp_13"), -99d);
				double score14Sp = Utils.formatDouble(scoreBean.attribute("sp_14"), -99d);
				double score15Sp = Utils.formatDouble(scoreBean.attribute("sp_15"), -99d);
				double score20Sp = Utils.formatDouble(scoreBean.attribute("sp_20"), -99d);
				double score21Sp = Utils.formatDouble(scoreBean.attribute("sp_21"), -99d);
				double score22Sp = Utils.formatDouble(scoreBean.attribute("sp_22"), -99d);
				double score23Sp = Utils.formatDouble(scoreBean.attribute("sp_23"), -99d);
				double score24Sp = Utils.formatDouble(scoreBean.attribute("sp_24"), -99d);
				double score25Sp = Utils.formatDouble(scoreBean.attribute("sp_25"), -99d);
				double score30Sp = Utils.formatDouble(scoreBean.attribute("sp_30"), -99d);
				double score31Sp = Utils.formatDouble(scoreBean.attribute("sp_31"), -99d);
				double score32Sp = Utils.formatDouble(scoreBean.attribute("sp_32"), -99d);
				double score33Sp = Utils.formatDouble(scoreBean.attribute("sp_33"), -99d);
				double score40Sp = Utils.formatDouble(scoreBean.attribute("sp_40"), -99d);
				double score41Sp = Utils.formatDouble(scoreBean.attribute("sp_41"), -99d);
				double score42Sp = Utils.formatDouble(scoreBean.attribute("sp_42"), -99d);
				double score50Sp = Utils.formatDouble(scoreBean.attribute("sp_50"), -99d);
				double score51Sp = Utils.formatDouble(scoreBean.attribute("sp_51"), -99d);
				double score52Sp = Utils.formatDouble(scoreBean.attribute("sp_52"), -99d);

				double totalGoal0Sp = Utils.formatDouble(totalGoalBean.attribute("tg_0"), -99d);
				double totalGoal1Sp = Utils.formatDouble(totalGoalBean.attribute("tg_1"), -99d);
				double totalGoal2Sp = Utils.formatDouble(totalGoalBean.attribute("tg_2"), -99d);
				double totalGoal3Sp = Utils.formatDouble(totalGoalBean.attribute("tg_3"), -99d);
				double totalGoal4Sp = Utils.formatDouble(totalGoalBean.attribute("tg_4"), -99d);
				double totalGoal5Sp = Utils.formatDouble(totalGoalBean.attribute("tg_5"), -99d);
				double totalGoal6Sp = Utils.formatDouble(totalGoalBean.attribute("tg_6"), -99d);
				double totalGoal7Sp = Utils.formatDouble(totalGoalBean.attribute("tg_7"), -99d);

				double halfCourtFFSp = Utils.formatDouble(halfCourtBean.attribute("hc_ff"), -99d);
				double halfCourtFPSp = Utils.formatDouble(halfCourtBean.attribute("hc_fp"), -99d);
				double halfCourtFSSp = Utils.formatDouble(halfCourtBean.attribute("hc_fs"), -99d);
				double halfCourtPFSp = Utils.formatDouble(halfCourtBean.attribute("hc_pf"), -99d);
				double halfCourtPPSp = Utils.formatDouble(halfCourtBean.attribute("hc_pp"), -99d);
				double halfCourtPSSp = Utils.formatDouble(halfCourtBean.attribute("hc_ps"), -99d);
				double halfCourtSFSp = Utils.formatDouble(halfCourtBean.attribute("hc_sf"), -99d);
				double halfCourtSPSp = Utils.formatDouble(halfCourtBean.attribute("hc_sp"), -99d);
				double halfCourtSSSp = Utils.formatDouble(halfCourtBean.attribute("hc_ss"), -99d);
				if (null == subIssueForJingCaiZuQiu) {
					try {
						subIssueForJingCaiZuQiu = new SubIssueForJingCaiZuQiu();
						subIssueForJingCaiZuQiu.setLotteryCode(LOTTERY_CODE);
						subIssueForJingCaiZuQiu.setPlayCode(playCode);
						subIssueForJingCaiZuQiu.setPollCode(pollCode);
						subIssueForJingCaiZuQiu.setIssue(date);
						subIssueForJingCaiZuQiu.setSn(sn);
						subIssueForJingCaiZuQiu.setWeek(week);
						subIssueForJingCaiZuQiu.setMatchId(matchId);
						subIssueForJingCaiZuQiu.setMatchColor(matchColor);
						subIssueForJingCaiZuQiu.setEndTime(sdfForEndTime.parse(endTime));
						subIssueForJingCaiZuQiu.setMatchName(matchName);
						subIssueForJingCaiZuQiu.setMainTeam(mainTeam);
						subIssueForJingCaiZuQiu.setGuestTeam(guestTeam);
						subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);
						subIssueForJingCaiZuQiu.setBonusOperator(Constants.SUB_ISSUE_BONUS_OPERATOR_NO);
						subIssueForJingCaiZuQiu.setCreateTime(new Date());
						subIssueForJingCaiZuQiu.setUpdateTime(new Date());
						subIssueForJingCaiZuQiu.setLetBall(letBall);
						subIssueForJingCaiZuQiu.setNegaSp(negaSp);
						subIssueForJingCaiZuQiu.setFlatSp(flatSp);
						subIssueForJingCaiZuQiu.setWinSp(winSp);
						subIssueForJingCaiZuQiu.setSpfNegaSp(spfNegaSp);
						subIssueForJingCaiZuQiu.setSpfFlatSp(spfFlatSp);
						subIssueForJingCaiZuQiu.setSpfWinSp(spfWinSp);
						subIssueForJingCaiZuQiu.setScoreFQTSp(scoreFQTSp);
						subIssueForJingCaiZuQiu.setScorePQTSp(scorePQTSp);
						subIssueForJingCaiZuQiu.setScoreSQTSp(scoreSQTSp);
						subIssueForJingCaiZuQiu.setScore00Sp(score00Sp);
						subIssueForJingCaiZuQiu.setScore01Sp(score01Sp);
						subIssueForJingCaiZuQiu.setScore02Sp(score02Sp);
						subIssueForJingCaiZuQiu.setScore03Sp(score03Sp);
						subIssueForJingCaiZuQiu.setScore04Sp(score04Sp);
						subIssueForJingCaiZuQiu.setScore05Sp(score05Sp);
						subIssueForJingCaiZuQiu.setScore10Sp(score10Sp);
						subIssueForJingCaiZuQiu.setScore11Sp(score11Sp);
						subIssueForJingCaiZuQiu.setScore12Sp(score12Sp);
						subIssueForJingCaiZuQiu.setScore13Sp(score13Sp);
						subIssueForJingCaiZuQiu.setScore14Sp(score14Sp);
						subIssueForJingCaiZuQiu.setScore15Sp(score15Sp);
						subIssueForJingCaiZuQiu.setScore20Sp(score20Sp);
						subIssueForJingCaiZuQiu.setScore21Sp(score21Sp);
						subIssueForJingCaiZuQiu.setScore22Sp(score22Sp);
						subIssueForJingCaiZuQiu.setScore23Sp(score23Sp);
						subIssueForJingCaiZuQiu.setScore24Sp(score24Sp);
						subIssueForJingCaiZuQiu.setScore25Sp(score25Sp);
						subIssueForJingCaiZuQiu.setScore30Sp(score30Sp);
						subIssueForJingCaiZuQiu.setScore31Sp(score31Sp);
						subIssueForJingCaiZuQiu.setScore32Sp(score32Sp);
						subIssueForJingCaiZuQiu.setScore33Sp(score33Sp);
						subIssueForJingCaiZuQiu.setScore40Sp(score40Sp);
						subIssueForJingCaiZuQiu.setScore41Sp(score41Sp);
						subIssueForJingCaiZuQiu.setScore42Sp(score42Sp);
						subIssueForJingCaiZuQiu.setScore50Sp(score50Sp);
						subIssueForJingCaiZuQiu.setScore51Sp(score51Sp);
						subIssueForJingCaiZuQiu.setScore52Sp(score52Sp);
						subIssueForJingCaiZuQiu.setTotalGoal0Sp(totalGoal0Sp);
						subIssueForJingCaiZuQiu.setTotalGoal1Sp(totalGoal1Sp);
						subIssueForJingCaiZuQiu.setTotalGoal2Sp(totalGoal2Sp);
						subIssueForJingCaiZuQiu.setTotalGoal3Sp(totalGoal3Sp);
						subIssueForJingCaiZuQiu.setTotalGoal4Sp(totalGoal4Sp);
						subIssueForJingCaiZuQiu.setTotalGoal5Sp(totalGoal5Sp);
						subIssueForJingCaiZuQiu.setTotalGoal6Sp(totalGoal6Sp);
						subIssueForJingCaiZuQiu.setTotalGoal7Sp(totalGoal7Sp);
						subIssueForJingCaiZuQiu.setHalfCourtFFSp(halfCourtFFSp);
						subIssueForJingCaiZuQiu.setHalfCourtFPSp(halfCourtFPSp);
						subIssueForJingCaiZuQiu.setHalfCourtFSSp(halfCourtFSSp);
						subIssueForJingCaiZuQiu.setHalfCourtPFSp(halfCourtPFSp);
						subIssueForJingCaiZuQiu.setHalfCourtPPSp(halfCourtPPSp);
						subIssueForJingCaiZuQiu.setHalfCourtPSSp(halfCourtPSSp);
						subIssueForJingCaiZuQiu.setHalfCourtSFSp(halfCourtSFSp);
						subIssueForJingCaiZuQiu.setHalfCourtSPSp(halfCourtSPSp);
						subIssueForJingCaiZuQiu.setHalfCourtSSSp(halfCourtSSSp);
						subIssueForJingCaiZuQiu.setOperatorsAward(Constants.SUB_ISSUE_BONUS_OPERATOR_NO);
						Map<String, Date> dateMap = getEndTime(LOTTERY_CODE, subIssueForJingCaiZuQiu.getEndTime());
						subIssueForJingCaiZuQiu.setEndDanShiTime(dateMap.get(END_TIME_DAN_SHI));
						subIssueForJingCaiZuQiu.setEndFuShiTime(dateMap.get(END_TIME_FU_SHI));
						subIssueForJingCaiZuQiu.setEndStatus(Constants.SUB_ISSUE_END_QUERY_YES);

						subIssueForJingCaiZuQiuService.save(subIssueForJingCaiZuQiu);

						StringBuffer key = new StringBuffer();
						key.append(subIssueForJingCaiZuQiu.getLotteryCode()).append(".").append(subIssueForJingCaiZuQiu.getIssue()).append(".");
						key.append(subIssueForJingCaiZuQiu.getPlayCode()).append(".").append(subIssueForJingCaiZuQiu.getPollCode()).append(".");
						key.append(subIssueForJingCaiZuQiu.getSn());
						memcachedClientWrapper.set(key.toString(), 0, subIssueForJingCaiZuQiu);

					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					if ("1".equals(subIssueForJingCaiZuQiu.getBackup1())) {// 不自动更新的对阵
						jingCaiZuQiuMap.remove(new StringBuilder(subIssueForJingCaiZuQiu.getIssue()).append(subIssueForJingCaiZuQiu.getSn()).append(subIssueForJingCaiZuQiu.getLotteryCode())
								.append(subIssueForJingCaiZuQiu.getPlayCode()).append(subIssueForJingCaiZuQiu.getPollCode()).toString());
						continue;
					}
					if (subIssueForJingCaiZuQiu.getEndOperator().intValue() == Constants.SUB_ISSUE_END_OPERATOR_END) {
						// 更新
						StringBuffer key = new StringBuffer();
						key.append(subIssueForJingCaiZuQiu.getLotteryCode()).append(".").append(subIssueForJingCaiZuQiu.getIssue()).append(".");
						key.append(subIssueForJingCaiZuQiu.getPlayCode()).append(".").append(subIssueForJingCaiZuQiu.getPollCode()).append(".");
						key.append(subIssueForJingCaiZuQiu.getSn());
						memcachedClientWrapper.delete(key.toString());
						jingCaiZuQiuMap.remove(new StringBuilder(subIssueForJingCaiZuQiu.getIssue()).append(subIssueForJingCaiZuQiu.getSn()).append(subIssueForJingCaiZuQiu.getLotteryCode())
								.append(subIssueForJingCaiZuQiu.getPlayCode()).append(subIssueForJingCaiZuQiu.getPollCode()).toString());
						continue;
					}
					if (subIssueForJingCaiZuQiu.getEndOperator().intValue() == Constants.SUB_ISSUE_END_OPERATOR_CANCEL) {
						// 更新
						StringBuffer key = new StringBuffer();
						key.append(subIssueForJingCaiZuQiu.getLotteryCode()).append(".").append(subIssueForJingCaiZuQiu.getIssue()).append(".");
						key.append(subIssueForJingCaiZuQiu.getPlayCode()).append(".").append(subIssueForJingCaiZuQiu.getPollCode()).append(".");
						key.append(subIssueForJingCaiZuQiu.getSn());
						memcachedClientWrapper.delete(key.toString());
						continue;
					}

					long oldTime = subIssueForJingCaiZuQiu.getEndTime().getTime();
					try {
						long diff = sdfForEndTime.parse(endTime).getTime() - oldTime;
						if (Math.abs(diff) > 60000) {
							subIssueForJingCaiZuQiu.setEndTime(sdfForEndTime.parse(endTime));
							logger.info("issue="+date+",sn="+sn+",playcode="+playCode+",diff="+diff+","+Utils.formatDate2Str(subIssueForJingCaiZuQiu.getEndTime(), "yyyyMMddHHmmss"));
						}
						subIssueForJingCaiZuQiu.setIssue(date);
						subIssueForJingCaiZuQiu.setSn(sn);
						subIssueForJingCaiZuQiu.setWeek(week);
						subIssueForJingCaiZuQiu.setMatchId(matchId);
						subIssueForJingCaiZuQiu.setMatchColor(matchColor);
						subIssueForJingCaiZuQiu.setMatchName(matchName);
						subIssueForJingCaiZuQiu.setMainTeam(mainTeam);
						subIssueForJingCaiZuQiu.setGuestTeam(guestTeam);
						subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_DEFAULT);
						subIssueForJingCaiZuQiu.setUpdateTime(new Date());
						subIssueForJingCaiZuQiu.setLetBall(letBall);
						subIssueForJingCaiZuQiu.setNegaSp(negaSp);
						subIssueForJingCaiZuQiu.setFlatSp(flatSp);
						subIssueForJingCaiZuQiu.setWinSp(winSp);
						subIssueForJingCaiZuQiu.setSpfNegaSp(spfNegaSp);
						subIssueForJingCaiZuQiu.setSpfFlatSp(spfFlatSp);
						subIssueForJingCaiZuQiu.setSpfWinSp(spfWinSp);
						subIssueForJingCaiZuQiu.setScoreFQTSp(scoreFQTSp);
						subIssueForJingCaiZuQiu.setScorePQTSp(scorePQTSp);
						subIssueForJingCaiZuQiu.setScoreSQTSp(scoreSQTSp);
						subIssueForJingCaiZuQiu.setScore00Sp(score00Sp);
						subIssueForJingCaiZuQiu.setScore01Sp(score01Sp);
						subIssueForJingCaiZuQiu.setScore02Sp(score02Sp);
						subIssueForJingCaiZuQiu.setScore03Sp(score03Sp);
						subIssueForJingCaiZuQiu.setScore04Sp(score04Sp);
						subIssueForJingCaiZuQiu.setScore05Sp(score05Sp);
						subIssueForJingCaiZuQiu.setScore10Sp(score10Sp);
						subIssueForJingCaiZuQiu.setScore11Sp(score11Sp);
						subIssueForJingCaiZuQiu.setScore12Sp(score12Sp);
						subIssueForJingCaiZuQiu.setScore13Sp(score13Sp);
						subIssueForJingCaiZuQiu.setScore14Sp(score14Sp);
						subIssueForJingCaiZuQiu.setScore15Sp(score15Sp);
						subIssueForJingCaiZuQiu.setScore20Sp(score20Sp);
						subIssueForJingCaiZuQiu.setScore21Sp(score21Sp);
						subIssueForJingCaiZuQiu.setScore22Sp(score22Sp);
						subIssueForJingCaiZuQiu.setScore23Sp(score23Sp);
						subIssueForJingCaiZuQiu.setScore24Sp(score24Sp);
						subIssueForJingCaiZuQiu.setScore25Sp(score25Sp);
						subIssueForJingCaiZuQiu.setScore30Sp(score30Sp);
						subIssueForJingCaiZuQiu.setScore31Sp(score31Sp);
						subIssueForJingCaiZuQiu.setScore32Sp(score32Sp);
						subIssueForJingCaiZuQiu.setScore33Sp(score33Sp);
						subIssueForJingCaiZuQiu.setScore40Sp(score40Sp);
						subIssueForJingCaiZuQiu.setScore41Sp(score41Sp);
						subIssueForJingCaiZuQiu.setScore42Sp(score42Sp);
						subIssueForJingCaiZuQiu.setScore50Sp(score50Sp);
						subIssueForJingCaiZuQiu.setScore51Sp(score51Sp);
						subIssueForJingCaiZuQiu.setScore52Sp(score52Sp);
						subIssueForJingCaiZuQiu.setTotalGoal0Sp(totalGoal0Sp);
						subIssueForJingCaiZuQiu.setTotalGoal1Sp(totalGoal1Sp);
						subIssueForJingCaiZuQiu.setTotalGoal2Sp(totalGoal2Sp);
						subIssueForJingCaiZuQiu.setTotalGoal3Sp(totalGoal3Sp);
						subIssueForJingCaiZuQiu.setTotalGoal4Sp(totalGoal4Sp);
						subIssueForJingCaiZuQiu.setTotalGoal5Sp(totalGoal5Sp);
						subIssueForJingCaiZuQiu.setTotalGoal6Sp(totalGoal6Sp);
						subIssueForJingCaiZuQiu.setTotalGoal7Sp(totalGoal7Sp);
						subIssueForJingCaiZuQiu.setHalfCourtFFSp(halfCourtFFSp);
						subIssueForJingCaiZuQiu.setHalfCourtFPSp(halfCourtFPSp);
						subIssueForJingCaiZuQiu.setHalfCourtFSSp(halfCourtFSSp);
						subIssueForJingCaiZuQiu.setHalfCourtPFSp(halfCourtPFSp);
						subIssueForJingCaiZuQiu.setHalfCourtPPSp(halfCourtPPSp);
						subIssueForJingCaiZuQiu.setHalfCourtPSSp(halfCourtPSSp);
						subIssueForJingCaiZuQiu.setHalfCourtSFSp(halfCourtSFSp);
						subIssueForJingCaiZuQiu.setHalfCourtSPSp(halfCourtSPSp);
						subIssueForJingCaiZuQiu.setHalfCourtSSSp(halfCourtSSSp);
						Map<String, Date> dateMap = getEndTime(LOTTERY_CODE, subIssueForJingCaiZuQiu.getEndTime());
						subIssueForJingCaiZuQiu.setEndDanShiTime(dateMap.get(END_TIME_DAN_SHI));
						subIssueForJingCaiZuQiu.setEndFuShiTime(dateMap.get(END_TIME_FU_SHI));
						subIssueForJingCaiZuQiuService.update(subIssueForJingCaiZuQiu);

						if ("00".equals(playCode) && oldTime != subIssueForJingCaiZuQiu.getEndTime().getTime()) {
							ticketReCodeService.updateTicketForIssueUpdate(subIssueForJingCaiZuQiu.getLotteryCode(), subIssueForJingCaiZuQiu.getIssue(), sn, subIssueForJingCaiZuQiu.getEndTime());
						}

						StringBuffer key = new StringBuffer();
						key.append(subIssueForJingCaiZuQiu.getLotteryCode()).append(".").append(subIssueForJingCaiZuQiu.getIssue()).append(".");
						key.append(subIssueForJingCaiZuQiu.getPlayCode()).append(".").append(subIssueForJingCaiZuQiu.getPollCode()).append(".");
						key.append(subIssueForJingCaiZuQiu.getSn());
						memcachedClientWrapper.set(key.toString(), 0, subIssueForJingCaiZuQiu);

					} catch (ParseException e) {
						e.printStackTrace();
					}
					jingCaiZuQiuMap.remove(new StringBuilder(subIssueForJingCaiZuQiu.getIssue()).append(subIssueForJingCaiZuQiu.getSn()).append(subIssueForJingCaiZuQiu.getLotteryCode())
							.append(subIssueForJingCaiZuQiu.getPlayCode()).append(subIssueForJingCaiZuQiu.getPollCode()).toString());
				}
			}

			if (jingCaiZuQiuMap.size() > 0) {
				// 为防止并发，应该提前结期处理
				
				long currTime = new Date().getTime();
				for (Map.Entry<String, SubIssueForJingCaiZuQiu> entry : jingCaiZuQiuMap.entrySet()) {
					SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = entry.getValue();
					long oldTime = subIssueForJingCaiZuQiu.getEndTime().getTime();
					if (oldTime <= currTime) {
						continue;
					}
					logger.info(LOTTERY_CODE+subIssueForJingCaiZuQiu.getIssue()+subIssueForJingCaiZuQiu.getSn()+"=not");
					/**
					subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
					subIssueForJingCaiZuQiu.setUpdateTime(new Date());
					subIssueForJingCaiZuQiuService.update(subIssueForJingCaiZuQiu);

					// 更新
					StringBuffer key = new StringBuffer();
					key.append(subIssueForJingCaiZuQiu.getLotteryCode()).append(".").append(subIssueForJingCaiZuQiu.getIssue()).append(".");
					key.append(subIssueForJingCaiZuQiu.getPlayCode()).append(".").append(subIssueForJingCaiZuQiu.getPollCode()).append(".");
					key.append(subIssueForJingCaiZuQiu.getSn());
					memcachedClientWrapper.delete(key.toString());
					 */
				}
			}
		}
	}

	public ISubIssueForJingCaiZuQiuService getSubIssueForJingCaiZuQiuService() {
		return subIssueForJingCaiZuQiuService;
	}

	public void setSubIssueForJingCaiZuQiuService(ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService) {
		this.subIssueForJingCaiZuQiuService = subIssueForJingCaiZuQiuService;
	}

	public IMainIssueService getMainIssueService() {
		return mainIssueService;
	}

	public void setMainIssueService(IMainIssueService mainIssueService) {
		this.mainIssueService = mainIssueService;
	}

}
