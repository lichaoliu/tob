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
<tr>
    <td width="20%" align="right" class="bold">
        奖级信息:
    </td>
</tr>
<table align="right">
<c:if test="${mainIssue.lotteryCode == '001'}">
    <tr>
        <td>
            一等奖单注奖金
            <input name="firstAmount" readonly="true" value="${award[0].amount == null ? 0 : award[0].amount}" class="disText"/>元，
        </td>
        <td>全国个数<input name="firstCount" value="${award[0].total == null ? 0 : award[0].total}"/>，</td>
        <td>地方个数<input name="cityFirstCount" value="${award[0].cityCount == null ? 0 : award[0].cityCount}"/></td>
    </tr>
    <tr>
        <td>
            二等奖单注奖金
            <input name="secondAmount" readonly="true" value="${award[1].amount == null ? 0 : award[1].amount}"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="secondCount" value="${award[1].total == null ? 0 : award[1].total}"/>，</td>
        <td> 地方个数<input name="citySecondCount" value="${award[1].cityCount == null ? 0 : award[1].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            三等奖单注奖金
            <input name="thirdAmount" readonly="true" value="3000" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="thirdCount" value="${award[2].total == null ? 0 : award[2].total}"/>，</td>
        <td> 地方个数<input name="cityThirdCount" value="${award[2].cityCount == null ? 0 : award[2].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            四等奖单注奖金
            <input name="fourthAmount" readonly="true" value="200" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="fourthCount" value="${award[3].total == null ? 0 : award[3].total}"/>，</td>
        <td> 地方个数<input name="cityFourthCount" value="${award[3].cityCount == null ? 0 : award[3].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            五等奖单注奖金
            <input name="fifthAmount" readonly="true" value="10" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="fifthCount" value="${award[4].total == null ? 0 : award[4].total}"/>，</td>
        <td> 地方个数<input name="cityFifthCount" value="${award[4].cityCount == null ? 0 : award[4].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            六等奖单注奖金
            <input name="sixthAmount" readonly="true" value="5" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="sixthCount" value="${award[5].total == null ? 0 : award[5].total}"/>，</td>
        <td> 地方个数<input name="citySixthCount" value="${award[5].cityCount == null ? 0 : award[5].cityCount}"/>
        </td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '002'}">
    <tr>
        <td>
            单选奖单注奖金
            <input name="firstAmount" readonly="true" value="1000" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="firstCount" value="${award[0].total == null ? 0 : award[0].total}"/>，</td>
        <td> 地方个数<input name="cityFirstCount" value="${award[0].cityCount == null ? 0 : award[0].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            组选3奖单注奖金
            <input name="secondAmount" readonly="true" value="320" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="secondCount" value="${award[1].total == null ? 0 : award[1].total}"/>，</td>
        <td> 地方个数<input name="citySecondCount" value="${award[1].cityCount == null ? 0 : award[1].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            组选6奖单注奖金
            <input name="thirdAmount" readonly="true" value="160" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="thirdCount" value="${award[2].total == null ? 0 : award[2].total}"/>，</td>
        <td> 地方个数<input name="cityThirdCount" value="${award[2].cityCount == null ? 0 : award[2].cityCount}"/>
        </td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '004'}">
    <tr>
        <td>
            一等奖单注奖金
            <input name="firstAmount" readonly="true" value="${award[0].amount == null ? 0 : award[0].amount}"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="firstCount" value="${award[0].total == null ? 0 : award[0].total}"/>，</td>
        <td> 地方个数<input name="cityFirstCount" value="${award[0].cityCount == null ? 0 : award[0].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            二等奖单注奖金
            <input name="secondAmount" readonly="true" value="${award[1].amount == null ? 0 : award[1].amount}"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="secondCount" value="${award[1].total == null ? 0 : award[1].total}"/>，</td>
        <td> 地方个数<input name="citySecondCount" value="${award[1].cityCount == null ? 0 : award[1].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            三等奖单注奖金
            <input name="thirdAmount" readonly="true" value="${award[2].amount == null ? 0 : award[2].amount}"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="thirdCount" value="${award[2].total == null ? 0 : award[2].total}"/>，</td>
        <td> 地方个数<input name="cityThirdCount" value="${award[2].cityCount == null ? 0 : award[2].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            四等奖单注奖金
            <input name="fourthAmount" readonly="true" value="200" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="fourthCount" value="${award[3].total == null ? 0 : award[3].total}"/>，</td>
        <td> 地方个数<input name="cityFourthCount" value="${award[3].cityCount == null ? 0 : award[3].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            五等奖单注奖金
            <input name="fifthAmount" readonly="true" value="50" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="fifthCount" value="${award[4].total == null ? 0 : award[4].total}"/>，</td>
        <td> 地方个数<input name="cityFifthCount" value="${award[4].cityCount == null ? 0 : award[4].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            六等奖单注奖金
            <input name="sixthAmount" readonly="true" value="10" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="sixthCount" value="${award[5].total == null ? 0 : award[5].total}"/>，</td>
        <td> 地方个数<input name="citySixthCount" value="${award[5].cityCount == null ? 0 : award[5].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            七等奖单注奖金
            <input name="seventhAmount" readonly="true" value="5" readonly="true" class="disText"/>元，
        </td>
        <td> 全国个数<input name="seventhCount" value="${award[6].total == null ? 0 : award[6].total}"/>，</td>
        <td> 地方个数<input name="citySeventhCount" value="${award[6].cityCount == null ? 0 : award[6].cityCount}"/>
        </td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '005'}">
    <tr>
        <td>
            一等奖单注奖金
            <input name="firstAmount" readonly="true" value="${award[0].amount == null ? 0 : award[0].amount}" class="disText"/>元，
        </td>
        <td> 全国个数<input name="firstCount" value="${award[0].total == null ? 0 : award[0].total}"/>，</td>
        <td> 地方个数<input name="cityFirstCount" value="${award[0].cityCount == null ? 0 : award[0].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            二等奖单注奖金
            <input name="secondAmount" readonly="true" value="${award[1].amount == null ? 0 : award[1].amount}" class="disText"/>元，
        </td>
        <td> 全国个数<input name="secondCount" value="${award[1].total == null ? 0 : award[1].total}"/>，</td>
        <td> 地方个数<input name="citySecondCount" value="${award[1].cityCount == null ? 0 : award[1].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            三等奖单注奖金
            <input name="thirdAmount" readonly="true" value="10000" readonly="true" class="disText"/>元，
        </td>
        <td> 全国个数<input name="thirdCount" value="${award[2].total == null ? 0 : award[2].total}"/>，</td>
        <td> 地方个数<input name="cityThirdCount" value="${award[2].cityCount == null ? 0 : award[2].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            四等奖单注奖金
            <input name="fourthAmount" readonly="true" value="500" readonly="true" class="disText"/>元，
        </td>
        <td> 全国个数<input name="fourthCount" value="${award[3].total == null ? 0 : award[3].total}"/>，</td>
        <td> 地方个数<input name="cityFourthCount" value="${award[3].cityCount == null ? 0 : award[3].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            五等奖单注奖金
            <input name="fifthAmount" readonly="true" value="50" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="fifthCount" value="${award[4].total == null ? 0 : award[4].total}"/>，</td>
        <td> 地方个数<input name="cityFifthCount" value="${award[4].cityCount == null ? 0 : award[4].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            六等奖单注奖金
            <input name="sixthAmount" readonly="true" value="5" readonly="true"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="sixthCount" value="${award[5].total == null ? 0 : award[5].total}"/>，</td>
        <td> 地方个数<input name="citySixthCount" value="${award[5].cityCount == null ? 0 : award[5].cityCount}"/>
        </td>
    </tr>
