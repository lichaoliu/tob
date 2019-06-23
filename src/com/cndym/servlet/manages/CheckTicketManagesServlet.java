package com.cndym.servlet.manages;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cndym.bean.query.CheckTicketQueryBean;
import com.cndym.bean.tms.CheckTicket;
import com.cndym.constant.Constants;
import com.cndym.service.ICheckTicketService;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

/**
 * User: mcs Date: 12-10-30 Time: 上午11:10
 */
public class CheckTicketManagesServlet extends BaseManagesServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(getClass());

    private final static String CHECK_TICKET_LIST = "checkTicketList";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        ICheckTicketService checkTicketService = (ICheckTicketService) SpringUtils.getBean("checkTicketServiceImpl");

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (CHECK_TICKET_LIST.equals(action)) {
            String type = Utils.formatStr(request.getParameter("type"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
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

            // 投注金额是否有差异
            Integer amountDiffStatus = Utils.formatInt(request.getParameter("amountDiffStatus"), null);
            // 税前奖金是否有差异
            Integer fixBonusDiffStatus = Utils.formatInt(request.getParameter("fixBonusDiffStatus"), null);
            // 税后奖金是否有差异
            Integer bonusDiffStatus = Utils.formatInt(request.getParameter("bonusDiffStatus"), null);
            // 出票状态是否有差异
            Integer ticketDiffStatus = Utils.formatInt(request.getParameter("ticketDiffStatus"), null);

            CheckTicketQueryBean queryBean = new CheckTicketQueryBean();
            CheckTicket checkTicket = new CheckTicket();

            checkTicket.setSid(sid);
            checkTicket.setLotteryCode(lotteryCode);
            checkTicket.setIssue(issue);
            checkTicket.setOrderId(orderId);
            checkTicket.setOutTicketId(outTicketId);
            checkTicket.setTicketId(ticketId);
            checkTicket.setPostCode(postCode);

            request.setAttribute("sid", sid);
            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("orderId", orderId);
            request.setAttribute("outTicketId", outTicketId);
            request.setAttribute("ticketId", ticketId);
            request.setAttribute("postCode", postCode);

            if (Utils.isNotEmpty(ticketStatus)) {
            	checkTicket.setTicketStatus(ticketStatus);
                request.setAttribute("ticketStatus", ticketStatus);
            }
            if (Utils.isNotEmpty(bonusStatus)) {
            	checkTicket.setBonusStatus(bonusStatus);
                request.setAttribute("bonusStatus", bonusStatus);
            }
            if (Utils.isNotEmpty(bigBonus)) {
            	checkTicket.setBigBonus(bigBonus);
                request.setAttribute("bigBonus", bigBonus);
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
            }else if (Utils.isEmpty(type)) {
                queryBean.setCreateStartTime(Utils.formatDate(Utils.yesterday("yyyy-MM-dd") + " 00:00:00"));
                request.setAttribute("sendStartTime", Utils.yesterday("yyyy-MM-dd"));
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendEndTime(calendar.getTime());
                request.setAttribute("sendEndTime", sendEndTime);
            } else if (Utils.isEmpty(type)) {
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(Utils.yesterday("yyyy-MM-dd") + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setCreateEndTime(calendar.getTime());
                request.setAttribute("sendEndTime", Utils.yesterday("yyyy-MM-dd"));
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

            if (Utils.isNotEmpty(amountDiffStatus)) {
                queryBean.setAmountDiffStatus(amountDiffStatus);
                request.setAttribute("amountDiffStatus", amountDiffStatus);
            }
            if (Utils.isNotEmpty(fixBonusDiffStatus)) {
                queryBean.setFixBonusDiffStatus(fixBonusDiffStatus);
                request.setAttribute("fixBonusDiffStatus", fixBonusDiffStatus);
            }
            if (Utils.isNotEmpty(bonusDiffStatus)) {
                queryBean.setBonusDiffStatus(bonusDiffStatus);
                request.setAttribute("bonusDiffStatus", bonusDiffStatus);
            }
            if (Utils.isNotEmpty(ticketDiffStatus)) {
                queryBean.setTicketDiffStatus(ticketDiffStatus);
                request.setAttribute("ticketDiffStatus", ticketDiffStatus);
            }

            queryBean.setCheckTicket(checkTicket);

            PageBean pageBean = checkTicketService.getCheckTicketListByPara(queryBean, page, pageSize);
            Map<String, Object> dataMap = checkTicketService.getCheckTicketCount(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("orderAmountDiff"));
                bonusAmount = Utils.formatDouble(dataMap.get("bonusAmountDiff"));
                fixBonusAmount = Utils.formatDouble(dataMap.get("fixBonusAmountDiff"));
            }
            request.setAttribute("ticketNumber", ticketNumber);
            request.setAttribute("ticketAmount", ticketAmount);
            request.setAttribute("bonusAmount", bonusAmount);
            request.setAttribute("fixBonusAmount", fixBonusAmount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/ticket/checkTicketList.jsp").forward(request, response);
        }

    }
}
