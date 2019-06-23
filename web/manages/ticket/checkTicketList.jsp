<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<c:set var="exportMaxPage" value="${myf:getExportMaxPage()}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>对账差异彩票列表</title>
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
            var maxPage = ${exportMaxPage};
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
            if (parseInt(endPage) - parseInt(startPage) >= parseInt(maxPage)) {
                alert("一次最多只能导出10000条");
                return;
            }
            $("#startPage").val(startPage);
            $("#endPage").val(endPage);
            var action = $("#form1").attr("action");
            $("#form1").attr("action", "${pageContext.request.contextPath}/manages/outData?action=ticketList");
            $("#form1").submit();
            $("#form1").attr("action", action);
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
        自动对账管理 >> 差异彩票查询
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" method="post" id="form1"
      action="${pageContext.request.contextPath}/manages/checkTicketServlet?action=checkTicketList">
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
    <td>彩票状态</td>
    <td>
        <select name="ticketStatus">
          	<option value=""
                    <c:if test="${'' == ticketStatus }">selected</c:if>>全部
            </option>
            <option value="3"
                    <c:if test="${3 == ticketStatus }">selected</c:if>>出票成功
            </option>
            <option value="4"
                    <c:if test="${4 == ticketStatus }">selected</c:if>>出票失败
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
            <option value="00"
                    <c:if test="${postCode == '00'}">selected</c:if>>待分配
            </option>
        </select>
    </td>
</tr>
<tr>
    <td>彩票编号</td>
    <td><input type="text" name="ticketId" value="${ticketId}" class="input"/></td>
    <td>商户票号</td>
    <td><input type="text" name="outTicketId" value="${outTicketId}" class="input"/></td>
    <td>批次号</td>
    <td><input type="text" name="orderId" value="${orderId}" class="input"/></td>
</tr>
<tr>

    <td>接收时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="createStartTime" value="${createStartTime }" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="createEndTime" value="${createEndTime }" readonly/>
    </td>
    <td>送票时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="sendStartTime" value="${sendStartTime}" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="sendEndTime" value="${sendEndTime}" readonly/>
    </td>
    <td>算奖时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="bonusStartTime" value="${bonusStartTime}" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="bonusEndTime" value="${bonusEndTime}" readonly/>
    </td>
    <td>回执时间</td>
    <td>
        <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="returnStartTime" value="${returnStartTime }" readonly/>
        - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                 name="returnEndTime" value="${returnEndTime }" readonly/>
    </td>
</tr>
<tr>
    <td>出票状态</td>
    <td>
        <select name="ticketDiffStatus">
            <option value=""
                    <c:if test="${'' == ticketDiffStatus }">selected</c:if>>全部
            </option>
            <option value="1"
                    <c:if test="${'1' == ticketDiffStatus }">selected</c:if>>有差异
            </option>
            <option value="2"
                    <c:if test="${'2' == ticketDiffStatus }">selected</c:if>>无差异
            </option>
        </select>
    </td>
    <td>投注金额</td>
    <td align="left">
        <select name="amountDiffStatus">
            <option value="0"
                    <c:if test="${amountDiffStatus == 0}">selected</c:if>>全部
            </option>
            <option value="1"
                    <c:if test="${amountDiffStatus == 1}">selected</c:if>>无差异
            </option>
            <option value="2"
                    <c:if test="${amountDiffStatus == 2}">selected</c:if>>有差异
            </option>
        </select>
    </td>
    <td>税前奖金</td>
    <td align="left">
        <select name="fixBonusDiffStatus">
            <option value="0"
                    <c:if test="${fixBonusDiffStatus == 0}">selected</c:if>>全部
            </option>
            <option value="1"
                    <c:if test="${fixBonusDiffStatus == 1}">selected</c:if>>无差异
            </option>
            <option value="2"
                    <c:if test="${fixBonusDiffStatus == 2}">selected</c:if>>有差异
            </option>
        </select>
    </td>
    <td>税后奖金</td>
    <td align="left">
         <select name="bonusDiffStatus">
            <option value="0"
                    <c:if test="${bonusDiffStatus == 0}">selected</c:if>>全部
            </option>
            <option value="1"
                    <c:if test="${bonusDiffStatus == 1}">selected</c:if>>无差异
            </option>
            <option value="2"
                    <c:if test="${bonusDiffStatus == 2}">selected</c:if>>有差异
            </option>
        </select>
    </td>
