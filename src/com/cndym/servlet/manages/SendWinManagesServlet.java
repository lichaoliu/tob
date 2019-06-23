package com.cndym.servlet.manages;

import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.*;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.*;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * User: mcs
 * Date: 12-10-30
 * Time: 下午4:16
 */
public class SendWinManagesServlet extends BaseManagesServlet {


    private Logger logger = Logger.getLogger(getClass());

    //竞彩篮球场次列表
    private final String BASKETBALL_MATCH_LIST = "basketballMatchList";

    //竞彩篮球赛事详情
    private final String BASKETBALL_DETAIL = "basketballDetail";

    //北单
    private final String BEIDAN_MATCH_LIST = "beiDanMatchList";
    private final String BEIDAN_DETAIL = "beiDanDetail";
    private final String BEIDAN_SEND_WIN = "beiDanSendWin";

    //竞彩篮球派奖
    private final String BASKETBALL_SEND_WIN = "basketballSendWin";

    private final String SPORT_ISSUE_LIST = "sportIssueList";

    private final String SPORT_ISSUE_DETAIL = "sportIssueDetail";

    //体彩派奖
    private final String SPORT_SEND_WIN = "sportSendWin";

    //竞彩足球场次列表
    private final String FOOTBALL_MATCH_LIST = "footballMatchList";

    //竞彩足球赛事详情
    private final String FOOTBALL_DETAIL = "footballDetail";

    //竞彩足球派奖
    private final String FOOTBALL_SEND_WIN = "footballSendWin";

    //单方案补派奖列表
    private final String SIMPLE_PROGRAMS_LIST = "simpleProgramsList";
    //单方案补派奖执行
    private final String SIMPLE_PROGRAMS_DO_CALC = "simpleProgramsDoCalc";

    //派奖查询
    private final String HAND_WORK_BONUS_QUERY = "handworkBonusQuery";

    //中奖查询
    private final String BONUS_LIST = "bonusList";
    //期中奖查询
    private final String ISSUE_BONUS_LIST = "issueBonusList";

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

        if (action.equals(BASKETBALL_MATCH_LIST)) {
            String matchNo = Utils.formatStr(request.getParameter("matchNo"));
            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));

            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = new SubIssueForJingCaiLanQiu();
            if (Utils.isNotEmpty(matchNo)) {
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
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                subIssueForJingCaiLanQiu.setEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(bonusOperator)) {
                subIssueForJingCaiLanQiu.setBonusOperator(Utils.formatInt(bonusOperator, null));
                request.setAttribute("bonusOperator", bonusOperator);
            }
