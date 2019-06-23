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
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<tr>
    <td class="title" style="text-align:left;">
        派奖管理 >> 中奖查询
    </td>
</tr>
<tr>
    <td style="padding: 4px;">
        <form name="form1" method="post"
              action="${pageContext.request.contextPath}/manages/sendWinManagesServlet?action=bonusList">
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                <tr>
                    <td>接入商名称</td>
                    <td>
                        <select name="sid" id="sid">
                            <option value="">全部</option>
                            <c:forEach items="${myf:getMemberList()}" var="member">
                                <option value="${member.sid}"
                                        <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>彩 种</td>
                    <td>
                        <select name="lotteryCode" id="lotteryCode">
                            <option value=""
                                    <c:if test="${'' == lotteryCode }">selected</c:if>>全部
                            </option>
                            <c:forEach items="${myf:findAllLotteryCode()}" var="lotteryCodeMap">
                                <option value="${lotteryCodeMap.key }"
                                        <c:if test="${lotteryCodeMap.key == lotteryCode }">selected</c:if> >
                                        ${lotteryCodeMap.value.name }
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>彩票状态</td>
                    <td>
                        <select name="ticketStatus">
                            <option value=""
                                    <c:if test="${'' == ticketStatus }">selected</c:if>>全部
                            </option>
                            <option value="0"
                                    <c:if test="${0 == ticketStatus }">selected</c:if>>未送票
                            </option>
                            <option value="2"
                                    <c:if test="${2 == ticketStatus }">selected</c:if>>送票未回执
                            </option>
                            <option value="3"
                                    <c:if test="${3 == ticketStatus }">selected</c:if>>出票成功
                            </option>
                            <option value="4"
                                    <c:if test="${4 == ticketStatus }">selected</c:if>>出票失败
                            </option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>彩票编号</td>
                    <td><input type="text" name="ticketId" value="${ticketId}" class="input"/></td>
                    <td>批次号</td>
                    <td><input type="text" name="orderId" value="${orderId}" class="input"/></td>
                    <td>期次</td>
                    <td><input type="text" name="issue" value="${issue}" class="input"/></td>
                    <td>商户票号</td>
                    <td><input type="text" name="outTicketId" value="${outTicketId}" class="input"/></td>
                </tr>
                <tr>
                    <td>接收时间</td>
                    <td>
                        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               name="createStartTime" value="${createStartTime }" readonly/>
                        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                 name="createEndTime" value="${createEndTime }" readonly/>
                    </td>
                    <td>送票时间</td>
                    <td>
                        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               name="sendStartTime" value="${sendStartTime}" readonly/>
                        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                 name="sendEndTime" value="${sendEndTime}" readonly/>
                    </td>
                    <td>回执时间</td>
                    <td>
                        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               name="returnStartTime" value="${returnStartTime }" readonly/>
                        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                 name="returnEndTime" value="${returnEndTime }" readonly/>
                    </td>
                    <td>派奖时间</td>
                    <td>
                        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               name="bonusStartTime" value="${bonusStartTime}" readonly/>
                        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                 name="bonusEndTime" value="${bonusEndTime}" readonly/>
                    </td>
                </tr>
                <tr>
                    <td>大小奖</td>
                    <td align="left">
                        <select name="bigBonus">
                            <option value=""
                                    <c:if test="${bigBonus == ''}">selected</c:if>>全部
                            </option>
                            <option value="0"
                                    <c:if test="${bigBonus == 0}">selected</c:if>>小奖
                            </option>
                            <option value="1"
                                    <c:if test="${bigBonus == 1}">selected</c:if>>大奖
                            </option>
                        </select>
                    </td>
                    <td align="right" colspan="6">
                        <input type="submit" name="Submit" class="submit" value="查询"
                               style="width: 64px; border: none">
                    </td>
                </tr>
            </table>
        </form>

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
                                      gotoURI="${pageContext.request.contextPath}/manages/sendWinManagesServlet?action=bonusList"/>
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