<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.cndym.lottery.lotteryInfo.LotteryList"%>
<%@ page import="com.cndym.cache.XMemcachedClientWrapper" %>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.email.MailEngine"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
	final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
	final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
	final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
	Logger logger = Logger.getLogger(getClass());
	
	XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
	String lotteryCode = "013,103,105,107";
	
	String email = Utils.formatStr(request.getParameter("email"));
	String mobile = Utils.formatStr(request.getParameter("mobile"));
	Connection conn = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	try {
		logger.info("monitorStartIssue start");
		PrintWriter printWriter = response.getWriter();
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, username, password);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer queryLotteryCodes = new StringBuffer();
		String[] lotteryCodes = lotteryCode.split(",");
		for(String code : lotteryCodes){
			StringBuffer key = new StringBuffer("lottery.").append(code).append(".control");
			String value = memcachedClientWrapper.get(key.toString());
			if (!Utils.isNotEmpty(value)) {
				queryLotteryCodes.append("'").append(code).append("',");
			}
		}
		
		if(queryLotteryCodes.length() > 0){//如果有在售的高频彩种
			queryLotteryCodes.deleteCharAt(queryLotteryCodes.length() - 1);
			StringBuilder result = new StringBuilder();
			StringBuffer sendInfoSql = new StringBuffer();
			
			sendInfoSql.append(" select * from tms_main_issue t where ")
					.append(" t.lottery_code in (")
					.append(queryLotteryCodes.toString())
					.append(")")
					.append(" and sysdate > (t.start_time +2/(24*60))")	
				    .append(" and t.status = 0");
			
			logger.info("sendInfoSql:" + sendInfoSql.toString());
			
			statement = conn.createStatement();
			resultSet = statement.executeQuery(sendInfoSql.toString());
			int count = 0;
			
			while (resultSet.next()) {
				String lottery_code = resultSet.getString("lottery_code");
				String issue = resultSet.getString("name");
				String startTime = sdf.format(new Date(resultSet.getTimestamp("start_time").getTime()));
				String endTime = sdf.format(new Date(resultSet.getTimestamp("end_time").getTime()));
				count ++;
				
				result.append("彩种：").append(LotteryList.getLotteryBean(lottery_code).getLotteryClass().getName()).append("(").append(lottery_code).append(")，");
				result.append("期次：").append(issue).append("，");
				result.append("开售时间：").append(startTime).append("，");
				result.append("截期时间：").append(endTime).append("；<br/>");
			}
			
			String body = result.toString();
			if (Utils.isNotEmpty(body)) {
				String msg = "";
				if(count > 0){
					msg = "tob-有(" + count + ")个彩种期次未能正常开售";
				}
				
				final String memo = msg;
				MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
				if (Utils.isNotEmpty(email)) {
					String[] emailArr = email.split(",");
					try {
						mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(Utils.isNotEmpty(mobile)) {
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
		}
		printWriter.flush();
		printWriter.close();
		logger.info("monitorStartIssue end");
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
