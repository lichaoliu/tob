<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.PrintWriter"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.email.MailEngine"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cndym.email.SmsEngine"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
	final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
	final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
	final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
	Logger logger = Logger.getLogger(getClass());
	
	String email = Utils.formatStr(request.getParameter("email"));

	Connection conn = null;
	Statement statement = null;
	ResultSet resultSet = null;

	try {
		logger.info("monitorTicketAmount start");
		PrintWriter printWriter = response.getWriter();
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, username, password);
		StringBuffer amountSql = new StringBuffer();
		
		Calendar lastCalendar = Calendar.getInstance();
		lastCalendar.add(Calendar.DATE,-1);
		lastCalendar.set(Calendar.HOUR_OF_DAY, 0);
		lastCalendar.set(Calendar.MINUTE, 0);
		lastCalendar.set(Calendar.SECOND, 0);
		
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String lastDay = sdf.format(lastCalendar.getTime());
		String currentDay = sdf.format(currentCalendar.getTime());
		
		amountSql.append("select t1.name,t2.sid,t2.amount from user_member t1,")
				 .append("(select t.sid,sum(t.amount) amount from tms_ticket t where t.ticket_status='3' ")
				 .append("and t.create_time>=to_date('")
				 .append(lastDay)
				 .append("','yyyy-mm-dd hh24:mi:ss') ")
				 .append("and t.create_time<to_date('")
				 .append(currentDay)
				 .append("','yyyy-mm-dd hh24:mi:ss') ")
				 .append("group by t.sid) t2 where t1.sid = t2.sid order by t2.amount desc");
		
		logger.info("amountSql:" + amountSql.toString());
		statement = conn.createStatement();
		resultSet = statement.executeQuery(amountSql.toString());
		BigDecimal sum = new BigDecimal(0d);
		StringBuffer result = new StringBuffer();
		result.append("<table style=\"border-right:1px solid #000;border-bottom:1px solid #000\" cellpadding='0' cellspacing='0' >");
		int index = 1;
		result.append("<tr><th style=\"border-left:1px solid #000;border-top:1px solid #000\" >排名</th><th style=\"border-left:1px solid #000;border-top:1px solid #000\" >接入商</th><th style=\"border-left:1px solid #000;border-top:1px solid #000\" >投注金</th></tr>");
		while (resultSet.next()) {
			String sid = resultSet.getString("sid");
			String name = resultSet.getString("name");
			String amount = resultSet.getString("amount");
			sum = sum.add(new BigDecimal(amount));
			result.append("<tr><td style=\"border-left:1px solid #000;border-top:1px solid #000\" >")
			  	  .append(index)
		          .append("</td><td style=\"border-left:1px solid #000;border-top:1px solid #000\" >")
				  .append(name)
			      .append("</td><td style=\"border-left:1px solid #000;border-top:1px solid #000\" >")
		    	  .append(amount)
			      .append("</td></tr>");
			index ++;
		}
		result.append("<tr><td colspan=\"2\"  style=\"border-left:1px solid #000;border-top:1px solid #000\">总计</td><td  style=\"border-left:1px solid #000;border-top:1px solid #000\">")
		      .append(sum.toString())
			  .append("</td></tr>");
		
		result.append("</table>");
		String body = result.toString();
		if (Utils.isNotEmpty(body)) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日");
			final String memo = sdf1.format(lastCalendar.getTime()) + "接入商销量";
			MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
			if (Utils.isNotEmpty(email)) {
				String[] emailArr = email.split(",");
				try {
					mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			printWriter.print(body);
		} else {
			printWriter.print("true");
		}
		printWriter.flush();
		printWriter.close();
		logger.info("monitorTicketAmount end");
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
