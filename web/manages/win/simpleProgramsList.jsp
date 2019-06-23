<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>算奖管理</title>
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
        $(document).ready(function() {
            $("#lotteryCode").change(function() {
                changeIssue($(this).val());
            });
            changeIssue($("#lotteryCode").val());
        })
        function changeIssue(lotteryCode) {
            if (lotteryCode == '200' || lotteryCode == '201') {
                $("#issueNameStart").attr("disabled", true);
                $("#issueNameEnd").attr("disabled", true);
            } else {
                $("#issueNameStart").attr("disabled", false);
                $("#issueNameEnd").attr("disabled", false);
            }
        }
        function onOpen(url) {
            window.open(url);
        }
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<tr>
    <td class="title" style="text-align:left;">
        算奖管理 >> 方案补算奖
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" method="post"
      action="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=simpleProgramsList">
<input type="hidden" name="nonDefault" value="1"/>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
    <tr>
        <td>用户名</td>
        <td><input type="text" name="userName" ${isUnion == 1 ? 'readonly' : ''} value="${userName}" class="input"/>
        </td>

        <td>方案号</td>
        <td>
            <input type="text" name="programsOrderId" value="${programsOrderId}" class="input">

        </td>


        <td>彩种</td>
        <td>
            <select name="lotteryCode" id="lotteryCode">
                <option value=""
                        <c:if test="${'' == lotteryCode }">selected</c:if>>请选择
                </option>
                <c:forEach items="${myf:findAllLotteryCodeFilter300()}" var="lotteryCodeMap">
                    <option value="${lotteryCodeMap.key }"
                            <c:if test="${lotteryCodeMap.key == lotteryCode }">selected</c:if> >
                            ${lotteryCodeMap.value.name }
                    </option>
                </c:forEach>
            </select>
        </td>
        <td>期次范围</td>
        <td>
            <input type="text" name="issueNameStart" id="issueNameStart" value="${issueNameStart}" class="input"
                   style="width:83px"> -
            <input type="text" name="issueNameEnd" id="issueNameEnd" value="${issueNameEnd}" class="input"
                   style="width:83px">
        </td>

    </tr>
    <tr>
        <%--<td>手机</td>--%>
        <%--<td><input type="text" name="mobile" value="${mobile}" class="input"/></td>--%>
        <%--<td>分类</td>--%>
        <%--<td>--%>
            <%--<select name="buyType">--%>
                <%--<option value=""--%>
                        <%--<c:if test="${buyType == ''}">selected</c:if>>全 部--%>
                <%--</option>--%>
                <%--<option value="1"--%>
                        <%--<c:if test="${buyType == '1'}">selected</c:if>>代 购--%>
                <%--</option>--%>
                <%--<option value="2"--%>
                        <%--<c:if test="${buyType == '2'}">selected</c:if>>合 买--%>
                <%--</option>--%>
                <%--<option value="3"--%>
                        <%--<c:if test="${buyType == '3'}">selected</c:if>>套 餐--%>
                <%--</option>--%>
                <%--<option value="4"--%>
                        <%--<c:if test="${buyType == '4'}">selected</c:if>>追 号--%>
                <%--</option>--%>
            <%--</select>--%>
        <%--</td>--%>

        <td>生成时间</td>
        <td>
            <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                   name="startTime" value="${startTime }" readonly/>
            - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                     name="endTime" value="${endTime }" readonly/>
        </td>

        <td>方案状态</td>
        <td><select name="orderStatus">
            <%--<option value=""--%>
                    <%--<c:if test="${'' == orderStatus }">selected</c:if> >全 部--%>
            <%--</option>--%>
            <%--<c:forEach items="${myf:getProgramsStatusMap()}" var="programsStatusMap">--%>
                <%--<c:if test="${programsStatusMap.key != -1 && programsStatusMap.key != 0}">--%>
                    <%--<option value="${programsStatusMap.key}"--%>
                            <%--<c:if--%>
                                    <%--test="${orderStatus == programsStatusMap.key}"> selected="selected" </c:if>--%>
                            <%-->${programsStatusMap.value}</option>--%>
                <%--</c:if>--%>
            <%--</c:forEach>--%>
            <option value="1"
                    selected >投注成功
            </option>
        </select>
        </td>


   
        <%--<td>接入来源</td>--%>
        <%--<td align="left">--%>
            <%--<select name="memberType" <c:if test="${isUnion==1}">disabled="" </c:if>>--%>
                <%--<option value=""--%>
                        <%--<c:if test="${memberType == ''}">selected</c:if>>全部--%>
                <%--</option>--%>
                <%--<option value="0"--%>
                        <%--<c:if test="${memberType == '0'}">selected</c:if>>普通用户--%>
                <%--</option>--%>
                <%--<option value="1"--%>
                        <%--<c:if test="${memberType == '1'}">selected</c:if>>代理用户--%>
                <%--</option>--%>
            <%--</select>--%>
        <%--</td>--%>
        <%--<td>合作来源</td>--%>
        <%--<td>--%>
            <%--<select name="partnersCode" <c:if test="${isUnion==1}">disabled="" </c:if>>--%>
                <%--<option value=""--%>
                        <%--<c:if test="${'' == partnersCode }">selected</c:if>>全部--%>
                <%--</option>--%>
                <%--<c:forEach items="${myf:getUntionMap()}" var="partnersCodeMap">--%>
                    <%--<option value="${partnersCodeMap.key }"--%>
                            <%--<c:if test="${partnersCodeMap.key == partnersCode }">selected</c:if> >--%>
                            <%--${partnersCodeMap.value }--%>
                    <%--</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        <%--</td>--%>
        <td>彩票状态</td>
        <td><select name="sendStatus">
            <%--<option value=""--%>
                    <%--<c:if test="${sendStatus == ''}">selected</c:if>>全部--%>
            <%--</option>--%>
            <%--<option value="0"--%>
                    <%--<c:if test="${sendStatus == 0}">selected</c:if>>未送票--%>
            <%--</option>--%>
            <%--<option value="2"--%>
                    <%--<c:if test="${sendStatus == 2}">selected</c:if>>送票未回执--%>
            <%--</option>--%>
            <%--<option value="3"--%>
                    <%--<c:if test="${sendStatus == 3}">selected</c:if>>出票失败--%>
            <%--</option>--%>
            <%--<option value="1"--%>
                    <%--<c:if test="${sendStatus == 1}">selected</c:if>>已出票--%>
            <%--</option>--%>
            <option value="1"
                    selected>已出票
            </option>
        </select>
        </td>
        <%--<td>中奖状态</td>--%>
        <%--<td>--%>
            <%--<select name="bonusStatus">--%>
                <%--<option value=""--%>
                        <%--<c:if test="${bonusStatus == ''}">selected</c:if>>全部--%>
                <%--</option>--%>
                <%--<option value="0"--%>
                        <%--<c:if test="${bonusStatus == '0'}">selected</c:if>>等待开奖--%>
                <%--</option>--%>
                <%--<option value="10"--%>
                        <%--<c:if test="${bonusStatus == '10'}">selected</c:if>>中奖未派奖--%>
                <%--</option>--%>
                <%--<option value="11"--%>
                        <%--<c:if test="${bonusStatus == '11'}">selected</c:if>>中奖已派奖--%>
                <%--</option>--%>
                <%--<option value="2"--%>
                        <%--<c:if test="${bonusStatus == '2'}">selected</c:if>>未中奖--%>
                <%--</option>--%>
            <%--</select>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--<tr>--%>
        <%--<td>单式上传</td>--%>
        <%--<td align="left">--%>
            <%--<select name="isUpload">--%>
                <%--<option value=""--%>
                        <%--<c:if test="${isUpload == ''}">selected</c:if>>全部--%>
                <%--</option>--%>
                <%--<option value="0"--%>
                        <%--<c:if test="${isUpload == '0'}">selected</c:if>>复式投注--%>
                <%--</option>--%>
                <%--<option value="1"--%>
                        <%--<c:if test="${isUpload == '1'}">selected</c:if>>单式先上传--%>
                <%--</option>--%>
                <%--<option value="2"--%>
                        <%--<c:if test="${isUpload == '2'}">selected</c:if>>单式后上传--%>
                <%--</option>--%>
            <%--</select>--%>
        <%--</td>--%>
        <%--<td>方案金额</td>--%>
        <%--<td>--%>
            <%--<input type="text" name="orderAmountStart" id="orderAmountStart" value="${orderAmountStart}" class="input"--%>
                   <%--style="width:83px">元 ---%>
            <%--<input type="text" name="orderAmountEnd" id="orderAmountEnd" value="${orderAmountEnd}" class="input"--%>
                   <%--style="width:83px">元--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--投注客户端--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<select name="platform">--%>
                <%--<option value=""--%>
                        <%--<c:if test="${'' == platform }">selected</c:if>>全部--%>
                <%--</option>--%>
                <%--<c:forEach items="${myf:getPlatFormMap()}" var="unionMap">--%>
                    <%--<option value="${unionMap.key }"--%>
                            <%--<c:if test="${unionMap.key == platform }">selected</c:if> >--%>
                            <%--${unionMap.value }--%>
                    <%--</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
        <%--</td>--%>
        <td colspan="2" align="right">
            <input type="submit" name="Submit" class="submit" value="查询"
                   style="width: 64px; border: none">
        </td>
    </tr>
