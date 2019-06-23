<%@page import="com.cndym.sms.SmsClient"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.cndym.email.MailEngine" %>
<%@ page import="com.cndym.utils.*" %>
<%@ page import="com.cndym.email.SmsEngine" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    PrintWriter printWriter = response.getWriter();
    String ip = Utils.formatStr(request.getParameter("ip"));
    String mobile = Utils.formatStr(request.getParameter("mobile"));
    String email = Utils.formatStr(request.getParameter("email"));

    HttpClientUtils hc = new HttpClientUtils("http://" + ip + ":8161/admin/queues.jsp");
    String html = hc.httpClientGet();
    String str = PatternUtils.regFind(html, "<div class=\"body-content\">[^<]*</div>", 0);
    str = PatternUtils.regFind(str, ">.*<", 0);
    str = str.substring(1, str.length() - 1).trim();
    String body = str;
    if (Utils.isNotEmpty(body) && !"true".equals(body)) {
        final String memo = "消息队列(" + ip + ")出现消息堆积";
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
        printWriter.print("1111:"+body);
    } else {
        printWriter.print("true");
    }
    printWriter.flush();
    printWriter.close();
%>



