<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>配置用户角色</title>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            配置用户角色
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=addPurviewGroup&id=${manages.id}">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td width="20%" align="right">
                            用户
                        </td>
                        <td>${manages.userName}</td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            可选角色
                        </td>
                        <td>
                            <select name="purviewGroupCode">
                                <c:forEach var="purviewGroup" items="${purviewGroupList}">
                                    <option value="${purviewGroup.purviewGroupCode}">${purviewGroup.name}</option>
                                </c:forEach>
                            </select>

                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" id="subBtn" value="添加" class="submit"
                                   style="width: 64px; border: none"/>
                        </td>
                    </tr>
                </table>
            </form>

            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="2">
                            已拥有的角色
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td>
                        角色名
                    </td>
                    <td align="center" width="10%">
                        操作
                    </td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="2" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="sub" items="${pageBean.pageContent}">
                        <tr >
                            <td>
                                    ${sub[1].name}
                            </td>
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/manages/systemManagesServlet?action=delPurviewGroup&id=${manages.id}&sid=${sub[0].id}" onclick="return isDel('您将删除“${sub[1].name}”角色，删除后该角色无法恢复。您要继续此操作吗？')">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <tr>
                    <td colspan="2" align="right">
                        <script language="javaScript">document.write(page("?", ${pageBean.pageTotal}, 20, ${pageBean.pageId}));</script>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script>
    function isDel(str) {
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>