<%@ page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.email.MailEngine"%>
<%@ page import="com.cndym.utils.SpringUtils"%>
<%@ page import="com.cndym.utils.ConfigUtils"%>
<%@ page import="com.cndym.email.SmsEngine"%>
<%@ page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@ page contentType="text/html;charset=utf8"%>
<%
	final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
    final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
    final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
    final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
    PrintWriter printWriter = response.getWriter();
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
		int minute = calendar.get(Calendar.MINUTE);

        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        StringBuilder result = new StringBuilder();
        String sql = "select lottery_code,issue,sn,end_time from tms_sub_issue_for_jczq where play_code='00' " +
                     "and sysdate-update_time<=numtodsinterval(1,'MINUTE') and end_operator=2";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        int count = 0;
        int countAward = 0;

        while (resultSet.next()) {
        	String lotteryCode = resultSet.getString("lottery_code");
        	String issue = resultSet.getString("issue");
        	String sn = resultSet.getString("sn");
            result.append("彩种:").append(lotteryCode);
            result.append(",期次:").append(issue);
            result.append(",场次:").append(sn);
            result.append(",开赛时间:").append(resultSet.getString("end_time"));
            result.append(";<br/>");
            count++;
        }
        String body = result.toString();
        if (Utils.isNotEmpty(body)) {
            String memo = "tob-竞彩足球:有("+count+")场取消请确认是否正常。";
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
        logger.info("end");
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
%>
