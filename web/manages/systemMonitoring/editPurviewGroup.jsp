<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>配置角色权限</title>
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
    <style type="text/css">
        div {
            padding: 4px;
        }
    </style>
</head>

<body>
<c:set var="fatherList" value="${fatherList}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            配置角色权限
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/purviewGroupServlet?action=addPurviewNew&id=${purviewGroup.id}&purviewGroupCode=${purviewGroup.purviewGroupCode}">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td width="20%" align="right">
                            角色名
                        </td>
                        <td style="font-weight: bold;font-size: 14px">${purviewGroup.name}</td>
                    </tr>
                    <tr>
                        <td width="20%" align="right">
                            选择权限
                        </td>
                        <td>
                            <c:set var="fCode" value="-1"/>
                            <c:forEach var="father" items="${fatherList}">
                                <c:if test="${fCode != father.CODE_FATHER}">
                                    <div style="font-weight: bold;">${father.NAME_FATHER}</div>
                                    <div style="border-bottom: 1px dashed #C0D8F1;">
                                        <c:forEach var="sub" items="${myf:getPurviewList(father.CODE_FATHER,purviews)}">
                                            <c:if test="${sub.CODE != '140003'}">
                                                <input style="width: 30px;border: none;vertical-align:middle;" type="checkbox" name="purviews"
                                                       value="${sub.CODE}"
                                                       <c:if test="${sub.PURVIEW_GROUP_CODE !=null}">checked</c:if>>${sub.NAME}
                                            </c:if>
                                            <c:if test="${sub.CODE == '140003'}">
                                                <input style="width: 30px;border: none;vertical-align:middle;" type="checkbox" name="purviews"
                                                       value="${sub.CODE}"
                                                       checked onClick="this.checked=true">${sub.NAME}
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </c:if>
                                <c:set var="fCode" value="${father.CODE_FATHER}"/>
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" id="subBtn" value="修改" class="submit"
                                   style="width: 64px; border: none" onclick="return ifLink('确认修改？')"/>
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