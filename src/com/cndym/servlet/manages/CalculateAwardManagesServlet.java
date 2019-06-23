package com.cndym.servlet.manages;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.*;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.*;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.*;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * User: mcs Date: 12-2-18 Time: 下午5:22
 */
public class CalculateAwardManagesServlet extends BaseManagesServlet {

    private Logger logger = Logger.getLogger(getClass());

    // 竞彩篮球场次列表
    private final String BASKETBALL_MATCH_LIST = "basketballMatchList";

    // 竞彩篮球赛事详情
    private final String BASKETBALL_DETAIL = "basketballDetail";

    // 北单
    private final String BEIDAN_MATCH_LIST = "beiDanMatchList";
    private final String BEIDAN_DETAIL = "beiDanDetail";
    private final String BEIDAN_CALCULATE_AWARD = "beiDanCalculateAward";

    // 竞彩篮球算奖
    private final String BASKETBALL_CALCULATE_AWARD = "basketballCalculateAward";

    private final String SPORT_ISSUE_LIST = "sportIssueList";

    private final String SPORT_ISSUE_DETAIL = "sportIssueDetail";

    // 体彩算奖
    private final String SPORT_CALCULATE_AWARD = "sportCalculateAward";

    // 竞彩足球场次列表
    private final String FOOTBALL_MATCH_LIST = "footballMatchList";

    // 竞彩足球赛事详情
    private final String FOOTBALL_DETAIL = "footballDetail";

    // 竞彩足球算奖
    private final String FOOTBALL_CALCULATE_AWARD = "footballCalculateAward";

