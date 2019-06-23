package com.cndym.servlet.coop;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cndym.bean.count.SaleTable;
import com.cndym.bean.query.AccountLogMember;
import com.cndym.bean.query.BonusTicketQueryBean;
import com.cndym.bean.query.TicketQueryBean;
import com.cndym.bean.sys.Manages;
import com.cndym.bean.tms.BonusTicket;
import com.cndym.bean.tms.Ticket;
import com.cndym.bean.user.AccountLog;
import com.cndym.bean.user.Member;
import com.cndym.constant.Constants;
import com.cndym.lottery.lotteryInfo.LotteryList;
import com.cndym.lottery.lotteryInfo.bean.LotteryBean;
import com.cndym.service.IAccountLogService;
import com.cndym.service.IBonusTicketService;
import com.cndym.service.ISaleTableService;
import com.cndym.service.ITicketService;
import com.cndym.admin.service.IBackTicketService;
import com.cndym.servlet.ElTagUtils;
import com.cndym.utils.DateUtils;
import com.cndym.utils.SpringUtils;
import com.cndym.utils.Utils;
import com.cndym.utils.hibernate.PageBean;

/**
 * Created by IntelliJ IDEA.
 * User: 邓玉明
 * Date: 11-8-1 下午4:01
 * QQ:757579248
 */
public class OutDataServlet extends HttpServlet {

