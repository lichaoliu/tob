<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.cndym.sms.SmsClient"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.email.MailEngine"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.cndym.email.SmsEngine"%>
<%@ page import="com.cndym.constant.Constants"%>
<%@ page import="com.cndym.bean.tms.MainIssue"%>
<%@ page import="com.cndym.utils.JsonBinder"%>
<%@ page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@ page import="org.codehaus.jackson.type.TypeReference"%>
<%@ page contentType="text/html;charset=utf8"%>
<%
	Map<String, MainIssue> mainIssueMap = new HashMap<String, MainIssue>();
	try {
		XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
		String issueJson = (String) memcachedClientWrapper.getMemcachedClient().get(Constants.MEMCACHED_CURRENT_ISSUE_LIST);
	    if (null != issueJson) {
			List<MainIssue> mainIssueList = JsonBinder.buildNormalBinder().getMapper().readValue(issueJson, new TypeReference<List<MainIssue>>() {});
				if (null != mainIssueList) {
					for (MainIssue mainIssue : mainIssueList) {
						mainIssueMap.put(mainIssue.getLotteryCode(), mainIssue);
				}
			}
        }
	} catch (Exception e) {
        e.printStackTrace();
	}

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
    //用户名，订单，订单状态，投注金额，投注时间，投注方式
    try {
    	logger.info("start");
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        StringBuilder result = new StringBuilder();
        String sql = "select m.name,t.ticket_id,t.lottery_code,t.issue,t.amount,t.create_time,t.send_time,t.backup1 " +
        			 "from tms_ticket t, user_member m where " +
        			 "t.ticket_status=2 and t.lottery_code in ('006','007','009','010','011','012','013','102','103','104','105','106','107') " +
        			 "and sysdate - t.create_time >= numtodsinterval(1,'MINUTE') and m.user_code = t.user_code ";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        int count = 0;
        Map<String,Integer> countMap = new HashMap<String,Integer>();
        MainIssue mainIssue = null;
        String lotteryCode = null;
        String backup1 = null;
        String issue = null;
        Long diffTime = 3 * 60 * 1000L;
        //logger.info("while-start");
        while (resultSet.next()) {
        	lotteryCode = resultSet.getString("lottery_code");
        	backup1 = resultSet.getString("backup1");
        	mainIssue = mainIssueMap.get(lotteryCode);
            Long createTime = resultSet.getTimestamp("create_time").getTime();
			issue = resultSet.getString("issue");

        	if (Utils.isNotEmpty(mainIssue)) {
	        	Long startTime = mainIssue.getStartTime().getTime();
	        	if ("013,103,105,107".contains(lotteryCode) && mainIssue.getEndTime().getTime() - new Date().getTime() > 10 * 60 * 1000L) {
	        		logger.info(lotteryCode+","+issue);
	        		continue;
	        	}
	        	if (new Date().getTime() - startTime < diffTime) {
	        		logger.info(lotteryCode+"="+resultSet.getString("ticket_id"));
	        		continue;
	        	}
        	}

            result.append("用户:");
            result.append(resultSet.getString("name"));
            result.append(",票号:");
            result.append(resultSet.getString("ticket_id"));
            result.append(",彩种:");
            result.append(resultSet.getString("lottery_code"));
            result.append(",期次:");
            result.append(resultSet.getString("issue"));
            result.append(",金额:");
            result.append(resultSet.getString("amount"));
            result.append(",创建:");
            result.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(createTime)));
            result.append(";<br/>");

            int lotteryCount = countMap.get(lotteryCode) == null ? 0: countMap.get(lotteryCode);
            lotteryCount++;
            countMap.put(lotteryCode, lotteryCount);
            count++;
        }
        //logger.info("while-end");
        String body = result.toString();
        if (Utils.isNotEmpty(body)) {
            String memo = "tob-高频送票未回执:";
            for (String lotteryCode1 : countMap.keySet()) {
            	memo = memo + "("+ lotteryCode1 +")"+ countMap.get(lotteryCode1) +"张;";
            }
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
        }
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (Exception e) {
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
    logger.info("end");
%>
