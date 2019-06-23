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
<%@ page import="com.cndym.control.PostMap"%>
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
		logger.info("monitorBonusAcount start");
		PrintWriter printWriter = response.getWriter();
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, username, password);
		StringBuilder result = new StringBuilder();
		StringBuffer amountSql = new StringBuffer();
		String weekDay = Utils.getWeekByDate(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar nowCalendar = Calendar.getInstance();
		String today = sdf.format(nowCalendar.getTime()) + " 00:00:00";
		String now = sdf.format(nowCalendar.getTime()) + " 08:10:00";
		nowCalendar.add(Calendar.DAY_OF_WEEK, -1);
		String yes = sdf.format(nowCalendar.getTime()) + " 08:10:00";
		String yestoday = sdf.format(nowCalendar.getTime()) + " 00:00:00";
		StringBuffer issueSql = new StringBuffer();
		
		issueSql.append("select t.lottery_code,max(t.name) name, max(t.end_time) end_time from tms_main_issue t where t.status = '3'")
				.append(" and t.end_time >= to_date('")
				.append(yestoday)
				.append("','yyyy-mm-dd hh24:mi:ss') and t.end_time < to_date('")
				.append(today)
				.append("','yyyy-mm-dd hh24:mi:ss')")	
			    .append(" and t.lottery_code not in ('006','007','009','010','011','012','013','101','102','103','104','105','106','107','202')")
			    .append(" group by t.lottery_code ");
		
		logger.info("issueSql:" + issueSql.toString());
		
		amountSql.append("select t.lottery_code,t.issue,t.ticket_id,t.post_code,t.fix_bonus_amount,t.bonus_amount,t.bonus_time from tms_bonus_ticket t where (");
		
		statement = conn.createStatement();
		resultSet = statement.executeQuery(issueSql.toString());
		int index = 0;
		int count = 0;
		
		
		Map<String,String> bonusTimeMap = new HashMap<String,String>();
		while (resultSet.next()) {
			String lottery_code = resultSet.getString("lottery_code");
			String issue = resultSet.getString("name");
			String bonusTime = sdf.format(resultSet.getDate("end_time"));
			bonusTimeMap.put(issue,bonusTime);
			if(index > 0){
				amountSql.append(" or ");
			}
			amountSql.append("(t.lottery_code = '")
					 .append(lottery_code)
					 .append("' and t.issue = '")
					 .append(issue)
					 .append("')");
			index ++;
		}
		
		amountSql.append(") and  t.bonus_amount >= 10000");
		
		StringBuffer bonusSql = new StringBuffer();
		bonusSql.append("select t.lottery_code,t.issue,t.ticket_id,t.post_code,t.fix_bonus_amount,t.bonus_amount,t.bonus_time from tms_bonus_ticket t where ")
				.append("t.lottery_code in ('006','007','009','010','011','012','013','101','102','103','104','105','106','107')")
				.append(" and t.create_time >= to_date('")
				.append(yes)
				.append("','yyyy-mm-dd hh24:mi:ss') and t.create_time < to_date('")
				.append(now)
				.append("','yyyy-mm-dd hh24:mi:ss')")	
				.append("and  t.bonus_amount >= 10000");
		
		StringBuffer bSql = new StringBuffer("(");
		bSql.append(amountSql).append(") union (").append(bonusSql).append(")");
		
		logger.info("bSql:" + bSql.toString());
		
		statement = conn.createStatement();
		resultSet = statement.executeQuery(bSql.toString());
		while (resultSet.next()) {
			String lottery_code = resultSet.getString("lottery_code");
			String issue = resultSet.getString("issue");
			String ticket_id = resultSet.getString("ticket_id");
			String post_code = resultSet.getString("post_code");
			String bounus_time = resultSet.getString("bonus_time");
			String fix_bonus_amount = resultSet.getString("fix_bonus_amount");
			String bonus_amount = resultSet.getString("bonus_amount");
			String tax_amount = new BigDecimal(fix_bonus_amount).subtract(new BigDecimal(bonus_amount)).toString();
			
			count ++;
			
			result.append("票号：").append(ticket_id).append("，");
			result.append("彩种：").append(LotteryList.getLotteryBean(lottery_code).getLotteryClass().getName()).append("(").append(lottery_code).append(")，");
			result.append("期次：").append(issue).append("，");
			result.append("开奖时间：").append(bounus_time).append("，");
			result.append("出票口：").append(PostMap.getPost(post_code).getName()).append("(").append(post_code).append(")，");
			result.append("税前奖金：").append(fix_bonus_amount).append("，");
			result.append("税后奖金：").append(bonus_amount).append("，");
			result.append("税额：").append(tax_amount);
			result.append(";<br/>");
			
			
		}
		
		
		String body = result.toString();
		if (Utils.isNotEmpty(body)) {
			String msg = "";
			if(count > 0){
				msg = "tob-有(" + count + ")张大奖票";
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
			printWriter.print(body);
		} else {
			printWriter.print("true");
		}
		printWriter.flush();
		printWriter.close();
		logger.info("monitorBonusAcount end");
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
