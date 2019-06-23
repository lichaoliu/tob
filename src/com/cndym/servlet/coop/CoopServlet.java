package com.cndym.servlet.coop;

import com.cndym.bean.count.SaleTable;
import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.sys.Manages;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.bean.tms.MainIssue;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.Account;
import com.cndym.bean.user.AccountLog;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.service.*;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.servlet.manages.BaseManagesServlet;
import com.cndym.utils.DateUtils;
import com.cndym.utils.Md5;
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
 * Created by IntelliJ IDEA.
 * User: MengJingyi
 * QQ: 116741034
 * Date: 12-11-2
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 */
public class CoopServlet extends BaseManagesServlet {
    private final static String ACCOUNT_LOG_LIST = "getAccountLogList";
    private final static String TICKET_LIST = "ticketList";
    private final static String TICKET_DETAIL = "ticketDetail";
    private final static String UPDATE_MANAGES = "updateManages";
    private final static String DETAIL_MEMBER = "detailMember";
    private final static String ISSUE_ORDER_LIST = "issueOrderList";
    //派奖查询
    private final String HAND_WORK_BONUS_QUERY = "handworkBonusQuery";
    //中奖查询
    private final String BONUS_LIST = "bonusList";

    //接入商退出系统
    private final String EXIT = "exit";
    private final String SALE_TABLE = "saleTable";


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doPost(request, response);
    }

    private Logger logger = Logger.getLogger(getClass());

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        IAccountLogService accountLogService = (IAccountLogService) SpringUtils.getBean("accountLogServiceImpl");
        ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
        IBackTicketService backTicketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
        IMemberService memberService = (IMemberService) SpringUtils.getBean("memberServiceImpl");

        String action = Utils.formatStr(request.getParameter("action"));


        Integer page = Utils.formatInt(request.getParameter("page"), 1);
        Integer pageSize = Utils.formatInt(request.getParameter("pageSize"), Constants.PAGE_SIZE_50);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        Manages coopUser = (Manages) request.getSession().getAttribute("coopUser");
        String sid = coopUser.getUserName();

        if (ACCOUNT_LOG_LIST.equals(action)) {
            AccountLogMember accountLogMember = new AccountLogMember();
            AccountLog accountLog = new AccountLog();

            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String eventCode = Utils.formatStr(request.getParameter("eventCode"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            String amountc = Utils.formatStr(request.getParameter("amountc"));
            String amountd = Utils.formatStr(request.getParameter("amountd"));

            accountLogMember.setSid(sid);
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
            request.getRequestDispatcher("/coop/account/accountDetails.jsp").forward(request, response);
            return;
        } else if (TICKET_LIST.equals(action)) {
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
            String orderId = Utils.formatStr(request.getParameter("orderId"));
            String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Integer ticketStatus = Utils.formatInt(request.getParameter("ticketStatus"), null);
            Integer bonusStatus = Utils.formatInt(request.getParameter("bonusStatus"), null);
            String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
            String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
            String sendStartTime = Utils.formatStr(request.getParameter("sendStartTime"));
            String sendEndTime = Utils.formatStr(request.getParameter("sendEndTime"));

            //彩期
            Integer issueStatus = Utils.formatInt(request.getParameter("issueStatus"), null);

            TicketQueryBean queryBean = new TicketQueryBean();
            Ticket ticket = new Ticket();


            ticket.setLotteryCode(lotteryCode);
            ticket.setIssue(issue);
            ticket.setOrderId(orderId);
            ticket.setOutTicketId(outTicketId);
            ticket.setTicketId(ticketId);


            request.setAttribute("lotteryCode", lotteryCode);
            request.setAttribute("issue", issue);
            request.setAttribute("orderId", orderId);
            request.setAttribute("outTicketId", outTicketId);
            request.setAttribute("ticketId", ticketId);

            ticket.setSid(sid);
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
                queryBean.setSendStartTime(Utils.formatDate(sendStartTime + " 00:00:00"));
                request.setAttribute("sendStartTime", sendStartTime);
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                request.setAttribute("sendEndTime", sendEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendEndTime(calendar.getTime());
            }

            if (Utils.isNotEmpty(issueStatus)) {
                queryBean.setIssueStatus(issueStatus);
                request.setAttribute("issueStatus", issueStatus);
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
            request.getRequestDispatcher("/coop/ticket/ticketList.jsp").forward(request, response);
        } else if (TICKET_DETAIL.equals(action)) {
            String ticketId = Utils.formatStr(request.getParameter("ticketId"));
            Ticket ticket = ticketService.getTicketByTicketId(ticketId);
            IMainIssueService mainIssueService = (IMainIssueService) SpringUtils.getBean("mainIssueServiceImpl");
            MainIssue mainIssue = mainIssueService.getMainIssueByLotteryIssue(ticket.getLotteryCode(), ticket.getIssue());

            request.setAttribute("mainIssue", mainIssue);
            request.setAttribute("ticket", ticket);
            request.getRequestDispatcher("/coop/ticket/ticketDetail.jsp").forward(request, response);
        } else if (UPDATE_MANAGES.equals(action)) {
            IManagesService managesService = (IManagesService) SpringUtils.getBean("managesServiceImpl");

            String password = Utils.formatStr(request.getParameter("password"));
            String oldPassword = Utils.formatStr(request.getParameter("oldPassword"));
            Manages manages = new Manages();
            manages.setId(coopUser.getId());
            if (Utils.isNotEmpty(password)) {
                manages.setPassWord(Md5.Md5(password));
            }
            if (oldPassword.equals(password)) {
                request.setAttribute("msg", "原密码与新密码一样");
                request.getRequestDispatcher("/coop/member/updateAdminPassword.jsp").forward(request, response);
                return;
            }
            if (Utils.isNotEmpty(oldPassword)) {
                if (!coopUser.getPassWord().equals(Md5.Md5(oldPassword))) {
                    request.setAttribute("msg", "原密码错误");
                    request.getRequestDispatcher("/coop/member/updateAdminPassword.jsp").forward(request, response);
                    return;
                }
            }
            int count = managesService.updateAdmin(manages);
            String description = "";

            if (count > 0) {
                request.setAttribute("msg", "接入商修改密码成功");
                description = "接入商<span style=\"color:#f00\">" + sid + "</span>修改密码";

            } else {
                request.setAttribute("msg", "接入商修改密码失败");
                description = "修改失败";
            }
            setManagesLog(request, action, description, Constants.MANAGER_LOG_SYS_MESSAGE);
            PageBean pageBean = managesService.getAdminList(manages, 1, Constants.PAGE_SIZE_50);
            request.getSession().setAttribute("adminUser", pageBean.getPageContent().get(0));
            if (Utils.isNotEmpty(oldPassword)) {
                request.getRequestDispatcher("/coop/member/updateAdminPassword.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/coop/updateManages.jsp").forward(request, response);

            return;
        } else if (DETAIL_MEMBER.equals(action)) {
            IAccountService accountService = (IAccountService) SpringUtils.getBean("accountServiceImpl");
            Member member = memberService.getMemberBySid(sid);
            Account account = accountService.getAccountBySid(sid);
            request.setAttribute("member", member);
            request.setAttribute("account", account);
            request.getRequestDispatcher("/coop/member/detailMember.jsp").forward(request, response);
        } else if (ISSUE_ORDER_LIST.equals(action)) {
            ISaleTableService saleTableService = (ISaleTableService) SpringUtils.getBean("saleTableServiceImpl");
            String issue = Utils.formatStr(request.getParameter("issue"));
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String startTime = Utils.formatStr(request.getParameter("startTime"));
            String endTime = Utils.formatStr(request.getParameter("endTime"));
            String cbonusAmount = Utils.formatStr(request.getParameter("cbonusAmount"));
            String dbonusAmount = Utils.formatStr(request.getParameter("dbonusAmount"));
            SaleTable saleTable = new SaleTable();
            saleTable.setSid(sid);
            if (Utils.isNotEmpty(issue)) {
                saleTable.setIssue(issue);
                request.setAttribute("issue", issue);
            }
            if (Utils.isNotEmpty(cbonusAmount)) {
                saleTable.setCbonusAmount(new Double(cbonusAmount));
                request.setAttribute("cbonusAmount", cbonusAmount);
            }
            if (Utils.isNotEmpty(dbonusAmount)) {
                saleTable.setDbonusAmount(new Double(dbonusAmount));
                request.setAttribute("dbonusAmount", dbonusAmount);
            }
            if (Utils.isNotEmpty(lotteryCode)) {
                saleTable.setLotteryCode(lotteryCode);
                request.setAttribute("lotteryCode", lotteryCode);
            }
            if (Utils.isNotEmpty(startTime)) {
                saleTable.setStartTime(Utils.formatDate(startTime + " 00:00:00"));
                request.setAttribute("startTime", startTime);
            }
            if (Utils.isNotEmpty(endTime)) {
                saleTable.setEndTime(Utils.formatDate(endTime + " 00:00:00"));
                request.setAttribute("endTime", endTime);
            }

            PageBean pageBean = saleTableService.getPageBeanListByPara(saleTable, page, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.getRequestDispatcher("/coop/account/accountIssueList.jsp").forward(request, response);
        } else if (BONUS_LIST.equals(action)) {
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
                queryBean.setCreateEndTime(calendar.getTime());
            }


            if (Utils.isNotEmpty(sendStartTime)) {
                queryBean.setSendStartTime(Utils.formatDate(sendStartTime + " 00:00:00"));
                request.setAttribute("sendStartTime", sendStartTime);
            }

            if (Utils.isNotEmpty(sendEndTime)) {
                request.setAttribute("sendEndTime", sendEndTime);
                Calendar calendar = Calendar.getInstance();
                Date endT = DateUtils.formatStr2Date(sendEndTime + " 00:00:00");
                calendar.setTime(endT);
                calendar.add(Calendar.DATE, 1);
                queryBean.setSendEndTime(calendar.getTime());
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
            PageBean pageBean = backTicketService.getPageBeanByPara(queryBean, page, pageSize);
            Map<String, Object> dataMap = backTicketService.getTicketCount(queryBean);
            Integer ticketNumber = 0;
            Double ticketAmount = 0d;
            Double bonusAmount = 0d;
            Double fixBonusAmount = 0d;
            Double orderAmount = 0d;
            if (Utils.isNotEmpty(dataMap)) {
                ticketNumber = Utils.formatInt(dataMap.get("ticketNum") + "", 0);
                ticketAmount = Utils.formatDouble(dataMap.get("orderAmount"));
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
            request.getRequestDispatcher("/coop/win/bonusList.jsp").forward(request, response);
            return;
        } else if (HAND_WORK_BONUS_QUERY.equals(action)) {
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
            request.getRequestDispatcher("/coop/win/handworkBonusList.jsp").forward(request, response);
            return;
        }

        if (EXIT.equals(action)) {
            request.getSession().removeAttribute("coopUser");
            response.sendRedirect("/coop/login.jsp");
            return;
        }

        if (SALE_TABLE.equals(action)) {
            ISaleTableService saleTableService = (ISaleTableService) SpringUtils.getBean("saleTableServiceImpl");
            String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
            String issue = Utils.formatStr(request.getParameter("issue"));
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
            request.getRequestDispatcher("/coop/account/accountIssueList.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/coop/error.jsp");
    }


}
