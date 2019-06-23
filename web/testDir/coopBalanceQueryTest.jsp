<%@ page import="org.apache.log4j.Logger"%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=utf8" %>
<%
Logger logger = Logger.getLogger(getClass());
final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
String mobile = Utils.formatStr(request.getParameter("mobile"));
String email = Utils.formatStr(request.getParameter("email"));

Double commonAlarmAmount = 0D;
Map<String, Double> alarmAmountMap = new HashMap<String, Double>();
alarmAmountMap.put("800001", 200000D);//竞彩网络

Connection conn = null;
Statement statement = null;
ResultSet resultSet = null;
try {
    logger.info("start");
    MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
	Class.forName(driverName);
	conn = DriverManager.getConnection(url, username, password);
	StringBuffer sql = new StringBuffer("select um.sid,um.name,(ua.recharge_amount+ua.bonus_amount) as amount from user_member um,user_account ua where um.user_code = ua.user_code and um.status = 1");

	statement = conn.createStatement();
	resultSet = statement.executeQuery(sql.toString());
	StringBuffer sub = null;
	while (resultSet.next()) {
		sub = new StringBuffer();
		String name = resultSet.getString("name");
		String sid = resultSet.getString("sid");
		double amount = resultSet.getDouble("amount");
        double alarmAmount = Utils.formatDouble(alarmAmountMap.get(sid), commonAlarmAmount);

		sub.append("tob-截止").append(Utils.today("MM-dd HH:mm:ss"));
		sub.append("(").append(name).append(")").append("余额：");
		sub.append(resultSet.getString("amount")).append("元，请及时加款。");

		if (alarmAmount >= amount && alarmAmount > 0) {
			if (Utils.isNotEmpty(email)) {
				String[] emailArr = email.split(",");
				try {
                    mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, sub.toString(), sub.toString(), null);
                } catch (Exception e) {
                	logger.error("", e);
                }
            }

            if (null != mobile && !"".equals(mobile)) {
                if (Utils.isNotEmpty(mobile)) {
                    try {
                        SmsClient.sendSMS(mobile, sub.toString());
                    } catch (Exception e) {
                        logger.error("", e);
                    }
                }
            }
        } else if (Utils.isNotEmpty(alarmAmountMap.get(sid))) {
        	logger.info(sub.toString());
        }
    }
    logger.info("end");
} catch (ClassNotFoundException e) {
	logger.error("", e);
} catch (SQLException e) {
	logger.error("", e);
} finally {
    if (null != resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
        	logger.error("", e);
        }
    }
    if (null != statement) {
        try {
            statement.close();
        } catch (SQLException e) {
        	logger.error("", e);
        }
    }
    if (null != conn) {
        try {
            conn.close();
        } catch (SQLException e) {
        	logger.error("", e);
        }
    }
}
%>
