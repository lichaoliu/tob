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
		if (hour >= 9 && hour < 22) {
			PrintWriter printWriter = response.getWriter();
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			StringBuilder result = new StringBuilder();

			String createTime = Utils.formatDate2Str(Utils.addDate(new Date(), "d", -2), "yyyyMMdd");
			
			String sql = "select ticket_id,lottery_code,issue,bonus_amount from tms_ticket t where t.create_time > to_date("+createTime+",'yyyymmdd') and t.bonus_status=1 and t.fix_bonus_amount=0";

			statement = conn.createStatement();
			resultSet = statement.executeQuery(sql);
			int count = 0;
			while (resultSet.next()) {
				String lottery_code = resultSet.getString("lottery_code");
				String issue = resultSet.getString("issue");
				result.append("ticket_id=").append(resultSet.getString("ticket_id")).append(",");
				result.append("lottery_code=").append(lottery_code).append(",");
				result.append("issue=").append(issue).append(",");
				result.append("bonus_amount=").append(resultSet.getString("bonus_amount"));
				result.append(";<br/>");
				count++;
			}
			
			if(hour >= 9 && hour < 10) {
				sql = "select ticket_id,lottery_code,issue,bonus_amount from tms_bonus_log where lottery_code in ('001','004') and dui_jiang_status=0 and fix_bonus_amount >=10000";
				statement = conn.createStatement();
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
					String lottery_code = resultSet.getString("lottery_code");
					String issue = resultSet.getString("issue");
					result.append("ticket_id=").append(resultSet.getString("ticket_id")).append(",");
					result.append("lottery_code").append(lottery_code).append(",");
					result.append("issue=").append(issue).append(",");
					result.append("bonus_amount=").append(resultSet.getString("bonus_amount"));
					result.append(";<br/>");
					count++;
				}
			}
			String body = result.toString();
			if (Utils.isNotEmpty(body)) {
				final String memo = "tob-有(" + count + ")张大奖票，需要手工返奖";
				MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
				if (Utils.isNotEmpty(email)) {
					String[] emailArr = email.split(",");
					try {
						mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (Utils.isNotEmpty(mobile)) {
					try {
						SmsClient.sendSMS(mobile, memo);
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
		}
		logger.info("end");
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
