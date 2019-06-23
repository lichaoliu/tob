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
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/zch.js"></script>
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
        彩票管理 >> 彩票查询
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" method="post" id="form1"
      action="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=ticketList">
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
    <td>打印票号</td>
    <td><input type="text" name="saleCode" value="${saleCode}" class="input"/></td>
</tr>
<tr>

    <td>接收时间&nbsp;<span style="color: red">*</span></td>
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
    <td>中奖状态</td>
    <td>
        <select name="bonusStatus">
            <option value=""
                    <c:if test="${'' == bonusStatus }">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${'0' == bonusStatus }">selected</c:if>>未开奖
            </option>
            <option value="1"
                    <c:if test="${'1' == bonusStatus }">selected</c:if>>中奖
            </option>
            <option value="2"
                    <c:if test="${'2' == bonusStatus }">selected</c:if>>未中奖
            </option>
        </select>
    </td>
    <td>返奖状态</td>
    <td align="left">
        <select name="issueBonusStatus">
            <option value=""
                    <c:if test="${issueBonusStatus == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${issueBonusStatus == 0}">selected</c:if>>未返奖
            </option>
            <option value="1"
                    <c:if test="${issueBonusStatus == 1}">selected</c:if>>已返奖
            </option>
        </select>
    </td>
    <td>算奖状态</td>
    <td align="left">
        <select name="operatorsAward">
            <option value=""
                    <c:if test="${operatorsAward == ''}">selected</c:if>>全部
            </option>
            <option value="0"
                    <c:if test="${operatorsAward == 0}">selected</c:if>>等待算奖
            </option>
            <option value="1"
                    <c:if test="${operatorsAward == 1}">selected</c:if>>算奖中
            </option>
            <option value="2"
                    <c:if test="${operatorsAward == 2}">selected</c:if>>算奖完成
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
</tr>
<tr>
    <td>期次</td>
    <td>
        <input type="text" name="issue" value="${issue}" class="input"/>
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
    <td>竞彩场次</td>
    <td>
       <input type="text" name="gameId" id="gameId" value="${gameId}" class="input"/>
    </td>
    <td align="center" colspan="4">
        <input type="submit" name="Submit" class="submit" onclick="return ifLink()" value="查询" style="width: 64px; border: none">
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
                    投注金额<fmt:formatNumber value="${requestScope.ticketAmount}" pattern="0.00"/>元,
                    税前中奖金额<fmt:formatNumber value="${requestScope.fixBonusAmount}" pattern="0.00"/>元,
                    税后中奖金额<fmt:formatNumber value="${requestScope.bonusAmount}" pattern="0.00"/>元
                    ]
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

            </td>
        </tr>
    </c:if>
    <tr id="one">
        <td width="3%">序号</td>
        <td width="18%">彩票编号</td>
        <td width="12%">商户信息</td>
        <td width="8%">彩种</td>
        <td width="6%">注数倍数</td>
        <td>金额(元)</td>
        <td width="6%">状态</td>
        <td width="12%">奖金(元)</td>
        <td width="14%">时间</td>
        <td width="5%">出票口</td>
        <td width="5%">子票</td>
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
                </td>
                <td align="left">
                    注数:${objectList.item}
                    <br/>
                    倍数:${objectList.multiple}
                </td>
                <td align="right">
                    <fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>
                </td>
                <td align="center">
                    <c:if test="${objectList.ticketStatus==0}">
                        未送票
                    </c:if>
                    <c:if test="${objectList.ticketStatus==1}">
                        调度中
                    </c:if>
                    <c:if test="${objectList.ticketStatus==2}">
                        送票未回执
                    </c:if>
                    <c:if test="${objectList.ticketStatus==3}">
                        出票成功<br>
                        <c:if test="${objectList.bonusStatus==0 }">
                            未开奖
                        </c:if>
                        <c:if test="${objectList.bonusStatus==1}">
                            中奖
                        </c:if>
                        <c:if test="${objectList.bonusStatus==2}">
                            未中奖
                        </c:if>
                    </c:if>
                    <c:if test="${objectList.ticketStatus==4 || objectList.ticketStatus==5}">
                        出票失败<br>
                        <font color="blue">${objectList.errCode}|${objectList.errMsg}</font>
                    </c:if>
                    <c:if test="${objectList.ticketStatus==6}">
                        重发<br>
                        <font color="blue">${objectList.errCode}|${objectList.errMsg}</font>
                    </c:if>
                </td>
                <td align="left">
                    <c:if test="${objectList.ticketStatus==3}">
                        税前:<fmt:formatNumber
                            value="${objectList.fixBonusAmount == null ? 0.00 : objectList.fixBonusAmount}"
                            pattern="0.00"/>
                        <br/>
                        税后:<fmt:formatNumber
                            value="${objectList.bonusAmount == null ? 0.00 : objectList.bonusAmount}"
                            pattern="0.00"/>
                    </c:if>
                    <c:if test="${objectList.ticketStatus!=3}">
                        税前:--
                        <br/>
                        税后:--
                    </c:if>
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
                <td align="center">
                    <a href="${pageContext.request.contextPath}/manages/subTicketManagesServlet?action=subTicketList&ticketId=${objectList.ticketId}"
                       target="_blank">查看</a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="11" align="right">
                <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                              total="${pageBean.itemTotal}"
                              gotoURI="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=ticketList"/>
            </td>
        </tr>
    </c:if>
</table>
</td>
</tr>
</table>
</body>
</html>