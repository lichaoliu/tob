<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>彩票详细</title>
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
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
<tr>
    <td class="title">
        ${myf:getLotteryChinaName(ticket.lotteryCode) }第${ticket.issue}期彩票[${ticket.ticketId}]详情
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
<tr>
    <td style="min-width: 100px;" align="right" class="bold">接入商ID:</td>
    <td>${ticket.sid}</td>
</tr>
<tr>
    <td style="min-width: 100px;" align="right" class="bold">彩票号码:</td>
    <td>${ticket.ticketId }</td>
</tr>
<tr>
    <td align="right" class="bold">批次号码:</td>
    <td>${ticket.orderId}</td>
</tr>
<tr>
    <td align="right" class="bold">商户票号:</td>
    <td>${ticket.outTicketId}</td>
</tr>
<tr>
    <td align="right" class="bold">打印票号:</td>
    <td>${ticket.saleCode}</td>
</tr>
<tr>
    <td align="right" class="bold">彩票序列号:</td>
    <td>${ticket.id}</td>
</tr>
<tr>
    <td align="right" class="bold">接收时间:</td>
    <td><fmt:formatDate value="${ticket.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
    <td align="right" class="bold">发送时间:</td>
    <td><fmt:formatDate value="${ticket.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
    <td align="right" class="bold">回执时间:</td>
    <td><fmt:formatDate value="${ticket.returnTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
    <td align="right" class="bold">算奖时间:</td>
    <td><fmt:formatDate value="${ticket.bonusTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>


<c:choose>
    <c:when test="${ticket.lotteryCode == '200' || ticket.lotteryCode == '201' || ticket.lotteryCode == '400'}">
        <c:set var="subTicket" value="${myf:subOrderMatchList(ticket.ticketId)}"/>
        <tr>
            <td align="right" class="bold">方案内容</td>
            <td class="three">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="sub">
                    <tr id="two">
                        <td align="center">场次</td>
                        <c:if test="${ticket.lotteryCode==200 || ticket.lotteryCode=='400'}">
                            <td align="center">主队</td>
                            <td align="center">客队</td>
                        </c:if>
                        <c:if test="${ticket.lotteryCode==201}">
                            <td align="center">客队</td>
                            <td align="center">主队</td>
                        </c:if>
                        <td align="center">全场比分</td>
                        <c:if test="${ticket.lotteryCode == '201'}">
                            <c:if test="${ticket.playCode=='04'}">
                                <td align="center">预设比分</td>
                            </c:if>
                        </c:if>
                        <td align="center">投注内容</td>
                        <td align="center">彩果</td>
                        <c:if test="${(ticket.lotteryCode==200 || ticket.lotteryCode==201) && ticket.playCode =='10'}">
                            <td align="center">玩法</td>
                        </c:if>
                    </tr>

                    <c:forEach var="match" items="${subTicket.matchBeanList}">
                        <tr>
                            <td align="center">
                                    ${match.matchNo}
                            </td>
                            <c:if test="${ticket.lotteryCode==200 || ticket.lotteryCode=='400'}">
                                <td align="center">${match.mainTeam}
                                    <c:if test="${match.letBall != null && match.letBall != ''}">
                                        (${match.letBall})
                                    </c:if></td>
                                <td align="center">${match.guestTeam}</td>
                            </c:if>
                            <c:if test="${ticket.lotteryCode==201}">
                                <td align="center">${match.guestTeam}</td>
                                <td align="center">${match.mainTeam}
                                    <c:if test="${match.letBall != null && match.letBall != ''}">
                                        (${match.letBall})
                                    </c:if></td>
                            </c:if>
                            <td align="center">
                                <c:if test="${ticket.lotteryCode==200 || ticket.lotteryCode=='400'}">
                                    <c:if test="${match.guestTeamScore != null && match.guestTeamScore != ''}">
                                        ${match.mainTeamScore} : ${match.guestTeamScore}
                                    </c:if>
                                    <c:if test="${match.guestTeamHalfScore != null && match.guestTeamHalfScore != ''}">
                                        (${match.mainTeamHalfScore}: ${match.guestTeamHalfScore})
                                    </c:if>
                                </c:if>
                                <c:if test="${ticket.lotteryCode==201}">
                                    <c:if test="${match.guestTeamScore != null && match.guestTeamScore != ''}">
                                        ${match.guestTeamScore} : ${match.mainTeamScore}
                                    </c:if>
                                </c:if>
                            </td>
                            <c:if test="${ticket.lotteryCode == '201'}">
                                <c:if test="${ticket.playCode=='04'}">
                                    <td align="center">${match.preCast}</td>
                                </c:if>
                            </c:if>
                            <td align="center">${match.numberInfo}</td>
                            <td align="center">${match.result}</td>
                            <c:if test="${(ticket.lotteryCode==200 || ticket.lotteryCode==201) && ticket.playCode =='10'}">
                                <td align="center">${myf:getLotteryBeanByLotteryPlayCode(ticket.lotteryCode,match.playCode).lotteryPlay.name}</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="5" class="l30">
                            过关方式：<span class="tor">${subTicket.guoGuan}</span>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </c:when>
    <c:otherwise>
        <tr>
            <td align="right" class="bold">投注号码:</td>
            <td align="left">
                <c:choose>
                    <c:when test="${ticket.lotteryCode == '006' && ticket.pollCode == '01' && ticket.playCode == '30'}">
                        ${myf:sscDxdsNumber(ticket.numberInfo)}
                    </c:when>
                    <c:otherwise>
                        <c:forTokens var="number" items="${ticket.numberInfo}" delims=";">
                            ${number}<br/>
                        </c:forTokens>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:otherwise>
