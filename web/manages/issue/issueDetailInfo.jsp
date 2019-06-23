<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>期次详情</title>
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
    <link href="${pageContext.request.contextPath}/css/kjgg.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body, td, th {
            font-family: Arial, "Times New Roman", "宋体";
        }
    </style>
    <script type="text/javascript">
        function windowClose() {
            window.close();
        }
    </script>
</head>

<body>
<!--中间内容开始 -->
<%--<c:set var="lotteryCode" value="${param.lotteryCode}"></c:set>--%>
<%--<c:set var="issue" value="${param.issue}"></c:set>--%>
<%--<c:set var="mainIssue" value="${myf:getMainIssueItem(lotteryCode,issue)}"></c:set>--%>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title">
            ${myf:getLotteryChinaName(mainIssue.lotteryCode)}第${mainIssue.name}期期次详情:
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                <tr>
                    <%--<td width="20%" align="right" class="bold"><img src="${pageContext.request.contextPath}/img/cply/${myf:getImage(mainIssue.lotteryCode)}"/></td>--%>
                    <td align="right" class="bold">开奖号码:</td>
                    <td>
                        <span style="color:red;">${mainIssue.bonusNumber}</span>
                        <%--<ul class="kjgg">--%>
                        <%--<c:forEach var="qianItem" items="${mainIssue.qian}" varStatus="indexs">--%>
                        <%--<li class="kjred">${qianItem}</li>--%>
                        <%--</c:forEach>--%>
                        <%--<c:forEach var="houItem" items="${mainIssue.hou}" varStatus="indexs">--%>
                        <%--<li class="kjbule">${houItem}</li>--%>
                        <%--</c:forEach>--%>
                        <%--</ul>--%>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">开售时间:</td>
                    <td><fmt:formatDate value="${mainIssue.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td align="right" class="bold">止售时间:</td>
                    <td><fmt:formatDate value="${mainIssue.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td align="right" class="bold">开奖日期:</td>
                    <td><fmt:formatDate value="${mainIssue.bonusTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td align="right" class="bold">兑奖时间:</td>
                    <td><fmt:formatDate value="${myf:operationDate(mainIssue.bonusTime,'d',59)}"
                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <%--<tr>--%>
                <%--<td align="right" class="bold">投注信息:</td>--%>
                <%--<td><span>选号:</span> <c:if test="${numberInfos != null }">--%>
                <%--<c:forEach var="object" items="${numberInfos}">--%>
                <%--<span>${object.number}</span> 【${object.pollName}】</c:forEach>--%>
                <%--</c:if>--%>
                <%--<br/></td>--%>
                <%--</tr>--%>
                <tr>
                    <td align="right" class="bold">本期投注金额:</td>
                    <td><fmt:formatNumber value="${mainIssue.globalSaleTotal}" pattern="#,##0.00"/>元</td>
                </tr>
                <tr>
                    <td align="right" class="bold">滚入下期奖金:</td>
                    <td><fmt:formatNumber value="${mainIssue.prizePool}" pattern="#,##0.00"/>元</td>
                </tr>
                <tr>
                    <td align="right" class="bold">开奖详情:</td>
                    <td class="three">
                        <table width="100%" border="0" cellpadding="4" cellspacing="0" class="sub">
                            <tr id="two">
                                <td>奖项</td>
                                <td>中奖注数</td>
                                <td>单注奖金</td>
                            </tr>
                            <c:forEach var="sub" items="${myf:formatBonusClassManages(mainIssue.bonusClass).levelList}">
                                <c:if test="${mainIssue.lotteryCode != '113' || (mainIssue.lotteryCode == '113' && !fn:startsWith(sub.title, '七') && !fn:startsWith(sub.title, '八') && !fn:startsWith(sub.title, '生肖乐'))}">
	                                <tr>
	                                    <td>${sub.title}</td>
	                                    <td><fmt:formatNumber value="${sub.total}" pattern="#,##0"/></td>
	                                    <td><fmt:formatNumber value="${sub.amount}" pattern="#,##0.00"/>元</td>
	                                </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
                <c:if test="${subGameList != null}">
                    <tr>
                        <td align="right" class="bold">对阵详情:</td>
                        <td class="three">
                            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="sub">
                                <tr id="two">
                                    <td>编号</td>
                                    <td>联赛</td>
                                    <td>主队</td>
                                    <td>客队</td>
                                    <td>上半场比分</td>
                                    <td>最终比分</td>
                                    <td>开赛时间</td>
                                </tr>
                                <c:forEach var="sub" items="${subGameList}">
                                    <tr>
                                        <td>${sub.index}</td>
                                        <td>${sub.leageName}</td>
                                        <td>${sub.masterName}</td>
                                        <td>${sub.guestName}</td>
                                        <td>${sub.scoreAtHalf}</td>
                                        <td>${sub.finalScore}</td>
                                        <td><fmt:formatDate value="${sub.startTime}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" class="submit" value="关闭" style="width: 64px; border: none"
                               onclick="javascript:window.close();"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</div>
</body>
</html>