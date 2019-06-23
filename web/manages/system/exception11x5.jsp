<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>11选5追号异常处理</title>
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

    <script type="text/javascript">

    </script>
</head>
<body>

<c:if test="${msg != null}">
    <script type="text/javascript">
        alert('${msg}');
    </script>
</c:if>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            11选5追号异常处理
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=exceptionHandle11x5Open">
                <table width="40%" align="center" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>网站前台销售期:${webIssue.name}</td>
                    </tr>
                    <tr>
                        <td>网站后台销售期:${nowIssue.name}</td>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>出票口销售期:${sendIssue.name}</td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>

                    </tr>
                    <tr>
                        <td align="center">
                            <input type="button" class="submit" value="确定" style="width: 64px; border: none"
                                   onclick="javascript:if (ifLink('执行此操作前请先解锁JOB，确定要执行此操作？')) {window.location.href='/manages/systemManagesServlet?action=exceptionHandle11x5ODo'}"/>
                            <input type="button" class="submit" value="关闭" style="width: 64px; border: none"
                               onclick="javascript:window.close();"/>
                        </td>
                    </tr>
                </table>
            </form>
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