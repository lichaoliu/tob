<%@page import="com.cndym.control.PostCodeUtil"%>
<%@page import="com.cndym.utils.SpringUtils"%>
<%@page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@page import="com.cndym.Buy" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

StringBuffer key = new StringBuffer("lottery.").append("113").append(".control");
XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
String value = memcachedClientWrapper.get(key.toString());


out.println(value);
out.println("------------------------------");
String canSellPost = (String) memcachedClientWrapper.getMemcachedClient().get("11314002");
out.println("canSellPost="+canSellPost);
out.println("------------------------------");
String cache = (String) memcachedClientWrapper.getMemcachedClient().get("memcached.current.issue.list");
out.println("memcached.current.issue.list="+cache);
out.println("------------------------------");
cache = (String) memcachedClientWrapper.getMemcachedClient().get("memcached.sale.issue.list");
out.println("memcached.sale.issue.list="+cache);
cache = (String) memcachedClientWrapper.getMemcachedClient().get("memcached.send.order.009");
out.println("memcached.send.order="+cache);

out.println("------------------------------");
out.println(PostCodeUtil.getCanSellPostCode("113","14002"));
out.println("------------------------------");

value = memcachedClientWrapper.get("RESENDDENDLINE10814026");

out.println("value="+value);
out.println("</br>------------------------------");
%>
