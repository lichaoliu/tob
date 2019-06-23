package com.cndym.servlet.manages;

import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.sys.Manages;
import com.cndym.bean.user.Account;
import com.cndym.bean.user.AccountLog;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.exception.ErrCode;
import com.cndym.service.IAccountLogService;
import com.cndym.service.IAccountService;
import com.cndym.service.IAdjustAccountService;
import com.cndym.service.IMemberService;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * *USER:MengJingyi
 * *TIME:2011 2011-4-30 下午01:52:05
 */

public class AccountManagesServlet extends BaseManagesServlet {
    private final static String ACCOUNT_BY_SID = "getAccountBySid";
    private final static String UPDATE_ACCOUNT = "updateAccount";
    private final static String ACCOUNT_LOG_LIST = "getAccountLogList";
    private final static String ADJUSTMENT_LIST = "getAdjustmentList";
    private final static String ADJUST_DETAILS = "getAdjustDetails";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    private Logger logger = Logger.getLogger(getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IAccountService accountService = (IAccountService) SpringUtils.getBean("accountServiceImpl");
        IAccountLogService accountLogService = (IAccountLogService) SpringUtils.getBean("accountLogServiceImpl");
        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");
        IAdjustAccountService adjustAccountService = (IAdjustAccountService) SpringUtils.getBean("adjustAccountServiceImpl");
        String action = Utils.formatStr(request.getParameter("action"));

        Manages manages = (Manages) request.getSession().getAttribute("adminUser");
        String adminName = "";
        if (Utils.isNotEmpty(manages)) {
            adminName = ((Manages) request.getSession().getAttribute("adminUser")).getUserName();
        }

        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);