    //账户明细
    private final String ACCOUNT_DETAIL = "accountDetail";
    //派奖查询
    private final String  BOUNS_OUT="bounsOut";
    //销售报表
    private final String ACCOUNT_ISSUE="accountIssue";
    //投注记录导出
    private final String TICKET_LIST_OUT_DATA = "ticketListOutData";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = Utils.formatStr(request.getParameter("action"));
        String body = null;
        if (ACCOUNT_DETAIL.equals(action)) {
            body = accountDetail(request);
        }
        if(BOUNS_OUT.equals(action)){
        	body=bounsDetail(request);
        }
        if(ACCOUNT_ISSUE.equals(action)){
        	body=issueDetail(request);
        }
        if (TICKET_LIST_OUT_DATA.equals(action)){
        	body = ticketListOutData(request);
        }
        if (!Utils.isNotEmpty(body)) {
            return;
        }
        response.setCharacterEncoding("GBK");
        response.setContentType("text/csv");
        String fileName = action + Utils.today("yyyyMMddHHmmss") + ".csv";
        String disposition = "attachment; fileName=" + fileName;
        response.setHeader("Content-Disposition", disposition);
        PrintWriter out = response.getWriter();
        out.print(body);
        out.flush();
        out.close();
    }
    
    public String issueDetail(HttpServletRequest request){
    	int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);
        
        Manages coopUser = (Manages) request.getSession().getAttribute("coopUser");
        String sid = coopUser.getUserName();
        
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
        List<Object[]> list=new ArrayList<Object[]>();
        for (int i = startPage; i <= endPage; i++) {
        	PageBean pageBean = saleTableService.getPageBeanByPara(saleTable, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return issueDetailToCsv(list, startPage);
    }
    
    public String issueDetailToCsv(List<Object[]> accountLogMemberList, Integer page) {
    	StringBuilder cvs = new StringBuilder();
        cvs.append("序号,彩种,期次,开售时间,止售,成功金额(元),成功票数,失败金额(元),失败票数,中奖票数,中奖税前(元),中奖税后(元),统计时间");
        int index = 0;
        for (Object objectArray : accountLogMemberList) {
        	SaleTable saleTable = (SaleTable) objectArray;
            cvs.append("\r\n");
            cvs.append((page - 1) * 50 + index + 1);
            cvs.append(",");
            cvs.append(LotteryList.getLotteryBean(saleTable.getLotteryCode()).getLotteryClass().getName());
            cvs.append(",");
            cvs.append("'"+saleTable.getIssue());
            cvs.append(",");
            cvs.append(Utils.formatDate2Str(saleTable.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
            cvs.append(",");
            cvs.append(Utils.formatDate2Str(saleTable.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            cvs.append(",");
            cvs.append(saleTable.getSuccessAmount());
            cvs.append(",");
            cvs.append(saleTable.getSuccessTicket());
            cvs.append(",");
            cvs.append(saleTable.getFailureAmount());
            cvs.append(",");
            cvs.append(saleTable.getBonusTicket());
            cvs.append(",");
            cvs.append(saleTable.getSuccessTicket());
            cvs.append(",");
            cvs.append(saleTable.getBonusAmount());
            cvs.append(",");
            cvs.append(saleTable.getFixBonusAmount());
            cvs.append(",");
            cvs.append(Utils.formatDate2Str(saleTable.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            index++;
        }
        return cvs.toString();
    }
    
    public String bounsDetail(HttpServletRequest request){
    	int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);
        
        Manages coopUser = (Manages) request.getSession().getAttribute("coopUser");
        String sid = coopUser.getUserName();
        
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
        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = bonusTicketService.getPageBeanByPara(ticketQueryBean, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return bounsDetailToCsv(list, startPage);
    }
    
    public String bounsDetailToCsv(List<Map> accountLogMemberList, Integer page) {
    	StringBuilder cvs = new StringBuilder();
        cvs.append("序号,批次号,票号,商户票号,投注彩种,投注期次,投注金额(元),投注时间,奖金类别,税前奖金(元),税后奖金(元),奖金时间,奖项信息");
        int index = 0;
        double bonusamount=0.00;
        double fixbonusamount=0.00;
        double amount=0.00;
        for (Map ticket : accountLogMemberList) {
            cvs.append("\r\n");
            cvs.append((page - 1) * 50 + index + 1);
            cvs.append(",");
            cvs.append("'"+ticket.get("ORDERID"));
            cvs.append(",");
            cvs.append("'"+ticket.get("TICKETID"));
            cvs.append(",");
            cvs.append("'"+ticket.get("OUTTICKETID"));
            cvs.append(",");
            cvs.append(LotteryList.getLotteryBean(ticket.get("LOTTERYCODE").toString()).getLotteryClass().getName());
            cvs.append(",");
            cvs.append("'"+ticket.get("ISSUE"));
            cvs.append(",");
            amount = ticket.get("AMOUNT").toString() == null ? 0.00 : Double.parseDouble(ticket.get("AMOUNT").toString());
            cvs.append(amount);
            cvs.append(",");
            cvs.append(Utils.formatDate2Str((Date)ticket.get("CREATETIME"), "yyyy-MM-dd HH:mm:ss"));
            cvs.append(",");
            cvs.append(ticket.get("BIGBONUS").toString() == "1" ? "大奖" : "小奖");
            cvs.append(",");
            fixbonusamount = ticket.get("FIXBONUSAMOUNT").toString() == null ? 0.00 : Double.parseDouble(ticket.get("FIXBONUSAMOUNT").toString());
            cvs.append(fixbonusamount);
            cvs.append(",");
            bonusamount = ticket.get("BONUSAMOUNT").toString() == null ? 0.00 : Double.parseDouble(ticket.get("BONUSAMOUNT").toString());
            cvs.append(bonusamount);
            cvs.append(",");
            cvs.append(Utils.formatDate2Str((Date)ticket.get("BONUSTIME"), "yyyy-MM-dd HH:mm:ss"));
            index++;
        }
        return cvs.toString();
    } 
    public String accountDetail(HttpServletRequest request) {
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);
        
        Manages adminUser = (Manages) request.getSession().getAttribute("coopUser");
        String sid = adminUser.getUserName();

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
        IAccountLogService accountLogService = (IAccountLogService) SpringUtils.getBean("accountLogServiceImpl");

        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = accountLogService.getAccountLogMemberList(accountLogMember, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        return accountDetailToCsv(list, startPage);
    }

    public String accountDetailToCsv(List<Object[]> accountLogMemberList, Integer page) {
        StringBuilder cvs = new StringBuilder();
        cvs.append("序号,交易编号,交易时间,交易类型,发生额(元),奖金变动前(元),奖金变动后(元),充值变动前(元),充值变动后(元),赠金变动前(元),赠金变动后(元),冻结变动前(元),冻结变动后(元),说明");
        int index = 0;
        AccountLog account = null;
        Member member = null;
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
            cvs.append("\r\n");
            cvs.append((page - 1) * 50 + index + 1);
            cvs.append(",");
            cvs.append("'"+account.getOrderId());
            cvs.append(",");
            cvs.append(Utils.formatDate2Str(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            cvs.append(",");
            cvs.append(ElTagUtils.getEventCodeType(account.getEventCode()));
            cvs.append(",");
            bonusAmount = account.getBonusAmount() == null ? 0.00 : account.getBonusAmount();
            rechargeAmount = account.getRechargeAmount() == null ? 0.00 : account.getRechargeAmount();
            presentAmount = account.getPresentAmount() == null ? 0.00 : account.getPresentAmount();
            freezeAmount = account.getFreezeAmount() == null ? 0.00 : account.getFreezeAmount();

            bonusAmountNew = account.getBonusAmountNew() == null ? 0.00 : account.getBonusAmountNew();
            rechargeAmountNew = account.getRechargeAmountNew() == null ? 0.00 : account.getRechargeAmountNew();
            presentAmountNew = account.getPresentAmountNew() == null ? 0.00 : account.getPresentAmountNew();
            freezeAmountNew = account.getFreezeAmountNew() == null ? 0.00 : account.getFreezeAmountNew();

            cvs.append(bonusAmount + rechargeAmount + presentAmount + freezeAmount);
            cvs.append(",");
            cvs.append(bonusAmountNew - bonusAmount).append(",").append(bonusAmountNew);
            cvs.append(",");
            cvs.append(rechargeAmountNew - rechargeAmount).append(",").append(rechargeAmountNew);
            cvs.append(",");
            cvs.append(presentAmountNew - presentAmount).append(",").append(presentAmountNew);
            cvs.append(",");
            cvs.append(freezeAmountNew - freezeAmount).append(",").append(freezeAmountNew);
            cvs.append(",");
            cvs.append(account.getMemo());
            index++;
        }
        return cvs.toString();
    }
    
    public String ticketListOutData(HttpServletRequest request) {
    	IBackTicketService ticketService = (IBackTicketService) SpringUtils.getBean("backTicketServiceImpl");
    	
        int startPage = Utils.formatInt(request.getParameter("startPage"), 1);
        int endPage = Utils.formatInt(request.getParameter("endPage"), 1);

        Manages adminUser = (Manages) request.getSession().getAttribute("coopUser");
        String sid = adminUser.getUserName();
        
        
        String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
        Integer ticketStatus = Utils.formatInt(request.getParameter("ticketStatus"),null);
        Integer bonusStatus = Utils.formatInt(request.getParameter("bonusStatus"),null);
        String ticketId = Utils.formatStr(request.getParameter("ticketId"));
        String orderId = Utils.formatStr(request.getParameter("orderId"));
        String issue = Utils.formatStr(request.getParameter("issue"));
        String createStartTime = Utils.formatStr(request.getParameter("createStartTime"));
        String createEndTime = Utils.formatStr(request.getParameter("createEndTime"));
        String outTicketId = Utils.formatStr(request.getParameter("outTicketId"));
        
        //彩期
        Integer issueStatus = Utils.formatInt(request.getParameter("issueStatus"), null);
        
        String eventCodeArr = Utils.formatStr(request.getParameter("eventCodeArr"));
        
        
        
        TicketQueryBean queryBean = new TicketQueryBean();
        Ticket ticket = new Ticket();


        ticket.setLotteryCode(lotteryCode);
        ticket.setIssue(issue);
        ticket.setOrderId(orderId);
        ticket.setOutTicketId(outTicketId);
        ticket.setTicketId(ticketId);
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
        
        queryBean.setTicket(ticket);
        
        PageBean pageBean = null;
        List list = new ArrayList();
        for (int i = startPage; i <= endPage; i++) {
            pageBean = ticketService.getPageBeanByPara(queryBean, i, Constants.PAGE_SIZE_50);
            list.addAll(pageBean.getPageContent());
        }
        
        return ticketListOutDataToCsv(list, startPage);
    }
    
    public String ticketListOutDataToCsv(List<Map> ticketList, Integer page) {
        StringBuilder cvs = new StringBuilder();
        cvs.append("序号,批次号,彩票编号,商户票号,彩种,玩法,期次,注数,倍数,金额(元),税前奖金(元),税后奖金(元),彩票状态,中奖状态,创建时间,回执时间,算奖时间");
        int index = 0;
        double amount = 0.00;
        double fixBonusAmount = 0.00;
        double bonusAmount = 0.00;
        for (Map mTticket : ticketList) {
        	cvs.append("\r\n");
            cvs.append((page - 1) * 50 + index + 1);
            cvs.append(",");
            cvs.append("'"+mTticket.get("ORDERID"));
            cvs.append(",");
            cvs.append("'"+mTticket.get("TICKETID"));
            cvs.append(",");
            cvs.append("'"+mTticket.get("OUTTICKETID"));
            cvs.append(",");
            cvs.append(ElTagUtils.getLotteryChinaName((String)mTticket.get("LOTTERYCODE")));
            cvs.append(",");
            cvs.append(ElTagUtils.getPlayChinaName((String)mTticket.get("LOTTERYCODE"),(String)mTticket.get("PLAYCODE")));
            cvs.append(",");
            cvs.append("'"+mTticket.get("ISSUE"));
            cvs.append(",");
            cvs.append(mTticket.get("ITEM"));
            cvs.append(",");
            cvs.append(mTticket.get("MULTIPLE"));
            cvs.append(",");
            String strAmount = String.valueOf(mTticket.get("AMOUNT"));
            //amount = Double.parseDouble(strAmount) == 0d ? 0.00 : Double.parseDouble(strAmount);
            cvs.append(formatDouble(strAmount,"0.00"));
            cvs.append(",");
            String strFixAmount = String.valueOf(mTticket.get("FIXBONUSAMOUNT"));
            //fixBonusAmount = Double.parseDouble(strFixAmount) == 0d ? 0.00 : Double.parseDouble(strFixAmount);
            cvs.append(formatDouble(strFixAmount,"0.00"));
            cvs.append(",");
            String strBonusAmount = String.valueOf(mTticket.get("BONUSAMOUNT"));
            //bonusAmount = Double.parseDouble(strBonusAmount) == 0d ? 0.00 : Double.parseDouble(strBonusAmount);
            cvs.append(formatDouble(strBonusAmount,"0.00"));
            cvs.append(",");
            BigDecimal bTickerStatus = (BigDecimal)mTticket.get("TICKETSTATUS");
            String tickerStatus = String.valueOf(bTickerStatus);
            if("0".equals(tickerStatus) || "1".equals(tickerStatus)){
            	cvs.append("未送票");
            }else if("2".equals(tickerStatus)){
            	cvs.append("送票未回执");
            }else if("3".equals(tickerStatus)){
            	cvs.append("出票成功");
            }else if("4".equals(tickerStatus)){
            	cvs.append(" 出票失败");
            }
            cvs.append(",");
            
            BigDecimal bBonussStatus = (BigDecimal)mTticket.get("BONUSSTATUS");
            String bonussStatus = String.valueOf(bBonussStatus);
            if("0".equals(bonussStatus)){
            	cvs.append("未开奖");
            }else if("1".equals(bonussStatus)){
            	cvs.append("中奖");
            }else if("2".equals(bonussStatus)){
            	cvs.append("未中奖");
            }
            cvs.append(",");
            Timestamp tCreateTime = (Timestamp)mTticket.get("CREATETIME");
            Timestamp tReturnTime = (Timestamp)mTticket.get("RETURNTIME");
            Timestamp tBounsTime = (Timestamp)mTticket.get("BONUSTIME");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            cvs.append(tCreateTime == null ? "" : sdf.format(tCreateTime));
            cvs.append(",");
            cvs.append(tReturnTime == null ? "" : sdf.format(tReturnTime));
            cvs.append(",");
            cvs.append(tBounsTime == null ? "" : sdf.format(tBounsTime));

            index++;
        }
        return cvs.toString();
    }
    
    public String formatDouble(String strDouble,String format){
	    try{
	    	BigDecimal b = new BigDecimal(strDouble);
		    b = b.setScale(format.split("\\.")[1].length(), BigDecimal.ROUND_HALF_DOWN);
		    return b.toString();
	    }catch(Exception e){
	    	return "0.00";
	    }
	 }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
