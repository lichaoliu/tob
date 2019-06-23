<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
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
    <script type="text/javascript">
        function onOpen(url) {
            window.open(url);
        }
        function checkAll(itemTotal) {
            if (document.getElementsByName("myCheckAll")[0].checked) {
                for (i = 1; i < itemTotal + 1; i++) {
                    document.getElementById("check" + i).checked = true;
                }
            } else {
                for (i = 1; i < itemTotal + 1; i++) {
                    document.getElementById("check" + i).checked = false;
                }
            }
        }

        function onFailed() {
            var j = 0;
            var myc = document.getElementsByName("myCheckbox");
            for (i = 0; i < myc.length; i++) {
                if (myc[i].checked) {
                    j++;
                }
            }
            if (j <= 0) {
                alert("请选中要退款的票");
                return false;
            }

            if (confirm("确定退款？")) {
                document.form2.action = "${pageContext.request.contextPath}/manages/ticketManagesServlet?action=successCancelTicketList";
                form2.submit();
            }
        }
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
<tr>
    <td class="title" style="text-align:left;">
        彩票管理 >> 异常出票查询
    </td>
</tr>
<tr>
<td style="padding: 4px;">
<form name="form1" method="post"
      action="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=errorTicketList">
    <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
        <tr>
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
            <td>彩 种</td>
            <td>
                <select name="lotteryCode" id="lotteryCode">
                    <option value="" <c:if test="${'' == lotteryCode }">selected</c:if>>全部
                    </option>
                    <c:forEach items="${myf:findAllLotteryCode()}" var="lotteryCodeMap">
                        <option value="${lotteryCodeMap.key }"
                                <c:if test="${lotteryCodeMap.key == lotteryCode }">selected</c:if> >
                                ${lotteryCodeMap.value.name }
                        </option>
                    </c:forEach>
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
            <td>期次状态</td>
            <td>
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
        </tr>
        <tr>
            <td>彩票编号</td>
            <td><input type="text" name="ticketId" value="${ticketId}" class="input"/></td>
            <td>商户票号</td>
            <td><input type="text" name="outTicketId" value="${outTicketId}" class="input"/></td>
            <td>批次号</td>
            <td><input type="text" name="orderId" value="${orderId}" class="input"/></td>
            <td>期次</td>
            <td>
                <input type="text" name="issue" value="${issue}" class="input"/>
            </td>
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
            <td align="right" colspan="4">
                <input type="submit" name="Submit" class="submit" value="查询" style="width: 64px; border: none">
            </td>
        </tr>
    </table>
    <input type="hidden" name="type" value="get"/>
</form>
<table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
    <form name="form2" method="post"
          action="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=failedTicketList">
        <c:if test="${pageBean.pageContent!=null}">
            <tr>
                <td align="left" colspan="11">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录;
                                累计彩票数量${requestScope.ticketNumber}张,
                                投注金额<fmt:formatNumber value="${requestScope.ticketAmount}" pattern="0.00"/>元
                ]</span>
                </td>
            </tr>
            <tr>
                <td align="left" colspan="11">
                    <span style="color:#0033cc;font-weight: bold;">
                        <input type="button" class="submit" value="批量退款" style="width: 64px;"
                               onclick="return onFailed();"/>
                     </span>
                </td>
            </tr>
        </c:if>
        <tr id="one">
            <td width="5%"><input name="myCheckAll" type="checkbox"
                                  onclick="checkAll(${fn:length(pageBean.pageContent)})"></td>
            <td width="5%">序号</td>
            <td width="18%">彩票编号</td>
            <td width="12%">商户信息</td>
            <td width="10%">彩种</td>
            <td width="8%">注数倍数</td>
            <td>金额(元)</td>
            <td width="6%">状态</td>
            <td width="10%">奖金(元)</td>
            <td width="16%">时间</td>
            <td width="5%">出票口</td>
        </tr>
        <c:if test="${pageBean.pageContent==null}">
            <tr>
                <td colspan="10" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
            </tr>
        </c:if>
        <c:if test="${pageBean.pageContent!=null}">
        <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
            <tr>
                <c:set value="${(page-1)*requestScope.pageSize + cont.count}" var="num"/>
                <td align="center">
                    <input name="myCheckbox" type="checkbox" id="check${cont.count}"
                           value="${objectList.ticketId}">
                </td>
                <td align="center">${num}</td>
                <td align="left">
                    批次号:${objectList.orderId}
                    <br/>
                    票号:<a
                        href="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=errorTicketDetail&ticketId=${objectList.ticketId}"
                        target="_blank">${objectList.ticketId}</a>
                    <br/>
                    商户票号:${objectList.outTicketId}
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
            </tr>
        </c:forEach>
    </form>
    <tr>
        <td colspan="11" align="right">
            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                          total="${pageBean.itemTotal}"
                          gotoURI="${pageContext.request.contextPath}/manages/ticketManagesServlet?action=noSendticketList"/>
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