        if (ACCOUNT_BY_SID.equals(action)) {
            String sid = Utils.formatStr(request.getParameter("sid"));
            if (Utils.isEmpty(sid)) {
                response.sendRedirect("/manages/account/lastAdjustment.jsp");
                return;
            }
            Member member = memberService.getMemberBySid(sid);
            if (null == member) {
                response.sendRedirect("/manages/account/lastAdjustment.jsp");
                return;
            }
            Account account = accountService.getAccountBySid(sid);
            request.setAttribute("account", account);
            request.setAttribute("member", member);
            request.getRequestDispatcher("/manages/account/updateAdjustment.jsp").forward(request, response);
            return;
        } else if (UPDATE_ACCOUNT.equals(action)) {
            String tag = "-";
            String userCode = Utils.formatStr(request.getParameter("userCode"));
            String sid = Utils.formatStr(request.getParameter("sid"));
            String type = Utils.formatStr(request.getParameter("type"));
            String operator = Utils.formatStr(request.getParameter("operator"));
            double amount = Utils.formatDouble(request.getParameter("amount"), 0d);
            String body = Utils.formatStr(request.getParameter("body"));
            if (tag.equals(operator)) {
                amount = amount * -1;
            }
            try {
                int i = adjustAccountService.doAddAdjust(sid, userCode, amount, type, body, adminName);
                if (i > 0) {
                    setManagesLog(request, action, sid + "商户，额度调整<span style=\"color:#f00\">" + amount + "</span>元", Constants.MANAGER_LOG_USER_MESSAGE);
                }
                response.sendRedirect("/manages/accountManagesServlet?action=getAdjustmentList");
                return;
            } catch (Exception e) {
                Member member = memberService.getMemberBySid(sid);
                Account account = accountService.getAccountBySid(sid);
                request.setAttribute("account", account);
                request.setAttribute("member", member);
                request.setAttribute("error", ErrCode.codeToMsg(e.getMessage()));
                request.getRequestDispatcher("/manages/account/updateAdjustment.jsp").forward(request, response);
                return;
            }
        } else if (ACCOUNT_LOG_LIST.equals(action)) {
            String type = Utils.formatStr(request.getParameter("type"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            if ("dy".equals(type)) {
                request.setAttribute("startTime", startTime);
                request.setAttribute("endTime", endTime);
                request.getRequestDispatcher("/manages/account/accountDetails.jsp").forward(request, response);
                return;
            }

            AccountLogMember accountLogMember = new AccountLogMember();
            AccountLog accountLog = new AccountLog();
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String eventCode = Utils.formatStr(request.getParameter("eventCode"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String amountc = Utils.formatStr(request.getParameter("amountc"));
            String amountd = Utils.formatStr(request.getParameter("amountd"));

            if (Utils.isNotEmpty(startTime)) {
                request.setAttribute("startTime", startTime);
                accountLogMember.setStartTime(DateUtils.formatStr2Date(startTime + " 00:00:00"));
            }
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                accountLogMember.setEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(eventCode)) {
                accountLog.setEventCode(eventCode);
                request.setAttribute("eventCode", eventCode);
            }
            if (Utils.isNotEmpty(orderId)) {
                accountLog.setOrderId(orderId);
                request.setAttribute("orderId", orderId);
            }
            if (Utils.isNotEmpty(ticketId)) {
                accountLog.setOrderId(ticketId);
                request.setAttribute("ticketId", ticketId);
            }
            if (Utils.isNotEmpty(amountc)) {
                accountLogMember.setAmountc(Utils.formatDouble(amountc, null));
                request.setAttribute("amountc", amountc);
            }
            if (Utils.isNotEmpty(amountd)) {
                accountLogMember.setAmountd(Utils.formatDouble(amountd, null));
                request.setAttribute("amountd", amountd);
            }
            if (Utils.isNotEmpty(sid)) {
                accountLogMember.setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(name)) {
                accountLogMember.setName(name);
                request.setAttribute("name", name);
            }
            accountLogMember.setAccountLog(accountLog);
            PageBean pageBean = accountLogService.getAccountLogMemberList(accountLogMember, page, pageSize);
            List list = accountLogService.getAccountLogAmountCount(accountLogMember);
            Map<String, Double> map = (HashMap<String, Double>) list.get(0);
            Object bonusAmountObj = map.get("bonusAmount");
            Object freezeAmountObj = map.get("freezeAmount");
            Object presentAmountObj = map.get("presentAmount");
            Object rechargeAmountObj = map.get("rechargeAmount");
            Double amountCount = Math.abs(Utils.formatDouble(bonusAmountObj))
                    + Math.abs(Utils.formatDouble(freezeAmountObj))
                    + Math.abs(Utils.formatDouble(presentAmountObj))
                    + Math.abs(Utils.formatDouble(rechargeAmountObj));

            request.setAttribute("amountCount", amountCount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/manages/account/accountDetails.jsp").forward(request, response);
            return;
        } else if (ADJUSTMENT_LIST.equals(action)) {
            AccountLogMember accountLogMember = new AccountLogMember();
            AccountLog accountLog = new AccountLog();
            Member member = new Member();
            String sid = Utils.formatStr(request.getParameter("sid"));
            String name = Utils.formatStr(request.getParameter("name"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String eventCode = Utils.formatStr(request.getParameter("eventCode"));
            String amountc = Utils.formatStr(request.getParameter("amountc"));
            String amountd = Utils.formatStr(request.getParameter("amountd"));
            String operator = Utils.formatStr(request.getParameter("operator"));
            String backup1 = Utils.formatStr(request.getParameter("backup1"));

            if (Utils.isNotEmpty(sid)) {
                member.setSid(sid);
                request.setAttribute("sid", sid);
            }
            if (Utils.isNotEmpty(name)) {
                member.setName(name);
                request.setAttribute("name", name);
            }
            if (Utils.isNotEmpty(startTime)) {
                request.setAttribute("startTime", startTime);
                accountLogMember.setStartTime(DateUtils.formatStr2Date(startTime + " 00:00:00"));
            }
            if (Utils.isNotEmpty(endTime)) {
                request.setAttribute("endTime", endTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                accountLogMember.setEndTime(calendar.getTime());
            }
            if (Utils.isNotEmpty(eventCode)) {
                accountLog.setEventCode(eventCode);
                request.setAttribute("eventCode", eventCode);
            }
            if (Utils.isNotEmpty(orderId)) {
                accountLog.setOrderId(orderId);
                request.setAttribute("orderId", orderId);
            }
            if (Utils.isNotEmpty(amountc)) {
                accountLogMember.setAmountc(Utils.formatDouble(amountc, null));
                request.setAttribute("amountc", amountc);
            }
            if (Utils.isNotEmpty(amountd)) {
                accountLogMember.setAmountd(Utils.formatDouble(amountd, null));
                request.setAttribute("amountd", amountd);
            }
            if (Utils.isNotEmpty(operator)) {
                accountLogMember.setOperator(operator);
                request.setAttribute("operator", operator);
            }
            if (Utils.isNotEmpty(backup1)) {
                accountLog.setBackup1(backup1);
                request.setAttribute("backup1", backup1);
            }
            accountLogMember.setMember(member);
            accountLogMember.setAccountLog(accountLog);
            accountLog.setEventCodeArr(new String[]{"00500", "10400"});
            PageBean pageBean = accountLogService.getAccountLogForAdjust(accountLogMember, page, pageSize);
            List list = accountLogService.getAccountLogAmountCountForAdjust(accountLogMember);
            Map map = (HashMap) list.get(0);
            Object bonusAmountObj = map.get("bonusAmount");
            Object freezeAmountObj = map.get("freezeAmount");
            Object presentAmountObj = map.get("presentAmount");
            Object rechargeAmountObj = map.get("rechargeAmount");
            Double amountCount = Math.abs(new Double(Utils.isNotEmpty(bonusAmountObj) ? bonusAmountObj.toString() : "0"))
                    + Math.abs(new Double(Utils.isNotEmpty(freezeAmountObj) ? freezeAmountObj.toString() : "0"))
                    + Math.abs(new Double(Utils.isNotEmpty(presentAmountObj) ? presentAmountObj.toString() : "0"))
                    + Math.abs(new Double(Utils.isNotEmpty(rechargeAmountObj) ? rechargeAmountObj.toString() : "0"));
            request.setAttribute("amountCount", amountCount);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("account/getAdjustement.jsp").forward(request, response);
            return;
        } else if (ADJUST_DETAILS.equals(action)) {
            String adjustId = Utils.formatStr(request.getParameter("adjustId"));
            PageBean pageBean = adjustAccountService.getAdjustAccountByAdjustId(adjustId);
            request.setAttribute("list", pageBean.getPageContent().get(0));
            request.getRequestDispatcher("account/getAdjustementDetails.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

}