</table>
</form>

<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <c:if test="${pageBean.pageContent!=null}">
        <tr>
            <td align="left" colspan="10">
                <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录,
                    累计投注金额<fmt:formatNumber value="${orderAmount }" pattern="0.00"/>元]</span>
            </td>
        </tr>
    </c:if>
    <tr id="one">
        <td width="5%">序号</td>
        <td width="10%">方案编号</td>
        <td width="14%">用户/手机</td>
        <td width="14%">来源</td>
        <td width="5%">分类</td>
        <td width="10%">彩种玩法</td>
        <td width="10%">金额(元)</td>
        <%--<td width="12%">中奖(元)</td>--%>
        <td width="10%">状态</td>
        <td width="10%">生成时间</td>
        <td>操作</td>
    </tr>
    <c:if test="${pageBean.pageContent==null}">
        <tr>
            <td colspan="10" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
        </tr>
    </c:if>
    <c:if test="${pageBean.pageContent!=null}">
        <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
            <tr>
                <td align="center">${(page-1)*requestScope.pageSize + cont.count}</td>
                <td>
                    <c:if test="${objectList[0].buyType==1 || objectList[0].buyType==3 || objectList[0].buyType==4}">
                        <a href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=queryOrderDetail&programsOrderId=${objectList[0].programsOrderId}"
                           target="_blank">${objectList[0].programsOrderId}</a>
                    </c:if>
                    <c:if test="${objectList[0].buyType==2}">
                        <a href="${pageContext.request.contextPath}/manages/orderManagesServlet?action=queryOrderHmDetail&programsOrderId=${objectList[0].programsOrderId}"
                           target="_blank">${objectList[0].programsOrderId}</a>
                    </c:if>
                </td>
                <td>
                    用户:
                    <c:if test="${objectList[1].memberType == 1}">
                        ${objectList[1].userName}
                    </c:if>
                    <c:if test="${objectList[1].memberType != 1}">
                        <a
                                href="${pageContext.request.contextPath}/manages/memberMessageServlet?action=getMemberByUserId&getMemberType=xq&userId=${objectList[1].id}"
                                target="_blank">${objectList[1].userName}</a>
                    </c:if>
                    <br/>
                    手机:${objectList[1].mobile}
                </td>
                <td align="left">
                    合作:${objectList[1].memberType == 1 ? "--" : myf:getUntionByUnionId(objectList[1].unionId)}
                    <br/>接入:${objectList[1].memberType == 1 ? "代理用户" : "普通用户"}
                    <br/>投注客户端:${myf:getRegisterApp(objectList[0].platform)}
                </td>
                <td align="center">
                        ${myf:getBuyType(objectList[0].buyType)}
                    <br/>
                        ${objectList[0].isUpload==0?"普通":"上传"}
                </td>
                <td align="left">
                    彩种:${myf:getLotteryChinaNameFilter300(objectList[0].lotteryCode,objectList[0].playCode)}<br/>
                    <c:if test="${objectList[0].playCode!=null}">
                        玩法:${myf:getPlayChinaName(objectList[0].lotteryCode,objectList[0].playCode)}
                    </c:if>
                    <c:if test="${objectList[0].playCode==null}">
                        玩法:--
                    </c:if><br/>
                    期次:${objectList[0].issue}
                </td>
                <td>
                    注数:${objectList[0].item}<br/>
                    倍数:${objectList[0].multiple}<br/>
                    金额:<fmt:formatNumber value="${objectList[0].orderAmount}" pattern="0.00"/>
                </td>
                <%--<td>--%>
                    <%--税前:<fmt:formatNumber value="${objectList[0].fixBonusAmount}" pattern="0.00"/><br/>--%>
                    <%--税后:<fmt:formatNumber value="${objectList[0].bonusAmount}" pattern="0.00"/>--%>
                    <%--<c:if test="${objectList[0].buyType == '2'}">--%>
                        <%--<br/>佣金:${myf:commissionFormat(objectList[0].bonusAmount, objectList[0].orderAmount, objectList[0].commission)}--%>
                    <%--</c:if>--%>
                <%--</td>--%>
                <td align="left">
                    方案:${myf:orderStatusName(objectList[0].orderStatus)}<br/>
                    出票:<c:choose>
                    <c:when test="${objectList[0].sendStatus==1 && objectList[0].orderStatus == 1}">已出票</c:when>
                    <c:when test="${objectList[0].sendStatus==1 && (objectList[0].orderStatus == -1 || objectList[0].orderStatus == 0)}">送票未回执</c:when>
                    <c:when test="${objectList[0].orderStatus == 3}">出票失败</c:when>
                    <c:otherwise>未送票</c:otherwise>
                </c:choose>
                    <br/>
                    中奖:
                    <c:if test="${(objectList[0].orderStatus < 1 && objectList[2].status != 3) || objectList[0].orderStatus == 1}">${myf:getBonusStatusToAccount(objectList[0].bonusStatus,objectList[0].bonusToAccount)}</c:if>
                    <c:if test="${(objectList[0].orderStatus == -1 || objectList[0].orderStatus == 0) && objectList[2].status == 3}">--</c:if>
                    <c:if test="${objectList[0].orderStatus == 3 || objectList[0].orderStatus == 4 || objectList[0].orderStatus == 5 || objectList[0].orderStatus == 6}">--</c:if>
                </td>
                <td align="left">
                    <fmt:formatDate value="${objectList[0].createTime}" pattern="yyyy-MM-dd"/><br/>
                    <fmt:formatDate value="${objectList[0].createTime}" pattern="HH:mm:ss"/>
                </td>
                <td align="center">
                    <c:if test="${objectList[0].orderStatus == 1}">
                        <a href="javascript:if (ifLink('确定要进行该方案算奖？')) {window.location.href='/manages/calculateAwardServlet?action=simpleProgramsDoCalc&programsOrderId=${objectList[0].programsOrderId}'}">算奖</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="10" align="right">
                <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                              total="${pageBean.itemTotal}"
                              gotoURI="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=simpleProgramsList"/>
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