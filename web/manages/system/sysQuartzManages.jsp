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
</head>

<body>
<c:if test="${param.action=='edit'}">
    ${myf:updateLock(param.name)}
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            系统状态监控 >> JOB监控设置
        </td>
        <td class="title" style="text-align:right">
            <a href="/manages/systemManagesServlet?action=getLock">添加JOB</a>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr style="display: none"/>
                <tr>
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
                        状态
                    </td>
                    <td align="center">
                        操作
                    </td>
                </tr>
                <c:forEach var="lock" items="${myf:getLock()}" varStatus="count">
                    <c:set var="redFlag" value="${myf:diffNowTimeSeconds(lock.createTime) >= 180 && lock.status==1}"/>
                    <tr>
                        <td align="center">
                            <font ${redFlag ? "color=red" : ""}>${count.count}</font>
                        </td>
                        <td align="left">
                            <font ${redFlag ? "color=red" : ""}>${lock.code}</font>
                        </td>
                        <td align="center">
                            <font ${redFlag ? "color=red" : ""}><fmt:formatDate value="${lock.createTime}"
                                                                                pattern="yyyy-MM-dd HH:mm:ss"/></font>
                        </td>
                        <td align="center">
                            <font ${redFlag ? "color=red" : ""}>${myf:diffNowTime(lock.createTime)}</font>
                        </td>
                        <td align="center">
                            <c:if test="${lock.status==0}">
                                正常
                            </c:if>
                            <c:if test="${lock.status==1}">
                                <font ${redFlag ? "color=red" : ""}>锁定</font>
                            </c:if>
                        </td>
                        <td>
                            <a href="javascript:if (ifLink('你确认要继续\'删除\'吗？')) {window.location.href='/manages/systemManagesServlet?action=deleteLock&name=${lock.name}'}">删除</a>&nbsp;|&nbsp;
                            <a href="/manages/systemManagesServlet?action=getLock&name=${lock.name}">修改</a>
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