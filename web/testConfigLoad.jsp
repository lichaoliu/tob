<%@page import="com.cndym.sendClient.hlj.HljSendClientConfig"%>
<%@page import="com.cndym.sendClient.yc.YcSendClientConfig"%>
<%@page import="com.cndym.sendClient.hb.HbSendClientConfig"%>
<%@page import="com.cndym.utils.ConfigUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

String post_url = HbSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
out.println("post_url="+post_url+"=<br/>");
HbSendClientConfig.forInstance();
post_url = HbSendClientConfig.getValue("POST_CODE_GP_LOTTERY");
out.println("post_url="+post_url+"=<br/>");
/**

out.println("post_url="+post_url+"=");
out.println("------------------------------</br>");
ConfigUtils.forInstance();
post_url = ConfigUtils.getValue("ALLOW_CANCEL_RESEND_ORDER_LOTTERY");
out.println("post_url="+post_url+"=");
out.println("------------------------------");
*/
%>
