<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.math.BigDecimal"%> 
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%> 
<%@ page import="com.cndym.lottery.lotteryInfo.LotteryList"%>
<%@ page import="com.cndym.control.PostMap"%>
<%@ page import="com.cndym.sendClient.ltkj.LtkjSendClientConfig"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.email.MailEngine"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cndym.email.SmsEngine"%>
<%@ page contentType="text/html;charset=gbk"%>
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
		logger.info("autoAddPostIssue start");
		PrintWriter printWriter = response.getWriter();
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, username, password);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String postCode = LtkjSendClientConfig.getValue("POST_CODE");
		
		StringBuffer issueSql = new StringBuffer();
		StringBuffer ssqSql = new StringBuffer();
		StringBuffer qlcSql = new StringBuffer();
		StringBuffer result = new StringBuffer();
		issueSql.append("select t.lottery_code,max(t.name) name from tms_post_issue t where t.status = '3' and t.post_code ='")
				.append(postCode)
				.append("'");
		//获取当前星期
		String weekDay = Utils.getWeekByDate(new Date());
		//如果是周二、四、日则处理双色球
		if("星期二星期四星期日".contains(weekDay)){
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			if("星期二星期日".contains(weekDay)){//加2天
				endCalendar.add(Calendar.DAY_OF_WEEK, 2);
			}
			
			if("星期四".contains(weekDay)){//加3天
				endCalendar.add(Calendar.DAY_OF_WEEK, 3);
			}
			
			issueSql.append(" and t.lottery_code = '001' group by t.lottery_code");
			logger.info("issueSql:" + issueSql.toString());
			statement = conn.createStatement();
			resultSet = statement.executeQuery(issueSql.toString());
			String issue = "";
			String newIssue = "";
			if(resultSet.next()) {
				issue = resultSet.getString("name");
			}
			if(Utils.isNotEmpty(issue)){
				String lastThree = issue.substring(4);
				String newIssueStart = issue.substring(0,4);
				if(lastThree.startsWith("0")){
					lastThree = lastThree.substring(1);
					if(lastThree.startsWith("0")){
						lastThree = lastThree.substring(1);
					}
				}
				
				Integer n = Utils.formatInt(lastThree,0) + 1;
				if(n < 10){
					newIssue = newIssueStart + "00" + n;
				}else if(n >= 10 && n < 99){
					newIssue = newIssueStart + "0" + n;
				}else{
					newIssue = newIssueStart + n;
				}
				String startTime = sdf.format(startCalendar.getTime()) + " 20:10:00";
				String duplexTime = sdf.format(endCalendar.getTime()) + " 19:45:00";
				String endTime = sdf.format(endCalendar.getTime()) + " 20:00:00";
				String bonusTime = sdf.format(endCalendar.getTime()) + " 20:00:00";;
				ssqSql.append("insert into tms_post_issue t values(seq_tms_post_issue.nextval,'001','")
					  .append(postCode).append("','")
					  .append(newIssue).append("',to_date('")
				      .append(startTime).append("','yyyy-mm-dd hh24:mi:ss'),").append("to_date('")
				      .append(duplexTime).append("','yyyy-mm-dd hh24:mi:ss'),").append("to_date('")
				      .append(endTime).append("','yyyy-mm-dd hh24:mi:ss'),1,").append("to_date('")
				      .append(bonusTime).append("','yyyy-mm-dd hh24:mi:ss'),0,null,null,'")
				      .append(sdf1.format(new Date()))
				      .append("',null,null)");
				logger.info("ssqSql:" + ssqSql.toString());
				int count = statement.executeUpdate(ssqSql.toString());
				if(count == 1){
					logger.info("今天是" + sdf.format(startCalendar.getTime()) + "，" + weekDay + "添加的双色球期次是：" + newIssue);
					result.append("今天是" + sdf.format(startCalendar.getTime()) + "，" + weekDay + "添加的双色球期次是：" + newIssue + ";<br/>");	
					
				}
			}
			
			
			
		}
		
		//如果是周一、三、五则处理七乐彩
		if("星期一星期三星期五".contains(weekDay)){
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			if("星期一星期三".contains(weekDay)){//加2天
				endCalendar.add(Calendar.DAY_OF_WEEK, 2);
			}
			
			if("星期五".contains(weekDay)){//加3天
				endCalendar.add(Calendar.DAY_OF_WEEK, 3);
			}
			
			issueSql.append(" and t.lottery_code = '004' group by t.lottery_code");
			logger.info("issueSql:" + issueSql.toString());
			statement = conn.createStatement();
			resultSet = statement.executeQuery(issueSql.toString());
			String issue = "";
			String newIssue = "";
			if(resultSet.next()) {
				issue = resultSet.getString("name");
			}
			if(Utils.isNotEmpty(issue)){
				String lastThree = issue.substring(4);
				String newIssueStart = issue.substring(0,4);
				if(lastThree.startsWith("0")){
					lastThree = lastThree.substring(1);
					if(lastThree.startsWith("0")){
						lastThree = lastThree.substring(1);
					}
				}
				
				Integer n = Utils.formatInt(lastThree,0) + 1;
				if(n < 10){
					newIssue = newIssueStart + "00" + n;
				}else if(n >= 10 && n < 99){
					newIssue = newIssueStart + "0" + n;
				}else{
					newIssue = newIssueStart + n;
				}
				String startTime = sdf.format(startCalendar.getTime()) + " 20:10:00";
				String duplexTime = sdf.format(endCalendar.getTime()) + " 19:45:00";
				String endTime = sdf.format(endCalendar.getTime()) + " 20:00:00";
				String bonusTime = sdf.format(endCalendar.getTime()) + " 20:00:00";;
				qlcSql.append("insert into tms_post_issue t values(seq_tms_post_issue.nextval,'004','")
					  .append(postCode).append("','")
					  .append(newIssue).append("',to_date('")
				      .append(startTime).append("','yyyy-mm-dd hh24:mi:ss'),").append("to_date('")
				      .append(duplexTime).append("','yyyy-mm-dd hh24:mi:ss'),").append("to_date('")
				      .append(endTime).append("','yyyy-mm-dd hh24:mi:ss'),1,").append("to_date('")
				      .append(bonusTime).append("','yyyy-mm-dd hh24:mi:ss'),0,null,null,'")
				      .append(sdf1.format(new Date()))
				      .append("',null,null)");
				logger.info("qlcSql:" + qlcSql.toString());
				int count = statement.executeUpdate(qlcSql.toString());
				if(count == 1){
					logger.info("今天是" + sdf.format(startCalendar.getTime()) + "，" + weekDay + "添加的七乐彩期次是：" + newIssue);
					result.append("今天是" + sdf.format(startCalendar.getTime()) + "，" + weekDay + "添加的七乐彩期次是：" + newIssue + ";<br/>");	
				}	
			}
		}
		
		printWriter.write(result.toString());
		printWriter.flush();
		printWriter.close();
		logger.info("autoAddPostIssue end");
		
		
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
