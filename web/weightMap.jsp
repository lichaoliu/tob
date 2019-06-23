<%@page import="com.cndym.control.PostCodeUtil"%>
<%@page import="com.cndym.utils.SpringUtils"%>
<%@page import="com.cndym.cache.XMemcachedClientWrapper"%>
<%@ page import="java.util.*" %>
<%@ page import="com.cndym.control.bean.Post"%>
<%@ page import="com.cndym.control.bean.Weight"%>
<%@ page import="com.cndym.control.WeightMap"%>
<%@ page import="com.cndym.control.PostMap"%>
<%@ page import="com.cndym.utils.JsonBinder" %>
<%@ page import="org.codehaus.jackson.map.ObjectMapper" %>
<%@ page import="org.codehaus.jackson.type.TypeReference" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

WeightMap.forInstance();
XMemcachedClientWrapper memcachedClientWrapper = (XMemcachedClientWrapper) SpringUtils.getBean("xmemcachedClientWrapper");
Map<String, Weight> weightMap = new HashMap<String, Weight>();
List<Post> postList = null;
out.println("<br/>------------------------------");
String weightJson = (String) memcachedClientWrapper.getMemcachedClient().get("weight_map_value");
out.println("<br/>weight_map_value="+weightJson);
try {
	weightMap = JsonBinder.buildNormalBinder().getMapper().readValue(weightJson, new TypeReference<Map<String, Weight>>() { } );
	
	postList = PostMap.postList();
} catch(Exception e) {
	e.printStackTrace();
}

Iterator<String> iter = weightMap.keySet().iterator();
Weight weight = null;
while (iter.hasNext()) {

    String key = iter.next();
    weight = weightMap.get(key);
    if (weight != null){
		out.println("<br/>lotteryCode="+key+",postCode="+weight.getDefaultPostCode());
    }
}

for (Post post : postList) {
	out.println("<br/>code="+post.getCode()+",name="+post.getName());
}

out.println("<br/>------------------------------");

%>