//            if (Utils.isNotEmpty(operatorsAward)) {
//                subIssueForJingCaiLanQiu.setOperatorsAward(Utils.formatInt(operatorsAward, Constants.OPERATORS_AWARD_COMPLETE));
//                request.setAttribute("operatorsAward", operatorsAward);
//            }
            subIssueForJingCaiLanQiu.setOperatorsAward(Utils.formatInt(operatorsAward, Constants.OPERATORS_AWARD_COMPLETE));
            subIssueForJingCaiLanQiu.setPollCode("02");
            subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT);

            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuResultListByParaDesc(subIssueForJingCaiLanQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("win/basketballMatchList.jsp").forward(request, response);
            return;
        }

        if (action.equals(BASKETBALL_DETAIL)) {
            String id = Utils.formatStr(request.getParameter("id"));
            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            if(Utils.isNotEmpty(bonusOperator)){
            	request.setAttribute("bonusOperator", bonusOperator);
            }
            if(Utils.isNotEmpty(startTime)){
            	request.setAttribute("startTime", startTime);
            }
            if(Utils.isNotEmpty(endTime)){
            	request.setAttribute("endTime", endTime);
            }
            
            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            try {
                //过关场次
                SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiu(Utils.formatLong(id));
                Integer mainTeamScore = subIssueForJingCaiLanQiu.getMainTeamScore();
                Integer guestTeamScore = subIssueForJingCaiLanQiu.getGuestTeamScore();

                //赛果
                request.setAttribute("sfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
                request.setAttribute("sfFloatAmount", subIssueForJingCaiLanQiu.getWinOrNegaSp());

                request.setAttribute("letBall", subIssueForJingCaiLanQiu.getLetBall());
                request.setAttribute("rfsfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiLanQiu.getLetBall()));
                request.setAttribute("rfsfFloatAmount", subIssueForJingCaiLanQiu.getLetWinOrNegaSp());

                request.setAttribute("preCast", subIssueForJingCaiLanQiu.getPreCast());
                request.setAttribute("dxfResult", ElTagUtils.dxfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiLanQiu.getPreCast()));
                request.setAttribute("dxfFloatAmount", subIssueForJingCaiLanQiu.getBigOrLittleSp());
                request.setAttribute("sfcResult", ElTagUtils.sfcScore(mainTeamScore, guestTeamScore));
                request.setAttribute("basketball", subIssueForJingCaiLanQiu);
            } catch (Exception e) {
                e.printStackTrace();
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
            request.getRequestDispatcher("win/basketballMatchDetail.jsp").forward(request, response);
            return;
        }

        if (action.equals(BASKETBALL_SEND_WIN)) {
            String lotteryCode = "201";
            String issue = Utils.formatStr(request.getParameter("issue"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            String week = Utils.formatStr(request.getParameter("week"));

            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            
            if(Utils.isEmpty(bonusOperator)){
            	bonusOperator = "";
            }
            if(Utils.isNotEmpty(bonusOperator)){
                request.setAttribute("bonusOperator", bonusOperator);
            }
            if(Utils.isEmpty(startTime)){
            	startTime = "";
            }
            if(Utils.isEmpty(endTime)){
            	endTime = "";
            }

            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuByIssueSn(issue, sn);
            if (!subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL) && (null == subIssueForJingCaiLanQiu.getMainTeamScore() || null == subIssueForJingCaiLanQiu.getGuestTeamScore())) {
                response.getWriter().print("<script>alert('录入的赛果格式不对');history.go(-1);</script>");
                return;
            }

            if (subIssueForJingCaiLanQiu.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                logger.error("竞彩篮球" + "(" + lotteryCode + ")第" + issue + "期派奖失败,算奖未完成");
                setManagesLog(request, action, "竞彩篮球" + "(" + lotteryCode + ")第" + issue + "期派奖失败,算奖未完成", Constants.MANAGER_LOG_WIN_MESSAGE);
                response.getWriter().print("<script>alert('算奖未完成');history.go(-1);</script>");
                return;
            }

            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            try {
                ticketService.doBonusAmountToAccount(lotteryCode, subIssueForJingCaiLanQiu.getIssue(), subIssueForJingCaiLanQiu.getSn());
                setManagesLog(request, action, "竞彩篮球(201)" + sn + "(" + issue + ")派奖成功", Constants.MANAGER_LOG_WIN_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                setManagesLog(request, action, "竞彩篮球(201)" + sn + "(" + issue + ")派奖失败", Constants.MANAGER_LOG_WIN_MESSAGE);
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
            //response.sendRedirect("/manages/sendWinManagesServlet?action=basketballMatchList&bonusOperator="+bonusOperator+"&startTime="+startTime+"&endTime="+endTime+"&page="+page+"&pageSize="+pageSize);
            request.getRequestDispatcher("/manages/sendWinManagesServlet?action=basketballMatchList").forward(request, response);
            return;
        }

        if (action.equals(SPORT_ISSUE_LIST)) {
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            Map<String, Object> sfcMap = new HashMap<String, Object>();
            sfcMap.put("lotteryName", "胜负彩14场");
            sfcMap.put("lotteryCode", "300");
            //销售中
            sfcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("300", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待派奖
            sfcMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("300", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            //已派奖（最近开奖的一期）
            sfcMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("300"));


            Map<String, Object> r9Map = new HashMap<String, Object>();
            r9Map.put("lotteryName", "胜负彩任九场");
            r9Map.put("lotteryCode", "303");
            //销售中
            r9Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("303", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            r9Map.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("303", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            //已开奖（最近开奖的一期）
            r9Map.put("lastSendWin", mainIssueService.getIssueOfLastBonus("303"));


            Map<String, Object> jqsMap = new HashMap<String, Object>();
            jqsMap.put("lotteryName", "进球数");
            jqsMap.put("lotteryCode", "302");
            jqsMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("302", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            jqsMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("302", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            jqsMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("302"));

            Map<String, Object> bqcMap = new HashMap<String, Object>();
            bqcMap.put("lotteryName", "半全场");
            bqcMap.put("lotteryCode", "301");
            bqcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("301", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            bqcMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("301", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            bqcMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("301"));

            Map<String, Object> dltMap = new HashMap<String, Object>();
            dltMap.put("lotteryName", "超级大乐透");
            dltMap.put("lotteryCode", "113");
            dltMap.put("tagInfo", "一三六开奖");
            dltMap.put("isToday", isToday(new int[]{1, 3, 6}));
            //销售中
            dltMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("113", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            dltMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("113", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            dltMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("113"));

            Map<String, Object> plsMap = new HashMap<String, Object>();
            plsMap.put("lotteryName", "排列3");
            plsMap.put("lotteryCode", "108");
            plsMap.put("tagInfo", "每天开奖");
            plsMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            //销售中
            plsMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("108", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            plsMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("108", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            plsMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("108"));

            Map<String, Object> plwMap = new HashMap<String, Object>();
            plwMap.put("lotteryName", "排列5");
            plwMap.put("lotteryCode", "109");
            plwMap.put("tagInfo", "每天开奖");
            plwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            //销售中
            plwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("109", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            plwMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("109", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            plwMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("109"));

            Map<String, Object> qxcMap = new HashMap<String, Object>();
            qxcMap.put("lotteryName", "七星彩");
            qxcMap.put("lotteryCode", "110");
            qxcMap.put("tagInfo", "二五日开奖");
            qxcMap.put("isToday", isToday(new int[]{2, 5, 7}));
            //销售中
            qxcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("110", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            qxcMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("110", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            qxcMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("110"));

            Map<String, Object> ssqMap = new HashMap<String, Object>();
            ssqMap.put("lotteryName", "双色球");
            ssqMap.put("lotteryCode", "001");
            ssqMap.put("tagInfo", "二四日开奖");
            ssqMap.put("isToday", isToday(new int[]{2, 4, 7}));
            //销售中
            ssqMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("001", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            ssqMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("001", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            ssqMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("001"));

            Map<String, Object> qlcMap = new HashMap<String, Object>();
            qlcMap.put("lotteryName", "七乐彩");
            qlcMap.put("lotteryCode", "004");
            qlcMap.put("tagInfo", "一三五开奖");
            qlcMap.put("isToday", isToday(new int[]{1, 3, 5}));
            //销售中
            qlcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("004", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            qlcMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("004", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            qlcMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("004"));

            Map<String, Object> d3Map = new HashMap<String, Object>();
            d3Map.put("lotteryName", "3D");
            d3Map.put("lotteryCode", "002");
            d3Map.put("tagInfo", "每天开奖");
            d3Map.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            //销售中
            d3Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("002", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            d3Map.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("002", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            d3Map.put("lastSendWin", mainIssueService.getIssueOfLastBonus("002"));

            Map<String, Object> df61Map = new HashMap<String, Object>();
            df61Map.put("lotteryName", "东方6+1");
            df61Map.put("lotteryCode", "005");
            df61Map.put("tagInfo", "一三六开奖");
            df61Map.put("isToday", isToday(new int[]{1, 3, 6}));
            //销售中
            df61Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("005", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            df61Map.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("005", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            df61Map.put("lastSendWin", mainIssueService.getIssueOfLastBonus("005"));

            Map<String, Object> swxwMap = new HashMap<String, Object>();
            swxwMap.put("lotteryName", "15选5");
            swxwMap.put("lotteryCode", "003");
            swxwMap.put("tagInfo", "每天开奖");
            swxwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            //销售中
            swxwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("003", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            //待开奖
            swxwMap.put("waitSendWinIssue", mainIssueService.getIssueListByAwardAndBonus("003", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
            swxwMap.put("lastSendWin", mainIssueService.getIssueOfLastBonus("003"));

//            Map<String, Object> syxwMap = new HashMap<String, Object>();
//            syxwMap.put("lotteryName", "11选5");
//            syxwMap.put("lotteryCode", "107");
//            syxwMap.put("tagInfo", "高频开奖");
//            syxwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
//            //销售中
//            syxwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("107", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
//            //待开奖
//            syxwMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("107", Constants.OPERATORS_AWARD_COMPLETE, Constants.ISSUE_BONUS_STATUS_NO));
//            syxwMap.put("lastAward", mainIssueService.getIssueOfLastAward("107"));

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            list.add(ssqMap);
            list.add(qlcMap);
            list.add(d3Map);
            list.add(df61Map);
            list.add(swxwMap);

            list.add(plsMap);
            list.add(plwMap);
            list.add(dltMap);
            list.add(qxcMap);
            list.add(sfcMap);
            list.add(r9Map);
            list.add(jqsMap);
            list.add(bqcMap);
            request.setAttribute("mapList", list);
            request.getRequestDispatcher("win/sportIssueList.jsp").forward(request, response);
            return;
        }

        if (action.equals(SPORT_ISSUE_DETAIL)) {
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");

            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));

            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);

            LotteryBean lotteryBean = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryBean(lotteryCode);
            LotteryClass lotteryClass = lotteryBean.getLotteryClass();
            request.setAttribute("lotteryName", lotteryClass.getName());

            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setTicketStatus(Constants.TICKET_STATUS_SUCCESS);
            ticket.setBonusStatus(Constants.BONUS_STATUS_YES);
            ticketQueryBean.setTicket(ticket);

            Map<String, Object> countMap = ticketService.getTicketCount(ticketQueryBean);
            if (Utils.isNotEmpty(countMap)) {
                //本期出票数量
                request.setAttribute("ticketNum", Utils.formatInt(countMap.get("ticketNum") + "", 0));
                //本期出票总金额
                request.setAttribute("orderAmount", Utils.formatDouble(countMap.get("orderAmount") + "", 0d));
                //本期中奖总金额
                request.setAttribute("bonusAmount", Utils.formatDouble(countMap.get("bonusAmount") + "", 0d));
            }

            request.setAttribute("mainIssue", mainIssue);
            request.getRequestDispatcher("win/sportIssueDetail.jsp").forward(request, response);
            return;
        }

        if (action.equals(SPORT_SEND_WIN)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String lotteryName = Utils.formatStr(request.getParameter("lotteryName"));
            String issue = Utils.formatStr(request.getParameter("issue"));

            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);

            if (mainIssue.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                logger.error(lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖失败,算奖未完成");
                setManagesLog(request, action, lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖失败,算奖未完成", Constants.MANAGER_LOG_WIN_MESSAGE);
                response.getWriter().print("<script>alert('算奖未完成');history.go(-1);</script>");
                return;
            }

            try {
                ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
                ticketService.doBonusAmountToAccount(lotteryCode, issue);

                //期销售报表
                ISaleTableService saleTableService = (ISaleTableService) SpringUtils.getBean("saleTableServiceImpl");
                saleTableService.countSaleTable(lotteryCode, issue);

                //期出票报表
                ITicketTableService ticketTableService = (ITicketTableService) SpringUtils.getBean("ticketTableServiceImpl");
                ticketTableService.countTicketTable(lotteryCode, issue);

                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖成功", Constants.MANAGER_LOG_WIN_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期派奖失败", Constants.MANAGER_LOG_WIN_MESSAGE);
            }
            response.sendRedirect("/manages/sendWinManagesServlet?action=sportIssueList");
            return;
        }


        if (action.equals(FOOTBALL_MATCH_LIST)) {
            String matchNo = Utils.formatStr(request.getParameter("matchNo"));
            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));

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
//            if (Utils.isNotEmpty(operatorsAward)) {
//                subIssueForJingCaiZuQiu.setOperatorsAward(Utils.formatInt(operatorsAward, null));
//                request.setAttribute("operatorsAward", operatorsAward);
//            }
            if (Utils.isNotEmpty(bonusOperator)) {
                subIssueForJingCaiZuQiu.setBonusOperator(Utils.formatInt(bonusOperator, null));
                request.setAttribute("bonusOperator", bonusOperator);
            }
            subIssueForJingCaiZuQiu.setOperatorsAward(Constants.OPERATORS_AWARD_COMPLETE);
            subIssueForJingCaiZuQiu.setPollCode("02");
            subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT);
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuResultListByParaDesc(subIssueForJingCaiZuQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("win/footballMatchList.jsp").forward(request, response);
        }

        if (action.equals(FOOTBALL_DETAIL)) {
            String id = Utils.formatStr(request.getParameter("id"));
            
            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            
            if(Utils.isNotEmpty(bonusOperator)){
            	request.setAttribute("bonusOperator", bonusOperator);
            }
            if(Utils.isNotEmpty(startTime)){
            	request.setAttribute("startTime", startTime);
            }
            if(Utils.isNotEmpty(endTime)){
            	request.setAttribute("endTime", endTime);
            }
            
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            try {
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiu(Utils.formatLong(id));

                Integer mainTeamScore = subIssueForJingCaiZuQiu.getMainTeamScore();
                Integer guestTeamScore = subIssueForJingCaiZuQiu.getGuestTeamScore();
                // 胜平负赛果
                request.setAttribute("spfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, ""));
                // 让球胜平负赛果
                request.setAttribute("rqspfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiZuQiu.getLetBall()));

                request.setAttribute("rqspfFloatAmount", subIssueForJingCaiZuQiu.getWinOrNegaSp());
                request.setAttribute("spfFloatAmount", subIssueForJingCaiZuQiu.getWinOrNegaSp());
                request.setAttribute("zjqsFloatAmount", subIssueForJingCaiZuQiu.getTotalGoalSp());
                request.setAttribute("bqcspfFloatAmount", subIssueForJingCaiZuQiu.getHalfCourtSp());
                //比分
                //赛果
                request.setAttribute("bfResult", ElTagUtils.bfScore(mainTeamScore, guestTeamScore));

                //总进球数
                //赛果
                request.setAttribute("zjqsResult", ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
                //半全场胜平负
                //赛果
                request.setAttribute("bqcspfResult", ElTagUtils.spfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore(), subIssueForJingCaiZuQiu.getGuestTeamHalfScore(), "0") + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
                request.setAttribute("football", subIssueForJingCaiZuQiu);
            } catch (Exception e) {
                e.printStackTrace();
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
            request.getRequestDispatcher("win/footballMatchDetail.jsp").forward(request, response);
            return;
        }

        if (action.equals(FOOTBALL_SEND_WIN)) {
            String lotteryCode = "200";
            String issue = Utils.formatStr(request.getParameter("issue"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            String week = Utils.formatStr(request.getParameter("week"));
            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            
            if(!Utils.isNotEmpty(bonusOperator)){
            	bonusOperator = "";
            }
            if(Utils.isNotEmpty(bonusOperator)){
                request.setAttribute("bonusOperator", bonusOperator);
            }
            if(!Utils.isNotEmpty(startTime)){
            	startTime = "";
            }
            if(!Utils.isNotEmpty(endTime)){
            	endTime = "";
            }

            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuByIssueSn(issue, sn);
            if (subIssueForJingCaiZuQiu.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                response.getWriter().print("<script>alert('算奖未完成');history.go(-1);</script>");
                return;
            }

            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            try {
                ticketService.doBonusAmountToAccount(lotteryCode, subIssueForJingCaiZuQiu.getIssue(), subIssueForJingCaiZuQiu.getSn());
                setManagesLog(request, action, "竞彩足球(200)" + sn + "(" + issue + ")派奖成功", Constants.MANAGER_LOG_WIN_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                setManagesLog(request, action, "竞彩足球(200)" + sn + "(" + issue + ")派奖失败", Constants.MANAGER_LOG_WIN_MESSAGE);
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
            //response.sendRedirect("/manages/sendWinManagesServlet?action=footballMatchList&bonusOperator="+bonusOperator+"&startTime="+startTime+"&endTime="+endTime+"&page="+page+"&pageSize="+pageSize);
            request.getRequestDispatcher("/manages/sendWinManagesServlet?action=footballMatchList").forward(request, response);
            return;
        }

        if (BEIDAN_MATCH_LIST.equals(action)) {
            String issueStart = Utils.formatStr(request.getParameter("issueStart"));
            String issueEnd = Utils.formatStr(request.getParameter("issueEnd"));

            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String endOperator = Utils.formatStr(request.getParameter("endOperator"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String bonusOperator = Utils.formatStr(request.getParameter("bonusOperator"));

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
            if (Utils.isNotEmpty(bonusOperator)) {
                subIssueForBeiDan.setBonusOperator(Utils.formatInt(bonusOperator, null));
                request.setAttribute("bonusOperator", bonusOperator);
            }
            subIssueForBeiDan.setOperatorsAward(Constants.OPERATORS_AWARD_COMPLETE);
            ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
            PageBean pageBean = subIssueForBeiDanService.getSubIssueForBeiDanResultListByParaDesc(subIssueForBeiDan, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("win/beiDanMatchList.jsp").forward(request, response);
            return;
        }
        if (BEIDAN_DETAIL.equals(action)) {
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
            request.getRequestDispatcher("win/beiDanMatchDetail.jsp").forward(request, response);
            return;
        }

        if (BEIDAN_SEND_WIN.equals(action)) {
            String lotteryCode = "400";

            String id = Utils.formatStr(request.getParameter("id"));
            ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
            SubIssueForBeiDan beiDan = subIssueForBeiDanService.getSubIssueForBeiDan(Utils.formatLong(id));
            if (null == beiDan) {
                response.getWriter().print("<script>alert('对阵不存在id=" + id + "');history.go(-1);</script>");
                return;
            }
            String issue = beiDan.getIssue();
            String sn = beiDan.getSn();

            if (beiDan.getOperatorsAward() != Constants.OPERATORS_AWARD_COMPLETE) {
                response.getWriter().print("<script>alert('算奖未完成');history.go(-1);</script>");
                return;
            }

            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            try {
                ticketService.doBonusAmountToAccount(lotteryCode, beiDan.getIssue(), beiDan.getSn());
                setManagesLog(request, action, "启动北单(400)" + sn + "(" + issue + ")成功", Constants.MANAGER_LOG_WIN_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                setManagesLog(request, action, "启动北单(400)" + sn + "(" + issue + ")失败", Constants.MANAGER_LOG_WIN_MESSAGE);
            }
            response.sendRedirect("/manages/sendWinManagesServlet?action=beiDanMatchList");
            return;
        }

        if (BONUS_LIST.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            Integer bigBonus = Utils.formatInt(request.getParameter("bigBonus"), null);
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));
            String returnStartTime = Utils.formatStr(request.getParameter("returnStartTime"));
            String returnEndTime = Utils.formatStr(request.getParameter("returnEndTime"));
            String bonusStartTime = Utils.formatStr(request.getParameter("bonusStartTime"));
            String bonusEndTime = Utils.formatStr(request.getParameter("bonusEndTime"));

            Ticket ticket = new Ticket();
            TicketQueryBean queryBean = new TicketQueryBean();

            ticket.setSid(sid);
            ticket.setBackup2(name);
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(outTicketId);
            ticket.setTicketId(ticketId);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("orderId", orderId);
            request.setAttribute("outTicketId", outTicketId);
            request.setAttribute("ticketId", ticketId);
            if (Utils.isNotEmpty(bigBonus)) {
                ticket.setBigBonus(bigBonus);
                request.setAttribute("bigBonus", bigBonus);
            }
            if (Utils.isNotEmpty(createStartTime)) {
                queryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            } else {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createStartTime", Utils.today("yyyy-MM-dd"));
            }

            if (Utils.isNotEmpty(createEndTime)) {
                request.setAttribute("createEndTime", createEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
            } else {
                request.setAttribute("createEndTime", Utils.today("yyyy-MM-dd"));
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(Utils.today("yyyy-MM-dd") + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateStartTime(calendar.getTime());
            }


            if (Utils.isNotEmpty(sendStartTime)) {
                request.setAttribute("sendStartTime", sendStartTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendStartTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendStartTime(calendar.getTime());
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                queryBean.setSendEndTime(Utils.formatDate(sendEndTime + " 00:00:00"));
                request.setAttribute("sendEndTime", sendEndTime);
            }

            if (Utils.isNotEmpty(returnStartTime)) {
                queryBean.setReturnStartTime(Utils.formatDate(returnStartTime + " 00:00:00"));
                request.setAttribute("returnStartTime", returnStartTime);
            }

            if (Utils.isNotEmpty(returnEndTime)) {
                request.setAttribute("returnEndTime", returnEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(returnEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setReturnEndTime(calendar.getTime());
            }

            if (Utils.isNotEmpty(bonusStartTime)) {
                queryBean.setBonusStartTime(Utils.formatDate(bonusStartTime + " 00:00:00"));
                request.setAttribute("bonusStartTime", bonusStartTime);
            }

            if (Utils.isNotEmpty(bonusEndTime)) {
                request.setAttribute("bonusEndTime", bonusEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(bonusEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setBonusEndTime(calendar.getTime());
            }
            queryBean.setTicket(ticket);
            IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
            PageBean pageBean = ticketService.getPageBeanByPara(queryBean, page, pageSize);
            Map<String, Object> dataMap = ticketService.getTicketCount(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            Double orderAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("amount"));
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmount"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmount"));
                orderAmount = Utils.formatDouble(dataMap.get("orderAmount"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("orderAmount", orderAmount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/win/bonusList.jsp").forward(request, response);
            return;
        }

        if (HAND_WORK_BONUS_QUERY.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));
            String returnStartTime = Utils.formatStr(request.getParameter("returnStartTime"));
            String returnEndTime = Utils.formatStr(request.getParameter("returnEndTime"));
            String bonusStartTime = Utils.formatStr(request.getParameter("bonusStartTime"));
            String bonusEndTime = Utils.formatStr(request.getParameter("bonusEndTime"));
            String bigBonus = Utils.formatStr(request.getParameter("bigBonus"));
            String bonusAmountS = Utils.formatStr(request.getParameter("bonusAmountS"));
            String bonusAmountE = Utils.formatStr(request.getParameter("bonusAmountE"));

            BonusTicket ticket = new BonusTicket();
            BonusTicketQueryBean ticketQueryBean = new BonusTicketQueryBean();
            if (Utils.isNotEmpty(sid)) {
                ticket.setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(name)) {
                ticket.setBackup2(name);
                request.setAttribute("name", name);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                ticket.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(ticketId)) {
                ticket.setTicketId(ticketId);
                request.setAttribute("ticketId", ticketId);
            }
            if (Utils.isNotEmpty(orderId)) {
                ticket.setOrderId(orderId);
                request.setAttribute("orderId", orderId);
            }
            if (Utils.isNotEmpty(issue)) {
                ticket.setIssue(issue);
                request.setAttribute("issue", issue);
            }
            if (Utils.isNotEmpty(outTicketId)) {
                ticket.setOutTicketId(outTicketId);
                request.setAttribute("outTicketId", outTicketId);
            }
            if (Utils.isNotEmpty(createStartTime)) {
                ticketQueryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            }
            if (Utils.isNotEmpty(createEndTime)) {
                request.setAttribute("createEndTime", createEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                ticketQueryBean.setCreateEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(sendStartTime)) {
                ticketQueryBean.setSendStartTime(Utils.formatDate(sendStartTime + " 00:00:00"));
                request.setAttribute("sendStartTime", sendStartTime);
            }
            if (Utils.isNotEmpty(sendEndTime)) {
                request.setAttribute("sendEndTime", sendEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                ticketQueryBean.setSendEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(returnStartTime)) {
                ticketQueryBean.setReturnStartTime(Utils.formatDate(returnStartTime + " 00:00:00"));
                request.setAttribute("returnStartTime", returnStartTime);
            }
            if (Utils.isNotEmpty(returnEndTime)) {
                request.setAttribute("returnEndTime", returnEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(returnEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                ticketQueryBean.setReturnEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(bonusStartTime)) {
                ticketQueryBean.setBonusStartTime(Utils.formatDate(bonusStartTime + " 00:00:00"));
                request.setAttribute("bonusStartTime", bonusStartTime);
            }
            if (Utils.isNotEmpty(bonusEndTime)) {
                request.setAttribute("bonusEndTime", bonusEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(bonusEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                ticketQueryBean.setBonusEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(bigBonus)) {
                ticket.setBigBonus(new Integer(bigBonus));
                request.setAttribute("bigBonus", bigBonus);
            }
            if (Utils.isNotEmpty(ticket)) {
                ticketQueryBean.setBonusTicket(ticket);
            }
            if (Utils.isNotEmpty(bonusAmountS)) {
                ticketQueryBean.setBonusAmountS(new Double(bonusAmountS));
                request.setAttribute("bonusAmountS", bonusAmountS);
            }
            if (Utils.isNotEmpty(bonusAmountE)) {
                ticketQueryBean.setBonusAmountE(new Double(bonusAmountE));
                request.setAttribute("bonusAmountE", bonusAmountE);
            }
            IBonusTicketService bonusTicketService = (IBonusTicketService) SpringUtils.getBean("bonusTicketServiceImpl");

            PageBean pageBean = bonusTicketService.getPageBeanByPara(ticketQueryBean, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            Map<String, Object> dataMap = bonusTicketService.getBonusTicketCount(ticketQueryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            Double orderAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("amount"));
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmount"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmount"));
                orderAmount = Utils.formatDouble(dataMap.get("orderAmount"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("orderAmount", orderAmount);
            request.getRequestDispatcher("/manages/win/handworkBonusList.jsp").forward(request, response);
            return;
        }
        if (ISSUE_BONUS_LIST.equals(action)) {
            String issue = Utils.formatStr(request.getParameter("issue"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            Ticket ticket = new Ticket();
            if (Utils.isNotEmpty(issue)) {
                ticket.setIssue(issue);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                ticket.setLotteryCode(lotteryCode);
            }
            if (Utils.isNotEmpty(sn)) {
                ticket.setEndGameId(sn);
            }
            ticket.setTicketStatus(Constants.TICKET_STATUS_SUCCESS);
            ticket.setBonusStatus(Constants.BONUS_STATUS_YES);
            IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
            PageBean pageBean = ticketService.getPageBeanByPara(ticket, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            ticketQueryBean.setTicket(ticket);
            Map<String, Object> dataMap = ticketService.getTicketCount(ticketQueryBean);
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            Double orderAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmount"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmount"));
                orderAmount = Utils.formatDouble(dataMap.get("orderAmount"));
            }
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("orderAmount", orderAmount);
            request.getRequestDispatcher("/manages/win/issueBonusList.jsp").forward(request, response);
            return;
        }
    }

    private boolean isToday(int[] day) {
        int now = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (int num : day) {
            if ((num + 1) % 8 == now) {
                return true;
            }
        }
        return false;
    }
}