</c:choose>

<tr>
    <td align="right" class="bold">彩票状态:</td>
    <td>${myf:getTicketStatus(ticket.ticketStatus)}</td>
</tr>
<c:if test="${ticket.lotteryCode != '200' && ticket.lotteryCode != '201' && ticket.lotteryCode != '400'}">
    <tr>
        <td align="right" class="bold">开奖号码:</td>
        <td width="83%" align="left">
            <c:if test="${mainIssue.bonusNumber != '' && mainIssue.bonusNumber != '-'}">
                ${mainIssue.bonusNumber}
            </c:if>
            <c:if test="${empty mainIssue.bonusNumber || mainIssue.bonusNumber == '-'}">
                --
            </c:if>
        </td>
    </tr>
</c:if>
<tr>
    <td align="right" class="bold">中奖状态:</td>
    <td>
        <c:if test="${ticket.bonusStatus == 0}">未开奖</c:if>
        <c:if test="${ticket.bonusStatus == 1}">中奖</c:if>
        <c:if test="${ticket.bonusStatus == 2}">未中奖</c:if>
    </td>
</tr>
<tr>
    <td align="right" class="bold">中奖金额:</td>
    <td>
        税前:<fmt:formatNumber value="${ticket.fixBonusAmount == null ? 0.00 : ticket.fixBonusAmount}"
                             pattern="0.00"/>
        税后:<fmt:formatNumber value="${ticket.bonusAmount == null ? 0.00 : ticket.bonusAmount}"
                             pattern="0.00"/>
    </td>
</tr>
<tr>
    <td align="right" class="bold">是否大奖:</td>
    <td>
        <c:if test="${ticket.bigBonus == 0}">否</c:if>
        <c:if test="${ticket.bigBonus == 1}">是</c:if>
    </td>
</tr>
<c:if test="${ticket.lotteryCode != '006' && ticket.lotteryCode != '200' && ticket.lotteryCode != '201' && ticket.lotteryCode != '400'}">
    <tr>
        <td align="right" class="bold">中奖情况:</td>
        <td>
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="sub">
                <tr id="two">
                    <td>奖级</td>
                    <td>注数</td>
                </tr>
                <c:forEach items="${myf:getBonusClass(ticket.bonusClass)}" var="bonusClass">
                    <tr>
                        <td>${myf:bonusLogClass(bonusClass.classes,ticket.lotteryCode)}</td>
                        <td>${bonusClass.total}</td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</c:if>
<tr>
    <td align="right" class="bold">彩票信息:</td>
    <td class="three">
        <table width="100%" border="0" cellpadding="4" cellspacing="0" class="sub">
            <tr>
                <td>玩法:${myf:getPlayChinaName(ticket.lotteryCode,ticket.playCode)}</td>
            </tr>
            <tr>
                <td>方式:${myf:getPollName(ticket.lotteryCode,ticket.playCode,ticket.pollCode)}</td>
            </tr>
            <tr>
                <td>注数:${ticket.item}</td>
            </tr>
            <tr>
                <td>倍数:${ticket.multiple}</td>
            </tr>
            <tr>
                <td>金额:${ticket.amount}元</td>
            </tr>
        </table>
    </td>
</tr>
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
</body>
</html>