<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.cndym.utils.Md5"%>
<%@ page import="com.cndym.utils.Utils"%>
<%@ page import="com.cndym.utils.HttpClientUtils"%>
<%@ page import="com.cndym.xmlObject.Header"%>
<%@ page import="com.cndym.xmlObject.BuilderXml"%>
<%@ page import="com.cndym.utils.OrderIdBuildUtils"%>
<%@ page contentType="text/html;charset=utf8"%>
<%
String sid = "800001";
String cmd = "001";
String digestType = "md5";
String url = "http://119.254.92.197/interface";
String userInfo = "中彩汇$130721198009276018$18611988049";

String lotteryCode = request.getParameter("lotteryId");
String playCode = request.getParameter("playId");
String issue = request.getParameter("issue");
String ticket = request.getParameter("ticket");
String orderId = OrderIdBuildUtils.buildOrderId();

StringBuffer order = new StringBuffer();
order.append("<order userInfo=\"").append(userInfo).append("\"");
order.append(" lotteryId=\"").append(lotteryCode).append("\"");
order.append(" issue=\"").append(issue).append("\"");
order.append(" playId=\"").append(playCode).append("\"");
order.append(" orderId=\"").append(orderId).append("\">");
order.append(ticket);
order.append("</order>");

try {
	StringBuffer header = new StringBuffer();
	String body = "<body>"+order.toString()+"</body>";
	String digest = Md5.Md5(sid+"922wasimd4"+body);
	header.append("<message><header><sid>").append(sid).append("</sid>");
	header.append("<cmd>"+cmd).append("</cmd><digestType>").append(digestType+"</digestType>");
	header.append("<digest>" + digest + "</digest></header>");
	String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + header.toString() + body + "</message>";

	Map<String, String> params = new HashMap<String, String>();
	out.println(msg);
	params.put("msg", msg);
	HttpClientUtils hcu = new HttpClientUtils(url, "utf-8", params);
	out.println(hcu.httpClientRequest());
} catch(Exception e) {
	e.printStackTrace();
	out.println("no");
}
%>