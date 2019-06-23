<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="com.cndym.utils.UnicodeReader"%>
<%@ page import="java.nio.charset.Charset"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="com.cndym.service.ITicketService"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cndym.bean.tms.Ticket"%>
<%@ page contentType="text/html;charset=gbk" %>
<%
    PrintWriter printWriter = response.getWriter();
    
    ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
    
    String line = "";
    Map<String, String> orderMap = new HashMap<String, String>();
    int i=1;
    try {
    	File file = new File("/home/zw/orderid.txt");
		FileInputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
		while ((line = br.readLine()) != null) {
			//out.println(line+"<br/>");
			List<Ticket> tickets = ticketService.getTicketByOrderId(line, "800005");
			if (Utils.isNotEmpty(tickets)) {
				for(Ticket ticket : tickets) {
					if (ticket.getTicketStatus() == 3) {
						out.println(i+"¡¢"+ticket.getOrderId()+","+ticket.getTicketId()+","+ticket.getPostCode()+","+ticket.getLotteryCode()+","+ticket.getCreateTime()+","+ticket.getSendTime()+","+ticket.getReturnTime()+","+ticket.getBackup1()+","+ticket.getSaleInfo()+"<br/>");
					}
				}
			}else {
				out.println(i+"¡¢====="+line+"<br/>");					
			}
			i++;
		}
    } catch(Exception e) {
    	e.printStackTrace();
    }

%>
