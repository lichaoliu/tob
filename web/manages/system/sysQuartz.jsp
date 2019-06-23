<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>JOB状态</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/validator.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/page.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/manages.css">
    
    <style>
    	.content1, .sub {
		    font-family: Verdana, Arial, Helvetica, sans-serif;
		    font-size: 12px;
		    border-collapse: collapse;
		    background-color: #FFF;
		    margin-top: 5px;
		}
		.content1 td { border: 1px solid #aebdcc; }
	   .content1 tr.add td { background-color: #F5F5F5; }
		.content1 {
		    border-top: 1px #aebdcc solid;
		    border-left: 1px #aebdcc solid;
		}
    </style>
</head>

<body>
<c:if test="${param.action=='edit'}">
    ${myf:updateLock(param.name)}
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            系统状态监控 >> JOB监控
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content1">
                <tr style="display: none"/>
                <%--<tr>--%>
                    <%--<td colspan="6" align="right"><a target="_blank" href="${pageContext.request.contextPath}/manages/systemManagesServlet?action=exceptionHandle11x5Open">11选5追号异常处理</a></td>--%>
                <%--</tr>--%>
                <font size="1">说明：锁定状态正常情况为<font color="green" size="1">绿色</font>，JOB任务处于运行状态为<font color="red" size="1">红色</font>，当JOB任务启用时启用状态为<font color="green" size="1">绿色</font>，停用时启用状态为<font color="red" size="1">红色</font>，其他项当JOB任务锁定三分钟以上为<font color="red" size="1">红色</font></font>
                <tr style="background-color: #F5F5F5;">
                    <td width="5%" align="center">
                        序号
                    </td>
                    <td width="20%" align="center">
                        名称
                    </td>
                    <td width="30%" align="center">
                        上次执行时间
                    </td>
                    <td width="10%" align="center">
                        距当前时间差
                    </td>
                    <td width="10%" align="center">
                        锁定状态
                    </td>
                                <td width="10%" align="center">
                        启用状态
                    </td>
                    <td align="center">
                        操作
                    </td>
                </tr>
                <c:set var="jobName" value=""/>
                <c:set var="jobCode" value=""/>
                <c:set var="currColor" value="background-color: #FFFFFF;"/>
                <c:set var="lastColor" value="background-color: #F0F0F0;"/>
                <c:forEach var="lock" items="${myf:getLock()}" varStatus="count">
                    <c:set var="redFlag" value="${myf:diffNowTimeSeconds(lock.createTime) >= 180 && lock.status==1}"/>
                    <c:if test="${jobName == fn:substring(lock.name, 0, 2) && jobCode == fn:substring(lock.code, 0, 2)}">
                    	<tr style="${lastColor}">
                    </c:if>
                    <c:if test="${jobName != fn:substring(lock.name, 0, 2) || jobCode != fn:substring(lock.code, 0, 2)}">
                    	 <tr style="${currColor}">
                    	 <c:set var="tempColor" value="${lastColor}"/>
                    	 <c:set var="lastColor" value="${currColor}"/>
                    	 <c:set var="currColor" value="${tempColor}"/>
                    	 <c:set var="jobName" value="${fn:substring(lock.name, 0, 2)}"/>
                    	 <c:set var="jobCode" value="${fn:substring(lock.code, 0, 2)}"/>
                    </c:if>
                        <td align="center">
                               <font ${redFlag ? "color=red" : ""}>${count.count}</font>
                        </td>
                        <td align="left">
                                <font ${redFlag ? "color=red" : ""}>${lock.code}</font>
                        </td>
                        <td align="center">
                            <font ${redFlag ? "color=red" : ""}><fmt:formatDate value="${lock.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
                        </td>
                        <td align="center">
                            <font ${redFlag ? "color=red" : ""}>${myf:diffNowTime(lock.createTime)}</font>
                        </td>
                        <td align="center">
                            <c:if test="${lock.status==0}">
								<font color="green">正常</font>
                            </c:if>
                            <c:if test="${lock.status==1}">
                                <font color="red">锁定</font>
                            </c:if>
                        </td>
                        
                        <td align="center">
                        	 <c:if test="${lock.stop==0}">
	                            	<font color="green">启用</font>
                            </c:if>
                            <c:if test="${lock.stop==1}">
                             <font color="red">停用</font>
							</c:if>
                        </td>
                        <td align="center">
                            <c:if test="${lock.status==1}">
                                <a href="javascript:if (ifLink('${lock.code}已被锁定${myf:diffNowTime(lock.createTime)}，你确认要继续\'解锁\'吗？')) {window.location.href='/manages/systemManagesServlet?action=unlockJobStatus&name=${lock.name}'}">解锁</a>
                            </c:if>
                            <c:if test="${lock.status==0}">
                                <a href="javascript:if (ifLink('${lock.code}正在工作${myf:diffNowTime(lock.createTime)}，你确认要继续\'锁定\'吗？')) {window.location.href='/manages/systemManagesServlet?action=lockJobStatus&name=${lock.name}'}">锁定</a>
                            </c:if>
                             &nbsp;&nbsp;&nbsp;&nbsp;
                             <c:if test="${lock.stop==1}">
                                <a href="javascript:if (ifLink('${lock.code}已被停用${myf:diffNowTime(lock.createTime)}，你确认要继续\'启用\'吗？')) {window.location.href='/manages/systemManagesServlet?action=startJob&name=${lock.name}'}">启用</a>
                            </c:if>
                            <c:if test="${lock.stop==0}">
                                <a href="javascript:if (ifLink('${lock.code}正在使用${myf:diffNowTime(lock.createTime)}，你确认要继续\'停用\'吗？')) {window.location.href='/manages/systemManagesServlet?action=stopJob&name=${lock.name}'}">停用</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>