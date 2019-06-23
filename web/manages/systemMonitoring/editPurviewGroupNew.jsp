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
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title">
            配置角色权限
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/purviewGroupServlet?action=addPurviewNew&purviewGroupCode=${purviewGroup.purviewGroupCode}&id=${purviewGroup.id}">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td width="20%" align="center">
                            角色名 ${purviewGroup.name}</td>
                    </tr>
                    <c:set var="fCode" value="1~1"/>
                    <c:forEach var="purviewUrl" items="${purviewUrlList}">
                        <c:if test="${fn:contains(adminPurview, purviewUrl.code)}">
                            <tr>
                                <td><c:if test="${fCode != purviewUrl.code_father}">${purviewUrl.name_father}</c:if>
                                    <input type="checkbox" checked="checked" value="${purviewUrl.code}"
                                           name="qxList"/>${purviewUrl.name}
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${!fn:contains(adminPurview, purviewUrl.code)}">
                            <tr>
                                <td><c:if test="${fCode != purviewUrl.code_father}">${purviewUrl.name_father}</c:if>
                                    <input type="checkbox" value="${purviewUrl.code}"
                                           name="qxList"/>${purviewUrl.name}
                                </td>
                            </tr>
                        </c:if>

                        <c:set var="fCode" value="${purviewUrl.code_father}"/>
                    </c:forEach>

                    <tr>
                        <td colspan="2" align="center">
                            <input type="submit" id="subBtn" value="编辑" class="submit"
                                   style="width: 64px; border: none"/>
                        </td>
                    </tr>
                </table>

                <%--<table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td width="20%" align="center">
                            角色名 ${purviewGroup.name}</td>
                    </tr>
                    <c:set var="fCode" value="0_0"/>
                    <c:forEach var="purviewFather" items="${purviewFathers}">
                        <c:forEach var="map" items="${myf:getPurviewMsg(adminPurview, purviewUrlList,purviewFathers)}">
                            <c:if test="${purviewFather.CODE_FATHER == map.key}">
                                <tr>
                                    <td>
                                        <c:forEach var="urlMap" items="${map.value}">
                                            <c:if test="${fCode != purviewFather.CODE_FATHER}">${purviewFather.NAME_FATHER}:</c:if>
                                            ${urlMap.key}++${urlMap.value}
                                            <c:set var="fCode" value="${purviewFather.CODE_FATHER}"/>
                                        </c:forEach>

                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </table>--%>

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