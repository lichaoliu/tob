<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>获得后台管理员信息</title>
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

<body
        <c:if test="${resultMsg != null}">onload="alertResult('${resultMsg}')"</c:if> >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <c:if test="${resultMsg != null}">
        <script>
            function alertResult(resultMsg) {
                alert(resultMsg);
            }
        </script>
    </c:if>
    <tr>
        <td class="title" style="text-align:left;">
            系统参数设置 >> 用户管理
        </td>
        <td class="title" style="text-align:right">
            [<a href="${pageContext.request.contextPath}/manages/systemMonitoring/addAdmin.jsp">新建管理员</a>]
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding: 4px;">
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getAdminList">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>
                            用户名
                        </td>
                        <td>
                            <input name="adminName" type="text" class="input" value="${requestScope.adminName}">
                        </td>
                        <td>
                            姓名
                        </td>
                        <td>
                            <input name="realName" type="text" class="input" value="${realName}">
                        </td>
                        <td>
                            角色
                        </td>
                        <td>
                            <select name="purviewGroupCode">
                                <option value="">请选择</option>
                                <c:forEach var="purviewGroup" items="${purviewGroupList}">
                                    <option
                                            <c:if test="${purviewGroupCode == purviewGroup.purviewGroupCode}">selected</c:if>
                                            value="${purviewGroup.purviewGroupCode}">${purviewGroup.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td align="right">
                            <input type="submit" name="Submit" class="submit" value="查询"
                                   style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="8" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="8">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td align="center" width="5%">
                        序号
                    </td>
                    <td align="left" width="10%">
                        用户名
                    </td>
                    <td align="left" width="10%">
                        姓名
                    </td>
                    <td width="15%" align="left">
                        部门
                    </td>
                    <td width="15%" align="left">
                        角色
                    </td>
                    <td align="left" width="15%">
                        电话
                    </td>
                    <td width="15%" align="left">
                        邮箱
                    </td>
                    <td align="left">
                        操作
                    </td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="8" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="memberList" items="${pageBean.pageContent}" varStatus="cont">
                        <c:if test="${memberList.userName != 'ycadministrator'}">
                            <tr>
                                <td>${requestScope.pageSize*(page - 1) + cont.count}</td>
                                <td>
                                        ${memberList.userName}
                                </td>
                                <td>
                                        ${memberList.realName}
                                </td>
                                <td>
                                        ${memberList.departments }
                                </td>
                                <td>
                                        ${memberList.name }
                                </td>
                                <td>
                                    手机:${memberList.mobile}<br/>
                                    座机:${memberList.phone }
                                </td>
                                <td>
                                        ${memberList.email }
                                </td>
                                <td>
                                    <c:if test="${memberList.type != 2}"><a href="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getAdminById&adminId=${memberList.id}">编辑</a></c:if>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <c:if test="${memberList.status != 3 && memberList.type != 2}"><br/>
                                        <a href="${pageContext.request.contextPath}/manages/systemManagesServlet?action=deleteManages&id=${memberList.id}"
                                           onclick="return ifLink('您将删除${memberList.userName}用户，删除后不能恢复。 要继续此操作吗？')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    </c:if><br/>
                                    <a href="${pageContext.request.contextPath}/manages/systemMonitoring/updateAdminPassword.jsp?id=${memberList.id}">修改密码</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    <tr>
                        <td colspan="8" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}" total="${pageBean.itemTotal}" gotoURI="${pageContext.request.contextPath}/manages/systemManagesServlet?action=getAdminList" />
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
