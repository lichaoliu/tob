<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.cndym.sms.SmsClient"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page contentType="text/html;charset=utf-8" %>
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
    //用户名，订单，订单状态，投注金额，投注时间，投注方式
    try {
    	logger.info("start");
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        StringBuilder result = new StringBuilder();
        String startTime = Utils.formatDate2Str(Utils.addDate(new Date(), "d", -1), "yyyyMMdd");
        String sql = "select t.sid,t.lottery_code,t.issue,t.ticket_id,t.amount,t.create_time " +
                "from tms_ticket t where t.create_time >= to_date('"+startTime+"','yyyymmdd') and " +
                "t.ticket_status in (0,1) and sysdate-t.create_time>=numtodsinterval(1,'MINUTE') ";
        logger.info("sql="+sql);
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        
        String lotteryCode = null;
        Map<String,Integer> countMap = new HashMap<String,Integer>();

        while (resultSet.next()) {
        	lotteryCode = resultSet.getString("lottery_code");

        	if(!Utils.isSendTicket(lotteryCode)) {
        		//continue;
        	}

            if (hour < 2 && week > 1 && "200,201".contains(lotteryCode)) {
                logger.info("week="+week+",hour="+hour);
                //continue;
        	}
            
        	//if (hour >= 8 || "200,201".contains(lotteryCode)) {
	            result.append("用户:");
	            result.append(resultSet.getString("sid"));
	            result.append(",票号:");
	            result.append(resultSet.getString("ticket_id"));
	            result.append(",金额:");
	            result.append(resultSet.getString("amount"));
	            result.append(",创建:");
	            result.append(resultSet.getString("create_time"));
	            result.append(",彩种:");
	            result.append(lotteryCode);
	            result.append(",期次:");
	            result.append(resultSet.getString("issue"));
	            result.append(";<br/>");
	
	            int lotteryCount = countMap.get(lotteryCode) == null ? 0: countMap.get(lotteryCode);
	            lotteryCount++;
	            countMap.put(lotteryCode, lotteryCount);
        	//}
        }
        String body = result.toString();
        if (Utils.isNotEmpty(body)) {
            String memo = "tob-未送或调度中的票:";
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
