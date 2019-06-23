<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="com.cndym.servlet.ElTagUtils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=utf8" %>
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
    //期次，彩种，状态
    try {
        logger.info("start");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Map<String,Integer> countMap = new HashMap<String,Integer>();
        String lotteryCode = null;
        String endTime = null;
        String startTime = null;
        
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        StringBuilder result = new StringBuilder();
        String sql = "select i.name,i.lottery_code,i.start_time,i.end_time from tms_main_issue i where i.status=3 and i.bonus_status=0 and sysdate - i.end_time >= numtodsinterval(5,'MINUTE')";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            lotteryCode = resultSet.getString("lottery_code");

            if(1 == ElTagUtils.lotteryControl(lotteryCode) || 1 == ElTagUtils.lotterySendControl(lotteryCode)) {
                logger.info(lotteryCode);
				continue;
            }

            Long diffSale = resultSet.getTimestamp("end_time").getTime() - resultSet.getTimestamp("start_time").getTime();
            Long diffEnd = new Date().getTime() - resultSet.getTimestamp("end_time").getTime();
            logger.info("lotteryCode="+lotteryCode+","+resultSet.getString("name")+",diffSale="+diffSale+",nowTime="+diffEnd);

            if ("001,002,004,108,109,110,113,200,201".contains(lotteryCode) && minute % 5 != 0) {
            	continue;
            }

            if ((lotteryCode.startsWith("20")) && diffEnd < (6 * 24 * 3600 * 1000)) {
                continue;
            } else if (diffSale > (20 * 3600 * 1000) && diffEnd < (4 * 3600 * 1000)) {
                continue;
            }

            result.append("彩种：");
            result.append(lotteryCode);
            result.append("，期次：");
            result.append(resultSet.getString("name"));
            result.append("，结期时间：");
            result.append(resultSet.getString("end_time"));
            result.append(";<br/>");

            int lotteryCount = countMap.get(lotteryCode) == null ? 0: countMap.get(lotteryCode);
            lotteryCount++;
            countMap.put(lotteryCode, lotteryCount);
        }
        String body = result.toString();
        if (Utils.isNotEmpty(body)) {
            String memo = "tob-未派奖:";
            for (String lotteryCode1 : countMap.keySet()) {
                memo = memo + "("+ lotteryCode1 +")"+ countMap.get(lotteryCode1) +"期;";
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
        logger.info("end");
    }
%>
