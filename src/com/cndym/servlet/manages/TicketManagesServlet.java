package com.cndym.servlet.manages;

import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.constant.Constants;
import com.cndym.service.IMainIssueService;
import com.cndym.service.ITicketService;
import com.cndym.service.impl.MainIssueServiceImpl;
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
import java.util.*;

/**
 * User: mcs Date: 12-10-30 Time: 上午11:10
 */
public class TicketManagesServlet extends BaseManagesServlet {

    private Logger logger = Logger.getLogger(getClass());

    private final static String TICKET_LIST = "ticketList";
    private final static String NO_SEND_TICKET_LIST = "noSendticketList";
    private final static String SEND_TICKET_INFO = "sti";
    private final static String SEND_TICKET_LIST = "stl";
    private final static String TICKET_DETAIL = "ticketDetail";
    private final static String NO_SEND_TICKET_DETAIL = "noSendTicketDetail";
    private final static String RE_SEND_TICKET = "reSendTicket";
    private final static String FAILED_TICKET = "failedTicket";
    private final static String RE_SEND_TICKET_LIST = "reSendTicketList";
    private final static String FAILED_TICKET_LIST = "failedTicketList";
    private final static String ERROR_TICKET_LIST = "errorTicketList";
    private final static String ERROR_TICKET_DETAIL = "errorTicketDetail";
    private final static String SUCCESS_CANCEL_TICKET = "successCancelTicket";
    private final static String SUCCESS_CANCEL_TICKET_LIST = "successCancelTicketList";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        IBackTicketService backTicketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (TICKET_LIST.equals(action)) {
            String type = Utils.formatStr(request.getParameter("type"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String saleCode = Utils.formatStr(request.getParameter("saleCode"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            Integer ticketStatus = Utils.formatInt(request.getParameter("ticketStatus"), null);
            Integer bonusStatus = Utils.formatInt(request.getParameter("bonusStatus"), null);
            Integer bigBonus = Utils.formatInt(request.getParameter("bigBonus"), null);
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));
            String returnStartTime = Utils.formatStr(request.getParameter("returnStartTime"));
            String returnEndTime = Utils.formatStr(request.getParameter("returnEndTime"));
            String bonusStartTime = Utils.formatStr(request.getParameter("bonusStartTime"));
            String bonusEndTime = Utils.formatStr(request.getParameter("bonusEndTime"));
            String gameId = Utils.formatStr(request.getParameter("gameId")); 
            
            // 彩期
            Integer issueStatus = Utils.formatInt(request.getParameter("issueStatus"), null);
            // 彩期是否返奖
            Integer issueBonusStatus = Utils.formatInt(request.getParameter("issueBonusStatus"), null);
            // 彩期算奖状态
            Integer operatorsAward = Utils.formatInt(request.getParameter("operatorsAward"), null);

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();

            ticket.setSid(sid);
            ticket.setUserInfo(name);
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(outTicketId);
            ticket.setTicketId(ticketId);
            ticket.setSaleCode(saleCode);
            ticket.setPostCode(postCode);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("orderId", orderId);
            request.setAttribute("outTicketId", outTicketId);
            request.setAttribute("ticketId", ticketId);
            request.setAttribute("saleCode", saleCode);
            request.setAttribute("postCode", postCode);
            
            if (Utils.isNotEmpty(gameId)) {
                ticket.setNumberInfo(gameId);
                request.setAttribute("gameId", gameId);
            }

            if (Utils.isNotEmpty(ticketStatus)) {
                ticket.setTicketStatus(ticketStatus);
                request.setAttribute("ticketStatus", ticketStatus);
            }
            if (Utils.isNotEmpty(bonusStatus)) {
                ticket.setBonusStatus(bonusStatus);
                request.setAttribute("bonusStatus", bonusStatus);
            }
            if (Utils.isNotEmpty(bigBonus)) {
                ticket.setBigBonus(bigBonus);
                request.setAttribute("bigBonus", bigBonus);
            }

            if (Utils.isNotEmpty(createStartTime)) {
                queryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            } else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createStartTime", Utils.today("yyyy-MM-dd"));
            }

            if (Utils.isNotEmpty(createEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
                request.setAttribute("createEndTime", createEndTime);
            } else if (Utils.isEmpty(type)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(Utils.today("yyyy-MM-dd") + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
                request.setAttribute("createEndTime", Utils.today("yyyy-MM-dd"));
            }

            if (Utils.isNotEmpty(sendStartTime)) {
                queryBean.setSendStartTime(Utils.formatDate(sendStartTime + " 00:00:00"));
                request.setAttribute("sendStartTime", sendStartTime);
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendEndTime(calendar.getTime());
                request.setAttribute("sendEndTime", sendEndTime);
            }

            if (Utils.isNotEmpty(returnStartTime)) {
                queryBean.setReturnStartTime(Utils.formatDate(returnStartTime + " 00:00:00"));
                request.setAttribute("returnStartTime", returnStartTime);
            }

            if (Utils.isNotEmpty(returnEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(returnEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setReturnEndTime(calendar.getTime());
                request.setAttribute("returnEndTime", returnEndTime);
            }

            if (Utils.isNotEmpty(bonusStartTime)) {
                queryBean.setBonusStartTime(Utils.formatDate(bonusStartTime + " 00:00:00"));
                request.setAttribute("bonusStartTime", bonusStartTime);
            }

            if (Utils.isNotEmpty(bonusEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(bonusEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setBonusEndTime(calendar.getTime());
                request.setAttribute("bonusEndTime", bonusEndTime);
            }

            if (Utils.isNotEmpty(issueStatus)) {
                queryBean.setIssueStatus(issueStatus);
                request.setAttribute("issueStatus", issueStatus);
            }
            if (Utils.isNotEmpty(issueBonusStatus)) {
                queryBean.setIssueBonusStatus(issueBonusStatus);
                request.setAttribute("issueBonusStatus", issueBonusStatus);
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                queryBean.setOperatorsAward(operatorsAward);
                request.setAttribute("operatorsAward", operatorsAward);
            }

            queryBean.setTicket(ticket);

            PageBean pageBean = backTicketService.getPageBeanByPara(queryBean, page, pageSize);
            Map<String, Object> dataMap = backTicketService.getTicketCount(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("orderAmount"));
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmount"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmount"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/ticket/ticketList.jsp").forward(request, response);
        }

        if (TICKET_DETAIL.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(ticket.getLotteryCode(), ticket.getIssue());

            request.setAttribute("mainIssue", mainIssue);
            request.setAttribute("ticket", ticket);
            request.getRequestDispatcher("/manages/ticket/ticketDetail.jsp").forward(request, response);
            return;
        }

        if (NO_SEND_TICKET_LIST.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            Integer ticketStatus = Utils.formatInt(request.getParameter("ticketStatus"), null);
            Integer bonusStatus = Utils.formatInt(request.getParameter("bonusStatus"), null);
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));
            String gameId = Utils.formatStr(request.getParameter("gameId"));

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();

            ticket.setSid(sid);
            ticket.setUserInfo(name);
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(outTicketId);
            ticket.setTicketId(ticketId);
            ticket.setPostCode(postCode);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("orderId", orderId);
            request.setAttribute("outTicketId", outTicketId);
            request.setAttribute("ticketId", ticketId);
            request.setAttribute("postCode", postCode);
            
            
            if (Utils.isNotEmpty(gameId)) {
                ticket.setNumberInfo(gameId);
                request.setAttribute("gameId", gameId);
            }
            if (Utils.isNotEmpty(ticketStatus)) {
                ticket.setTicketStatus(ticketStatus);
                request.setAttribute("ticketStatus", ticketStatus);
            }
            if (Utils.isNotEmpty(bonusStatus)) {
                ticket.setBonusStatus(bonusStatus);
                request.setAttribute("bonusStatus", bonusStatus);
            }

            if (Utils.isNotEmpty(createStartTime)) {
                queryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            }

            if (Utils.isNotEmpty(createEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
                request.setAttribute("createEndTime", createEndTime);
            }
            if (Utils.isNotEmpty(sendStartTime)) {
                queryBean.setSendStartTime(Utils.formatDate(sendStartTime + " 00:00:00"));
                request.setAttribute("sendStartTime", sendStartTime);
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendEndTime(calendar.getTime());
                request.setAttribute("sendEndTime", sendEndTime);
            }

            queryBean.setTicket(ticket);
            
            pageSize = 200;
            request.setAttribute("pageSize", pageSize);

            PageBean pageBean = backTicketService.getPageBeanByParaForNoSend(queryBean, page, pageSize);
            Map<String, Object> dataMap = backTicketService.getTicketCountNoSend(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("orderAmount"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/ticket/noSendTicketList.jsp").forward(request, response);
        }

        if (ERROR_TICKET_LIST.equals(action)) {
            String url = "/manages/ticket/errorTicketList.jsp";
            String myType = Utils.formatStr(request.getParameter("myType"));
            if ("dy".equals(myType)) {
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }
            String type = Utils.formatStr(request.getParameter("type"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String saleCode = Utils.formatStr(request.getParameter("saleCode"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            Integer ticketStatus = Utils.formatInt(request.getParameter("ticketStatus"), 3);
            Integer bonusStatus = Utils.formatInt(request.getParameter("bonusStatus"), null);
            Integer bigBonus = Utils.formatInt(request.getParameter("bigBonus"), null);
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));
            String returnStartTime = Utils.formatStr(request.getParameter("returnStartTime"));
            String returnEndTime = Utils.formatStr(request.getParameter("returnEndTime"));
            String bonusStartTime = Utils.formatStr(request.getParameter("bonusStartTime"));
            String bonusEndTime = Utils.formatStr(request.getParameter("bonusEndTime"));

            // 彩期
            Integer issueStatus = Utils.formatInt(request.getParameter("issueStatus"), null);
            // 彩期是否返奖
            Integer issueBonusStatus = Utils.formatInt(request.getParameter("issueBonusStatus"), null);
            // 彩期算奖状态
            Integer operatorsAward = Utils.formatInt(request.getParameter("operatorsAward"), null);

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();

            ticket.setSid(sid);
            ticket.setUserInfo(name);
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(outTicketId);
            ticket.setTicketId(ticketId);
            ticket.setSaleCode(saleCode);
            ticket.setPostCode(postCode);

            request.setAttribute("sid", sid);
            request.setAttribute("name", name);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("orderId", orderId);
            request.setAttribute("outTicketId", outTicketId);
            request.setAttribute("ticketId", ticketId);
            request.setAttribute("saleCode", saleCode);
            request.setAttribute("postCode", postCode);

            if (Utils.isNotEmpty(ticketStatus)) {
                ticket.setTicketStatus(ticketStatus);
                request.setAttribute("ticketStatus", ticketStatus);
            }
            if (Utils.isNotEmpty(bonusStatus)) {
                ticket.setBonusStatus(bonusStatus);
                request.setAttribute("bonusStatus", bonusStatus);
            }
            if (Utils.isNotEmpty(bigBonus)) {
                ticket.setBigBonus(bigBonus);
                request.setAttribute("bigBonus", bigBonus);
            }

            if (Utils.isNotEmpty(createStartTime)) {
                queryBean.setCreateStartTime(Utils.formatDate(createStartTime + " 00:00:00"));
                request.setAttribute("createStartTime", createStartTime);
            } else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.today("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("createStartTime", Utils.today("yyyy-MM-dd"));
            }

            if (Utils.isNotEmpty(createEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
                request.setAttribute("createEndTime", createEndTime);
            } else if (Utils.isEmpty(type)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(Utils.today("yyyy-MM-dd") + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
                request.setAttribute("createEndTime", Utils.today("yyyy-MM-dd"));
            }

            if (Utils.isNotEmpty(sendStartTime)) {
                queryBean.setSendStartTime(Utils.formatDate(sendStartTime + " 00:00:00"));
                request.setAttribute("sendStartTime", sendStartTime);
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendEndTime(calendar.getTime());
                request.setAttribute("sendEndTime", sendEndTime);
            }

            if (Utils.isNotEmpty(returnStartTime)) {
                queryBean.setReturnStartTime(Utils.formatDate(returnStartTime + " 00:00:00"));
                request.setAttribute("returnStartTime", returnStartTime);
            }

            if (Utils.isNotEmpty(returnEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(returnEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setReturnEndTime(calendar.getTime());
                request.setAttribute("returnEndTime", returnEndTime);
            }

            if (Utils.isNotEmpty(bonusStartTime)) {
                queryBean.setBonusStartTime(Utils.formatDate(bonusStartTime + " 00:00:00"));
                request.setAttribute("bonusStartTime", bonusStartTime);
            }

            if (Utils.isNotEmpty(bonusEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(bonusEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setBonusEndTime(calendar.getTime());
                request.setAttribute("bonusEndTime", bonusEndTime);
            }

            if (Utils.isNotEmpty(issueStatus)) {
                queryBean.setIssueStatus(issueStatus);
                request.setAttribute("issueStatus", issueStatus);
            }
            if (Utils.isNotEmpty(issueBonusStatus)) {
                queryBean.setIssueBonusStatus(issueBonusStatus);
                request.setAttribute("issueBonusStatus", issueBonusStatus);
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                queryBean.setOperatorsAward(operatorsAward);
                request.setAttribute("operatorsAward", operatorsAward);
            }

            queryBean.setTicket(ticket);

            PageBean pageBean = backTicketService.getPageBeanByPara(queryBean, page, pageSize);
            Map<String, Object> dataMap = backTicketService.getTicketCount(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("orderAmount"));
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmount"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmount"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher(url).forward(request, response);
        }

        if (ERROR_TICKET_DETAIL.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(ticket.getLotteryCode(), ticket.getIssue());

            request.setAttribute("mainIssue", mainIssue);
            request.setAttribute("ticket", ticket);
            request.getRequestDispatcher("/manages/ticket/errorTicketDetail.jsp").forward(request, response);
            return;
        }


        if (SEND_TICKET_INFO.equals(action)) {
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            Date startTime = null;
            Date endTime = null;
            if (Utils.isNotEmpty(createStartTime)) {
                startTime = Utils.formatDate(createStartTime + " 00:00:00");
                request.setAttribute("createStartTime", createStartTime);
            } else {
                String startTimea = DateUtils.formatDate2Str(new Date(), "yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Utils.formatDate(startTimea + " 00:00:00"));
                startTime = calendar.getTime();
                request.setAttribute("createStartTime", DateUtils.formatDate2Str(startTime, "yyyy-MM-dd"));
            }
            if (Utils.isNotEmpty(createEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                endTime = calendar.getTime();
                request.setAttribute("createEndTime", createEndTime);
            }
            if (Utils.isNotEmpty(postCode)) {
                request.setAttribute("postCode", postCode);
            }

            List<Map<String, Object>> list = backTicketService.findNoSendTickets(startTime, endTime, postCode);
            List<Map<String, Object>> doingList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> sendingList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> reSendingList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> filSendingList = new ArrayList<Map<String, Object>>();
            StringBuffer ptResult = new StringBuffer();
            ptResult.append("SZC Ticket Monitor logs ");
            StringBuffer jcResult = new StringBuffer();
            jcResult.append("JC Ticket Monitor logs ");
            
            for (Map<String, Object> m : list) {
                if (new Integer(m.get("status").toString()) == Constants.TICKET_STATUS_DOING || new 
                		Integer(m.get("status").toString()) == Constants.TICKET_STATUS_WAIT) {
                    doingList.add(m);
                    ptResult.append("未送票或调度中:")
		            		.append("lotteryCode=[" + m.get("lottery_code") + "]")
		            		.append(",issue=[" + m.get("issue") + "]")
		            		.append(",count=[" + m.get("tts") + "]")
		            		.append(",createTime=[" + m.get("create_time") + "]")
		            		.append(",duplexTime=[" + m.get("duplex_time") + "]")
		            		.append("/n");
                } else if (new Integer(m.get("status").toString()) == Constants.TICKET_STATUS_SENDING) {
                    if (ElTagUtils.getStiTime(m.get("send_time").toString(), m.get("lottery_code").toString())) {
                        sendingList.add(m);
                        ptResult.append("送票未回执:")
		                        .append("postCode=[" + m.get("post_code") + "]")
		                		.append("lotteryCode=[" + m.get("lottery_code") + "]")
		                		.append(",issue=[" + m.get("issue") + "]")
		                		.append(",count=[" + m.get("tts") + "]")
		                		.append(",sendTime=[" + m.get("send_time") + "]")
		                		.append(",duplexTime=[" + m.get("duplex_time") + "]")
		                		.append("/n");
                    }
                } else if (new Integer(m.get("status").toString()) == Constants.TICKET_STATUS_FAILURE) {
                    filSendingList.add(m);
                    ptResult.append("失败票:")
		                    .append("postCode=[" + m.get("post_code") + "]")
		            		.append("lotteryCode=[" + m.get("lottery_code") + "]")
		            		.append(",issue=[" + m.get("issue") + "]")
		            		.append(",count=[" + m.get("tts") + "]")
		            		.append(",sendTime=[" + m.get("send_time") + "]")
		            		.append("/n");
                } else if (new Integer(m.get("status").toString()) == Constants.TICKET_STATUS_RESEND) {
                    reSendingList.add(m);
                    ptResult.append("重发票:")
		            	    .append("postCode=[" + m.get("post_code") + "]")
		            		.append("lotteryCode=[" + m.get("lottery_code") + "]")
		            		.append(",issue=[" + m.get("issue") + "]")
		            		.append(",count=[" + m.get("tts") + "]")
		            		.append(",createTime=[" + m.get("create_time") + "]")
		            		.append(",duplexTime=[" + m.get("duplex_time") + "]")
		            		.append("/n");
                }
            }
            
            List<Map<String, Object>> jcList = backTicketService.findJcNoSendTickets(startTime, endTime, postCode);
            for (Map<String, Object> n : jcList) {
                if (new Integer(n.get("status").toString()) == Constants.TICKET_STATUS_DOING || new 
                		Integer(n.get("status").toString()) == Constants.TICKET_STATUS_WAIT) {
                    doingList.add(n);
                    jcResult.append("未送票或调度中:")
            		.append("lotteryCode=[" + n.get("lottery_code") + "]")
            		.append(",issue=[" + n.get("issue") + "]")
            		.append(",gameid=[" + n.get("gameid") + "]")
            		.append(",count=[" + n.get("tts") + "]")
            		.append(",createTime=[" + n.get("create_time") + "]")
            		.append(",duplexTime=[" + n.get("duplex_time") + "]")
            		.append("/n");
                } else if (new Integer(n.get("status").toString()) == Constants.TICKET_STATUS_SENDING) {
                    if (ElTagUtils.getStiTime(n.get("send_time").toString(), n.get("lottery_code").toString())) {
                        sendingList.add(n);
                        jcResult.append("送票未回执:")
		                        .append("postCode=[" + n.get("post_code") + "]")
		                		.append("lotteryCode=[" + n.get("lottery_code") + "]")
		                		.append(",issue=[" + n.get("issue") + "]")
		                		.append(",gameid=[" + n.get("gameid") + "]")
		                		.append(",count=[" + n.get("tts") + "]")
		                		.append(",sendTime=[" + n.get("send_time") + "]")
		                		.append(",duplexTime=[" + n.get("duplex_time") + "]")
		                		.append("/n");
                    }
                } else if (new Integer(n.get("status").toString()) == Constants.TICKET_STATUS_FAILURE) {
                    filSendingList.add(n);
                    jcResult.append("失败票:")
		                    .append("postCode=[" + n.get("post_code") + "]")
		            		.append("lotteryCode=[" + n.get("lottery_code") + "]")
		            		.append(",issue=[" + n.get("issue") + "]")
		            		.append(",gameid=[" + n.get("gameid") + "]")
		            		.append(",count=[" + n.get("tts") + "]")
		            		.append(",sendTime=[" + n.get("send_time") + "]")
		            		.append("/n");
                } else if (new Integer(n.get("status").toString()) == Constants.TICKET_STATUS_RESEND) {
                    reSendingList.add(n);
                    jcResult.append("重发票:")
		            	    .append("postCode=[" + n.get("post_code") + "]")
		            		.append("lotteryCode=[" + n.get("lottery_code") + "]")
		            		.append(",issue=[" + n.get("issue") + "]")
		            		.append(",gameid=[" + n.get("gameid") + "]")
		            		.append(",count=[" + n.get("tts") + "]")
		            		.append(",createTime=[" + n.get("create_time") + "]")
		            		.append(",duplexTime=[" + n.get("duplex_time") + "]")
		            		.append("/n");
                }
            }
            
            logger.info(ptResult.toString());
            logger.info(jcResult.toString());

            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            List<Map<String, Object>> noWinIssueList = mainIssueService.getnoWinIssueList(3, 0);
            List<Map<String, Object>> saleIssueList = mainIssueService.getnoWinIssueList(0, -1);

            request.setAttribute("doingList", doingList);
            request.setAttribute("sendingList", sendingList);
            request.setAttribute("reSendingList", reSendingList);
            request.setAttribute("filSendingList", filSendingList);
            request.setAttribute("noWinIssueList", noWinIssueList);
            request.setAttribute("saleIssueList", saleIssueList);
            request.getRequestDispatcher("/manages/ticket/sendTicketInfo.jsp").forward(request, response);
            return;
        }

        if (SEND_TICKET_LIST.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String postCode = Utils.formatStr(request.getParameter("postCode"));
            Integer ticketStatus = Utils.formatInt(request.getParameter("ticketStatus"), null);
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));

            // 彩期
            Integer issueStatus = Utils.formatInt(request.getParameter("issueStatus"), null);
            // 彩期是否返奖
            Integer issueBonusStatus = Utils.formatInt(request.getParameter("issueBonusStatus"), null);
            // 彩期算奖状态
            Integer operatorsAward = Utils.formatInt(request.getParameter("operatorsAward"), null);

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();

            ticket.setSid(sid);
            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setPostCode(postCode);

            request.setAttribute("sid", sid);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("postCode", postCode);

            if (Utils.isNotEmpty(ticketStatus)) {
                ticket.setTicketStatus(ticketStatus);
                request.setAttribute("ticketStatus", ticketStatus);
            }

            Date startTime = null;
            Date endTime = null;
            if (Utils.isNotEmpty(createStartTime)) {
                startTime = Utils.formatDate(createStartTime + " 00:00:00");
                request.setAttribute("createStartTime", createStartTime);
            } else {
                String startTimea = DateUtils.formatDate2Str(new Date(), "yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(Utils.formatDate(startTimea + " 00:00:00"));
                calendar.add(Calendar.DATE, -7);
                startTime = calendar.getTime();
                request.setAttribute("createStartTime", DateUtils.formatDate2Str(startTime, "yyyy-MM-dd"));
            }
            queryBean.setCreateStartTime(startTime);
            if (Utils.isNotEmpty(createEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(createEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                endTime = calendar.getTime();
                queryBean.setCreateEndTime(endTime);
                request.setAttribute("createEndTime", createEndTime);
            }

            if (Utils.isNotEmpty(issueStatus)) {
                queryBean.setIssueStatus(issueStatus);
                request.setAttribute("issueStatus", issueStatus);
            }
            if (Utils.isNotEmpty(issueBonusStatus)) {
                queryBean.setIssueBonusStatus(issueBonusStatus);
                request.setAttribute("issueBonusStatus", issueBonusStatus);
            }
            if (Utils.isNotEmpty(operatorsAward)) {
                queryBean.setOperatorsAward(operatorsAward);
                request.setAttribute("operatorsAward", operatorsAward);
            }

            queryBean.setTicket(ticket);

            PageBean pageBean = backTicketService.getPageBeanByPara(queryBean, page, pageSize);
            Map<String, Object> dataMap = backTicketService.getTicketCount(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("orderAmount"));
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmount"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmount"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/ticket/sendTicketList.jsp").forward(request, response);
        }

        if (NO_SEND_TICKET_DETAIL.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(ticket.getLotteryCode(), ticket.getIssue());

            request.setAttribute("mainIssue", mainIssue);
            request.setAttribute("ticket", ticket);
            request.getRequestDispatcher("/manages/ticket/noSendTicketDetail.jsp").forward(request, response);
            return;
        }

        if (RE_SEND_TICKET.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            if (Utils.isNotEmpty(ticketId)) {
                int rs = ticketService.updateTicketForWait(ticketId);
                if (rs == 1) {
                    setManagesLog(request, action, "重发<span style=\"color:#f00\">" + ticketId + "</span>", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
                    request.setAttribute("msg", "重发成功");
                } else {
                    request.setAttribute("msg", "重发失败");
                }
            } else {
                request.setAttribute("msg", "重发失败");
            }

            request.getRequestDispatcher("/manages/ticketManagesServlet?action=noSendTicketDetail&ticketId=" + ticketId).forward(request, response);
        }

        if (RE_SEND_TICKET_LIST.equals(action)) {
        	 String myCheckbox[] = request.getParameterValues("myCheckbox");
             String time = ElTagUtils.addDate("d", 0);
             
             String sid= Utils.formatStr(request.getParameter("form2Sid"));
             String lotteryCode=Utils.formatStr(request.getParameter("form2LotteryCode"));
             String ticketStatus=Utils.formatStr(request.getParameter("form2TicketStatus"));
             String postCode=Utils.formatStr(request.getParameter("form2PostCode"));
             String ticketIds=Utils.formatStr(request.getParameter("form2TicketId"));
             String outTicketId=Utils.formatStr(request.getParameter("form2OutTicketId"));
             String orderId=Utils.formatStr(request.getParameter("form2OrderId"));
             String issue=Utils.formatStr(request.getParameter("form2Issue"));
             String createStartTime=Utils.formatStr(request.getParameter("form2CreateStartTime"));
             String createEndTime=Utils.formatStr(request.getParameter("form2CreateEndTime"));
             String sendStartTime=Utils.formatStr(request.getParameter("form2SendStartTime"));
             String sendEndTime=Utils.formatStr(request.getParameter("form2SendEndTime"));
             
             String url = "/manages/ticketManagesServlet?action=noSendticketList&createStartTime=" +(createStartTime==null?time:createStartTime)+ 

            		 "&createEndTime=" + (createEndTime==null?time:createEndTime)
             		+ "&sid=" + (sid==null?"":sid)
             		+ "&lotteryCode=" + (lotteryCode==null?"":lotteryCode)
             		+ "&ticketStatus=" + (ticketStatus==null?"":ticketStatus)
             		+ "&postCode=" + (postCode==null?"":postCode)
             		+ "&ticketId=" +  (ticketIds==null?"":ticketIds)
             		+ "&outTicketId=" + (outTicketId==null?"":outTicketId)
             		+ "&orderId=" + (orderId==null?"":orderId)
             		+ "&issue=" + (issue==null?"":issue)
             		+ "&sendStartTime=" + (sendStartTime==null?"":sendStartTime)
             		+ "&sendEndTime=" + (sendEndTime==null?"":sendEndTime);
             		
             for (String ticketId : myCheckbox) {
                 if (Utils.isNotEmpty(ticketId)) {
                     int rs = ticketService.updateTicketForWait(ticketId);
                     if (rs == 1) {
                         setManagesLog(request, action, "重发<span style=\"color:#f00\">" + ticketId + "</span>", 

                        Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
                         request.setAttribute("msg", "重发成功");
                     } else {
                         request.setAttribute("msg", "重发失败");
                     }
                 } else {
                     request.setAttribute("msg", "重发失败");
                 }
             }
             response.sendRedirect(url);

        }

        if (FAILED_TICKET.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            int status = ticket.getTicketStatus();
            if (status == Constants.TICKET_STATUS_DOING || status == Constants.TICKET_STATUS_SENDING || status == Constants.TICKET_STATUS_RESEND) {
                int rs = ticketService.doHhandTicketFailed(ticketId);
                if (rs == 0) {
                    setManagesLog(request, action, "<span style=\"color:#f00\">" + ticketId + "</span>退款", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
                    request.setAttribute("msg", "退款成功");
                } else {
                    request.setAttribute("msg", "退款失败");
                }
            } else {
                request.setAttribute("msg", "退款失败");
            }

            request.getRequestDispatcher("/manages/ticketManagesServlet?action=noSendTicketDetail&ticketId=" + ticketId).forward(request, response);
        }

        if (FAILED_TICKET_LIST.equals(action)) {
            String myCheckbox[] = request.getParameterValues("myCheckbox");
            String time = ElTagUtils.addDate("d", 0);
            
            String sid= Utils.formatStr(request.getParameter("form2Sid"));
            String lotteryCode=Utils.formatStr(request.getParameter("form2LotteryCode"));
            String ticketStatus=Utils.formatStr(request.getParameter("form2TicketStatus"));
            String postCode=Utils.formatStr(request.getParameter("form2PostCode"));
            String ticketIds=Utils.formatStr(request.getParameter("form2TicketId"));
            String outTicketId=Utils.formatStr(request.getParameter("form2OutTicketId"));
            String orderId=Utils.formatStr(request.getParameter("form2OrderId"));
            String issue=Utils.formatStr(request.getParameter("form2Issue"));
            String createStartTime=Utils.formatStr(request.getParameter("form2CreateStartTime"));
            String createEndTime=Utils.formatStr(request.getParameter("form2CreateEndTime"));
            String sendStartTime=Utils.formatStr(request.getParameter("form2SendStartTime"));
            String sendEndTime=Utils.formatStr(request.getParameter("form2SendEndTime"));
            
            String url = "/manages/ticketManagesServlet?action=noSendticketList&createStartTime=" +(createStartTime==null?time:createStartTime)+ 

           		 "&createEndTime=" + (createEndTime==null?time:createEndTime)
            		+ "&sid=" + (sid==null?"":sid)
            		+ "&lotteryCode=" + (lotteryCode==null?"":lotteryCode)
            		+ "&ticketStatus=" + (ticketStatus==null?"":ticketStatus)
            		+ "&postCode=" + (postCode==null?"":postCode)
            		+ "&ticketId=" +  (ticketIds==null?"":ticketIds)
            		+ "&outTicketId=" + (outTicketId==null?"":outTicketId)
            		+ "&orderId=" + (orderId==null?"":orderId)
            		+ "&issue=" + (issue==null?"":issue)
            		+ "&sendStartTime=" + (sendStartTime==null?"":sendStartTime)
            		+ "&sendEndTime=" + (sendEndTime==null?"":sendEndTime);
            
            for (String ticketId : myCheckbox) {
                int rs = ticketService.doHhandTicketFailed(ticketId);
                if (rs == 0) {
                    setManagesLog(request, action, "<span style=\"color:#f00\">" + ticketId + "</span>退款", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
                    request.setAttribute("msg", "退款成功");
                } else {
                    request.setAttribute("msg", "退款失败");
                }
            }
            response.sendRedirect(url);
        }

        if (SUCCESS_CANCEL_TICKET.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            int status = ticket.getTicketStatus();
            int rs = ticketService.doHhandTicketSuccess(ticketId);
            if (status == Constants.TICKET_STATUS_SUCCESS) {
                if (rs == 0) {
                    setManagesLog(request, action, "<span style=\"color:#f00\">" + ticketId + "</span>退款", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
                    request.setAttribute("msg", "退款成功");
                } else {
                    request.setAttribute("msg", "退款失败");
                }
            } else {
                request.setAttribute("msg", "退款失败,此处只能给成功票退款");
            }
            request.getRequestDispatcher("/manages/ticketManagesServlet?action=errorTicketDetail&ticketId=" + ticketId).forward(request, response);
        }

        if (SUCCESS_CANCEL_TICKET_LIST.equals(action)) {
            String myCheckbox[] = request.getParameterValues("myCheckbox");
            String time = ElTagUtils.addDate("d", 0);
            String url = "/manages/ticketManagesServlet?action=errorTicketList&myType=dy";
            for (String ticketId : myCheckbox) {
                int rs = ticketService.doHhandTicketSuccess(ticketId);
                if (rs == 0) {
                    setManagesLog(request, action, "<span style=\"color:#f00\">" + ticketId + "</span>退款", Constants.MANAGER_LOG_BUY_ORDER_MESSAGE);
                    request.setAttribute("msg", "退款成功");
                } else {
                    request.setAttribute("msg", "退款失败");
                }
            }
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
