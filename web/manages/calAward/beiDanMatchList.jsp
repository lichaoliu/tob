<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>北京单场场次查询</title>
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
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            开奖管理 >> 北单算奖
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">


            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=beiDanMatchList">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td style="width:4%">期号范围</td>
                        <td style="width:15%">
                            <input style="width: 80px;" type="text" name="issueStart" value="${issueStart}"
                                   class="input">-
                            <input style="width: 80px;" type="text" name="issueEnd" value="${issueEnd}" class="input">
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
                                    <option value="${award.key}"
                                            <c:if test="${award.key == operatorsAward}">selected</c:if> >${award.value}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td style="width:5%">开赛时间</td>
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
                        <td align="left" colspan="11">
                            <span style="color:#0033cc;font-weight: bold;">[本次查询结果共${pageBean.itemTotal }条记录]</span>
                        </td>
                    </tr>
                </c:if>
                <tr id="one">
                    <td width="5%">序号</td>
                    <td width="8%">赛事编号</td>
                    <td width="8%">联赛</td>
                    <td width="8%">主队</td>
                    <td width="8%">客队</td>
                    <td width="8%">让球</td>
                    <td width="10%">开赛时间</td>
                    <td width="12%">进行状态</td>
                    <td width="14%">对阵赛果</td>
                    <td width="8%">对阵编辑</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="11" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="beiDan" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                                    ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td align="left">
                                期号:${beiDan.issue}<br/>
                                场次:${beiDan.week}${beiDan.sn}
                            </td>
                            <td align="center">
                                    ${beiDan.matchName}
                            </td>
                            <td align="center">${beiDan.mainTeam}</td>
                            <td align="center">${beiDan.guestTeam}</td>
                            <td align="center">
                                    ${beiDan.letBall}
                            </td>
                            <td align="center">
                                    <%--止售:<fmt:formatDate value="${beiDan.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
                                    <%--<br />--%>
                                <fmt:formatDate value="${beiDan.endTime}" pattern="yyyy-MM-dd"/><br/>
                                <fmt:formatDate value="${beiDan.endTime}" pattern="HH:mm:ss"/>
                            </td>
                            <td align="left">
                                对阵:<c:if test="${beiDan.endOperator == 0}">销售中</c:if>
                                <c:if test="${beiDan.endOperator == 1}">期结</c:if>
                                <c:if test="${beiDan.endOperator == 2}">已取消</c:if>
                                <br/>
                                算奖:${myf:getOperatorsAward(beiDan.operatorsAward)}
                                <br/>
                                全场:<c:if
                                    test="${beiDan.mainTeamScore != null}">${beiDan.mainTeamScore} : ${beiDan.guestTeamScore}</c:if>
                                <br/>
                                半场:<c:if
                                    test="${beiDan.mainTeamHalfScore != null}">${beiDan.mainTeamHalfScore} : ${beiDan.guestTeamHalfScore}</c:if>
                            </td>
                            <td align="left">
                                胜平负:<c:if
                                    test="${beiDan.mainTeamScore != null}">${myf:rfspfScore(beiDan.mainTeamScore,beiDan.guestTeamScore,beiDan.letBall)}</c:if>
                                <c:if test="${beiDan.winOrNegaSp != null}">(<fmt:formatNumber
                                        value="${beiDan.winOrNegaSp}" pattern="0.00"/>)</c:if><br/>
                                比 分:<c:if
                                    test="${beiDan.mainTeamScore != null}">${myf:bfScoreForBeiDan(beiDan.mainTeamScore,beiDan.guestTeamScore)}</c:if>
                                <c:if test="${beiDan.scoreSp != null}">(<fmt:formatNumber value="${beiDan.scoreSp}"
                                                                                          pattern="0.00"/>)</c:if><br/>
                                进球数:<c:if
                                    test="${beiDan.mainTeamScore != null}">${beiDan.mainTeamScore+beiDan.guestTeamScore}</c:if>
                                <c:if test="${beiDan.totalGoalSp != null}">(<fmt:formatNumber
                                        value="${beiDan.totalGoalSp}" pattern="0.00"/>)</c:if><br/>
                                半全场:<c:if
                                    test="${beiDan.mainTeamScore != null}">${myf:rfspfScore(beiDan.mainTeamHalfScore,beiDan.guestTeamHalfScore,"0")}${myf:rfspfScore(beiDan.mainTeamScore,beiDan.guestTeamScore,"0")}</c:if>
                                <c:if test="${beiDan.halfCourtSp != null}">(<fmt:formatNumber
                                        value="${beiDan.halfCourtSp}" pattern="0.00"/>)</c:if><br/>
                                上下单双:<c:if
                                    test="${beiDan.mainTeamScore != null}">${myf:sxdsScore(beiDan.mainTeamScore,beiDan.guestTeamScore)}</c:if>
                                <c:if test="${beiDan.shangXiaPanSp != null}">(<fmt:formatNumber
                                        value="${beiDan.shangXiaPanSp}" pattern="0.00"/>)</c:if>
                            </td>
                            <td align="center">
                                <c:if test="${beiDan.operatorsAward == 0}">
                                    <c:if test="${beiDan.mainTeamScore != null || beiDan.endOperator==2}">
                                        <a href="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=beiDanDetail&id=${beiDan.id}">启动算奖</a>
                                    </c:if>
                                    <c:if test="${beiDan.mainTeamScore == null && beiDan.endOperator!=2}">
                                        <font color="#cccccc">启动算奖</font>
                                    </c:if>
                                </c:if>
                                <c:if test="${beiDan.operatorsAward != 0}">${myf:getOperatorsAward(beiDan.operatorsAward)}</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="11" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=beiDanMatchList"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>