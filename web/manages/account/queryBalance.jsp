<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>查询</title>
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
        <td class="title" style="text-align:left;">
            彩票管理 >> 额度查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="70%" border="1" cellpadding="4" cellspacing="0" class="seach">
                <tr>
                    <td align="right">
                        <input type="button" name="Submit" class="submit" value="查询" style="width: 64px; border: none"
                               onclick="window.location.href='?action=query'">
                    </td>
                </tr>
            </table>
            
            <table width="70%" border="1" cellpadding="4" cellspacing="0" class="seach">
                <tr>
                    <td align="right">
                        <div style=" float:right;">
                              <a href="/manages/adminManages?action=memberList"  style="font-weight: bold;">接入商金额统计</a>
                        </div>
                    </td>
                </tr>
            </table>
            
            <table width="70%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr id="one">
                    <td width="5%" align="center">序号</td>
                    <td width="20%" align="center">出票口</td>
                    <td width="30%" align="center">余额</td>
                    <!--
                    <td align="center">
                        缴款机号
                    </td>
                    <td width="20%" align="center">
                        查询时间
                    </td>
                -->
                </tr>
                <c:if test="${param.action=='query'}">
                    <c:forEach var="item" items="${myf:queryBanlance()}" varStatus="index">
                        <tr>
                            <td align="center">${index.index+1}</td>
                            <td align="center">${item.name}</td>
                            <td align="right">
                                <fmt:formatNumber value="${item.balance}" pattern="0.00"/>
                            </td>
                            <!--<td align="center">
                                    ${item.no}
                            </td>
                            <td align="center">
                                <%=com.cndym.utils.Utils.today("yyyy-MM-dd HH:mm:ss")%>
                            </td>
                        --></tr>
                    </c:forEach>
                </c:if>

                <c:if test="${param.action!='query'}">
                    <tr>
                        <td align="center" colspan="5">
                            提醒：点击“查询”按钮后可获得本系统的出票额度。
                        </td>
                    </tr>
                </c:if>

            </table>
        </td>
    </tr>
</table>
</body>
</html>