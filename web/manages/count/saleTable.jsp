<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>销售报表</title>
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
        function exportExcel() {
            var pageTotal = '${map.pageTotal}';
            var startPage = $("#inputStart").val();
            var endPage = $("#inputEnd").val();
            if (parseInt(startPage) > parseInt(pageTotal) || parseInt(endPage) > parseInt(pageTotal)) {
                alert("页码不能大于记录最大页码！");
                return;
            }
            if (parseInt(startPage) > parseInt(endPage)) {
                alert("页码输入错误！");
                return;
            }
            $("#startPage").val(startPage);
            $("#endPage").val(endPage);
            var action = $("#form1").attr("action");
            $("#form1").attr("action", "${pageContext.request.contextPath}/manages/outData?action=saleTable");
            $("#form1").submit();
            $("#form1").attr("action", action);
        }
    </script>
</head>
<body>
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
       class="table">
    <tr>
        <td class="title" style="text-align:left;">
            统计报表 >> 彩期销售报表
        </td>
        <td class="title" style="text-align:right;padding-right: 8px">
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;" colspan="2">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/countServlet?action=saleTable">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>彩种</td>
                        <td>
                            <select name="lotteryCode" id="lotteryCode">
                                <option value="" <c:if test="${'' == lotteryCode }">selected</c:if>> 全部 </option>
                                <c:forEach var="lottery" items="${lotteryList}">
                                    <c:if test="${lottery.key != '200' && lottery.key != '201' && lottery.key != '400'}">
                                        <option value="${lottery.key}"
                                                <c:if test="${lottery.key == lotteryCode}">selected</c:if> >
                                                ${lottery.value.name }
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                        <td>期次</td>
                        <td><input type="text" name="issue" value="${issue}"></td>
                        <td align="right"><input type="submit" name="Submit" class="submit" value="统计" style="width: 64px; border: none"></td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr>
                    <td colspan="13">
                        <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal}条记录]</div>
                        <div style=" float:right;"></div>
                        <div style="clear:both;"></div>
                    </td>
                </tr>
                <tr id="one">
                    <td rowspan="2">序号</td>
                    <td rowspan="2">彩种</td>
                    <td rowspan="2">期次</td>
                    <td colspan="2">开止时间</td>
                    <td colspan="2">成功</td>
                    <td colspan="2">失败</td>
                    <td colspan="3">中奖</td>
                    <td rowspan="2">统计时间</td>
                </tr>
                <tr align="center">
                    <td>开售</td>
                    <td>止售</td>
					<td>票数</td>
                    <td>金额</td>
					<td>票数</td>
                    <td>金额</td>
					<td>票数</td>
                    <td>税前</td>
                    <td>税后</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="13" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="sub" items="${pageBean.pageContent}" varStatus="cont">
                        <tr align="right">
                            <td align="center">${(page-1)*pageSize+cont.count}</td>
                            <td align="center">${myf:getLotteryChinaName(sub.lotteryCode)}</td>
							<td><a href="${pageContext.request.contextPath}/manages/countServlet?action=saleTableForSub&lotteryCode=${sub.lotteryCode}&issue=${sub.issue}" target="_blank">${sub.issue}</a></td>
                            <td align="center"><fmt:formatDate value="${sub.startTime}" pattern="yy-MM-dd HH:mm:ss"/></td>
							<td align="center"><fmt:formatDate value="${sub.endTime}" pattern="yy-MM-dd HH:mm:ss"/></td>
							<td>${sub.successTicket}</td>
                            <td><fmt:formatNumber value="${sub.successAmount}" pattern="0.00"/></td>
							<td>${sub.failureTicket}</td>
                            <td><fmt:formatNumber value="${sub.failureAmount}" pattern="0.00"/></td>
                            <td>${sub.bonusTicket}</td>
                            <td><fmt:formatNumber value="${sub.fixBonusAmount}" pattern="0.00"/></td>
                            <td><fmt:formatNumber value="${sub.bonusAmount}" pattern="0.00"/></td>
                            <td align="center"><fmt:formatDate value="${sub.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="13" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}" total="${pageBean.itemTotal}" gotoURI="${pageContext.request.contextPath}/manages/countServlet"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>