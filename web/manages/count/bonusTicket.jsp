<%--
  Created by IntelliJ IDEA.
  User: MengJingyi
  QQ: 116741034
  Date: 13-4-23
  Time: 下午4:12
  To change this template use File | Settings | File Templates.
--%>
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
            统计报表 >> 中奖差异报表
        </td>
        <td class="title" style="text-align:right;padding-right: 8px">
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;" colspan="2">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/countServlet?action=bonusTicket">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>
                            期次范围
                        </td>
                        <td width="33%">
                            <input type="text" name="issueStart" value="${issueStart}">
                            －
                            <input type="text" name="issueEnd" value="${issueEnd}">
                        </td>
                        <td>
                            彩种
                        </td>
                        <td>
                            <select name="lotteryCode">
                                <option value=""
                                        <c:if test="${'' == lotteryIssue.lotteryCode }">selected</c:if>>
                                    全部
                                </option>
                                <c:forEach var="lottery" items="${myf:findAllLotteryCode()}">
                                    <option value="${lottery.key}"
                                            <c:if test="${lottery.key == lotteryCode}">selected</c:if> >
                                            ${lottery.value.name }
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            出票口
                        </td>
                        <td>
                            <select name="postCode">
                                <option value=""
                                        <c:if test="${postCode == ''}">selected</c:if>>全部
                                </option>
                                <c:forEach var="postBean" items="${myf:getPostMaps()}">
                                    <option value="${postBean.code}"
                                            <c:if test="${postCode == postBean.code}">selected</c:if>>${postBean.name}
                                    </option>
                                </c:forEach>
                                <option value="00"
                                        <c:if test="${postCode == '00'}">selected</c:if>>待分配
                                </option>
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
                    <td colspan="7">
                        <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal}条记录]
                        </div>
                        <div style="clear:both;"></div>
                    </td>
                </tr>
                <tr id="one">
                    <td width="5%">
                        序号
                    </td>
                    <td width="10%">
                        期次
                    </td>
                    <td>
                        彩种
                    </td>
                    <td width="18%">
                        自主算奖税前
                    </td>
                    <td width="18%">
                        出票口算奖税前
                    </td>
                    <td width="18%">
                        自主算奖税后
                    </td>
                    <td width="18%">
                        出票口算奖税后
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
                                    ${'' == sub.issue ? sub.bissue : sub.issue }
                            </td>
                            <td>
                                    ${myf:getLotteryChinaName('' == sub.lotteryCode ? sub.blotteryCode : sub.lotteryCode)}
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${'' == sub.ATFAMONT ? '0.00' : sub.ATFAMONT}" pattern="0.00"/>
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${'' == sub.btfAmount ? '0.00' : sub.btfAmount}"
                                                  pattern="0.00"/>
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${'' == sub.atAmount ? '0.00' : sub.atAmount}" pattern="0.00"/>
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${'' == sub.btAmount ? '0.00' : sub.btAmount}" pattern="0.00"/>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="7" align="right">
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