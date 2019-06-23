<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
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
	PrintWriter printWriter = response.getWriter();
	Class.forName(driverName);
	conn = DriverManager.getConnection(url, username, password);
	StringBuilder result = new StringBuilder();
	Map<String,Integer> lotteryIssueBonus = new HashMap<String, Integer>();

	String after_date = Utils.formatDate2Str(Utils.addDate(new Date(), "d", -4), "yyyyMMdd");
	String before_date = Utils.formatDate2Str(new Date(), "yyyyMMdd");
	String sql = "select lottery_code,issue,bonus_status from tms_ticket where create_time >= to_date('"+after_date+
			"','yyyymmdd') and create_time < to_date('"+before_date+"','yyyymmdd') and lottery_code not in ('200','201') and ticket_status=3 "+
			"group by lottery_code,issue,bonus_status order by lottery_code,issue";
	logger.info(sql);

	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);

	while (resultSet.next()) {
		String lottery_code = resultSet.getString("lottery_code");
		String issue = resultSet.getString("issue");
		int bonus_status = resultSet.getInt("bonus_status") > 0 ? 1 : 0;
		String key = lottery_code+issue;
		if (lotteryIssueBonus.containsKey(key)) {
			if (bonus_status != lotteryIssueBonus.get(key)) {
				result.append("彩种:"+lottery_code+",期次:"+issue+"存在未派奖订单;<br/>");
			} else {
				lotteryIssueBonus.put(key, bonus_status);
			}
		} else {
			lotteryIssueBonus.put(key, bonus_status);
		}
	}

	sql = "select lottery_code,end_game_id,bonus_status from tms_ticket where create_time >= to_date('"+after_date+
			"','yyyymmdd') and create_time < to_date('"+before_date+"','yyyymmdd') and lottery_code in ('200','201') and ticket_status=3 "+
			"group by lottery_code,end_game_id,bonus_status order by lottery_code,end_game_id";
	logger.info(sql);

	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql);
	while (resultSet.next()) {
		String lottery_code = resultSet.getString("lottery_code");
		String issue = resultSet.getString("end_game_id");
		int bonus_status = resultSet.getInt("bonus_status") > 0 ? 1 : 0;
		String key = lottery_code+issue;
		if (lotteryIssueBonus.containsKey(key)) {
			if (bonus_status != lotteryIssueBonus.get(key)) {
				result.append("彩种:"+lottery_code+",期次:"+issue+"存在未派奖订单;<br/>");
			} else {
				lotteryIssueBonus.put(key, bonus_status);
			}
		} else {
			lotteryIssueBonus.put(key, bonus_status);
		}
	}
	
	String body = result.toString();
	if (Utils.isNotEmpty(body)) {
		final String memo = "tob-已派奖期存在未派奖订单，详情见邮件，请核实。";
		MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
		if (Utils.isNotEmpty(email)) {
			String[] emailArr = email.split(",");
			try {
				mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		if (Utils.isNotEmpty(mobile)) {
			try {
				SmsClient.sendSMS(mobile, memo);
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
