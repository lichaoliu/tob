<%@ page import="com.cndym.jms.producer.GateWayOrderMessageProducer" %>
<%@ page import="com.cndym.utils.SpringUtils" %>
<%@ page import="com.cndym.utils.Utils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>手工发送批次</title></head>
<body>
<%
    String lotteryCode = Utils.formatStr(request.getParameter("lotteryCode"));
    String sendOrderId = Utils.formatStr(request.getParameter("sendOrderId"));
    String sid = Utils.formatStr(request.getParameter("sid"));
    if (null != lotteryCode && null != sendOrderId && null != sid) {
        GateWayOrderMessageProducer gatewayOrderMessageProducer = (GateWayOrderMessageProducer) SpringUtils.getBean("gateWayOrderMessageProducer");
        gatewayOrderMessageProducer.sendMessage(lotteryCode, sendOrderId, sid);
    }
%>
</body>
</html>