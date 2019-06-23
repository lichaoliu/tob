<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>进出明细</title>
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
            $("#form1").attr("action", "${pageContext.request.contextPath}/manages/outData?action=accountDetail");
            $("#form1").submit();
            $("#form1").attr("action", action);
        }
    </script>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<tr>
    <td class="title" style="text-align:left;">
        账务管理 >> 进出明细
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" id="form1" method="post"
      action="${pageContext.request.contextPath}/manages/accountManagesServlet?action=getAccountLogList">
    <input type="hidden" name="startPage" id="startPage"/>
    <input type="hidden" name="endPage" id="endPage"/>
    <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
        <tr>
            <td>接入商名称</td>
            <td>
                <select name="sid">
                    <option value="">全部</option>
                    <c:forEach items="${myf:getMemberList()}" var="member">
                        <option value="${member.sid}"
                                <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td>交易时间</td>
            <td><input type="text" onClick="WdatePicker()" name="startTime" value="${startTime }" readonly
                       style="width: 83px;"/>
                - <input type="text" onClick="WdatePicker()" name="endTime" value="${endTime }" readonly
                         style="width: 83px;"/>
            </td>
            <td>关联票号</td>
            <td><input name="ticketId" type="text" value="${ticketId}">
            </td>
        </tr>
        <tr>
            <td>交易类型</td>
            <td>
                <select name="eventCode">
                    <option value="">全部</option>
                    <option value="00100,00200,00400,00500"
                            <c:if test="${param.eventCode == '00100,00200,00400,00500'}">selected</c:if>>收入
                    </option>
                    <option value="00100"
                            <c:if test="${param.eventCode == '00100'}">selected</c:if> >&#160;&#160;出票失败
                    </option>
                    <option value="00200" <c:if test="${param.eventCode == '00200'}">selected</c:if>>&#160;&#160;派奖
                    </option>
                    <option value="00400" <c:if test="${param.eventCode == '00400'}">selected</c:if>>&#160;&#160;返佣
                    </option>
                    <option value="00500" <c:if test="${param.eventCode == '00500'}">selected</c:if>>&#160;&#160;额度加
                    </option>
                    <option value="10000,10400" <c:if test="${param.eventCode == '10000,10400'}">selected</c:if>>支出
                    </option>
                    <option value="10000"
                            <c:if test="${param.eventCode == '10000'}">selected</c:if> >&#160;&#160;购买
                    </option>
                    <option value="10400" <c:if test="${param.eventCode == '10400'}">selected</c:if>>&#160;&#160;额度减
                    </option>
                </select>
            </td>
            <td>
                发生额
            </td>
            <td><input name="amountc" value="${amountc }" style="width: 83px;"
                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                - <input name="amountd" value="${amountd }" style="width: 83px;"
                         onkeyup="this.value=this.value.replace(/\D/g,'')"
                         onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
            </td>
            <td>关联批次号</td>
            <td><input name="orderId" type="text" value="${orderId}">
            </td>
        </tr>
        <tr>
            <td colspan="6" align="center">
                <input type="submit" name="Submit" class="submit" value="查询" style="width: 64px; border: none">
            </td>
        </tr>
    </table>
</form>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <c:if test="${pageBean.pageContent!=null}">
        <tr>
            <td align="left" colspan="11">
                <div style="color:#0033cc;font-weight: bold; float: left;">
                    [本次查询结果共${pageBean.itemTotal }条记录,累计发生额共<fmt:formatNumber
                        value="${amountCount }"
                        pattern="0.00"/>元]
                </div>
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
    </c:if>
    <tr id="one">
        <td width="5%">序号</td>
        <td width="15%" align="center">
            接入商
        </td>
        <td width="10%" align="center">
            交易编号
        </td>
        <td width="10%" align="center">
            交易时间
        </td>
        <td width="8%" align="center">
            交易类型
        </td>
        <td width="8%" align="center">
            发生额(元)
        </td>
        <td width="10%" align="center">
            奖金变动(元)
        </td>
        <td width="10%" align="center">
            充值变动(元)
        </td>
        <td width="10%" align="center">
            赠金变动(元)
        </td>
        <td width="10%" align="center">
            冻结变动(元)
        </td>
        <td align="center" width="15%">
            说明
        </td>
    </tr>
    <c:if test="${pageBean.pageContent==null}">
        <tr>
            <td colspan="11" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
        </tr>
    </c:if>
    <c:if test="${pageBean.pageContent!=null}">
        <c:forEach var="accountLogList" items="${pageBean.pageContent}" varStatus="cont">
            <tr>
                <td align="center">${requestScope.pageSize*(page - 1) + cont.count}</td>
                <td>
                    名称:${accountLogList[1].name}<br/>
                    ID:${accountLogList[1].sid}
                </td>
                <td align="center">
                        ${accountLogList[0].orderId }
                </td>
                <td align="center">
                    <fmt:formatDate value="${accountLogList[0].createTime }" pattern="yyyy-MM-dd"/><br/>
                    <fmt:formatDate value="${accountLogList[0].createTime }" pattern="HH:mm:ss"/>
                </td>
                <td align="center">
                        ${myf:getEventCodeType(accountLogList[0].eventCode) }

                </td>
                <td align="right">
                    <fmt:formatNumber
                            value="${
                            myf:abs(accountLogList[0].freezeAmount) == 0 ?
                            (
                            myf:abs(accountLogList[0].rechargeAmount) + myf:abs(accountLogList[0].bonusAmount) +
                             myf:abs(accountLogList[0].presentAmount)
                             ) : myf:abs(accountLogList[0].freezeAmount)}"
                            pattern="0.00"/><br/>
                </td>
                <td align="right">
                    前:<fmt:formatNumber
                        value="${accountLogList[0].bonusAmountNew - accountLogList[0].bonusAmount}"
                        pattern="0.00"/><br/>
                    后:<fmt:formatNumber
                        value="${accountLogList[0].bonusAmountNew}"
                        pattern="0.00"/>
                </td>
                <td align="right">
                    前:<fmt:formatNumber
                        value="${accountLogList[0].rechargeAmountNew - accountLogList[0].rechargeAmount}"
                        pattern="0.00"/><br/>
                    后:<fmt:formatNumber
                        value="${accountLogList[0].rechargeAmountNew}"
                        pattern="0.00"/>
                </td>
                <td align="right">
                    前:<fmt:formatNumber
                        value="${accountLogList[0].presentAmountNew - accountLogList[0].presentAmount}"
                        pattern="0.00"/><br/>
                    后:<fmt:formatNumber
                        value="${accountLogList[0].presentAmountNew}"
                        pattern="0.00"/>
                </td>
                <td align="right">
                    前:<fmt:formatNumber
                        value="${accountLogList[0].freezeAmountNew - accountLogList[0].freezeAmount}"
                        pattern="0.00"/><br/>
                    后:<fmt:formatNumber
                        value="${accountLogList[0].freezeAmountNew}"
                        pattern="0.00"/>
                </td>
                <td align="left">
                        ${accountLogList[0].memo}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="11" align="right">
                <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                              total="${pageBean.itemTotal}"
                              gotoURI="${pageContext.request.contextPath}/manages/accountManagesServlet?action=getAccountLogList"/>
            </td>
        </tr>
    </c:if>
</table>
</td>
</tr>
</table>
<script>
    function ifLink(str) {
        var bln = confirm(str);
        return bln;
    }
</script>
</body>
</html>