</tr>
<tr>
    <td>期次</td>
    <td>
        <input type="text" name="issue" value="${issue}" class="input"/>
    </td>
    <td>中奖状态</td>
    <td>
        <select name="bonusStatus">
            <option value=""
                    <c:if test="${'' == bonusStatus }">selected</c:if>>全部
            </option>
            <option value="1"
                    <c:if test="${'1' == bonusStatus }">selected</c:if>>中奖
            </option>
            <option value="2"
                    <c:if test="${'2' == bonusStatus }">selected</c:if>>未中奖
            </option>
        </select>
    </td>
    <td>大小奖</td>
    <td align="left">
        <select name="bigBonus">
            <option value=""
                    <c:if test="${bigBonus == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${bigBonus == 0}">selected</c:if>>小奖
            </option>
            <option value="1"
                    <c:if test="${bigBonus == 1}">selected</c:if>>大奖
            </option>
        </select>
    </td>
    <td align="center" colspan="4">
        <input type="submit" name="Submit" class="submit" value="查询" style="width: 64px; border: none">
    </td>
</tr>
</table>
<input type="hidden" name="type" value="get"/>
</form>

<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <c:if test="${pageBean.pageContent!=null}">
        <tr>
            <td align="left" colspan="11">
                <div style="color:#0033cc;font-weight: bold; float: left;">[本次查询结果共${pageBean.itemTotal }条记录;
                    累计彩票数量${requestScope.ticketNumber}张,
                    投注金额差异<fmt:formatNumber value="${requestScope.ticketAmount}" pattern="0.00"/>元,
                    税前中奖金额差异<fmt:formatNumber value="${requestScope.fixBonusAmount}" pattern="0.00"/>元,
                    税后中奖金额差异<fmt:formatNumber value="${requestScope.bonusAmount}" pattern="0.00"/>元
                    ]
                </div>
                <div style=" float:right;">
                    <input type="text" size="2" id="inputStart" value="1"
                           onkeyup="this.value=this.value.replace(/\D/g,'')"
                           onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                    页 - <input type="text" size="2" id="inputEnd" value="1"
                               onkeyup="this.value=this.value.replace(/\D/g,'')"
                               onafterpaste="this.value=this.value.replace(/\D/g,'')"/> 页
                   <!--  <a href="javascript:void(0)" onclick="exportExcel();" style="font-weight: bold;">导出</a> -->
                </div>

            </td>
        </tr>
    </c:if>
    <tr id="one">
        <td width="3%">序号</td>
        <td width="16%">彩票编号</td>
        <td width="10%">商户信息</td>
        <td width="8%">投注信息</td>
        <td width="12%">投注金额</td>
        <td width="12%">税前奖金</td>
        <td width="12%">税后奖金</td>
        <td width="12%">出票状态</td>
        <td width="10%">时间</td>
        <td width="5%">出票口</td>
    </tr>
    <c:if test="${pageBean.pageContent==null}">
        <tr>
            <td colspan="11" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
        </tr>
    </c:if>
    <c:if test="${pageBean.pageContent!=null}">
        <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
            <tr>
                <td align="center">${(page-1)*requestScope.pageSize + cont.count}</td>
                <td align="left">
                    批次号:${objectList.orderId}
                    <br/>
                    票号:<a
                        href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=ticketDetail&ticketId=${objectList.ticketId}"
                        target="_blank">${objectList.ticketId}</a>
                    <br/>
                    商户票号:${objectList.outTicketId}
                    <br/>
                    出票票号:${objectList.backup1}
                </td>
                <td>
                    商户 ID:${objectList.sid}
                    <br/>
                    商户名称:${objectList.name}
                </td>
                <td>
                    彩种:${myf:getLotteryChinaName(objectList.lotteryCode)}
                    <br/>
                    玩法:${myf:getPlayChinaName(objectList.lotteryCode,objectList.playCode)}
                    <br/>
                    期次:${objectList.issue}
       	 注数:${objectList.item}
                    <br/>
                    倍数:${objectList.multiple}
                </td>
                <td>
                    中彩汇：<fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>
              <br/>
        ${myf:getPostCodeName(objectList.postCode)}:<fmt:formatNumber value="${objectList.upAmount}" pattern="0.00"/>
       		  <br/>
                     差异金额:   <c:if test="${objectList.amountDiff!=0}"><span style="color:red"><fmt:formatNumber value="${objectList.amountDiff}" pattern="0.00"/></span></c:if> 
                   <c:if test="${objectList.amountDiff==0}">无差异</c:if>                   
                    
                </td>
                <td>
                    中彩汇：<fmt:formatNumber value="${objectList.fixBonusAmount}" pattern="0.00"/>
              <br/>
        ${myf:getPostCodeName(objectList.postCode)}:<fmt:formatNumber value="${objectList.upFixBonusAmount}" pattern="0.00"/>
       		  <br/>
                     差异金额:   <c:if test="${objectList.fixBonusAmountDiff!=0}"><span style="color:red"><fmt:formatNumber value="${objectList.fixBonusAmountDiff}" pattern="0.00"/></span></c:if> 
                   <c:if test="${objectList.fixBonusAmountDiff==0}">无差异</c:if>    
                </td>
                
                <td>
                    中彩汇：<fmt:formatNumber value="${objectList.bonusAmount}" pattern="0.00"/>
              <br/>
        ${myf:getPostCodeName(objectList.postCode)}:<fmt:formatNumber value="${objectList.upBonusAmount}" pattern="0.00"/>
       		  <br/>
                     差异金额:   <c:if test="${objectList.bonusAmountDiff!=0}"><span style="color:red"><fmt:formatNumber value="${objectList.bonusAmountDiff}" pattern="0.00"/></span></c:if> 
                   <c:if test="${objectList.bonusAmountDiff==0}">无差异</c:if>  
                </td>
	             <td align="left">
	                中彩汇： <c:if test="${objectList.ticketStatus==3}">出票成功</c:if>  
	              <c:if test="${objectList.ticketStatus==4}">出票失败</c:if>  
              <br/>
        ${myf:getPostCodeName(objectList.postCode)}:${objectList.upTicketStatusDesc}
       		  <br/>
             是否一致:   <c:if test="${objectList.ticketStatusDiff==1}"><span style="color:red">否</span></c:if> 
                   <c:if test="${objectList.ticketStatusDiff==0}">是</c:if>  
	            </td>
                <td align="left">
                    生成:<fmt:formatDate value="${objectList.createTime}" pattern="yy-MM-dd HH:mm:ss"/>
                    <br/>
                    发送:<fmt:formatDate value="${objectList.sendTime}" pattern="yy-MM-dd HH:mm:ss"/>
                    <br/>
                    回执:<fmt:formatDate value="${objectList.returnTime}" pattern="yy-MM-dd HH:mm:ss"/>
                    <br/>
                    算奖:<fmt:formatDate value="${objectList.bonusTime}" pattern="yy-MM-dd HH:mm:ss"/>
                </td>
                <td align="center">
                        ${myf:getPostCodeName(objectList.postCode)}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="11" align="right">
                <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                              total="${pageBean.itemTotal}"
                              gotoURI="${pageContext.request.contextPath}/manages/checkTicketServlet?action=checkTicketList"/>
            </td>
        </tr>
    </c:if>
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