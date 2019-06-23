<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>北京单场场次详情</title>
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
    <link href="${pageContext.request.contextPath}/css/kjgg.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        body, td, th {
            font-family: Arial, "Times New Roman", "宋体";
        }
    </style>
    <script type="text/javascript">
        function windowClose() {
            window.close();
        }
    </script>
</head>

<body>
<table width="900" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
    <tr>
        <td class="title">
            <%--北京单场赛事${beiDan.issue}${beiDan.week}${beiDan.sn}赛事详情--%>
            ${beiDan.mainTeam} VS ${beiDan.guestTeam} 北京单场对阵详情
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                <tr>
                    <td align="right" class="bold">比赛编号:</td>
                    <td>
                        ${beiDan.issue}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">比赛序号:</td>
                    <td>${beiDan.week}${beiDan.sn}</td>
                </tr>
                <tr>
                    <td align="right" class="bold">主队:</td>
                    <td>
                        ${beiDan.mainTeam}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">客队:</td>
                    <td>
                        ${beiDan.guestTeam}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">联赛:</td>
                    <td>
                        ${beiDan.matchName}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">比赛时间:</td>
                    <td>
                        <fmt:formatDate value="${beiDan.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">让球数:</td>
                    <td>
                        ${beiDan.letBall}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">全场比分:</td>
                    <td>
                        <c:if test="${beiDan.mainTeamScore != null}">${beiDan.mainTeamScore} : ${beiDan.guestTeamScore}</c:if>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">半场比分:</td>
                    <td>
                        <c:if test="${beiDan.mainTeamHalfScore != null}">${beiDan.mainTeamHalfScore} : ${beiDan.guestTeamHalfScore}</c:if>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">赛果SP:</td>
                    <td>
                        <!-- 胜平负 -->
                        胜平负:
                        <!-- 赛果 -->
                        ${spfResult}
                        <!-- 浮动奖金 -->
                        <c:if test="${requestScope.spfFloatAmount != null}">SP:<fmt:formatNumber
                                value="${requestScope.spfFloatAmount}" pattern="0.00"/></c:if>
                        <br/>
                        上下单双:
                        <!-- 赛果 -->
                        ${sxdsResult}
                        <!-- 浮动奖金 -->
                        <c:if test="${requestScope.sxdsFloatAmount != null}">SP:<fmt:formatNumber
                                value="${requestScope.sxdsFloatAmount}" pattern="0.00"/></c:if>
                        <br/>
                        <!-- 总进球数 -->
                        总进球数:
                        <!-- 赛果 -->
                        ${zjqsResult}
                        <!-- 浮动奖金 -->
                        <c:if test="${requestScope.zjqsFloatAmount != null}">SP:<fmt:formatNumber
                                value="${requestScope.zjqsFloatAmount}" pattern="0.00"/></c:if>
                        <br/>
                        <!-- 半全场胜平负 -->
                        半全场胜平负:
                        <!-- 赛果 -->
                        ${bqcspfResult}
                        <!-- 浮动奖金 -->
                        <c:if test="${requestScope.bqcspfFloatAmount != null}">SP:<fmt:formatNumber
                                value="${requestScope.bqcspfFloatAmount}" pattern="0.00"/></c:if>
                        <br/>
                        <!-- 比分 -->
                        比分:
                        <!-- 赛果 -->
                        ${bfResult}
                        <!-- 浮动奖金 -->
                        <c:if test="${requestScope.bfFloatAmount != null}">SP:<fmt:formatNumber
                                value="${requestScope.bfFloatAmount}" pattern="0.00"/></c:if>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">及时SP:</td>
                    <td>
                        <!-- 胜平负 -->
                        <b>胜平负</b>:
                        <c:if test="${beiDan.winSp != null}">胜(<fmt:formatNumber
                                value="${beiDan.winSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.flatSp != null}">平(<fmt:formatNumber
                                value="${beiDan.flatSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.negaSp != null}">负(<fmt:formatNumber
                                value="${beiDan.negaSp}" pattern="0.00"/>)</c:if>
                        <br/>
                        <b>上下单双</b>:
                        <c:if test="${beiDan.shangDanSp != null}">上单(<fmt:formatNumber
                                value="${beiDan.shangDanSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.shangShuangSp != null}">上双(<fmt:formatNumber
                                value="${beiDan.shangShuangSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.xiaDanSp != null}">下单(<fmt:formatNumber
                                value="${beiDan.xiaDanSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.xiaShuangSp != null}">下双(<fmt:formatNumber
                                value="${beiDan.xiaShuangSp}" pattern="0.00"/>)</c:if>
                        <br/>
                        <!-- 总进球数 -->
                        <b>总进球数</b>:
                        <c:if test="${beiDan.totalGoal0Sp != null}">0(<fmt:formatNumber
                                value="${beiDan.totalGoal0Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal1Sp != null}">1(<fmt:formatNumber
                                value="${beiDan.totalGoal1Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal2Sp != null}">2(<fmt:formatNumber
                                value="${beiDan.totalGoal2Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal3Sp != null}">3(<fmt:formatNumber
                                value="${beiDan.totalGoal3Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal4Sp != null}">4(<fmt:formatNumber
                                value="${beiDan.totalGoal4Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal5Sp != null}">5(<fmt:formatNumber
                                value="${beiDan.totalGoal5Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal6Sp != null}">6(<fmt:formatNumber
                                value="${beiDan.totalGoal6Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.totalGoal7Sp != null}">7+(<fmt:formatNumber
                                value="${beiDan.totalGoal7Sp}" pattern="0.00"/>)</c:if>
                        <br/>
                        <!-- 半全场胜平负 -->
                        <b>半全场胜平负</b>:
                        <c:if test="${beiDan.halfCourtFFSp != null}">负负(<fmt:formatNumber
                                value="${beiDan.halfCourtFFSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtFPSp != null}">负平(<fmt:formatNumber
                                value="${beiDan.halfCourtFPSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtFSSp != null}">负胜(<fmt:formatNumber
                                value="${beiDan.halfCourtFSSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtPFSp != null}">平负(<fmt:formatNumber
                                value="${beiDan.halfCourtPFSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtPPSp != null}">平平(<fmt:formatNumber
                                value="${beiDan.halfCourtPPSp}" pattern="0.00"/>),</c:if>
                        <br/>
                        <c:if test="${beiDan.halfCourtPSSp != null}">平胜(<fmt:formatNumber
                                value="${beiDan.halfCourtPSSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtSFSp != null}">胜负(<fmt:formatNumber
                                value="${beiDan.halfCourtSFSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtSPSp != null}">胜平(<fmt:formatNumber
                                value="${beiDan.halfCourtSPSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.halfCourtSSSp != null}">胜胜(<fmt:formatNumber
                                value="${beiDan.halfCourtSSSp}" pattern="0.00"/>)</c:if>
                        <br/>
                        <!-- 比分 -->
                        <b>比分</b>:
                        <c:if test="${beiDan.score00Sp != null}">0:0(<fmt:formatNumber
                                value="${beiDan.score00Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score01Sp != null}">0:1(<fmt:formatNumber
                                value="${beiDan.score01Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score02Sp != null}">0:2(<fmt:formatNumber
                                value="${beiDan.score02Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score03Sp != null}">0:3(<fmt:formatNumber
                                value="${beiDan.score03Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score04Sp != null}">0:4(<fmt:formatNumber
                                value="${beiDan.score04Sp}" pattern="0.00"/>),</c:if>
                        <br/>
                        <c:if test="${beiDan.score10Sp != null}">1:0(<fmt:formatNumber
                                value="${beiDan.score10Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score11Sp != null}">1:1(<fmt:formatNumber
                                value="${beiDan.score11Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score12Sp != null}">1:2(<fmt:formatNumber
                                value="${beiDan.score12Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score13Sp != null}">1:3(<fmt:formatNumber
                                value="${beiDan.score13Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score14Sp != null}">1:4(<fmt:formatNumber
                                value="${beiDan.score14Sp}" pattern="0.00"/>),</c:if>
                        <br/>
                        <c:if test="${beiDan.score20Sp != null}">2:0(<fmt:formatNumber
                                value="${beiDan.score20Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score21Sp != null}">2:1(<fmt:formatNumber
                                value="${beiDan.score21Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score22Sp != null}">2:2(<fmt:formatNumber
                                value="${beiDan.score22Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score23Sp != null}">2:3(<fmt:formatNumber
                                value="${beiDan.score23Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score24Sp != null}">2:4(<fmt:formatNumber
                                value="${beiDan.score24Sp}" pattern="0.00"/>),</c:if>
                        <br/>
                        <c:if test="${beiDan.score30Sp != null}">3:0(<fmt:formatNumber
                                value="${beiDan.score30Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score31Sp != null}">3:1(<fmt:formatNumber
                                value="${beiDan.score31Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score32Sp != null}">3:2(<fmt:formatNumber
                                value="${beiDan.score32Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score33Sp != null}">3:3(<fmt:formatNumber
                                value="${beiDan.score33Sp}" pattern="0.00"/>),</c:if>
                        <br/>
                        <c:if test="${beiDan.score40Sp != null}">4:0(<fmt:formatNumber
                                value="${beiDan.score40Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score41Sp != null}">4:1(<fmt:formatNumber
                                value="${beiDan.score41Sp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.score42Sp != null}">4:2(<fmt:formatNumber
                                value="${beiDan.score42Sp}" pattern="0.00"/>),</c:if>
                        <br/>
                        <c:if test="${beiDan.scoreSQTSp != null}">胜其他(<fmt:formatNumber
                                value="${beiDan.scoreSQTSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.scorePQTSp != null}">平其它(<fmt:formatNumber
                                value="${beiDan.scorePQTSp}" pattern="0.00"/>),</c:if>
                        <c:if test="${beiDan.scoreFQTSp != null}">负其它(<fmt:formatNumber
                                value="${beiDan.scoreFQTSp}" pattern="0.00"/>)</c:if>
                    </td>
                </tr>
                
                <%--<tr>--%>
                    <%--<td align="right" class="bold">预计截止投注时间:</td>--%>
                    <%--<td>--%>
                        <%--<fmt:formatDate value="${beiDan.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td align="right" class="bold">彩期状态:</td>--%>
                    <%--<td>--%>
                        <%--<c:if test="${beiDan.endOperator == 0}">销售中</c:if>--%>
                        <%--<c:if test="${beiDan.endOperator == 1}">期结</c:if>--%>
                        <%--<c:if test="${beiDan.endOperator == 2}">已取消</c:if>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td align="right" class="bold">算奖状态:</td>--%>
                    <%--<td>--%>
                        <%--${myf:getOperatorsAward(beiDan.operatorsAward)}--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td align="right" class="bold">让分:</td>--%>
                    <%--<td>--%>
                        <%--${beiDan.letBall}--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td align="right" class="bold">比赛结果</td>--%>
                    <%--<td>--%>
                        <%--上半场比分:--%>
                        <%--<c:if test="${beiDan.mainTeamHalfScore != null}">${beiDan.mainTeamHalfScore}--%>
                            <%--: ${beiDan.guestTeamHalfScore}</c:if>--%>
                        <%--<br/>--%>
                        <%--全场比分:--%>
                        <%--<c:if test="${beiDan.mainTeamScore != null}">${beiDan.mainTeamScore}--%>
                            <%--: ${beiDan.guestTeamScore}</c:if>--%>
                        <%--<br/>--%>
                        <%--<!-- 胜平负 -->--%>
                        <%--胜平负:--%>
                        <%--<!-- 赛果 -->--%>
                        <%--${spfResult}--%>
                        <%--<!-- 浮动奖金 -->--%>
                        <%--<c:if test="${requestScope.spfFloatAmount != null}">浮动奖金<fmt:formatNumber--%>
                                <%--value="${requestScope.spfFloatAmount}" pattern="0.00"/>元</c:if>--%>
                        <%--<br/>--%>
                        <%--上下单双:--%>
                        <%--<!-- 赛果 -->--%>
                        <%--${sxdsResult}--%>
                        <%--<!-- 浮动奖金 -->--%>
                        <%--<c:if test="${requestScope.sxdsFloatAmount != null}">浮动奖金<fmt:formatNumber--%>
                                <%--value="${requestScope.sxdsFloatAmount}" pattern="0.00"/>元</c:if>--%>
                        <%--<br/>--%>
                        <%--<!-- 总进球数 -->--%>
                        <%--总进球数:--%>
                        <%--<!-- 赛果 -->--%>
                        <%--${zjqsResult}--%>
                        <%--<!-- 浮动奖金 -->--%>
                        <%--<c:if test="${requestScope.zjqsFloatAmount != null}">浮动奖金<fmt:formatNumber--%>
                                <%--value="${requestScope.zjqsFloatAmount}" pattern="0.00"/>元</c:if>--%>
                        <%--<br/>--%>
                        <%--<!-- 半全场胜平负 -->--%>
                        <%--半全场胜平负:--%>
                        <%--<!-- 赛果 -->--%>
                        <%--${bqcspfResult}--%>
                        <%--<!-- 浮动奖金 -->--%>
                        <%--<c:if test="${requestScope.bqcspfFloatAmount != null}">浮动奖金<fmt:formatNumber--%>
                                <%--value="${requestScope.bqcspfFloatAmount}" pattern="0.00"/>元</c:if>--%>
                        <%--<br/>--%>
                        <%--<!-- 比分 -->--%>
                        <%--<!-- 赛果 -->--%>
                        <%--比分:${bfResult}--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" class="submit" value="关闭" style="width: 64px; border: none"
                               onclick="javascript:window.close();"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</div>
</body>
</html>