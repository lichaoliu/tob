<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>编辑公告</title>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/selectArea.js"></script>
    <link href="${pageContext.request.contextPath}/css/user.css" rel="stylesheet"/>
    <style type="text/css">
        .disText{
            color:#cccccc;
        }
    </style>
</head>

<body <c:if test="${issueMsg != null}">onload="alertResult('${issueMsg}')"</c:if>>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
<c:if test="${issueMsg != null}">
    <script>
        function alertResult(issueMsg) {
            alert("编辑成功");
            window.close();
        }
    </script>
</c:if>
<tr>
    <td class="title">
        ${myf:getLotteryChinaName(mainIssue.lotteryCode)}第${mainIssue.name}期开奖公告信息编辑
    </td>
</tr>
<c:if test="${mainIssue != null}">
<tr>
<td style="padding: 4px;">
<form id="form1" method="post"
      action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=updateIssueMsg">
<table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
<input type="hidden" name="mainIssueId" value="${mainIssue.id}"/>
<input type="hidden" name="lotteryCode" value="${mainIssue.lotteryCode}"/>
<input type="hidden" name="issue" value="${mainIssue.name}"/>
<tr>
    <td width="20%" align="right" class="bold">
            ${myf:getLotteryChinaName(mainIssue.lotteryCode)}:
    </td>
    <td>${mainIssue.name}期</td>
</tr>
<tr>
    <td width="20%" align="right" class="bold">
        开售时间:
    </td>
    <td><fmt:formatDate value="${mainIssue.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
    <td width="20%" align="right" class="bold">
        止售时间:
    </td>
    <td><fmt:formatDate value="${mainIssue.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
    <td width="20%" align="right" class="bold">
        开奖时间:
    </td>
    <td><fmt:formatDate value="${mainIssue.bonusTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
</tr>
<tr>
    <td width="20%" align="right" class="bold">
        开奖号码:
    </td>
    <td>${mainIssue.bonusNumber }</td>
</tr>
<tr>
    <td width="20%" align="right" class="bold">
        销售总额:
    </td>
    <td><input type="text" value="${mainIssue.globalSaleTotal == null ? 0 : mainIssue.globalSaleTotal}" name="globalSaleTotal"
               id="globalSaleTotal"/>元
    </td>
</tr>
<c:if test="${mainIssue.lotteryCode != '002'}">
    <tr>
        <td width="20%" align="right" class="bold">
            奖池信息:
        </td>
        <td><input type="text" value="${mainIssue.prizePool == null ? 0 : mainIssue.prizePool}" name="prizePool"
                   id="prizePool"/>元
        </td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '300'}">
<tr>
    <td width="20%" align="right" class="bold">
        任九销售总额:
    </td>
    <td><input type="text" value="${mainIssue.backup3 == null ? 0 : mainIssue.backup3}" name="globalSaleTotal9"
               id="globalSaleTotal9"/>元
    </td>
</tr>
    <%--<tr>--%>
        <%--<td width="20%" align="right" class="bold">--%>
            <%--任九奖池信息:--%>
        <%--</td>--%>
        <%--<td><input type="text" value="${mainIssue.backup2 == null ? 0 : mainIssue.backup2}" name="prizePool9"--%>
                   <%--id="prizePool9"/>元--%>
        <%--</td>--%>
    <%--</tr>--%>
</c:if>
<c:if test="${mainIssue.lotteryCode == '113'}">
    <tr>
        <td width="20%" align="right" class="bold">
            生肖乐销售总额:
        </td>
        <td><input type="text" value="${mainIssue.backup2 == null ? 0 : mainIssue.backup2}" name="backup2"
                   id="backup2"/>元
        </td>
    </tr>
</c:if>
<tr>
    <td width="20%" align="right" class="bold">
        奖级信息:
    </td>
</tr>
<table align="right">
<c:if test="${mainIssue.lotteryCode == '113'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            一等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv02}"/>
        <td>
            二等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv03}"/>
        <td>
            三等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv04}"/>
        <td>
            四等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv05}"/>
        <td>
            五等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv06}"/>
        <td>
            六等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv11}"/>
        <td>
            一等奖追加单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount - award.lv01.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv12}"/>
        <td>
            二等奖追加单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount - award.lv02.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv13}"/>
        <td>
            三等奖追加单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount - award.lv03.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv14}"/>
        <td>
            四等奖追加单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount - award.lv04.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv15}"/>
        <td>
            五等奖追加单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount - award.lv05.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '111'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            一等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv02}"/>
        <td>
            二等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="50" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv03}"/>
        <td>
            三等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="5" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '110'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            一等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv02}"/>
        <td>
            二等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv03}"/>
        <td>
            三等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="1800" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv04}"/>
        <td>
            四等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="300" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv05}"/>
        <td>
            五等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="20" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv06}"/>
        <td>
            六等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="5" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '108'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            排列三直选单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv02}"/>
        <td>
            排列三组三单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv03}"/>
        <td>
            排列三组六单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '109'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            排列五单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '300'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            一等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv02}"/>
        <td>
            二等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
    <tr><c:set var="lv" value="${award.lv03}"/>
        <td>
            任选九单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '301'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            一等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '302'}">
    <tr><c:set var="lv" value="${award.lv01}"/>
        <td>
            一等奖单注奖金
            <input name="amount" readonly="true" class="disText"  value="${lv.amount == null ? 0 : lv.amount}" class="width:5px"/>元，
        </td>
        <td>全国个数<input name="total" value="${lv.total == null ? 0 : lv.total}"/>，</td>
        <td>地方个数<input name="cityCount" value="${lv.cityCount == null ? 0 : lv.cityCount}"/></td>
    </tr>
</c:if>
</table>

<tr>
    <td colspan="2" align="center">
        <input type="submit" id="subBtn" value="保存" class="submit"
               style="width: 64px; border: none" onClick="return qr()"/>
        <input type="button" name="Submit" value="取消" class="submit"
               style="width: 64px; border: none" onClick="back()">
    </td>
</tr>
</table>
</form>
</td>
</tr>
</c:if>
</table>
<script type="text/javascript">
function qr() {
    var saleTotal = form1.saleTotal.value;
    if ("" == saleTotal || null == saleTotal) {
        alert("销售总额不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(saleTotal)) {
        alert("请输入有效销售总额");
        return false;
    }
    var prizePool = form1.prizePool.value;
    if ("" == prizePool || null == prizePool) {
        alert("奖池信息不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(prizePool)) {
        alert("请输入有效奖池信息");
        return false;
    }
  
    var bln = confirm("确认编辑公告？");
    return bln;
}
function back() {
    window.close();
}
</script>
</body>
</html>