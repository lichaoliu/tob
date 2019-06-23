<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.sql.*"%>
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
    PrintWriter printWriter = response.getWriter();

    String mobile = Utils.formatStr(request.getParameter("mobile"));
    String email = Utils.formatStr(request.getParameter("email"));

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;
    //用户名，订单，订单状态，投注金额，投注时间，投注方式
    try {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        StringBuilder result = new StringBuilder();
        String sql = "select m.name,t.ticket_id,t.lottery_code,t.issue,t.amount,t.create_time " +
                "from tms_ticket t,user_member m " +
                "where m.user_code = t.user_code and t.ticket_status in (4,5) " +
                "and sysdate - t.create_time<=numtodsinterval(3,'MINUTE')";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        int count = 0;
        Map<String,Integer> countMap = new HashMap<String,Integer>();
        while (resultSet.next()) {
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
            Long createTime = resultSet.getTimestamp("create_time").getTime();
            result.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(createTime)));
            result.append(";<br/>");
            
            String lotteryCode = resultSet.getString("lottery_code");
            int lotteryCount = countMap.get(lotteryCode) == null ? 0: countMap.get(lotteryCode);
            lotteryCount++;
            countMap.put(lotteryCode, lotteryCount);
            count++;
        }
        String body = result.toString();
        if (Utils.isNotEmpty(body)) {
            String memo = "tob-投注失败有:";
            for (String lotteryCode : countMap.keySet()) {
            	memo = memo + "("+ lotteryCode +")"+ countMap.get(lotteryCode) +"张票;";
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
            printWriter.print(body);
        } else {
            printWriter.print("true");
        }
        printWriter.flush();
        printWriter.close();
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
