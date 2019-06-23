<%@page import="com.cndym.utils.SpringUtils"%>
<%@page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@page import="com.cndym.Buy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<body>
<%
XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
memcachedClientWrapper.getMemcachedClient().set("0012013048", 0, "08,12");
String canSellPost = (String) memcachedClientWrapper.getMemcachedClient().get("0012013048");
//memcachedClientWrapper.getMemcachedClient().delete("10813030");
%>
<form action="/interface" method="post" id="form1" name="form1">
    <textarea rows="30" cols="100" name="msg"><%=canSellPost %>
    </textarea><br/>
    <input name="submit" type="submit" value="提交"/>
</form>
</body>
</html>