</c:if>
<c:if test="${mainIssue.lotteryCode == '003'}">
    <tr>
        <td>
            特别奖单注奖金
            <input name="firstAmount" readonly="true" value="${award[0].amount == null ? 0 : award[0].amount}"  class="disText"/>元，
        </td>
        <td> 全国个数<input name="firstCount" value="${award[0].total == null ? 0 : award[0].total}"/>，</td>
        <td> 地方个数<input name="cityFirstCount" value="${award[0].cityCount == null ? 0 : award[0].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            一等奖单注奖金
            <input name="secondAmount" readonly="true" value="${award[1].amount == null ? 0 : award[1].amount}" class="disText"/>元，
        </td>
        <td> 全国个数<input name="secondCount" value="${award[1].total == null ? 0 : award[1].total}"/>，</td>
        <td> 地方个数<input name="citySecondCount" value="${award[1].cityCount == null ? 0 : award[1].cityCount}"/>
        </td>
    </tr>
    <tr>
        <td>
            二等奖单注奖金
            <input name="thirdAmount" readonly="true" value="10" readonly="true" class="disText"/>元，
        </td>
        <td> 全国个数<input name="thirdCount" value="${award[2].total == null ? 0 : award[2].total}"/>，</td>
        <td> 地方个数<input name="cityThirdCount" value="${award[2].cityCount == null ? 0 : award[2].cityCount}"/>
        </td>
    </tr>
    <%--<tr>
        <td>
            加奖单注奖金&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;<input name="fourthAmount" value="${award[3].amount == null ? 0 : award[3].amount}"/>元，
        </td>
        <td> 全国个数<input name="fourthCount" value="${award[3].total == null ? 0 : award[3].total}"/>，</td>
        <td> 地方个数<input name="cityFourthCount" value="${award[3].cityCount == null ? 0 : award[3].cityCount}"/>
        </td>
    </tr>--%>
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
    var firstAmount = form1.firstAmount.value;
    if ("" == firstAmount || null == firstAmount) {
        alert("一等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(firstAmount)) {
        alert("请输入有效一等奖金额");
        return false;
    }
    var firstCount = form1.firstCount.value;
    if ("" == firstCount) {
        alert("一等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(firstCount)) {
        alert("一等奖全国个数必须输入整数");
        return false;
    }
    var cityFirstCount = form1.cityFirstCount.value;
    if ("" == cityFirstCount) {
        alert("一等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(cityFirstCount)) {
        alert("一等奖地方个数必须输入整数");
        return false;
    }


    var secondAmount = form1.secondAmount.value;
    if ("" == secondAmount) {
        alert("二等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(secondAmount)) {
        alert("请输入有效二等奖金额");
        return false;
    }
    var secondCount = form1.secondCount.value;
    if ("" == secondCount) {
        alert("二等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(secondCount)) {
        alert("二等奖全国个数必须输入整数");
        return false;
    }
    var citySecondCount = form1.citySecondCount.value;
    if ("" == citySecondCount) {
        alert("二等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(citySecondCount)) {
        alert("二等奖地方个数必须输入整数");
        return false;
    }


    var thirdAmount = form1.thirdAmount.value;
    if ("" == thirdAmount) {
        alert("三等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(thirdAmount)) {
        alert("请输入有效三等奖金额");
        return false;
    }
    var thirdCount = form1.thirdCount.value;
    if ("" == thirdCount) {
        alert("三等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(thirdCount)) {
        alert("三等奖全国个数必须输入整数");
        return false;
    }
    var cityThirdCount = form1.cityThirdCount.value;
    if ("" == cityThirdCount) {
        alert("三等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(cityThirdCount)) {
        alert("三等奖地方个数必须输入整数");
        return false;
    }


    var fourthAmount = form1.fourthAmount.value;
    if ("" == fourthAmount) {
        alert("四等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(fourthAmount)) {
        alert("请输入有效四等奖金额");
        return false;
    }
    var fourthCount = form1.fourthCount.value;
    if ("" == fourthCount) {
        alert("四等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(fourthCount)) {
        alert("四等奖全国个数必须输入整数");
        return false;
    }
    var cityFourthCount = form1.cityFourthCount.value;
    if ("" == cityFourthCount) {
        alert("四等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(cityFourthCount)) {
        alert("四等奖地方个数必须输入整数");
        return false;
    }

    var fifthAmount = form1.fifthAmount.value;
    if ("" == fifthAmount) {
        alert("五等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(fifthAmount)) {
        alert("请输入有效五等奖金额");
        return false;
    }
    var fifthCount = form1.fifthCount.value;
    if ("" == fifthCount) {
        alert("五等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(fifthCount)) {
        alert("五等奖全国个数必须输入整数");
        return false;
    }
    var cityFifthCount = form1.cityFifthCount.value;
    if ("" == cityFifthCount) {
        alert("五等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(cityFifthCount)) {
        alert("五等奖地方个数必须输入整数");
        return false;
    }

    var sixthAmount = form1.sixthAmount.value;
    if ("" == sixthAmount) {
        alert("六等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(sixthAmount)) {
        alert("请输入有效六等奖金额");
        return false;
    }
    var sixthCount = form1.sixthCount.value;
    if ("" == sixthCount) {
        alert("六等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(sixthCount)) {
        alert("六等奖全国个数必须输入整数");
        return false;
    }
    var citySixthCount = form1.citySixthCount.value;
    if ("" == citySixthCount) {
        alert("六等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(citySixthCount)) {
        alert("六等奖地方个数必须输入整数");
        return false;
    }


    var seventhAmount = form1.seventhAmount.value;
    if ("" == seventhAmount) {
        alert("七等奖奖金不能为空");
        return false;

    } else if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(seventhAmount)) {
        alert("请输入有效七等奖金额");
        return false;
    }
    var seventhCount = form1.seventhCount.value;
    if ("" == seventhCount) {
        alert("七等奖全国个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(seventhCount)) {
        alert("七等奖全国个数必须输入整数");
        return false;
    }
    var citySeventhCount = form1.citySeventhCount.value;
    if ("" == citySeventhCount) {
        alert("七等奖七等奖地方个数不能为空");
        return false;

    } else if (!/^[0-9]+$/.test(citySeventhCount)) {
        alert("七等奖地方个数必须输入整数");
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