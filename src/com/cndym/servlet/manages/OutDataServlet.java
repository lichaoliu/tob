package com.cndym.servlet.manages;

import com.cndym.bean.count.*;
import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.*;
import com.cndym.constant.Constants;
import com.cndym.service.*;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: 邓玉明
 * Date: 11-8-1 下午4:01
 * QQ:757579248
 */
public class OutDataServlet extends HttpServlet {

    private final String SALE_TABLE = "saleTable";
    private final String ACCOUNT_COUNT_TABLE = "accountCountTable";
    //额度调整
    private final String ACCOUNT_ADJUSTMENT = "accountAdjustment";
    //账户明细
    private final String ACCOUNT_DETAIL = "accountDetail";
    //彩票查询
    private final String TICKET_LIST = "ticketList";
    //彩票报表
    private final String LOTTERY_COUNT = "lotteryCount";

    //按期
    private final String LOTTERY_DATE_COUNT = "lotteryDateCount";
//    //按日
//    private final String DATE_COUNT = "dateCount";

    private String date = null;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        String body = null;
        date = Utils.formatDate2Str(new Date(), "yyyyMMddHHmmss");
        if (SALE_TABLE.equals(action)) {
            body = saleTable(request, response);
        }
        if (ACCOUNT_COUNT_TABLE.equals(action)) {
            body = accountCountTable(request, response);
        }
        if (ACCOUNT_DETAIL.equals(action)) {
            body = accountDetail(request, response);
        }
        if (ACCOUNT_ADJUSTMENT.equals(action)) {
            body = accountAdjustment(request, response);
        }
        if (TICKET_LIST.equals(action)) {
            body = ticketList(request, response);
        }
        if (LOTTERY_COUNT.equals(action)) {
            body = lotteryCount(request, response);
        }
        if (LOTTERY_DATE_COUNT.equals(action)) {
            body = lotteryDateCount(request, response);
        }
//        if (ISSUE_COUNT_NEW.equals(action)) {
//            body = issueCountNew(request, response);
//        }
        if (!Utils.isNotEmpty(body)) {
            return;
        }

    }


    public String accountAdjustment(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

        AccountLogMember accountLogMember = new AccountLogMember();
        AccountLog accountLog = new AccountLog();
        Member member = new Member();
        String name = Utils.formatStr(request.getParameter("name"));
        String sid = Utils.formatStr(request.getParameter("sid"));
        String startTime = Utils.formatStr(request.getParameter("startTime"));
        String orderId = Utils.formatStr(request.getParameter("orderId"));
        String endTime = Utils.formatStr(request.getParameter("endTime"));
        String eventCode = Utils.formatStr(request.getParameter("eventCode"));
        String amountc = Utils.formatStr(request.getParameter("amountc"));
        String amountd = Utils.formatStr(request.getParameter("amountd"));
        String operator = Utils.formatStr(request.getParameter("operator"));
        String backup1 = Utils.formatStr(request.getParameter("backup1"));

        if (Utils.isNotEmpty(name)) {
            member.setName(name);
        }
        if (Utils.isNotEmpty(sid)) {
            member.setSid(sid);
        }
        if (Utils.isNotEmpty(startTime)) {
            accountLogMember.setStartTime(DateUtils.formatStr2Date(startTime + " 00:00:00"));
        }
        if (Utils.isNotEmpty(endTime)) {
            Calendar calendar = Calendar.getInstance();
            Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
            calendar.setTime(endT);
            calendar.add(Calendar.DATE, 1);
            accountLogMember.setEndTime(calendar.getTime());
        }
        if (Utils.isNotEmpty(eventCode)) {
            accountLog.setEventCode(eventCode);
        }
        if (Utils.isNotEmpty(orderId)) {
            accountLog.setOrderId(orderId);
        }
        if (Utils.isNotEmpty(amountc)) {
            accountLogMember.setAmountc(Utils.formatDouble(amountc, null));
        }
        if (Utils.isNotEmpty(amountd)) {
            accountLogMember.setAmountd(Utils.formatDouble(amountd, null));
        }
        if (Utils.isNotEmpty(operator)) {
            accountLogMember.setOperator(operator);
        }
        if (Utils.isNotEmpty(backup1)) {
            accountLog.setBackup1(backup1);
        }
        accountLogMember.setMember(member);
        accountLogMember.setAccountLog(accountLog);
        accountLog.setEventCodeArr(new String[]{"00500", "10400"});
        IAccountLogService accountLogService = (IAccountLogService) SpringUtils.getBean("accountLogServiceImpl");

        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = accountLogService.getAccountLogForAdjust(accountLogMember, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return accountAdjustmentToCsv(list, request, response);
    }

    public String accountAdjustmentToCsv(List<Object[]> accountLogMemberList, HttpServletRequest request, HttpServletResponse response) {
        List<String[]> strs = new ArrayList<String[]>();
        int index = 1;
        AccountLog account = null;
        Member member = null;
        AdjustAccount adjustAccount = null;
        double bonusAmount = 0.00;
        double rechargeAmount = 0.00;
        double presentAmount = 0.00;
        double freezeAmount = 0.00;

        double bonusAmountNew = 0.00;
        double rechargeAmountNew = 0.00;
        double presentAmountNew = 0.00;
        double freezeAmountNew = 0.00;
        for (Object[] objectArray : accountLogMemberList) {
            account = (AccountLog) objectArray[0];
            member = (Member) objectArray[1];
            adjustAccount = (AdjustAccount) objectArray[2];

            String str[] = new String[16];
            str[0] = index + "";
            str[1] = member.getSid();
            str[2] = member.getName();
            str[3] = account.getOrderId();
            str[4] = Utils.formatDate2Str(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            str[5] = adjustAccount.getOperator();

            bonusAmount = Utils.isNotEmpty(account.getBonusAmount()) ? account.getBonusAmount() : 0.00;
            rechargeAmount = Utils.isNotEmpty(account.getRechargeAmount()) ? account.getRechargeAmount() : 0.00;
            presentAmount = Utils.isNotEmpty(account.getPresentAmount()) ? account.getPresentAmount() : 0.00;
            freezeAmount = Utils.isNotEmpty(account.getFreezeAmount()) ? account.getFreezeAmount() : 0.00;

            bonusAmountNew = Utils.isNotEmpty(account.getBonusAmountNew()) ? account.getBonusAmountNew() : 0.00;
            rechargeAmountNew = Utils.isNotEmpty(account.getRechargeAmountNew()) ? account.getRechargeAmountNew() : 0.00;
            presentAmountNew = Utils.isNotEmpty(account.getPresentAmountNew()) ? account.getPresentAmountNew() : 0.00;
            freezeAmountNew = Utils.isNotEmpty(account.getFreezeAmountNew()) ? account.getFreezeAmountNew() : 0.00;

            str[6] = Utils.formatNumberZ(bonusAmount + rechargeAmount + presentAmount + freezeAmount);
            str[7] = Utils.formatNumberZ(bonusAmountNew - bonusAmount);
            str[8] = Utils.formatNumberZ(bonusAmountNew);
            str[9] = Utils.formatNumberZ(rechargeAmountNew - rechargeAmount);
            str[10] = Utils.formatNumberZ(rechargeAmountNew);
            str[11] = Utils.formatNumberZ(presentAmountNew - presentAmount);
            str[12] = Utils.formatNumberZ(presentAmountNew);
            str[13] = Utils.formatNumberZ(freezeAmountNew - freezeAmount);
            str[14] = Utils.formatNumberZ(freezeAmountNew);
            str[15] = account.getMemo();
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/accountAdjustment.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/accountAdjustment" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }

    public String ticketList(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

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
        PageBean pageBean = null;
        List list = new ArrayList();
        IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
        for (int i = startPage; i <= endPage; i++) {
            pageBean = ticketService.getPageBeanByPara(queryBean, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return ticketListToCsv(list, request, response);
    }


    public String ticketListToCsv(List<Object> ticketList, HttpServletRequest request, HttpServletResponse response) {
        List<String[]> strs = new ArrayList<String[]>();
        int index = 1;
        for (Object object : ticketList) {
            Map map = (Map) object;
            String str[] = new String[20];
            str[0] = index + "";
            Object orderId = map.get("orderId");
            str[1] = Utils.isNotEmpty(orderId) ? orderId.toString() : "";
            Object ticketId = map.get("ticketId");
            str[2] = Utils.isNotEmpty(ticketId) ? ticketId.toString() : "";
            Object outTicketId = map.get("outTicketId");
            str[3] = Utils.isNotEmpty(outTicketId) ? outTicketId.toString() : "";
            Object sid = map.get("sid");
            str[4] = Utils.isNotEmpty(sid) ? sid.toString() : "";
            Object name = map.get("name");
            str[5] = Utils.isNotEmpty(name) ? name.toString() : "";
            String lotteryCode = map.get("lotteryCode").toString();
            str[6] = ElTagUtils.getLotteryChinaName(lotteryCode) + ElTagUtils.getPlayChinaName(lotteryCode, map.get("playCode").toString());
            Object issue = map.get("issue");
            str[7] = Utils.isNotEmpty(issue) ? issue.toString() : "";
            Object item = map.get("item");
            str[8] = Utils.isNotEmpty(item) ? item.toString() : "";
            Object multiple = map.get("multiple");
            str[9] = Utils.isNotEmpty(multiple) ? multiple.toString() : "";
            Object amount = map.get("amount");
            str[10] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(amount) ? amount.toString() : "0"));
            String statusName = "";
            int status = new Integer(map.get("ticketStatus").toString());
            if (0 == status) {
                statusName = "未送票";
            } else if (1 == status) {
                statusName = "调度中";
            } else if (2 == status) {
                statusName = "送票未回执";
            } else if (3 == status) {
                statusName = "出票成功";
            } else if (4 == status || 5 == status) {
                statusName = "出票失败" + "(" + map.get("errCode") + "| " + map.get("errMsg") + ")";
            } else if (6 == status) {
                statusName = "重发" + "(" + map.get("errCode") + "| " + map.get("errMsg") + ")";
            }
            str[11] = statusName;
            String bonusStatusName = "";
            int bonusStatus = new Integer(map.get("bonusStatus").toString());
            if (3 == status) {
                bonusStatusName = "等待开奖";
                if (1 == bonusStatus) {
                    bonusStatusName = "中奖";
                } else if (2 == bonusStatus) {
                    bonusStatusName = "未中奖";
                }
            }
            str[12] = bonusStatusName;
            Object fixBonusAmount = map.get("fixBonusAmount");
            str[13] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(fixBonusAmount) ? fixBonusAmount.toString() : "0"));
            Object bonusAmount = map.get("bonusAmount");
            str[14] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(bonusAmount) ? bonusAmount.toString() : "0"));
            str[15] = map.get("createTime").toString();
            Object sendTime = map.get("sendTime");
            str[16] = Utils.isNotEmpty(sendTime) ? sendTime.toString() : "";
            Object returnTime = map.get("returnTime");
            str[17] = Utils.isNotEmpty(returnTime) ? returnTime.toString() : "";
            Object bonusTime = map.get("bonusTime");
            str[18] = Utils.isNotEmpty(bonusTime) ? bonusTime.toString() : "";
            str[19] = ElTagUtils.getPostCodeName(map.get("postCode").toString());
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/ticketList.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/ticketList" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }

    public String lotteryCount(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

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
        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = ticketService.getPageBeanByParaLottery(ticketQueryBean);
            list.addAll(pageBean.getPageContent());
        }
        return lotteryCountToCsv(list, request, response);
    }

    public String lotteryCountToCsv(List<Object> ticketList, HttpServletRequest request, HttpServletResponse response) {
        List<String[]> strs = new ArrayList<String[]>();
        int index = 1;
        for (Object object : ticketList) {
            Map map = (Map) object;
            String str[] = new String[5];
            str[0] = index + "";
            Object lotteryCode = map.get("lotteryCode");
            str[1] = Utils.isNotEmpty(lotteryCode) ? ElTagUtils.getLotteryChinaName(lotteryCode.toString()) : "";
            Object amount = map.get("amount");
            str[2] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(amount) ? amount.toString() : "0"));
            Object fixBonusAmount = map.get("fixBonusAmount");
            str[3] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(fixBonusAmount) ? fixBonusAmount.toString() : "0"));
            Object bonusAmount = map.get("bonusAmount");
            str[4] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(bonusAmount) ? bonusAmount.toString() : "0"));
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/lotteryCount.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/lotteryCount" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }


    public String lotteryDateCount(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);
        String startIssue = Utils.formatStr(request.getParameter("startIssue"));
        String endIssue = Utils.formatStr(request.getParameter("endIssue"));
        String sid = Utils.formatStr(request.getParameter("name"));
        String postCode = Utils.formatStr(request.getParameter("postCode"));
        String ticketStatus = Utils.formatStr(request.getParameter("ticketStatus"));
        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        String type = Utils.formatStr(request.getParameter("type"));
        TicketQueryBean ticketQueryBean = new TicketQueryBean();
        Ticket ticket = new Ticket();
        if ("1".equals(type)) {
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
        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = ticketService.getDateOrIssueCount(ticketQueryBean, type, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return lotteryDateCountToCsv(list, type, request, response);
    }

    public String lotteryDateCountToCsv(List<Object> ticketList, String type, HttpServletRequest request, HttpServletResponse response) {
        List<String[]> strs = new ArrayList<String[]>();
        int index = 1;
        for (Object object : ticketList) {
            Map map = (Map) object;
            String str[] = new String[5];
            str[0] = index + "";
            if ("1".equals(type)) {
                Object time = map.get("time");
                str[1] = Utils.isNotEmpty(time) ? time.toString() : "";
            } else {
                Object issue = map.get("issue");
                str[1] = Utils.isNotEmpty(issue) ? issue.toString() : "";
            }
            Object amount = map.get("amount");
            str[2] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(amount) ? amount.toString() : "0"));
            Object fixBonusAmount = map.get("fixBonusAmount");
            str[3] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(fixBonusAmount) ? fixBonusAmount.toString() : "0"));
            Object bonusAmount = map.get("bonusAmount");
            str[4] = Utils.formatNumberZ(new Double(Utils.isNotEmpty(bonusAmount) ? bonusAmount.toString() : "0"));
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/lotteryDateCount.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/lotteryDateCount" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }


    public String accountDetail(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

        AccountLogMember accountLogMember = new AccountLogMember();
        AccountLog accountLog = new AccountLog();
        Member member = new Member();
        String sid = Utils.formatStr(request.getParameter("sid"));
        String name = Utils.formatStr(request.getParameter("name"));
        String startTime = Utils.formatStr(request.getParameter("startTime"));
        String orderId = Utils.formatStr(request.getParameter("orderId"));
        String endTime = Utils.formatStr(request.getParameter("endTime"));
        String eventCode = Utils.formatStr(request.getParameter("eventCode"));
        String fsCode = Utils.formatStr(request.getParameter("fsCode"));
        String amountc = Utils.formatStr(request.getParameter("amountc"));
        String amountd = Utils.formatStr(request.getParameter("amountd"));
        String eventCodeArr = Utils.formatStr(request.getParameter("eventCodeArr"));
        if (Utils.isNotEmpty(name)) {
            member.setName(name);
        }
        if (Utils.isNotEmpty(sid)) {
            member.setSid(sid);
        }
        if (Utils.isNotEmpty(startTime)) {
            accountLogMember.setStartTime(DateUtils.formatStr2Date(startTime + " 00:00:00"));
        }
        if (Utils.isNotEmpty(endTime)) {
            Calendar calendar = Calendar.getInstance();
            Date endT = DateUtils.formatStr2Date(endTime + " 00:00:00");
            calendar.setTime(endT);
            calendar.add(Calendar.DATE, 1);
            accountLogMember.setEndTime(calendar.getTime());
        }
        if (Utils.isNotEmpty(eventCode)) {
            accountLog.setEventCode(eventCode);
        }
        if (Utils.isNotEmpty(eventCodeArr)) {
            accountLog.setEventCodeArr(eventCodeArr.split(","));
        }
        if (Utils.isNotEmpty(orderId)) {
            accountLog.setOrderId(orderId);
        }
        if (Utils.isNotEmpty(fsCode)) {
            String msg[] = fsCode.split("-");
            accountLog.setEventType(new Integer(msg[0]));
            accountLog.setType(msg[1]);
        }
        if (Utils.isNotEmpty(amountc)) {
            accountLogMember.setAmountc(Utils.formatDouble(amountc, null));
        }
        if (Utils.isNotEmpty(amountd)) {
            accountLogMember.setAmountd(Utils.formatDouble(amountd, null));
        }
        accountLogMember.setMember(member);
        accountLogMember.setAccountLog(accountLog);
        IAccountLogService accountLogService = (IAccountLogService) SpringUtils.getBean("accountLogServiceImpl");

        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = accountLogService.getAccountLogMemberList(accountLogMember, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return accountDetailToCsv(list, request, response);
    }

    public String accountDetailToCsv(List<Object[]> accountLogMemberList, HttpServletRequest request, HttpServletResponse response) {
        List<String[]> strs = new ArrayList<String[]>();
        AccountLog account = null;
        Member member = null;
        double bonusAmount = 0;
        double rechargeAmount = 0;
        double presentAmount = 0;
        double freezeAmount = 0;

        double bonusAmountNew = 0;
        double rechargeAmountNew = 0;
        double presentAmountNew = 0;
        double freezeAmountNew = 0;
        int index = 1;
        for (Object[] objectArray : accountLogMemberList) {
            account = (AccountLog) objectArray[0];
            member = (Member) objectArray[1];
            String str[] = new String[16];
            str[0] = index + "";
            str[1] = member.getSid();
            str[2] = member.getName();
            str[3] = account.getOrderId();
            str[4] = Utils.formatDate2Str(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            str[5] = ElTagUtils.getEventCodeType(account.getEventCode());

            bonusAmount = Utils.isNotEmpty(account.getBonusAmount()) ? account.getBonusAmount() : 0.00;
            rechargeAmount = Utils.isNotEmpty(account.getRechargeAmount()) ? account.getRechargeAmount() : 0.00;
            presentAmount = Utils.isNotEmpty(account.getPresentAmount()) ? account.getPresentAmount() : 0.00;
            freezeAmount = Utils.isNotEmpty(account.getFreezeAmount()) ? account.getFreezeAmount() : 0.00;

            bonusAmountNew = Utils.isNotEmpty(account.getBonusAmountNew()) ? account.getBonusAmountNew() : 0.00;
            rechargeAmountNew = Utils.isNotEmpty(account.getRechargeAmountNew()) ? account.getRechargeAmountNew() : 0.00;
            presentAmountNew = Utils.isNotEmpty(account.getPresentAmountNew()) ? account.getPresentAmountNew() : 0.00;
            freezeAmountNew = Utils.isNotEmpty(account.getFreezeAmountNew()) ? account.getFreezeAmountNew() : 0.00;

            str[6] = Utils.formatNumberZ(bonusAmount + rechargeAmount + presentAmount + freezeAmount);
            str[7] = Utils.formatNumberZ(bonusAmountNew - bonusAmount);
            str[8] = Utils.formatNumberZ(bonusAmountNew);
            str[9] = Utils.formatNumberZ(rechargeAmountNew - rechargeAmount);
            str[10] = Utils.formatNumberZ(rechargeAmountNew);
            str[11] = Utils.formatNumberZ(presentAmountNew - presentAmount);
            str[12] = Utils.formatNumberZ(presentAmountNew);
            str[13] = Utils.formatNumberZ(freezeAmountNew - freezeAmount);
            str[14] = Utils.formatNumberZ(freezeAmountNew);
            str[15] = account.getMemo();
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/accountDetailList.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/accountDetailList" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }


    public String saleTable(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

        ISaleTableService saleTableService = (ISaleTableService) SpringUtils.getBean("saleTableServiceImpl");
        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        String issue = Utils.formatStr(request.getParameter("issue"));
        int page = Utils.formatInt(request.getParameter("page"), 1);
        SaleTable saleTable = new SaleTable();
        saleTable.setLotteryCode(Utils.formatStr(lotteryCode));
        saleTable.setIssue(Utils.formatStr(issue));

        PageBean pageBean = null;
        List saleTables = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = saleTableService.getPageBeanListByPara(saleTable, i, Constants.PAGE_SIZE_50);
            saleTables.addAll(pageBean.getPageContent());
        }
        return saleTableToCvs(saleTables, request, response);
    }


    public String saleTableToCvs(List<SaleTable> saleTables, HttpServletRequest request, HttpServletResponse response) {
        List<String[]> strs = new ArrayList<String[]>();
        int index = 1;
        for (SaleTable saleTable : saleTables) {
            String str[] = new String[14];
            str[0] = index + "";
            str[1] = ElTagUtils.getLotteryChinaName(saleTable.getLotteryCode() == "197" ? "107" : saleTable.getLotteryCode());
            str[2] = saleTable.getIssue();
            str[3] = saleTable.getSid();
            str[4] = saleTable.getName();
            str[5] = Utils.formatDate2Str(saleTable.getStartTime(), "yyyy-MM-dd HH:mm:ss");
            str[6] = Utils.formatDate2Str(saleTable.getEndTime(), "yyyy-MM-dd HH:mm:ss");
            str[7] = Utils.formatNumberZ(Utils.isNotEmpty(saleTable.getSuccessAmount()) ? saleTable.getSuccessAmount() : 0);
            str[8] = (saleTable.getSuccessTicket() == null ? 0 : saleTable.getSuccessTicket()) + "";
            str[9] = Utils.formatNumberZ(Utils.isNotEmpty(saleTable.getFailureAmount()) ? saleTable.getFailureAmount() : 0);
            str[10] = (saleTable.getFailureTicket() == null ? 0 : saleTable.getFailureTicket()) + "";
            str[11] = Utils.formatNumberZ(Utils.isNotEmpty(saleTable.getBonusAmount()) ? saleTable.getBonusAmount() : 0);
            str[12] = (saleTable.getBonusTicket() == null ? 0 : saleTable.getBonusTicket()) + "";
            str[13] = Utils.formatDate2Str(saleTable.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/saleTable.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/saleTable" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }


    public String accountCountTable(HttpServletRequest request, HttpServletResponse response) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

        String sid = Utils.formatStr(request.getParameter("sid"));
        String startDate = Utils.formatStr(request.getParameter("startDate"));
        String endDate = Utils.formatStr(request.getParameter("endDate"));
        int page = Utils.formatInt(request.getParameter("page"), 1);
        IAccountTableService accountTableService = (IAccountTableService) SpringUtils.getBean("accountTableServiceImpl");
        AccountTable accountTable = new AccountTable();
        if (Utils.isNotEmpty(startDate)) {
            accountTable.setStartDate(startDate + " 00:00:00");

        }
        if (Utils.isNotEmpty(endDate)) {
            Calendar calendar = Calendar.getInstance();
            Date endT = DateUtils.formatStr2Date(endDate + " 00:00:00");
            calendar.setTime(endT);
            calendar.add(Calendar.DATE, 1);
            accountTable.setEndDate(DateUtils.formatDate2Str(calendar.getTime(), "yyyy-MM-dd hh:mm:ss"));
        }

        if (Utils.isNotEmpty(sid)) {
            accountTable.setSid(sid);
        } else {
            accountTable.setSid("false");
        }

        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = accountTableService.getPageBeanByPara(accountTable, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return accountTableToCvs(list, request, response);
    }

    public String accountTableToCvs(List<AccountTable> accountTableList, HttpServletRequest request, HttpServletResponse response) {
        int index = 1;
        List<String[]> strs = new ArrayList<String[]>();
        for (AccountTable accountTable : accountTableList) {
            String str[] = new String[12];
            str[0] = index + "";
            str[1] = accountTable.getCurrDate();
            str[2] = Utils.formatNumberZ((accountTable.getBonusAmount() == null ? 0.00 : accountTable.getBonusAmount()) + (accountTable.getCommissionAmount() == null ? 0.00 : accountTable.getCommissionAmount()));
            str[3] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getEditAccountJia()) ? accountTable.getEditAccountJia() : 0);
            str[4] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getCommissionAmount()) ? accountTable.getCommissionAmount() : 0);
            str[5] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getFailureAmount()) ? accountTable.getFailureAmount() : 0);
            str[6] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getPayAmount()) ? accountTable.getPayAmount() : 0);
            str[7] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getEditAccountJian()) ? accountTable.getEditAccountJian() : 0);
            str[8] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getRechargeAmountNew()) ? accountTable.getRechargeAmountNew() : 0);
            str[9] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getBonusAmountNew()) ? accountTable.getBonusAmountNew() : 0);
            str[10] = Utils.formatNumberZ(Utils.isNotEmpty(accountTable.getPresentAmountNew()) ? accountTable.getPresentAmountNew() : 0);
            str[11] = Utils.formatDate2Str(accountTable.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            index++;
            strs.add(str);
        }
        String tempPath = request.getRealPath("/manages") + "/xls/accountDetail.xls";
        String filePath = request.getRealPath("/manages") + "/xls/xlsFiles/accountDetail" + date + ".xls";
        return toXls(strs, tempPath, filePath, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    //下载xls调用
    public String toXls(List list, String tempPath, String filePath, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("root", list);
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        XLSTransformer transformer = new XLSTransformer();
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            transformer.transformXLS(tempPath, map, filePath);
            fis = new FileInputStream(filePath);
            os = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/x-download");
            byte[] mybyte = new byte[1024];
            int len = 0;
            while ((len = fis.read(mybyte)) != -1) {
                os.write(mybyte, 0, len);
            }
            File tempDir = new File(path);
            if (tempDir.exists() && tempDir.isDirectory()) {
                if (tempDir.listFiles().length > 0) {
                    File[] files = tempDir.listFiles();
                    for (int i = 0; i < tempDir.listFiles().length; i++) {
                        if (files[i].isFile()) {
                            files[i].delete();
                        }
                    }
                }
            }
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
