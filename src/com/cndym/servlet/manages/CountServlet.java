package com.cndym.servlet.manages;

import com.cndym.bean.count.*;
import com.cndym.bean.query.TicketMainIssueBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.service.*;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: mcs Date: 12-10-31 Time: 下午3:50
 */
public class CountServlet extends BaseManagesServlet {

    private final String ACCOUNT_TABLE = "accountTable";
    private final String SALE_TABLE = "saleTable";
    private final String ACCOUNT_TABLE_FOR_SUB = "accountTableForSub";
    private final String SALE_TABLE_FOR_SUB = "saleTableForSub";
    private final String TICKET_TABLE = "ticketTable";
    private final String TICKET_TABLE_FOR_SUB = "ticketTableForSub";
    private final String DAY_SALE_TABLE = "daySaleTable";
    private final String DAY_SALE_TABLE_FOR_SUB = "daySaleTableForSub";
    private final String DAY_TICKET_TABLE = "dayTicketTable";
    private final String DAY_TICKET_TABLE_FOR_SUB = "dayTicketTableForSub";
    private final String DAY_TICKET_COUNT = "dayTicketCount";
    private final String LOTTERY_COUNT = "lotteryCount";
    private final String BONUS_TICKET = "bonusTicket";
    private final String LOTTERY_ISSUE_COUNT = "lotteryIssueCount";
    private final String LOTTERY_DATE_COUNT = "lotteryDateCount";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    private Logger logger = Logger.getLogger(getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        IAccountTableService accountTableService = (IAccountTableService) SpringUtils.getBean("accountTableServiceImpl");
        ISaleTableService saleTableService = (ISaleTableService) SpringUtils.getBean("saleTableServiceImpl");
        ITicketTableService ticketTableService = (ITicketTableService) SpringUtils.getBean("ticketTableServiceImpl");
        IDaySaleTableService daySaleTableService = (IDaySaleTableService) SpringUtils.getBean("daySaleTableServiceImpl");
        IDayTicketTableService dayTicketTableService = (IDayTicketTableService) SpringUtils.getBean("dayTicketTableServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (ACCOUNT_TABLE.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String currDate = Utils.formatStr(request.getParameter("currDate"));

            AccountTable accountTable = new AccountTable();
            accountTable.setSid(sid);
            accountTable.setName(name);
            if (Utils.isNotEmpty(startDate)) {
                accountTable.setStartDate(startDate);
            }
            if (Utils.isNotEmpty(endDate)) {
                accountTable.setEndDate(endDate);
            }
            if (Utils.isNotEmpty(currDate)) {
                accountTable.setCurrDate(currDate);
            }
            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            if (Utils.isNotEmpty(sid)) {
                accountTable.setSid(sid);
            } else {
                accountTable.setSid("false");
            }

            PageBean pageBean = accountTableService.getPageBeanByPara(accountTable, page, pageSize);
            Map<String, Object> map = accountTableService.getAccountTableCount(accountTable);
            request.setAttribute("countMap", map);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/accountTable.jsp").forward(request, response);
            return;
        } else if (SALE_TABLE.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));

