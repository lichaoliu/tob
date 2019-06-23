<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.cndym.service.ITicketService" %>
<%@ page contentType="text/html;charset=gbk" %>
<%
    PrintWriter printWriter = response.getWriter();
    ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
    printWriter.println(ticketService.getTicketByTicketId("30418164522026dT98Qr").getTicketStatus() + "<br/>");
    ticketService.doBonusToAccount("30418164522026dT98Qr", 63.12d);
    printWriter.println("ok~~~~~~~~~~~~~~~~~~~~");
	
%>