    // 单方案补算奖列表
    private final String SIMPLE_PROGRAMS_LIST = "simpleProgramsList";
    // 单方案补算奖执行
    private final String SIMPLE_PROGRAMS_DO_CALC = "simpleProgramsDoCalc";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            if (Utils.isNotEmpty(operatorsAward)) {
                subIssueForJingCaiLanQiu.setOperatorsAward(Utils.formatInt(operatorsAward, null));
                request.setAttribute("operatorsAward", operatorsAward);
            }
            subIssueForJingCaiLanQiu.setPollCode("02");
            subIssueForJingCaiLanQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT);

            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuResultListByParaDesc(subIssueForJingCaiLanQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("calAward/basketballMatchList.jsp").forward(request, response);
            return;
        }

        if (action.equals(BASKETBALL_DETAIL)) {
            String id = Utils.formatStr(request.getParameter("id"));
            
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            if (Utils.isNotEmpty(operatorsAward)) {
            	request.setAttribute("operatorsAward", operatorsAward);
            }
            if (Utils.isNotEmpty(startTime)) {
            	request.setAttribute("startTime", startTime);
            }
            if (Utils.isNotEmpty(endTime)) {
            	request.setAttribute("endTime", endTime);
            }
            
            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            try {
                // 过关场次
                SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiu(Utils.formatLong(id));
                Integer mainTeamScore = subIssueForJingCaiLanQiu.getMainTeamScore();
                Integer guestTeamScore = subIssueForJingCaiLanQiu.getGuestTeamScore();

                // 赛果
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

            request.getRequestDispatcher("calAward/basketballMatchDetail.jsp").forward(request, response);
            return;
        }

        if (action.equals(BASKETBALL_CALCULATE_AWARD)) {
            String lotteryCode = "201";
            String issue = Utils.formatStr(request.getParameter("issue"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            String week = Utils.formatStr(request.getParameter("week"));
            
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            if (Utils.isEmpty(operatorsAward)) {
            	operatorsAward = "";
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                request.setAttribute("operatorsAward", operatorsAward);
            }
            if (Utils.isEmpty(startTime)) {
            	startTime = "";
            }
            if (Utils.isEmpty(endTime)) {
            	endTime = "";
            }

            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuByIssueSn(issue, sn);
            if (!subIssueForJingCaiLanQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)
                    && (null == subIssueForJingCaiLanQiu.getMainTeamScore() || null == subIssueForJingCaiLanQiu.getGuestTeamScore())) {
                response.getWriter().print("<script>alert('录入的赛果格式不对');history.go(-1);</script>");
                return;
            }

            // 判断票的状态
            IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            ticket.setLotteryCode(lotteryCode);
            ticket.setEndGameId(issue + sn);
            ticket.setTicketStatus(Constants.TICKET_STATUS_DOING);
            ticketQueryBean.setTicket(ticket);
            Map<String, Object> ticketMap = ticketService.getTicketCount(ticketQueryBean);
            if (Utils.isNotEmpty(ticketMap)) {
                int ticketNum = Utils.formatInt(ticketMap.get("ticketNum") + "", 0);
                if (ticketNum > 0) {
                    response.getWriter().print("<script>alert('有未收到回执的票!');history.go(-1);</script>");
                }
            }

            StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
            interfaceUrl.append("?lotteryCode=").append(lotteryCode);
            interfaceUrl.append("&issue=").append(issue);
            interfaceUrl.append("&sn=").append(sn);

            HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
            logger.info("发送到URL:" + interfaceUrl);
            String reStr = "";
            try {
                reStr = httpClientUtils.httpClientGet();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            logger.error("返回信息:" + reStr);

            if (Utils.isNotEmpty(reStr) && reStr.equals("success")) {
                setManagesLog(request, action, "启动竞彩篮球(201)" + week + sn + "(" + issue + ")成功", Constants.MANAGER_LOG_START_CAL_AWARD);
            } else {
                setManagesLog(request, action, "启动竞彩篮球((201))" + week + sn + "(" + issue + ")失败", Constants.MANAGER_LOG_START_CAL_AWARD);
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
            //response.sendRedirect("/manages/calculateAwardServlet?action=basketballMatchList&operatorsAward="+operatorsAward+"&startTime="+startTime+"&endTime="+endTime+"&page="+page+"&pageSize="+pageSize);
           // response.sendRedirect("/manages/calculateAwardServlet?action=basketballMatchList&operatorsAward="+operatorsAward+"&startTime="+startTime+"&endTime="+endTime+"&page="+page+"&pageSize="+pageSize
           //         +"&matchNo="+matchNo+"&mainTeam="+mainTeam+"&guestTeam="+guestTeam+"&matchName="+matchName);
            request.getRequestDispatcher("/manages/calculateAwardServlet?action=basketballMatchList").forward(request, response);
            return;
        }

        if (action.equals(SPORT_ISSUE_LIST)) {
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            Map<String, Object> sfcMap = new HashMap<String, Object>();
            sfcMap.put("lotteryName", "胜负彩14场");
            sfcMap.put("lotteryCode", "300");
            // 销售中
            sfcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("300", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            sfcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("300", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            // 已开奖（最近开奖的一期）
            sfcMap.put("lastAward", mainIssueService.getIssueOfLastAward("300"));

            Map<String, Object> r9Map = new HashMap<String, Object>();
            r9Map.put("lotteryName", "胜负彩任九场");
            r9Map.put("lotteryCode", "303");
            // 销售中
            r9Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("303", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            r9Map.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("303", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            // 已开奖（最近开奖的一期）
            r9Map.put("lastAward", mainIssueService.getIssueOfLastAward("303"));

            Map<String, Object> jqsMap = new HashMap<String, Object>();
            jqsMap.put("lotteryName", "进球数");
            jqsMap.put("lotteryCode", "302");
            jqsMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("302", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            jqsMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("302", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            jqsMap.put("lastAward", mainIssueService.getIssueOfLastAward("302"));

            Map<String, Object> bqcMap = new HashMap<String, Object>();
            bqcMap.put("lotteryName", "半全场");
            bqcMap.put("lotteryCode", "301");
            bqcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("301", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            bqcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("301", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            bqcMap.put("lastAward", mainIssueService.getIssueOfLastAward("301"));

            Map<String, Object> dltMap = new HashMap<String, Object>();
            dltMap.put("lotteryName", "超级大乐透");
            dltMap.put("lotteryCode", "113");
            dltMap.put("tagInfo", "一三六开奖");
            dltMap.put("isToday", isToday(new int[]{1, 3, 6}));
            // 销售中
            dltMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("113", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            dltMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("113", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            dltMap.put("lastAward", mainIssueService.getIssueOfLastAward("113"));

            Map<String, Object> plsMap = new HashMap<String, Object>();
            plsMap.put("lotteryName", "排列3");
            plsMap.put("lotteryCode", "108");
            plsMap.put("tagInfo", "每天开奖");
            plsMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            // 销售中
            plsMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("108", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            plsMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("108", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            plsMap.put("lastAward", mainIssueService.getIssueOfLastAward("108"));

            Map<String, Object> plwMap = new HashMap<String, Object>();
            plwMap.put("lotteryName", "排列5");
            plwMap.put("lotteryCode", "109");
            plwMap.put("tagInfo", "每天开奖");
            plwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            // 销售中
            plwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("109", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            plwMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("109", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            plwMap.put("lastAward", mainIssueService.getIssueOfLastAward("109"));

            Map<String, Object> qxcMap = new HashMap<String, Object>();
            qxcMap.put("lotteryName", "七星彩");
            qxcMap.put("lotteryCode", "110");
            qxcMap.put("tagInfo", "二五日开奖");
            qxcMap.put("isToday", isToday(new int[]{2, 5, 7}));
            // 销售中
            qxcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("110", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            qxcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("110", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            qxcMap.put("lastAward", mainIssueService.getIssueOfLastAward("110"));

            Map<String, Object> ssqMap = new HashMap<String, Object>();
            ssqMap.put("lotteryName", "双色球");
            ssqMap.put("lotteryCode", "001");
            ssqMap.put("tagInfo", "二四日开奖");
            ssqMap.put("isToday", isToday(new int[]{2, 4, 7}));
            // 销售中
            ssqMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("001", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            ssqMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("001", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            ssqMap.put("lastAward", mainIssueService.getIssueOfLastAward("001"));

            Map<String, Object> qlcMap = new HashMap<String, Object>();
            qlcMap.put("lotteryName", "七乐彩");
            qlcMap.put("lotteryCode", "004");
            qlcMap.put("tagInfo", "一三五开奖");
            qlcMap.put("isToday", isToday(new int[]{1, 3, 5}));
            // 销售中
            qlcMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("004", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            qlcMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("004", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            qlcMap.put("lastAward", mainIssueService.getIssueOfLastAward("004"));

            Map<String, Object> d3Map = new HashMap<String, Object>();
            d3Map.put("lotteryName", "3D");
            d3Map.put("lotteryCode", "002");
            d3Map.put("tagInfo", "每天开奖");
            d3Map.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            // 销售中
            d3Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("002", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            d3Map.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("002", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            d3Map.put("lastAward", mainIssueService.getIssueOfLastAward("002"));

            Map<String, Object> df61Map = new HashMap<String, Object>();
            df61Map.put("lotteryName", "东方6+1");
            df61Map.put("lotteryCode", "005");
            df61Map.put("tagInfo", "一三六开奖");
            df61Map.put("isToday", isToday(new int[]{1, 3, 6}));
            // 销售中
            df61Map.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("005", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            df61Map.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("005", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            df61Map.put("lastAward", mainIssueService.getIssueOfLastAward("005"));

            Map<String, Object> swxwMap = new HashMap<String, Object>();
            swxwMap.put("lotteryName", "15选5");
            swxwMap.put("lotteryCode", "003");
            swxwMap.put("tagInfo", "每天开奖");
            swxwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            // 销售中
            swxwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("003", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // 待开奖
            swxwMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("003", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            swxwMap.put("lastAward", mainIssueService.getIssueOfLastAward("003"));

            // Map<String, Object> syxwMap = new HashMap<String, Object>();
            // syxwMap.put("lotteryName", "11选5");
            // syxwMap.put("lotteryCode", "107");
            // syxwMap.put("tagInfo", "高频开奖");
            // syxwMap.put("isToday", isToday(new int[]{1, 2, 3, 4, 5, 6, 7}));
            // //销售中
            // syxwMap.put("saleIssue", mainIssueService.getIssueListByStatusAndAward("107", Constants.ISSUE_STATUS_START, Constants.OPERATORS_AWARD_WAIT));
            // //待开奖
            // syxwMap.put("waitAwardIssue", mainIssueService.getIssueListByStatusAndAward("107", Constants.ISSUE_STATUS_END, Constants.OPERATORS_AWARD_WAIT));
            // syxwMap.put("lastAward", mainIssueService.getIssueOfLastAward("107"));

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
            request.getRequestDispatcher("calAward/sportIssueList.jsp").forward(request, response);
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
            ticketQueryBean.setTicket(ticket);

            Map<String, Object> countMap = ticketService.getTicketCount(ticketQueryBean);
            if (Utils.isNotEmpty(countMap)) {
                // 本期出票数量
                request.setAttribute("ticketNum", Utils.formatInt(countMap.get("ticketNum") + "", 0));
                // 本期出票总金额
                request.setAttribute("orderAmount", Utils.formatDouble(countMap.get("orderAmount") + "", 0d));
            }
            request.setAttribute("mainIssue", mainIssue);
            request.getRequestDispatcher("calAward/sportIssueDetail.jsp").forward(request, response);
            return;
        }

        if (action.equals(SPORT_CALCULATE_AWARD)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String lotteryName = Utils.formatStr(request.getParameter("lotteryName"));
            String issue = Utils.formatStr(request.getParameter("issue"));

            StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
            interfaceUrl.append("?lotteryCode=").append(lotteryCode);
            interfaceUrl.append("&issue=").append(issue);

            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
            if (Utils.isEmpty(mainIssue.getBonusNumber()) || Utils.isEmpty(mainIssue.getBonusClass()) || "-".equals(mainIssue.getBonusNumber()) || "-".equals(mainIssue.getBonusClass())) {
                logger.error("启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期失败,期次开奖号码或奖级设置不对");
                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期失败,期次开奖号码或奖级设置不对", Constants.MANAGER_LOG_START_CAL_AWARD);
                response.getWriter().print("<script>alert('期次开奖号码或奖级设置不对');history.go(-1);</script>");
                return;
            }

            // 判断票的状态
            IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setTicketStatus(Constants.TICKET_STATUS_DOING);
            ticketQueryBean.setTicket(ticket);
            Map<String, Object> ticketMap = ticketService.getTicketCount(ticketQueryBean);
            if (Utils.isNotEmpty(ticketMap)) {
                int ticketNum = Utils.formatInt(ticketMap.get("ticketNum") + "", 0);
                if (ticketNum > 0) {
                    response.getWriter().print("<script>alert('有未收到回执的票!');history.go(-1);</script>");
                }
            }

            HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
            logger.info("发送到URL:" + interfaceUrl);
            String reStr = "";
            try {
                reStr = httpClientUtils.httpClientGet();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            logger.error("返回信息:" + reStr);

            if (Utils.isNotEmpty(reStr) && reStr.equals("success")) {
                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期成功", Constants.MANAGER_LOG_START_CAL_AWARD);
                // 开奖信息通知
                String bonusInfoSendUrl = ConfigUtils.getValue("BONUS_INFO_SEND_URL");
                if (!Utils.isNotEmpty(bonusInfoSendUrl)) {
                    return;
                }
                for (String sendUrl : bonusInfoSendUrl.split(",")) {
                    final String url = sendUrl;
                    final String lottery = lotteryCode;
                    final String issueName = issue;
                    final String bonusNumber = mainIssue.getBonusNumber();
                    final String bonusTime = Utils.formatDate2Str(mainIssue.getBonusTime(), "yyyy-MM-dd HH:mm:ss");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sendBonusInfo(url, lottery, issueName, bonusNumber, bonusTime);
                        }
                    }).start();
                }
            } else {
                setManagesLog(request, action, "启动" + lotteryName + "(" + lotteryCode + ")第" + issue + "期失败", Constants.MANAGER_LOG_START_CAL_AWARD);
            }
            response.sendRedirect("/manages/calculateAwardServlet?action=sportIssueList");
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
                subIssueForJingCaiZuQiu.setOperatorsAward(Utils.formatInt(operatorsAward, null));
                request.setAttribute("operatorsAward", operatorsAward);
            }
            subIssueForJingCaiZuQiu.setPollCode("02");
            subIssueForJingCaiZuQiu.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_NON_DEFAULT);
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuResultListByParaDesc(subIssueForJingCaiZuQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("calAward/footballMatchList.jsp").forward(request, response);
        }

        if (action.equals(FOOTBALL_DETAIL)) {
            String id = Utils.formatStr(request.getParameter("id"));
            
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            if (Utils.isNotEmpty(operatorsAward)) {
            	request.setAttribute("operatorsAward", operatorsAward);
            }
            if (Utils.isNotEmpty(startTime)) {
            	request.setAttribute("startTime", startTime);
            }
            if (Utils.isNotEmpty(endTime)) {
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
                request.setAttribute("spfFloatAmount", subIssueForJingCaiZuQiu.getSpfWinOrNegaSp());
                request.setAttribute("zjqsFloatAmount", subIssueForJingCaiZuQiu.getTotalGoalSp());
                request.setAttribute("bqcspfFloatAmount", subIssueForJingCaiZuQiu.getHalfCourtSp());
                // 比分
                // 赛果
                request.setAttribute("bfResult", ElTagUtils.bfScore(mainTeamScore, guestTeamScore));

                // 总进球数
                // 赛果
                request.setAttribute("zjqsResult", ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
                // 半全场胜平负
                // 赛果
                request.setAttribute(
                        "bqcspfResult",
                        ElTagUtils.spfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore(), subIssueForJingCaiZuQiu.getGuestTeamHalfScore(), "0")
                                + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
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
            request.getRequestDispatcher("calAward/footballMatchDetail.jsp").forward(request, response);
            return;
        }

        if (action.equals(FOOTBALL_CALCULATE_AWARD)) {
            String lotteryCode = "200";
            String issue = Utils.formatStr(request.getParameter("issue"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            String week = Utils.formatStr(request.getParameter("week"));
            
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            if (Utils.isEmpty(operatorsAward)) {
            	operatorsAward = "";
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                request.setAttribute("operatorsAward", operatorsAward);
            }
            if (Utils.isEmpty(startTime)) {
            	startTime = "";
            }
            if (Utils.isEmpty(endTime)) {
            	endTime = "";
            }

            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuByIssueSn(issue, sn);
            if (!subIssueForJingCaiZuQiu.getEndOperator().equals(Constants.SUB_ISSUE_END_OPERATOR_CANCEL)
                    && (null == subIssueForJingCaiZuQiu.getMainTeamScore() || null == subIssueForJingCaiZuQiu.getGuestTeamScore())) {
                response.getWriter().print("<script>alert('录入的赛果格式不对');history.go(-1);</script>");
                return;
            }

            // 判断票的状态
            IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            ticket.setLotteryCode(lotteryCode);
            ticket.setEndGameId(issue + sn);
            ticket.setTicketStatus(Constants.TICKET_STATUS_DOING);
            ticketQueryBean.setTicket(ticket);
            Map<String, Object> ticketMap = ticketService.getTicketCount(ticketQueryBean);
            if (Utils.isNotEmpty(ticketMap)) {
                int ticketNum = Utils.formatInt(ticketMap.get("ticketNum") + "", 0);
                if (ticketNum > 0) {
                    response.getWriter().print("<script>alert('有未收到回执的票!');history.go(-1);</script>");
                }
            }

            StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
            interfaceUrl.append("?lotteryCode=").append(lotteryCode);
            interfaceUrl.append("&issue=").append(issue);
            interfaceUrl.append("&sn=").append(sn);

            HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
            logger.info("发送到URL:" + interfaceUrl);
            String reStr = "";
            try {
                reStr = httpClientUtils.httpClientGet();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            logger.error("返回信息:" + reStr);

            if (Utils.isNotEmpty(reStr) && reStr.equals("success")) {
                setManagesLog(request, action, "启动竞彩足球(200)" + week + sn + "(" + issue + ")成功", Constants.MANAGER_LOG_START_CAL_AWARD);
            } else {
                setManagesLog(request, action, "启动竞彩足球((200))" + week + sn + "(" + issue + ")失败", Constants.MANAGER_LOG_START_CAL_AWARD);
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
            //response.sendRedirect("/manages/calculateAwardServlet?action=footballMatchList&operatorsAward="+operatorsAward+"&startTime="+startTime+"&endTime="+endTime+"&page="+page+"&pageSize="+pageSize+"&matchNo="+matchNo+"&mainTeam="+mainTeam+"&guestTeam="+guestTeam+"&matchName="+matchName);
            request.getRequestDispatcher("/manages/calculateAwardServlet?action=footballMatchList").forward(request, response);
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
            request.getRequestDispatcher("calAward/beiDanMatchList.jsp").forward(request, response);
            return;
        }
        if (BEIDAN_DETAIL.equals(action)) {
            String id = Utils.formatStr(request.getParameter("id"));
            ISubIssueForBeiDanService subIssueForBeiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
            try {
                SubIssueForBeiDan subIssueForBeiDan = subIssueForBeiDanService.getSubIssueForBeiDan(Utils.formatLong(id));

                Integer mainTeamScore = subIssueForBeiDan.getMainTeamScore();
                Integer guestTeamScore = subIssueForBeiDan.getGuestTeamScore();
                // 胜平负
                // 赛果
                request.setAttribute("spfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForBeiDan.getLetBall()));
                // 浮动奖金
                request.setAttribute("spfFloatAmount", subIssueForBeiDan.getWinOrNegaSp());

                // 上下单双
                // 赛果
                request.setAttribute("sxdsResult", ElTagUtils.sxdsScore(mainTeamScore, guestTeamScore));
                // 浮动奖金
                request.setAttribute("sxdsFloatAmount", subIssueForBeiDan.getShangXiaPanSp());

                // 比分
                // 赛果
                request.setAttribute("bfResult", ElTagUtils.bfScoreForBeiDan(mainTeamScore, guestTeamScore));
                request.setAttribute("bfFloatAmount", subIssueForBeiDan.getScoreSp());

                // 总进球数
                // 赛果
                request.setAttribute("zjqsResult", ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
                // 浮动奖金
                request.setAttribute("zjqsFloatAmount", subIssueForBeiDan.getTotalGoalSp());

                // 半全场胜平负
                // 赛果
                request.setAttribute("bqcspfResult",
                        ElTagUtils.spfScore(subIssueForBeiDan.getMainTeamHalfScore(), subIssueForBeiDan.getGuestTeamHalfScore(), "0") + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
                // 浮动奖金
                request.setAttribute("bqcspfFloatAmount", subIssueForBeiDan.getHalfCourtSp());

                request.setAttribute("beiDan", subIssueForBeiDan);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("calAward/beiDanMatchDetail.jsp").forward(request, response);
            return;
        }

        if (BEIDAN_CALCULATE_AWARD.equals(action)) {
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

            if (!beiDan.isInputAward()) {
                response.getWriter().print("<script>alert('录入的赛果格式不对');history.go(-1);</script>");
                return;
            }

            StringBuffer interfaceUrl = new StringBuffer(ConfigUtils.getValue("CALCULATE_AWARD_REQUEST"));
            interfaceUrl.append("?lotteryCode=").append(lotteryCode);
            interfaceUrl.append("&issue=").append(issue);
            interfaceUrl.append("&sn=").append(sn);

            HttpClientUtils httpClientUtils = new HttpClientUtils(interfaceUrl.toString());
            logger.info("发送到URL:" + interfaceUrl);
            String reStr = "";
            try {
                reStr = httpClientUtils.httpClientGet();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            logger.error("返回信息:" + reStr);

            if (Utils.isNotEmpty(reStr) && reStr.equals("success")) {
                setManagesLog(request, action, "启动北单(400)" + sn + "(" + issue + ")成功", Constants.MANAGER_LOG_START_CAL_AWARD);
            } else {
                setManagesLog(request, action, "启动北单(400)" + sn + "(" + issue + ")失败", Constants.MANAGER_LOG_START_CAL_AWARD);
            }
            response.sendRedirect("/manages/calculateAwardServlet?action=beiDanMatchList");
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

    private void sendBonusInfo(String url, String lotteryCode, String issue, String bonusNumber, String bonusTime) {
        HttpClientUtils httpClientUtils = new HttpClientUtils(url);
        Map<String, String> map = new HashMap<String, String>();
        map.put("operator", "save");
        map.put("lotteryCode", lotteryCode);
        map.put("issue", issue);
        map.put("bonusNumber", bonusNumber);
        map.put("bonusTime", bonusTime);
        httpClientUtils.setPara(map);
        httpClientUtils.httpClientRequest();
    }
}
