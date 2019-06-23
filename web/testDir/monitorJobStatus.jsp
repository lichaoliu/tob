<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=utf8" %>
<%
	Logger logger = Logger.getLogger(getClass());
	Map<String, Long> jobMap = new HashMap<String, Long>();
	jobMap.put("cancelOrderQuartz", 3L);
	jobMap.put("jingJiIssueEndQuartz", 3L);
	jobMap.put("ltkjIssueQuery", 5L);
	jobMap.put("accountTableQuartz", 1450L);


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
    try {
    	logger.info("start");
    	Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);

        StringBuilder result = new StringBuilder();
        String sql = "select sdl.name,sdl.create_time,sysdate from sys_distribution_lock sdl where sdl.create_time<=sysdate-numtodsinterval(1,'MINUTE')";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        int count = 0;
        while (resultSet.next()) {
        	String name = resultSet.getString("name");
        	if (jobMap.containsKey(name)) {
        		Long alarmMinute = jobMap.get(name);
           		Long diffSale = resultSet.getTimestamp("sysdate").getTime() - resultSet.getTimestamp("create_time").getTime();
           		if (alarmMinute * 60 * 1000 > diffSale) {
           			continue;
           		} 
			} else if (!name.contains("Gp") && name.endsWith("OrderQuery") && hour < 9) {
				continue;
			}
            result.append("名称:");
            result.append(resultSet.getString("name"));
            result.append(",上次时间:");
            result.append(resultSet.getString("create_time"));
            result.append(",当前系统时间:");
            result.append(resultSet.getString("sysdate"));
            result.append(";<br/>");
            count++;
        }

        String body = result.toString();
        if (Utils.isNotEmpty(body)) {
            final String memo = "tob-job监控列表中有(" + count + ")个job运行异常请查看.";
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
