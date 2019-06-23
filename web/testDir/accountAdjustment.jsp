<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
    final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
    final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
    final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");
    PrintWriter printWriter = response.getWriter();
    Logger logger = Logger.getLogger(getClass());

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try {
    	logger.info("start");
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        DecimalFormat decimalFormat = new DecimalFormat("##############.##");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMinimumIntegerDigits(1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);
        StringBuilder result = new StringBuilder();
        String sql = "select t.operator as operator,m.name,t.recharge_amount as rechargeAmount,t.bonus_amount as bonusAmount,t.create_time as createTime from user_adjust_account t,user_member m where m.user_code=t.user_code and sysdate - t.create_time<=numtodsinterval(2,'MINUTE')";
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql);
        StringBuffer sub = new StringBuffer();
        while (resultSet.next()) {
            String operator = resultSet.getString("operator");
            String userName = resultSet.getString("name");
            String rechargeAmount = decimalFormat.format(resultSet.getDouble("rechargeAmount"));
            String bonusAmount = decimalFormat.format(resultSet.getDouble("bonusAmount"));
            Long createTime = resultSet.getTimestamp("createTime").getTime();
            sub.append(";(").append(operator).append(")于(").append(Utils.formatDate2Str(new Date(createTime), "yyyy-MM-dd HH:mm:ss")).append(")调整(").append(userName).append(")");
            if (!rechargeAmount.equals("0.00")) {
                sub.append("充值金: ").append(rechargeAmount);
            }
            if (!bonusAmount.equals("0.00")) {
                sub.append("奖金: ").append(bonusAmount);
            }
        }
        if (sub.length() > 0) {
            result.append(sub.substring(1));
        }
        String mobile = Utils.formatStr(request.getParameter("mobile"));
        String email = Utils.formatStr(request.getParameter("email"));
        MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
        if (result.length() > 0) {
            if (Utils.isNotEmpty(email)) {
                final String memo = "tob-额度调整确认";
                String[] emailArr = email.split(",");
                try {
                	logger.info("mail:"+email+","+memo+":"+result.toString());
                    mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, result.toString(), memo, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (null != mobile && !"".equals(mobile)) {
                if (Utils.isNotEmpty(mobile)) {
                    try {
                    	logger.info("sms:"+mobile+","+result.toString());
                        SmsClient.sendSMS(mobile, result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            printWriter.print(result.toString());
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
