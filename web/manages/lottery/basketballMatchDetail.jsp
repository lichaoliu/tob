<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="../../jstl.jsp" %>
<html>
<head>
    <title>篮球场次详情</title>
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
            竞彩篮球赛事${basketball.issue}${basketball.week}${basketball.sn}赛事详情
        </td>
    </tr>
    <tr>
        <td style="padding: 4px;">
            <table width="100%" border="0" cellpadding="4" cellspacing="0" class="seach_none">
                <tr>
                    <td align="right" class="bold">比赛日期:</td>
                    <td>
                        ${basketball.issue}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">赛事编号:</td>
                    <td>${basketball.week}${basketball.sn}</td>
                </tr>
                <tr>
                    <td align="right" class="bold">主队:</td>
                    <td>
                        ${basketball.mainTeam}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">客队:</td>
                    <td>
                        ${basketball.guestTeam}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">联赛:</td>
                    <td>
                        ${basketball.matchName}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">预计开赛时间:</td>
                    <td>
                        <fmt:formatDate value="${basketball.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">预计截止投注时间:</td>
                    <td>
                        <fmt:formatDate value="${basketball.endFuShiTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">彩期状态:</td>
                    <td>
                        <c:if test="${basketball.endOperator == 0}">销售中</c:if>
                        <c:if test="${basketball.endOperator == 1}">期结</c:if>
                        <c:if test="${basketball.endOperator == 2}">已取消</c:if>
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">算奖状态:</td>
                    <td>
                        ${myf:getOperatorsAward(basketball.operatorsAward)}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">让分:</td>
                    <td>
                        ${letBall}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">大小分:</td>
                    <td>
                        ${preCast}
                    </td>
                </tr>
                <tr>
                    <td align="right" class="bold">比赛结果</td>
                    <td class="three">
                        上半场比分:
                        <c:if test="${basketball.mainTeamHalfScore != null}">${basketball.mainTeamHalfScore}
                            : ${basketball.guestTeamHalfScore}</c:if>
                        <br/>
                        全场比分:
                        <c:if test="${basketball.mainTeamScore != null}">${basketball.mainTeamScore}
                            : ${basketball.guestTeamScore}</c:if>
                        <br/>
                        胜负:
                        <!-- 赛果 -->
                        ${sfResult}
                        <!-- 浮动奖金 -->
                        <c:if test="${sfFloatAmount != null}">
                            ,浮动奖金<fmt:formatNumber value="${sfFloatAmount}" pattern="0.00"/>元
                        </c:if>
                        <br/>
                        让分胜负:
                        <!-- 赛果 -->
                        <c:if test="${rfsfResult != null && rfsfResult != ''}">${rfsfResult}</c:if>

                        <!-- 浮动奖金 -->
                        <c:if test="${rfsfFloatAmount != null}">
                            ,浮动奖金<fmt:formatNumber value="${rfsfFloatAmount}" pattern="0.00"/>元
                        </c:if>
                        <br/>
                        胜分差:
                        <!-- 赛果 -->
                        ${sfcResult}
                        <br/>
                        大小分:
                        <!-- 赛果 -->
                        <c:if test="${dxfResult != null && dxfResult != ''}">${dxfResult}</c:if>
                        <!-- 浮动奖金 -->
                        <c:if test="${dxfFloatAmount != null}">
                            ,浮动奖金<fmt:formatNumber value="${dxfFloatAmount}" pattern="0.00"/>元
                        </c:if>
                    </td>
                </tr>
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