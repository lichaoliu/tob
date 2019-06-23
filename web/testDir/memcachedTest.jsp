<%@page import="com.cndym.sms.SmsClient"%>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.ConfigUtils" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page import="com.cndym.cache.XMemcachedClientWrapper" %>
<%@ page import="java.io.PrintWriter"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%
Logger logger = Logger.getLogger(getClass());
String mobile = Utils.formatStr(request.getParameter("mobile"));
String email = Utils.formatStr(request.getParameter("email"));
final String key = "test_memcached_key";
XMemcachedClientWrapper memcachedClient = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
try {
	PrintWriter printWriter = response.getWriter();
    memcachedClient.set(key, 0, "true");
    String body = memcachedClient.get(key);
    if (!Utils.isNotEmpty(body)) {
        final String memo = "tob-Memcached异常";
        MailEngine mailEngine = (MailEngine) SpringUtils.getBean("mailEngine");
        if (Utils.isNotEmpty(email)) {
            String[] emailArr = email.split(",");
            try {
                mailEngine.sendMessage(emailArr, ConfigUtils.getValue("MAIL.DEFAULT.FROM"), null, body, memo, null);
            } catch (Exception e) {
            	logger.error("", e);
            }
        }

        if (Utils.isNotEmpty(mobile)) {
            try {
                SmsClient.sendSMS(mobile, memo);
            } catch (Exception e) {
            	logger.error("", e);
            }
        }
    } else {
    	printWriter.print("true");
    }
    printWriter.flush();
    printWriter.close();
} catch(Exception e) {
	logger.error("", e);
}
%>