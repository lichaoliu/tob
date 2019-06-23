<%--
  Created by IntelliJ IDEA.
  User: MengJingyi
  QQ: 116741034
  Date: 13-4-18
  Time: 下午2:10
  To change this template use File | Settings | File Templates.
--%>
<%@page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    final String driverName = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.DRIVERCLASSNAME");
    final String url = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.URL");
    final String username = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.USERNAME");
    final String password = com.cndym.utils.ConfigUtils.getValue("ORACLE.DATASOURCE.PASSWORD");

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;

    Connection conn2 = null;
    Statement statement2 = null;

    try {
        long interval = 8;
        int myCount = 5;
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, username, password);
        DecimalFormat decimalFormat = new DecimalFormat("##############.##");
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMinimumIntegerDigits(1);
        StringBuffer sql = new StringBuffer("select oba.sid,um.name,ua.recharge_amount as rechargeAmount,ua.bonus_amount as bonusAmount," +
                "oba.balance_amount balanceAmount ,oba.update_time updateTime ,oba.count obaCount,oba.mobile mobile," +
                "oba.email email,oba.context obaContext from user_member um,user_account ua,other_balance_alarm oba " +
                "where um.user_code = ua.user_code and um.status = 1 and oba.sid=um.sid " +
                "and (ua.recharge_amount + ua.bonus_amount) < oba.balance_amount ");
        statement = conn.createStatement();
        resultSet = statement.executeQuery(sql.toString());
        while (resultSet.next()) {
            int count = resultSet.getInt("obaCount");
            Date updateTime = resultSet.getTimestamp("updateTime");
            String sid = resultSet.getString("sid");
            if (count >= myCount) {
                long diff = new Date().getTime() - updateTime.getTime();
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);
                if (diffDays > 0 || diffHours >= interval) {
                    conn2 = DriverManager.getConnection(url, username, password);
                    String s = "update other_balance_alarm o set o.count=0 where sid='" + sid + "'";
                    statement2 = conn2.createStatement();
                    statement2.executeUpdate(s);
                    if (null != statement2) {
                        try {
                            statement2.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != conn2) {
                        try {
                            conn2.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                continue;
            }
            String name = resultSet.getString("name");
            String context = "(" + name + ")" + resultSet.getString("obaContext");
            String emails = resultSet.getString("email");
            String mobiles = resultSet.getString("mobile");
            MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
            if (Utils.isNotEmpty(emails)) {
                final String memo = "接入商可用额度提醒";
                String[] emailArr = emails.split(",");
                try {
                    mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, context, memo, null);
                    conn2 = DriverManager.getConnection(url, username, password);
                    String s = "update other_balance_alarm o set o.count=" + (count + 1) + ",o.update_time=sysdate where sid='" + sid + "'";
                    statement2 = conn2.createStatement();
                    statement2.executeUpdate(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (Utils.isNotEmpty(mobiles)) {
                try {
                    SmsClient.sendSMS(mobiles, context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        StringBuffer buffer = new StringBuffer();
        if (Utils.isNotEmpty(buffer)) {
            out.print(buffer.toString());
        } else {
            out.print("true");
        }
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
        if (null != statement2) {
            try {
                statement2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != conn2) {
            try {
                conn2.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>