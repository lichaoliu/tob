<%@ page import="java.util.Date"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="com.cndym.service.ITicketService"%>
<%@ page import="com.cndym.dao.IMainIssueDao"%>
<%@ page import="com.cndym.dao.IBonusLogDao"%>
<%@ page import="com.cndym.dao.IBonusTicketDao"%>
<%@ page import="com.cndym.service.IAccountService"%>
<%@ page import="com.cndym.bean.tms.Ticket"%>
<%@ page import="com.cndym.bean.tms.BonusLog"%>
<%@ page import="com.cndym.bean.tms.MainIssue"%>
<%@ page import="com.cndym.bean.tms.BonusTicket"%>
<%@ page import="com.cndym.constant.Constants"%>
<%@ page contentType="text/html;charset=gbk"%>
<%
String ticketId = request.getParameter("id");
Double fixBonusAmount = Utils.formatDouble(request.getParameter("fix"), 0D);
ITicketService ticketService = (ITicketService)SpringUtils.getBean("ticketServiceImpl");
Ticket ticket = ticketService.getTicketByTicketId(ticketId);
out.println("<br/>ticketId="+ticketId);
out.println("<br/>fixBonusAmount="+fixBonusAmount);
if (fixBonusAmount > 0D && ticketId != null && null != ticket) {
	int bigBonus = 0;
	Double bonusAmount = fixBonusAmount;
	if (fixBonusAmount >= 10000) {
		bonusAmount = fixBonusAmount * 0.8;
		bigBonus = 1;
	}
	out.println("<br/>getBonusStatus="+ticket.getBonusStatus());
	ticket.setBonusAmount(bonusAmount);
	ticket.setFixBonusAmount(fixBonusAmount);
	ticketService.update(ticket);
	out.println("<br/>1");
	IBonusLogDao bonusLogDao = (IBonusLogDao)SpringUtils.getBean("bonusLogDaoImpl");

	BonusLog bonusLog = bonusLogDao.getBonusLogByTicketId(ticketId);
	if (null == bonusLog) {
		bonusLog = new BonusLog();
		bonusLog.setTicketId(ticketId);
		bonusLog.setLotteryCode(ticket.getLotteryCode());
		bonusLog.setIssue(ticket.getIssue());
		bonusLog.setBonusAmount(ticket.getBonusAmount());
		bonusLog.setBonusClass(ticket.getBonusClass());
		bonusLog.setFixBonusAmount(ticket.getFixBonusAmount());
		bonusLog.setBigBonus(bigBonus);
		bonusLog.setCreateTime(new Date());
		bonusLog.setPostCode(ticket.getPostCode());
		bonusLog.setSaleCode(ticket.getSaleCode());
		bonusLog.setSaleTime("");
		bonusLog.setDuiJiangStatus(0);
		bonusLog.setMachineCode("");
		bonusLogDao.save(bonusLog);
	} else {
		bonusLog.setBonusAmount(ticket.getBonusAmount());
		bonusLog.setFixBonusAmount(ticket.getFixBonusAmount());
		bonusLog.setBigBonus(bigBonus);
		bonusLogDao.update(bonusLog);
	}
	out.println("<br/>2");
	
	IAccountService accountService = (IAccountService)SpringUtils.getBean("accountServiceImpl");
    accountService.doAccount(ticket.getUserCode(), Constants.R00200, bonusAmount, ticket.getLotteryCode()+"."+ticket.getIssue(), ticket.getSid());
    out.println("<br/>3");
    
	IMainIssueDao mainIssueDao = (IMainIssueDao)SpringUtils.getBean("mainIssueDaoImpl");
    MainIssue mainIssue = mainIssueDao.getMainIssueForUpdate(ticket.getLotteryCode(), ticket.getIssue());
    mainIssue.setBonusTotal(mainIssue.getBonusTotal() + bonusAmount);
    mainIssueDao.update(mainIssue);
    out.println("<br/>4");
    
    IBonusTicketDao bonusTicketDao = (IBonusTicketDao)SpringUtils.getBean("bonusTicketDaoImpl");
    BonusTicket bonusTicket = new BonusTicket();
    bonusTicket.setOutTicketId(ticket.getOutTicketId());
    bonusTicket.setSid(ticket.getSid());
    bonusTicket.setUserCode(ticket.getUserCode());
    bonusTicket.setOrderId(ticket.getOrderId());
    bonusTicket.setTicketId(ticket.getTicketId());
    bonusTicket.setLotteryCode(ticket.getLotteryCode());
    bonusTicket.setPlayCode(ticket.getPlayCode());
    bonusTicket.setPollCode(ticket.getPollCode());
    bonusTicket.setIssue(ticket.getIssue());
    bonusTicket.setPostCode(ticket.getPostCode());
    bonusTicket.setAmount(ticket.getAmount());
    bonusTicket.setBonusAmount(ticket.getBonusAmount());
    bonusTicket.setBonusClass(ticket.getBonusClass());
    bonusTicket.setFixBonusAmount(ticket.getFixBonusAmount());
    bonusTicket.setStartGameId(ticket.getStartGameId());
    bonusTicket.setEndGameId(ticket.getEndGameId());
    bonusTicket.setCreateTime(ticket.getCreateTime());
    bonusTicket.setSendTime(ticket.getSendTime());
    bonusTicket.setReturnTime(ticket.getReturnTime());
    bonusTicket.setBonusTime(ticket.getBonusTime());
    bonusTicket.setBigBonus(ticket.getBigBonus());
    bonusTicket.setBackup1(ticket.getBackup1());
    bonusTicket.setBackup2(ticket.getBackup2());
    bonusTicket.setBackup3(ticket.getBackup3());
    bonusTicketDao.save(bonusTicket);
    out.println("<br/>5");
    out.println("<br/>OK");
} else {
	out.println("NO");
}

%>