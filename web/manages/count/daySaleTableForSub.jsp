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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
       class="table">
    <tr>
        <td class="title" style="text-align:left;">
            统计报表 >> 日销售报表
        </td>
        <td class="title" style="text-align:right;padding-right: 8px">
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;" colspan="2">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/countServlet?action=daySaleTableForSub">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>
                            日期范围
                        </td>
                        <td>
                            <input type="text" name="startDate" value="${startDate}" onClick="WdatePicker()"
                                   style="width: 83px;"
                                   readonly>
                            －
                            <input type="text" name="endDate" value="${endDate}" onClick="WdatePicker()"
                                   style="width: 83px;" readonly>
                        </td>
                        <td>接入商名称</td>
                        <td>
                            <select name="sid" id="sid">
                                <option value="">全部</option>
                                <c:forEach items="${myf:getMemberList()}" var="member">
                                    <option value="${member.sid}">${member.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td align="right"><input type="submit" name="Submit" class="submit" value="统计"
                                                 style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr>
                    <td colspan="11">
                        <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal}条记录]
                        </div>
                        <div style=" float:right;">

                        </div>
                        <div style="clear:both;"></div>
                    </td>
                </tr>
                <tr id="one" align="center">
                    <td rowspan="2">序号</td>
                    <td rowspan="2">日期</td>
					<td colspan="2">接入商信息</td>
                    <td colspan="2">成功</td>
                    <td colspan="2">失败</td>
                    <td colspan="3">中奖</td>
                </tr>
                <tr align="center">
                    <td>商户 ID</td>
                    <td>商户名称</td>
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
                        <td colspan="11" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="sub" items="${pageBean.pageContent}" varStatus="cont">
                        <tr align="right">
                            <td align="center">${(page-1)*pageSize+cont.count}</td>
                            <td align="center">${sub.curDate}</td>
                            <td align="center">${sub.sid}</td>
                            <td align="center">${sub.name}</td>
                            <td>${sub.successTicket}</td>
                            <td><fmt:formatNumber value="${sub.successAmount}" pattern="0.00"/></td>
                            <td>${sub.failureTicket}</td>
                            <td><fmt:formatNumber value="${sub.failureAmount}" pattern="0.00"/></td>
                            <td>${sub.bonusTicket}</td>
                            <td><fmt:formatNumber value="${sub.fixBonusAmount}" pattern="0.00"/></td>
                            <td><fmt:formatNumber value="${sub.bonusAmount}" pattern="0.00"/></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="11" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/countServlet"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>