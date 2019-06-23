package com.cndym.servlet.manages;

import com.cndym.adapter.tms.examine.utils.NumberAreaExamine;
import com.cndym.bean.tms.*;
import com.cndym.constant.Constants;
import com.cndym.service.*;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.bonusClass.BonusClassManager;
import com.cndym.utils.bonusClass.BonusClassUtils;
import com.cndym.utils.bonusClass.Level;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * 开奖管理
 * User: lfx
 * Date: 12-2-16 上午10:59
 */
public class OpenAwardManagesServlet extends BaseManagesServlet {

    private Logger logger = Logger.getLogger(getClass());

    private final static String OPEN_AWARD_INDEX = "openAwardIndex"; //开奖录入首界面
    private final static String EDIT_OPEN_INFO = "editOpenInfo"; //打开修改开奖信息页面
    private final static String DO_EDIT_OPEN_INFO = "doEditOpenInfo";//修改开奖信息

    private final static String OPEN_FOOTBALL_LIST = "openFootball";//打开竞彩足球列表页
    private final static String OPEN_BASKETBALL_LIST = "openBasketball";//打开竞彩篮球列表页
    private final static String OPEN_BEIDAN_LIST = "openBeiDan";//打开北京单场列表页

    private final static String EDIT_OPEN_FOOTBALL = "editOpenFootball";//打开竞彩足球赛果编辑页
    private final static String DO_EDIT_FOOTBALL = "doEditFootball";
    private final static String EDIT_OPEN_BASKETBALL = "editOpenBasketball";//打开竞彩篮球赛果编辑页
    private final static String DO_EDIT_BASKETBALL = "doEditBasketball";
    private final static String EDIT_OPEN_BEIDAN = "editOpenBeiDan";
    private final static String DO_EDIT_BEIDAN = "doEditBeiDan";
    private final static String CANCEL_OR_RECOVERY_MATCH = "cancelOrRecoveryMatch";//取消或者恢复场次

    IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
    ISubGameService subGameService = (ISubGameService) SpringUtils.getBean("subGameServiceImpl");

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        //录入开奖信息展现首页
        if (OPEN_AWARD_INDEX.equals(action)) {

            Map<String, Object> sfcMap = new HashMap<String, Object>();
            sfcMap.put("lotteryName", "胜负彩14场");
            sfcMap.put("lotteryCode", "300");
            //销售中
            //sfcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("300", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            sfcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("300", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //已开奖（最近开奖的一期）
            //sfcMap.put("lastAward", mainIssueService.getIssueOfLastAward("300"));
            logger.info(sfcMap);

            Map<String, Object> r9Map = new HashMap<String, Object>();
            r9Map.put("lotteryName", "任九场");
            r9Map.put("lotteryCode", "303");
            //销售中
            //r9Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("303", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            r9Map.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("303", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //已开奖（最近开奖的一期）
            //r9Map.put("lastAward", mainIssueService.getIssueOfLastAward("303"));
            logger.info(r9Map);

            Map<String, Object> jqsMap = new HashMap<String, Object>();
            jqsMap.put("lotteryName", "进球数");
            jqsMap.put("lotteryCode", "302");
            //jqsMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("302", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            jqsMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("302", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //jqsMap.put("lastAward", mainIssueService.getIssueOfLastAward("302"));
            logger.info(jqsMap);

            Map<String, Object> bqcMap = new HashMap<String, Object>();
            bqcMap.put("lotteryName", "半全场");
            bqcMap.put("lotteryCode", "301");
            //bqcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("301", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            bqcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("301", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //bqcMap.put("lastAward", mainIssueService.getIssueOfLastAward("301"));
            logger.info(bqcMap);

            Map<String, Object> dltMap = new HashMap<String, Object>();
            dltMap.put("lotteryName", "超级大乐透");
            dltMap.put("lotteryCode", "113");
            dltMap.put("tagInfo", "一三六开奖");
            dltMap.put("isToday", isToday(new int[]{1, 3, 6}));
            //销售中
            //dltMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("113", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            dltMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("113", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //dltMap.put("lastAward", mainIssueService.getIssueOfLastAward("113"));
            logger.info(dltMap);

            Map<String, Object> plwMap = new HashMap<String, Object>();
            plwMap.put("lotteryName", "排列3/5");
            plwMap.put("lotteryCode", "109");
            plwMap.put("tagInfo", "每天开奖");
            plwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            //销售中
            //plwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("109", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            plwMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("109", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //plwMap.put("lastAward", mainIssueService.getIssueOfLastAward("109"));
            logger.info(plwMap);

            Map<String, Object> qxcMap = new HashMap<String, Object>();
            qxcMap.put("lotteryName", "七星彩");
            qxcMap.put("lotteryCode", "110");
            qxcMap.put("tagInfo", "二五日开奖");
            qxcMap.put("isToday", isToday(new int[]{2, 5, 7}));
            //销售中
            //qxcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("110", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            qxcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("110", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //qxcMap.put("lastAward", mainIssueService.getIssueOfLastAward("110"));
            logger.info(qxcMap);

            Map<String, Object> ssqMap = new HashMap<String, Object>();
            ssqMap.put("lotteryName", "双色球");
            ssqMap.put("lotteryCode", "001");
            ssqMap.put("tagInfo", "二四日开奖");
            ssqMap.put("isToday", isToday(new int[]{2, 4, 7}));
            //销售中
            //ssqMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("001", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            ssqMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("001", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //ssqMap.put("lastAward", mainIssueService.getIssueOfLastAward("001"));
            logger.info(ssqMap);

            Map<String, Object> qlcMap = new HashMap<String, Object>();
            qlcMap.put("lotteryName", "七乐彩");
            qlcMap.put("lotteryCode", "004");
            qlcMap.put("tagInfo", "一三五开奖");
            qlcMap.put("isToday", isToday(new int[]{1, 3, 5}));
            //销售中
            //qlcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("004", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            qlcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("004", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //qlcMap.put("lastAward", mainIssueService.getIssueOfLastAward("004"));
            logger.info(qlcMap);

            Map<String, Object> d3Map = new HashMap<String, Object>();
            d3Map.put("lotteryName", "3D");
            d3Map.put("lotteryCode", "002");
            d3Map.put("tagInfo", "每天开奖");
            d3Map.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            //销售中
            //d3Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("002", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            d3Map.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("002", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            //d3Map.put("lastAward", mainIssueService.getIssueOfLastAward("002"));
            logger.info(d3Map);

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            list.add(plwMap);
            list.add(dltMap);
            list.add(qxcMap);
            list.add(sfcMap);
            list.add(r9Map);
            list.add(jqsMap);
            list.add(bqcMap);

            list.add(ssqMap);
            list.add(qlcMap);
            list.add(d3Map);
            request.setAttribute("mapList", list);

            request.getRequestDispatcher("openAward/openAwardIndex.jsp").forward(request, response);
        } else if (EDIT_OPEN_INFO.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String name = Utils.formatStr(request.getParameter("name"));
            MainIssue issue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, name);
            List<SubGame> subGameList = subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, name);
            request.setAttribute("issue", issue);
            request.setAttribute("lastIssue", mainIssueService.getIssueOfLastAward(lotteryCode));

            BonusClassManager bcm = null;
            try {
                bcm = new BonusClassManager(issue.getBonusClass());
            } catch (Exception e) {
            }
            if ("300".equals(lotteryCode)) {
                if (bcm != null) {
                    Level level1 = bcm.getLevelOfNum(1);
                    Level level2 = bcm.getLevelOfNum(2);
                    request.setAttribute("lv01", level1.getAmount());
                    request.setAttribute("lv02", level2.getAmount());
                    request.setAttribute("lv01zu", level1.getTotal());
                    request.setAttribute("lv02zu", level2.getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber().split("\\*"));
                    String[] scoreAtHalfzs = new String[14];
                    String[] scoreAtHalfks = new String[14];
                    String[] secondHalfTheScorez = new String[14];
                    String[] secondHalfTheScorek = new String[14];
                    for (int i = 0; i < subGameList.size(); i++) {
                        SubGame subGame = subGameList.get(i);
                        if (Utils.isNotEmpty(subGame.getScoreAtHalf())) {
                            String[] scoreAtHalf = subGame.getScoreAtHalf().split(":");
                            scoreAtHalfzs[i] = scoreAtHalf[0];
                            scoreAtHalfks[i] = scoreAtHalf[1];
                        }
                        if (Utils.isNotEmpty(subGame.getFinalScore())) {
                            String[] SecondHalfTheScore = subGame.getFinalScore().split(":");
                            secondHalfTheScorez[i] = SecondHalfTheScore[0];
                            secondHalfTheScorek[i] = SecondHalfTheScore[1];
                        }
                    }
                    request.setAttribute("scoreAtHalfz", scoreAtHalfzs);
                    request.setAttribute("scoreAtHalfk", scoreAtHalfks);
                    request.setAttribute("secondHalfTheScorez", secondHalfTheScorez);
                    request.setAttribute("secondHalfTheScorek", secondHalfTheScorek);
                }
                request.setAttribute("subGameList", subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, name));
                request.getRequestDispatcher("openAward/editOpenAwardInfo_sfc.jsp").forward(request, response);
                return;
            }
            if ("303".equals(lotteryCode)) {
                if (bcm != null) {
                    Level level1 = bcm.getLevelOfNum(1);
                    request.setAttribute("lv01", level1.getAmount());
                    request.setAttribute("lv01zu", level1.getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber().split("\\*"));
                    String[] scoreAtHalfzs = new String[14];
                    String[] scoreAtHalfks = new String[14];
                    String[] secondHalfTheScorez = new String[14];
                    String[] secondHalfTheScorek = new String[14];
                    for (int i = 0; i < subGameList.size(); i++) {
                        SubGame subGame = subGameList.get(i);
                        if (Utils.isNotEmpty(subGame.getScoreAtHalf())) {
                            String[] scoreAtHalf = subGame.getScoreAtHalf().split(":");
                            scoreAtHalfzs[i] = scoreAtHalf[0];
                            scoreAtHalfks[i] = scoreAtHalf[1];
                        }
                        if (Utils.isNotEmpty(subGame.getFinalScore())) {
                            String[] SecondHalfTheScore = subGame.getFinalScore().split(":");
                            secondHalfTheScorez[i] = SecondHalfTheScore[0];
                            secondHalfTheScorek[i] = SecondHalfTheScore[1];
                        }
                    }
                    request.setAttribute("scoreAtHalfz", scoreAtHalfzs);
                    request.setAttribute("scoreAtHalfk", scoreAtHalfks);
                    request.setAttribute("secondHalfTheScorez", secondHalfTheScorez);
                    request.setAttribute("secondHalfTheScorek", secondHalfTheScorek);
                }
                request.setAttribute("subGameList", subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, name));
                request.getRequestDispatcher("openAward/editOpenAwardInfo_r9.jsp").forward(request, response);
                return;
            }
            if ("301".equals(lotteryCode)) {
                if (bcm != null) {
                    Level level1 = bcm.getLevelOfNum(1);
                    request.setAttribute("lv01", level1.getAmount());
                    request.setAttribute("lv01zu", level1.getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber().split("\\*"));
                    String[] scoreAtHalfzs = new String[6];
                    String[] scoreAtHalfks = new String[6];
                    String[] secondHalfTheScorez = new String[6];
                    String[] secondHalfTheScorek = new String[6];
                    for (int i = 0; i < subGameList.size(); i++) {
                        SubGame subGame = subGameList.get(i);
                        if (Utils.isNotEmpty(subGame.getScoreAtHalf())) {
                            String[] scoreAtHalf = subGame.getScoreAtHalf().split(":");
                            scoreAtHalfzs[i] = scoreAtHalf[0];
                            scoreAtHalfks[i] = scoreAtHalf[1];
                        }
                        if (Utils.isNotEmpty(subGame.getFinalScore())) {
                            String[] SecondHalfTheScore = subGame.getFinalScore().split(":");
                            secondHalfTheScorez[i] = SecondHalfTheScore[0];
                            secondHalfTheScorek[i] = SecondHalfTheScore[1];
                        }
                    }
                    request.setAttribute("scoreAtHalfz", scoreAtHalfzs);
                    request.setAttribute("scoreAtHalfk", scoreAtHalfks);
                    request.setAttribute("secondHalfTheScorez", secondHalfTheScorez);
                    request.setAttribute("secondHalfTheScorek", secondHalfTheScorek);
                }
                request.setAttribute("subGameList", subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, name));
                request.getRequestDispatcher("openAward/editOpenAwardInfo_bqc.jsp").forward(request, response);
                return;
            }
            if ("302".equals(lotteryCode)) {
                if (bcm != null) {
                    Level level1 = bcm.getLevelOfNum(1);
                    request.setAttribute("lv01", level1.getAmount());
                    request.setAttribute("lv01zu", level1.getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber().split("\\*"));
                    String[] scoreAtHalfzs = new String[4];
                    String[] scoreAtHalfks = new String[4];
                    String[] secondHalfTheScorez = new String[4];
                    String[] secondHalfTheScorek = new String[4];
                    for (int i = 0; i < subGameList.size(); i++) {
                        SubGame subGame = subGameList.get(i);
                        if (Utils.isNotEmpty(subGame.getScoreAtHalf())) {
                            String[] scoreAtHalf = subGame.getScoreAtHalf().split(":");
                            scoreAtHalfzs[i] = scoreAtHalf[0];
                            scoreAtHalfks[i] = scoreAtHalf[1];
                        }
                        if (Utils.isNotEmpty(subGame.getFinalScore())) {
                            String[] SecondHalfTheScore = subGame.getFinalScore().split(":");
                            secondHalfTheScorez[i] = SecondHalfTheScore[0];
                            secondHalfTheScorek[i] = SecondHalfTheScore[1];
                        }
                    }
                    request.setAttribute("scoreAtHalfz", scoreAtHalfzs);
                    request.setAttribute("scoreAtHalfk", scoreAtHalfks);
                    request.setAttribute("secondHalfTheScorez", secondHalfTheScorez);
                    request.setAttribute("secondHalfTheScorek", secondHalfTheScorek);
                }
                request.setAttribute("subGameList", subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, name));
                request.getRequestDispatcher("openAward/editOpenAwardInfo_jqc.jsp").forward(request, response);
                return;
            }
            if ("001".equals(lotteryCode)) {
                if (null != bcm) {
                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                    request.setAttribute("lv04zu", bcm.getLevelOfNum(4).getTotal());
                    request.setAttribute("lv05zu", bcm.getLevelOfNum(5).getTotal());
                    request.setAttribute("lv06zu", bcm.getLevelOfNum(6).getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_ssq.jsp").forward(request, response);
                return;
            }
            if ("002".equals(lotteryCode)) {
                if (null != bcm) {
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_d3.jsp").forward(request, response);
                return;
            }
            if ("003".equals(lotteryCode)) {
                if (null != bcm) {
                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_15x5.jsp").forward(request, response);
                return;
            }
            if ("004".equals(lotteryCode)) {
                if (null != bcm) {
                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
                    request.setAttribute("lv03", bcm.getLevelOfNum(3).getAmount());
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                    request.setAttribute("lv04zu", bcm.getLevelOfNum(4).getTotal());
                    request.setAttribute("lv05zu", bcm.getLevelOfNum(5).getTotal());
                    request.setAttribute("lv06zu", bcm.getLevelOfNum(6).getTotal());
                    request.setAttribute("lv07zu", bcm.getLevelOfNum(7).getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_qlc.jsp").forward(request, response);
                return;
            }
            if ("005".equals(lotteryCode)) {
                if (null != bcm) {
                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                    request.setAttribute("lv04zu", bcm.getLevelOfNum(4).getTotal());
                    request.setAttribute("lv05zu", bcm.getLevelOfNum(5).getTotal());
                    request.setAttribute("lv06zu", bcm.getLevelOfNum(6).getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_df61.jsp").forward(request, response);
                return;
            }
            if ("113".equals(lotteryCode)) {
                if (bcm != null) {
                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03", bcm.getLevelOfNum(3).getAmount());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                    request.setAttribute("lv04", bcm.getLevelOfNum(4).getAmount());
                    request.setAttribute("lv04zu", bcm.getLevelOfNum(4).getTotal());
                    request.setAttribute("lv05", bcm.getLevelOfNum(5).getAmount());
                    request.setAttribute("lv05zu", bcm.getLevelOfNum(5).getTotal());
                    request.setAttribute("lv06", bcm.getLevelOfNum(6).getAmount());
                    request.setAttribute("lv06zu", bcm.getLevelOfNum(6).getTotal());
                    /*request.setAttribute("lv07zu", bcm.getLevelOfNum(7).getTotal());*/
                    /*request.setAttribute("lv08zu", bcm.getLevelOfNum(8).getTotal());*/

                    request.setAttribute("lv10", bcm.getLevelOfNum(10).getAmount());//生肖乐
                    request.setAttribute("lv10zu", bcm.getLevelOfNum(10).getTotal());

                    request.setAttribute("lv11", Double.valueOf(bcm.getLevelOfNum(11).getAmount()) - Double.valueOf(bcm.getLevelOfNum(1).getAmount()));//N等奖追加
                    request.setAttribute("lv11zu", bcm.getLevelOfNum(11).getTotal());
                    request.setAttribute("lv12", Double.valueOf(bcm.getLevelOfNum(12).getAmount()) - Double.valueOf(bcm.getLevelOfNum(2).getAmount()));
                    request.setAttribute("lv12zu", bcm.getLevelOfNum(12).getTotal());
                    request.setAttribute("lv13", Double.valueOf(bcm.getLevelOfNum(13).getAmount()) - Double.valueOf(bcm.getLevelOfNum(3).getAmount()));
                    request.setAttribute("lv13zu", bcm.getLevelOfNum(13).getTotal());
                    request.setAttribute("lv14", Double.valueOf(bcm.getLevelOfNum(14).getAmount()) - Double.valueOf(bcm.getLevelOfNum(4).getAmount()));
                    request.setAttribute("lv14zu", bcm.getLevelOfNum(14).getTotal());
                    request.setAttribute("lv15", Double.valueOf(bcm.getLevelOfNum(15).getAmount()) - Double.valueOf(bcm.getLevelOfNum(5).getAmount()));
                    request.setAttribute("lv15zu", bcm.getLevelOfNum(15).getTotal());
                    request.setAttribute("lv16", Double.valueOf(bcm.getLevelOfNum(16).getAmount()) - Double.valueOf(bcm.getLevelOfNum(6).getAmount()));
                    request.setAttribute("lv16zu", bcm.getLevelOfNum(16).getTotal());
                    /*request.setAttribute("lv17zu", bcm.getLevelOfNum(17).getTotal());*/
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_dlt.jsp").forward(request, response);
                return;
            }
            if ("109".equals(lotteryCode)) {
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                if (null != bcm) {
                    request.setAttribute("plwZu", bcm.getLevelOfNum(1).getTotal());
                }
                MainIssue plsIssue = mainIssueService.getMainIssueByLotteryIssue("108", name);
                request.setAttribute("plsIssue", plsIssue);
                BonusClassManager plsBcm = null;
                try {
                    plsBcm = new BonusClassManager(plsIssue.getBonusClass());
                } catch (Exception e) {
                }
                if (null != plsBcm) {
                    request.setAttribute("lv01", plsBcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02", plsBcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03", plsBcm.getLevelOfNum(3).getTotal());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_plw.jsp").forward(request, response);
                return;
            }
            if ("110".equals(lotteryCode)) {
                if (null != bcm) {
                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
                    request.setAttribute("lv03", bcm.getLevelOfNum(3).getAmount());
                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
                    request.setAttribute("lv04zu", bcm.getLevelOfNum(4).getTotal());
                    request.setAttribute("lv05zu", bcm.getLevelOfNum(5).getTotal());
                    request.setAttribute("lv06zu", bcm.getLevelOfNum(6).getTotal());
                    request.setAttribute("lv07zu", bcm.getLevelOfNum(7).getTotal());
                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_qxc.jsp").forward(request, response);
                return;
            }
            if ("107".equals(lotteryCode)) {
//                if (null != bcm) {
//                    request.setAttribute("lv01", bcm.getLevelOfNum(1).getAmount());//N等奖
//                    request.setAttribute("lv02", bcm.getLevelOfNum(2).getAmount());
//                    request.setAttribute("lv01zu", bcm.getLevelOfNum(1).getTotal());
//                    request.setAttribute("lv02zu", bcm.getLevelOfNum(2).getTotal());
//                    request.setAttribute("lv03zu", bcm.getLevelOfNum(3).getTotal());
//                }
                if (Utils.isNotEmpty(issue.getBonusNumber())) {
                    request.setAttribute("bonusNumber", issue.getBonusNumber());
                }
                request.getRequestDispatcher("openAward/editOpenAwardInfo_11x5.jsp").forward(request, response);
                return;
            }
        } else if (DO_EDIT_OPEN_INFO.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String name = Utils.formatStr(request.getParameter("name"));
            String prizePool = Utils.formatStr(request.getParameter("prizePool"));
            String globalSaleTotal = Utils.formatStr(request.getParameter("globalSaleTotal"));
            boolean flag = false;

            MainIssue issue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, name);
            String bonusNumber = request.getParameter("bonusNumber");

            try {
                List<SubGame> subGameList = subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, name);
                if ("300".equals(lotteryCode) || "301".equals(lotteryCode) || "302".equals(lotteryCode) || "303".equals(lotteryCode)) {
                    String[] scoreAtHalfzs = new String[14];
                    String[] scoreAtHalfks = new String[14];
                    String[] secondHalfTheScorezs = new String[14];
                    String[] secondHalfTheScoreks = new String[14];
                    String[] bonusNumbers = new String[14];
                    try {
                        bonusNumbers = request.getParameterValues("bonusNumber");
                        bonusNumber = buildResultAndCheck(bonusNumbers);
                        scoreAtHalfzs = request.getParameterValues("scoreAtHalfz");
                        scoreAtHalfks = request.getParameterValues("scoreAtHalfk");
                        secondHalfTheScorezs = request.getParameterValues("secondHalfTheScorez");
                        secondHalfTheScoreks = request.getParameterValues("secondHalfTheScorek");
                    } catch (Exception e) {
                        bonusNumber = null;
                    }
                    if (Utils.isEmpty(bonusNumber)) {
                        response.getWriter().print("<script>alert('录入的开奖号码格式不对');window.location.href='/manages/openAward?action=openAwardIndex';</script>");
                        return;
                    }
                    List<SubGame> subGames = new ArrayList<SubGame>();
                    int i = 0;
                    for (SubGame subGame : subGameList) {
                        //上半场比分
                        String scoreAtHalfz = Utils.isNotEmpty(scoreAtHalfzs) ? scoreAtHalfzs[i] : "";
                        String scoreAtHalfk = Utils.isNotEmpty(scoreAtHalfks) ? scoreAtHalfks[i] : "";
                        //下半场比分
                        String secondHalfTheScorez = Utils.isNotEmpty(secondHalfTheScorezs) ? secondHalfTheScorezs[i] : "";
                        String secondHalfTheScorek = Utils.isNotEmpty(secondHalfTheScoreks) ? secondHalfTheScoreks[i] : "";
                        subGame.setScoreAtHalf(Utils.isNotEmpty(scoreAtHalfz) && Utils.isNotEmpty(scoreAtHalfk) ? scoreAtHalfz + ":" + scoreAtHalfk : "");
                        subGame.setFinalScore(Utils.isNotEmpty(secondHalfTheScorez) && Utils.isNotEmpty(secondHalfTheScorek) ? secondHalfTheScorez + ":" + secondHalfTheScorek : "");
                        if ("301".equals(lotteryCode) || "302".equals(lotteryCode)) {
                            //半全场处理  、进球彩
                            int shfszInt = -1;
                            int shfskInt = -1;
                            if (Utils.isNotEmpty(secondHalfTheScorez)) {
                                shfszInt = new Integer(secondHalfTheScorez);
                            }
                            if (Utils.isNotEmpty(secondHalfTheScorek)) {
                                shfskInt = new Integer(secondHalfTheScorek);
                            }
                            if (shfszInt == -1 && shfskInt == -1) {
                                subGame.setResult("");
                            } else {
                                subGame.setResult(bonusNumbers[(2 * i) + 1]);
                            }
                        } else {
                            //14场、任九场处理
                            subGame.setResult(bonusNumbers[i]);
                        }
                        subGames.add(subGame);
                        i++;
                    }
                    subGameService.doSaveAllObject(subGames);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            BonusClassManager bcm = null;
            try {
                bcm = new BonusClassManager(issue.getBonusClass());
            } catch (Exception e) {
            	logger.error("BonusClassManager", e);
            }
            if (bcm == null) {
                bcm = new BonusClassManager();
            }

            if (Utils.isNotEmpty(bonusNumber)) {
                issue.setBonusNumber(bonusNumber);
            }
            if (Utils.isNotEmpty(prizePool)) {
                issue.setPrizePool(prizePool);
            } else {
                issue.setPrizePool("");
            }
            if (Utils.isNotEmpty(globalSaleTotal)) {
                issue.setGlobalSaleTotal(globalSaleTotal);
            } else {
                issue.setGlobalSaleTotal("");
            }


            if ("300".equals(lotteryCode)) {

                String lv01 = Utils.formatStr(request.getParameter("lv01"));
                String lv02 = Utils.formatStr(request.getParameter("lv02"));
                String lv01zu = Utils.formatStr(request.getParameter("lv01zu"));
                String lv02zu = Utils.formatStr(request.getParameter("lv02zu"));
                Level level1 = bcm.getLevelOfNum(1);
                Level level2 = bcm.getLevelOfNum(2);
                level1.setAmount(lv01);
                level2.setAmount(lv02);
                level1.setTotal(lv01zu);
                level2.setTotal(lv02zu);
                issue.setBonusClass(bcm.toString());
            } else if ("303".equals(lotteryCode)) {
                String lv01 = Utils.formatStr(request.getParameter("lv01"));
                String lv01zu = Utils.formatStr(request.getParameter("lv01zu"));
                Level level1 = bcm.getLevelOfNum(1);
                level1.setAmount(lv01);
                level1.setTotal(lv01zu);
                issue.setBonusClass(bcm.toString());
            } else if ("301".equals(lotteryCode)) {
                String lv01 = Utils.formatStr(request.getParameter("lv01"));
                String lv01zu = Utils.formatStr(request.getParameter("lv01zu"));
                Level level1 = bcm.getLevelOfNum(1);
                level1.setAmount(lv01);
                level1.setTotal(lv01zu);

                issue.setBonusClass(bcm.toString());
            } else if ("302".equals(lotteryCode)) {
                String lv01 = Utils.formatStr(request.getParameter("lv01"));
                String lv01zu = Utils.formatStr(request.getParameter("lv01zu"));
                Level level1 = bcm.getLevelOfNum(1);
                level1.setAmount(lv01);
                level1.setTotal(lv01zu);

                issue.setBonusClass(bcm.toString());
            } else if ("001".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 1, 33, 2);
                    if (!flag || !NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[1].split(","), 1, 16, 2)) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                if (Utils.isEmpty(issue.getBonusClass()) || "-".equals(issue.getBonusClass())) {
                    bcm = BonusClassUtils.getDefaultBonusClassManagerByLotteryCode("001");
                }
                bcm.getLevelOfNum(1).setAmount(Utils.formatStr(request.getParameter("lv01")));
                bcm.getLevelOfNum(2).setAmount(Utils.formatStr(request.getParameter("lv02")));

                for (int i = 1; i <= 6; i++) {
                    bcm.getLevelOfNum(i).setTotal(Utils.formatStr(request.getParameter("lv0" + i + "zu")));
                }

                issue.setBonusClass(bcm.toString());
            } else if ("005".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 0, 9, 1);
                    if (!flag || !NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[1].split(","), 1, 12, 2)) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                if (Utils.isEmpty(issue.getBonusClass()) || "-".equals(issue.getBonusClass())) {
                    bcm = BonusClassUtils.getDefaultBonusClassManagerByLotteryCode("005");
                }
                bcm.getLevelOfNum(1).setAmount(Utils.formatStr(request.getParameter("lv01")));
                bcm.getLevelOfNum(2).setAmount(Utils.formatStr(request.getParameter("lv02")));

                for (int i = 1; i <= 6; i++) {
                    bcm.getLevelOfNum(i).setTotal(Utils.formatStr(request.getParameter("lv0" + i + "zu")));
                }

                issue.setBonusClass(bcm.toString());
            } else if ("003".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 1, 15, 2);
                    if (!flag) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                if (Utils.isEmpty(issue.getBonusClass()) || "-".equals(issue.getBonusClass())) {
                    bcm = BonusClassUtils.getDefaultBonusClassManagerByLotteryCode("003");
                }
                bcm.getLevelOfNum(1).setAmount(Utils.formatStr(request.getParameter("lv01")));
                bcm.getLevelOfNum(2).setAmount(Utils.formatStr(request.getParameter("lv02")));

                for (int i = 1; i <= 3; i++) {
                    bcm.getLevelOfNum(i).setTotal(Utils.formatStr(request.getParameter("lv0" + i + "zu")));
                }

                issue.setBonusClass(bcm.toString());
            } else if ("004".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 1, 30, 2);
                    if (!flag || !NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[1].split(","), 1, 30, 2)) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                if (Utils.isEmpty(issue.getBonusClass()) || "-".equals(issue.getBonusClass())) {
                    bcm = BonusClassUtils.getDefaultBonusClassManagerByLotteryCode("004");
                }
                bcm.getLevelOfNum(1).setAmount(Utils.formatStr(request.getParameter("lv01")));
                bcm.getLevelOfNum(2).setAmount(Utils.formatStr(request.getParameter("lv02")));
                bcm.getLevelOfNum(3).setAmount(Utils.formatStr(request.getParameter("lv03")));

                for (int i = 1; i <= 7; i++) {
                    bcm.getLevelOfNum(i).setTotal(Utils.formatStr(request.getParameter("lv0" + i + "zu")));
                }

                issue.setBonusClass(bcm.toString());
            } else if ("002".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 0, 9, 1);
                    if (!flag) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                if (Utils.isEmpty(issue.getBonusClass()) || "-".equals(issue.getBonusClass())) {
                    bcm = BonusClassUtils.getDefaultBonusClassManagerByLotteryCode("002");
                }

                for (int i = 1; i <= 3; i++) {
                    bcm.getLevelOfNum(i).setTotal(Utils.formatStr(request.getParameter("lv0" + i + "zu")));
                }

                issue.setBonusClass(bcm.toString());
            } else if ("113".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 1, 35, 2);
                    if (!flag || !NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[1].split(","), 1, 12, 2)) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                	logger.error("lotteryCode"+lotteryCode, e);
                    outError(response);
                    return;
                }

                Level lv01 = bcm.getLevelOfNum(1);
                Level lv02 = bcm.getLevelOfNum(2);
                Level lv03 = bcm.getLevelOfNum(3);
                Level lv04 = bcm.getLevelOfNum(4);
                Level lv05 = bcm.getLevelOfNum(5);
                Level lv06 = bcm.getLevelOfNum(6);
                Level lv07 = bcm.getLevelOfNum(7);
                Level lv08 = bcm.getLevelOfNum(8);
                Level lv10 = bcm.getLevelOfNum(10);
                Level lv11 = bcm.getLevelOfNum(11);
                Level lv12 = bcm.getLevelOfNum(12);
                Level lv13 = bcm.getLevelOfNum(13);
                Level lv14 = bcm.getLevelOfNum(14);
                Level lv15 = bcm.getLevelOfNum(15);
                Level lv16 = bcm.getLevelOfNum(16);
                Level lv17 = bcm.getLevelOfNum(17);
                lv01.setAmount(Utils.formatStr(request.getParameter("lv01")));
                lv02.setAmount(Utils.formatStr(request.getParameter("lv02")));
                lv03.setAmount(Utils.formatStr(request.getParameter("lv03")));
                lv04.setAmount(Utils.formatStr(request.getParameter("lv04")));
                lv05.setAmount(Utils.formatStr(request.getParameter("lv05")));
                lv06.setAmount(Utils.formatStr(request.getParameter("lv06")));

                lv01.setTotal(Utils.formatStr(request.getParameter("lv01zu")));
                lv02.setTotal(Utils.formatStr(request.getParameter("lv02zu")));
                lv03.setTotal(Utils.formatStr(request.getParameter("lv03zu")));
                lv04.setTotal(Utils.formatStr(request.getParameter("lv04zu")));
                lv05.setTotal(Utils.formatStr(request.getParameter("lv05zu")));
                lv06.setTotal(Utils.formatStr(request.getParameter("lv06zu")));
                lv07.setTotal("");
                lv08.setTotal("");

                lv10.setAmount(Utils.formatStr(request.getParameter("lv10")));
                lv10.setTotal(Utils.formatStr(request.getParameter("lv10zu")));

                lv11.setAmount(String.valueOf(new BigDecimal(Utils.formatDouble(request.getParameter("lv11"), 0d) + Utils.formatDouble(request.getParameter("lv01"), 0d))));
                lv12.setAmount(String.valueOf(new BigDecimal(Utils.formatDouble(request.getParameter("lv12"), 0d) + Utils.formatDouble(request.getParameter("lv02"), 0d))));
                lv13.setAmount(String.valueOf(new BigDecimal(Utils.formatDouble(request.getParameter("lv13"), 0d) + Utils.formatDouble(request.getParameter("lv03"), 0d))));
                lv14.setAmount(String.valueOf(new BigDecimal(Utils.formatDouble(request.getParameter("lv14"), 0d) + Utils.formatDouble(request.getParameter("lv04"), 0d))));
                lv15.setAmount(String.valueOf(new BigDecimal(Utils.formatDouble(request.getParameter("lv15"), 0d) + Utils.formatDouble(request.getParameter("lv05"), 0d))));

                lv11.setTotal(Utils.formatStr(request.getParameter("lv11zu")));
                lv12.setTotal(Utils.formatStr(request.getParameter("lv12zu")));
                lv13.setTotal(Utils.formatStr(request.getParameter("lv13zu")));
                lv14.setTotal(Utils.formatStr(request.getParameter("lv14zu")));
                lv15.setTotal(Utils.formatStr(request.getParameter("lv15zu")));
                lv16.setTotal("");
                lv17.setTotal("");
                issue.setBonusClass(bcm.toString());
                if (Utils.isNotEmpty(bonusNumber)) {
                    issue.setBonusNumber(formatBonusNumber113(bonusNumber.replace("-", "#")));
                } else {
                    issue.setBonusNumber("");
                }
                String globalSaleTotal10 = Utils.formatStr(request.getParameter("globalSaleTotal10"));//生肖乐销售总额
                if (Utils.isNotEmpty(globalSaleTotal10)) {
                    issue.setBackup2(globalSaleTotal10);
                }
            } else if ("109".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 0, 9, 1);
                    if (!flag) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                if (bonusNumber.length() != "1,2,3,4,5".length()) {
                    outError(response);
                    return;
                }
                bcm.getLevelOfNum(1).setTotal(Utils.formatStr(request.getParameter("plwZu")));
                issue.setBonusClass(bcm.toString());

                MainIssue plsIssue = mainIssueService.getMainIssueByLotteryIssue("108", name);
                String plsSaleTotal = Utils.formatStr(request.getParameter("plsSaleTotal"));
                if (Utils.isNotEmpty(plsSaleTotal)) {
                    plsIssue.setGlobalSaleTotal(plsSaleTotal);
                }
                BonusClassManager plsBcm = null;
                try {
                    plsBcm = new BonusClassManager(plsIssue.getBonusClass());
                } catch (Exception e) {
                    plsBcm = new BonusClassManager();
                }

                plsBcm.getLevelOfNum(1).setTotal(Utils.formatStr(request.getParameter("lv01")));
                plsBcm.getLevelOfNum(2).setTotal(Utils.formatStr(request.getParameter("lv02")));
                plsBcm.getLevelOfNum(3).setTotal(Utils.formatStr(request.getParameter("lv03")));
                plsIssue.setBonusClass(plsBcm.toString());
                if (Utils.isNotEmpty(bonusNumber)) {
                    plsIssue.setBonusNumber(bonusNumber.substring(0, 5));
                    issue.setBonusNumber(bonusNumber);
                } else {
                    issue.setBonusNumber("");
                    plsIssue.setBonusNumber("");
                }
                mainIssueService.update(plsIssue);
            } else if ("110".equals(lotteryCode)) {
                String[] bonusNumArr = bonusNumber.split("#");
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumArr[0].split(","), 0, 9, 1);
                    if (!flag) {
                        outError(response);
                        return;
                    }
                } catch (Exception e) {
                    outError(response);
                    return;
                }

                Level lv01 = bcm.getLevelOfNum(1);
                Level lv02 = bcm.getLevelOfNum(2);
                Level lv03 = bcm.getLevelOfNum(3);
                Level lv04 = bcm.getLevelOfNum(4);
                Level lv05 = bcm.getLevelOfNum(5);
                Level lv06 = bcm.getLevelOfNum(6);
                lv01.setAmount(Utils.formatStr(request.getParameter("lv01")));
                lv02.setAmount(Utils.formatStr(request.getParameter("lv02")));
                lv01.setTotal(Utils.formatStr(request.getParameter("lv01zu")));
                lv02.setTotal(Utils.formatStr(request.getParameter("lv02zu")));
                lv03.setTotal(Utils.formatStr(request.getParameter("lv03zu")));
                lv04.setTotal(Utils.formatStr(request.getParameter("lv04zu")));
                lv05.setTotal(Utils.formatStr(request.getParameter("lv05zu")));
                lv06.setTotal(Utils.formatStr(request.getParameter("lv06zu")));
                issue.setBonusClass(bcm.toString());
                if (Utils.isNotEmpty(bonusNumber)) {
                    issue.setBonusNumber(bonusNumber);
                } else {
                    issue.setBonusNumber("");
                }
            } else if ("107".equals(lotteryCode)) {
                try {
                    flag = NumberAreaExamine.defaultNumberAraeExamine(bonusNumber.split(","), 1, 11, 2);
                    if (!flag) {
                        outError(response);
                        return;
                    }
                    if (Utils.isEmpty(issue.getBonusClass()) || "-".equals(issue.getBonusClass())) {
                        bcm = BonusClassUtils.getDefaultBonusClassManagerByLotteryCode("107");
                    }
                    issue.setBonusClass(bcm.toString());
                } catch (Exception e) {
                    outError(response);
                    return;
                }
            }
            if (mainIssueService.update(issue)) {
                setManagesLog(request, action, "保存开奖信息(" + lotteryCode + ")" + "(" + issue.getName() + ")成功", Constants.MANAGER_LOG_OPEN_AWARD_MESSAGE);
            }

            response.sendRedirect("/manages/openAward?action=openAwardIndex");
        } else if (OPEN_FOOTBALL_LIST.equals(action)) {
            String matchNo = Utils.formatStr(request.getParameter("matchNo"));
            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));

            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = new SubIssueForJingCaiZuQiu();
            if (Utils.isNotEmpty(matchNo)) {
                subIssueForJingCaiZuQiu.setMatchColor(matchNo);
                request.setAttribute("matchNo", matchNo);
            }
            if (Utils.isNotEmpty(mainTeam)) {
                subIssueForJingCaiZuQiu.setMainTeam(mainTeam);
                request.setAttribute("mainTeam", mainTeam);
            }
            if (Utils.isNotEmpty(guestTeam)) {
                subIssueForJingCaiZuQiu.setGuestTeam(guestTeam);
                request.setAttribute("guestTeam", guestTeam);
            }
            if (Utils.isNotEmpty(matchName)) {
                subIssueForJingCaiZuQiu.setMatchName(matchName);
                request.setAttribute("matchName", matchName);
            }
            if (Utils.isNotEmpty(startTime)) {
                subIssueForJingCaiZuQiu.setUpdateTime(Utils.formatDate(startTime + " 00:00:00"));
                request.setAttribute("startTime", startTime);
            }
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                subIssueForJingCaiZuQiu.setEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                if ("0".equals(operatorsAward)) {
                    subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT);
                } else if ("1".equals(operatorsAward)) {//开奖中
                    subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_END);
                    subIssueForJingCaiZuQiu.setBonusOperator(Constants.BONUS_STATUS_WAIT);
                } else if ("2".equals(operatorsAward)) {//已算奖
                    subIssueForJingCaiZuQiu.setBonusOperator(Constants.BONUS_STATUS_YES);
                } else if ("3".equals(operatorsAward)) {//已取消
                    subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
                }
                request.setAttribute("operatorsAward", operatorsAward);
            } else {
                subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_END);
            }
            subIssueForJingCaiZuQiu.setPollCode("02");
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuListByParaDesc(subIssueForJingCaiZuQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("openAward/footballMatchList.jsp").forward(request, response);
        } else if (OPEN_BASKETBALL_LIST.equals(action)) {
            String matchNo = Utils.formatStr(request.getParameter("matchNo"));
            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));

            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = new SubIssueForJingCaiLanQiu();
            if (Utils.isNotEmpty(matchNo)) {
//                subIssueForJingCaiLanQiu.setSn(matchNo);
                subIssueForJingCaiLanQiu.setMatchColor(matchNo);
                request.setAttribute("matchNo", matchNo);
            }
            if (Utils.isNotEmpty(mainTeam)) {
                subIssueForJingCaiLanQiu.setMainTeam(mainTeam);
                request.setAttribute("mainTeam", mainTeam);
            }
            if (Utils.isNotEmpty(guestTeam)) {
                subIssueForJingCaiLanQiu.setGuestTeam(guestTeam);
                request.setAttribute("guestTeam", guestTeam);
            }
            if (Utils.isNotEmpty(matchName)) {
                subIssueForJingCaiLanQiu.setMatchName(matchName);
                request.setAttribute("matchName", matchName);
            }
            if (Utils.isNotEmpty(startTime)) {
                subIssueForJingCaiLanQiu.setUpdateTime(Utils.formatDate(startTime + " 00:00:00"));
                request.setAttribute("startTime", startTime);
            }
