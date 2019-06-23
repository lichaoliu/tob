<%--
  Created by IntelliJ IDEA.
  User: MengJingyi
  QQ: 116741034
  Date: 13-5-31
  Time: 下午5:51
  To change this template use File | Settings | File Templates.
--%>
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
            统计报表 >> 商户期次对账报表
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" method="post" id="form1"
                  action="${pageContext.request.contextPath}/manages/countServlet?action=lotteryIssueCount">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>接入商ID</td>
                        <td><input type="text" name="sid" value="${sid}" class="input"/></td>
                        <td>接入商名称</td>
                        <td>
                            <select name="name">
                                <option value=""
                                        <c:if test="${'' == name }">selected</c:if>>全部
                                </option>
                                <c:forEach items="${myf:getSidNameList()}" var="nameMap">
                                    <option value="${nameMap.sid }"
                                            <c:if test="${nameMap.sid == name }">selected</c:if> >
                                            ${nameMap.name }
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
                                <option value="1"
                                        <c:if test="${1 == ticketStatus }">selected</c:if>>调度中
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
                                <option value="5"
                                        <c:if test="${5 == ticketStatus }">selected</c:if>>系统取消
                                </option>
                                <option value="6"
                                        <c:if test="${6 == ticketStatus }">selected</c:if>>重发
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>结期时间</td>
                        <td>
                            <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                   name="startTime" value="${startTime }" readonly/>
                            - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                     name="endTime" value="${endTime }" readonly/>
                        </td>
                        <td>期次状态</td>
                        <td align="left">
                            <select name="issueStatus">
                                <option value=""
                                        <c:if test="${issueStatus == ''}">selected</c:if>>全部
                                </option>
                                <option value="0"
                                        <c:if test="${issueStatus == 0}">selected</c:if>>预售
                                </option>
                                <option value="1"
                                        <c:if test="${issueStatus == 1}">selected</c:if>>开售
                                </option>
                                <option value="2"
                                        <c:if test="${issueStatus == 2}">selected</c:if>>暂停
                                </option>
                                <option value="3"
                                        <c:if test="${issueStatus == 3}">selected</c:if>>结期
                                </option>
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
                    </tr>
                    <tr>
                        <td align="right" colspan="6"><input type="submit" name="Submit" class="submit" value="统计"
                                                             style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>

            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="8" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <tr>
                        <td align="left" colspan="11">
                            <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal}条记录;
                                累计投注金额<fmt:formatNumber value="${requestScope.totalAmount}" pattern="0.00"/>元,
                                税前中奖金额<fmt:formatNumber value="${requestScope.totalFixBonuxAmount}" pattern="0.00"/>元,
                                税后中奖金额<fmt:formatNumber value="${requestScope.totalBonuxAmount}" pattern="0.00"/>元
                                ]
                            </div>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="10%">序号</td>
                    <td width="15%">彩种</td>
                    <td width="15%">期次</td>
                    <td>金额(元)</td>
                    <td width="20%">税前奖金(元)</td>
                    <td width="20%">税后奖金(元)</td>
                </tr>
                <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
                    <tr>
                        <td align="center">${(page-1)*requestScope.pageSize + cont.count}</td>
                        <td>
                                ${myf:getLotteryChinaName(objectList.lotteryCode)}
                        </td>
                        <td>
                                ${objectList.issue}
                        </td>
                        <td align="right">
                            <fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>
                        </td>

                        <td align="right">
                            <fmt:formatNumber
                                    value="${objectList.fixBonusAmount == null ? 0.00 : objectList.fixBonusAmount}"
                                    pattern="0.00"/>
                        </td>

                        <td align="right">
                            <fmt:formatNumber
                                    value="${objectList.bonusAmonnt == null ? 0.00 : objectList.bonusAmonnt}"
                                    pattern="0.00"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="11" align="right">
                        <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                      total="${pageBean.itemTotal}"
                                      gotoURI="${pageContext.request.contextPath}/manages/countServlet?action=lotteryIssueCount"/>
                    </td>
                </tr>
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