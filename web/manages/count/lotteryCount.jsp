<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>彩票报表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="max-age=0" forua="true"/>
    <meta http-equiv="Cache-Control" content="no-cache" forua="true"/>
    <meta http-equiv="Cache-Control" content="must-revalidate" forua="true"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/zch.js"></script>
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
            $("#form1").attr("action", "${pageContext.request.contextPath}/manages/outData?action=lotteryCount");
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
            统计报表 >> 彩种报表
        </td>
        <td class="title" style="text-align:right;padding-right: 8px">
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;" colspan="2">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/countServlet?action=lotteryCount">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td>订单接收时间&nbsp;<span style="color: red">*</span></td>
                        <td>
                            <input type="text" name="createStartTime" value="${createStartTime}" onClick="WdatePicker()"
                                   style="width: 83px;"
                                   readonly>
                            －
                            <input type="text" name="createEndTime" value="${createEndTime}" onClick="WdatePicker()"
                                   style="width: 83px;" readonly>
                        </td>

                        <td>
                            期次范围
                        </td>
                        <td>
                            <input type="text" name="issueStatus" value="${issueStatus}" style="width: 83px;">
                            －
                            <input type="text" name="issueEnd" value="${issueEnd}" style="width: 83px;">
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
                    </tr>
                    <tr>
                        <td>
                            订单送票时间
                        </td>
                        <td>
                            <input type="text" name="sendStartTime" value="${sendStartTime}" onClick="WdatePicker()"
                                   style="width: 83px;"
                                   readonly>
                            －
                            <input type="text" name="sendEndTime" value="${sendEndTime}" onClick="WdatePicker()"
                                   style="width: 83px;" readonly>
                        </td>
                        <td>彩票状态</td>
                        <td>
                            <select name="orderStatus">
                                <option value=""
                                        <c:if test="${'' == orderStatus }">selected</c:if>>全部
                                </option>
                                <option value="0"
                                        <c:if test="${0 == orderStatus }">selected</c:if>>未送票
                                </option>
                                <option value="2"
                                        <c:if test="${2 == orderStatus }">selected</c:if>>送票未回执
                                </option>
                                <option value="3"
                                        <c:if test="${3 == orderStatus }">selected</c:if>>出票成功
                                </option>
                                <option value="4"
                                        <c:if test="${4 == orderStatus }">selected</c:if>>出票失败
                                </option>
                            </select>
                        </td>
                        <td>出票口</td>
                        <td align="left">
                            <select name="postCode">
                                <option value=""
                                        <c:if test="${postCode == ''}">selected</c:if>>全部
                                </option>
                                <c:forEach var="postBean" items="${myf:getPostMaps()}">
                                    <option value="${postBean.code}"
                                            <c:if test="${postCode == postBean.code}">selected</c:if>>${postBean.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td align="right" colspan="2"><input type="submit" name="Submit" onclick="return ifLink()" class="submit" value="统计"
                                                             style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="type" value="get"/>
            </form>
            <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
                <tr>
                    <td colspan="7">
                        <div style="color:#0033cc;font-weight: bold; float: left;">
                            [本次查询结果：投注总额:${totalAmount}元,税前奖金:${totalFixBonuxAmount}元,税后奖金:${totalBonuxAmount}元]
                        </div>
                        <div style=" float:right;">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" size="2" id="inputStart" value="1"
                                   onkeyup="this.value=this.value.replace(/\D/g,'')"
                                   onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                            页 - <input type="text" size="2" id="inputEnd" value="1"
                                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 页
                            <a href="javascript:void(0)" onclick="exportExcel();"
                               style="font-weight: bold;">导出</a>
                        </div>
                    </td>
                </tr>
                <tr id="one">
                    <td width="5%">
                        序号
                    </td>
                    <td width="20%">
                        彩种
                    </td>
                    <td width="25%">
                        投注金额
                    </td>
                    <td width="25%">
                        税前金额
                    </td>
                    <td width="25%">
                        税后金额
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
                                    ${myf:getLotteryChinaName(sub.lotteryCode)}<br/>
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${sub.amount}" pattern="0.00"/><br/>
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${sub.fixBonusAmount}" pattern="0.00"/><br/>
                            </td>
                            <td align="right">
                                <fmt:formatNumber value="${sub.bonusAmount}" pattern="0.00"/>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>