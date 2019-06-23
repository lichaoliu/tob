<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>派奖管理</title>
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
            派奖管理 >> 中奖查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="7">
                <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录,
                    累计投注金额<fmt:formatNumber value="${requestScope.orderAmount}" pattern="0.00"/> 元,
                    税前奖金<fmt:formatNumber value="${requestScope.fixBonusAmount}" pattern="0.00"/> 元,
                    税后奖金<fmt:formatNumber value="${requestScope.bonusAmount}" pattern="0.00"/> 元]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="5%">序号</td>
                    <td width="15%">票号</td>
                    <td width="10%">商户信息</td>
                    <td width="16%">投注信息</td>
                    <td width="6%">注数倍数</td>
                    <td width="14%">奖金信息</td>
                    <td width="14%">奖项信息</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="7" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">${requestScope.pageSize*(page - 1) + cont.count}</td>
                            <td align="left">
                                批 次 号:${objectList.orderId}
                                <br/>
                                票 号 :<a
                                    href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=ticketDetail&ticketId=${objectList.ticketId}"
                                    target="_blank">${objectList.ticketId}</a>
                                <br/>
                                商户票号:${objectList.outTicketId}
                            </td>
                            <td align="center">
                                商户 ID:${objectList.sid}
                                <br/>
                                商户名称:${objectList.name}
                            </td>
                            <td align="left">
                                彩种:${myf:getLotteryChinaName(objectList.lotteryCode)}
                                <br/>
                                期次:${objectList.issue}
                                <br/>
                                金额:<fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>元
                                <br/>
                                时间:<fmt:formatDate value="${objectList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td align="left">
                                注数:${objectList.item}
                                <br/>
                                倍数:${objectList.multiple}
                            </td>
                            <td align="left">
                                类别:<c:if test="${objectList.bigBonus == 1}">大奖</c:if><c:if
                                    test="${objectList.bigBonus != 1}">小奖</c:if>
                                <br/>
                                税前:<fmt:formatNumber value="${objectList.fixBonusAmount}" pattern="0.00"/>元
                                <br/>
                                税后:<fmt:formatNumber value="${objectList.bonusAmount}" pattern="0.00"/>元
                            </td>
                            <td>
                                <c:if test="${objectList.lotteryCode != '006'}">
                                    <c:forEach var="bonus" items="${myf:getBonusClass(objectList.bonusClass)}">
                                        ${myf:bonusLogClass(bonus.classes,objectList.lotteryCode)}${bonus.total}注
                                        <br/>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="7" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/sendWinManagesServlet"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
<script type="text/javascript">
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>