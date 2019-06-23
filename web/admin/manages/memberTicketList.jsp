<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<c:set var="exportMaxPage" value="${myf:getExportMaxPage()}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>出票商与彩票</title>
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
        function onOpen(url) {
            window.open(url);
        }
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<tr>
    <td class="title" style="text-align:left;">
        彩票管理 >> 接入商与彩种
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" method="post" id="form1"
      action="${pageContext.request.contextPath}/manages/postTicketServlet?action=memberTicketList">
<input type="hidden" name="startPage" id="startPage"/>
<input type="hidden" name="endPage" id="endPage"/>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
<tr>

    <td>接入商名称</td>
    <td>
        <select name="sid" id="sid">
            <option value="">全部</option>
            <c:forEach items="${myf:getMemberList()}" var="member">
                <option value="${member.sid}" <c:if test="${member.sid == sid}">selected</c:if>>${member.name}</option>
            </c:forEach>
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
    

    <td>接收时间&nbsp;<span style="color: red">*</span></td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="createStartTime" value="${createStartTime }" readonly/>
               --
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="createEndTime" value="${createEndTime }" readonly/>
    
    </td>
    <td align="center">
        <input type="submit" name="Submit" class="submit" onclick="return ifLink()" value="查询" style="width: 64px; border: none">
    </td>
</tr>
<tr>
    
</tr>
</table>
<input type="hidden" name="type" value="get"/>
</form>

<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <c:if test="${pageBean.pageContent != null}">
        <tr>
            <td colspan="5" align="right">
                <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal }条记录;
                    投注金额<fmt:formatNumber value="${requestScope.sumAmount}" pattern="0.00"/>元]
                </div>
            </td>
        </tr>
    </c:if>
    <tr id="one">
        <td width="5%">序号</td>
        <td width="20%">接入商ID</td>
        <td width="20%">接入商名称</td>        
        <td width="25%">金额(元)</td>
        <td width="30%">彩种</td>
        
    </tr>
    <c:if test="${pageBean.pageContent==null}">
        <tr>
            <td colspan="5" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
        </tr>
    </c:if>
    <c:if test="${pageBean.pageContent!=null}">
        <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
            <tr>
                <td align="center">${(page-1)*requestScope.pageSize + cont.count}</td>
                <td align="center">${objectList.sid}</td>
                <td align="center">${objectList.name}</td>
                
                <td align="center">
                    <fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>
                </td>
                <td align="center">
                   ${myf:getLotteryChinaName(objectList.lotteryCode)}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="5" align="right">
                <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                              total="${pageBean.itemTotal}"
                              gotoURI="${pageContext.request.contextPath}/manages/postTicketServlet?action=memberTicketList"/>
            </td>
        </tr>
    </c:if>
</table>
</td>
</tr>
</table>
</body>
</html>