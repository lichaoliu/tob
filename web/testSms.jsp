<%@page import="com.cndym.sms.SmsClient"%>
<%@page import="com.cndym.utils.SpringUtils"%>
<%@page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@page import="com.cndym.sendClient.fcbynew.FcbySendClientConfig"%>
<%@page import="com.cndym.sendClient.rlyg.RlygSendClientConfig"%>
<%@page import="com.cndym.utils.ConfigUtils"%>
<%@page import="com.cndym.control.WeightMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<%
		SmsClient.sendSMS("15611230028,18611988049", "test..............");
	%>
</body>
</html>