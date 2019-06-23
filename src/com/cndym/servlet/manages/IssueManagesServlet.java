package com.cndym.servlet.manages;

import com.cndym.bean.tms.*;
import com.cndym.bean.user.Member;
import com.cndym.cache.XMemcachedClientWrapper;
import com.cndym.constant.Constants;
import com.cndym.exception.CndymException;
import com.cndym.exception.ErrCode;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.lottery.lotteryInfo.bean.LotteryClass;
import com.cndym.service.*;
import com.cndym.service.subIssue.BaseSubIssueOperator;
import com.cndym.servlet.ElTagUtils;
import com.cndym.servlet.manages.bean.BonusClass;
import com.cndym.utils.ConfigUtils;
import com.cndym.utils.DateUtils;
import com.cndym.utils.HttpClientUtils;
import com.cndym.utils.JsonBinder;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.bonusClass.BonusClassManager;
import com.cndym.utils.bonusClass.Level;
import com.cndym.utils.hibernate.PageBean;
import com.google.gson.Gson;

import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-5-4 下午03:44:06
 */

public class IssueManagesServlet extends BaseManagesServlet {

    //历史，未来期次总数查询
    private final String LOTTERY_TOTAL_ISSUE_QUERY = "lotteryTotalIssueQuery";

    //期次详细
    private final String ISSUE_DETAIL_QUERY = "issueDetailQuery";

    //足球期次查询
    private final String ZQ_ISSUE_QUERY = "zqIssueQuery";

    //添加期次
    private final String SAVE_ISSUE = "saveIssue";

    //修改期次
    private final String UPDATE_ISSUE = "updateIssue";

    //彩种控制
    private final String LOTTERY_CONTROL = "lotteryControl";

    //彩种出票控制
    private final String LOTTERY_SEND_CONTROL = "lotterySendControl";

    //录入公告
    private final String UPDATE_ISSUE_MSG = "updateIssueMsg";

    //根据彩种和期次获得期次信息
    private final String GET_ISSUE_BY_PARAM = "getIssueMsg";

    //竞彩篮球赛事查询
    private final String BASKETBALL_MATCH_LIST = "basketballMatchList";

    //竞彩篮球赛事详情
    private final String BASKETBALL_DETAIL = "basketballDetail";
    
    //竞彩篮球赛事详情
    private final String BASKETBALL_DETAIL_PS = "basketballDetailPs";

    //竞彩足球赛事查询
    private final String FOOTBALL_MATCH_LIST = "footballMatchList";

    //竞彩足球赛事详情
    private final String FOOTBALL_DETAIL = "footballDetail";
    
    //竞彩足球赛事详情
    private final String FOOTBALL_DETAIL_PS = "footballDetailPs";
    
    //**赛事详情中的修改
    private final String ISSUE_MATCH_EDIT = "issueMatchEdit";

    private final String ISSUE_MATCH_EDIT_DO = "issueMatchEditDo";

    //**赛事各玩法禁售
    private final String MATCH_EDIT = "matchEdit";

    private final String MATCH_EDIT_DO = "matchEditDo";

    //竞彩足球赛事查询
    private final String BEIDAN_MATCH_LIST = "beiDanMatchList";

    //竞彩足球赛事详情
    private final String BEIDAN_DETAIL = "beiDanDetail";

    //足彩期次录入页面
    private final String FOOTBALL_EDIT_ISSUE_LIST = "footballIssueList";

    //进入录入足彩期次
    private final String TO_FOOTBALL_EDIT_ISSUE = "toFootballIssueEdit";

    //足彩期次录入
    private final String FOOTBALL_EDIT_ISSUE = "footballIssueEdit";

    //彩期信息浏览
    private final String ISSUE_DETAIL_INFO = "issueDetailInfo";
    
    private final String POST_ISSUE_QUERY = "postIssueQuery";

    private final static String CANCEL_OR_RECOVERY_MATCH = "cancelOrRecoveryMatch";//取消或者恢复场次
    
