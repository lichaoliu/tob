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
Double amount = Double.valueOf(request.getParameter("amount"));
ITicketService ticketService = (ITicketService)SpringUtils.getBean("ticketServiceImpl");

if (ticketId != null && ticketId !="" && amount > 0) {
	out.println("<br/>ticketId="+ticketId+"=");
	out.println("<br/>amount="+amount+"=");	
	ticketService.doBonusToAccount(ticketId, amount);
	out.println("<br/>OK");
}
%>