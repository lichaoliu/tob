<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>竞彩篮球算奖</title>
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
        function toEditOpenBasketball(id){
                    var matchNo= $("#matchNo").val();
                    var mainTeam= $("#mainTeam").val();
                    var guestTeam= $("#guestTeam").val();
                    var matchName= $("#matchName").val();
                    var operatorsAward= $("#operatorsAward").val();
                    var page= $("#pageId").val();
                    window.location.href='${pageContext.request.contextPath}/manages/calculateAwardServlet?action=basketballDetail&id='+id+'&matchNo='+encodeURIComponent(encodeURIComponent(matchNo))+"&mainTeam="+encodeURIComponent(encodeURIComponent(mainTeam))+"&guestTeam="+encodeURIComponent(encodeURIComponent(guestTeam))+"&matchName="+encodeURIComponent(encodeURIComponent(matchName))+"&operatorsAward="+encodeURIComponent(encodeURIComponent(operatorsAward))+"&page="+page;
                }
    </script>
</head>
<body>
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            算奖管理 >> 竞彩篮球算奖
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <input type="hidden" value="${requestScope.page}" id="pageId"/>
            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=basketballMatchList">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td style="width:5%">赛事编号</td>
                        <td style="width:10%">
                           <input type="text" name="matchNo"  value="${matchNo}" id="matchNo" class="input">
                        </td>

                        <td style="width:5%">主队</td>
                        <td style="width:10%">
                            <input type="text" name="mainTeam" value="${mainTeam}" id="mainTeam" class="input">
                        </td>
                        <td style="width:5%">客队</td>
                        <td style="width:10%">
                            <input type="text" name="guestTeam" value="${guestTeam}" id="guestTeam" class="input">
                        </td>
                        <td style="width:5%">联赛</td>
                        <td style="width:10%">
                            <input type="text" name="matchName" value="${matchName}" id="matchName" class="input">
                        </td>
                        
                    </tr>
                    <tr>
                        <td style="width:5%">算奖状态</td>
                        <td style="width:10%">
                            <select name="operatorsAward" id="operatorsAward">
                                <option value="" <c:if test="${'' == operatorsAward }">selected</c:if>>全部</option>
                                <c:forEach var="award" items="${myf:getOperatorsAwardMap()}">
                                    <option value="${award.key}" <c:if test="${award.key == operatorsAward}">selected</c:if> >${award.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td style="width:5%">赛事日期</td>
                        <td style="width:15%">
                            <input style="width: 83px;" type="text" name="startTime" value="${startTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:30%" readonly="readonly">-
                            <input style="width: 83px;" type="text" name="endTime"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                   value="${endTime}" style="width:30%" readonly="readonly">
                        </td>
                        <td colspan="4" align="right">
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
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="5%">序号</td>
                    <td width="8%">赛事日期</td>
                    <td width="8%">赛事编号</td>
                    <td width="8%">联赛</td>
                    <td width="8%">主队</td>
                    <td width="8%">客队</td>
                    <td width="12%">止售时间</td>
                    <td width="10%">比分</td>
                    <td width="17%">赛果</td>
                    <td width="7%">算奖状态</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="10" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="basketball" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                                ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td align="center">
                                ${basketball.subIssueForJingCaiLanQiu.issue}
                            </td>
                            <td align="center">
                                ${basketball.subIssueForJingCaiLanQiu.week}${basketball.subIssueForJingCaiLanQiu.sn}
                            </td>
                            <td align="center">
                                ${basketball.subIssueForJingCaiLanQiu.matchName}
                            </td>
                            <td align="center">${basketball.subIssueForJingCaiLanQiu.mainTeam}</td>
                            <td align="center">${basketball.subIssueForJingCaiLanQiu.guestTeam}</td>
                            <td align="center">
                                <fmt:formatDate value="${basketball.subIssueForJingCaiLanQiu.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                <%--半场:<c:if test="${basketball.subIssueForJingCaiLanQiu.mainTeamHalfScore != null}">${basketball.subIssueForJingCaiLanQiu.mainTeamHalfScore} : ${basketball.subIssueForJingCaiLanQiu.guestTeamHalfScore}</c:if>--%>
                                <%--<br />--%>
                                全场:<c:if test="${basketball.subIssueForJingCaiLanQiu.mainTeamScore != null}">${basketball.subIssueForJingCaiLanQiu.mainTeamScore} : ${basketball.subIssueForJingCaiLanQiu.guestTeamScore}</c:if>
                            </td>
                            <td>
                                胜负:${basketball.sfResult}
                                <c:if test="${basketball.subIssueForJingCaiLanQiu.winOrNegaSp != null}">
                                    ,浮动奖金<fmt:formatNumber value="${basketball.subIssueForJingCaiLanQiu.winOrNegaSp}" pattern="0.00"/>元
                                </c:if>
                                <br />
                                让分胜负:<c:if test="${basketball.rfshResult != ''}">${basketball.rfshResult}</c:if>
                                <c:if test="${basketball.subIssueForJingCaiLanQiu.letWinOrNegaSp != null}">
                                    ,浮动奖金<fmt:formatNumber value="${basketball.subIssueForJingCaiLanQiu.letWinOrNegaSp}" pattern="0.00"/>元
                                </c:if>
                                <br />
                                胜分差:${basketball.sfcResult}
                                <br />
                                大小分:<c:if test="${basketball.dxfResult != null && basketball.dxfResult != ''}">${basketball.dxfResult}</c:if>
                                <c:if test="${basketball.subIssueForJingCaiLanQiu.bigOrLittleSp != null}">
                                    ,浮动奖金<fmt:formatNumber value="${basketball.subIssueForJingCaiLanQiu.bigOrLittleSp}" pattern="0.00"/>元
                                </c:if>
                            </td>
                            <td align="center">
                                <c:if test="${basketball.subIssueForJingCaiLanQiu.operatorsAward == null || basketball.subIssueForJingCaiLanQiu.operatorsAward == 0}">
                                    <c:if test="${basketball.subIssueForJingCaiLanQiu.mainTeamScore != null || basketball.subIssueForJingCaiLanQiu.endOperator==2}">
                                    <!--<a href="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=basketballDetail&id=${basketball.subIssueForJingCaiLanQiu.id}&operatorsAward=${operatorsAward}&startTime=${startTime}&endTime=${endTime}">启动算奖</a>
                                    -->
                                        <a href="#" onclick="toEditOpenBasketball('${basketball.subIssueForJingCaiLanQiu.id}');" >启动算奖</a>
                                    </c:if>
                                    <c:if test="${basketball.subIssueForJingCaiLanQiu.mainTeamScore == null && basketball.subIssueForJingCaiLanQiu.endOperator!=2}">
                                        <font color="#cccccc">启动算奖</font>
                                    </c:if>
                                </c:if>
                                <c:if test="${basketball.subIssueForJingCaiLanQiu.operatorsAward == 1}">算奖中</c:if>
                                <c:if test="${basketball.subIssueForJingCaiLanQiu.operatorsAward == 2}">已算奖</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="10" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=basketballMatchList"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>