    private final static String MEMBER_QUERY_LOTTERY_CODE = "memberQueryByLotteryCode";//根据彩种查询代理商


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    private Logger logger = Logger.getLogger(getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        IMainIssueService issueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        ISubGameService subGameService = (ISubGameService) SpringUtils.getBean("subGameServiceImpl");
        
        IPostIssueService postService = (IPostIssueService) SpringUtils.getBean("postIssueServiceImpl");
        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        
        if (UPDATE_ISSUE_MSG.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            if ("113".equals(lotteryCode) || "108".equals(lotteryCode) || "109".equals(lotteryCode) || "110".equals(lotteryCode) || "111".equals(lotteryCode) || "300".equals(lotteryCode) || "301".equals(lotteryCode) || "302".equals(lotteryCode) || "303".equals(lotteryCode)) {
                doUpdateIssueMsgTc(request, response);
                return;
            }
            String mainIssueId = Utils.formatStr(request.getParameter("mainIssueId"));
            String issue = Utils.formatStr(request.getParameter("issue"));


            String saleTotal = Utils.formatStr(request.getParameter("saleTotal"));
            String globalSaleTotal = Utils.formatStr(request.getParameter("globalSaleTotal"));
            String prizePool = Utils.formatStr(request.getParameter("prizePool"));

            String firstAmount = Utils.formatStr(request.getParameter("firstAmount"));
            String secondAmount = Utils.formatStr(request.getParameter("secondAmount"));
            String thirdAmount = Utils.formatStr(request.getParameter("thirdAmount"));
            String fourthAmount = Utils.formatStr(request.getParameter("fourthAmount"));
            String fifthAmount = Utils.formatStr(request.getParameter("fifthAmount"));
            String sixthAmount = Utils.formatStr(request.getParameter("sixthAmount"));
            String seventhAmount = Utils.formatStr(request.getParameter("seventhAmount"));

            String firstCount = Utils.formatStr(request.getParameter("firstCount"));
            String secondCount = Utils.formatStr(request.getParameter("secondCount"));
            String thirdCount = Utils.formatStr(request.getParameter("thirdCount"));
            String fourthCount = Utils.formatStr(request.getParameter("fourthCount"));
            String fifthCount = Utils.formatStr(request.getParameter("fifthCount"));
            String sixthCount = Utils.formatStr(request.getParameter("sixthCount"));
            String seventhCount = Utils.formatStr(request.getParameter("seventhCount"));

            String cityFirstCount = Utils.formatStr(request.getParameter("cityFirstCount"));
            String citySecondCount = Utils.formatStr(request.getParameter("citySecondCount"));
            String cityThirdCount = Utils.formatStr(request.getParameter("cityThirdCount"));
            String cityFourthCount = Utils.formatStr(request.getParameter("cityFourthCount"));
            String cityFifthCount = Utils.formatStr(request.getParameter("cityFifthCount"));
            String citySixthCount = Utils.formatStr(request.getParameter("citySixthCount"));
            String citySeventhCount = Utils.formatStr(request.getParameter("citySeventhCount"));

            MainIssue mainIssue = new MainIssue();
            mainIssue.setId(new Long(mainIssueId));
            if (Utils.isNotEmpty(saleTotal)) {
                mainIssue.setSaleTotal(saleTotal);
            }
            if (Utils.isNotEmpty(prizePool)) {
                mainIssue.setPrizePool(prizePool);
            }
            if (Utils.isNotEmpty(globalSaleTotal)) {
                mainIssue.setGlobalSaleTotal(globalSaleTotal);
            }

            StringBuffer msg = new StringBuffer("[");
            if (Utils.isNotEmpty(firstAmount)) {
                msg.append("{'c1':");
                msg.append(firstAmount);
                msg.append(",");
                if (Utils.isNotEmpty(firstCount)) {
                    msg.append("'m1':");
                    msg.append(firstCount);
                    msg.append(",");
                }
                if (Utils.isNotEmpty(cityFirstCount)) {
                    msg.append("'a1':");
                    msg.append(cityFirstCount);
                    msg.append(",");
                }
                if (lotteryCode.equals("002")) {
                    msg.append("'n1':1,'t1':'直选3'},");
                } else {
                    msg.append("'n1':1,'t1':'一等奖'},");
                }
            }


            if (Utils.isNotEmpty(secondAmount)) {
                msg.append("{'c1':");
                msg.append(secondAmount);
                msg.append(",");
                if (Utils.isNotEmpty(secondCount)) {
                    msg.append("'m1':");
                    msg.append(secondCount);
                    msg.append(",");
                }

                if (Utils.isNotEmpty(citySecondCount)) {
                    msg.append("'a1':");
                    msg.append(citySecondCount);
                    msg.append(",");
                }
                if (lotteryCode.equals("002")) {
                    msg.append("'n1':2,'t1':'组选三'},");
                } else {
                    msg.append("'n1':2,'t1':'二等奖'},");
                }
            }


            if (Utils.isNotEmpty(thirdAmount)) {
                msg.append("{'c1':");
                msg.append(thirdAmount);
                msg.append(",");
                if (Utils.isNotEmpty(thirdCount)) {
                    msg.append("'m1':");
                    msg.append(thirdCount);
                    msg.append(",");
                }
                if (Utils.isNotEmpty(cityThirdCount)) {
                    msg.append("'a1':");
                    msg.append(cityThirdCount);
                    msg.append(",");
                }
                if (lotteryCode.equals("002")) {
                    msg.append("'n1':3,'t1':'组选六'},");
                } else {
                    msg.append("'n1':3,'t1':'三等奖'},");
                }
            }


            if (Utils.isNotEmpty(fourthAmount)) {
                msg.append("{'c1':");
                msg.append(fourthAmount);
                msg.append(",");
                if (Utils.isNotEmpty(fourthCount)) {
                    msg.append("'m1':");
                    msg.append(fourthCount);
                    msg.append(",");
                }
                if (Utils.isNotEmpty(cityFourthCount)) {
                    msg.append("'a1':");
                    msg.append(cityFourthCount);
                    msg.append(",");
                }
                msg.append("'n1':4,'t1':'四等奖'},");
            }


            if (Utils.isNotEmpty(fifthAmount)) {
                msg.append("{'c1':");
                msg.append(fifthAmount);
                msg.append(",");
                if (Utils.isNotEmpty(fifthCount)) {
                    msg.append("'m1':");
                    msg.append(fifthCount);
                    msg.append(",");
                }

                if (Utils.isNotEmpty(cityFifthCount)) {
                    msg.append("'a1':");
                    msg.append(cityFifthCount);
                    msg.append(",");
                }
                msg.append("'n1':5,'t1':'五等奖'},");
            }


            if (Utils.isNotEmpty(sixthAmount)) {
                msg.append("{'c1':");
                msg.append(sixthAmount);
                msg.append(",");
                if (Utils.isNotEmpty(sixthCount)) {
                    msg.append("'m1':");
                    msg.append(sixthCount);
                    msg.append(",");
                }

                if (Utils.isNotEmpty(citySixthCount)) {
                    msg.append("'a1':");
                    msg.append(citySixthCount);
                    msg.append(",");
                }
                msg.append("'n1':6,'t1':'六等奖'},");
            }


            if (Utils.isNotEmpty(seventhAmount)) {
                msg.append("{'c1':");
                msg.append(seventhAmount);
                msg.append(",");
                if (Utils.isNotEmpty(seventhCount)) {
                    msg.append("'m1':");
                    msg.append(seventhCount);
                    msg.append(",");
                }

                if (Utils.isNotEmpty(citySeventhCount)) {
                    msg.append("'a1':");
                    msg.append(citySeventhCount);
                    msg.append(",");
                }
                msg.append("'n1':7,'t1':'七等奖'},");
            }


            String str = msg.toString();
            mainIssue.setBonusClass(str.substring(0, str.length() - 1) + "]");
            issueService.updateIssue(mainIssue);
            this.setManagesLog(request, UPDATE_ISSUE_MSG, "修改了<span style=\"color:#f00\">" + ElTagUtils.getLotteryChinaName(lotteryCode) + "</span>第<span style=\"color:#f00\">" + issue + "</span>期开奖公告", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);

            request.setAttribute("issueMsg", "issueMsg");
            response.sendRedirect("/manages/issueManagesServlet?action=zqIssueQuery&status=3&lotteryCode=" + lotteryCode);
            return;


        } else if (GET_ISSUE_BY_PARAM.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            if ("113".equals(lotteryCode) || "108".equals(lotteryCode) || "109".equals(lotteryCode) || "110".equals(lotteryCode) || "111".equals(lotteryCode) || "300".equals(lotteryCode) || "301".equals(lotteryCode) || "302".equals(lotteryCode)) {
            	updateIssueMsgTc(request, response);
                return;
            }
            String issue = Utils.formatStr(request.getParameter("issue"));
            MainIssue mainIssue = issueService.getMainIssueByLotteryIssue(lotteryCode, issue);
            List<BonusClass> bonusClasses = new ArrayList<BonusClass>();
            if (Utils.isNotEmpty(mainIssue)) {
                String msg = mainIssue.getBonusClass();
                if (Utils.isNotEmpty(msg)) {
                    msg = msg.replaceAll("'", "\"");
                    try {
                        List<Map> maps = JsonBinder.buildNonDefaultBinder().getMapper().readValue(msg, new TypeReference<List<Map>>() {
                        });
                        for (Map sub : maps) {
                            Object n1 = sub.get("n");
                            Integer n1Int = Utils.isNotEmpty(n1) ? Integer.parseInt(n1.toString()) : 0;
                            Object m1 = sub.get("m");
                            Long m1Int = Utils.isNotEmpty(m1) ? Long.parseLong(m1.toString()) : 0;
                            Object c1 = sub.get("c");
                            String c1Str = Utils.isNotEmpty(c1) ? c1.toString() : "0";
                            Object a1 = sub.get("a");
                            Long a1Int = Utils.isNotEmpty(a1) ? Long.parseLong(a1.toString()) : 0;
                            bonusClasses.add(new BonusClass(n1Int, m1Int, c1Str, a1Int));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            request.setAttribute("mainIssue", mainIssue);
            if (Utils.isNotEmpty(bonusClasses)) {
                request.setAttribute("award", bonusClasses);
            }
            request.getRequestDispatcher("issue/updateIssueMsg.jsp").forward(request, response);
            return;
        } else if (SAVE_ISSUE.equals(action)) {
            String issue = Utils.formatStr(request.getParameter("issue"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            Date startTime = Utils.formatDate(request.getParameter("startTime"));
            Date endTime = Utils.formatDate(request.getParameter("endTime"));
            Date bonsuTime = Utils.formatDate(request.getParameter("bonusTime"));
            MainIssue mainIssue = new MainIssue();
            mainIssue.setLotteryCode(lotteryCode);
            mainIssue.setName(issue);
            mainIssue.setStartTime(startTime);
            mainIssue.setEndTime(endTime);
            mainIssue.setBonusTime(bonsuTime);
            int result =0;
            try {
				result = issueService.doSaveIssue(mainIssue);
			} catch (Exception e) {
				logger.error("",e);
			}
            String description = "";
            if (result == 1) {
                request.setAttribute("msg", "添加彩期成功");
                description = "添加lotteryCode=" + lotteryCode + ",issue=" + issue + "成功";
            } else {
                request.setAttribute("msg", "添加彩期失败");
                description = "添加lotteryCode=" + lotteryCode + ",issue=" + issue + "失败";
            }
            setManagesLog(request, action, description, Constants.MANAGER_LOG_EDIT_SIMPLE_ISSUE);
            request.getRequestDispatcher("lottery/saveIssue.jsp").forward(request, response);
            return;
        } else if (UPDATE_ISSUE.equals(action)) {
            Long issueId = Utils.formatLong(request.getParameter("issueId"));
            String issueName = Utils.formatStr(request.getParameter("issueName"));
            String issueStatus = Utils.formatStr(request.getParameter("issueStatus"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String sendStatus = Utils.formatStr(request.getParameter("sendStatus"));
            Date startTime = Utils.formatDate(request.getParameter("startTime"));
            Date endTime = Utils.formatDate(request.getParameter("endTime"));
            Date simplexTime = Utils.formatDate(request.getParameter("simplexTime"));
            Date duplexTime = Utils.formatDate(request.getParameter("duplexTime"));
            String bonusNumber = Utils.formatStr(request.getParameter("bonusNumber"));
            String bonusStatus = Utils.formatStr(request.getParameter("bonusStatus"));
            String prizePool = Utils.formatStr(request.getParameter("prizePool"));
            Date kjTime = Utils.formatDate(request.getParameter("kjTime"));
            MainIssue issue = new MainIssue();
            if (Utils.isNotEmpty(issueId)) {
                issue.setId(issueId);
            }
            if (Utils.isNotEmpty(issueName)) {
                issue.setName(issueName.trim());
            }
            if (Utils.isNotEmpty(issueStatus)) {
                issue.setStatus(new Integer(issueStatus.trim()));
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                issue.setLotteryCode(lotteryCode.trim());
            }
            if (Utils.isNotEmpty(sendStatus)) {
                issue.setSendStatus(new Integer(sendStatus.trim()));
            }
            if (Utils.isNotEmpty(startTime)) {
                issue.setStartTime(startTime);
            }
            if (Utils.isNotEmpty(endTime)) {
                issue.setEndTime(endTime);
            }
            if (Utils.isNotEmpty(simplexTime)) {
                issue.setSimplexTime(simplexTime);
            }
            if (Utils.isNotEmpty(duplexTime)) {
                issue.setDuplexTime(duplexTime);
            }
            if (Utils.isNotEmpty(bonusNumber)) {
                issue.setBonusNumber(bonusNumber.trim());
            }
            if (Utils.isNotEmpty(bonusStatus)) {
                issue.setBonusStatus(new Integer(bonusStatus.trim()));
            }
            if (Utils.isNotEmpty(prizePool)) {
                issue.setPrizePool(prizePool);
            }
            if (Utils.isNotEmpty(kjTime)) {
                issue.setBonusTime(kjTime);
            }
            int count = issueService.updateIssue(issue);
            String description = "";
            if (count > 0) {
                description = "修改期次成功 issueId=" + issueId;
                request.setAttribute("msg", "修改期次成功");
            } else {
                description = "修改期次失败 issueId=" + issueId;
                request.setAttribute("msg", "修改失败");
            }
            setManagesLog(request, action, description, Constants.MANAGER_LOG_DEFAULT);
            request.getRequestDispatcher("lottery/updateIssue.jsp").forward(request, response);
            return;
        } else if (LOTTERY_TOTAL_ISSUE_QUERY.equals(action)) {
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String issueNameStart = Utils.formatStr(request.getParameter("issueNameStart"));
            String issueNameEnd = Utils.formatStr(request.getParameter("issueNameEnd"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String status = Utils.formatStr(request.getParameter("status"));
            List lotteryList = issueService.getLotteryTotalIssue(lotteryCode, Utils.formatInt(status, null), issueNameStart, issueNameEnd, startTime, endTime);
            request.setAttribute("lotteryList", lotteryList);
            request.getRequestDispatcher("lottery/lotteryIssueTotalList.jsp").forward(request, response);
            return;
        } else if (ISSUE_DETAIL_QUERY.equals(action)) {
            String issueId = Utils.formatStr(request.getParameter("issueId"));
            MainIssue issue = new MainIssue();
            if (!Utils.isNotEmpty(issueId)) {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            MainIssue mainIssue = issueService.findById(MainIssue.class, issueId);
            request.setAttribute("issue", mainIssue);
            request.getRequestDispatcher("lottery/issueDetail.jsp").forward(request, response);
            return;
        } else if (LOTTERY_CONTROL.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            StringBuffer key = new StringBuffer("lottery.")
                    .append(lotteryCode)
                    .append(".control");
            XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
            String value = memcachedClientWrapper.get(key.toString());
            com.cndym.lottery.lotteryInfo.bean.LotteryClass lotteryClass = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryClass(lotteryCode);
            if (Utils.isNotEmpty(value)) {
                memcachedClientWrapper.delete(key.toString());
                setManagesLog(request, action, lotteryClass.getName() + "继续销售", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
            } else {
                memcachedClientWrapper.set(key.toString(), 0, "0");
                setManagesLog(request, action, lotteryClass.getName() + "暂停销售", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
            }
            response.sendRedirect("/manages/issueManagesServlet?action=lotteryTotalIssueQuery");
            return;
        } else if (LOTTERY_SEND_CONTROL.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String key = Constants.MEMCACHED_SEND_ORDER + lotteryCode;
            XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
            String value = memcachedClientWrapper.get(key);
            com.cndym.lottery.lotteryInfo.bean.LotteryClass lotteryClass = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryClass(lotteryCode);
            if (Utils.isNotEmpty(value)) {
                memcachedClientWrapper.delete(key);
                setManagesLog(request, action, lotteryClass.getName() + "继续出票", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
            } else {
                memcachedClientWrapper.set(key, 0, "0");
                setManagesLog(request, action, lotteryClass.getName() + "暂停出票", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
            }
            response.sendRedirect("/manages/issueManagesServlet?action=lotteryTotalIssueQuery");
            return;
        } else if (BASKETBALL_MATCH_LIST.equals(action)) {
            String matchNo = Utils.formatStr(request.getParameter("matchNo"));
            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String endOperator = Utils.formatStr(request.getParameter("endOperator"));
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
            if (Utils.isNotEmpty(endOperator)) {
                subIssueForJingCaiLanQiu.setEndOperator(Utils.formatInt(endOperator, null));
                request.setAttribute("endOperator", endOperator);
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
            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuListByParaDesc(subIssueForJingCaiLanQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("lottery/basketballMatchList.jsp").forward(request, response);
            return;

        } else if (action.equals(BASKETBALL_DETAIL)) {
            String id = Utils.formatStr(request.getParameter("id"));
            ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
            try {
                //过关场次
                SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiu(Utils.formatLong(id));
                //String sn, String date, String lotteryCode, String playCode, String pollCode
                //单关(让分胜负)
                SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForRFSF = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(), subIssueForJingCaiLanQiu.getIssue(), "02", "01");
                //单关(大小分)
                SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForDXF = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(), subIssueForJingCaiLanQiu.getIssue(), "04", "01");
                Integer mainTeamScore = subIssueForJingCaiLanQiu.getMainTeamScore();
                Integer guestTeamScore = subIssueForJingCaiLanQiu.getGuestTeamScore();

                request.setAttribute("sfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "+0"));
                request.setAttribute("sfFloatAmount", subIssueForJingCaiLanQiu.getWinOrNegaSp());

                if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForRFSF)) {
                    request.setAttribute("letBall", subIssueForJingCaiLanQiuForRFSF.getLetBall());
                    request.setAttribute("rfsfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiLanQiuForRFSF.getLetBall()));
                    request.setAttribute("rfsfFloatAmount", subIssueForJingCaiLanQiuForRFSF.getLetWinOrNegaSp());
                }

                if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForDXF)) {
                    request.setAttribute("preCast", subIssueForJingCaiLanQiuForDXF.getPreCast());
                    request.setAttribute("dxfResult", ElTagUtils.dxfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiLanQiuForDXF.getPreCast()));
                    request.setAttribute("dxfFloatAmount", subIssueForJingCaiLanQiuForDXF.getBigOrLittleSp());
                }
                request.setAttribute("sfcResult", ElTagUtils.sfcScore(mainTeamScore, guestTeamScore));
                request.setAttribute("basketball", subIssueForJingCaiLanQiu);
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.getRequestDispatcher("lottery/basketballMatchDetail.jsp").forward(request, response);
            return;
        } else if (action.equals(BASKETBALL_DETAIL_PS)) {
        	String id = Utils.formatStr(request.getParameter("id"));
        	ISubIssueForJingCaiLanQiuService subIssueForJingCaiLanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
        	try {
        		Map<String, Object> map=new HashMap<String, Object>();
        		//过关场次
        		SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiu(Utils.formatLong(id));
        		//String sn, String date, String lotteryCode, String playCode, String pollCode
        		//单关(让分胜负)
        		SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForRFSF = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(), subIssueForJingCaiLanQiu.getIssue(), "02", "01");
        		//单关(大小分)
        		SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiuForDXF = subIssueForJingCaiLanQiuService.getSubIssueForJingCaiLanQiuBySnDate(subIssueForJingCaiLanQiu.getSn(), subIssueForJingCaiLanQiu.getIssue(), "04", "01");
        		Integer mainTeamScore = subIssueForJingCaiLanQiu.getMainTeamScore();
        		Integer guestTeamScore = subIssueForJingCaiLanQiu.getGuestTeamScore();
        		
        		map.put("sfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "+0"));
        		map.put("sfFloatAmount", subIssueForJingCaiLanQiu.getWinOrNegaSp());
        		
        		if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForRFSF)) {
        			map.put("letBall", subIssueForJingCaiLanQiuForRFSF.getLetBall());
        			map.put("rfsfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiLanQiuForRFSF.getLetBall()));
        			map.put("rfsfFloatAmount", subIssueForJingCaiLanQiuForRFSF.getLetWinOrNegaSp());
        		}
        		
        		if (Utils.isNotEmpty(subIssueForJingCaiLanQiuForDXF)) {
        			map.put("preCast", subIssueForJingCaiLanQiuForDXF.getPreCast());
        			map.put("dxfResult", ElTagUtils.dxfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiLanQiuForDXF.getPreCast()));
        			map.put("dxfFloatAmount", subIssueForJingCaiLanQiuForDXF.getBigOrLittleSp());
        		}
        		map.put("sfcResult", ElTagUtils.sfcScore(mainTeamScore, guestTeamScore));
        		map.put("basketball", subIssueForJingCaiLanQiu);
        		Gson gson=new Gson();
                
        		response.getWriter().write(gson.toJson(map));
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	return;
        } else if (action.equals(FOOTBALL_MATCH_LIST)) {
            String matchNo = Utils.formatStr(request.getParameter("matchNo"));
            String mainTeam = Utils.formatStr(request.getParameter("mainTeam"));
            String guestTeam = Utils.formatStr(request.getParameter("guestTeam"));
            String matchName = Utils.formatStr(request.getParameter("matchName"));
            String endOperator = Utils.formatStr(request.getParameter("endOperator"));
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
            if (Utils.isNotEmpty(endOperator)) {
                subIssueForJingCaiZuQiu.setEndOperator(Utils.formatInt(endOperator, null));
                request.setAttribute("endOperator", endOperator);
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
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            PageBean pageBean = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuListByParaDesc(subIssueForJingCaiZuQiu, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("lottery/footballMatchList.jsp").forward(request, response);
            return;
        } else if (action.equals(FOOTBALL_DETAIL)) {
            String id = Utils.formatStr(request.getParameter("id"));
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            try {
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiu(Utils.formatLong(id));

                Integer mainTeamScore = subIssueForJingCaiZuQiu.getMainTeamScore();
                Integer guestTeamScore = subIssueForJingCaiZuQiu.getGuestTeamScore();
                //胜平负
                //赛果
                request.setAttribute("spfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiZuQiu.getLetBall()));
                //浮动奖金
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuForSPF = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(), subIssueForJingCaiZuQiu.getIssue(), "01", "01");
                if (Utils.isNotEmpty(subIssueForJingCaiZuQiuForSPF)) {
                    request.setAttribute("spfFloatAmount", subIssueForJingCaiZuQiuForSPF.getWinOrNegaSp());
                }
                //比分
                //赛果
                request.setAttribute("bfResult", ElTagUtils.bfScore(mainTeamScore, guestTeamScore));

                //总进球数
                //赛果
                request.setAttribute("zjqsResult", ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
                //浮动奖金
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuForZJQS = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(), subIssueForJingCaiZuQiu.getIssue(), "02", "01");
                if (Utils.isNotEmpty(subIssueForJingCaiZuQiuForZJQS)) {
                    request.setAttribute("zjqsFloatAmount", subIssueForJingCaiZuQiuForZJQS.getTotalGoalSp());
                }
                //半全场胜平负
                //赛果
                request.setAttribute("bqcspfResult", ElTagUtils.spfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore(), subIssueForJingCaiZuQiu.getGuestTeamHalfScore(), "0") + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
                //浮动奖金
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuForBQCSPF = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(), subIssueForJingCaiZuQiu.getIssue(), "03", "01");
                if (Utils.isNotEmpty(subIssueForJingCaiZuQiuForBQCSPF)) {
                    request.setAttribute("bqcspfFloatAmount", subIssueForJingCaiZuQiuForBQCSPF.getHalfCourtSp());
                }
                request.setAttribute("football", subIssueForJingCaiZuQiu);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.getRequestDispatcher("lottery/footballMatchDetail.jsp").forward(request, response);
            return;
        } else if (action.equals(FOOTBALL_DETAIL_PS)) {
            String id = Utils.formatStr(request.getParameter("id"));
            Map<String, Object> map=new HashMap<String, Object>();
            ISubIssueForJingCaiZuQiuService subIssueForJingCaiZuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
            try {
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiu(Utils.formatLong(id));

                Integer mainTeamScore = subIssueForJingCaiZuQiu.getMainTeamScore();
                Integer guestTeamScore = subIssueForJingCaiZuQiu.getGuestTeamScore();
                //胜平负
                //赛果
                map.put("spfResult", ElTagUtils.spfScore(mainTeamScore, guestTeamScore, subIssueForJingCaiZuQiu.getLetBall()));
                //浮动奖金
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuForSPF = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(), subIssueForJingCaiZuQiu.getIssue(), "01", "01");
                if (Utils.isNotEmpty(subIssueForJingCaiZuQiuForSPF)) {
                	map.put("spfFloatAmount", subIssueForJingCaiZuQiuForSPF.getWinOrNegaSp());
                }
                //比分
                //赛果
                map.put("bfResult", ElTagUtils.bfScore(mainTeamScore, guestTeamScore));

                //总进球数
                //赛果
                map.put("zjqsResult", ElTagUtils.zjqsScore(mainTeamScore, guestTeamScore));
                //浮动奖金
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuForZJQS = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(), subIssueForJingCaiZuQiu.getIssue(), "02", "01");
                if (Utils.isNotEmpty(subIssueForJingCaiZuQiuForZJQS)) {
                	map.put("zjqsFloatAmount", subIssueForJingCaiZuQiuForZJQS.getTotalGoalSp());
                }
                //半全场胜平负
                //赛果
                map.put("bqcspfResult", ElTagUtils.spfScore(subIssueForJingCaiZuQiu.getMainTeamHalfScore(), subIssueForJingCaiZuQiu.getGuestTeamHalfScore(), "0") + ElTagUtils.spfScore(mainTeamScore, guestTeamScore, "0"));
                //浮动奖金
                SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiuForBQCSPF = subIssueForJingCaiZuQiuService.getSubIssueForJingCaiZuQiuBySnDate(subIssueForJingCaiZuQiu.getSn(), subIssueForJingCaiZuQiu.getIssue(), "03", "01");
                if (Utils.isNotEmpty(subIssueForJingCaiZuQiuForBQCSPF)) {
                	map.put("bqcspfFloatAmount", subIssueForJingCaiZuQiuForBQCSPF.getHalfCourtSp());
                }
                map.put("football",subIssueForJingCaiZuQiu);
                Gson gson=new Gson();
                
                response.getWriter().write(gson.toJson(map));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        } else if (action.equals(BEIDAN_MATCH_LIST)) {
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
            request.getRequestDispatcher("lottery/beiDanMatchList.jsp").forward(request, response);
            return;
        } else if (action.equals(BEIDAN_DETAIL)) {
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
            request.getRequestDispatcher("lottery/beiDanMatchDetail.jsp").forward(request, response);
            return;
        } else if (action.equals(FOOTBALL_EDIT_ISSUE_LIST)) {
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            //胜负彩(300)
            //销售中彩期
            Map<String, Object> sfcMap = new HashMap<String, Object>();
            sfcMap.put("lotteryName", "14场");
            sfcMap.put("lotteryCode", "300");
            sfcMap.put("startIssue", mainIssueService.getStartIssueNameForEditFootball("300"));
            //已录入未销售彩期
            sfcMap.put("waitIssue", mainIssueService.getWaitIssueNameForEditFootBall("300"));
            //最近待录入彩期
            sfcMap.put("notEditIssue", mainIssueService.getNotIssueNameForEditFootBall("300"));
            //预售
            sfcMap.put("preIssue", mainIssueService.getPreIssueNameForEditFootBall("300"));

            Map<String, Object> r9Map = new HashMap<String, Object>();
            r9Map.put("lotteryName", "任九场");
            r9Map.put("lotteryCode", "303");
            r9Map.put("startIssue", mainIssueService.getStartIssueNameForEditFootball("303"));
            //已录入未销售彩期
            r9Map.put("waitIssue", mainIssueService.getWaitIssueNameForEditFootBall("303"));
            //最近待录入彩期
            r9Map.put("notEditIssue", mainIssueService.getNotIssueNameForEditFootBall("303"));
            //预售
            r9Map.put("preIssue", mainIssueService.getPreIssueNameForEditFootBall("303"));

            //半全场
            Map<String, Object> bqcMap = new HashMap<String, Object>();
            bqcMap.put("lotteryName", "半全场");
            bqcMap.put("lotteryCode", "301");
            bqcMap.put("startIssue", mainIssueService.getStartIssueNameForEditFootball("301"));
            //已录入未销售彩期
            bqcMap.put("waitIssue", mainIssueService.getWaitIssueNameForEditFootBall("301"));
            //最近待录入彩期
            bqcMap.put("notEditIssue", mainIssueService.getNotIssueNameForEditFootBall("301"));
            //预售
            bqcMap.put("preIssue", mainIssueService.getPreIssueNameForEditFootBall("301"));

            //进球数
            Map<String, Object> jqsMap = new HashMap<String, Object>();
            jqsMap.put("lotteryName", "进球数");
            jqsMap.put("lotteryCode", "302");
            jqsMap.put("startIssue", mainIssueService.getStartIssueNameForEditFootball("302"));
            //已录入未销售彩期
            jqsMap.put("waitIssue", mainIssueService.getWaitIssueNameForEditFootBall("302"));
            //最近待录入彩期
            jqsMap.put("notEditIssue", mainIssueService.getNotIssueNameForEditFootBall("302"));
            //预售
            jqsMap.put("preIssue", mainIssueService.getPreIssueNameForEditFootBall("302"));

            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            dataList.add(sfcMap);
            dataList.add(r9Map);
            dataList.add(bqcMap);
            dataList.add(jqsMap);
            request.setAttribute("dataList", dataList);
            request.setAttribute("error", request.getAttribute("error"));
            request.getRequestDispatcher("lottery/footballLotteryIssueList.jsp").forward(request, response);
            return;
        } else if (action.equals(TO_FOOTBALL_EDIT_ISSUE)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            LotteryBean lotteryBean = com.cndym.lottery.lotteryInfo.LotteryList.getLotteryBean(lotteryCode);
            LotteryClass lotteryClass = lotteryBean.getLotteryClass();
            request.setAttribute("lotteryName", lotteryClass.getName());
            request.setAttribute("lotteryCode", lotteryCode);
            Integer matchNum = 14;
            if (lotteryCode.equals("301")) {
                matchNum = 6;
            } else if (lotteryCode.equals("302")) {
                matchNum = 4;
            }
            if (Utils.isNotEmpty(lotteryCode) && Utils.isNotEmpty(issue)) {
                MainIssue mainIssue = issueService.getMainIssueByLotteryIssue(lotteryCode, issue);
                List<SubGame> subGameList = subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, issue);
                request.setAttribute("mainIssue", mainIssue);
                request.setAttribute("subGameList", subGameList);
                if (Utils.isNotEmpty(mainIssue)) {
                    request.setAttribute("startTime", Utils.formatDate2Str(mainIssue.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
                    request.setAttribute("endTime", Utils.formatDate2Str(mainIssue.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
                    request.setAttribute("bonusTime", Utils.formatDate2Str(mainIssue.getBonusTime(), "yyyy-MM-dd HH:mm:ss"));
                }
            }
            request.setAttribute("matchNum", matchNum);
            request.getRequestDispatcher("lottery/footballLotteryIssueEdit.jsp").forward(request, response);
            return;
        } else if (action.equals(FOOTBALL_EDIT_ISSUE)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String lotteryName = Utils.formatStr(request.getParameter("lotteryName"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            Integer matchNum = Utils.formatInt(request.getParameter("matchNum"), 0);
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String bonusTime = Utils.formatStr(request.getParameter("bonusTime"));
            if (!Utils.isNotEmpty(lotteryCode) || !Utils.isNotEmpty(issue)
                    || !Utils.isNotEmpty(startTime) || !Utils.isNotEmpty(endTime) || !Utils.isNotEmpty(bonusTime) || matchNum == 0) {
                request.setAttribute("error", "录入期次失败!");
                request.getRequestDispatcher("/manages/issueManagesServlet?action=footballIssueList").forward(request, response);
                return;
            }
            List<String> mainTeamList = new ArrayList<String>();
            List<String> guestTeamList = new ArrayList<String>();
            List<String> leageNameList = new ArrayList<String>();
            List<String> playTimeList = new ArrayList<String>();
            String mainTeam = "";
            String guestTeam = "";
            String leageName = "";
            String playTime = "";
            for (int index = 1; index <= matchNum; index++) {
                mainTeam = Utils.formatStr(request.getParameter("mainTeam" + index));
                guestTeam = Utils.formatStr(request.getParameter("guestTeam" + index));
                leageName = Utils.formatStr(request.getParameter("leageName" + index));
                playTime = Utils.formatStr(request.getParameter("playTime" + index));

                if (!Utils.isNotEmpty(mainTeam) || !Utils.isNotEmpty(guestTeam) || !Utils.isNotEmpty(leageName)
                        || !Utils.isNotEmpty(playTime)) {
                    mainTeamList.clear();
                    guestTeamList.clear();
                    leageNameList.clear();
                    playTimeList.clear();
                    break;
                }
                mainTeamList.add(mainTeam);
                guestTeamList.add(guestTeam);
                leageNameList.add(leageName);
                playTimeList.add(playTime);
            }

            if (!Utils.isNotEmpty(mainTeamList) || !Utils.isNotEmpty(guestTeamList) || !Utils.isNotEmpty(leageNameList)
                    || !Utils.isNotEmpty(playTimeList)) {
                request.setAttribute("error", "录入期次失败!");
                request.getRequestDispatcher("/manages/issueManagesServlet?action=footballIssueList").forward(request, response);
                return;
            }

            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = new MainIssue();
            mainIssue.setLotteryCode(lotteryCode);
            mainIssue.setName(issue);
            mainIssue.setStartTime(Utils.formatDate(startTime));
            mainIssue.setEndTime(Utils.formatDate(endTime));
            mainIssue.setBonusTime(Utils.formatDate(bonusTime));

            boolean flag = mainIssueService.doUpdateIssue(mainIssue, mainTeamList, guestTeamList, leageNameList, playTimeList);
            String msg = "录入" + lotteryName + "第" + issue + "期期次";
            if (flag) {
                msg += "成功";
            } else {
                msg += "失败";
            }
            setManagesLog(request, action, msg, Constants.MANAGER_LOG_EDIT_FOOTBALL_ISSUE);
            response.sendRedirect(request.getContextPath() + "/manages/issueManagesServlet?action=footballIssueList");
            return;
        } else if (ISSUE_DETAIL_INFO.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));

            if (Utils.isEmpty(lotteryCode) || Utils.isEmpty(issue)) {
                return;
            }
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(lotteryCode, issue);
            if (null == mainIssue) {
                return;
            }
            request.setAttribute("mainIssue", mainIssue);
            int lotteryCodeNum = Integer.parseInt(lotteryCode);
            if (lotteryCodeNum >= 300 && lotteryCodeNum < 400) {
                List<SubGame> subGameList = subGameService.getSubGameListByLotteryCodeIssue(lotteryCode, issue);
                request.setAttribute("subGameList", subGameList);
            }
            request.getRequestDispatcher("/manages/issue/issueDetailInfo.jsp").forward(request, response);
        } else if (CANCEL_OR_RECOVERY_MATCH.equals(action)) {
            String type = request.getParameter("type");
            String lotteryCode = request.getParameter("lotteryCode");
            String sn = request.getParameter("sn");
            String issue = request.getParameter("issue");
            Long id = Utils.formatLong(request.getParameter("id"));
            if (Utils.isEmpty(type) || Utils.isEmpty(lotteryCode) || Utils.isEmpty(sn) || Utils.isEmpty(issue)) {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            issue = issue.replace("-", "");
            int updateCount = 0;
            Integer changeStatus = null;
            String operationInfo = "";
            String reAction = "";
            if ("cancel".equals(type)) {
                changeStatus = Constants.SUB_ISSUE_END_OPERATOR_CANCEL;
                operationInfo = "[取消对阵]";
            } else if ("recovery".equals(type)) {
                changeStatus = Constants.SUB_ISSUE_END_OPERATOR_DEFAULT;
                operationInfo = "[恢复对阵]";
            } else if ("end".equals(type)) {
                changeStatus = Constants.SUB_ISSUE_END_OPERATOR_END;
                operationInfo = "[比赛结束]";
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            if ("200".equals(lotteryCode)) {
                ISubIssueForJingCaiZuQiuService zuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
                if (Constants.SUB_ISSUE_END_OPERATOR_DEFAULT == changeStatus) {
                    SubIssueForJingCaiZuQiu subIssue = zuQiuService.getSubIssueForJingCaiZuQiu(id);
                    changeStatus = subIssue.getEndFuShiTime().getTime() > new Date().getTime() ? Constants.SUB_ISSUE_END_OPERATOR_DEFAULT : Constants.SUB_ISSUE_END_OPERATOR_END;
                }
                updateCount = zuQiuService.doUpdateOperatorForSn(issue, sn, changeStatus);
                reAction = FOOTBALL_MATCH_LIST;
            } else if ("201".equals(lotteryCode)) {
                ISubIssueForJingCaiLanQiuService lanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
                if (Constants.SUB_ISSUE_END_OPERATOR_DEFAULT == changeStatus) {
                    SubIssueForJingCaiLanQiu subIssue = lanQiuService.getSubIssueForJingCaiLanQiu(id);
                    changeStatus = subIssue.getEndFuShiTime().getTime() > new Date().getTime() ? Constants.SUB_ISSUE_END_OPERATOR_DEFAULT : Constants.SUB_ISSUE_END_OPERATOR_END;
                }
                updateCount = lanQiuService.doUpdateOperatorForSn(issue, sn, changeStatus);
                reAction = BASKETBALL_MATCH_LIST;
            } else if ("400".equals(lotteryCode)) {
                ISubIssueForBeiDanService beiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
                if (Constants.SUB_ISSUE_END_OPERATOR_DEFAULT == changeStatus) {
                    SubIssueForBeiDan subIssue = beiDanService.getSubIssueForBeiDan(id);
                    changeStatus = subIssue.getEndFuShiTime().getTime() > new Date().getTime() ? Constants.SUB_ISSUE_END_OPERATOR_DEFAULT : Constants.SUB_ISSUE_END_OPERATOR_END;
                }
                updateCount = beiDanService.doUpdateOperatorForSn(issue, sn, changeStatus);
                reAction = BEIDAN_MATCH_LIST;
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            if (updateCount > 0) {
                setManagesLog(request, action, "操作对阵状态" + operationInfo + lotteryCode + issue + sn, Constants.MANAGER_LOG_JC_ISSUE_MANAGES);
            }
            response.sendRedirect("/manages/issueManagesServlet?action=" + reAction);
            return;
        } else if (ISSUE_MATCH_EDIT.equals(action)) {
            String lotteryCode = request.getParameter("lotteryCode");
            Long id = Utils.formatLong(request.getParameter("id"));
            if ("200".equals(lotteryCode)) {
                ISubIssueForJingCaiZuQiuService zuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
                SubIssueForJingCaiZuQiu subIssue = zuQiuService.getSubIssueForJingCaiZuQiu(id);
                request.setAttribute("subIssue", subIssue);
            } else if ("201".equals(lotteryCode)) {
                ISubIssueForJingCaiLanQiuService lanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
                SubIssueForJingCaiLanQiu subIssue = lanQiuService.getSubIssueForJingCaiLanQiu(id);
                request.setAttribute("subIssue", subIssue);
            } else if ("400".equals(lotteryCode)) {
                ISubIssueForBeiDanService beiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
                SubIssueForBeiDan subIssue = beiDanService.getSubIssueForBeiDan(id);
                request.setAttribute("subIssue", subIssue);
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
            request.setAttribute("showType", request.getParameter("showType"));
            request.getRequestDispatcher("lottery/issueMatchEdit.jsp").forward(request, response);
            return;
        } else if (ISSUE_MATCH_EDIT_DO.equals(action)) {

            String type = request.getParameter("type");
            String lotteryCode = request.getParameter("lotteryCode");
            Long id = Utils.formatLong(request.getParameter("id"));

            String endTime = request.getParameter("endTime");
            String isFlush = request.getParameter("isFlush");
            String isHide = request.getParameter("isHide");

            StringBuffer operationInfo = new StringBuffer("修改");

            if ("200".equals(lotteryCode)) {
                ISubIssueForJingCaiZuQiuService zuQiuService = (ISubIssueForJingCaiZuQiuService) SpringUtils.getBean("subIssueForJingCaiZuQiuServiceImpl");
                SubIssueForJingCaiZuQiu subIssue = zuQiuService.getSubIssueForJingCaiZuQiu(id);
                SubIssueForJingCaiZuQiu param = new SubIssueForJingCaiZuQiu();
                param.setIssue(subIssue.getIssue());
                param.setSn(subIssue.getSn());
                PageBean pageBean = zuQiuService.getSubIssueForJingCaiZuQiuListByPara(param, 1, pageSize);

                operationInfo.append("竞足");
                operationInfo.append(subIssue.getIssue()).append(" ").append(subIssue.getWeek());
                operationInfo.append(subIssue.getSn()).append(" ");
                operationInfo.append(subIssue.getMainTeam()).append("VS").append(subIssue.getGuestTeam()).append("对阵");
                if ("matchTime".equals(type)) {
                    subIssue.setEndTime(DateUtils.formatStr2Date(endTime, "yyyy-MM-dd HH:mm:ss"));
                    //更新销售时间
                    BaseSubIssueOperator baseSubIssueOperator = new BaseSubIssueOperator();
                    Map<String, Date> dateMap = baseSubIssueOperator.getEndTime(subIssue.getLotteryCode(), subIssue.getEndTime());
                    subIssue.setEndDanShiTime(dateMap.get("danShi"));
                    subIssue.setEndFuShiTime(dateMap.get("fuShi"));
                    subIssue.setBackup1("1");//停止自动更新

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiZuQiu sub : (List<SubIssueForJingCaiZuQiu>) pageBean.getPageContent()) {
                            sub.setEndTime(subIssue.getEndTime());
                            sub.setEndDanShiTime(subIssue.getEndDanShiTime());
                            sub.setEndFuShiTime(subIssue.getEndFuShiTime());
                            sub.setBackup1(subIssue.getBackup1());
                            zuQiuService.update(sub);
                        }
                    }
                    //更新末关
                    ITicketReCodeService ticketReCodeService = (ITicketReCodeService) SpringUtils.getBean("ticketReCodeServiceImpl");
                    ticketReCodeService.updateTicketForIssueUpdate(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn(), subIssue.getEndTime());
                    operationInfo.append("的开赛时间为").append(endTime);
                    sendEndTimeTob(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn(), endTime);
                } else if ("cancel".equals(type)) {
                    subIssue.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
                    subIssue.setBackup1("1");//停止自动更新

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiZuQiu sub : (List<SubIssueForJingCaiZuQiu>) pageBean.getPageContent()) {
                            sub.setEndOperator(subIssue.getEndOperator());
                            sub.setBackup1(subIssue.getBackup1());
                            zuQiuService.update(sub);
                        }
                    }
                    operationInfo.append("为取消状态");

                    clearZQMatch(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn());

                } else if ("autoFlush".equals(type)) {
                    if ("1".equals(isFlush)) {
                        subIssue.setBackup1("1");
                        operationInfo.append("为手动更新");
                    } else {
                        subIssue.setBackup1("0");
                        operationInfo.append("为自动更新");
                    }

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiZuQiu sub : (List<SubIssueForJingCaiZuQiu>) pageBean.getPageContent()) {
                            sub.setBackup1(subIssue.getBackup1());
                            zuQiuService.update(sub);
                        }
                    }
                } else if ("hide".equals(type)) {
                    if ("1".equals(isHide)) {
                        subIssue.setBackup2("1");
                        operationInfo.append("为隐藏状态");
                    } else {
                        subIssue.setBackup2("0");
                        operationInfo.append("为非隐藏状态");
                    }

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiZuQiu sub : (List<SubIssueForJingCaiZuQiu>) pageBean.getPageContent()) {
                            sub.setBackup2(subIssue.getBackup2());
                            zuQiuService.update(sub);
                        }
                    }
                } else {
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } else if ("201".equals(lotteryCode)) {
                ISubIssueForJingCaiLanQiuService lanQiuService = (ISubIssueForJingCaiLanQiuService) SpringUtils.getBean("subIssueForJingCaiLanQiuServiceImpl");
                SubIssueForJingCaiLanQiu subIssue = lanQiuService.getSubIssueForJingCaiLanQiu(id);

                SubIssueForJingCaiLanQiu param = new SubIssueForJingCaiLanQiu();
                param.setIssue(subIssue.getIssue());
                param.setSn(subIssue.getSn());
                PageBean pageBean = lanQiuService.getSubIssueForJingCaiLanQiuListByPara(param, 1, pageSize);

                operationInfo.append("竞篮");
                operationInfo.append(subIssue.getIssue()).append(" ").append(subIssue.getWeek());
                operationInfo.append(subIssue.getSn()).append(" ");
                operationInfo.append(subIssue.getMainTeam()).append("VS").append(subIssue.getGuestTeam()).append("对阵");
                if ("matchTime".equals(type)) {
                    subIssue.setEndTime(DateUtils.formatStr2Date(endTime, "yyyy-MM-dd HH:mm:ss"));
                    //更新销售时间
                    BaseSubIssueOperator baseSubIssueOperator = new BaseSubIssueOperator();
                    Map<String, Date> dateMap = baseSubIssueOperator.getEndTime(subIssue.getLotteryCode(), subIssue.getEndTime());
                    subIssue.setEndDanShiTime(dateMap.get("danShi"));
                    subIssue.setEndFuShiTime(dateMap.get("fuShi"));
                    subIssue.setBackup1("1");//停止自动更新

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiLanQiu sub : (List<SubIssueForJingCaiLanQiu>) pageBean.getPageContent()) {
                            sub.setEndTime(subIssue.getEndTime());
                            sub.setEndDanShiTime(subIssue.getEndDanShiTime());
                            sub.setEndFuShiTime(subIssue.getEndFuShiTime());
                            sub.setBackup1(subIssue.getBackup1());
                            lanQiuService.update(sub);
                        }
                    }
                    //更新末关
                    ITicketReCodeService ticketReCodeService = (ITicketReCodeService) SpringUtils.getBean("ticketReCodeServiceImpl");
                    ticketReCodeService.updateTicketForIssueUpdate(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn(), subIssue.getEndTime());
                    operationInfo.append("的开赛时间为").append(endTime);
                    sendEndTimeTob(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn(), endTime);
                } else if ("cancel".equals(type)) {
                    subIssue.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
                    subIssue.setBackup1("1");//停止自动更新

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiLanQiu sub : (List<SubIssueForJingCaiLanQiu>) pageBean.getPageContent()) {
                            sub.setEndOperator(subIssue.getEndOperator());
                            sub.setBackup1(subIssue.getBackup1());
                            lanQiuService.update(sub);
                        }
                    }
                    clearLQMatch(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn());
                    operationInfo.append("为取消状态");
                } else if ("autoFlush".equals(type)) {
                    if ("1".equals(isFlush)) {
                        subIssue.setBackup1("1");
                        operationInfo.append("为手动更新");
                    } else {
                        subIssue.setBackup1("0");
                        operationInfo.append("为自动更新");
                    }

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiLanQiu sub : (List<SubIssueForJingCaiLanQiu>) pageBean.getPageContent()) {
                            sub.setBackup1(subIssue.getBackup1());
                            lanQiuService.update(sub);
                        }
                    }

//                    lanQiuService.update(subIssue);
                } else if ("hide".equals(type)) {
                    if ("1".equals(isHide)) {
                        subIssue.setBackup2("1");
                        operationInfo.append("为隐藏状态");
                    } else {
                        subIssue.setBackup2("0");
                        operationInfo.append("为非隐藏状态");
                    }

                    if (null != pageBean.getPageContent()) {
                        for (SubIssueForJingCaiLanQiu sub : (List<SubIssueForJingCaiLanQiu>) pageBean.getPageContent()) {
                            sub.setBackup2(subIssue.getBackup2());
                            lanQiuService.update(sub);
                        }
                    }

//                    lanQiuService.update(subIssue);
                } else {
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } else if ("400".equals(lotteryCode)) {
                ISubIssueForBeiDanService beiDanService = (ISubIssueForBeiDanService) SpringUtils.getBean("subIssueForBeiDanServiceImpl");
                SubIssueForBeiDan subIssue = beiDanService.getSubIssueForBeiDan(id);
                operationInfo.append("北单");
                operationInfo.append(subIssue.getIssue()).append(" ");
                operationInfo.append(subIssue.getSn()).append(" ");
                operationInfo.append(subIssue.getMainTeam()).append("VS").append(subIssue.getGuestTeam()).append("对阵");
                if ("matchTime".equals(type)) {
                    subIssue.setEndTime(DateUtils.formatStr2Date(endTime, "yyyy-MM-dd HH:mm:ss"));
                    //更新销售时间
                    BaseSubIssueOperator baseSubIssueOperator = new BaseSubIssueOperator();
                    Map<String, Date> dateMap = baseSubIssueOperator.getEndTime(subIssue.getLotteryCode(), subIssue.getEndTime());
                    subIssue.setEndDanShiTime(dateMap.get("danShi"));
                    subIssue.setEndFuShiTime(dateMap.get("fuShi"));
                    subIssue.setBackup1("1");//停止自动更新
                    beiDanService.update(subIssue);
                    //更新末关
                    ITicketReCodeService ticketReCodeService = (ITicketReCodeService) SpringUtils.getBean("ticketReCodeServiceImpl");
                    ticketReCodeService.updateTicketForIssueUpdate(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn(), subIssue.getEndTime());
                    operationInfo.append("的开赛时间为").append(endTime);
                } else if ("cancel".equals(type)) {
                    subIssue.setEndOperator(Constants.SUB_ISSUE_END_OPERATOR_CANCEL);
                    subIssue.setBackup1("1");//停止自动更新
                    beiDanService.update(subIssue);
                    clearBDMatch(subIssue.getLotteryCode(), subIssue.getIssue(), subIssue.getSn());
                    operationInfo.append("为取消状态");
                } else if ("autoFlush".equals(type)) {
                    if ("1".equals(isFlush)) {
                        subIssue.setBackup1("1");
                        operationInfo.append("为手动更新");
                    } else {
                        subIssue.setBackup1("0");
                        operationInfo.append("为自动更新");
                    }
                    beiDanService.update(subIssue);
                } else if ("hide".equals(type)) {
                    if ("1".equals(isHide)) {
                        subIssue.setBackup2("1");
                        operationInfo.append("为隐藏状态");
                    } else {
                        subIssue.setBackup2("0");
                        operationInfo.append("为非隐藏状态");
                    }
                    beiDanService.update(subIssue);
                } else {
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
//            setManagesLog(request, action, "操作对阵(" + lotteryCode + issue + sn+")["+operationInfo+"]", Constants.MANAGER_LOG_JC_ISSUE_MANAGES);
            setManagesLog(request, action, operationInfo.toString(), Constants.MANAGER_LOG_JC_ISSUE_MANAGES);

            response.sendRedirect("/manages/issueManagesServlet?action=issueMatchEdit&lotteryCode=" + lotteryCode + "&id=" + id);
            return;
        } else if (MATCH_EDIT.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            MatchEditLog matchEditLog = new MatchEditLog();
            if (Utils.isNotEmpty(lotteryCode)) {
                matchEditLog.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(sn)) {
                matchEditLog.setSn(sn);
                request.setAttribute("sn", sn);
            }
            if (Utils.isNotEmpty(issue)) {
                matchEditLog.setIssue(issue);
                request.setAttribute("issue", issue);
            }
            IMatchEditLogService matchEditLogService = (IMatchEditLogService) SpringUtils.getBean("matchEditLogServiceImpl");
            List<MatchEditLog> matchEditLogs = matchEditLogService.getMatchEditLogsByMsg(matchEditLog);
            Map<String, String> map = new HashMap<String, String>();
            for (MatchEditLog m : matchEditLogs) {
                String code = m.getPlayCode() + m.getPollCode();
                map.put(code, code);
            }
            request.setAttribute("map", map);
            request.getRequestDispatcher("lottery/matchEdit.jsp").forward(request, response);
            return;
        } else if (MATCH_EDIT_DO.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String sn = Utils.formatStr(request.getParameter("sn"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String[] playType = request.getParameterValues("playType");
            String key = Constants.MEMCACHED_MATCH_LOG + issue + sn + lotteryCode;
            List<MatchEditLog> matchEditLogs = new ArrayList<MatchEditLog>();
            IMatchEditLogService matchEditLogService = (IMatchEditLogService) SpringUtils.getBean("matchEditLogServiceImpl");
            XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
            try {
                matchEditLogService.delEditLogsByMsg(lotteryCode, issue, sn);
                memcachedClientWrapper.getMemcachedClient().delete(key + "0101");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0102");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0201");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0202");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0301");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0302");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0401");
                memcachedClientWrapper.getMemcachedClient().delete(key + "0402");
                if ("200".equals(lotteryCode)) {
                    memcachedClientWrapper.getMemcachedClient().delete(key + "0501");
                    memcachedClientWrapper.getMemcachedClient().delete(key + "0502");
                }
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MemcachedException e) {
                e.printStackTrace();
            }
            if (!Utils.isNotEmpty(playType)) {
                response.sendRedirect("/manages/issueManagesServlet?action=matchEdit&lotteryCode=" + lotteryCode + "&issue=" + issue + "&sn=" + sn);
                return;
            }
            //添加停售赛事
            for (String play : playType) {
                MatchEditLog matchEditLog = new MatchEditLog();
                if (Utils.isNotEmpty(lotteryCode)) {
                    matchEditLog.setLotteryCode(lotteryCode);
                }
                if (Utils.isNotEmpty(sn)) {
                    matchEditLog.setSn(sn);
                }
                if (Utils.isNotEmpty(issue)) {
                    matchEditLog.setIssue(issue);
                }
                String[] msg = play.split("-");
                String playCode = msg[0];
                String pollCode = msg[1];
                matchEditLog.setPlayCode(playCode);
                matchEditLog.setPollCode(pollCode);
                matchEditLog.setCreateTime(new Date());
                matchEditLog.setStatus(0);
                matchEditLogs.add(matchEditLog);
            }
            boolean result = matchEditLogService.setEditLogsByMsg(matchEditLogs);
            if (result) {
                Map<String, String> map = new HashMap<String, String>();
                try {
                    for (MatchEditLog m : matchEditLogs) {
                        String code = m.getPlayCode() + m.getPollCode();
                        memcachedClientWrapper.getMemcachedClient().set(key + code, 432000, 0);
                        map.put(code, code);
                    }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (MemcachedException e) {
                    e.printStackTrace();
                }
                request.setAttribute("map", map);
            }
            response.sendRedirect("/manages/issueManagesServlet?action=matchEdit&lotteryCode=" + lotteryCode + "&issue=" + issue + "&sn=" + sn);
            return;
        } else if (ZQ_ISSUE_QUERY.equals(action)) {
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String issueNameStart = Utils.formatStr(request.getParameter("issueNameStart"));
            String issueNameEnd = Utils.formatStr(request.getParameter("issueNameEnd"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String status = Utils.formatStr(request.getParameter("status"), null);
            String bonusStatus = Utils.formatStr(request.getParameter("bonusStatus"), null);
            String order = Utils.formatStr(request.getParameter("order"));
            String operatorsAward = Utils.formatStr(request.getParameter("operatorsAward"));
            String statuses = Utils.formatStr(request.getParameter("statuses"));

            MainIssue mainIssue = new MainIssue();

            if (Utils.isNotEmpty(lotteryCode)) {
                mainIssue.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(status)) {
                mainIssue.setStatus(Utils.formatInt(status, null));
                request.setAttribute("status", status);
            }
            if (Utils.isNotEmpty(statuses)) {
                try {
                    String[] statusArray = statuses.split(",");
                    Integer[] statusIntArray = new Integer[statusArray.length];
                    for (int i = 0; i < statusArray.length; i++) {
                        statusIntArray[i] = Utils.formatInt(statusArray[i], 0);
                    }
                    mainIssue.setStatuses(statusIntArray);
                } catch (Exception e) {

                }
            }

            if (Utils.isNotEmpty(bonusStatus)) {
                mainIssue.setBonusStatus(Utils.formatInt(bonusStatus, null));
                request.setAttribute("bonusStatus", bonusStatus);
            }

            if (Utils.isNotEmpty(issueNameStart)) {
                mainIssue.setIssueNameStart(issueNameStart);
                request.setAttribute("issueNameStart", issueNameStart);
            }

            if (Utils.isNotEmpty(issueNameEnd)) {
                mainIssue.setIssueNameEnd(issueNameEnd);
                request.setAttribute("issueNameEnd", issueNameEnd);
            }

            if (Utils.isNotEmpty(startTime)) {
                mainIssue.setStartTime(Utils.formatDate(startTime + " 00:00:00"));
                request.setAttribute("startTime", startTime);
            }

            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                mainIssue.setEndTime(calendar.getTime());
            }

            if (Utils.isNotEmpty(operatorsAward)) {
                mainIssue.setOperatorsAward(Utils.formatInt(operatorsAward, null));
                request.setAttribute("operatorsAward", operatorsAward);
            }

            if (!Utils.isNotEmpty(order)) {
                order = "desc";
            }
            PageBean pageBean = issueService.getIssueList(mainIssue, page, pageSize, order);
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("order", order);
            request.getRequestDispatcher("lottery/zqIssuePageList.jsp").forward(request, response);
            return;
        } else if (POST_ISSUE_QUERY.equals(action)){
        	String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        	String postCode = Utils.formatStr(request.getParameter("postCode"));
        	
        	List<Map<String,Object>> postIssueList = postService.getLotteryPostIssue(lotteryCode,postCode);
            request.setAttribute("postIssueList", postIssueList);
            request.getRequestDispatcher("lottery/lotteryPostIssueList.jsp").forward(request, response);
            return;
        } else if (MEMBER_QUERY_LOTTERY_CODE.equals(action)) {
        	String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        	Member member = new Member();
        	if (Utils.isNotEmpty(lotteryCode)) {
            	member.setBackup1(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            List<Member> lstMember = memberService.getMemberListForLotteryCode(member);
            request.setAttribute("lstMember", lstMember);
            request.setAttribute("lstMemberSize", lstMember.size());
            request.getRequestDispatcher("/manages/member/qryMemberByLotteryIdList.jsp").forward(request, response);
        }
    }

    private void updateIssueMsgTc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IMainIssueService issueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        String issue = Utils.formatStr(request.getParameter("issue"));
        MainIssue mainIssue = issueService.getMainIssueByLotteryIssue(lotteryCode, issue);

        if (null == mainIssue) {
            return;
        }
        if (Utils.isNotEmpty(mainIssue.getBonusClass())) {
            BonusClassManager bonusClassManager = new BonusClassManager(mainIssue.getBonusClass());
            request.setAttribute("award", bonusClassManager.toMap());
        }
        request.setAttribute("mainIssue", mainIssue);

        request.getRequestDispatcher("issue/updateIssueMsgTc.jsp").forward(request, response);
        return;
    }

    private void doUpdateIssueMsgTc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IMainIssueService issueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        String mainIssueId = Utils.formatStr(request.getParameter("mainIssueId"));
        String issue = Utils.formatStr(request.getParameter("issue"));


        String saleTotal = Utils.formatStr(request.getParameter("saleTotal"));
        String globalSaleTotal = Utils.formatStr(request.getParameter("globalSaleTotal"));
        String prizePool = Utils.formatStr(request.getParameter("prizePool"));

        String globalSaleTotal9 = Utils.formatStr(request.getParameter("globalSaleTotal9"));
        String prizePool9 = Utils.formatStr(request.getParameter("prizePool9"));

        String globalSaleTotal10 = Utils.formatStr(request.getParameter("globalSaleTotal10"));

        MainIssue mainIssue = issueService.getMainIssueByLotteryIssue(lotteryCode, issue);

        if (Utils.isNotEmpty(globalSaleTotal9)) {
            mainIssue.setBackup3(globalSaleTotal9);
        }
        if (Utils.isNotEmpty(prizePool9)) {
            mainIssue.setBackup2(prizePool9);
        }

        if (Utils.isNotEmpty(globalSaleTotal10)) {
            mainIssue.setBackup2(globalSaleTotal10);
        }

//        mainIssue.setId(new Long(mainIssueId));
        if (Utils.isNotEmpty(saleTotal)) {
            mainIssue.setSaleTotal(saleTotal);
        }
        if (Utils.isNotEmpty(prizePool)) {
            mainIssue.setPrizePool(prizePool);
        }
        if (Utils.isNotEmpty(globalSaleTotal)) {
            mainIssue.setGlobalSaleTotal(globalSaleTotal);
        }
        String[] totalArr = request.getParameterValues("total");
        String[] cityCountArr = request.getParameterValues("cityCount");
        BonusClassManager bonusClassManager = new BonusClassManager();
        if (Utils.isNotEmpty(mainIssue.getBonusClass())) {
            bonusClassManager = new BonusClassManager(mainIssue.getBonusClass());
        }
        for (int i = 1; i <= totalArr.length; i++) {
            Level level = null;
            if (i < 9) {
                level = bonusClassManager.getLevelOfNum(i);
            } else if (i == 9) {
                level = bonusClassManager.getLevelOfNum(10);//生肖乐
            } else if (i > 9) {
                level = bonusClassManager.getLevelOfNum(i + 1);
            }
            level.setTotal(totalArr[i - 1]);
            level.setCityCount(Long.valueOf(cityCountArr[i - 1]));
        }

        mainIssue.setBonusClass(bonusClassManager.toString());
        issueService.updateIssue(mainIssue);
        this.setManagesLog(request, UPDATE_ISSUE_MSG, "修改了<span style=\"color:#f00\">" + ElTagUtils.getLotteryChinaName(lotteryCode) + "</span>第<span style=\"color:#f00\">" + issue + "</span>期开奖公告", Constants.MANAGER_LOG_EDIT_BONUS_INFO);

        request.setAttribute("issueMsg", "issueMsg");
        response.sendRedirect("/manages/issueManagesServlet?action=zqIssueQuery&status=3&lotteryCode=" + lotteryCode);
        return;
    }

    private void clearLQMatch(String lotteryCode, String issue, String sn) {
        XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
        Map<String, String> lotteryMap = new HashMap<String, String>();
        lotteryMap.put("00", "02");
        lotteryMap.put("01", "01");
        lotteryMap.put("02", "01");
        lotteryMap.put("04", "01");
        for (String lotteryKey : lotteryMap.keySet()) {
            StringBuffer key = new StringBuffer();
            key.append(lotteryCode).append(".").append(issue).append(".");
            key.append(lotteryKey).append(".").append(lotteryMap.get(lotteryKey)).append(".");
            key.append(sn);
            SubIssueForJingCaiLanQiu subIssueForJingCaiLanQiu = (SubIssueForJingCaiLanQiu) memcachedClientWrapper.get(key.toString());
            if (Utils.isNotEmpty(subIssueForJingCaiLanQiu)) {
                logger.info("删除竞彩篮球场次缓存key: " + key);
                memcachedClientWrapper.delete(key.toString());
            }
        }
    }

    private void clearZQMatch(String lotteryCode, String issue, String sn) {
        XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
        Map<String, String> lotteryMap = new HashMap<String, String>();
        lotteryMap.put("00", "02");
        lotteryMap.put("01", "01");
        lotteryMap.put("02", "01");
        lotteryMap.put("03", "01");
        for (String lotteryKey : lotteryMap.keySet()) {
            StringBuffer key = new StringBuffer();
            key.append(lotteryCode).append(".").append(issue).append(".");
            key.append(lotteryKey).append(".").append(lotteryMap.get(lotteryKey)).append(".");
            key.append(sn);
            SubIssueForJingCaiZuQiu subIssueForJingCaiZuQiu = (SubIssueForJingCaiZuQiu) memcachedClientWrapper.get(key.toString());
            if (Utils.isNotEmpty(subIssueForJingCaiZuQiu)) {
                logger.info("删除竞彩足球场次缓存key: " + key);
                memcachedClientWrapper.delete(key.toString());
            }
        }
    }

    private void clearBDMatch(String lotteryCode, String issue, String sn) {
        XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
        StringBuffer key = new StringBuffer();
        key.append(lotteryCode).append(".").append(issue).append(".");
        key.append(sn);
        SubIssueForBeiDan subIssueForBeiDan = (SubIssueForBeiDan) memcachedClientWrapper.get(key.toString());
        if (Utils.isNotEmpty(subIssueForBeiDan)) {
            logger.info("删除北京单场场次缓存key: " + key);
            memcachedClientWrapper.delete(key.toString());
        }
    }
    
    /**
     * 当后台改变竞彩停售时间时，会向toc发送更新请求
     * @param lotteryCode
     * @param issue
     * @param sn
     * @param endTime
     */
    private void sendEndTimeTob(String lotteryCode,String issue,String sn, String endTime){
    	try {
    		String url = ConfigUtils.getValue("TOC_BACKEND_URL");
    		Map<String, String> param = new HashMap<String, String>();
    		param.put("action", "issueMatchEditDoFromTob");
    		param.put("lotteryCode", lotteryCode);
            param.put("issue", issue);
            param.put("sn", sn);
            param.put("endTime", endTime);
            String encoding = "utf-8";
			HttpClientUtils hcu = new HttpClientUtils(url, encoding, param);
			hcu.httpClientRequestD();
			
		} catch (Exception e) {
			logger.error("sendEndTimeTob lotteryCode:" + lotteryCode + ",issue:" + issue + ",sn:" + sn + ",endTime:" + endTime, e);
		}
    }
}
