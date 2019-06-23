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
<%@ page import="com.cndym.utils.hibernate.PageBean"%>
<%@ page import="com.cndym.bean.query.TicketQueryBean"%>
<%@ page contentType="text/html;charset=gbk" %>
<%
    PrintWriter printWriter = response.getWriter();
    String filename = request.getParameter("f");
    ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");
    
    String line = "";
    Map<String, String> orderMap = new HashMap<String, String>();
    int i=1;
    try {
        TicketQueryBean queryBean = new TicketQueryBean();
        Ticket ticket1 = new Ticket();
        
    	File file = new File("/home/zw/"+filename);
		FileInputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new UnicodeReader(in, Charset.defaultCharset().name()));
		while ((line = br.readLine()) != null) {
			//out.println(i+"."+line);
	        ticket1.setOutTicketId(line.trim());
	        queryBean.setTicket(ticket1);
	        PageBean pageBean = ticketService.getPageBeanByPara(queryBean, 1, 2);
	        List<Object> tickets = pageBean.getPageContent();
			for(Object ticket : tickets) {
				Map map = (Map)ticket;
				//out.println(i+"¡¢"+ticket.getOutTicketId()+","+ticket.getBackup1()+","+ticket.getPostCode()+","+ticket.getLotteryCode()+","+ticket.getCreateTime()+","+ticket.getSendTime()+","+ticket.getReturnTime()+","+ticket.getBackup1()+","+ticket.getSaleInfo()+"<br/>");
				out.println(i+"¡¢"+map.get("OUTTICKETID")+","+map.get("BACKUP1")+"<br/>");
			}
			i++;
		}
    } catch(Exception e) {
    	e.printStackTrace();
    }

%>
