<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>算奖管理</title>
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
            派奖管理 >> 普通彩种派奖
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr id="one">
                    <td width="4%">序号</td>
                    <td width="8%">彩种</td>
                    <td width="8%">开奖日期</td>
                    <td width="14%">彩期说明</td>
                    <td width="14%">待派奖彩期</td>
                    <td>最新派奖日志</td>
                    <td width="10%">中奖票查询</td>
                </tr>
                <c:forEach var="issueMap" items="${requestScope.mapList}" varStatus="cont">
                    <tr>
                        <td align="center">${cont.count}</td>
                        <td align="center">${issueMap.lotteryName}</td>
                        <td align="center">${issueMap.tagInfo == null ? "&nbsp;<br/>不定期" : issueMap.tagInfo}
                            <c:if test="${issueMap.tagInfo != null}"></br></c:if> ${issueMap.isToday ? "<br/><font color=red>今日开奖</font>":"</br>&nbsp;"}</td>
                        <td align="left">
                            销售中：<c:forEach var="issue" items="${issueMap.saleIssue}"><a
                                href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${issue.lotteryCode}&issue=${issue.name}"
                                target="_black">${issue.name}期</a> </c:forEach><br/>
                            已派奖：<c:if test="${issueMap.lastSendWin != null}"><a
                                href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueDetailInfo&lotteryCode=${issueMap.lastSendWin.lotteryCode}&issue=${issueMap.lastSendWin.name}"
                                target="_black">${issueMap.lastSendWin.name}期</a></c:if>
                        </td>
                        <td align="center">
                            <c:forEach var="issue" items="${issueMap.waitSendWinIssue}" end="5">
                                ${issue.name}期【<a href="${pageContext.request.contextPath}/manages/sendWinManagesServlet?action=sportIssueDetail&lotteryCode=${issueMap.lotteryCode}&issue=${issue.name}">派奖</a>】<br/>
                            </c:forEach>
                            <c:if test="${fn:length(issueMap.waitSendWinIssue) > 5}">...</c:if>
                        </td>
                        <td align="left">
                            <c:set var="issue1" value="${issueMap.waitSendWinIssue[0]}"/>
                            <c:set var="issue2" value="${issueMap.waitSendWinIssue[1]}"/>
                            <c:set var="issue3" value="${issueMap.waitSendWinIssue[2]}"/>
                            <c:if test="${null != issue1 && issue1.bonusNumber != null}">
                                <c:if test="${issue1.lotteryCode != '113'}">
                                    <span style="color:blue;">${issue1.name}</span>期开奖号码为<span style="color:red;">${issue1.bonusNumber}</span>,奖级分布:<c:forEach
                                        var="lvlList"
                                        items="${myf:formatBonusClassManages(issue1.bonusClass).levelList}"
                                        varStatus="st">${lvlList.title}<span
                                        style="color:#ff0000;">${lvlList.amount}</span>元<c:if
                                        test="${!st.last}">,</c:if></c:forEach>
                                    <c:if test="${issue1.lotteryCode=='109'}"><c:set var="pls"
                                                                                     value="${myf:getDefaultBonusClassManagerByLotteryCode('108')}"/><c:forEach
                                            var="plslv" items="${pls.levelList}" varStatus="ss">,${plslv.title}<span
                                            style="color:red;">${plslv.amount}</span>元</c:forEach></c:if>
                                </c:if>
                                <c:if test="${issue1.lotteryCode == '113'}">
                                    <span style="color:blue;">${issue1.name}</span>期开奖号码为<span style="color:red;">${issue1.bonusNumber}</span>,奖级分布:${myf:formatDltBonusClassShow(issue1.bonusClass)}
                                </c:if>
                            </c:if>
                        </td>
                        <td align="center">
                            <c:forEach var="issue" items="${issueMap.waitSendWinIssue}">
                                <a href="${pageContext.request.contextPath}/manages/sendWinManagesServlet?action=issueBonusList&issue=${issue.name}&lotteryCode=${issueMap.lotteryCode}"
                                   target="_black">中奖票</a>
                                <br />
                            </c:forEach>
                        </td>
                            <%--<td align="left">--%>
                            <%--<c:if test="${null != issue2}">--%>
                            <%--<a href="${pageContext.request.contextPath}/manages/openSendWin?action=editOpenInfo&lotteryCode=${issueMap.lotteryCode}&name=${issue2.name}">${issue2.name}期</a><br/>--%>
                            <%--开奖号码：${issue2.bonusNumber}<br/>--%>
                            <%--奖级：${myf:filterBonusClassToString(issue2.bonusClass,null,null)}--%>
                            <%--</c:if>--%>
                            <%--</td>--%>
                            <%--<td align="left">--%>
                            <%--<c:if test="${null != issue3}">--%>
                            <%--${issue3.name}期(<a href="${pageContext.request.contextPath}/manages/openSendWin?action=editOpenInfo&lotteryCode=${issueMap.lotteryCode}&name=${issue3.name}">编辑</a>)<br/>--%>
                            <%--开奖号码：${issue3.bonusNumber}<br/>--%>
                            <%--奖级：${myf:filterBonusClassToString(issue3.bonusClass,null,null)}--%>
                            <%--</c:if>--%>
                            <%--</td>--%>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>
</body>
</html>