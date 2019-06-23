<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<c:set var="exportMaxPage" value="${myf:getExportMaxPage()}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>彩票列表</title>
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
            彩票管理 >> 彩票查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="11">
                            <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal }条记录;
                                累计彩票数量${requestScope.ticketNumber}张,
                                投注金额<fmt:formatNumber value="${requestScope.ticketAmount}" pattern="0.00"/>元
                                ]
                            </div>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="3%">序号</td>
                    <td width="18%">彩票编号</td>
                    <td width="12%">商户信息</td>
                    <td width="8%">彩种</td>
                    <td width="6%">注数倍数</td>
                    <td>金额(元)</td>
                    <td width="6%">状态</td>
                    <td width="12%">奖金(元)</td>
                    <td width="14%">时间</td>
                    <td width="5%">出票口</td>
                    <td width="5%">子票</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="11" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">${(page-1)*requestScope.pageSize + cont.count}</td>
                            <td align="left">
                                批次号:${objectList.orderId}
                                <br/>
                                票号:<a
                                    href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=ticketDetail&ticketId=${objectList.ticketId}"
                                    target="_blank">${objectList.ticketId}</a>
                                <br/>
                                商户票号:${objectList.outTicketId}
                                <br/>
                                出票票号:${objectList.backup1}
                            </td>
                            <td>
                                商户 ID:${objectList.sid}
                                <br/>
                                商户名称:${objectList.name}
                            </td>
                            <td>
                                彩种:${myf:getLotteryChinaName(objectList.lotteryCode)}
                                <br/>
                                玩法:${myf:getPlayChinaName(objectList.lotteryCode,objectList.playCode)}
                                <br/>
                                期次:${objectList.issue}
                            </td>
                            <td align="left">
                                注数:${objectList.item}
                                <br/>
                                倍数:${objectList.multiple}
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>
                            </td>
                            <td align="center">
                                <c:if test="${objectList.ticketStatus==0}">
                                    未送票
                                </c:if>
                                <c:if test="${objectList.ticketStatus==1}">
                                    调度中
                                </c:if>
                                <c:if test="${objectList.ticketStatus==2}">
                                    送票未回执
                                </c:if>
                                <c:if test="${objectList.ticketStatus==3}">
                                    出票成功<br>
                                    <c:if test="${objectList.bonusStatus==0 }">
                                        未开奖
                                    </c:if>
                                    <c:if test="${objectList.bonusStatus==1}">
                                        中奖
                                    </c:if>
                                    <c:if test="${objectList.bonusStatus==2}">
                                        未中奖
                                    </c:if>
                                </c:if>
                                <c:if test="${objectList.ticketStatus==4 || objectList.ticketStatus==5}">
                                    出票失败<br>
                                    <font color="blue">${objectList.errCode}|${objectList.errMsg}</font>
                                </c:if>
                                <c:if test="${objectList.ticketStatus==6}">
                                    重发<br>
                                    <font color="blue">${objectList.errCode}|${objectList.errMsg}</font>
                                </c:if>
                            </td>
                            <td align="left">
                                <c:if test="${objectList.ticketStatus==3}">
                                    税前:<fmt:formatNumber
                                        value="${objectList.fixBonusAmount == null ? 0.00 : objectList.fixBonusAmount}"
                                        pattern="0.00"/>
                                    <br/>
                                    税后:<fmt:formatNumber
                                        value="${objectList.bonusAmount == null ? 0.00 : objectList.bonusAmount}"
                                        pattern="0.00"/>
                                </c:if>
                                <c:if test="${objectList.ticketStatus!=3}">
                                    税前:--
                                    <br/>
                                    税后:--
                                </c:if>
                            </td>
                            <td align="left">
                                生成:<fmt:formatDate value="${objectList.createTime}" pattern="yy-MM-dd HH:mm:ss"/>
                                <br/>
                                发送:<fmt:formatDate value="${objectList.sendTime}" pattern="yy-MM-dd HH:mm:ss"/>
                                <br/>
                                回执:<fmt:formatDate value="${objectList.returnTime}" pattern="yy-MM-dd HH:mm:ss"/>
                                <br/>
                                算奖:<fmt:formatDate value="${objectList.bonusTime}" pattern="yy-MM-dd HH:mm:ss"/>
                            </td>
                            <td align="center">
                                    ${myf:getPostCodeName(objectList.postCode)}
                            </td>
                            <td align="center">
                                <a href="${pageContext.request.contextPath}/manages/subTicketManagesServlet?action=subTicketList&ticketId=${objectList.ticketId}"
                                   target="_blank">查看</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="11" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=ticketList"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>

<script type="text/javascript">
</script>


</html>