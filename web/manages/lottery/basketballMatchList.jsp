<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>竞彩篮球场次查询</title>
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
        function goMatchDetail(msg) {
          var flag = window.open('<%=request.getContextPath()%>/manages/lottery/basketballMatchDetailPs.jsp?id='+msg,new Array(),'dialogHeight:590px;dialogWidth:1037px;dialogLeft:150px');
      	}
    </script>
</head>
<body>
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            彩期管理 >> 竞彩篮球查询
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">


            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/issueManagesServlet?action=basketballMatchList">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td style="width:5%">赛事编号</td>
                        <td style="width:10%">
                           <input type="text" name="matchNo" value="${matchNo}" class="input">
                        </td>

                        <td style="width:5%">主队</td>
                        <td style="width:10%">
                            <input type="text" name="mainTeam" value="${mainTeam}" class="input">
                        </td>
                        <td style="width:5%">客队</td>
                        <td style="width:10%">
                            <input type="text" name="guestTeam" value="${guestTeam}" class="input">
                        </td>
                        <td style="width:5%">联赛</td>
                        <td style="width:10%">
                            <input type="text" name="matchName" value="${matchName}" class="input">
                        </td>
                        
                    </tr>
                    <tr>
                        <td style="width:5%">销售状态</td>
                        <td style="width:10%">
                            <select name="endOperator">
                                <option value="" <c:if test="${'' == endOperator}">selected</c:if>>全部</option>
                                <option value="3" <c:if test="${'3' == endOperator}">selected</c:if>>预售</option>
                                <option value="0" <c:if test="${'0' == endOperator}">selected</c:if>>销售中</option>
                                <option value="1" <c:if test="${'1' == endOperator}">selected</c:if>>期结</option>
                                <option value="2" <c:if test="${'2' == endOperator}">selected</c:if>>已取消</option>
                            </select>
                        </td>
                        <td style="width:5%">算奖状态</td>
                        <td style="width:10%">
                            <select name="operatorsAward">
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
                        <td align="left" colspan="12">
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
                    <td width="8%">规则</td>
                    <td width="17%">预计止售时间</td>
                    <td width="10%">比分</td>
                    <td width="7%">销售状态</td>
                    <td width="8%">数据状态</td>
                    <td>操作</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="12" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="basketball" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                                ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td align="center">
                                ${basketball.issue}
                            </td>
                            <td align="center">
                                <!--  <a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=basketballDetail&id=${basketball.id}" target="_blank">${basketball.week}${basketball.sn}</a>-->
                            	<a href="javascript:void(0)" onclick="goMatchDetail(${basketball.id});">${basketball.week}${basketball.sn}</a>
                            </td>
                            <td align="center">
                                ${basketball.matchName}
                            </td>
                            <td align="center">${basketball.mainTeam}</td>
                            <td align="center">${basketball.guestTeam}</td>
                            <td align="left">
                                让分:${basketball.letBall}
                                <br/>
                                大小分:${basketball.preCast}
                            </td>
                            <td>
                                止售:<fmt:formatDate value="${basketball.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                <br />
                                开赛:<fmt:formatDate value="${basketball.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td>
                                半场:<c:if test="${basketball.mainTeamHalfScore != null}">${basketball.mainTeamHalfScore} : ${basketball.guestTeamHalfScore}</c:if>
                                <br />
                                全场:<c:if test="${basketball.mainTeamScore != null}">${basketball.mainTeamScore} : ${basketball.guestTeamScore}</c:if>
                            </td>
                            <td align="center">
                                <c:if test="${basketball.endOperator == 0}">销售中</c:if>
                                <c:if test="${basketball.endOperator == 1}">期结</c:if>
                                <c:if test="${basketball.endOperator == 2}">已取消</c:if>
                                <br/>
                                ${myf:getOperatorsAward(basketball.operatorsAward)}
                            </td>
                            <td align="left">
                                更新:${basketball.backup1 == '1' ? '不自动':'自动'}<br/>
                                隐藏:${basketball.backup2 == '1' ? '是':'否'}
                            </td>
                            <td align="center">
                                <%--<c:if test="${basketball.endOperator == 0}"><a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=cancelOrRecoveryMatch&type=cancel&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">取消对阵</a></c:if>--%>
                                <%--<c:if test="${basketball.endOperator == 1}">--</c:if>--%>
                                <c:if test="${basketball.endOperator == 2}"><a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=cancelOrRecoveryMatch&type=recovery&lotteryCode=201&issue=${basketball.issue}&sn=${basketball.sn}&id=${basketball.id}">恢复对阵</a></c:if>
                                <br/><a href="${pageContext.request.contextPath}/manages/issueManagesServlet?action=issueMatchEdit&lotteryCode=201&id=${basketball.id}">修改</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="12" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/issueManagesServlet?action=basketballMatchList"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>