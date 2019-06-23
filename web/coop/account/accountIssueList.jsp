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
            var pageTotal = '${pageBean.pageTotal}';
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
            $("#form1").attr("action", "${pageContext.request.contextPath}/coop/outData?action=accountIssue");
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
            统计报表 >> 销售报表
        </td>
        <td class="title" style="text-align:right;padding-right: 8px">
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;" colspan="2">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/coop/cooperationServlet?action=saleTable">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>
                            彩种
                        </td>
                        <td>
                            <select name="lotteryCode" id="lotteryCode">
                                <option value="" <c:if test="${'' == lotteryCode }">selected</c:if>>
                                    全部
                                </option>
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
                        <td>
                            期次
                        </td>
                        <td>
                            <input type="text" name="issue" value="${issue}">
                        </td>
                        <td align="right"><input type="submit" name="Submit" class="submit" value="统计"
                                                 style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr>
                    <td colspan="7">
                        <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal}条记录]</div>
                        <div style=" float:right;">
                            <input type="text" size="2" id="inputStart" value="1"
                                   onkeyup="this.value=this.value.replace(/\D/g,'')"
                                   onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                            页 - <input type="text" size="2" id="inputEnd" value="1"
                                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 页
                            <a href="javascript:void(0)" onclick="exportExcel();" style="font-weight: bold;">导出</a>
                        </div>
                        <div style="clear:both;"></div>
                    </td>
                </tr>
                <tr id="one">
                    <td width="5%">
                        序号
                    </td>
                    <td width="15%">
                        彩种/期号
                    </td>
                    <td width="16%">
                        开止时间
                    </td>
                    <td width="16%">
                        成功
                    </td>
                    <td width="16%">
                        失败
                    </td>
                    <td width="16%">
                        中奖
                    </td>
                    <td>
                        统计时间
                    </td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="7" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="sub" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                               ${(page-1)*pageSize+cont.count}
                            </td>
                            <td>
                                彩种:${myf:getLotteryChinaName(sub.lotteryCode)}<br/>
                                期次:${sub.issue}
                            </td>
                            <td>
                                开售:<fmt:formatDate value="${sub.startTime}" pattern="yy-MM-dd HH:mm:ss"/><br/>
                                止售:<fmt:formatDate value="${sub.endTime}" pattern="yy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                金额:<fmt:formatNumber value="${sub.successAmount}" pattern="0.00"/><br/>
                                票数:${sub.successTicket}
                            </td>
                            <td>
                                金额:<fmt:formatNumber value="${sub.failureAmount}" pattern="0.00"/><br/>
                                票数:${sub.failureTicket}
                            </td>
                            <td align="left">
                                票数:${sub.bonusTicket}<br/>
                                税前:<fmt:formatNumber value="${sub.bonusAmount}" pattern="0.00"/><br/>
                                税后:<fmt:formatNumber value="${sub.fixBonusAmount}" pattern="0.00"/>
                            </td>
                            <td align="center">
                                <fmt:formatDate value="${sub.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="7" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/coop/cooperationServlet"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>