//            else {
//                Calendar calendar = Calendar.getInstance();
//                calendar.add(Calendar.DAY_OF_YEAR, -1);
//                calendar.set(Calendar.HOUR_OF_DAY, 0);
//                calendar.set(Calendar.MINUTE, 0);
//                calendar.set(Calendar.SECOND, 0);
//                subIssueForJingCaiLanQiu.setUpdateTime(calendar.getTime());
//            }
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                subIssueForJingCaiLanQiu.setEndTime(calendar.getTime());
            }
//            else {
//            Calendar calendar = Calendar.getInstance();
//            Date endT = DateUtils.formatStr2Date(DateUtils.formatDate2Str(new Date(), "yyyy-MM-dd") + " 00:00:00");
//            calendar.setTime(endT);
//            calendar.add(Calendar.DATE, 1);
//            subIssueForJingCaiLanQiu.setEndTime(calendar.getTime());
//            }
            if (Utils.isNotEmpty(operatorsAward)) {
                if ("0".equals(operatorsAward)) {
                    subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT);
                } else if ("1".equals(operatorsAward)) {//开奖中
                    subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_END);
                    subIssueForJingCaiLanQiu.setBonusOperator(Constants.BONUS_STATUS_WAIT);
                } else if ("2".equals(operatorsAward)) {//已算奖
                    subIssueForJingCaiLanQiu.setBonusOperator(Constants.BONUS_STATUS_YES);
                } else if ("3".equals(operatorsAward)) {//已取消
                    subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
                }
                request.setAttribute("operatorsAward", operatorsAward);
            } else {
                subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_END);
            }
            subIssueForJingCaiLanQiu.setPollCode("02");
            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuListByParaDesc(subIssueForJingCaiLanQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("openAward/basketballMatchList.jsp").forward(request, response);
        } else if (OPEN_BEIDAN_LIST.equals(action)) {
            String issueStart = Utils.formatStr(request.getParameter("issueStart"));
            String issueEnd = Utils.formatStr(request.getParameter("issueEnd"));

            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String endOperator = Utils.formatStr(request.getParameter("endOperator"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));

            SubIssueForBeiDan subIssueForBeiDan = new SubIssueForBeiDan();
            if (Utils.isNotEmpty(issueStart)) {
                subIssueForBeiDan.setIssueStart(issueStart);
                request.setAttribute("issueStart", issueStart);
            }
            if (Utils.isNotEmpty(issueEnd)) {
                subIssueForBeiDan.setIssueEnd(issueEnd);
                request.setAttribute("issueEnd", issueEnd);
            }
            if (Utils.isNotEmpty(mainTeam)) {
                subIssueForBeiDan.setMainTeam(mainTeam);
                request.setAttribute("mainTeam", mainTeam);
            }
            if (Utils.isNotEmpty(guestTeam)) {
                subIssueForBeiDan.setGuestTeam(guestTeam);
                request.setAttribute("guestTeam", guestTeam);
            }
            if (Utils.isNotEmpty(matchName)) {
                subIssueForBeiDan.setMatchName(matchName);
                request.setAttribute("matchName", matchName);
            }
            if (Utils.isNotEmpty(endOperator)) {
                subIssueForBeiDan.setEndOperator(Utils.formatInt(endOperator, null));
                request.setAttribute("endOperator", endOperator);
            }
            if (Utils.isNotEmpty(startTime)) {
                subIssueForBeiDan.setUpdateTime(Utils.formatDate(startTime + " 00:00:00"));
                request.setAttribute("startTime", startTime);
            }
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                subIssueForBeiDan.setEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                subIssueForBeiDan.setOperatorsAward(Utils.formatInt(operatorsAward, null));
                request.setAttribute("operatorsAward", operatorsAward);
            }

            ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
            PageBean pageBean = subIssueForBeiDanService.getSubIssueForBeiDanResultListByParaDesc(subIssueForBeiDan, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("openAward/beiDanMatchList.jsp").forward(request, response);
            return;
        } else if (EDIT_OPEN_FOOTBALL.equals(action)) {
            String matchNo=request.getParameter("matchNo");
            if(Utils.isNotEmpty(matchNo)){
                String temp= URLDecoder.decode(matchNo,"utf-8");
                request.setAttribute("matchNo",temp);
            }
            String mainTeam=request.getParameter("mainTeam");
            if(Utils.isNotEmpty(mainTeam)){
                String temp= URLDecoder.decode(mainTeam,"utf-8") ;
                request.setAttribute("mainTeam",temp);
            }
            String guestTeam=request.getParameter("guestTeam");
            if(Utils.isNotEmpty(guestTeam)){
                String temp= URLDecoder.decode(guestTeam,"utf-8") ;
                request.setAttribute("guestTeam",temp);
            }
            String matchName=request.getParameter("matchName");
            if(Utils.isNotEmpty(matchName)){
                String temp= URLDecoder.decode(matchName,"utf-8") ;
                request.setAttribute("matchName",temp);
            }
            String operatorsAward=request.getParameter("operatorsAward");
            if(Utils.isNotEmpty(operatorsAward)){
                String temp= URLDecoder.decode(operatorsAward,"utf-8") ;
                request.setAttribute("operatorsAward",temp);
            }

           ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
           request.setAttribute("football", subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiu(Long.valueOf(request.getParameter("id"))));
            request.getRequestDispatcher("openAward/footballMatchDetail.jsp").forward(request, response);
            return;
        } else if (DO_EDIT_FOOTBALL.equals(action)) {
            String id = Utils.formatStr(request.getParameter("id"));
            String mainTeamHalfScore = Utils.formatStr(request.getParameter("mainTeamHalfScore"));
            String guestTeamHalfScore = Utils.formatStr(request.getParameter("guestTeamHalfScore"));
            String mainTeamScore = Utils.formatStr(request.getParameter("mainTeamScore"));
            String guestTeamScore = Utils.formatStr(request.getParameter("guestTeamScore"));
            String winOrNegaSp = Utils.formatStr(request.getParameter("winOrNegaSp"));
            String spfWinOrNegaSp = Utils.formatStr(request.getParameter("spfWinOrNegaSp"));
            String totalGoalSp = Utils.formatStr(request.getParameter("totalGoalSp"));
            String halfCourtSp = Utils.formatStr(request.getParameter("halfCourtSp"));
            String scoreSp = Utils.formatStr(request.getParameter("scoreSp"));

            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiu(Long.valueOf(id));
            if (Utils.isNotEmpty(mainTeamHalfScore)) {
                subIssueForJingCaiZuQiu.setMainTeamHalfScore(Integer.valueOf(mainTeamHalfScore));
            }
            if (Utils.isNotEmpty(guestTeamHalfScore)) {
                subIssueForJingCaiZuQiu.setGuestTeamHalfScore(Integer.valueOf(guestTeamHalfScore));
            }
            if (Utils.isNotEmpty(mainTeamScore)) {
                subIssueForJingCaiZuQiu.setMainTeamScore(Integer.valueOf(mainTeamScore));
            }
            if (Utils.isNotEmpty(guestTeamScore)) {
                subIssueForJingCaiZuQiu.setGuestTeamScore(Integer.valueOf(guestTeamScore));
            }
            if (Utils.isNotEmpty(winOrNegaSp)) {
                subIssueForJingCaiZuQiu.setWinOrNegaSp(Double.valueOf(winOrNegaSp));
            }
            if (Utils.isNotEmpty(spfWinOrNegaSp)) {
                subIssueForJingCaiZuQiu.setSpfWinOrNegaSp(Double.valueOf(spfWinOrNegaSp));
            }
            if (Utils.isNotEmpty(totalGoalSp)) {
                subIssueForJingCaiZuQiu.setTotalGoalSp(Double.valueOf(totalGoalSp));
            }
            if (Utils.isNotEmpty(halfCourtSp)) {
                subIssueForJingCaiZuQiu.setHalfCourtSp(Double.valueOf(halfCourtSp));
            }
            if (Utils.isNotEmpty(scoreSp)) {
                subIssueForJingCaiZuQiu.setScoreSp(Double.valueOf(scoreSp));
            }
            subIssueForJingCaiZuQiu.setInputAwardStatus(Constants.INPUT_AWARD_YES);
            if (subIssueForJingCaiZuQiuService.update(subIssueForJingCaiZuQiu)) {
                setManagesLog(request, action, "保存开奖信息(200)" + "(" + subIssueForJingCaiZuQiu.getIssue() + "|" + subIssueForJingCaiZuQiu.getSn() + ")成功", Constants.MANAGER_LOG_OPEN_AWARD_MESSAGE);
            }
            String matchNo=request.getParameter("matchNo");
            if(Utils.isNotEmpty(matchNo)){
                String temp= URLDecoder.decode(matchNo,"utf-8");
                request.setAttribute("matchNo",temp);
            }
            String mainTeam=request.getParameter("mainTeam");
            if(Utils.isNotEmpty(mainTeam)){
                String temp= URLDecoder.decode(mainTeam,"utf-8") ;
                request.setAttribute("mainTeam",temp);
            }
            String guestTeam=request.getParameter("guestTeam");
            if(Utils.isNotEmpty(guestTeam)){
                String temp= URLDecoder.decode(guestTeam,"utf-8") ;
                request.setAttribute("guestTeam",temp);
            }
            String matchName=request.getParameter("matchName");
            if(Utils.isNotEmpty(matchName)){
                String temp= URLDecoder.decode(matchName,"utf-8") ;
                request.setAttribute("matchName",temp);
            }
            String operatorsAward=request.getParameter("operatorsAward");
            if(Utils.isNotEmpty(operatorsAward)){
                String temp= URLDecoder.decode(operatorsAward,"utf-8") ;
                request.setAttribute("operatorsAward",temp);
            }
            request.getRequestDispatcher("/manages/openAward?action=openFootball").forward(request, response);
            //response.sendRedirect("/manages/openAward?action=openFootball&operatorsAward="+operatorsAward+"&page="+page+"&pageSize="+pageSize+"&matchNo="+matchNo+"&mainTeam="+mainTeam+"&guestTeam="+guestTeam+"&matchName="+matchName);
            return;
        } else if (EDIT_OPEN_BASKETBALL.equals(action)) {
            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiu(Long.valueOf(request.getParameter("id")));

            //让分胜负玩法的让分
            //SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu1 = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(), subIssueForJingCaiLanQiu.getIssue(), "02", "01");
            //subIssueForJingCaiLanQiu.setLetBall(subIssueForJingCaiLanQiu1.getLetBall());
            //大小分的预设总分
            //SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu2 = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(), subIssueForJingCaiLanQiu.getIssue(), "04", "01");
            //subIssueForJingCaiLanQiu.setPreCast(subIssueForJingCaiLanQiu2.getPreCast());

            String matchNo=request.getParameter("matchNo");
            if(Utils.isNotEmpty(matchNo)){
                String temp= URLDecoder.decode(matchNo,"utf-8");
                request.setAttribute("matchNo",temp);
            }
            String mainTeam=request.getParameter("mainTeam");
            if(Utils.isNotEmpty(mainTeam)){
                String temp= URLDecoder.decode(mainTeam,"utf-8") ;
                request.setAttribute("mainTeam",temp);
            }
            String guestTeam=request.getParameter("guestTeam");
            if(Utils.isNotEmpty(guestTeam)){
                String temp= URLDecoder.decode(guestTeam,"utf-8") ;
                request.setAttribute("guestTeam",temp);
            }
            String matchName=request.getParameter("matchName");
            if(Utils.isNotEmpty(matchName)){
                String temp= URLDecoder.decode(matchName,"utf-8") ;
                request.setAttribute("matchName",temp);
            }
            String operatorsAward=request.getParameter("operatorsAward");
            if(Utils.isNotEmpty(operatorsAward)){
                String temp= URLDecoder.decode(operatorsAward,"utf-8") ;
                request.setAttribute("operatorsAward",temp);
            }

            request.setAttribute("basketball", subIssueForJingCaiLanQiu);
            request.getRequestDispatcher("openAward/basketballMatchDetail.jsp").forward(request, response);
            return;
        } else if (DO_EDIT_BASKETBALL.equals(action)) {
            String id = Utils.formatStr(request.getParameter("id"));
            String mainTeamHalfScore = Utils.formatStr(request.getParameter("mainTeamHalfScore"));
            String guestTeamHalfScore = Utils.formatStr(request.getParameter("guestTeamHalfScore"));
            String mainTeamScore = Utils.formatStr(request.getParameter("mainTeamScore"));
            String guestTeamScore = Utils.formatStr(request.getParameter("guestTeamScore"));
            String winOrNegaSp = Utils.formatStr(request.getParameter("winOrNegaSp"));
            String letWinOrNegaSp = Utils.formatStr(request.getParameter("letWinOrNegaSp"));
            String winNegaDiffSp = Utils.formatStr(request.getParameter("winNegaDiffSp"));
            String bigOrLittleSp = Utils.formatStr(request.getParameter("bigOrLittleSp"));

            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiu(Long.valueOf(id));
            if (Utils.isNotEmpty(mainTeamHalfScore)) {
                subIssueForJingCaiLanQiu.setMainTeamHalfScore(Integer.valueOf(mainTeamHalfScore));
            }
            if (Utils.isNotEmpty(guestTeamHalfScore)) {
                subIssueForJingCaiLanQiu.setGuestTeamHalfScore(Integer.valueOf(guestTeamHalfScore));
            }
            if (Utils.isNotEmpty(mainTeamScore)) {
                subIssueForJingCaiLanQiu.setMainTeamScore(Integer.valueOf(mainTeamScore));
            }
            if (Utils.isNotEmpty(guestTeamScore)) {
                subIssueForJingCaiLanQiu.setGuestTeamScore(Integer.valueOf(guestTeamScore));
            }
            if (Utils.isNotEmpty(winOrNegaSp)) {
                subIssueForJingCaiLanQiu.setWinOrNegaSp(Double.valueOf(winOrNegaSp));
            }
            if (Utils.isNotEmpty(letWinOrNegaSp)) {
                subIssueForJingCaiLanQiu.setLetWinOrNegaSp(Double.valueOf(letWinOrNegaSp));
            }
            if (Utils.isNotEmpty(winNegaDiffSp)) {
                subIssueForJingCaiLanQiu.setWinNegaDiffSp(Double.valueOf(winNegaDiffSp));
            }
            if (Utils.isNotEmpty(bigOrLittleSp)) {
                subIssueForJingCaiLanQiu.setBigOrLittleSp(Double.valueOf(bigOrLittleSp));
            }
            subIssueForJingCaiLanQiu.setInputAwardStatus(Constants.INPUT_AWARD_YES);
            if (subIssueForJingCaiLanQiuService.update(subIssueForJingCaiLanQiu)) {
                setManagesLog(request, action, "保存开奖信息(201)" + "(" + subIssueForJingCaiLanQiu.getIssue() + "|" + subIssueForJingCaiLanQiu.getSn() + ")成功", Constants.MANAGER_LOG_OPEN_AWARD_MESSAGE);
            }
            String matchNo=request.getParameter("matchNo");
            if(Utils.isNotEmpty(matchNo)){
                String temp= URLDecoder.decode(matchNo,"utf-8");
                request.setAttribute("matchNo",temp);
            }
            String mainTeam=request.getParameter("mainTeam");
            if(Utils.isNotEmpty(mainTeam)){
                String temp= URLDecoder.decode(mainTeam,"utf-8") ;
                request.setAttribute("mainTeam",temp);
            }
            String guestTeam=request.getParameter("guestTeam");
            if(Utils.isNotEmpty(guestTeam)){
                String temp= URLDecoder.decode(guestTeam,"utf-8") ;
                request.setAttribute("guestTeam",temp);
            }
            String matchName=request.getParameter("matchName");
            if(Utils.isNotEmpty(matchName)){
                String temp= URLDecoder.decode(matchName,"utf-8") ;
                request.setAttribute("matchName",temp);
            }
            String operatorsAward=request.getParameter("operatorsAward");
            if(Utils.isNotEmpty(operatorsAward)){
                String temp= URLDecoder.decode(operatorsAward,"utf-8") ;
                request.setAttribute("operatorsAward",temp);
            }
            request.getRequestDispatcher("/manages/openAward?action=openBasketball").forward(request, response);

            //response.sendRedirect("/manages/openAward?action=openFootball&operatorsAward="+operatorsAward+"&page="+page+"&pageSize="+pageSize+"&matchNo="+matchNo+"&mainTeam="+mainTeam+"&guestTeam="+guestTeam
                  //  +"&matchName="+matchName);
            return;
        } else if (EDIT_OPEN_BEIDAN.equals(action)) {
            String id = Utils.formatStr(request.getParameter("id"));
            ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
            try {
                SubIssueForBeiDan subIssueForBeiDan = subIssueForBeiDanService.getSubIssueForBeiDan(Utils.formatLong(id));

                Integer mainTeamScore = subIssueForBeiDan.getMainTeamScore();
                Integer guestTeamScore = subIssueForBeiDan.getGuestTeamScore();
                //胜平负
                //赛果
                request.setAttribute("spfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForBeiDan.getLetBall()));
                //浮动奖金
                request.setAttribute("spfFloatAmount", subIssueForBeiDan.getWinOrNegaSp());

                //上下单双
                //赛果
                request.setAttribute("sxdsResult", ElTagUtils.sxdsScore(mainTeamScore, guestTeamScore));
                //浮动奖金
                request.setAttribute("sxdsFloatAmount", subIssueForBeiDan.getShangXiaPanSp());

                //比分
                //赛果
                request.setAttribute("bfResult", ElTagUtils.bfScoreForBeiDan(mainTeamScore, guestTeamScore));
                request.setAttribute("bfFloatAmount", subIssueForBeiDan.getScoreSp());

                //总进球数
                //赛果
                request.setAttribute("zjqsResult", ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
                //浮动奖金
                request.setAttribute("zjqsFloatAmount", subIssueForBeiDan.getTotalGoalSp());

                //半全场胜平负
                //赛果
                request.setAttribute("bqcspfResult", ElTagUtils.spfScore(subIssueForBeiDan.getMainTeamHalfScore(), subIssueForBeiDan.getGuestTeamHalfScore(), "0") + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
                //浮动奖金
                request.setAttribute("bqcspfFloatAmount", subIssueForBeiDan.getHalfCourtSp());

                request.setAttribute("beiDan", subIssueForBeiDan);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("openAward/beiDanMatchDetail.jsp").forward(request, response);
            return;
        } else if (DO_EDIT_BEIDAN.equals(action)) {
            String id = Utils.formatStr(request.getParameter("id"));
            ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
            SubIssueForBeiDan beiDan = subIssueForBeiDanService.getSubIssueForBeiDan(Utils.formatLong(id));

            if (Constants.OPERATORS_AWARD_WAIT != beiDan.getOperatorsAward()) {
                response.getWriter().print("<script>alert('该对阵已经算奖，不能保存修改的信息!');history.go(-1);</script>");
                return;
            }

            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            beiDan.setMainTeam(mainTeam);
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            beiDan.setGuestTeam(guestTeam);
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            beiDan.setMatchName(matchName);

            String mainTeamScore = Utils.formatStr(request.getParameter("mainTeamScore"));
            beiDan.setMainTeamScore(Utils.formatInt(mainTeamScore, null));
            String guestTeamScore = Utils.formatStr(request.getParameter("guestTeamScore"));
            beiDan.setGuestTeamScore(Utils.formatInt(guestTeamScore, null));
            String mainTeamHalfScore = Utils.formatStr(request.getParameter("mainTeamHalfScore"));
            beiDan.setMainTeamHalfScore(Utils.formatInt(mainTeamHalfScore, null));
            String guestTeamHalfScore = Utils.formatStr(request.getParameter("guestTeamHalfScore"));
            beiDan.setGuestTeamHalfScore(Utils.formatInt(guestTeamHalfScore, null));

            String winOrNegaSp = Utils.formatStr(request.getParameter("winOrNegaSp"));
            beiDan.setWinOrNegaSp(Utils.formatDouble(winOrNegaSp, null));
            String shangXiaPanSp = Utils.formatStr(request.getParameter("shangXiaPanSp"));
            beiDan.setShangXiaPanSp(Utils.formatDouble(shangXiaPanSp, null));
            String totalGoalSp = Utils.formatStr(request.getParameter("totalGoalSp"));
            beiDan.setTotalGoalSp(Utils.formatDouble(totalGoalSp, null));
            String halfCourtSp = Utils.formatStr(request.getParameter("halfCourtSp"));
            beiDan.setHalfCourtSp(Utils.formatDouble(halfCourtSp, null));
            String scoreSp = Utils.formatStr(request.getParameter("scoreSp"));
            beiDan.setScoreSp(Utils.formatDouble(scoreSp, null));

            if (null != mainTeamScore && null != guestTeamScore && null != mainTeamHalfScore && null != guestTeamHalfScore && null != winOrNegaSp && null != shangXiaPanSp && null != totalGoalSp && null != halfCourtSp && null != scoreSp) {
                beiDan.setInputAwardStatus(Constants.INPUT_AWARD_YES);
            }

            if (subIssueForBeiDanService.update(beiDan)) {
                if (null != beiDan.getEndTime()) {
                    //更新末关
                    ITicketReCodeService ticketReCodeService = (ITicketReCodeService) SpringUtils.getBean("ticketReCodeServiceImpl");
                    ticketReCodeService.updateTicketForIssueUpdate(beiDan.getLotteryCode(), beiDan.getIssue(), beiDan.getSn(), beiDan.getEndTime());
                }
                setManagesLog(request, action, "保存开奖信息(400)" + "(" + beiDan.getIssue() + "|" + beiDan.getSn() + ")成功", Constants.MANAGER_LOG_OPEN_AWARD_MESSAGE);
            }
            response.sendRedirect("/manages/openAward?action=openBeiDan");
            return;
        } else if (CANCEL_OR_RECOVERY_MATCH.equals(action)) {
            String type = request.getParameter("type");
            String lotteryCode = request.getParameter("lotteryCode");
            String sn = request.getParameter("sn");
            String issue = request.getParameter("issue");
            String id = request.getParameter("id");
            if (Utils.isEmpty(type) || Utils.isEmpty(lotteryCode) || Utils.isEmpty(sn) || Utils.isEmpty(issue)) {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            int updateCount = 0;
            Integer changeStatus = null;
            String operationInfo = "";
            String reAction = "";
            if ("cancel".equals(type)) {
                changeStatus = Constants.SUB_ISSUE_END_OPERATOR_CANCEL;
                operationInfo = "[取消]";
            } else if ("recovery".equals(type)) {
                changeStatus = Constants.SUB_ISSUE_END_OPERATOR_DEFAULT;
                operationInfo = "[销售中]";
            } else if ("end".equals(type)) {
                changeStatus = Constants.SUB_ISSUE_END_OPERATOR_END;
                operationInfo = "[比赛结束]";
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            if ("200".equals(lotteryCode)) {
                ISubIssueForJingCaiZuQiuService zuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
                updateCount = zuQiuService.doUpdateOperatorForSn(issue, sn, changeStatus);
                reAction = EDIT_OPEN_FOOTBALL;
            } else if ("201".equals(lotteryCode)) {
                ISubIssueForJingCaiLanQiuService lanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
                updateCount = lanQiuService.doUpdateOperatorForSn(issue, sn, changeStatus);
                reAction = EDIT_OPEN_BASKETBALL;
            } else if ("400".equals(lotteryCode)) {
                ISubIssueForBeiDanService beiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
                updateCount = beiDanService.doUpdateOperatorForSn(issue, sn, changeStatus);
                reAction = EDIT_OPEN_BEIDAN;
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            if (updateCount > 0) {
                setManagesLog(request, action, "将对阵状态设置为" + operationInfo + lotteryCode + issue + sn, Constants.MANAGER_LOG_JC_ISSUE_MANAGES);
            }
            response.sendRedirect("/manages/openAward?action=" + reAction + "&id=" + id);
            return;
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

    }

    private String buildResultAndCheck(String[] results) {
        StringBuffer sb = new StringBuffer();
        int[] mask = {0, 1, 2, 3, 4, 9};
        for (String str : results) {
            if (Utils.isEmpty(str)) {
                sb.append("*");
            } else {
                if (isInArr(Integer.parseInt(str), mask)) {
                    sb.append(str + "*");
                } else {
                    return null;
                }
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 判断num是否存在于numArr中
     *
     * @param num
     * @param numArr
     * @return
     */
    private boolean isInArr(int num, int[] numArr) {
        for (int one : numArr) {
            if (num == one) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证当天的星期几是否包含在day里
     *
     * @param day 星期一至星期日 1-7
     * @return
     */
    private boolean isToday(int[] day) {
        int now = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (int num : day) {
            if ((num + 1) % 8 == now) {
                return true;
            }
        }
        return false;
    }

    /**
     * 大乐透录入开奖号码格式化
     *
     * @param str
     * @return
     */
    private String formatBonusNumber113(String str) {
        StringBuffer num = new StringBuffer();
        String[] left = str.split("#")[0].split(",");
        String[] right = str.split("#")[1].split(",");
        for (int i = 0; i < left.length; i++) {
            if (left[i].length() == 1) {
                num.append("0");
            }
            num.append(left[i]);
            if (i < left.length - 1) {
                num.append(",");
            }
        }
        num.append("#");
        for (int i = 0; i < right.length; i++) {
            if (right[i].length() == 1) {
                num.append("0");
            }
            num.append(right[i]);
            if (i < right.length - 1) {
                num.append(",");
            }
        }
        return num.toString();
    }

    private void outError(HttpServletResponse response) throws IOException {
        response.getWriter().print("<script>alert('录入的开奖号码格式不对');history.go(-1);</script>");
        return;
    }

    public static void main(String[] args) {
    }
}