            SaleTable saleTable = new SaleTable();
            saleTable.setLotteryCode(lotteryCode);
            saleTable.setIssue(issue);
            saleTable.setSid(sid);
            saleTable.setName(name);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);

            if (Utils.isNotEmpty(sid)) {
                saleTable.setSid(sid);
            } else {
                saleTable.setSid("false");
            }

            PageBean pageBean = saleTableService.getPageBeanByPara(saleTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/saleTable.jsp").forward(request, response);
            return;
        } else if (ACCOUNT_TABLE_FOR_SUB.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String currDate = Utils.formatStr(request.getParameter("currDate"));

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            AccountTable accountTable = new AccountTable();
            accountTable.setSid(sid);
            accountTable.setName(name);

            if (Utils.isNotEmpty(startDate)) {
                accountTable.setStartDate(startDate);
            }
            if (Utils.isNotEmpty(endDate)) {
                accountTable.setEndDate(endDate);
            }

            if (Utils.isNotEmpty(currDate)) {
                accountTable.setStartDate(currDate);
                accountTable.setEndDate(currDate);
                request.setAttribute("startDate", currDate);
                request.setAttribute("endDate", currDate);
            }

            if (Utils.isNotEmpty(sid)) {
                accountTable.setSid(sid);
            } else {
                accountTable.setSid("true");
            }

            PageBean pageBean = accountTableService.getPageBeanByPara(accountTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/accountTableForSub.jsp").forward(request, response);
            return;
        } else if (SALE_TABLE_FOR_SUB.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);

            SaleTable saleTable = new SaleTable();
            saleTable.setLotteryCode(lotteryCode);
            saleTable.setIssue(issue);
            if (Utils.isNotEmpty(sid)) {
                saleTable.setSid(sid);
            } else {
                saleTable.setSid("true");
            }
            saleTable.setName(name);

            PageBean pageBean = saleTableService.getPageBeanByPara(saleTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/saleTableForSub.jsp").forward(request, response);
            return;
        } else if (TICKET_TABLE.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));

            request.setAttribute("postCode", postCode);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);

            TicketTable ticketTable = new TicketTable();
            ticketTable.setLotteryCode(lotteryCode);
            ticketTable.setIssue(issue);
            if (Utils.isNotEmpty(postCode)) {
                ticketTable.setPostCode(postCode);
            } else {
                ticketTable.setPostCode("false");
            }
            PageBean pageBean = ticketTableService.getPageBeanByPara(ticketTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/ticketTable.jsp").forward(request, response);
            return;
        } else if (TICKET_TABLE_FOR_SUB.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));

            request.setAttribute("postCode", postCode);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);

            TicketTable ticketTable = new TicketTable();
            ticketTable.setLotteryCode(lotteryCode);
            ticketTable.setIssue(issue);
            if (Utils.isNotEmpty(postCode)) {
                ticketTable.setPostCode(postCode);
            } else {
                ticketTable.setPostCode("true");
            }
            PageBean pageBean = ticketTableService.getPageBeanByPara(ticketTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/ticketTableForSub.jsp").forward(request, response);
            return;
        } else if (DAY_SALE_TABLE.equals(action)) {
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));

            DaySaleTable saleTable = new DaySaleTable();
            saleTable.setStartDate(startDate);
            saleTable.setEndDate(endDate);
            saleTable.setSid(sid);
            saleTable.setName(name);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            if (Utils.isNotEmpty(sid)) {
                saleTable.setSid(sid);
            } else {
                saleTable.setSid("false");
            }

            PageBean pageBean = daySaleTableService.getPageBeanByPara(saleTable, page, pageSize);
            Map<String, Object> map = daySaleTableService.getdaySaleTableCount(saleTable);
            request.setAttribute("countMap", map);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/daySaleTable.jsp").forward(request, response);
            return;
        } else if (DAY_SALE_TABLE_FOR_SUB.equals(action)) {
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));

            DaySaleTable saleTable = new DaySaleTable();
            saleTable.setStartDate(startDate);
            saleTable.setEndDate(endDate);
            saleTable.setSid(sid);
            saleTable.setName(name);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            if (Utils.isNotEmpty(sid)) {
                saleTable.setSid(sid);
            } else {
                saleTable.setSid("true");
            }

            PageBean pageBean = daySaleTableService.getPageBeanByPara(saleTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/daySaleTableForSub.jsp").forward(request, response);
            return;
        } else if (DAY_TICKET_TABLE.equals(action)) {
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));

            request.setAttribute("postCode", postCode);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            DayTicketTable ticketTable = new DayTicketTable();
            ticketTable.setStartDate(startDate);
            ticketTable.setEndDate(endDate);
            if (Utils.isNotEmpty(postCode)) {
                ticketTable.setPostCode(postCode);
            } else {
                ticketTable.setPostCode("false");
            }
            PageBean pageBean = dayTicketTableService.getPageBeanByPara(ticketTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/dayTicketTable.jsp").forward(request, response);
            return;
        } else if (DAY_TICKET_TABLE_FOR_SUB.equals(action)) {
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));

            request.setAttribute("postCode", postCode);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);

            DayTicketTable ticketTable = new DayTicketTable();
            ticketTable.setStartDate(startDate);
            ticketTable.setEndDate(endDate);
            if (Utils.isNotEmpty(postCode)) {
                ticketTable.setPostCode(postCode);
            } else {
                ticketTable.setPostCode("true");
            }
            PageBean pageBean = dayTicketTableService.getPageBeanByPara(ticketTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/dayTicketTableForSub.jsp").forward(request, response);
            return;
        } else if (DAY_TICKET_COUNT.equals(action)) {
            String startDate = Utils.formatStr(request.getParameter("startDate"));
            String endDate = Utils.formatStr(request.getParameter("endDate"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));

            request.setAttribute("postCode", postCode);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            request.setAttribute("lotteryCode", lotteryCode);

            if (Utils.isNotEmpty(startDate)) {
                startDate = startDate + " 00:00:00";
            }
            if (Utils.isNotEmpty(endDate)) {
                endDate = endDate + " 23:59:59";
            }

            Map<String, Object> cond = new HashMap<String, Object>();
            cond.put("postCode", postCode);
            cond.put("lotteryCode", lotteryCode);
            cond.put("startTime", startDate);
            cond.put("endTime", endDate);
            cond.put("page", page);
            cond.put("pageSize", pageSize);

            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            PageBean pageBean = ticketService.getTicketCountByDay(cond);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/dayTicketCount.jsp").forward(request, response);
            return;
        } else if (LOTTERY_COUNT.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String issueStatus = Utils.formatStr(request.getParameter("issueStatus"));
            String issueEnd = Utils.formatStr(request.getParameter("issueEnd"));
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String orderStatus = Utils.formatStr(request.getParameter("orderStatus"));
            String type = Utils.formatStr(request.getParameter("type"));

            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            ticketQueryBean.setTicket(ticket);
            if (Utils.isNotEmpty(sid)) {
                ticketQueryBean.getTicket().setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(orderStatus)) {
                ticketQueryBean.getTicket().setTicketStatus(new Integer(orderStatus));
                request.setAttribute("orderStatus", orderStatus);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                ticketQueryBean.getTicket().setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(postCode)) {
                ticketQueryBean.getTicket().setPostCode(postCode);
                request.setAttribute("postCode", postCode);
            }
            if (Utils.isNotEmpty(createStartTime)) {
                ticketQueryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            } else if (Utils.isEmpty(type)) {
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(c.DAY_OF_YEAR, -7);
                String time = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
                ticketQueryBean.setCreateStartTime(Utils.formatDate(time + " 00:00:00"));
                request.setAttribute("createStartTime", time);
            }
            if (Utils.isNotEmpty(createEndTime)) {
                request.setAttribute("createEndTime", createEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                ticketQueryBean.setCreateEndTime(calendar.getTime());
            } else if (Utils.isEmpty(type)) {
                request.setAttribute("createEndTime", Utils.today("yyyy-MM-dd"));
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(Utils.today("yyyy-MM-dd") + " 00:00:00");
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
            if (Utils.isNotEmpty(issueStatus)) {
                ticketQueryBean.setIssueStart(issueStatus);
                request.setAttribute("issueStatus", issueStatus);
            }
            if (Utils.isNotEmpty(issueEnd)) {
                ticketQueryBean.setIssueEnd(issueEnd);
                request.setAttribute("issueEnd", issueEnd);
            }
            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            PageBean pageBean = ticketService.getPageBeanByParaLottery(ticketQueryBean);
            request.setAttribute("pageBean", pageBean);
            Double totalAmount = 0d;
            Double totalFixBonuxAmount = 0d;
            Double totalBonuxAmount = 0d;
            for (Object obj : pageBean.getPageContent()) {
                Map<String, Object> map = (Map<String, Object>) obj;
                totalAmount += new Double(map.get("amount").toString());
                totalFixBonuxAmount += new Double(map.get("fixBonusAmount").toString());
                totalBonuxAmount += new Double(map.get("bonusAmount").toString());
            }
            request.setAttribute("totalAmount", Utils.formatNumberZ(totalAmount));
            request.setAttribute("totalFixBonuxAmount", Utils.formatNumberZ(totalFixBonuxAmount));
            request.setAttribute("totalBonuxAmount", Utils.formatNumberZ(totalBonuxAmount));
            request.getRequestDispatcher("/manages/count/lotteryCount.jsp").forward(request, response);
            return;
        } else if (BONUS_TICKET.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issueStart = Utils.formatStr(request.getParameter("issueStart"));
            String issueEnd = Utils.formatStr(request.getParameter("issueEnd"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));

            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            if (Utils.isNotEmpty(lotteryCode)) {
                ticket.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(postCode)) {
                ticket.setPostCode(postCode);
                request.setAttribute("postCode", postCode);
            }
            ticketQueryBean.setTicket(ticket);
            if (Utils.isNotEmpty(issueStart)) {
                ticketQueryBean.setIssueStart(issueStart);
                request.setAttribute("issueStart", issueStart);
            }
            if (Utils.isNotEmpty(issueEnd)) {
                ticketQueryBean.setIssueEnd(issueEnd);
                request.setAttribute("issueEnd", issueEnd);
            }
            PageBean pageBean = ticketService.getPageBeanByTicket(ticketQueryBean, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/count/bonusTicket.jsp").forward(request, response);
            return;
        } else if (LOTTERY_ISSUE_COUNT.equals(action)) {
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String issueStatus = Utils.formatStr(request.getParameter("issueStatus"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String sidName = Utils.formatStr(request.getParameter("name"));
            String ticketStatus = Utils.formatStr(request.getParameter("ticketStatus"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            Ticket ticket = new Ticket();
            MainIssue mainIssue = new MainIssue();
            TicketMainIssueBean ticketMainIssueBean = new TicketMainIssueBean();
            if (Utils.isEmpty(startTime)) {
                startTime = Utils.formatDate2Str(new Date(), "yyyy-MM-dd");
            }
            request.setAttribute("startTime", startTime);
            ticketMainIssueBean.setEndTimeStart(Utils.formatDate(startTime + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
            if (Utils.isEmpty(endTime)) {
                endTime = Utils.formatDate2Str(new Date(), "yyyy-MM-dd");
            }
            request.setAttribute("endTime", endTime);
            Calendar calendar = Calendar.getInstance();
            Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
            calendar.setTime(endT);
            calendar.add(Calendar.DATE, 1);
            ticketMainIssueBean.setEndTimeEnd(calendar.getTime());
            if (Utils.isNotEmpty(issueStatus)) {
                mainIssue.setStatus(new Integer(issueStatus));
                request.setAttribute("issueStatus", issueStatus);
            }
            if (Utils.isNotEmpty(sid)) {
                ticket.setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(sidName)) {
                ticket.setSid(sidName);
                request.setAttribute("name", sidName);
            }
            if (Utils.isNotEmpty(ticketStatus)) {
                ticket.setTicketStatus(new Integer(ticketStatus));
                request.setAttribute("ticketStatus", ticketStatus);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                ticket.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            ticketMainIssueBean.setTicket(ticket);
            ticketMainIssueBean.setMainIssue(mainIssue);
            PageBean pageBean = ticketService.getIssueLotteryCount(ticketMainIssueBean, page, pageSize);
            Map<String, Object> dataMap = ticketService.getTicketMainIssueCountByIssue(ticketMainIssueBean);
            Double totalAmount = 0d;
            Double totalFixBonuxAmount = 0d;
            Double totalBonuxAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                if (Utils.isNotEmpty(dataMap.get("totalAmount"))) {
                    totalAmount = new Double(dataMap.get("totalAmount").toString());
                    totalFixBonuxAmount = new Double(dataMap.get("totalFixBonuxAmount").toString());
                    totalBonuxAmount = new Double(dataMap.get("totalBonuxAmount").toString());
                }

            }
            request.setAttribute("totalAmount", Utils.formatNumberZ(totalAmount));
            request.setAttribute("totalFixBonuxAmount", Utils.formatNumberZ(totalFixBonuxAmount));
            request.setAttribute("totalBonuxAmount", Utils.formatNumberZ(totalBonuxAmount));
            request.setAttribute("pageBean", pageBean);

            request.getRequestDispatcher("/manages/count/lotteryIssueCount.jsp").forward(request, response);
            return;
        } else if (LOTTERY_DATE_COUNT.equals(action)) {
            String startIssue = Utils.formatStr(request.getParameter("startIssue"));
            String endIssue = Utils.formatStr(request.getParameter("endIssue"));
            String sid = Utils.formatStr(request.getParameter("name"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            String ticketStatus = Utils.formatStr(request.getParameter("ticketStatus"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String status = Utils.formatStr(request.getParameter("status"));
            String type = Utils.formatStr(request.getParameter("type"));
            String url = "/manages/count/lotteryIssueCountNew.jsp";
            TicketQueryBean ticketQueryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();
            if ("1".equals(type)) {
                url = "/manages/count/lotteryDateCount.jsp";
                String startTime = Utils.formatStr(request.getParameter("startTime"));
                String endTime = Utils.formatStr(request.getParameter("endTime"));
                if (Utils.isEmpty(startTime)) {
                    startTime = Utils.formatDate2Str(new Date(), "yyyy-MM-dd");
                }
                request.setAttribute("startTime", startTime);
                ticketQueryBean.setSendStartTime(Utils.formatDate(startTime + " 00:00:00", "yyyy-MM-dd hh:mm:ss"));
                if (Utils.isEmpty(endTime)) {
                    endTime = Utils.formatDate2Str(new Date(), "yyyy-MM-dd");
                }
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                ticketQueryBean.setSendEndTime(calendar.getTime());
            }
            if (Utils.isEmpty(status)) {
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            if (Utils.isNotEmpty(startIssue)) {
                ticketQueryBean.setIssueStart(startIssue);
                request.setAttribute("startIssue", startIssue);
            }
            if (Utils.isNotEmpty(endIssue)) {
                ticketQueryBean.setIssueEnd(endIssue);
                request.setAttribute("endIssue", endIssue);
            }
            if (Utils.isNotEmpty(sid)) {
                ticket.setSid(sid);
                request.setAttribute("name", sid);
            }
            if (Utils.isNotEmpty(ticketStatus)) {
                ticket.setTicketStatus(new Integer(ticketStatus));
                request.setAttribute("ticketStatus", ticketStatus);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                ticket.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(postCode)) {
                ticket.setPostCode(postCode);
                request.setAttribute("postCode", postCode);
            }
            request.setAttribute("type", type);
            ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
            ticketQueryBean.setTicket(ticket);
            PageBean pageBean = ticketService.getDateOrIssueCount(ticketQueryBean, type, page, pageSize);
            List<Map<String, Object>> dataMapList = ticketService.getDateOrIssueCountByMsg(ticketQueryBean, type);
            Double totalAmount = 0d;
            Double totalFixBonuxAmount = 0d;
            Double totalBonuxAmount = 0d;
            if (Utils.isNotEmpty(dataMapList)) {
                for (Map<String, Object> dataMap : dataMapList) {
                    if (Utils.isNotEmpty(dataMap.get("orderAmount"))) {
                        totalAmount += new Double(dataMap.get("orderAmount").toString());
                        totalFixBonuxAmount += new Double(dataMap.get("fixBonusAmount").toString());
                        totalBonuxAmount += new Double(dataMap.get("bonusAmount").toString());
                    }
                }
            }
            request.setAttribute("totalAmount", Utils.formatNumberZ(totalAmount));
            request.setAttribute("totalFixBonuxAmount", Utils.formatNumberZ(totalFixBonuxAmount));
            request.setAttribute("totalBonuxAmount", Utils.formatNumberZ(totalBonuxAmount));
            request.setAttribute("pageBean", pageBean);

            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
    }
}
