<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>帐户报表</title>
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
            $("#form1").attr("action", "${pageContext.request.contextPath}/manages/outData?action=accountCountTable");
            $("#form1").submit();
            $("#form1").attr("action", action);
        }
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">统计报表 >> 接入商帐户报表</td>
    </tr>
    <tr>
        <td style="padding: 4px;" colspan="2">
            <div>
                <form name="form1" id="form1" method="post"action="${pageContext.request.contextPath}/manages/countServlet?action=accountTableForSub">
                    <input type="hidden" name="startPage" id="startPage"/>
                    <input type="hidden" name="endPage" id="endPage"/>
                    <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                        <tr>
                            <td>商户ID</td>
                            <td><input type="text" name="sid" value="${sid}"></td>
                            <td>商户名称</td>
                            <td><input type="text" name="name" value="${name}"></td>
                            <td>日期范围</td>
                            <td><input type="text" name="startDate" value="${startDate}" onClick="WdatePicker()" style="width: 83px;" readonly>－
                                <input type="text" name="endDate" value="${endDate}" onClick="WdatePicker()" style="width: 83px;" readonly>
                            </td>
                            <td align="right"><input type="submit" name="Submit" class="submit" value="查询" style="width: 64px; border: none">
                            </td>
                        </tr>
                    </table>
                </form>

                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                    <c:if test="${pageBean.pageContent != null}">
                        <tr>
                            <td align="left" colspan="14">
                                <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal }条记录]
                                </div>
                                <div style=" float:right;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <input type="text" size="2" id="inputStart" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
								页 - <input type="text" size="2" id="inputEnd" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 页
                                    <a href="javascript:void(0)" onclick="exportExcel();" style="font-weight: bold;">导出</a>
                                </div>
                                <div style="clear:both;"></div>
                            </td>
                        </tr>
                    </c:if>
                    <tr id="one">
                        <td rowspan="2">序号</td>
                        <td rowspan="2">日期</td>
                        <td colspan="2">接入商</td>
                        <td colspan="3">可用额度收入</td>
                        <td colspan="2">可用额度支出</td>
                        <td colspan="2">帐户余额</td>
                    </tr>
                    <tr id="twe">
                        <td align="center">商户 ID</td>
                        <td align="center">商户名称</td>
                        <td align="center">派奖</td>
                        <td align="center">额度加</td>
                        <td align="center">退款</td>
                        <td align="center">投注</td>
                        <td align="center">额度减</td>
                        <td align="center">充值金</td>
                        <td align="center">奖金</td>
                    </tr>
                    <c:if test="${pageBean.pageContent==null}">
                        <tr><td colspan="14" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td></tr>
                    </c:if>
                    <c:if test="${pageBean.pageContent!=null}">
                        <c:forEach var="sub" items="${pageBean.pageContent}" varStatus="cont">
                            <tr>
                                <td align="center">${(page-1)*pageSize+cont.index+1}</td>
                                <td align="center">${sub.currDate}</td>
								<td align="center">${sub.sid}</td>
                                <td align="center">${sub.name}</td>
                                <td align="right"><fmt:formatNumber value="${sub.bonusAmount == null ? 0.0 : sub.bonusAmount}" type="currency" pattern="0.00"/></td>
                                <td align="right"><fmt:formatNumber value="${sub.editAccountJia == null ? 0.0 : sub.editAccountJia}" type="currency" pattern="0.00"/></td>
                                <td align="right"><fmt:formatNumber value="${sub.failureAmount == null ? 0.0 : sub.failureAmount}" type="currency" pattern="0.00"/></td>
                                <td align="right"><fmt:formatNumber value="${sub.payAmount == null ? 0.0 : sub.payAmount}" type="currency" pattern="0.00"/></td>
                                <td align="right"><fmt:formatNumber value="${sub.editAccountJian == null ? 0.0 : sub.editAccountJian}" type="currency" pattern="0.00"/></td>
                                <td align="right"><fmt:formatNumber value="${sub.rechargeAmountNew}" type="currency" pattern="0.00"/><br/></td>
                                <td align="right"><fmt:formatNumber value="${sub.bonusAmountNew}" type="currency" pattern="0.00"/><br/></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="14" align="right">
                                <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}" total="${pageBean.itemTotal}" gotoURI="${pageContext.request.contextPath}/manages/countServlet?action=accountTableForSub"/>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </td>
    </tr>
</table>
</body>
</html>