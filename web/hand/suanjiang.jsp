<%@page import="org.apache.log4j.Logger"%>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.service.ITicketService"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cndym.bean.tms.Ticket"%>
<%@ page contentType="text/html;charset=gbk" %>
<%
final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
ITicketService ticketService = (ITicketService) SpringUtils.getBean("ticketServiceImpl");

String issue = request.getParameter("issue");
String sn = request.getParameter("sn");
Connection conn = null;
Statement statement = null;
ResultSet resultSet = null;
try {
	Logger logger = Logger.getLogger(getClass());
	Class.forName(driverName);
	conn = DriverManager.getConnection(url, username, password);
	String createTime = Utils.formatDate2Str(Utils.addDate(new Date(), "d", -5), "yyyyMMdd");
	
	String sql = "select ticket_id,end_game_id from tms_ticket where create_time >= to_date('"+createTime+"','yyyymmdd') and lottery_code='200' and end_game_id='"+issue+sn+"' and bonus_status=0";
	//out.println(sql);
	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);
	int index = 1;
	while (resultSet.next()) {
		String ticketId = resultSet.getString("ticket_id");
		out.println("<br/>"+index+",ticketId="+ticketId );
		out.println("==old-end="+resultSet.getString("end_game_id"));
		Ticket ticket = ticketService.getTicketByTicketId(ticketId);
		String numberInfo = ticket.getNumberInfo();
		String[] arr = numberInfo.split("\\|");
		if (arr.length != 2) {
		    out.println("No1");
		}
		
		String[] matchIdArr = arr[0].split(";");

		Long endMatchId = 19700101001L;
		for (String number : matchIdArr) {
		    String[] subArr = number.split(":");
		    out.println("--sn="+subArr[0]);
		    if (Long.valueOf(subArr[0]) > endMatchId) {
		    	endMatchId = Long.valueOf(subArr[0]);
		    }
		}
		if(endMatchId > 20140101001L) {
			ticket.setEndGameId(String.valueOf(endMatchId));
			out.println("==new-end="+endMatchId);
			boolean bool = ticketService.update(ticket);
			out.println("<br/>OK="+bool);
			logger.info("ticketId="+ticketId+","+endMatchId);
		} else {
			out.println("No4");
		}
		index++;
	}
} catch (ClassNotFoundException e) {
	e.printStackTrace();
} catch (SQLException e) {
	e.printStackTrace();
} finally {
	if (null != resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (null != statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (null != conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
%>
