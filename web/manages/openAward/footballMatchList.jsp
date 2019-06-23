<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>竞彩足球赛果录入</title>
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
        function toEditOpenFootball(id){
            var matchNo= $("#matchNo").val();
            var mainTeam= $("#mainTeam").val();
            var guestTeam= $("#guestTeam").val();
            var matchName= $("#matchName").val();
            var operatorsAward= $("#operatorsAward").val();
            var page= $("#pageId").val();
            window.location.href='${pageContext.request.contextPath}/manages/openAward?action=editOpenFootball&id='+id+'&matchNo='+encodeURIComponent(encodeURIComponent(matchNo))+"&mainTeam="+encodeURIComponent(encodeURIComponent(mainTeam))+"&guestTeam="+encodeURIComponent(encodeURIComponent(guestTeam))+"&matchName="+encodeURIComponent(encodeURIComponent(matchName))+"&operatorsAward="+encodeURIComponent(encodeURIComponent(operatorsAward))+"&page="+page;
        }
    </script>
</head>
<body>
<c:set var="lotteryList" value="${myf:findAllLotteryCode()}"></c:set>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table">
    <tr>
        <td class="title" style="text-align:left;">
            开奖管理 >> 竞彩足球录入
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <input type="hidden" value="${requestScope.page}" id="pageId"/>

            <form name="form1" method="post"
                  action="${pageContext.request.contextPath}/manages/openAward?action=openFootball">
                <table width="100%" border="1" cellpadding="4" cellspacing="0" class="seach">
                    <tr>
                        <td style="width:4%">赛事编号</td>
                        <td style="width:10%">
                            <input type="text" name="matchNo" id="matchNo" value="${matchNo}" class="input">
                        </td>

                        <td style="width:5%">主队</td>
                        <td style="width:10%">
                            <input type="text" name="mainTeam" id="mainTeam" value="${mainTeam}" class="input">
                        </td>
                        <td style="width:5%">客队</td>
                        <td style="width:10%">
                            <input type="text" name="guestTeam" id="guestTeam" value="${guestTeam}" class="input">
                        </td>
                        <td style="width:5%">联赛</td>
                        <td style="width:10%">
                            <input type="text" name="matchName" id="matchName" value="${matchName}" class="input">
                        </td>

                    </tr>
                    <tr>
                        <td style="width:5%">赛事日期</td>
                        <td style="width:15%">
                            <input style="width: 83px;" type="text" name="startTime" value="${startTime}"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:30%" readonly="readonly">-
                            <input style="width: 83px;" type="text" name="endTime"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                                   value="${endTime}" style="width:30%" readonly="readonly">
                        </td>
                        <td style="width:5%">算奖状态</td>
                        <td style="width:10%">
                            <select name="operatorsAward" id="operatorsAward" >
                                <option value="0" <c:if test="${'0' == operatorsAward }">selected</c:if>>全部</option>
                                <option value="1" <c:if test="${'1' == operatorsAward }">selected</c:if>>开奖中</option>
                                <option value="2" <c:if test="${'2' == operatorsAward }">selected</c:if>>已算奖</option>
                                <option value="3" <c:if test="${'3' == operatorsAward }">selected</c:if>>已取消</option>
                            </select>
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
                        <td align="left" colspan="11">
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
                    <td width="8%">止售时间</td>
                    <td width="8%">对阵比分</td>
                    <td width="17%">赛果</td>
                    <td>录入赛果</td>
                </tr>
                <c:if test="${pageBean.pageContent==null}">
                    <tr>
                        <td colspan="11" align="center">请在上面填写筛选条件，并点击查询按钮来查询数据！</td>
                    </tr>
                </c:if>
                <c:if test="${pageBean.pageContent!=null}">
                    <c:forEach var="football" items="${pageBean.pageContent}" varStatus="cont">
                        <tr>
                            <td align="center">
                                    ${(page-1) * requestScope.pageSize + cont.count}
                            </td>
                            <td align="center">
                                    ${football.issue}
                            </td>
                            <td align="center">
                                    ${football.week}${football.sn}
                            </td>
                            <td align="center">
                                    ${football.matchName}
                            </td>
                            <td align="center">${football.mainTeam}</td>
                            <td align="center">${football.guestTeam}</td>
                            <td align="center"><c:if
                                    test="${football.letBall != null}">让球${football.letBall}</c:if></td>
                            <td align="left">
                                <fmt:formatDate value="${football.endFuShiTime}" pattern="yyyy-MM-dd"/><br/>
                                <fmt:formatDate value="${football.endFuShiTime}" pattern="HH:mm:ss"/>
                            </td>
                            <td align="left">
                                全场:<c:if test="${football.mainTeamScore != null}">${football.mainTeamScore}:${football.guestTeamScore}</c:if><br/>
                                半场:<c:if test="${football.mainTeamHalfScore != null}">${football.mainTeamHalfScore}:${football.guestTeamHalfScore}</c:if>
                            </td>
                            <td>
                                <%--比分:<c:if--%>
                                    <%--test="${football.mainTeamHalfScore != null}">${football.mainTeamHalfScore}:${football.guestTeamHalfScore}</c:if>--%>
                                <%--/<c:if--%>
                                    <%--test="${football.mainTeamScore != null}">${football.mainTeamScore}:${football.guestTeamScore}</c:if>--%>
                                <br/>
                                让球胜平负:<c:if
                                    test="${football.mainTeamScore != null && football.guestTeamScore != null}">${myf:rfspfScore(football.mainTeamScore,football.guestTeamScore,football.letBall)} SP:</c:if>${football.winOrNegaSp}
                                <br/>
                                胜平负:<c:if
                                    test="${football.mainTeamScore != null && football.guestTeamScore != null}">${myf:rfspfScore(football.mainTeamScore,football.guestTeamScore,'')} SP:</c:if>${football.spfWinOrNegaSp}
                                <br/>                                
                                总进球数:<c:if
                                    test="${football.mainTeamScore != null && football.guestTeamScore != null}">${football.mainTeamScore + football.guestTeamScore} SP:</c:if>${football.totalGoalSp}
                                <br/>
                                半全场胜平负:<c:if
                                    test="${football.mainTeamScore != null && football.guestTeamScore != null && football.mainTeamHalfScore != null && football.guestTeamHalfScore != null}">${football.mainTeamHalfScore > football.guestTeamHalfScore ? "胜" : (football.mainTeamHalfScore == football.guestTeamHalfScore ? "平" : (football.mainTeamHalfScore < football.guestTeamHalfScore ? "负" : ""))}${football.mainTeamScore > football.guestTeamScore ? "胜" : (football.mainTeamScore == football.guestTeamScore ? "平" : (football.mainTeamScore < football.guestTeamScore ? "负" : ""))} SP:</c:if>${football.halfCourtSp}
                                <br/>
                                比分:<c:if test="${football.mainTeamScore != null}"><c:set var="bfjg"
                                                                                         value="${football.mainTeamScore == 3 && football.guestTeamScore == 4 ? '负其它' : (football.mainTeamScore == 4 && football.guestTeamScore == 3 ? '胜其它' : (football.mainTeamScore > 5 || football.guestTeamScore > 5 || football.mainTeamScore + football.guestTeamScore > 7 ? (football.mainTeamScore >football.guestTeamScore ? '胜其它' : (football.mainTeamScore == football.guestTeamScore ? '平其它' : '负其它')) : '111')) }"></c:set><c:if
                                    test="${bfjg == '111'}">${football.mainTeamScore}:${football.guestTeamScore}</c:if><c:if
                                    test="${bfjg != '111'}">${bfjg}</c:if></c:if>
                            </td>
                            <td align="center">
                                <c:if test="${(football.endOperator == 1 || football.endOperator == 2) && football.bonusOperator == 0}">
                                    <a href="#" onclick="toEditOpenFootball('${football.id}');" >开奖中(编辑)</a>
                                </c:if>
                                <c:if test="${football.endOperator == 1 && football.bonusOperator == 1}">已算奖</c:if>
                                    <%--<c:if test="${football.endOperator == 2}">已取消</c:if>--%>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="11" align="right">
                            <page:pageTag page="${requestScope.page}" pageSize="${requestScope.pageSize}"
                                          total="${pageBean.itemTotal}"
                                          gotoURI="${pageContext.request.contextPath}/manages/openAward?action=openFootball"/>
                        </td>
                    </tr>
                </c:if>
            </table>
        </td>
    </tr>
</table>
</body>
</html>