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
ITicketService ticketService = (ITicketService)SpringUtils.getBean("ticketServiceImpl");
Ticket ticket = ticketService.getTicketByTicketId(ticketId);
out.println("<br/>ticketId="+ticketId);
out.println("<br/>getSid="+ticket.getSid());
Double bonusAmount = ticket.getBonusAmount();
Double fixBounsAmount = ticket.getFixBonusAmount();
out.println("<br/>getAmount="+bonusAmount);
out.println("<br/>getFixBonusAmount="+fixBounsAmount);
if(fixBounsAmount < bonusAmount){
	 out.println("<br/>ERROR, bonusAmount["+ bonusAmount + "] > fixBounsAmount[" +fixBounsAmount+"]" );
}else if(ticketId != null && ticketId !="") {
	IAccountService accountService = (IAccountService)SpringUtils.getBean("accountServiceImpl");
	accountService.doAccount(ticket.getUserCode(), Constants.R00200, ticket.getBonusAmount(), ticket.getLotteryCode()+"."+ticket.getIssue(), ticket.getSid());
	out.println("<br/>OK");
}
%>
