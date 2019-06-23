<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>彩票列表</title>
    <meta http-equiv="refresh"
          content="30;url=${pageContext.request.contextPath}/manages/ticketManagesServlet?action=sti&createStartTime=${createStartTime}&createEndTime=${createEndTime}&postCode=${postCode}">
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
<script>
    function play_click(url) {
        var div = document.getElementById('music');
        div.src = url;
    }
</script>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<tr>
    <td class="title" style="text-align:left;">
        彩票管理 >> 未出票和期次异常查询
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" method="post"
      action="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=sti">
    <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
        <tr>
            <td>接收时间</td>
            <td>
                <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                       name="createStartTime" value="${createStartTime }" readonly/>
                - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                         name="createEndTime" value="${createEndTime }" readonly/>
            </td>
            <td>出票口</td>
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
        </tr>
        <tr>
            <td align="center" colspan="4">
                <input type="submit" name="Submit" class="submit" value="查询"
                       style="width: 64px; border: none">
                &nbsp;&nbsp;<input type="button" class="submit" value="停止报警" onclick="play_click('');"
                                   style="width: 64px; border: none">
            </td>

        </tr>
    </table>
</form>
<br>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <tr id="one">
        <td width="100%" colspan="7">未发票信息</td>
    </tr>
    <tr id="one">
        <td width="5%">序号</td>
        <td width="10%">彩种</td>
        <td width="10%">期次</td>
        <td>数量</td>
        <td width="20%">最早创建时间</td>
        <td width="20%">结期</td>
        <td width="5%">详细</td>
    </tr>
    <c:if test="${doingList!=null}">
        <c:forEach var="objectList1" items="${doingList}" varStatus="cont">
            <c:if test="${myf:getStiTime2(objectList1.CREATE_TIME,objectList1.LOTTERY_CODE)}">
                <audio id="music" src="" autoplay="autoplay" loop="true">
                </audio>
                <script>
                    play_click("${pageContext.request.contextPath}/mp3/wsp.mp3")
                </script>
            </c:if>
            <tr style="color:red">
                <td align="center">${cont.count}</td>
                <td align="center">
                        ${myf:getLotteryChinaName(objectList1.LOTTERY_CODE)}
                </td>
                <td align="center">
                <c:if test="${objectList1.LOTTERY_CODE != '200' && objectList1.LOTTERY_CODE != '201'}">
                        ${objectList1.ISSUE}
                 </c:if>
                 <c:if test="${objectList1.LOTTERY_CODE == '200' || objectList1.LOTTERY_CODE == '201'}">
                        ${objectList1.GAMEID}
                 </c:if>
                </td>
                <td align="center">
                        ${objectList1.TTS}
                </td>
                <td align="center">
                        ${objectList1.CREATE_TIME}
                </td>
                <td align="center">
                        ${myf:getSyTime(objectList1.DUPLEX_TIME)}
                </td>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=noSendticketList&lotteryCode=${objectList1.LOTTERY_CODE}&issue=${objectList1.ISSUE}&gameId=${objectList1.GAMEID}&ticketStatus=0&createStartTime=${createStartTime}&createEndTime=${createEndTime}">详细</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <tr id="one">
        <td width="100%" colspan="8">需手工重发票信息</td>
    </tr>
    <tr id="one">
        <td width="5%">序号</td>
        <td width="15%">出票口</td>
        <td width="10%">彩种</td>
        <td width="10%">期次</td>
        <td>数量</td>
        <td width="20%">最早创建时间</td>
        <td width="20%">结期</td>
        <td width="5%">详细</td>
    </tr>
    <c:if test="${reSendingList!=null}">
        <c:forEach var="objectList" items="${reSendingList}" varStatus="cont">
            <c:if test="${myf:getStiTime2(objectList.CREATE_TIME,objectList.LOTTERY_CODE)}">
                <audio id="music" src="" autoplay="autoplay" loop="true">
                </audio>
                <script>
                    play_click("${pageContext.request.contextPath}/mp3/xcf.mp3")
                </script>

            </c:if>
            <tr style="color:red">
                <td align="center">${cont.count}</td>
                <td align="center">
                        ${myf:getPostCodeName(objectList.POST_CODE)}
                </td>
                <td align="center">
                        ${myf:getLotteryChinaName(objectList.LOTTERY_CODE)}
                </td>
                <td align="center">
                 <c:if test="${objectList.LOTTERY_CODE != '200' && objectList.LOTTERY_CODE != '201'}">
                        ${objectList.ISSUE}
                 </c:if>
                 <c:if test="${objectList.LOTTERY_CODE == '200' || objectList.LOTTERY_CODE == '201'}">
                        ${objectList.GAMEID}
                 </c:if>
                </td>
                <td align="center">
                        ${objectList.TTS}
                </td>
                <td align="center">
                        ${objectList.CREATE_TIME}
                </td>
                <td align="center">
                        ${myf:getSyTime(objectList.DUPLEX_TIME)}
                </td>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=noSendticketList&lotteryCode=${objectList.LOTTERY_CODE}&issue=${objectList.ISSUE}&gameId=${objectList.GAMEID}&postCode=${objectList.POST_CODE}&ticketStatus=6&createStartTime=${createStartTime}&createEndTime=${createEndTime}">详细</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <tr id="one">
        <td width="100%" colspan="8">送票未回执信息</td>
    </tr>
    <tr id="one">
        <td width="5%">序号</td>
        <td width="15%">出票口</td>
        <td width="10%">彩种</td>
        <td width="10%">期次</td>
        <td>数量</td>
        <td width="20%">最早发送时间</td>
        <td width="20%">结期</td>
        <td width="5%">详细</td>
    </tr>
    <c:if test="${sendingList!=null}">
        <c:forEach var="object1List" items="${sendingList}" varStatus="cont">
            <c:if test="${myf:getEndTime(object1List.DUPLEX_TIME,object1List.SEND_TIME,object1List.LOTTERY_CODE)}">
                <audio id="music" src="" autoplay="autoplay" loop="true">
                </audio>
                <script>
                    play_click("${pageContext.request.contextPath}/mp3/swh.mp3")
                </script>
            </c:if>
            <tr <c:if
                    test="${myf:getStiTime(object1List.SEND_TIME,object1List.LOTTERY_CODE)}"> style="color:red"</c:if>>
                <td align="center">${cont.count}</td>
                <td align="center">
                        ${myf:getPostCodeName(object1List.POST_CODE)}
                </td>
                <td align="center">
                        ${myf:getLotteryChinaName(object1List.LOTTERY_CODE)}
                </td>
                <td align="center">
                 <c:if test="${object1List.LOTTERY_CODE != '200' && object1List.LOTTERY_CODE != '201'}">
                        ${object1List.ISSUE}
                 </c:if>
                 <c:if test="${object1List.LOTTERY_CODE == '200' || object1List.LOTTERY_CODE == '201'}">
                        ${object1List.GAMEID}
                 </c:if>
                </td>
                <td align="center">
                        ${object1List.TTS}
                </td>
                <td align="center">
                        ${object1List.SEND_TIME}
                </td>
                <td align="center">
                        ${myf:getSyTime(object1List.DUPLEX_TIME)}
                </td>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=noSendticketList&lotteryCode=${object1List.LOTTERY_CODE}&issue=${object1List.ISSUE}&gameId=${object1List.GAMEID}&postCode=${object1List.POST_CODE}&ticketStatus=2&createStartTime=${createStartTime}&createEndTime=${createEndTime}">详细</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <tr id="one">
        <td width="100%" colspan="7">失败票信息</td>
    </tr>
    <tr id="one">
        <td width="10%">序号</td>
        <td width="25%">出票口</td>
        <td width="15%">彩种</td>
        <td width="10%">期次</td>
        <td>数量</td>
        <td width="20%">最早发送时间</td>
        <td width="10%">详细</td>
    </tr>
    <c:if test="${filSendingList!=null}">
        <c:forEach var="object1List" items="${filSendingList}" varStatus="cont">
            <tr>
                <td align="center">${cont.count}</td>
                <td align="center">
                        ${myf:getPostCodeName(object1List.POST_CODE)}
                </td>
                <td align="center">
                        ${myf:getLotteryChinaName(object1List.LOTTERY_CODE)}
                </td>
                <td align="center">
                 <c:if test="${object1List.LOTTERY_CODE != '200' && object1List.LOTTERY_CODE != '201'}">
                        ${object1List.ISSUE}
                 </c:if>
                 <c:if test="${object1List.LOTTERY_CODE == '200' || object1List.LOTTERY_CODE == '201'}">
                        ${object1List.GAMEID}
                 </c:if>
                </td>
                <td align="center">
                        ${object1List.TTS}
                </td>
                <td align="center">
                        ${object1List.SEND_TIME}
                </td>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=noSendticketList&lotteryCode=${object1List.LOTTERY_CODE}&issue=${object1List.ISSUE}&gameId=${object1List.GAMEID}&postCode=${object1List.POST_CODE}&ticketStatus=4&createStartTime=${createStartTime}&createEndTime=${createEndTime}">详细</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <tr id="one">
        <td width="100%" colspan="7">期次未派奖</td>
    </tr>
    <tr id="one">
        <td width="10%">序号</td>
        <td width="25%">彩种</td>
        <td>数量</td>
        <td width="10%">详细</td>
    </tr>
    <c:if test="${noWinIssueList!=null}">
        <c:forEach var="object1List" items="${noWinIssueList}" varStatus="cont">
            <tr>
                <td align="center">${cont.count}</td>
                <td align="center">
                        ${myf:getLotteryChinaName(object1List.LOTTERY_CODE)}
                </td>
                <td align="center">
                        ${object1List.COUNT}
                </td>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery&status=3&bonusStatus=0&lotteryCode=${object1List.LOTTERY_CODE}">详细</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <tr id="one">
        <td width="100%" colspan="7">期次预售期</td>
    </tr>
    <tr id="one">
        <td width="10%">序号</td>
        <td width="25%">彩种</td>
        <td>数量</td>
        <td width="10%">详细</td>
    </tr>
    <c:if test="${saleIssueList!=null}">
        <c:forEach var="object1List" items="${saleIssueList}" varStatus="cont">
            <tr>
                <td align="center">${cont.count}</td>
                <td align="center">
                        ${myf:getLotteryChinaName(object1List.LOTTERY_CODE)}
                </td>
                <td align="center">
                        ${object1List.COUNT}
                </td>
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=zqIssueQuery&status=0&lotteryCode=${object1List.LOTTERY_CODE}">详细</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<br>
</td>
</tr>
</table>
</body>
<script type="text/javascript">
</script>


</html>