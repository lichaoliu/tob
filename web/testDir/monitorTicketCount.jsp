<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.email.MailEngine"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cndym.email.SmsEngine"%>
<%@ page contentType="text/html;charset=utf8"%>
<%
final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
Logger logger = Logger.getLogger(getClass());
	
String mobile = Utils.formatStr(request.getParameter("mobile"));
String email = Utils.formatStr(request.getParameter("email"));

Connection conn = null;
Statement statement = null;
ResultSet resultSet = null;

try {
	logger.info("start");
	Calendar calendar = Calendar.getInstance();
	int hour = calendar.get(Calendar.HOUR_OF_DAY);
	String date1 = Utils.formatDate2Str(Utils.addDate(new Date(), "d", -1), "yyyyMMdd");
	String date2 = Utils.formatDate2Str(Utils.addDate(new Date(), "d", 0), "yyyyMMdd");
	StringBuilder result = new StringBuilder();
	Class.forName(driverName);

	conn = DriverManager.getConnection(url, username, password);
	String sql = "select sum(amount) amount from tms_ticket where create_time>=to_date('"+date1+"','yyyymmdd') and ";
	sql += "create_time < to_date('"+date2+"','yyyymmdd') and ticket_status <= 3 ";
	logger.info(sql);
	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);
	Double money = 0D;
	while (resultSet.next()) {
		money = resultSet.getDouble("amount");
		result.append("tob:"+Utils.formatNumberZD(money)+"<br/>");
	}
	conn = DriverManager.getConnection("jdbc:oracle:thin:@119.254.92.201:1522:orc1", "lottery_mouse", "lottery_mouse");
	sql = "select sum(amount) amount from tms_ticket where create_time>=to_date('"+date1+"','yyyymmdd') and ";
	sql += "create_time < to_date('"+date2+"','yyyymmdd') and ticket_status <= 3 ";
	logger.info(sql);
	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);

	while (resultSet.next()) {
		money += resultSet.getDouble("amount");
		result.append("tob':"+Utils.formatNumberZD(resultSet.getDouble("amount"))+"<br/>");
	}
	result.append("合计:"+Utils.formatNumberZD(money)+"<br/>");
	String body = result.toString();
	if (Utils.isNotEmpty(body)) {
		final String memo = "出票统计"+date1;
		MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
		if (Utils.isNotEmpty(email)) {
			String[] emailArr = email.split(",");
			try {
				out.println(body);
				mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}
	logger.info("end");
} catch (ClassNotFoundException e) {
	logger.error("", e);
} catch (SQLException e) {
	logger.error("", e);
} finally {
	if (null != resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			logger.error("", e);
		}
	}
	if (null != statement) {
		try {
			statement.close();
		} catch (SQLException e) {
			logger.error("", e);
		}
	}
	if (null != conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("", e);
		}
	}
}
%>
