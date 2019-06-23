<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>足彩彩期管理</title>
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
        function onOpen(url) {
            window.open(url);
        }
    </script>
</head>
<body>
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            彩期管理 >> 足彩对阵管理
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr id="one">
                    <td width="5%">序号</td>
                    <td width="10%">彩种</td>
                    <td width="15%">当前彩期</td>
                    <td width="25%">销售未发单彩期</td>
                    <td width="25%">预售彩期</td>
                    <td width="10%">操作</td>
                </tr>
                <c:forEach var="issueMap" items="${requestScope.dataList}" varStatus="cont">
                    <tr>
                        <td align="center">${cont.count}</td>
                        <td align="center">${issueMap.lotteryName}</td>
                        <td align="center">
                            <c:forEach var="issue" items="${issueMap.startIssue}">
                                <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}"
                                   target="_black">${issue.name}期</a>【<a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=toFootballIssueEdit&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}">修改</a>】<br/>
                            </c:forEach>
                        </td>
                        <td align="center">
                            <c:forEach var="issue" items="${issueMap.waitIssue}">
                                <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}"
                                   target="_black">${issue.name}期</a> 【<a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=toFootballIssueEdit&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}">修改</a>】<br/>
                            </c:forEach>
                        </td>
                        <td align="center">
                            <c:forEach var="issue" items="${issueMap.preIssue}">
                                <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}"
                                   target="_black">${issue.name}期</a> 【<a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=toFootballIssueEdit&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}">修改</a>】<br/>
                            </c:forEach>
                        </td>
                        <td align="center">
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery&lotteryCode=${issueMap.lotteryCode}&statuses=0,1&order=desc"
                               target="_blank">查询</a>
                            <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=toFootballIssueEdit&lotteryCode=${issueMap.lotteryCode}">【录入】</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
<c:if test="${requestScope.error != null}">
    <script type="text/javascript">
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>