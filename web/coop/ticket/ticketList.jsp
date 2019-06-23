<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>投注列表</title>
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
    </script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            业务管理 >> 投注查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <form name="form1" id="form1" method="post"
                  action="${pageContext.request.contextPath}/coop/cooperationServlet?action=ticketList">
                <input type="hidden" name="startPage" id="startPage"/>
                <input type="hidden" name="endPage" id="endPage"/>
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
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
                                <option value="2"
                                        <c:if test="${2 == ticketStatus }">selected</c:if>>送票未回执
                                </option>
                                <option value="3"
                                        <c:if test="${3 == ticketStatus }">selected</c:if>>出票成功
                                </option>
                                <option value="4"
                                        <c:if test="${4 == ticketStatus }">selected</c:if>>出票失败
                                </option>
                            </select>
                        </td>
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
                    </tr>
                    <tr>
                        <td>彩票编号</td>
                        <td><input type="text" name="ticketId" value="${ticketId}" class="input"/></td>
                        <td>批 次 号</td>
                        <td><input type="text" name="orderId" value="${orderId}" class="input"/></td>
                        <td>期次</td>
                        <td>
                            <input type="text" name="issue" value="${issue}" class="input"/>
                        </td>
                    </tr>
                    <tr>
                        <td>创建时间</td>
                        <td>
                            <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                   name="createStartTime" value="${createStartTime }" readonly/>
                            - <input style="width: 83px;" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                     name="createEndTime" value="${createEndTime }" readonly/>
                        </td>
                        <td>商户票号</td>
                        <td><input type="text" name="outTicketId" value="${outTicketId}" class="input"/></td>
                        <td align="right" colspan="2">
                            <input type="submit" name="Submit" class="submit" value="查询"
                                   style="width: 64px; border: none">
                        </td>
                    </tr>
                </table>
            </form>

        <table width="100%" border="1" cellpadding="4" cellspacing="0" class="content">
            <c:if test="${pageBean.pageContent!=null}">
                <tr>
                    <td align="left" colspan="9">
                    <div style=" float:left;">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录;
                                累计彩票数量${requestScope.ticketNumber}张,
                                投注金额<fmt:formatNumber value="${requestScope.ticketAmount}" pattern="0.00"/>元,
                                税前中奖金额<fmt:formatNumber value="${requestScope.fixBonusAmount}" pattern="0.00"/>元,
                                税后中奖金额<fmt:formatNumber value="${requestScope.bonusAmount}" pattern="0.00"/>元
                                ]</span>
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
                <td width="5%">序号</td>
                <td width="28%">彩票编号</td>
                <td width="12%">彩种</td>
                <td width="8%">注数倍数</td>
                <td>金额(元)</td>
                <td width="12%">奖金(元)</td>
                <td width="6%">状态</td>
                <td width="18%">时间</td>
            </tr>
            <c:if test="${pageBean.pageContent==null}">
                <tr>
                    <td colspan="9" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                </tr>
            </c:if>
            <c:if test="${pageBean.pageContent!=null}">
                <c:forEach var="objectList" items="${pageBean.pageContent}" varStatus="cont">
                    <tr>
                        <td align="center">${(page-1)*requestScope.pageSize + cont.count}</td>
                        <td align="left">
                            批 次 号:${objectList.orderId}
                            <br/>
                            彩票编号:<a
                                href="${pageContext.request.contextPath}/coop/cooperationServlet?action=ticketDetail&ticketId=${objectList.ticketId}"
                                target="_blank">${objectList.ticketId}</a>
                            <br/>
                            商户票号:${objectList.outTicketId}
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
                        <td align="left">
                            <fmt:formatNumber value="${objectList.amount}" pattern="0.00"/>
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
                        <td align="center">
                            <c:if test="${objectList.ticketStatus==0 || objectList.ticketStatus==1}">
                                未送票
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
                            <c:if test="${objectList.ticketStatus==4}">
                                出票失败
                            </c:if>
                        </td>
                        <td align="left">
                            创建:<fmt:formatDate value="${objectList.createTime}" pattern="yy-MM-dd HH:mm:ss"/>
                            <br/>
                            回执:<fmt:formatDate value="${objectList.returnTime}" pattern="yy-MM-dd HH:mm:ss"/>
                            <br/>
                            算奖:<fmt:formatDate value="${objectList.bonusTime}" pattern="yy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="9" align="right">
                        <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                      total="${pageBean.itemTotal}"
                                      gotoURI="${pageContext.request.contextPath}/coop/cooperationServlet?action=ticketList"/>
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
            $("#form1").attr("action", "${pageContext.request.contextPath}/coop/outData?action=ticketListOutData");
            $("#form1").submit();
            $("#form1").attr("action", action);
        }
</script>

<script type="text/javascript">
</script>


</html>