<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>角色管理</title>
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
<body <c:if test="${resultMsg != null}">onload="alertResult('${resultMsg}')"</c:if>
        <c:if test="${managesNames != null}">onload="getManagesNameList('${managesNames}')"</c:if> >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <c:if test="${resultMsg != null}">
        <script>
            function alertResult(resultMsg) {
                alert(resultMsg);
            }
        </script>

    </c:if>

    <c:if test="${managesNames != null}">
        <script>
            function getManagesNameList(managesNames) {
                alert("删除角色失败，请先将以下管理员角色重新更换 \n" + managesNames);
            }
        </script>
    </c:if>
    <tr>
        <td class="title" style="text-align:left;">
            系统参数设置 >> 角色管理
        </td>
        <td class="title" style="text-align:right;">
            ［<a href="${pageContext.request.contextPath}/manages/systemMonitoring/addPurviewGroup.jsp">添加角色</a>］
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="4">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="5%">序号</td>
                    <td align="center">
                        角色名
                    </td>
                    <td width="20%" align="center">
                        创建时间
                    </td>
                    <td width="10%" align="center">
                        操作
                    </td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="4" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="purviewGroupList" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">${requestScope.pageSize*(page - 1) + cont.count}</td>
                            <td align="center">
                                    ${purviewGroupList.name}
                            </td>
                            <td align="center">
                                <fmt:formatDate value="${purviewGroupList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td align="center">
                                <c:if test="${purviewGroupList.purviewGroupCode != '785451'}">
                                    <a href="${pageContext.request.contextPath}/manages/purviewGroupServlet?action=editNew1&id=${purviewGroupList.id}">配置权限</a>｜
                                    <a href="${pageContext.request.contextPath}/manages/purviewGroupServlet?action=del&purviewGroupCode=${purviewGroupList.purviewGroupCode}"
                                       onclick="return ifLink('您将删除“${purviewGroupList.name}”角色，删除后该角色无法恢复。您要继续此操作吗？')">删除</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}" total="${pageBean.itemTotal}" gotoURI="${pageContext.request.contextPath}/manages/purviewGroupServlet?action=list" />
                        </td>
                    </tr>
                </c:if>
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