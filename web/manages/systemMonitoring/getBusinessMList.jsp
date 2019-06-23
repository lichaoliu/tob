<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>系统监控</title>
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
<c:set var="map" value="${myf:getBusinessMList()}"/>
<c:set var="drawCount" value="${map.drawCount}"/>
<c:set var="fillCount" value="${map.fillCount}"/>
<c:set var="winCount" value="${map.winCount}"/>
<c:set var="lotteryList" value="${map.lotteryList}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            系统状态监控 >> 业务状态
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr style="display: none"/>
                <c:if test="${lotteryList != null}">
                    <tr>

                        <th align="left">投注出票状态</th>
                    </tr>
                    <c:forEach var="lottery" items="${lotteryList}">
                        <tr>

                            <td align="left">
                                <font color="red">${myf:getLotteryChinaName(lottery.lotteryCode)}</font>:${lottery.issue}期
                                方案总数
                                <c:if test="${lottery.count != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}"
                                        target="_blank">${lottery.count}</a></c:if>
                                <c:if test="${lottery.count == 0}">${lottery.count}</c:if>
                                笔，
                                普通方案
                                <c:if test="${lottery.countPt != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&buyType=14"
                                        target="_blank">${lottery.countPt}</a></c:if>
                                <c:if test="${lottery.countPt == 0}">${lottery.countPt}</c:if>
                                笔，
                                合买方案
                                <c:if test="${lottery.countHm != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&buyType=2"
                                        target="_blank">${lottery.countHm}</a></c:if>
                                <c:if test="${lottery.countHm == 0}">${lottery.countHm}</c:if>
                                笔，
                                等待成交
                                <!--TODO 等待成交状态有问题 -->
                                <c:if test="${lottery.countWait != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=10"
                                        target="_blank">${lottery.countWait}</a></c:if>
                                <c:if test="${lottery.countWait == 0}">${lottery.countWait}</c:if>
                                笔，
                                投注成功
                                <c:if test="${lottery.countSuccess != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=1"
                                        target="_blank">${lottery.countSuccess}</a></c:if>
                                <c:if test="${lottery.countSuccess == 0}">${lottery.countSuccess}</c:if>
                                笔，
                                部分成交
                                <c:if test="${lottery.countPart != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=2"
                                        target="_blank">${lottery.countPart}</a></c:if>
                                <c:if test="${lottery.countPart == 0}">${lottery.countPart}</c:if>
                                笔，
                                投注失败
                                <c:if test="${lottery.countFailure != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=3"
                                        target="_blank">${lottery.countFailure}</a></c:if>
                                <c:if test="${lottery.countFailure == 0}">${lottery.countFailure}</c:if>
                                笔，
                                方案撤单
                                <c:if test="${lottery.countCancel != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=46&buyType=2"
                                        target="_blank">${lottery.countCancel}</a></c:if>
                                <c:if test="${lottery.countCancel == 0}">${lottery.countCancel}</c:if>
                                笔，
                                追号取消
                                <c:if test="${lottery.countCancelZh != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=46&buyType=4"
                                        target="_blank">${lottery.countCancelZh}</a></c:if>
                                <c:if test="${lottery.countCancelZh == 0}">${lottery.countCancelZh}</c:if>
                                笔，
                                合买流单
                                <c:if test="${lottery.countStreaming != 0}"><a
                                        href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=getOrderList&lotteryCode=${lottery.lotteryCode}&issueName=${lottery.issue}&orderStatus=5"
                                        target="_blank">${lottery.countStreaming}</a></c:if>
                                <c:if test="${lottery.countStreaming == 0}">${lottery.countStreaming}</c:if>
                                笔
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <tr>

                    <th align="left">资金操作状态</th>
                </tr>
                <tr>
                    <td align="left">
                        <font color="red">充值</font>:充值进行中记录：
                        <c:if test="${fillCount != 0}"><a
                                href="${pageContext.request.contextPath}/manages/accountMessageServlet?action=getRechargeList&getFillTypetype=cx&status=0"
                                target="_blank">${fillCount}</a></c:if>
                        <c:if test="${fillCount == 0}">${fillCount}</c:if>
                        条
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <font color="red">派奖</font>:等待派奖记录:
                        <c:if test="${winCount != 0}"><a
                                href="${pageContext.request.contextPath}/manages/winManagesServlet?action=handworkBonusQuery&status=0"
                                target="_blank">${winCount}</a></c:if>
                        <c:if test="${winCount == 0}">${winCount}</c:if>
                        条
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        <font color="red">提现</font>:等待审核记录:
                        <c:if test="${drawCount != 0}"><a
                                href="${pageContext.request.contextPath}/manages/accountMessageServlet?action=getDrawList&darwStatus=0"
                                target="_blank">${drawCount}</a></c:if>
                        <c:if test="${drawCount == 0}">${drawCount}</c:if>
                        条
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>