<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>足球场次详情</title>
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

        $(document).ready(function() {
            $("#calAwardBtn").click(function() {
                var bln = confirm("是否确认开始算奖?");
                if (bln) {
                    $("#awardForm").submit();
                } else {
                    history.go(-1);
                }
            });
        });
    </script>
</head>

<body>
<form id="awardForm" method="post"
      action="${pageContext.request.contextPath}/manages/calculateAwardServlet?action=footballCalculateAward">
    <input type="hidden" name="issue" value="${football.issue}"/>
    <input type="hidden" name="sn" value="${football.sn}"/>
    <input type="hidden" name="week" value="${football.week}"/>
    <input type="hidden" name="matchNo" value="${matchNo}"/>
    <input type="hidden" name="mainTeam" value="${mainTeam}"/>
    <input type="hidden" name="guestTeam" value="${guestTeam}"/>
    <input type="hidden" name="matchName" value="${matchName}"/>
    <input type="hidden" name="operatorsAward" value="${operatorsAward}"/>
    <input type="hidden" name="startTime" value="${startTime}"/>
    <input type="hidden" name="endTime" value="${endTime}"/>
    <input type="hidden" name="page" value="${page}"/>
    <input type="hidden" name="pageSize" value="${pageSize}"/>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table1">
        <tr>
            <td class="title">
                竞彩足球赛事${football.issue}${football.week}${football.sn}算奖启动
            </td>
        </tr>
        <tr>
            <td style="padding: 4px;">
                <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                    <tr>
                        <td align="right" class="bold">比赛日期:</td>
                        <td>
                            ${football.issue}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">赛事编号:</td>
                        <td>${football.week}${football.sn}</td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">主队:</td>
                        <td>
                            ${football.mainTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">客队:</td>
                        <td>
                            ${football.guestTeam}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">联赛:</td>
                        <td>
                            ${football.matchName}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预计开赛时间:</td>
                        <td>
                            <fmt:formatDate value="${football.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">预计截止投注时间:</td>
                        <td>
                            <fmt:formatDate value="${football.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">彩期状态:</td>
                        <td>
                            <c:if test="${football.endOperator == 0}">销售中</c:if>
                            <c:if test="${football.endOperator == 1}">期结</c:if>
                            <c:if test="${football.endOperator == 2}">已取消</c:if>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">算奖状态:</td>
                        <td>
                            ${myf:getOperatorsAward(football.operatorsAward)}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">让球:</td>
                        <td>
                            ${football.letBall}
                        </td>
                    </tr>
                    <tr>
                        <td align="right" class="bold">比赛结果</td>
                        <td>
                            上半场比分:
                            <c:if test="${football.mainTeamHalfScore != null}">${football.mainTeamHalfScore}
                                : ${football.guestTeamHalfScore}</c:if>
                            <br/>
                            全场比分:
                            <c:if test="${football.mainTeamScore != null}">${football.mainTeamScore}
                                : ${football.guestTeamScore}</c:if>
                            <br/><br/>
                            <!-- 胜平负 -->
                            胜平负:
                            <!-- 赛果 -->
                            ${spfResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.spfFloatAmount != null}">浮动奖金<fmt:formatNumber
                                    value="${requestScope.spfFloatAmount}" pattern="0.00"/>元</c:if>
                            <br/>
                            让球胜平负:
                            <!-- 赛果 -->
                            ${rqspfResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.rqspfFloatAmount != null}">浮动奖金<fmt:formatNumber
                                    value="${requestScope.rqspfFloatAmount}" pattern="0.00"/>元</c:if>
                            <br/>                            
                            <!-- 总进球数 -->
                            总进球数:
                            <!-- 赛果 -->
                            ${zjqsResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.zjqsFloatAmount != null}">浮动奖金<fmt:formatNumber
                                    value="${requestScope.zjqsFloatAmount}" pattern="0.00"/>元</c:if>
                            <br/>
                            <!-- 半全场胜平负 -->
                            半全场胜平负:
                            <!-- 赛果 -->
                            ${bqcspfResult}
                            <!-- 浮动奖金 -->
                            <c:if test="${requestScope.bqcspfFloatAmount != null}">浮动奖金<fmt:formatNumber
                                    value="${requestScope.bqcspfFloatAmount}" pattern="0.00"/>元</c:if>
                            <br/>
                            <!-- 比分 -->
                            <!-- 赛果 -->
                            比分:${bfResult}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <input type="button" class="submit" value="开始算奖" id="calAwardBtn"
                                   style="width: 100px; border: none"/>

                            <input type="button" class="submit" value="取消" style="width: 64px; border: none"
                                   onclick="javascript:history.go(-1);"/>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>