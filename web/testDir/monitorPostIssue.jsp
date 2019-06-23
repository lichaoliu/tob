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
<%@ page import="com.cndym.control.PostMap"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
	final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
	final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
	final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
	Logger logger = Logger.getLogger(getClass());
	
	XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
	//String lotteryCodes = "'001','002','004','108','109','110','113'";
	String lotteryCodes = "'001','004','110','113'";
	//获取当前星期
	String weekDay = Utils.getWeekByDate(new Date());
	String email = Utils.formatStr(request.getParameter("email"));
	String mobile = Utils.formatStr(request.getParameter("mobile"));
	Connection conn = null;
	Statement statement = null;
	ResultSet resultSet = null;
	StringBuffer result = new StringBuffer();
	StringBuffer lotteryResult = new StringBuffer();
	try {
		logger.info("monitorPostIssue start");
		PrintWriter printWriter = response.getWriter();
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, username, password);
		
		StringBuffer postSql = new StringBuffer();
		StringBuffer issueSql = new StringBuffer();
		postSql.append("select distinct t5.lottery_code,t5.post_code from (")
			   .append("(select distinct t.lottery_code,t.post_code from tms_control_weight t where t.lottery_code in ("+lotteryCodes+"))")
			   .append("union")
			   .append(" (select distinct t2.lottery_code, t1.post_code from tms_weight_sid t1, tms_weight_play t2 where t1.play_id = t2.id and t2.lottery_code in ("+lotteryCodes+"))")
			   .append("union")
			   .append(" (select distinct t2.lottery_code, t3.post_code from tms_weight_time t1, tms_weight_play t2, tms_weight_rule t3 where t1.play_id = t2.id and t3.time_id = t1.id and t2.lottery_code in ("+lotteryCodes+"))")
			   .append(") t5");
		
		logger.info("monitorPostIssue postSql:" + postSql.toString());
		statement = conn.createStatement();
		resultSet = statement.executeQuery(postSql.toString());
		Map<String,String> lotteryPost = new HashMap();
		while (resultSet.next()) {
			String lottery_code = resultSet.getString("lottery_code");
			String post_code = resultSet.getString("post_code");
			if("99".equals(post_code)){
				continue;
			}
			String postCodes = lotteryPost.get(lottery_code);
			if(!Utils.isNotEmpty(postCodes)){
				lotteryPost.put(lottery_code,post_code);
			}else{
				lotteryPost.put(lottery_code,postCodes + "," + post_code);
			}
		}
		
		//如果是周二、四、日，双色球
		issueSql.append("select t.lottery_code,max(t.name) name from tms_post_issue t where t.status = '3' and t.lottery_code in('002','108','109'");
		if("星期二星期四星期日".contains(weekDay)){
			issueSql.append(",'001'");
		}
		
		if("星期一星期三星期五".contains(weekDay)){
			issueSql.append(",'004'");
		}
		
		if("星期二星期五星期日".contains(weekDay)){
			issueSql.append(",'110'");
		}
		
		if("星期一星期三星期六".contains(weekDay)){
			issueSql.append(",'113'");
		}
		
		issueSql.append(") group by t.lottery_code");
		logger.info("monitorPostIssue issueSql:" + issueSql.toString());
		resultSet = statement.executeQuery(issueSql.toString());
		int count = 0;
		while (resultSet.next()) {
			String lottery_code = resultSet.getString("lottery_code");
			String issue = resultSet.getString("name");
			String newIssue = "";
			String lastThree = "";
			String newIssueStart = "";
			if("001".equals(lottery_code)||"004".equals(lottery_code)){
				lastThree = issue.substring(4);
				newIssueStart = issue.substring(0,4);
			}else{
				lastThree = issue.substring(2);
				newIssueStart = issue.substring(0,2);
			}
			
			if(lastThree.startsWith("0")){
				lastThree = lastThree.substring(1);
				if(lastThree.startsWith("0")){
					lastThree = lastThree.substring(1);
				}
			}
			
			Integer n = Utils.formatInt(lastThree,0) + 1;
			if(n < 10){
				newIssue = newIssueStart + "00" + n;
			}else if(n >= 10 && n <= 99){
				newIssue = newIssueStart + "0" + n;
			}else{
				newIssue = newIssueStart + n;
			}
			
			String postCodes = lotteryPost.get(lottery_code);
			if(Utils.isNotEmpty(postCodes)){
				StringBuffer innerResult = new StringBuffer();
				int innerCount = 0;
				innerResult.append("彩种：").append(LotteryList.getLotteryBean(lottery_code).getLotteryClass().getName()).append("(").append(lottery_code).append(")，");
				String[] postCodeArray = postCodes.split(",");
				String cachePostCodes = memcachedClientWrapper.get(lottery_code + newIssue);
				logger.info("monitorPostIssue newIssue:" + (lottery_code + newIssue) + ",cachePostCodes:" + cachePostCodes + ",postCodes:" + postCodes);
				if(Utils.isNotEmpty(cachePostCodes)){
					for(String postCode : postCodeArray){
						if(!cachePostCodes.contains(postCode)){
							innerResult.append("出票口：").append(PostMap.getPost(postCode).getName()).append("(").append(postCode).append(")，");
							innerCount ++;
						}
					}
				}else{
					for(String postCode : postCodeArray){
						innerResult.append("出票口：").append(PostMap.getPost(postCode).getName()).append("(").append(postCode).append(")，");
							innerCount ++;
					}
				}
				if(innerCount > 0){
					lotteryResult.append(lottery_code).append(",");
					count = count + innerCount;
					result.append(innerResult)
						  .append("不能获取新期次：")
						  .append(newIssue)
					      .append(";<br/>");
				}
				
			}
			
		 }
		
		String body = result.toString();
		logger.info("monitorPostIssue body:" + body + ",count:" + count);
		if (Utils.isNotEmpty(body) && count > 0) {
			String msg  = "tob-有(" + count + ")个出票口不能获取彩种("+lotteryResult.deleteCharAt(lotteryResult.length() - 1)+")的新期次";
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
		printWriter.flush();
		printWriter.close();
		logger.info("monitorPostIssue end");
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
